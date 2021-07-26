package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Paint;
import android.graphics.Path;

import com.jacstuff.sketchy.brushes.BrushShape;


/*
     inside length = starPointSide * 2 + ( innerChord)
     innerChord =  2 * startPointSide * 0.309
     inside_length = (2 * starPointSide) + (2 * starPointSide * 0.309)
     inside_length = 2 * starPointSide + 0.618 * starPointSide
     inside_length / 2.618 = starPointSide
 */
public class StarBrush extends PentagonBrush {


    private float insideBottomPointX, insideBottomLeftPointX, insideTopLeftPointX, insideTopRightPointX, insideBottomRightPointX;
    private float insideBottomPointY, insideBottomLeftPointY, insideTopLeftPointY, insideTopRightPointY, insideBottomRightPointY;
    private float insideBottomPointXOffset, insideBottomLeftPointXOffset, insideTopLeftPointXOffset, insideTopRightPointXOffset, insideBottomRightPointXOffset;
    private float insideBottomPointYOffset, insideBottomLeftPointYOffset, insideTopLeftPointYOffset, insideTopRightPointYOffset, insideBottomRightPointYOffset;


    public StarBrush(){
        super();
        brushShape = BrushShape.STAR;
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){
        deriveOutsidePoints(0,0);
        deriveInsidePoints();
        Path path = new Path();

        path.moveTo(topPoint.x, topPoint.y);
        path.lineTo(insideTopRightPointX, insideTopRightPointY);
        path.lineTo(rightX, rightY);
        path.lineTo(insideBottomRightPointX, insideBottomRightPointY);
        path.lineTo(bottomRightX, bottomRightY);
        path.lineTo(insideBottomPointX, insideBottomPointY);
        path.lineTo(bottomLeftX, bottomLeftY);
        path.lineTo(insideBottomLeftPointX, insideBottomLeftPointY);
        path.lineTo(leftX, leftY);
        path.lineTo(insideTopLeftPointX, insideTopLeftPointY);

        path.close();
        canvas.drawPath(path, paint);
    }


    private void deriveInsidePoints(){

        insideTopRightPointX = insideTopRightPointXOffset;
        insideTopRightPointY = -halfBrushSize + insideTopRightPointYOffset;

        insideTopLeftPointX = insideTopLeftPointXOffset;
        insideTopLeftPointY = -halfBrushSize  + insideTopLeftPointYOffset;

        insideBottomLeftPointX = insideBottomLeftPointXOffset;
        insideBottomLeftPointY = -halfBrushSize   + insideBottomLeftPointYOffset;

        insideBottomRightPointX = insideBottomRightPointXOffset;
        insideBottomRightPointY = -halfBrushSize + insideBottomRightPointYOffset;

        insideBottomPointX = bottomLeftX + insideBottomPointXOffset;
        insideBottomPointY = bottomLeftY + insideBottomPointYOffset;
    }


    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
        final float BASE_TO_SIDE_LENGTH_RATIO = 0.309f;
        final float INSIDE_LINE_TO_STAR_POINT_LENGTH_RATIO = 2.618f;
        float starPointSideLength = distanceToOppositePoint / INSIDE_LINE_TO_STAR_POINT_LENGTH_RATIO;

        float innerChordLength = starPointSideLength * BASE_TO_SIDE_LENGTH_RATIO * 2;
        float furtherInnerPointLength = starPointSideLength + innerChordLength;

        insideTopRightPointXOffset = getXPoint(radsFromTopPointToBottomRight, starPointSideLength);
        insideTopRightPointYOffset = getYPoint(radsFromTopPointToBottomRight, starPointSideLength);

        insideBottomRightPointXOffset = getXPoint(radsFromTopPointToBottomRight, furtherInnerPointLength);
        insideBottomRightPointYOffset = getYPoint(radsFromTopPointToBottomRight, furtherInnerPointLength);

        insideBottomPointXOffset = getXPoint(radsFromBottomLeftPointToRight, starPointSideLength);
        insideBottomPointYOffset = getYPoint(radsFromBottomLeftPointToRight, starPointSideLength);

        insideBottomLeftPointXOffset = getXPoint(radsFromTopPointToBottomLeft, furtherInnerPointLength);
        insideBottomLeftPointYOffset = getYPoint(radsFromTopPointToBottomLeft, furtherInnerPointLength);

        insideTopLeftPointXOffset = getXPoint(radsFromTopPointToBottomLeft, starPointSideLength);
        insideTopLeftPointYOffset = getYPoint(radsFromTopPointToBottomLeft, starPointSideLength);
    }


}
