package com.jacstuff.sketchy.brushes.shapes.twostep;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.shapes.drawer.ArbitraryTriangleDrawer;
import com.jacstuff.sketchy.brushes.shapes.initializer.DragRectInitializer;
import com.jacstuff.sketchy.utils.MathUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class TempTriangleBrush extends AbstractTwoStepBrush  implements Brush, TwoStepBrush {

    float downX, downY, upX, upY;
    private float lineMidpointX, lineMidpointY;
    final Path path;
    private float thirdPointX, thirdPointY;


    public TempTriangleBrush() {
        super(BrushShape.TRIANGLE_ARBITRARY);
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
        path.lineTo(upX -offsetX, upY - offsetY);
        path.close();
        canvas.drawPath(path, paint);
        setFirstTrianglePoint(firstPointX, firstPointY);
        setSecondTrianglePoint(secondPointX, secondPointY);
        setThirdTrianglePoint(thirdPointX -offsetX,thirdPointY - offsetY);
        mainViewModel.hasFirstLineBeenDrawn = true;
    }


    void drawShapeWithExistingPoints(TrianglePoints trianglePoints, Paint paint){
        path.reset();
        path.moveTo(trianglePoints.getPoint1().x, trianglePoints.getPoint1().y);
        path.lineTo(trianglePoints.getPoint2().x,trianglePoints.getPoint2().y);
        path.lineTo(trianglePoints.getPoint3().x, trianglePoints.getPoint3().y);
        path.close();
        canvas.drawPath(path, paint);
    }



    public PointF getShapeMidPoint(){
        PointF point = new PointF();
        point.x = (downX + upX + thirdPointX) /3;
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
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint) {
        if(mainViewModel.isConnectedTrianglesModeEnabled && mainViewModel.hasFirstTriangleBeenDrawn){
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
            if(mainViewModel.isConnectedTrianglesModeEnabled){
                drawShapeWithExistingPoints(getLatestTriangle(new PointF(x,y)), paint);
                return;
            }
            drawShape(x, y, 0,0, paint);
            return;
        }
        canvas.drawLine(downX, downY, x, y, paint);
    }


    private TrianglePoints getOldTriangle(){
        return new TrianglePoints(mainViewModel.variableTrianglePoint1,
                mainViewModel.variableTrianglePoint2,
                mainViewModel.variableTrianglePoint3);
    }


    @Override
    public void onTouchUp(float x, float y, Paint paint) {
        onTouchUp(x, y, 0,0, paint);
    }


    @Override
    public void onTouchUp(float x, float y, float offsetX, float offsetY, Paint paint) {
        if(stepState == StepState.SECOND){
            if(mainViewModel.isConnectedTrianglesModeEnabled){
                TrianglePoints trianglePoints = getLatestTriangle(new PointF(x,y));
                drawShapeWithExistingPoints(trianglePoints, paint);
                mainViewModel.variableTrianglePoint1 = trianglePoints.getPoint1();
                mainViewModel.variableTrianglePoint2 = trianglePoints.getPoint2();
                mainViewModel.variableTrianglePoint3 = trianglePoints.getPoint3();
                return;
            }
            drawShape(x, y, offsetX, offsetY, paint);
            return;
        }
        onTouchUpForFirstState(x,y, paint);
    }




    private void onTouchUpForFirstState(float x, float y, Paint paint){
        canvas.drawLine(downX, downY, x, y, paint);
        upX = x;
        upY = y;
        lineMidpointX = ((upX + downX) /2);
        lineMidpointY = ((upY + downY) /2);
    }


    private void setFirstTrianglePoint(float x, float y){
        mainViewModel.variableTrianglePoint1 = new PointF(x,y);
    }


    private void setSecondTrianglePoint(float x, float y){
        mainViewModel.variableTrianglePoint2 = new PointF(x,y);
    }


    private void setThirdTrianglePoint(float x, float y){
        mainViewModel.variableTrianglePoint3 = new PointF(x,y);
    }



    @Override
    public boolean isUsingPlacementHelper(){
        return false;
    }


    public PointF getLineMidPoint() {
        PointF p = new PointF();
        p.x = lineMidpointX;
        p.y = lineMidpointY;
        return p;
    }


    public TrianglePoints getLatestTriangle(PointF latestThirdPoint){
       List<PointF> newTrianglePoints = getClosestTwoTrianglePointsToLatestPoint(getOldTriangle(), latestThirdPoint);
       return new TrianglePoints(latestThirdPoint,
               newTrianglePoints.get(1),
               newTrianglePoints.get(2));
    }


    private List<PointF> getClosestTwoTrianglePointsToLatestPoint(TrianglePoints oldTriangle, PointF latestPoint){
        return oldTriangle.getPoints()
                .stream()
                .sorted((p1,p2) -> Float.compare(MathUtils.getDistance(p1, latestPoint), MathUtils.getDistance(p2, latestPoint)))
                .limit(2)
                .collect(Collectors.toList());
    }


    class TrianglePoints{

        final PointF point1, point2, point3;
        public TrianglePoints(PointF point1, PointF point2, PointF point3){
            this.point1 = point1;
            this.point2 = point2;
            this.point3 = point3;
        }

        public PointF getPoint1(){
            return point1;
        }

        public PointF getPoint2(){
            return point2;
        }

        public PointF getPoint3(){
            return point3;
        }

        public List<PointF> getPoints(){
            return Arrays.asList(point1, point2, point3);
        }

    }

}
