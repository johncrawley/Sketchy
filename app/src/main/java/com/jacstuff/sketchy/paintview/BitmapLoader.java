package com.jacstuff.sketchy.paintview;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jacstuff.sketchy.paintview.history.HistoryItem;

public class BitmapLoader {

    private Matrix matrix;
    private final PaintView paintView;
    private final Canvas canvas;
    private final Paint drawPaint;


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
        Rect dest = new Rect(0,0, paintView.getWidth(), paintView.getHeight());
        canvas.drawBitmap(bitmapToDraw, src, dest, drawPaint);
    }


    private Bitmap getRotatedBitmap(Bitmap bm, int currentOrientation){
        int angle = currentOrientation == Configuration.ORIENTATION_LANDSCAPE ? -90 : 90;
        Matrix m = new Matrix();
        m.postRotate(angle);
        return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
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
