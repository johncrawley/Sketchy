package com.jacstuff.sketchy.brushes.shapes.drawer;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.HashMap;
import java.util.Map;

public class DrawerFactory {

    public enum Type { BASIC, DRAG_LINE, DRAG_RECT, PATH}
    private final Map<Type, Drawer> drawerMap;


    public DrawerFactory(PaintView paintView, MainViewModel mainViewModel){
        drawerMap = new HashMap<>();
        drawerMap.put(Type.BASIC, new BasicDrawer( paintView, mainViewModel));
        drawerMap.put(Type.DRAG_LINE, new DragLineDrawer(paintView, mainViewModel));
        drawerMap.put(Type.DRAG_RECT, new DragRectDrawer(paintView, mainViewModel));
        drawerMap.put(Type.PATH, new PathDrawer(paintView, mainViewModel));
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
