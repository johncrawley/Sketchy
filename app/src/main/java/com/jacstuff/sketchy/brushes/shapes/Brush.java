package com.jacstuff.sketchy.brushes.shapes;


import android.graphics.Paint;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;
import com.jacstuff.sketchy.brushes.styles.Style;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public interface Brush {

    void onTouchDown(float x, float y, Paint paint);
    void init(PaintView paintView, MainActivity mainActivity, DrawerFactory drawerFactory);
    void onTouchMove(float x, float y, Paint paint);
    void onTouchUp(float x, float y, Paint paint);
    void onTouchUp(float x, float y, float offsetX, float offsetY, Paint paint);
    void setBrushSize(int brushSize);
    void setStyle(BrushStyle style);
    void reinitialize();
    void add(BrushStyle brushStyle, Style style);
    void notifyStrokeWidthChanged();
    BrushShape getBrushShape();
    void touchDown(float x, float y, Paint paint);
    void touchMove(float x, float y, Paint paint);
    void touchUp(float x, float y, Paint paint);
    boolean isColorChangedOnDown();

}
