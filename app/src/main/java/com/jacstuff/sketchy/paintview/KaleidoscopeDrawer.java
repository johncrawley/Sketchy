package com.jacstuff.sketchy.paintview;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import com.jacstuff.sketchy.paintview.helpers.KaleidoscopeHelper;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class KaleidoscopeDrawer {

    private final Canvas canvas;
    private final MainViewModel viewModel;
    private final Paint glitchPaint;
    private final KaleidoscopeHelper kaleidoscopeHelper;
    private final PaintView paintView;
    private Bitmap glitchImage;

    KaleidoscopeDrawer(PaintView paintView, MainViewModel viewModel, KaleidoscopeHelper kaleidoscopeHelper){
        this.paintView = paintView;
        this.canvas = paintView.getCanvas();
        this.viewModel = viewModel;
        glitchPaint = new Paint();
        this.kaleidoscopeHelper = kaleidoscopeHelper;
    }


    void drawKaleidoscope(float x, float y, Paint paint){
        drawKaleidoscope(x,y, paint, false);
    }


    void drawKaleidoscope(float x, float y, Paint paint, boolean isDragLine){
        if(viewModel.isGlitchModeEnabled) {
            glitchImage = Bitmap.createScaledBitmap(paintView.getBitmap(), 500, 500, false);
        }
        canvas.save();
        canvas.translate(kaleidoscopeHelper.getCenterX(), kaleidoscopeHelper.getCenterY());

        for(float angle = 0; angle < kaleidoscopeHelper.getMaxDegrees(); angle += kaleidoscopeHelper.getDegreeIncrement()){
            drawKaleidoscopeSegment(x, y, angle, isDragLine, paint);
        }
        if(viewModel.isGlitchModeEnabled){
            drawGlitchSegments(x,y);
        }
        canvas.restore();
    }


    private void drawGlitchSegments(float x, float y){
        BitmapShader bitmapShader = new BitmapShader(glitchImage, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        glitchPaint.setStyle(Paint.Style.FILL);
        glitchPaint.setShader(bitmapShader);
        for(float angle = 0; angle < kaleidoscopeHelper.getMaxDegrees(); angle += kaleidoscopeHelper.getDegreeIncrement()) {
            drawGlitchModeSegment(x, y, angle);
        }
    }


    private void drawGlitchModeSegment(float x, float y, float angle){
        canvas.save();
        canvas.rotate(angle);
        float gx = x - (kaleidoscopeHelper.getCenterX());
        float gy = y - (kaleidoscopeHelper.getCenterY());
        canvas.drawBitmap(glitchImage ,gx, gy, glitchPaint);
        canvas.restore();
    }


    private void drawKaleidoscopeSegment(float x, float y, float angle, boolean isDragLine, Paint paint){
        canvas.save();
        canvas.rotate(angle);
        if(isDragLine){
            paintView.drawDragLine(x , y, kaleidoscopeHelper.getCenterX(), kaleidoscopeHelper.getCenterY());
        }
        else {
            paintView.rotateAndDraw(x - kaleidoscopeHelper.getCenterX(), y - kaleidoscopeHelper.getCenterY(), paint);
        }
        canvas.restore();
    }
}