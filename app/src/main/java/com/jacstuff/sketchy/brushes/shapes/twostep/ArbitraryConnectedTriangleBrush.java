package com.jacstuff.sketchy.brushes.shapes.twostep;

import static com.jacstuff.sketchy.paintview.helpers.shadow.ShadowOffsetType.USE_SET_VALUE;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.ArbitraryTriangleDrawer;
import com.jacstuff.sketchy.paintview.history.DrawHistory;


public class ArbitraryConnectedTriangleBrush extends CurvedLineBrush {

    final Path path;
    private float thirdPointX, thirdPointY;
    private boolean hasAlreadyDrawnOnce = false; // used to prevent repeated saves and calculations for each segment of a kaleidoscope
    private PointF adjustedThirdPoint;
    private DrawHistory drawHistory;


    public ArbitraryConnectedTriangleBrush() {
        super();
        setBrushShape(BrushShape.TRIANGLE_ARBITRARY);
        path = new Path();
        isDrawnFromCenter = false;
        usesBrushSizeControl = false;
        resetStepState();
        setBrushShape(BrushShape.TRIANGLE_ARBITRARY);
        shadowOffsetType = USE_SET_VALUE;
    }


    @Override
    public void postInit(){
        super.postInit();
        this.drawer = new ArbitraryTriangleDrawer(paintView, this);
       // drawHistory = viewModel.drawHistory;
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
        var currentItem = drawHistory.getCurrent();
        if(currentItem != null){
            currentItem.getConnectedTriangleState().setFirstItemDrawn(false);
            currentItem.getTrianglePoints().reset();
        }
       // mainActivity.getConnectedTriangleIconModifier().resetIconAndState();
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
        log("entered brushDownForConnectedMode() shouldDraw connected triangle: " + drawHistory.shouldDrawConnectedTriangle());
        if(drawHistory.shouldDrawConnectedTriangle()){
            assignClosestPointsForConnectTriangle(new PointF(p.x, p.y));
            setStateTo(StepState.SECOND);
        }
    }


    private void log(String msg){
        System.out.println("^^^ ArbConnectedTriangleBrush: " + msg);
    }


    private boolean isFirstItemDrawn(){
        return drawHistory.isFirstTriangleDrawn();
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


    private void onTouchUpSecondState(PointF thirdPoint, float offsetX, float offsetY, Paint paint){
        if(drawHistory.isConnectedTriangleModeEnabled()){
            onTouchUpSecondStateConnected(thirdPoint, offsetX, offsetY, paint);
            return;
        }
        drawTriangle(thirdPoint, offsetX, offsetY, paint);
    }


    private void onTouchUpSecondStateConnected(PointF thirdPoint, float offsetX, float offsetY, Paint paint){
        if(!hasAlreadyDrawnOnce){
            setConnectedIconAndState();
            saveTrianglePoints();
            hasAlreadyDrawnOnce = true;
            var currentItem = drawHistory.getCurrent();
            if(currentItem != null){
                adjustedThirdPoint = currentItem.getTrianglePoints()
                        .getNearbyPointToOrUse(thirdPoint);
                drawHistory.saveThirdTrianglePoint(adjustedThirdPoint);
                currentItem.getConnectedTriangleState().setFirstItemDrawn(true);
            }
        }
        drawTriangle(adjustedThirdPoint, offsetX, offsetY, paint);
    }


    private void setConnectedIconAndState(){
      //  mainActivity.getConnectedTriangleIconModifier().setConnectedIconAndState();
    }


    private void saveTrianglePoints(){
        drawHistory.addTrianglePoints(new PointF(downX, downY), new PointF(upX, upY));
    }


    void drawTriangle(PointF thirdPoint, float offsetX, float offsetY, Paint paint){
        thirdPointX = thirdPoint.x;
        thirdPointY = thirdPoint.y;

        var p1 = new PointF(downX - offsetX, downY - offsetY);
        var p2 = new PointF(upX - offsetX, upY - offsetY);
        var offset = new PointF(offsetX, offsetY);

        drawTrianglePath(p1, p2, offset, paint);
    }


    private void drawTrianglePath(PointF firstPoint, PointF secondPoint, PointF offset, Paint paint){
        path.reset();
        path.moveTo(firstPoint.x, firstPoint.y);
        path.lineTo(thirdPointX - offset.x,thirdPointY - offset.y);
        path.lineTo(secondPoint.x, secondPoint.y);
        path.close();
        canvas.drawPath(path, paint);
    }


    private void assignClosestPointsForConnectTriangle(PointF latestThirdPoint){
        if(!drawHistory.isConnectedTriangleModeEnabled()){
            return;
        }
        var currentItem = drawHistory.getCurrent();
        if(currentItem != null){
            var closestTrianglePoints = currentItem.getTrianglePoints().getNearestPointsTo(latestThirdPoint);
            if(closestTrianglePoints == null){
                return;
            }
            downX =  closestTrianglePoints.get(0).x;
            downY =  closestTrianglePoints.get(0).y;
            upX = closestTrianglePoints.get(1).x;
            upY = closestTrianglePoints.get(1).y;
        }
    }


}
