package com.jacstuff.sketchy.brushes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.shapes.CircleBrush;
import com.jacstuff.sketchy.brushes.shapes.LineBrush;
import com.jacstuff.sketchy.brushes.shapes.RoundedRectangleBrush;
import com.jacstuff.sketchy.brushes.shapes.SquareBrush;
import com.jacstuff.sketchy.brushes.styles.DashedStyle;
import com.jacstuff.sketchy.brushes.styles.DashedStyleForLines;
import com.jacstuff.sketchy.brushes.styles.FillStyle;
import com.jacstuff.sketchy.brushes.styles.FillStyleForLines;
import com.jacstuff.sketchy.brushes.styles.OutlineStyle;
import com.jacstuff.sketchy.brushes.styles.Style;

import java.util.HashMap;
import java.util.Map;

public class BrushFactory {

    private Map<BrushShape, Brush> brushMap;
    private CircleBrush circleBrush;
    private SquareBrush squareBrush;
    private LineBrush lineBrush;
    private RoundedRectangleBrush roundedRectangleBrush;

    public BrushFactory(Canvas canvas, Paint paint, int brushSize){
        initBrushes(canvas, paint, brushSize);
        brushMap = new HashMap<>();
        brushMap.put(BrushShape.CIRCLE, circleBrush);
        brushMap.put(BrushShape.ROUNDED_RECTANGLE, roundedRectangleBrush);
        brushMap.put(BrushShape.SQUARE, squareBrush);
        brushMap.put(BrushShape.LINE, lineBrush);
    }

    private void initBrushes(Canvas canvas, Paint paint, int brushSize){

        Style fillStyle = new FillStyle();
        Style dashedStyle = new DashedStyle();
        Style outlineStyle = new OutlineStyle();

        circleBrush = new CircleBrush(canvas, paint);
        circleBrush.add(BrushStyle.FILL, fillStyle);
        circleBrush.add(BrushStyle.OUTLINE, outlineStyle);
        circleBrush.add(BrushStyle.BROKEN_OUTLINE, dashedStyle);
        circleBrush.setBrushSize(brushSize);

        squareBrush = new SquareBrush(canvas, paint);
        squareBrush.add(BrushStyle.FILL, fillStyle);
        squareBrush.add(BrushStyle.OUTLINE, outlineStyle);
        squareBrush.add(BrushStyle.BROKEN_OUTLINE, dashedStyle);
        squareBrush.setBrushSize(brushSize);

        roundedRectangleBrush = new RoundedRectangleBrush(canvas, paint, brushSize);
        roundedRectangleBrush.add(BrushStyle.FILL, fillStyle);
        roundedRectangleBrush.add(BrushStyle.OUTLINE, outlineStyle);
        roundedRectangleBrush.add(BrushStyle.BROKEN_OUTLINE, dashedStyle);
        roundedRectangleBrush.setBrushSize(brushSize);

        lineBrush = new LineBrush(canvas, paint);
        lineBrush.add(BrushStyle.FILL, new FillStyleForLines());
        lineBrush.add(BrushStyle.OUTLINE, outlineStyle);
        lineBrush.add(BrushStyle.BROKEN_OUTLINE, new DashedStyleForLines(brushSize));
        lineBrush.setBrushSize(brushSize);
    }

    public Brush getResettedBrushFor(BrushShape shape, BrushStyle brushStyle){
        Brush brush =  brushMap.get(shape);
        brush.setStyle(brushStyle);
        return brush;
    }


}
