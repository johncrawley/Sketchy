package com.jacstuff.sketchy.paintview;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;

import com.jacstuff.sketchy.paintview.history.HistoryItem;

public class BitmapLoader {

    private Matrix matrix;
    private final PaintView paintView;
    private final Canvas canvas;
    private final Paint drawPaint;
    private int largestCanvasWidthForLandscape, largestCanvasHeightForLandscape;
    private int largestCanvasWidthForPortrait, largestCanvasHeightForPortrait;


    public BitmapLoader(PaintView paintView, Canvas canvas, Paint drawPaint){
        this.paintView = paintView;
        this.canvas = canvas;
        this.drawPaint = drawPaint;
    }


    Bitmap getCorrectlyOrientatedBitmapFrom(HistoryItem historyItem){
        if(historyItem == null){
            return null;
        }
        Bitmap bitmapToDraw = historyItem.getBitmap();
        if(bitmapToDraw == null){
            return null;
        }
        initMatrixIfNull();
        int currentOrientation = getScreenOrientation();
        if(historyItem.getOrientation() != currentOrientation) {
            bitmapToDraw = getRotatedBitmap(bitmapToDraw, currentOrientation);
        }
        return bitmapToDraw;
    }


    void drawBitmapToScale(Bitmap bitmapToDraw){
        Rect src = new Rect(0,0, bitmapToDraw.getWidth(), bitmapToDraw.getHeight());
        Rect dest = createCanvasRect();
        canvas.drawBitmap(bitmapToDraw, src, dest, drawPaint);
        System.out.println("BitmapLoader.drawBitmapToScale: history bitmap dimensions: " + bitmapToDraw.getWidth() + "," + bitmapToDraw.getHeight() + " paintView max dimensions: " + largestCanvasWidthForPortrait + ", " + largestCanvasHeightForPortrait);
    }


    private Bitmap getRotatedBitmap(Bitmap bm, int currentOrientation){
        int angle = currentOrientation == Configuration.ORIENTATION_LANDSCAPE ? -90 : 90;
        Matrix m = new Matrix();
        m.postRotate(angle);
        return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
    }


    /*
        On rare occasions, selecting "undo" was painting a history bitmap that was smaller than the canvas dimensions.
        This method is an attempt to always create the largest destination Rect for the loaded history bitmap,
         comparing the previously measured size of paintView to the current.
     */
    private Rect createCanvasRect(){
        return paintView.isInPortrait() ? createPortraitCanvasRect() : createLandscapeCanvasRect();
    }


    private Rect createPortraitCanvasRect(){
        largestCanvasWidthForPortrait = Math.max(paintView.getMeasuredWidth(), largestCanvasWidthForPortrait);
        largestCanvasHeightForPortrait = Math.max(paintView.getMeasuredHeight(), largestCanvasHeightForPortrait);
        return new Rect(0,0, largestCanvasWidthForPortrait, largestCanvasHeightForPortrait);
    }


    private Rect createLandscapeCanvasRect(){
        largestCanvasWidthForLandscape = Math.max(paintView.getMeasuredWidth(), largestCanvasWidthForLandscape);
        largestCanvasHeightForLandscape = Math.max(paintView.getMeasuredHeight(), largestCanvasHeightForLandscape);
        return new Rect(0,0, largestCanvasWidthForLandscape, largestCanvasHeightForLandscape);
    }


    private void initMatrixIfNull(){
        if(matrix == null){
            matrix = new Matrix();
            matrix.postScale(1,1);
        }
    }


    private int getScreenOrientation(){
        return paintView.getScreenOrientation();
    }


}
