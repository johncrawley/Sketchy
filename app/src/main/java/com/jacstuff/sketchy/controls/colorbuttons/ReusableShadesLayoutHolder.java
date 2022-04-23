package com.jacstuff.sketchy.controls.colorbuttons;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonUtils;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.List;

public class ReusableShadesLayoutHolder {

    private final ButtonUtils buttonUtils;
    private LinearLayout reusableShadesLayout;
    private final Context context;
    private final MainViewModel viewModel;

    public ReusableShadesLayoutHolder(MainActivity activity, ButtonUtils buttonUtils){
        this.context = activity;
        this.viewModel = activity.getViewModel();
        this.buttonUtils = buttonUtils;
    }


    public LinearLayout getLayout(){
        return reusableShadesLayout;
    }


    public void initReusableShadesLayout(ColorShadeCreator colorShadeCreator){
        List<Integer> shades = viewModel.buttonShadesStore.getShadesFor(Color.BLACK, colorShadeCreator);
        reusableShadesLayout = new LinearLayout(context);
        for(int shade: shades){
            LinearLayout buttonLayout = buttonUtils.createShadeButton(shade, ButtonType.SHADE);
            reusableShadesLayout.addView(buttonLayout);
        }
    }


    public void assignShadesToReusableShadesLayout(int color){
        List<Integer> shades = viewModel.buttonShadesStore.getShadesFor(color);
        for(int i=0; i< viewModel.numberOfSequenceShadesForButtons; i++){
            LinearLayout wrapperLayout = (LinearLayout) reusableShadesLayout.getChildAt(i);
            if(wrapperLayout == null){
                return;
            }
            Button button = (Button)wrapperLayout.getChildAt(0);
            if(shades.size() < i){
                continue;
            }
            int shade = shades.get(i);
            String key = buttonUtils.createColorKey(shade, ButtonType.SHADE);
            button.setBackgroundColor(shade);
            button.setTag(R.string.tag_button_key, key);
            button.setTag(R.string.tag_button_color, shade);
        }
    }

}
