package com.jacstuff.sketchy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{//} implements {// View.OnTouchListener {


    //private DrawSurface drawSurface;
    //private StateManager stateManager;
    private int width;
    private int height;

        private PaintView paintView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            paintView = findViewById(R.id.paintView);
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            paintView.init(metrics);
            Button saveButton = findViewById(R.id.saveButton);
            saveButton.setOnClickListener(this);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater menuInflater = getMenuInflater();
            //menuInflater.inflate(R.menu.main, menu);
            return super.onCreateOptionsMenu(menu);
        }

        public void onClick(View v){
            if(v.getId() == R.id.saveButton){
                saveImage();
            }
        }

        private void saveImage(){

            Log.i("Main", "Save button clicked!");
            String dirPath = Environment.getExternalStorageDirectory().getPath() + "/Sketches/";
            String path = dirPath + "sketch.jpg";
            createSaveDirIfDoesntExist();
            try{

                FileOutputStream fos = new FileOutputStream(path, false);
                Bitmap bitmap = paintView.getBitmap();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            }catch ( IOException e){
                Log.i("Main", e.getMessage());
            }


        }


    private void createSaveDirIfDoesntExist(){
        String path = Environment.getExternalStorageDirectory() + "/Sketches";
        File folder = new File(path);
        if(!folder.exists()){
            boolean result = folder.mkdirs();
            Log.i("Main", "Attempted to make Sketches dir, result : "+ result);
        }
    }


    private void deriveScreenDimensions(){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        Log.i("MainActivity", "derived dimensions : width : " + width + " height: " + height);
    }


}
