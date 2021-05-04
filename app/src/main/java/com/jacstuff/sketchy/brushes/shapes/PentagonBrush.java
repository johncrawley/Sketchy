package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintGroup;


/*

       There are 5 points in a pentagon, and we start with the first one, which is directly above the midpoint.
        - we want the height of the pentagon to be the brush size
        - so the first point's y will be half a brush size less than the midpoint y

       - next we will get the two points that make up the base of the pentagon
        - there's an imaginary line that runs from the top point to these two points
        - that line is always the pentagon height multiplied by 1.051414
        - to get the actual points, we need the angles and then those points can be determined by:

                x1 = x0 + distance*cos(angle)
                y1 = y0 + distance*sin(angle)

        - where x0,y0 is (in this case) the top point and (x1,y1) will be a base point

        - Here's where it get's more complicated
            - the above trig. equations are based on an x,y scale
                    where x goes left-to-right
                and where y goes down to up

            - however in android canvas, while x does go left-to-right,
               y goes up-to-down, i.e. y=0 is at the top of the screen

            - so, to make things easier to calculate we switch the axis
                - so the actual canvas y-axis serves as our trig x-axis
                - and the actual canvas x-axis serves as our trig y-axis

                -for this reason we switch the cos and sin operations to get the offset points
                 to be pedantic, they are now
                        x1 = x0 + distance*sin(angle)
                        y1 = y0 + distance*cos(angle)

                - also note that with this switch,
                    - a line at 0 degrees is running North-to-South
                    - a line at 45 degrees runs NW to SE
                    - a line at 90 degrees runs horizontal, and so on...


          - it's a given that, when you join the top point to the two base points
            - the angle of the 2 lines is 36 degrees
            - it then follows that the angle from our inverted axis to the right point is 18 degrees
            - and the angle to the  left base point is -18 degrees


          - now with the two base points, we can derive the left and right points in the same way
            - using the left base point to get its opposite on the right side
            - and the right base point to get its opposite on the left side

            - the only thing that changes is the angle provided
                -because the original +/-18 degrees was relative to the switched x-axis
                - the new angles work out at +/-126

            - NB we could have worked out out the left and right points with a different method
               i.e. getting the ratio of a pentagon side to it's height
               and getting the appropriate angles
               but one's as easy as the other

             - I adapted some information from the following:
               https://www.instructables.com/Figuring-Measurements-of-a-5-pointed-Symmetrical-L/
*/

public class PentagonBrush extends AbstractBrush implements Brush {

    Point topPoint;
    float bottomRightX, bottomRightY, bottomLeftX, bottomLeftY, leftX, leftY, rightX, rightY;
    float heightToOppositePointRatio = 1.051414f;
    float distanceToOppositePoint = 0f;


    double radsFromTopPointToBottomRight;
    double radsFromTopPointToBottomLeft;
    double radsFromBottomRightPointToLeft;
    double radsFromBottomLeftPointToRight;

    int xFromTopPointToBottomRight;
    int yFromTopPointToBottomRight;
    int xFromTopPointToBottomLeft;
    int yFromTopPointToBottomLeft;
    int xFromBottomRightPointToLeft;
    int yFromBottomRightPointToLeft;
    int xFromBottomLeftPointToRight;
    int yFromBottomLeftPointToRight;


    public PentagonBrush(Canvas canvas, PaintGroup paintGroup){
        super(canvas, paintGroup, BrushShape.PENTAGON);
        topPoint = new Point();
        radsFromTopPointToBottomRight  = Math.toRadians(18);
        radsFromTopPointToBottomLeft   = Math.toRadians(-18);
        radsFromBottomRightPointToLeft = Math.toRadians(-126);
        radsFromBottomLeftPointToRight = Math.toRadians(126);
    }


    @Override
    public void onTouchDown(float x, float y, Paint paint){
        currentStyle.onDraw(paintGroup);
        deriveOutsidePoints(x,y);
        Path path = new Path();
        path.moveTo(topPoint.x, topPoint.y);
        path.lineTo(rightX, rightY);
        path.lineTo(bottomRightX, bottomRightY);
        path.lineTo(bottomLeftX, bottomLeftY);
        path.lineTo(leftX, leftY);
        path.close();
        canvas.drawPath(path, paint);
    }


    void deriveOutsidePoints(float x, float y){

        float topY = y - halfBrushSize;
        topPoint.set(x, topY );
        bottomRightX = x    + xFromTopPointToBottomRight;
        bottomRightY = topY + yFromTopPointToBottomRight;

        bottomLeftX = x     + xFromTopPointToBottomLeft;
        bottomLeftY = topY  + yFromTopPointToBottomLeft;

        rightX = bottomLeftX + xFromBottomLeftPointToRight;
        rightY = bottomLeftY + yFromBottomLeftPointToRight;

        leftX = bottomRightX + xFromBottomRightPointToLeft;
        leftY = bottomRightY + yFromBottomRightPointToLeft;

    }


    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);

        distanceToOppositePoint = brushSize * heightToOppositePointRatio;

        xFromTopPointToBottomRight  = getXPoint(radsFromTopPointToBottomRight);
        yFromTopPointToBottomRight  = getYPoint(radsFromTopPointToBottomRight);

        xFromTopPointToBottomLeft   = getXPoint(radsFromTopPointToBottomLeft);
        yFromTopPointToBottomLeft   = getYPoint(radsFromTopPointToBottomLeft);

        xFromBottomRightPointToLeft = getXPoint(radsFromBottomRightPointToLeft);
        yFromBottomRightPointToLeft = getYPoint(radsFromBottomRightPointToLeft);

        xFromBottomLeftPointToRight = getXPoint(radsFromBottomLeftPointToRight);
        yFromBottomLeftPointToRight = getYPoint(radsFromBottomLeftPointToRight);

    }



    private int getXPoint(double rads){
        return getXPoint(rads, distanceToOppositePoint);
    }


    private int getYPoint(double rads){
        return getYPoint(rads, distanceToOppositePoint);
    }


    int getXPoint(double rads, float distance){
        return (int) (distance * Math.sin(rads));
    }


    int getYPoint(double rads, float distance){
        return (int)(distance * Math.cos(rads));
    }

}