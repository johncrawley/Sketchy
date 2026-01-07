package com.jacstuff.sketchy.brushes;

import com.jacstuff.sketchy.brushes.shapes.onestep.ArcBrush;
import com.jacstuff.sketchy.brushes.shapes.BananaBrush;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.shapes.onestep.pathshape.AstroidBrush;
import com.jacstuff.sketchy.brushes.shapes.Brushable;
import com.jacstuff.sketchy.brushes.shapes.onestep.CircleBrush;
import com.jacstuff.sketchy.brushes.shapes.onestep.pathshape.SemicircleBrush;
import com.jacstuff.sketchy.brushes.shapes.onestep.SquareBrush;
import com.jacstuff.sketchy.brushes.shapes.onestep.pathshape.TriangleBrush;
import com.jacstuff.sketchy.brushes.shapes.smoothpath.SmoothPathBrush;
import com.jacstuff.sketchy.brushes.shapes.onestep.pathshape.spirals.CrazySpiralBrush;
import com.jacstuff.sketchy.brushes.shapes.onestep.pathshape.CrescentBrush;
import com.jacstuff.sketchy.brushes.shapes.onestep.RandomBrush;
import com.jacstuff.sketchy.brushes.shapes.onestep.pathshape.spirals.SpiralBrush;
import com.jacstuff.sketchy.brushes.shapes.twostep.ArbitraryConnectedTriangleBrush;
import com.jacstuff.sketchy.brushes.shapes.twostep.CurvedLineBrush;
import com.jacstuff.sketchy.brushes.shapes.onestep.pathshape.DiamondBrush;
import com.jacstuff.sketchy.brushes.shapes.onestep.pathshape.HexagonBrush;
import com.jacstuff.sketchy.brushes.shapes.onestep.pathshape.ParallelogramBrush;
import com.jacstuff.sketchy.brushes.shapes.onestep.pathshape.PointedOvalBrush;
import com.jacstuff.sketchy.brushes.shapes.onestep.OvalBrush;
import com.jacstuff.sketchy.brushes.shapes.PathBrush;
import com.jacstuff.sketchy.brushes.shapes.onestep.pathshape.PentagonBrush;
import com.jacstuff.sketchy.brushes.shapes.threestep.VariableRectangleBrush;
import com.jacstuff.sketchy.brushes.shapes.onestep.RoundedRectangleBrush;
import com.jacstuff.sketchy.brushes.shapes.onestep.StraightLineBrush;
import com.jacstuff.sketchy.brushes.shapes.onestep.TextBrush;
import com.jacstuff.sketchy.brushes.shapes.onestep.pathshape.TrapezoidBrush;
import com.jacstuff.sketchy.brushes.shapes.onestep.pathshape.WavyLineBrush;
import com.jacstuff.sketchy.brushes.shapes.onestep.pathshape.XBrush;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.StyleHelper;

import java.util.HashMap;
import java.util.Map;

public class BrushFactory {

    private Map<BrushShape, Brush> brushMap;
    private Map<BrushShape, Brushable> brushShapeMap;
    private final Brush bananaBrush;
    private DrawerFactory drawerFactory;
    private PathBrush shadowPathBrush;
    private StyleHelper styleHelper;
    private Brushable currentBrushShape;


    public BrushFactory(){
        bananaBrush = new BananaBrush();
    }


    public void init(PaintView paintView, int brushSize, int maxDimension){
        drawerFactory = new DrawerFactory(paintView);
        initShadowPathBrush();
        drawerFactory.init();
        styleHelper = paintView.getPaintHelperManager().getStyleHelper();
        setupBrushMap(maxDimension);
        handleStyles(brushSize);
    }


    public Brush getBrushFor(BrushShape shape){
        var brush = brushMap.getOrDefault(shape, bananaBrush);
        if(brush != null){
            brush.setStyle(styleHelper.getCurrentStyle());
            brush.reinitialize();
        }
        return brush;
    }


    private void log(String msg){
        System.out.println("^^^ BrushFactory: " + msg);
    }


    public PathBrush getShadowPathBrush(){
        return shadowPathBrush;
    }


    private void setupBrushShapeMap(int maxScreenDimension){
        brushShapeMap = new HashMap<>(40);
        add(new TriangleBrush());
        add(new SquareBrush());
        add(new CircleBrush());
        add(new PentagonBrush());
        add(new HexagonBrush());
        add(new ArcBrush());
        add(new OvalBrush());
        add(new XBrush());
        add(new DiamondBrush());
        add(new RoundedRectangleBrush());
        add(new AstroidBrush());
        add(new PointedOvalBrush());
        add(new CrescentBrush());
        add(new TrapezoidBrush());
        add(new ParallelogramBrush());
        add(new StraightLineBrush(380, maxScreenDimension));
        add(new WavyLineBrush());
        add(new SemicircleBrush());
        add(new TextBrush());
        add(new SpiralBrush());
        add(new CrazySpiralBrush());

        addBrush(new RandomBrush());
    }


    private void setupBrushMap(int maxDimension){
        brushMap = new HashMap<>();
        //addBrush(new LineBrush());
        //addBrush(new VariableCircleBrush());
        addBrush(new CurvedLineBrush());
        addBrush(new PathBrush());
        addBrush(new SmoothPathBrush());
       // addBrush(new VariableRectangleBrush());
        addBrush(new ArbitraryConnectedTriangleBrush());
    }


    private void initShadowPathBrush(){
        shadowPathBrush = new PathBrush();
        shadowPathBrush.init(drawerFactory);
    }



    private void addBrush(Brush brush){
        brush.init(drawerFactory);
        brushMap.put(brush.getBrushShape(), brush);
    }



    private void add(Brushable brushable){
        brushShapeMap.put(brushable.getBrushShape(), brushable);
    }


    private void handleStyles(int brushSize) {
        for (Brush brush : brushMap.values()) {
            brush.setBrushSize(brushSize);
        }
    }


}
