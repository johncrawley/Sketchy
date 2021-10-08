package com.jacstuff.sketchy.utils;

import android.content.Context;
import android.widget.Toast;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;

public class Toaster {
    private Toast toast;
    private final Context context;

    public Toaster(Context context){
        this.context = context;
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
