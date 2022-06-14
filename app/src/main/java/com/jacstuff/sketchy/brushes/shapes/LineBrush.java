package com.jacstuff.sketchy.brushes.shapes;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.widget.Button;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;
import com.jacstuff.sketchy.brushes.shapes.initializer.LineInitializer;


public class LineBrush extends AbstractBrush implements Brush {

    private float xDown, yDown;
    private Button shapeButton;

    public LineBrush() {
        super(BrushShape.LINE);
        brushInitializer = new LineInitializer();
        drawerType = DrawerFactory.Type.DRAG_LINE;
    }

    @Override
    public void postInit(){
        shapeButton = mainActivity.findViewById(R.id.shapeButton);
    }

    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        if(mainViewModel.doesRandomBrushMorph)
        if(!mainViewModel.hasFirstLineBeenDrawn) {
            xDown = p.x;
            yDown = p.y;
        }
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint) {
        canvas.drawLine(xDown, yDown, x, y, paint);
    }


    @Override
    public void onTouchUp(float x, float y, float offsetX, float offsetY, Paint paint) {
        if(mainViewModel.isConnectingLinesModeEnabled){
            mainViewModel.hasFirstLineBeenDrawn = true;
            shapeButton.setBackgroundResource(R.drawable.button_shape_line_connected);
        }
        canvas.drawLine(xDown - offsetX,
                yDown - offsetY,
                x - offsetX,
                y - offsetY,
                paint);
        xDown = x;
        yDown = y;
    }


    @Override
    public void onTouchUp(float x, float y, Paint paint) {
        canvas.drawLine(xDown, yDown, x, y, paint);
    }


    @Override
    public boolean isUsingPlacementHelper(){
        return false;
    }
}