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


public class ArbitraryConnectedTriangleBrush extends CurvedLineBrush {

    final Path path;
    private float thirdPointX, thirdPointY;
    private boolean hasAlreadyDrawnOnce = false; // used to prevent repeated saves and calculations for each segment of a kaleidoscope
    private PointF adjustedThirdPoint;


    public ArbitraryConnectedTriangleBrush() {
        super();
        setBrushShape(BrushShape.TRIANGLE_ARBITRARY);
        brushInitializer = new DragRectInitializer();
        path = new Path();
        isDrawnFromCenter = false;
        resetStepState();
        setBrushShape(BrushShape.TRIANGLE_ARBITRARY);
    }


    @Override
    public void postInit(){
        super.postInit();
        this.drawer = new ArbitraryTriangleDrawer(paintView, viewModel, this);
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
        resetStepState();
        viewModel.connectedTriangleState.setFirstItemDrawn(false);
        mainActivity.getConnectedTriangleIconModifier().resetIconAndState();
        viewModel.trianglePoints.reset();
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint) {
        brushDownForConnectedMode(p);
        if (stepState == StepState.FIRST) {
            downX = p.x;
            downY = p.y;
        }
    }


    private void brushDownForConnectedMode(Point p){
        hasAlreadyDrawnOnce = false;
        if(isInConnectedMode() && viewModel.connectedTriangleState.isFirstItemDrawn()){
            assignClosestPointsForConnectTriangle(new PointF(p.x, p.y));
            setStateTo(StepState.SECOND);
        }
    }


    private boolean isInConnectedMode(){
        return viewModel.connectedTriangleState.isConnectedModeEnabled();
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint) {
        if(stepState == StepState.SECOND){
            drawTriangle(new PointF(x, y), 0,0, paint);
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
        onTouchUpSecondState(new PointF(x,y), offsetX, offsetY, paint);
    }


    private void onTouchUpForFirstState(float x, float y, Paint paint){
        canvas.drawLine(downX, downY, x, y, paint);
        upX = x;
        upY = y;
    }


    private void onTouchUpSecondState(PointF point, float offsetX, float offsetY, Paint paint){
        if(isInConnectedMode()){
            onTouchUpSecondStateConnected(point, offsetX, offsetY, paint);
            return;
        }
        drawTriangle(point, offsetX, offsetY, paint);
    }


    private void onTouchUpSecondStateConnected(PointF originalPoint, float offsetX, float offsetY, Paint paint){
        if(!hasAlreadyDrawnOnce){
            mainActivity.getConnectedTriangleIconModifier().setConnectedIconAndState();
            saveTrianglePoints();
            hasAlreadyDrawnOnce = true;
            adjustedThirdPoint = viewModel.trianglePoints.getClosePointOrAddToExisting(originalPoint);
            viewModel.connectedTriangleState.setFirstItemDrawn(true);
        }
        drawTriangle(adjustedThirdPoint, offsetX, offsetY, paint);
    }


    private void saveTrianglePoints(){
        viewModel.trianglePoints.addPoints(new PointF(downX, downY),
                new PointF(upX, upY));
    }


    void drawTriangle(PointF thirdPoint, float offsetX, float offsetY, Paint paint){
        thirdPointX = thirdPoint.x;
        thirdPointY = thirdPoint.y;

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
    }


    @Override
    public boolean isUsingPlacementHelper(){
        return false;
    }


    private void assignClosestPointsForConnectTriangle(PointF latestThirdPoint){
        if(!viewModel.connectedTriangleState.isConnectedModeEnabled()){
            return;
        }
        List<PointF> closesTrianglePoints = viewModel.trianglePoints.getNearestPointsTo(latestThirdPoint);
        downX =  closesTrianglePoints.get(0).x;
        downY =  closesTrianglePoints.get(0).y;
        upX = closesTrianglePoints.get(1).x;
        upY = closesTrianglePoints.get(1).y;
    }


}
