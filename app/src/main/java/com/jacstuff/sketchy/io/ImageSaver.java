package com.jacstuff.sketchy.io;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.paintview.PaintView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageSaver {


    private final Context context;

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


    public void loadImage(Intent data, PaintView paintView){
        Uri uri = data.getData();
        if(uri == null){
            return;
        }
        try{
            InputStream input = context.getContentResolver().openInputStream(uri);
            if(input == null){
                showLoadErrorToast();
                return;
            }
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            paintView.loadBitmap(bitmap);
        }catch (IOException e){
            showLoadErrorToast();
        }
    }


    private void showLoadErrorToast(){
        Toast.makeText(context, context.getString(R.string.toast_open_file_error), Toast.LENGTH_SHORT).show();
    }


    private void showSaveErrorToast(){
        Toast.makeText(context, context.getString(R.string.toast_save_sketch_error), Toast.LENGTH_SHORT).show();
    }


    private void showSaveSuccessToast(){
        Toast.makeText(context, context.getString(R.string.toast_save_sketch_success), Toast.LENGTH_SHORT).show();
    }


    private byte[] getImageByteArrayFrom(PaintView paintView){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        paintView.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

}
