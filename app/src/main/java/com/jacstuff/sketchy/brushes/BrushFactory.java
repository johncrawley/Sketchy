package com.jacstuff.sketchy.brushes;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.jacstuff.sketchy.BrushShape;
import com.jacstuff.sketchy.BrushStyle;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class BrushFactory {

    private Map<BrushShape, Brush> brushMap;
    private CircleBrush circleBrush;
    private SquareBrush squareBrush;
    private LineBrush lineBrush;

    public BrushFactory(Canvas canvas, Paint paint, int brushSize){
        initBrushes(canvas, paint, brushSize);

        brushMap = new HashMap<>();
        brushMap.put(BrushShape.CIRCLE, circleBrush);
        brushMap.put(BrushShape.SQUARE, squareBrush);
        brushMap.put(BrushShape.LINE, lineBrush);
    }

    private void initBrushes(Canvas canvas, Paint paint, int brushSize){

        BiConsumer<Paint, Integer> fillStyle = (p, i)-> {
            p.setStyle(Paint.Style.FILL_AND_STROKE);
            p.setPathEffect(null);
        };


        BiConsumer<Paint, Integer> outlineStyle = (p,i)-> {
            paint.setStrokeWidth(1);
            paint.setStyle(Paint.Style.STROKE);
            paint.setPathEffect(null);
        };


        BiConsumer<Paint, Integer> lineOutlineStyle = (p,i)-> {
            paint.setStrokeWidth(1);
            paint.setStyle(Paint.Style.STROKE);
            paint.setPathEffect(null);
        };


        BiConsumer<Paint, Integer> brokenOutlineStyle = (p, i) -> {
            paint.setStrokeWidth(1);
            p.setStyle(Paint.Style.STROKE);
            float onStroke = 15;
            float offStroke = 36;
            p.setPathEffect(new DashPathEffect(new float[] {onStroke, offStroke}, 0));
        };


        BiConsumer<Paint, Integer> brokenLineStyle = (p, i) -> {
            int halfBrushSize = i /2;
            paint.setStrokeWidth(halfBrushSize);
            paint.setStyle(Paint.Style.STROKE);

            float onStroke =  20 + (halfBrushSize /8f);
            float offStroke = 60 + (halfBrushSize );

            paint.setPathEffect(new DashPathEffect(new float[] {onStroke, offStroke}, 0));
        };


        circleBrush = new CircleBrush(canvas, paint, brushSize);
        circleBrush.add(BrushStyle.FILL, fillStyle);
        circleBrush.add(BrushStyle.OUTLINE, outlineStyle);
        circleBrush.add(BrushStyle.BROKEN_OUTLINE, brokenOutlineStyle);

        squareBrush = new SquareBrush(canvas, paint, brushSize);
        squareBrush.add(BrushStyle.FILL, fillStyle);
        squareBrush.add(BrushStyle.OUTLINE, outlineStyle);
        squareBrush.add(BrushStyle.BROKEN_OUTLINE, brokenOutlineStyle);


        lineBrush = new LineBrush(canvas, paint, brushSize);
        lineBrush.add(BrushStyle.FILL, fillStyle);
        lineBrush.add(BrushStyle.OUTLINE, lineOutlineStyle);
        lineBrush.add(BrushStyle.BROKEN_OUTLINE, brokenLineStyle);
    }

    public Brush getResettedBrushFor(BrushShape shape, BrushStyle brushStyle, int brushSize){
        Brush brush =  brushMap.get(shape);
        brush.reset(brushSize);
        brush.setStyle(brushStyle);
        return brush;
    }


}
