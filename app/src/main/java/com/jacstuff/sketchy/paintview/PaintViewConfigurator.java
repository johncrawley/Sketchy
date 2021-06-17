package com.jacstuff.sketchy.paintview;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.MainViewModel;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.model.TextControlsDto;
import com.jacstuff.sketchy.ui.SettingsPopup;

public class PaintViewConfigurator {

    private int screenWidth, screenHeight;
    private final Context context;

    private int actionBarHeight;
    private int totalMargin, totalPaintViewMargins;
    private int layoutHeight;

    public PaintViewConfigurator(MainActivity mainActivity, int layoutWidth, int layoutHeight){
        this.context = mainActivity;
        deriveScreenDimensions(mainActivity.getWindowManager());
        getDimensionValues();
        this.layoutHeight = layoutHeight;
    }

    private void getDimensionValues(){
        actionBarHeight = getDimension(R.dimen.action_bar_height);
        int paintViewLayoutMargin = getDimension(R.dimen.paint_view_layout_margin);
        int paintViewMargin = getDimension(R.dimen.paint_view_margin);
        int paintViewLayoutPadding = getDimension(R.dimen.paint_view_layout_padding);



        totalMargin = (paintViewMargin + paintViewLayoutMargin + paintViewLayoutPadding) * 2;
        totalPaintViewMargins = ( paintViewMargin + paintViewLayoutMargin) * 2;
    }


    public void configure(MainViewModel viewModel, PaintView paintView, SettingsPopup settingsPopup, TextControlsDto textControlsDto){
        int paintViewWidth, paintViewHeight;
        paintViewHeight = layoutHeight;
        int colorButtonHeight = getDimension(R.dimen.color_button_height);
        int landscapePaintViewWidth = screenWidth - ((colorButtonHeight * 3) + totalPaintViewMargins + actionBarHeight);
        if(isInLandscapeMode()){
            paintViewHeight = screenHeight - (totalMargin);
            paintViewWidth = landscapePaintViewWidth;
        } else {
            paintViewWidth = screenWidth - totalMargin;
        }

        paintView.init(viewModel, paintViewWidth, paintViewHeight,settingsPopup, textControlsDto);
    }


    private boolean isInLandscapeMode(){
        int orientation = context.getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_LANDSCAPE;
    }


    private int getDimension(int dimensionCode){
        return (int) context.getResources().getDimension(dimensionCode);
    }


    private void deriveScreenDimensions(WindowManager windowManager){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
    }


}

