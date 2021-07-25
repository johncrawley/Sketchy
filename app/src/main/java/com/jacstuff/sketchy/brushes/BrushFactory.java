package com.jacstuff.sketchy.brushes;

import android.graphics.Canvas;

import com.jacstuff.sketchy.brushes.shapes.ArcBrush;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.shapes.CircleBrush;
import com.jacstuff.sketchy.brushes.shapes.BananaBrush;
import com.jacstuff.sketchy.brushes.shapes.CrescentBrush;
import com.jacstuff.sketchy.brushes.shapes.HexagonBrush;
import com.jacstuff.sketchy.brushes.shapes.LineBrush;
import com.jacstuff.sketchy.brushes.shapes.OvalBrush;
import com.jacstuff.sketchy.brushes.shapes.PathBrush;
import com.jacstuff.sketchy.brushes.shapes.PentagonBrush;
import com.jacstuff.sketchy.brushes.shapes.RectangleBrush;
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
        add(circleBrush);
        add(new RoundedRectangleBrush(canvas, paintGroup, brushSize));
        add( new SquareBrush(canvas, paintGroup));
        add(new TriangleBrush(canvas, paintGroup));
        add(new PentagonBrush(canvas, paintGroup));
        add(new HexagonBrush(canvas, paintGroup));
        add(new StarBrush(canvas, paintGroup));
        add(new LineBrush(canvas, paintGroup));
        add(new StraightLineBrush(canvas, paintGroup));
        add(new WavyLineBrush(canvas, paintGroup));
        add(new ArcBrush(canvas, paintGroup));
        add(new TextBrush(canvas, paintGroup, viewModel));
        add(new OvalBrush(canvas, paintGroup));
        add(new CrescentBrush(canvas, paintGroup));
        add(new TextOnCircleBrush(canvas, paintGroup, viewModel));
        add(new PathBrush(canvas, paintGroup));
        add(new RectangleBrush(canvas, paintGroup));
    }


    private void add(Brush brush){
        brushMap.put(brush.getBrushShape(), brush);
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
