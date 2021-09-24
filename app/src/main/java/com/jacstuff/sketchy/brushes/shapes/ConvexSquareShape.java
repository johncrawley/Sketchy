package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushShape;

public class ConvexSquareShape extends AbstractBrush implements Brush {

        private float quarterBrushSize;

        public ConvexSquareShape(){
            super(BrushShape.CONVEX_SQUARE);
        }


        @Override
        public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
            Point topLeftCorner = new Point(-halfBrushSize, -halfBrushSize);
            Point topRightCorner = new Point(halfBrushSize, -halfBrushSize);
            Point bottomRightCorner = new Point(halfBrushSize, halfBrushSize);
            Point bottomLeftCorner = new Point(-halfBrushSize, halfBrushSize);
            int midPercentage = mainActivity.getResources().getInteger(R.integer.convex_square_shape_seek_bar_max) / 2;
            int percentage = mainViewModel.convexSquareShapeMidpointLengthPercentage - midPercentage;
            int  midPointLength =  (int)((quarterBrushSize/100f) * percentage);
            Point midCurveTop = new Point(0, -midPointLength);
            Point midCurveBottom = new Point(0, midPointLength);
            Point midCurveLeft = new Point(-midPointLength, 0);
            Point midCurveRight = new Point (midPointLength, 0);

            Path path = new Path();
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
        }

}
