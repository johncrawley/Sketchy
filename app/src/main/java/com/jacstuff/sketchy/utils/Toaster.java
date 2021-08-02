package com.jacstuff.sketchy.utils;

import android.content.Context;
import android.widget.Toast;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;

public class Toaster {
    private Toast colorPatternToast, toast;
    private Context context;

    public Toaster(Context context){
        this.context = context;
    }

    public void toastPattern(String msg){
        if(colorPatternToast != null){
            colorPatternToast.cancel();
        }
        colorPatternToast = Toast.makeText(context, context.getString(R.string.pattern_toast_prefix) +  msg, Toast.LENGTH_SHORT);
        colorPatternToast.show();
    }


    public void toast(int resId){
        String msg = context.getString(resId);
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
