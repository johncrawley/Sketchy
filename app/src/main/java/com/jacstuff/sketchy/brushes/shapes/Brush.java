package com.jacstuff.sketchy.brushes.shapes;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;
import com.jacstuff.sketchy.brushes.styles.Style;


public interface Brush {

    void init(DrawerFactory drawerFactory);

    void onTouchDown(float x, float y, Paint paint);
    void onTouchMove(float x, float y, Paint paint);
    void onTouchUp(float x, float y, Paint paint);
    void onTouchUp(float x, float y, float offsetX, float offsetY, Paint paint);

    int getBrushSize();
    int getHalfBrushSize();

    PointF getShapeMidpoint();
    PointF getShapeMinPoint();
    PointF getShapeMaxPoint();

    void reset();

    void onTouchDown(Point p, Canvas canvas, Paint paint);
    void onTouchMove(Point p, Canvas canvas, Paint paint);
    void onTouchUp(Point p, Canvas canvas, Paint paint);

    boolean isUsingPlacementHelper();

    void setBrushSize(int brushSize);
    void setStyle(Style style);
    boolean isDrawnFromCenter(); //i.e. is canvas (0,0) translated to centre of shape before drawing.
    void reinitialize();
    void notifyStrokeWidthChanged();
    void recalculateDimensions();
    BrushShape getBrushShape();

    void onDeallocate();

    void touchDown(float x, float y, Paint paint);
    void touchMove(float x, float y, Paint paint);
    void touchUp(float x, float y, Paint paint);

    boolean isColorChangedOnDown();

    default boolean isOnFirstStep(){ return true;}
}
