package com.jacstuff.sketchy.brushes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.shapes.CircleBrush;
import com.jacstuff.sketchy.brushes.shapes.HexagonBrush;
import com.jacstuff.sketchy.brushes.shapes.LineBrush;
import com.jacstuff.sketchy.brushes.shapes.PentagonBrush;
import com.jacstuff.sketchy.brushes.shapes.RoundedRectangleBrush;
import com.jacstuff.sketchy.brushes.shapes.SquareBrush;
import com.jacstuff.sketchy.brushes.shapes.StarBrush;
import com.jacstuff.sketchy.brushes.shapes.StraightLineBrush;
import com.jacstuff.sketchy.brushes.shapes.TriangleBrush;
import com.jacstuff.sketchy.brushes.styles.DashedStyle;
import com.jacstuff.sketchy.brushes.styles.DashedStyleForLines;
import com.jacstuff.sketchy.brushes.styles.FillStyle;
import com.jacstuff.sketchy.brushes.styles.FillStyleForLines;
import com.jacstuff.sketchy.brushes.styles.OutlineStyle;
import com.jacstuff.sketchy.brushes.styles.Style;
import com.jacstuff.sketchy.brushes.styles.ThickOutlineStyle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BrushFactory {

    private Map<BrushShape, Brush> brushMap;
    private Brush circleBrush, squareBrush, lineBrush,
            roundedRectangleBrush, triangleBrush, hexagonBrush,
            pentagonBrush, starBrush, straightLineBrush;


    public BrushFactory(Canvas canvas, Paint paint, int brushSize){
        initBrushes(canvas, paint, brushSize);
        brushMap = new HashMap<>();
        brushMap.put(BrushShape.CIRCLE, circleBrush);
        brushMap.put(BrushShape.ROUNDED_RECTANGLE, roundedRectangleBrush);
        brushMap.put(BrushShape.SQUARE, squareBrush);
        brushMap.put(BrushShape.LINE, lineBrush);
        brushMap.put(BrushShape.TRIANGLE, triangleBrush);
        brushMap.put(BrushShape.PENTAGON, pentagonBrush);
        brushMap.put(BrushShape.HEXAGON, hexagonBrush);
        brushMap.put(BrushShape.STAR, starBrush);
        brushMap.put(BrushShape.STRAIGHT_LINE, straightLineBrush);
    }

    private void initBrushes(Canvas canvas, Paint paint, int brushSize){

        Style fillStyle = new FillStyle();
        Style dashedStyle = new DashedStyle();
        Style outlineStyle = new OutlineStyle();
        Style thickOutlineStyle = new ThickOutlineStyle();

        circleBrush = new CircleBrush(canvas, paint);
        squareBrush = new SquareBrush(canvas, paint);
        roundedRectangleBrush = new RoundedRectangleBrush(canvas, paint, brushSize);
        triangleBrush = new TriangleBrush(canvas, paint);
        hexagonBrush = new HexagonBrush(canvas, paint);
        pentagonBrush = new PentagonBrush(canvas, paint);
        starBrush = new StarBrush(canvas, paint);
        straightLineBrush = new StraightLineBrush(canvas, paint);

        for(Brush brush : Arrays.asList(squareBrush, circleBrush, triangleBrush,
                roundedRectangleBrush, hexagonBrush, pentagonBrush, starBrush, straightLineBrush)){
            brush.add(BrushStyle.FILL, fillStyle);
            brush.add(BrushStyle.OUTLINE, outlineStyle);
            brush.add(BrushStyle.BROKEN_OUTLINE, dashedStyle);
            brush.add(BrushStyle.THICK_OUTLINE, thickOutlineStyle);
            brush.setBrushSize(brushSize);
        }


        lineBrush = new LineBrush(canvas, paint);
        lineBrush.add(BrushStyle.FILL, new FillStyleForLines());
        lineBrush.add(BrushStyle.OUTLINE, outlineStyle);
        lineBrush.add(BrushStyle.BROKEN_OUTLINE, new DashedStyleForLines(brushSize));
        lineBrush.add(BrushStyle.THICK_OUTLINE, new ThickOutlineStyle());
        lineBrush.setBrushSize(brushSize);
    }


    public Brush getResettedBrushFor(BrushShape shape, BrushStyle brushStyle){
        Brush brush =  brushMap.get(shape);
        if(brush == null){
            brush = circleBrush;
        }
        brush.setStyle(brushStyle);
        return brush;
    }


}
