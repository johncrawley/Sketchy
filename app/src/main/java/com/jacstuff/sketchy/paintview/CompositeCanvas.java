package com.jacstuff.sketchy.paintview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;

public class CompositeCanvas extends Canvas {

    private Canvas previewCanvas;
    private Paint previewPaint;

    public CompositeCanvas(Canvas previewCanvas, Paint previewPaint){
        super();
        this.previewCanvas = previewCanvas;
        this.previewPaint = previewPaint;
    }

    public void setPreviewCanvas(Canvas previewCanvas){
        this.previewCanvas = previewCanvas;
    }

    @Override
    public void drawPath(Path path, @NonNull Paint paint){
        super.drawPath(path, paint);
        previewCanvas.drawPath(path, previewPaint);
    }


    @Override
    public void drawCircle(float x, float y, float radius, @NonNull Paint paint){
        super.drawCircle(x,y, radius, paint);
        previewCanvas.drawCircle(x,y, radius, previewPaint);
    }


    @Override
    public void drawLine(float x1, float y1, float x2,  float y2, @NonNull Paint paint){
        super.drawLine(x1, y1, x2, y2, paint);
        previewCanvas.drawLine(x1, y1, x2, y2, previewPaint);
    }


    public void drawPreviewLine(float x1, float y1, float x2,  float y2, @NonNull Paint paint){
        previewCanvas.drawLine(x1, y1, x2, y2, previewPaint);
    }

    @Override
    public void drawRoundRect(@NonNull RectF rect, float rx, float ry, @NonNull Paint paint){
        super.drawRoundRect(rect, rx, ry, paint);
        previewCanvas.drawRoundRect(rect, rx, ry, previewPaint);
    }

    @Override
    public void drawRect(float left, float top, float right, float bottom, Paint paint){
        super.drawRect(left, top, right, bottom, paint);
        previewCanvas.drawRect(left, top, right, bottom, previewPaint);
    }



}
