package com.jacstuff.sketchy.brushes.shapes;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;
import com.jacstuff.sketchy.paintview.helpers.shadow.ShadowOffsetType;


public class LineBrush extends AbstractBrush implements Brush {

    private float xDown, yDown;

    public LineBrush() {
        super(BrushShape.LINE);
        drawerType = DrawerFactory.Type.DRAG_LINE;
        shadowOffsetType = ShadowOffsetType.USE_STROKE_WIDTH;
        usesBrushSizeControl = false;
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        xDown = p.x;
        yDown = p.y;
        /*
        var lineState = viewModel.drawHistory.getLineState();
        if(!lineState.isFirstItemDrawn()){
            xDown = p.x;
            yDown = p.y;
        }
        else if(lineState.isConnectedModeEnabled()){
            var coordinates = viewModel.drawHistory.getLineUpCoordinates();
            xDown = coordinates.x;
            yDown = coordinates.y;
        }

         */
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint) {
        canvas.drawLine(xDown, yDown, x, y, paint);
    }


    @Override
    public void onTouchUp(float x, float y, float offsetX, float offsetY, Paint paint) {
      //  mainActivity.getConnectedLineIconModifier().setConnectedIconAndState();
        canvas.drawLine(xDown - offsetX,
                yDown - offsetY,
                x - offsetX,
                y - offsetY,
                paint);
       // viewModel.drawHistory.saveLineUpCoordinates(x,y);
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
        //mainActivity.getConnectedLineIconModifier().resetIconAndState();
    }
}