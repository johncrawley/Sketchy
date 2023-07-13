package com.jacstuff.sketchy.brushes.shapes.twostep;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.ArbitraryTriangleDrawer;
import com.jacstuff.sketchy.brushes.shapes.initializer.DragRectInitializer;

import java.util.List;


public class TempTriangleBrush extends CurvedLineBrush {

    final Path path;
    private float thirdPointX, thirdPointY;


    public TempTriangleBrush() {
        super();
        setBrushShape(BrushShape.TRIANGLE_ARBITRARY);
        brushInitializer = new DragRectInitializer();
        path = new Path();
        isDrawnFromCenter = false;
        resetState();
        setBrushShape(BrushShape.TRIANGLE_ARBITRARY);
    }


    @Override
    public void postInit(){
        super.postInit();
        this.drawer = new ArbitraryTriangleDrawer(paintView, mainViewModel, this);
        drawer.init();
    }


    public PointF getShapeMidPoint(){
        PointF point = new PointF();
        point.x = (downX + upX + thirdPointX) / 3;
        point.y = (downY + upY + thirdPointY) / 3;
        return point;
    }


    @Override
    public void reset(){
        downX = 0;
        downY = 0;
        upX = 0;
        upY = 0;
        resetState();
        mainViewModel.hasFirstTriangleBeenDrawn = false;
        mainViewModel.trianglePoints.reset();
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint) {
        if(mainViewModel.hasFirstTriangleBeenDrawn){
            assignClosestPointsForConnectTriangle(new PointF(p.x, p.y));
            setStateTo(StepState.SECOND);
        }
        if (stepState == StepState.FIRST) {
            downX = p.x;
            downY = p.y;
        }
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint) {
        if(stepState == StepState.SECOND){
            drawShape(x, y, 0,0, paint);
            return;
        }
        canvas.drawLine(downX, downY, x, y, paint);
    }


    @Override
    public void onTouchUp(float x, float y, Paint paint) {
        onTouchUp(x, y, 0,0, paint);
    }


    @Override
    public void onTouchUp(float x, float y, float offsetX, float offsetY, Paint paint) {
        if(stepState == StepState.FIRST){
            onTouchUpForFirstState(x,y, paint);
            return;
        }
        onTouchUpSecondState(x,y, offsetX, offsetY, paint);
    }


    private void onTouchUpForFirstState(float x, float y, Paint paint){
        canvas.drawLine(downX, downY, x, y, paint);
        upX = x;
        upY = y;
    }


    private void onTouchUpSecondState(float x, float y, float offsetX, float offsetY, Paint paint){
        saveTrianglePoints(x, y);
        drawShape(x, y, offsetX, offsetY, paint);
        mainViewModel.hasFirstTriangleBeenDrawn = true;
    }


    private void saveTrianglePoints(float x, float y){
        mainViewModel.trianglePoints.addPoints(new PointF(downX, downY),
                new PointF(x, y),
                new PointF(upX, upY));
    }


    void drawShape(float x, float y, float offsetX, float offsetY, Paint paint){
        thirdPointX = x;
        thirdPointY = y;
        float firstPointX = downX - offsetX;
        float firstPointY = downY - offsetY;

        float secondPointX = upX - offsetX;
        float secondPointY = upY - offsetY;

        path.reset();
        path.moveTo(firstPointX, firstPointY);
        path.lineTo(thirdPointX -offsetX,thirdPointY - offsetY);
        path.lineTo(secondPointX, secondPointY);
        path.close();
        canvas.drawPath(path, paint);
        mainViewModel.hasFirstLineBeenDrawn = true;
    }


    @Override
    public boolean isUsingPlacementHelper(){
        return false;
    }


    private void assignClosestPointsForConnectTriangle(PointF latestThirdPoint){
        if(!mainViewModel.isConnectedTrianglesModeEnabled){
            return;
        }
        List<PointF> closesTrianglePoints = mainViewModel.trianglePoints.getNearestPointsTo(latestThirdPoint);
        downX =  closesTrianglePoints.get(0).x;
        downY =  closesTrianglePoints.get(0).y;
        upX = closesTrianglePoints.get(1).x;
        upY = closesTrianglePoints.get(1).y;
    }


}
