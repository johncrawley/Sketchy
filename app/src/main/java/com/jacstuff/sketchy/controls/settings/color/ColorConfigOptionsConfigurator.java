package com.jacstuff.sketchy.controls.settings.color;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.controls.settings.AbstractButtonConfigurator;
import com.jacstuff.sketchy.controls.settings.ButtonsConfigurator;
import com.jacstuff.sketchy.paintview.PaintView;

public class ColorConfigOptionsConfigurator  extends AbstractButtonConfigurator<Void> implements ButtonsConfigurator<Void> {

    public ColorConfigOptionsConfigurator(MainActivity activity, PaintView paintView){
        super(activity, paintView);
    }


    @Override
    public void configure(){
        ColorTransparencySeekBar colorTransparencySeekBar = new ColorTransparencySeekBar(activity, paintView);
    }


    @Override
    public void handleClick(int viewId, Void actionType) {
        //do nothing
    }

}
