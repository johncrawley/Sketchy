package com.jacstuff.sketchy.paintview;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.ui.SettingsPopup;

public class PaintViewConfigurator {

    private int screenWidth, screenHeight;
    private Context context;

    private int actionBarHeight;
    private int totalMargin, totalPaintViewMargins;

    private int layoutWidth, layoutHeight;

    public PaintViewConfigurator(Context context, WindowManager windowManager, int layoutWidth, int layoutHeight){
        this.context = context;
        deriveScreenDimensions(windowManager);
        getDimensionValues();
        this.layoutWidth = layoutWidth;
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


    public void configure(PaintView paintView, SettingsPopup settingsPopup){
        int paintViewWidth, paintViewHeight;
        paintViewHeight = layoutHeight;

        if(isInLandscapeMode()){
            paintViewHeight = screenHeight - (actionBarHeight + totalMargin);
            paintViewWidth = (screenWidth / 2) - totalPaintViewMargins;

        } else {
            paintViewWidth = screenWidth - totalMargin;
           // paintViewHeight = ((screenHeight-actionBarHeight) /2) - totalPaintViewMargins;
        }
        paintView.init(paintViewWidth, paintViewHeight,settingsPopup);
        paintView.setCurrentColor(Color.BLACK);
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

