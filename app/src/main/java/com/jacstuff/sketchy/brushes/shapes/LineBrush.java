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


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        if(!mainViewModel.connectedLineState.hasFirstItemBeenDrawn) {
            xDown = p.x;
            yDown = p.y;
        }
        else if(mainViewModel.connectedLineState.isConnectedModeEnabled){
            xDown = mainViewModel.nextLineDownX;
            yDown = mainViewModel.nextLineDownY;
        }
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint) {
        canvas.drawLine(xDown, yDown, x, y, paint);
    }


    @Override
    public void onTouchUp(float x, float y, float offsetX, float offsetY, Paint paint) {
        mainActivity.getConnectLineIconModifier().setConnectedIconAndState();
        canvas.drawLine(xDown - offsetX,
                yDown - offsetY,
                x - offsetX,
                y - offsetY,
                paint);
        mainViewModel.nextLineDownX = x;
        mainViewModel.nextLineDownY = y;
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
        mainActivity.getConnectLineIconModifier().resetIconAndState();
    }
}