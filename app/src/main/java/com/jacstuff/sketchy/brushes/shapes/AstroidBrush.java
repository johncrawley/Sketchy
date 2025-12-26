package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;

public class AstroidBrush extends AbstractPathShape{

        private float quarterBrushSize;
        private Point topLeftCorner, topRightCorner,bottomRightCorner,bottomLeftCorner;
        private Point midCurveTop, midCurveBottom, midCurveLeft, midCurveRight;
        private int curveRate;

        public AstroidBrush(){
            super(BrushShape.ASTROID);
            path = new Path();
        }


        public void postInit(){
            recalculateDimensions();
        }


        @Override
        public void generatePath(PointF p){
            path.reset();
            path.moveTo( topLeftCorner.x, topLeftCorner.y);
            path.quadTo(midCurveTop.x, midCurveTop.y, topRightCorner.x, topRightCorner.y);
            path.quadTo(midCurveRight.x, midCurveRight.y, bottomRightCorner.x, bottomRightCorner.y);
            path.quadTo(midCurveBottom.x, midCurveBottom.y, bottomLeftCorner.x, bottomLeftCorner.y);
            path.quadTo(midCurveLeft.x, midCurveLeft.y, topLeftCorner.x, topLeftCorner.y);
            path.close();
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
            int midPercentage = 530 / 2;
            //int percentage = mainViewModel.astroid_shape_curve_rate - midPercentage;
            int percentage = midPercentage - curveRate;
            int  midPointLength =  (int)((quarterBrushSize/100f) * percentage);
            midCurveTop = new Point(0, -midPointLength);
            midCurveBottom = new Point(0, midPointLength);
            midCurveLeft = new Point(-midPointLength, 0);
            midCurveRight = new Point (midPointLength, 0);
        }


}
