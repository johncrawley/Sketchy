package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushShape;

public class AstroidBrush extends AbstractBrush implements Brush {

        private float quarterBrushSize;
        private final Path path;
        private Point topLeftCorner, topRightCorner,bottomRightCorner,bottomLeftCorner;
        private Point midCurveTop, midCurveBottom, midCurveLeft, midCurveRight;

        public AstroidBrush(){
            super(BrushShape.ASTROID);
            path = new Path();
        }


        @Override
        public void postInit(){
            recalculateDimensions();
        }


        @Override
        public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
            path.reset();
            path.moveTo( topLeftCorner.x, topLeftCorner.y);
            path.quadTo(midCurveTop.x, midCurveTop.y, topRightCorner.x, topRightCorner.y);
            path.quadTo(midCurveRight.x, midCurveRight.y, bottomRightCorner.x, bottomRightCorner.y);
            path.quadTo(midCurveBottom.x, midCurveBottom.y, bottomLeftCorner.x, bottomLeftCorner.y);
            path.quadTo(midCurveLeft.x, midCurveLeft.y, topLeftCorner.x, topLeftCorner.y);
            path.close();
            canvas.drawPath(path, paint);
        }


        @Override
        public void onTouchMove(float x, float y, Paint paint){
            onTouchDown(x, y, paint);
        }


        @Override
        public void setBrushSize(int brushSize){
            super.setBrushSize(brushSize);
            quarterBrushSize = halfBrushSize / 2f;
            recalculateDimensions();
        }


        @Override
        public void recalculateDimensions(){
            topLeftCorner = new Point(-halfBrushSize, -halfBrushSize);
            topRightCorner = new Point(halfBrushSize, -halfBrushSize);
            bottomRightCorner = new Point(halfBrushSize, halfBrushSize);
            bottomLeftCorner = new Point(-halfBrushSize, halfBrushSize);
            int midPercentage = mainActivity.getResources().getInteger(R.integer.astroid_shape_curve_seek_bar_max) / 2;
            //int percentage = mainViewModel.astroid_shape_curve_rate - midPercentage;
            int percentage = midPercentage - mainViewModel.astroidShapeCurveRate;
            int  midPointLength =  (int)((quarterBrushSize/100f) * percentage);
            midCurveTop = new Point(0, -midPointLength);
            midCurveBottom = new Point(0, midPointLength);
            midCurveLeft = new Point(-midPointLength, 0);
            midCurveRight = new Point (midPointLength, 0);
        }


}
