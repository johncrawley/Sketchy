package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.paintview.KaleidoscopeDrawer;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.KaleidoscopeHelper;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

// for drawing lines
// onMove only draws preview line
// doesn't draw proper line until onUp
// doesn't draw shadow until onUp
public class DragLineDrawer implements Drawer{

        private final PaintHelperManager paintHelperManager;
        private final KaleidoscopeHelper kaleidoscopeHelper;
        private final KaleidoscopeDrawer kaleidoscopeDrawer;
        private final Canvas canvas;
        private final Brush brush;
        private final PaintView paintView;


        public DragLineDrawer(Brush brush, PaintView paintView, MainViewModel viewModel){
            this.brush = brush;
            this.paintView = paintView;
            this.canvas =  paintView.getCanvas();
            this.paintHelperManager = paintView.getPaintHelperManager();
            kaleidoscopeHelper = paintHelperManager.getKaleidoscopeHelper();

            kaleidoscopeDrawer = new KaleidoscopeDrawer(paintView, viewModel, kaleidoscopeHelper);
        }

        public void init(){
            kaleidoscopeDrawer.initParentDrawer(this);
        }


        @Override
        public void down(float x, float y, Paint paint) {
            brush.onTouchDown(x,y, paint);
            paintView.invalidate();
        }


        @Override
        public void move(float x, float y, Paint paint) {
            paintView.enablePreviewLayer();
            brush.onTouchMove(x,y, paint);
            paintView.invalidate();
        }


        @Override
        public void up(float x, float y, Paint paint) {
            paintView.disablePreviewLayer();
            if(kaleidoscopeHelper.isEnabled()){
                kaleidoscopeDrawer.drawKaleidoscope(x, y, paint);
            }
            else{
                drawDragLine(x,y, paint);
            }
            paintView.pushHistory();
            paintView.invalidate();
        }


        void drawDragLine(float x, float y, Paint paint){
            if(paintHelperManager.getShadowHelper().isShadowEnabled()){
                brush.onTouchUp(x,y, 0,0, paintView.getShadowPaint());
            }
            brush.onTouchUp(x,y, 0, 0, paint);
        }


    @Override
    public void drawKaleidoscopeSegment(float x, float y, float angle, Paint paint){
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
            brushTouchUp(x,y, paintView.getShadowPaint());
        }
        brushTouchUp(x,y, paint);
    }


    private void brushTouchUp(float x, float y, Paint paint){
        brush.onTouchUp(x,y, kaleidoscopeHelper.getCenterX(), kaleidoscopeHelper.getCenterY(), paint);
    }
}

