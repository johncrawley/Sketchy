package com.jacstuff.sketchy.brushes;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.brushes.shapes.ArcBrush;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.shapes.CircleBrush;
import com.jacstuff.sketchy.brushes.shapes.CrescentBrush;
import com.jacstuff.sketchy.brushes.shapes.DiamondBrush;
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
import com.jacstuff.sketchy.brushes.shapes.XBrush;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;
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
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.HashMap;
import java.util.Map;

public class BrushFactory {

    private Map<BrushShape, Brush> brushMap;
    private Brush circleBrush;
    private PaintView paintView;
    private final MainViewModel mainViewModel;
    private DrawerFactory drawerFactory;
    private final MainActivity mainActivity;
    private PathBrush shadowPathBrush;

    public BrushFactory(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        this.mainViewModel = mainActivity.getViewModel();
    }


    public void init(PaintView paintView, int brushSize){
        this.paintView = paintView;
        drawerFactory = new DrawerFactory(paintView, mainViewModel);
        initShadowPathBrush();
        drawerFactory.init();
        circleBrush = new CircleBrush();
        PaintGroup paintGroup = paintView.getPaintGroup();
        setupBrushMap();
        handleStyles(brushSize, paintGroup);
        addLineBrushAndStyles(paintGroup, brushSize);
    }


    private void setupBrushMap(){
        brushMap = new HashMap<>();
        add(circleBrush);
        add(new RoundedRectangleBrush());
        add( new SquareBrush());
        add(new TriangleBrush());
        add(new PentagonBrush());
        add(new HexagonBrush());
        add(new StarBrush());
        add(new LineBrush());
        add(new StraightLineBrush());
        add(new WavyLineBrush());
        add(new ArcBrush());
        add(new TextBrush());
        add(new OvalBrush());
        add(new CrescentBrush());
        add(new TextOnCircleBrush());
        add(new PathBrush());
        add(new RectangleBrush());
        add(new XBrush());
        add(new DiamondBrush());
    }


    private void initShadowPathBrush(){
        shadowPathBrush = new PathBrush();
        shadowPathBrush.init(paintView, mainActivity, drawerFactory);
    }


    public PathBrush getShadowPathBrush(){
        return shadowPathBrush;
    }


    private void add(Brush brush){
        brush.init(paintView, mainActivity, drawerFactory);
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
        brush.reinitialize();
        return brush;
    }

}
