package com.jacstuff.sketchy.brushes.shapes.line;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;


public class LineOutlineDrawer implements LineDrawer {

    private final Canvas canvas;

    public LineOutlineDrawer(Canvas canvas){
        this.canvas = canvas;
    }


    @Override
    public void draw(float x1, float y1, float x2, float y2, int brushSize, Paint paint){
        int adjustedBrushSize = (brushSize / 3) +  2;
        drawLineOutline(x1, y1, x2, y2, adjustedBrushSize, paint);
    }


    private void drawLineOutline(float x1, float y1, float x2, float y2, int brushSize, Paint paint){
        Point p1 = new Point((int) x1, (int)y1);
        Point p2 = new Point((int) x2,(int) y2);
        Point p3 = getPointPerpendicularTo(x1, y1, x2, y2, false);
        Point p4 = getPointPerpendicularTo(x2, y2, x1, y1, true);
        Point p5, p6;
        float distance = getDistance( p1, p2);
        if(distance < brushSize){
            p5 = p3;
            p6 = p4;
        }
        else {
            float divisor = distance / brushSize;
            p5 = getPointFromPerpendicularLine(p1, p3, divisor);
            p6 = getPointFromPerpendicularLine(p2, p4, divisor);
        }
        drawLineFrom(p1, p2, paint);
        drawLineFrom(p1, p5, paint);
        drawLineFrom(p2, p6, paint);
        drawLineFrom(p5, p6, paint);
    }

    private void drawLineFrom(Point p1, Point p2, Paint paint){
        canvas.drawLine(p1.x, p1.y, p2.x, p2.y, paint);
    }

    private Point getPointPerpendicularTo(float x1, float y1, float x2, float y2, boolean isOppositeSide){
        float xDiff = x2 - x1;
        float yDiff = y2 - y1;
        if(isOppositeSide){
            yDiff *= -1;
            xDiff *= -1;
        }
        float x3 = x1 + yDiff;
        float y3 = y1 - xDiff;
        return new Point((int)x3, (int) y3);
    }


    private Point getPointFromPerpendicularLine(Point p1, Point p2, float divisor){
        float top = divisor - 1;
        float x = ((top / divisor) * p1.x ) + ((1.0f/divisor) * p2.x );
        float y = ((top / divisor) * p1.y ) + ((1.0f/divisor) * p2.y );
        return new Point((int)x, (int) y);
    }


    private float getDistance(Point p1, Point p2){
        double ac = Math.abs(p2.y - p1.y);
        double cb = Math.abs(p2.x - p1.x);
        return (float)Math.hypot(ac, cb);
    }

}
