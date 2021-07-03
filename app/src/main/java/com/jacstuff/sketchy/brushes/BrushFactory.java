package com.jacstuff.sketchy.brushes;

import android.graphics.Canvas;

import com.jacstuff.sketchy.brushes.shapes.ArcBrush;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.shapes.CircleBrush;
import com.jacstuff.sketchy.brushes.shapes.HexagonBrush;
import com.jacstuff.sketchy.brushes.shapes.LineBrush;
import com.jacstuff.sketchy.brushes.shapes.PentagonBrush;
import com.jacstuff.sketchy.brushes.shapes.RoundedRectangleBrush;
import com.jacstuff.sketchy.brushes.shapes.SquareBrush;
import com.jacstuff.sketchy.brushes.shapes.StarBrush;
import com.jacstuff.sketchy.brushes.shapes.StraightLineBrush;
import com.jacstuff.sketchy.brushes.shapes.TextBrush;
import com.jacstuff.sketchy.brushes.shapes.TextOnCircleBrush;
import com.jacstuff.sketchy.brushes.shapes.TriangleBrush;
import com.jacstuff.sketchy.brushes.shapes.WavyLineBrush;
import com.jacstuff.sketchy.brushes.styles.DashedStyle;
import com.jacstuff.sketchy.brushes.styles.DashedStyleForLines;
import com.jacstuff.sketchy.brushes.styles.DoubleEdgeStyle;
import com.jacstuff.sketchy.brushes.styles.FillStyle;
import com.jacstuff.sketchy.brushes.styles.FillStyleForLines;
import com.jacstuff.sketchy.brushes.styles.JaggedStyle;
import com.jacstuff.sketchy.brushes.styles.OutlineStyle;
import com.jacstuff.sketchy.brushes.styles.SpikedStyle;
import com.jacstuff.sketchy.brushes.styles.TranslateStyle;
import com.jacstuff.sketchy.brushes.styles.WavyStyle;
import com.jacstuff.sketchy.paintview.PaintGroup;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.HashMap;
import java.util.Map;

public class BrushFactory {

    private Map<BrushShape, Brush> brushMap;
    private final Brush circleBrush;
    private final MainViewModel viewModel;

    public BrushFactory(Canvas canvas, PaintGroup paintGroup, int brushSize, MainViewModel viewModel){
        this.viewModel = viewModel;
        circleBrush = new CircleBrush(canvas, paintGroup);
        setupBrushMap(canvas, paintGroup, brushSize);
        handleStyles(brushSize, paintGroup);
        addLineBrushAndStyles(paintGroup, brushSize);
    }


    private void setupBrushMap(Canvas canvas, PaintGroup paintGroup, int brushSize){
        brushMap = new HashMap<>();
        brushMap.put(BrushShape.CIRCLE,            circleBrush);
        brushMap.put(BrushShape.ROUNDED_RECTANGLE,  new RoundedRectangleBrush(canvas, paintGroup, brushSize));
        brushMap.put(BrushShape.SQUARE,             new SquareBrush(canvas, paintGroup));
        brushMap.put(BrushShape.TRIANGLE,           new TriangleBrush(canvas, paintGroup));
        brushMap.put(BrushShape.PENTAGON,           new PentagonBrush(canvas, paintGroup));
        brushMap.put(BrushShape.HEXAGON,            new HexagonBrush(canvas, paintGroup));
        brushMap.put(BrushShape.STAR,               new StarBrush(canvas, paintGroup));
        brushMap.put(BrushShape.LINE,               new LineBrush(canvas, paintGroup));
        brushMap.put(BrushShape.STRAIGHT_LINE,      new StraightLineBrush(canvas, paintGroup));
        brushMap.put(BrushShape.WAVY_LINE,         new WavyLineBrush(canvas, paintGroup));
        brushMap.put(BrushShape.ARC,                new ArcBrush(canvas, paintGroup));
        brushMap.put(BrushShape.TEXT,               new TextBrush(canvas, paintGroup, viewModel));
        brushMap.put(BrushShape.TEXT_ON_CIRCLE,     new TextOnCircleBrush(canvas, paintGroup, viewModel));
    }


    private void handleStyles(int brushSize, PaintGroup paintGroup) {
        for (Brush brush : brushMap.values()) {
            brush.add(BrushStyle.FILL,              new FillStyle());
            brush.add(BrushStyle.OUTLINE,           new OutlineStyle());
            brush.add(BrushStyle.BROKEN_OUTLINE,    new DashedStyle(paintGroup));
            brush.add(BrushStyle.JAGGED,            new JaggedStyle(paintGroup));
            brush.add(BrushStyle.WAVY,              new WavyStyle(paintGroup));
            brush.add(BrushStyle.SPIKED,            new SpikedStyle(paintGroup));
            brush.add(BrushStyle.DOUBLE_EDGE,       new DoubleEdgeStyle(paintGroup));
            brush.add(BrushStyle.TRANSLATE,         new TranslateStyle(paintGroup));
            brush.setBrushSize(brushSize);
        }
    }


    private void addLineBrushAndStyles(PaintGroup paintGroup, int brushSize){
        Brush lineBrush = brushMap.get(BrushShape.LINE);
        if(lineBrush == null){
            return;
        }
        lineBrush.add(BrushStyle.FILL, new FillStyleForLines());
        lineBrush.add(BrushStyle.BROKEN_OUTLINE, new DashedStyleForLines(paintGroup, brushSize));
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
