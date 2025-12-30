package com.jacstuff.sketchy.brushes.shapes.threestep;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.AbstractBrush;
import com.jacstuff.sketchy.brushes.shapes.AbstractShape;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;
import com.jacstuff.sketchy.paintview.helpers.shadow.ShadowOffsetType;


public class LineBrush extends AbstractShape implements ThreeStepShape {

    private float xDown, yDown;

    public LineBrush() {
        super(BrushShape.LINE);
        drawerType = DrawerFactory.Type.DRAG_LINE;
        shadowOffsetType = ShadowOffsetType.USE_STROKE_WIDTH;
        usesBrushSizeControl = false;
    }

    @Override
    public void place(PointF p){

    }

    @Override
    public void adjust(PointF p){

    }


    @Override
    public void releaseAndDraw(PointF p, Canvas canvas, Paint paint){

    }

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


    public void onTouchMove(float x, float y, Paint paint) {
        //canvas.drawLine(xDown, yDown, x, y, paint);
    }


    public void onTouchUp(float x, float y, float offsetX, float offsetY, Paint paint) {
      //  mainActivity.getConnectedLineIconModifier().setConnectedIconAndState();
        /*
        canvas.drawLine(xDown - offsetX,
                yDown - offsetY,
                x - offsetX,
                y - offsetY,
                paint);

         */
       // viewModel.drawHistory.saveLineUpCoordinates(x,y);
    }


    public void onTouchUp(float x, float y, Paint paint) {
      //  canvas.drawLine(xDown, yDown, x, y, paint);
    }


    public boolean isUsingPlacementHelper(){
        return false;
    }


    public void reset(){
        //mainActivity.getConnectedLineIconModifier().resetIconAndState();
    }

    @Override
    public void draw(PointF point, Canvas canvas, Paint paint) {

    }
}