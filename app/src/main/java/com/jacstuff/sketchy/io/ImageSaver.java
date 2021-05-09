package com.jacstuff.sketchy.io;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import com.jacstuff.sketchy.paintview.PaintView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageSaver {


    private Context context;

    public ImageSaver(Context context){
        this.context = context;
    }

    public void saveImageToFile(Intent data, PaintView paintView){
        Uri uri = data.getData();
        if(uri == null){
            return;
        }
        try {
            OutputStream output = context.getContentResolver().openOutputStream(uri);
            if(output == null){
                showSaveErrorToast();
                return;
            }
            byte [] bytes = getImageByteArrayFrom(paintView);
            output.write(bytes);
            output.flush();
            output.close();
            showSaveSuccessToast();
        } catch (IOException e) {
            showSaveErrorToast();
        }
    }


    private void showSaveErrorToast(){
        Toast.makeText(context, "ERROR, UNABLE TO SAVE FILE", Toast.LENGTH_SHORT).show();
    }


    private void showSaveSuccessToast(){
        Toast.makeText(context, "SKETCH SAVED", Toast.LENGTH_SHORT).show();
    }


    private byte[] getImageByteArrayFrom(PaintView paintView){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        paintView.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

}
