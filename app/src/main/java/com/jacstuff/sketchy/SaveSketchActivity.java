package com.jacstuff.sketchy;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.graphics.Bitmap.CompressFormat.JPEG;
import static android.os.Environment.DIRECTORY_PICTURES;

public class SaveSketchActivity extends Activity implements View.OnClickListener {


    private String SKETCHES_DIR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.);
        setContentView(R.layout.activity_save_sketch);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        Log.i("saveSKetchActivity", "Entered onCreate()");

        SKETCHES_DIR = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).getPath() + "/Sketches/";
        findViewById(R.id.cancelButton).setOnClickListener(this);
        findViewById(R.id.saveButton).setOnClickListener(this);
    }



    public void onClick(View view){
        int id = view.getId();

        switch (id){
            case R.id.saveButton:
                saveImage();
                finish();
            default: finish();
        }

    }


    private void saveImage(){
        EditText editText = findViewById(R.id.filenameText);
        String filename = editText.getText().toString();
        String path = SKETCHES_DIR + filename + ".jpg";
        createSaveDirIfDoesntExist();
        try{

            FileOutputStream fos = new FileOutputStream(path, false);

            PaintViewSingleton paintViewSingleton = PaintViewSingleton.getInstance();
            PaintView paintView = paintViewSingleton.getPaintView();
            Bitmap bitmapToSave = paintView.getBitmap();
            bitmapToSave.compress(JPEG, 100, fos);
            fos.flush();
            fos.close();
        }catch ( IOException e){
            Log.i("Main", e.getMessage());
        }
    }


    private void createSaveDirIfDoesntExist(){
        File folder = new File(SKETCHES_DIR);
        if(!folder.exists()){
            boolean result = folder.mkdirs();
        }
    }


}
