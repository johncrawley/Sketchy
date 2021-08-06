package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.KaleidoscopeHelper;
import com.jacstuff.sketchy.viewmodel.MainViewModel;


public class KaleidoscopeDrawer {

    Canvas canvas;
    final MainViewModel viewModel;
    final KaleidoscopeHelper kaleidoscopeHelper;
    final PaintView paintView;
    final Paint infinityPaint;
    Bitmap infinityImage;
    private Drawer parentDrawer;


    public KaleidoscopeDrawer(PaintView paintView, MainViewModel viewModel, KaleidoscopeHelper kaleidoscopeHelper){
        this.paintView = paintView;
        this.canvas = paintView.getCanvas();
        this.viewModel = viewModel;
        infinityPaint = new Paint();
        this.kaleidoscopeHelper = kaleidoscopeHelper;
    }


    public void setCanvas(Canvas canvas){
        this.canvas = canvas;
    }

    public void initParentDrawer(Drawer drawer){
        parentDrawer = drawer;
    }


    public void drawKaleidoscope(float x, float y, Paint paint){
        createInfinityBitmap();
        canvas.save();
        rotateAndDrawAroundAxis(x,y, paint);
        drawInfinityModeSegments(x,y);
        canvas.restore();
    }


    void createInfinityBitmap(){
        if(viewModel.isInfinityModeEnabled) {
            infinityImage = Bitmap.createScaledBitmap(paintView.getBitmap(), 500, 500, false);
        }
    }


    private void rotateAndDrawAroundAxis(float x, float y, Paint paint){
        canvas.translate(kaleidoscopeHelper.getCenterX(), kaleidoscopeHelper.getCenterY());
        for(float angle = 0; angle < kaleidoscopeHelper.getMaxDegrees(); angle += kaleidoscopeHelper.getDegreeIncrement()){
            canvas.save();
            canvas.rotate(angle);
            parentDrawer.drawKaleidoscopeSegment(x, y, paint);
            canvas.restore();
        }
    }


    void drawInfinityModeSegments(float x, float y){
        if(!viewModel.isInfinityModeEnabled){
            return;
        }
        BitmapShader bitmapShader = new BitmapShader(infinityImage, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        infinityPaint.setStyle(Paint.Style.FILL);
        infinityPaint.setShader(bitmapShader);
        for(float angle = 0; angle < kaleidoscopeHelper.getMaxDegrees(); angle += kaleidoscopeHelper.getDegreeIncrement()) {
            drawGlitchModeSegment(x, y, angle);
        }
    }


    private void drawGlitchModeSegment(float x, float y, float angle){
        canvas.save();
        canvas.rotate(angle);
        float gx = x - (kaleidoscopeHelper.getCenterX());
        float gy = y - (kaleidoscopeHelper.getCenterY());
        canvas.drawBitmap(infinityImage,gx, gy, infinityPaint);
        canvas.restore();
    }

}
