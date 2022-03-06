package com.jacstuff.sketchy.brushes.styles;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;

import com.jacstuff.sketchy.paintview.PaintGroup;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class JaggedStyle  extends AbstractStyle implements Style {

    private PathDashPathEffect pathDashPathEffect;
    private PaintGroup paintGroup;
    private final MainViewModel viewModel;
    private final int jaggedAdvance = 12;


    public JaggedStyle( PaintGroup paintGroup, MainViewModel viewModel){
        this.paintGroup = paintGroup;
        this.viewModel = viewModel;
        pathDashPathEffect = createJaggedPath(20);
    }


    @Override
    public void init(PaintGroup paintGroup, int brushSize ) {
        this.paintGroup = paintGroup;
        this.brushSize = brushSize;
        paintGroup.setStyle(Paint.Style.STROKE);
        assignPath();
    }


    private void assignPath(){
        pathDashPathEffect = createJaggedPath(paintGroup.getLineWidth() / 4);
        paintGroup.setPathEffect(pathDashPathEffect);
    }


    private PathDashPathEffect createJaggedPath(float unitLength){
        return new PathDashPathEffect(createPath(unitLength), jaggedAdvance, 5, PathDashPathEffect.Style.ROTATE);
    }


    private Path createPath(float twoUnits) {
        Path p = new Path();
        float oneUnit = twoUnits /2;
        float smallHeight = twoUnits + 0.1f;
        float largeHeight = (oneUnit * 3) + viewModel.jaggedStyleExtraHeight;

        p.moveTo(-1 -oneUnit, smallHeight);
        p.lineTo(twoUnits,largeHeight );
        p.lineTo(twoUnits,-smallHeight);
        p.lineTo(-oneUnit,-largeHeight);
        p.close();
        return p;
    }


    void onDrawAfterSettingsChanged(){
            assignPath();
        }

}
