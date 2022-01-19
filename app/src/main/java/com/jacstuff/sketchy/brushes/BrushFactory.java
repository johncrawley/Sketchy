package com.jacstuff.sketchy.brushes;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.brushes.shapes.ArcBrush;
import com.jacstuff.sketchy.brushes.shapes.BananaBrush;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.shapes.CircleBrush;
import com.jacstuff.sketchy.brushes.shapes.AstroidBrush;
import com.jacstuff.sketchy.brushes.shapes.CrescentBrush;
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
import com.jacstuff.sketchy.brushes.shapes.SquareBrush;
import com.jacstuff.sketchy.brushes.shapes.StarBrush;
import com.jacstuff.sketchy.brushes.shapes.StraightLineBrush;
import com.jacstuff.sketchy.brushes.shapes.TextBrush;
import com.jacstuff.sketchy.brushes.shapes.TextOnCircleBrush;
import com.jacstuff.sketchy.brushes.shapes.TrapezoidBrush;
import com.jacstuff.sketchy.brushes.shapes.twostep.ArbitraryTriangleBrush;
import com.jacstuff.sketchy.brushes.shapes.TriangleBrush;
import com.jacstuff.sketchy.brushes.shapes.WavyLineBrush;
import com.jacstuff.sketchy.brushes.shapes.XBrush;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.StyleHelper;
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
    private StyleHelper styleHelper;

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
        styleHelper = paintView.getPaintHelperManager().getStyleHelper();
        setupBrushMap();
        handleStyles(brushSize);
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
        add(new CurvedLineBrush());
        add(new StraightLineBrush());
        add(new WavyLineBrush());
        add(new ArcBrush());
        add(new TextBrush());
        add(new OvalBrush());
        add(new CrescentBrush());
        add(new TextOnCircleBrush());
        add(new BananaBrush());
        add(new PathBrush());
        add(new RectangleBrush());
        add(new XBrush());
        add(new DiamondBrush());
        add(new AstroidBrush());
        add(new PointedOvalBrush());
        add(new TrapezoidBrush());
        add(new ParallelogramBrush());
        add(new ArbitraryTriangleBrush());
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


    private void handleStyles(int brushSize) {
        for (Brush brush : brushMap.values()) {
            brush.setBrushSize(brushSize);
        }
    }


    public Brush getReinitializedBrushFor(BrushShape shape){
        Brush brush =  brushMap.get(shape);
        if(brush == null){
            brush = circleBrush;
        }
        brush.setStyle(styleHelper.getCurrentStyle());
        brush.reinitialize();
        return brush;
    }

}
