package com.jacstuff.sketchy.brushes.shapes.drawer;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.HashMap;
import java.util.Map;

public class DrawerFactory {

    public enum Type { BASIC, DRAG_LINE, DRAG_RECT, PATH, SMOOTH_PATH, CURVE}
    private final Map<Type, Drawer> drawerMap;


    public DrawerFactory(PaintView paintView){
        drawerMap = new HashMap<>();
        drawerMap.put(Type.BASIC, new BasicDrawer( paintView));
        drawerMap.put(Type.DRAG_LINE, new DragLineDrawer(paintView));
        drawerMap.put(Type.DRAG_RECT, new DragRectDrawer(paintView));
       // drawerMap.put(Type.PATH, new PathDrawer(paintView));
       // drawerMap.put(Type.SMOOTH_PATH, new SmoothPathDrawer(paintView));
    }

    public void init(){
        for(Drawer drawer: drawerMap.values()){
            drawer.init();
            drawer.initExtra();
        }
    }



    public Drawer get(Type type){
        return drawerMap.get(type);
    }

}
