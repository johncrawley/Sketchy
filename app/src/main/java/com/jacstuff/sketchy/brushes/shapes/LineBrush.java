package com.jacstuff.sketchy.brushes.shapes;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;
import com.jacstuff.sketchy.brushes.shapes.initializer.LineInitializer;


public class LineBrush extends AbstractBrush implements Brush {

    private float xDown, yDown;

    public LineBrush() {
        super(BrushShape.LINE);
        brushInitializer = new LineInitializer();
        drawerType = DrawerFactory.Type.DRAG_LINE;
    }

    private void log(String msg){
        System.out.println("^^^ LineBrush: " + msg);
    }

    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){

        log("entered onBrushTouchDown() history size: " + viewModel.drawHistory.size());
        var historyItem = viewModel.drawHistory.getCurrent();
        if(historyItem != null){
            log("onBrushTouchDown, current Item ID: "+  historyItem.getId());
            var lineState = historyItem.getConnectedLineState();
            if(!lineState.isFirstItemDrawn()){
                log("onBrushTouchDown() current history: first item is not drawn");
                xDown = p.x;
                yDown = p.y;
            }
            else if(lineState.isConnectedModeEnabled()){
                var coordinates = historyItem.getLineUpCoordinates();
                xDown = coordinates.x;
                yDown = coordinates.y;
            }
            else{
                log("onBrushTouchDown() default else");
            }
        }
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint) {
        canvas.drawLine(xDown, yDown, x, y, paint);
    }


    @Override
    public void onTouchUp(float x, float y, float offsetX, float offsetY, Paint paint) {
        mainActivity.getConnectedLineIconModifier().setConnectedIconAndState();
        canvas.drawLine(xDown - offsetX,
                yDown - offsetY,
                x - offsetX,
                y - offsetY,
                paint);
        viewModel.drawHistory.saveLineUpCoordinates(x,y);
    }


    @Override
    public void onTouchUp(float x, float y, Paint paint) {
        canvas.drawLine(xDown, yDown, x, y, paint);
    }


    @Override
    public boolean isUsingPlacementHelper(){
        return false;
    }


    @Override
    public void reset(){
        mainActivity.getConnectedLineIconModifier().resetIconAndState();
    }
}