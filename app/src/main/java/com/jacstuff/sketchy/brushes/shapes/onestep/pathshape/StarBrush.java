package com.jacstuff.sketchy.brushes.shapes.onestep.pathshape;

import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;


public class StarBrush extends PentagonBrush {


    private Point insideBottom, insideTopLeft, insideTopRight, insideBottomRight, insideBottomLeft;
    private float furtherInnerPointLength;
    private float starPointSideLength;

    public StarBrush(){
        super();
        brushShape = BrushShape.STAR;
    }


    @Override
    public void generatePath(PointF point){
        deriveOutsidePoints();
        deriveInsidePoints();
        path = new Path();
        path.moveTo(topPoint.x, topPoint.y);
        path.lineTo(insideTopRight.x, insideTopRight.y);
        path.lineTo(rightX, rightY);
        path.lineTo(insideBottomRight.x, insideBottomRight.y);
        path.lineTo(bottomRightX, bottomRightY);
        path.lineTo(insideBottom.x, insideBottom.y);
        path.lineTo(bottomLeftX, bottomLeftY);
        path.lineTo(insideBottomLeft.x, insideBottomLeft.y);
        path.lineTo(leftX, leftY);
        path.lineTo(insideTopLeft.x, insideTopLeft.y);
        path.close();
    }


    private void deriveInsidePoints(){

        insideTopRight   = new Point(getX(radsFromTopPointToBottomRight, starPointSideLength),
                getYOffset(radsFromTopPointToBottomRight, starPointSideLength));

        insideTopLeft    = new Point(getX(radsFromTopPointToBottomLeft, starPointSideLength),
                getYOffset(radsFromTopPointToBottomLeft, starPointSideLength));

        insideBottomLeft = new Point(getX(radsFromTopPointToBottomLeft, furtherInnerPointLength),
                getYOffset(radsFromTopPointToBottomLeft, furtherInnerPointLength));

        insideBottomRight = new Point(getX(radsFromTopPointToBottomRight, furtherInnerPointLength),
                getYOffset(radsFromTopPointToBottomRight, furtherInnerPointLength));

        insideBottom      = new Point((int)bottomLeftX + getX(radsFromBottomLeftPointToRight, starPointSideLength),
                (int)bottomLeftY + getY(radsFromBottomLeftPointToRight, starPointSideLength));
    }


    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
        final float BASE_TO_SIDE_LENGTH_RATIO = 0.309f;
        final float INSIDE_LINE_TO_STAR_POINT_LENGTH_RATIO = 2.618f;
        starPointSideLength = distanceToOppositePoint / INSIDE_LINE_TO_STAR_POINT_LENGTH_RATIO;
        float innerChordLength = starPointSideLength * BASE_TO_SIDE_LENGTH_RATIO * 2;
        furtherInnerPointLength = starPointSideLength + innerChordLength;
    }


    private int getYOffset(double rads, float pointLength){
        return -halfBrushSize + getY(rads, pointLength);
    }


}
