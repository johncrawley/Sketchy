package com.jacstuff.sketchy.brushes.shapes.drawer;

import static com.jacstuff.sketchy.brushes.shapes.drawer.DrawerType.BASIC;
import static com.jacstuff.sketchy.brushes.shapes.drawer.DrawerType.DRAG_LINE;
import static com.jacstuff.sketchy.brushes.shapes.drawer.DrawerType.DRAG_RECT;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.HashMap;
import java.util.Map;

public class DrawerFactory {

    private final Map<DrawerType, Drawer> drawerMap;


    public DrawerFactory(PaintView paintView){
        drawerMap = new HashMap<>();
        drawerMap.put(BASIC, new BasicDrawer(paintView));
        drawerMap.put(DRAG_LINE, new DragLineDrawer(paintView));
        drawerMap.put(DRAG_RECT, new DragRectDrawer(paintView));
       // drawerMap.put(Type.PATH, new PathDrawer(paintView));
       // drawerMap.put(Type.SMOOTH_PATH, new SmoothPathDrawer(paintView));
    }

    public void init(){
        for(Drawer drawer: drawerMap.values()){
            drawer.init();
            drawer.initExtra();
        }
    }



    public Drawer get(DrawerType type){
        return drawerMap.get(type);
    }

}
