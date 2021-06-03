package com.jacstuff.sketchy.brushes.styles;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;

import com.jacstuff.sketchy.paintview.PaintGroup;

public class JaggedStyle  extends AbstractStyle implements Style {

        PathDashPathEffect pathDashPathEffect;
        private PaintGroup paintGroup;

    public JaggedStyle(PaintGroup paintGroup){
        this.paintGroup = paintGroup;
        pathDashPathEffect = new PathDashPathEffect(createPath(20, 10), 12, 5, PathDashPathEffect.Style.ROTATE);
    }


        @Override
        public void init(PaintGroup paintGroup, int brushSize ) {
            this.paintGroup = paintGroup;
            this.brushSize = brushSize;
            paintGroup.setStyle(Paint.Style.STROKE);
            assignPath();
        }


        private static Path createPath(float val, float val2) {
            Path p = new Path();
            p.moveTo(-1 -val2, val);
            p.lineTo(val,val + val2);
            p.lineTo(val,-val);
            p.lineTo(-val2,-val - val2);
            p.close();
            return p;
        }


        private void assignPath(){
            float jaggedVal1 = paintGroup.getLineWidth() / 4;
            float  jaggedVal2 = jaggedVal1 /2;
            pathDashPathEffect = new PathDashPathEffect(createPath(jaggedVal1, jaggedVal2), 12, 5, PathDashPathEffect.Style.ROTATE);
            paintGroup.setPathEffect(pathDashPathEffect);
        }


        void onDrawAfterSettingsChanged(){
            assignPath();
        }

}
