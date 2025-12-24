package com.jacstuff.sketchy.brushes;

import com.jacstuff.sketchy.brushes.shapes.ArcBrush;
import com.jacstuff.sketchy.brushes.shapes.BananaBrush;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.shapes.AstroidBrush;
import com.jacstuff.sketchy.brushes.shapes.Brushable;
import com.jacstuff.sketchy.brushes.shapes.CircleBrush;
import com.jacstuff.sketchy.brushes.shapes.SemicircleBrush;
import com.jacstuff.sketchy.brushes.shapes.SquareBrush;
import com.jacstuff.sketchy.brushes.shapes.TriangleBrush;
import com.jacstuff.sketchy.brushes.shapes.smoothpath.SmoothPathBrush;
import com.jacstuff.sketchy.brushes.shapes.VariableCircleBrush;
import com.jacstuff.sketchy.brushes.shapes.spirals.CrazySpiralBrush;
import com.jacstuff.sketchy.brushes.shapes.CrescentBrush;
import com.jacstuff.sketchy.brushes.shapes.RandomBrush;
import com.jacstuff.sketchy.brushes.shapes.spirals.SpiralBrush;
import com.jacstuff.sketchy.brushes.shapes.twostep.ArbitraryConnectedTriangleBrush;
import com.jacstuff.sketchy.brushes.shapes.twostep.CurvedLineBrush;
import com.jacstuff.sketchy.brushes.shapes.DiamondBrush;
import com.jacstuff.sketchy.brushes.shapes.HexagonBrush;
import com.jacstuff.sketchy.brushes.shapes.ParallelogramBrush;
import com.jacstuff.sketchy.brushes.shapes.PointedOvalBrush;
import com.jacstuff.sketchy.brushes.shapes.LineBrush;
import com.jacstuff.sketchy.brushes.shapes.OvalBrush;
import com.jacstuff.sketchy.brushes.shapes.PathBrush;
import com.jacstuff.sketchy.brushes.shapes.PentagonBrush;
import com.jacstuff.sketchy.brushes.shapes.RectangleBrush;
import com.jacstuff.sketchy.brushes.shapes.RoundedRectangleBrush;
import com.jacstuff.sketchy.brushes.shapes.StraightLineBrush;
import com.jacstuff.sketchy.brushes.shapes.TextBrush;
import com.jacstuff.sketchy.brushes.shapes.TextOnCircleBrush;
import com.jacstuff.sketchy.brushes.shapes.TrapezoidBrush;
import com.jacstuff.sketchy.brushes.shapes.WavyLineBrush;
import com.jacstuff.sketchy.brushes.shapes.XBrush;
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

    private void setupBrushShapeMap(){
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
        add(new AstroidBrush());
    }

    private void setupBrushMap(int maxDimension){
        brushMap = new HashMap<>();
        addBrush(bananaBrush);
        addBrush(new RoundedRectangleBrush());
       // add(new SquareBrush());
      //  add(new TriangleBrush());
       // add(new PentagonBrush());
       // addBrush(new HexagonBrush());
        addBrush(new LineBrush());
        addBrush(new CurvedLineBrush());
        addBrush(new StraightLineBrush(380, maxDimension));
        addBrush(new WavyLineBrush());
       // addBrush(new ArcBrush());
        addBrush(new SemicircleBrush());
        addBrush(new TextBrush());
        //addBrush(new OvalBrush());
        addBrush(new CrescentBrush());
        addBrush(new TextOnCircleBrush());
        addBrush(new BananaBrush());
        addBrush(new PathBrush());
        addBrush(new SmoothPathBrush());
        addBrush(new RectangleBrush());
        addBrush(new PointedOvalBrush());
        addBrush(new TrapezoidBrush());
        addBrush(new ParallelogramBrush());
        addBrush(new ArbitraryConnectedTriangleBrush());
        addBrush(new RandomBrush());
        addBrush(new SpiralBrush());
        addBrush(new CrazySpiralBrush());
        addBrush(new VariableCircleBrush());
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
