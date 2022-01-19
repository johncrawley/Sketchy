package com.jacstuff.sketchy.brushes.shapes.twostep;

import android.graphics.Paint;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.ArbitraryTriangleDrawer;


public class ArbitraryTriangleBrush extends CurvedLineBrush {

    private float thirdPointX, thirdPointY;

    public ArbitraryTriangleBrush() {
        super();
        setBrushShape(BrushShape.TRIANGLE_ARBITRARY);
    }


    @Override
    public void postInit(){
        super.postInit();
        this.drawer = new ArbitraryTriangleDrawer(paintView, mainViewModel, this);
        drawer.init();
    }


    @Override
    void drawShape(float x, float y, float offsetX, float offsetY, Paint paint){
        thirdPointX = x;
        thirdPointY = y;
        path.reset();
        path.moveTo(downX - offsetX, downY - offsetY);
        path.lineTo(thirdPointX -offsetX,thirdPointY - offsetY);
        path.lineTo(upX -offsetX, upY - offsetY);
        path.close();
        canvas.drawPath(path, paint);
    }


    @Override
    public PointF getShapeMidPoint(){
        PointF point = new PointF();
        point.x = (downX + upX + thirdPointX) /3;
        point.y = (downY + upY + thirdPointY) / 3;
        return point;
    }
}
