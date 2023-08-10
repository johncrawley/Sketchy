package com.jacstuff.sketchy.paintview;

import static com.jacstuff.sketchy.paintview.helpers.PaintFactory.createPaint;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PaintGroup {


    private final List<Paint> paints;
    private Paint previewPaint, drawPaint, shadowPaint, blankPaint, canvasBitmapPaint;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;

    public PaintGroup(){
        paints = new ArrayList<>();
        initPaints();
        paints.add(drawPaint);
        paints.add(previewPaint);
        paints.add(shadowPaint);
    }


    public void setStyle(Paint.Style style){
        for(Paint p : paints){
            p.setStyle(style);
        }
    }


    private void initPaints(){
        drawPaint = createPaint(Color.WHITE);
        previewPaint = createPaint(Color.DKGRAY);
        shadowPaint = createPaint(Color.BLACK);
        initCanvasBitmapPaint();
        initBlankPaint();
    }


    private void initCanvasBitmapPaint(){
        canvasBitmapPaint = new Paint();
        canvasBitmapPaint.setAntiAlias(true);
        canvasBitmapPaint.setDither(true);
    }


    private void initBlankPaint(){
        blankPaint = new Paint();
        blankPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        blankPaint.setColor(DEFAULT_BG_COLOR);
    }


    public Paint getDrawPaint(){
        return drawPaint;
    }


    public Paint getPreviewPaint(){
        return previewPaint;
    }


    public Paint getShadowPaint(){
        return shadowPaint;
    }


    public Paint getBlankPaint(){
        return blankPaint;
    }


    public Paint getCanvasBitmapPaint(){
        return canvasBitmapPaint;
    }


    public float getLineWidth(){
       return paints.get(0).getStrokeWidth();
    }


    public String getStyle(){
        return paints.get(0).getStyle().toString();
    }


    public void setPathEffect(PathEffect pathEffect){
        for(Paint p: paints){
            p.setPathEffect(pathEffect);
        }
    }

    public void setTypeface(Typeface typeface){
        for(Paint p: paints){
            p.setTypeface(typeface);
        }
    }


    public void setTextSkewX(float value){
        doActionOnPaints(p -> p.setTextSkewX(value));
    }


    private void doActionOnPaints(Consumer<Paint> consumer){
        for(Paint p: paints) {
            consumer.accept(p);
        }
    }


    public void setLetterSpacing(float value){
        for(Paint p: paints){
            p.setLetterSpacing(value);
        }
    }

    public void setStrokeWidth(float strokeWidth){
        for(Paint p: paints){
            p.setStrokeWidth(strokeWidth);
        }
    }
    public void setTextSize(float textSize){
        for(Paint p: paints){
            p.setTextSize(textSize);
        }
    }

    public void setTextBold(boolean b){
        for(Paint p : paints){
            p.setFakeBoldText(b);
        }
    }


    public void setStrikeThrough(boolean b){
        for(Paint p : paints){
            p.setStrikeThruText(b);
        }
    }


    public void setStrokeCap(Paint.Cap cap){
        for(Paint p : paints){
            p.setStrokeCap(cap);
        }
    }


    public void setStrokeJoin(Paint.Join join){
        for(Paint p : paints){
            p.setStrokeJoin(join);
        }
    }


    public void setTextUnderline(boolean b){
        for(Paint p : paints){
            p.setUnderlineText(b);
        }
    }



}
