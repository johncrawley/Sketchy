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
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.jacstuff.sketchy.state.StateManager;
import com.jacstuff.sketchy.state.StateManagerImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            String path = Environment.getExternalStorageDirectory().getPath() + "/Sketches/sketch.jpg";
            Log.i("Main", "save path:" + path);
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

class DrawView extends View {



    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private List<TouchPoint> touchPointList;
    private int radius = 12;
    private Bitmap bitmap;
    private Bitmap b2;
    private Context context;
    private String savePath;

    public DrawView(Context context, String savePath){
        super(context);
        setDrawingCacheEnabled(true);
        paint.setColor(Color.RED);
        touchPointList = new ArrayList<>();
        this.savePath = savePath;
       // bitmap = getBitmapFromView();
    }

    public void addTouchPoint(float x, float y){
        touchPointList.add(new TouchPoint(x,y, false));

    }

    @Override
    public void invalidate(){
        super.invalidate();
    }

    //@Override
    public void onDraw2(Canvas canvas){

        List<TouchPoint> touchPoints = new ArrayList<>(touchPointList);
        //if(bitmap != null) {
        //    canvas.drawBitmap(bitmap, 0, 0, paint);
        //}
        for(TouchPoint tp : touchPoints){
            canvas.drawCircle(tp.getX(), tp.getY(), radius, paint);

        }
        bitmap = getBitmapFromView();
        touchPointList.clear();
        Log.i("Main", "exiting onDraw()");

    }


    @Override
    protected void onDraw(Canvas c) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        List<TouchPoint> touchPoints = new ArrayList<>(touchPointList);

        if(touchPoints.size() == 0){
            c.drawCircle(50, 50, 30, paint);
        }
        b2 = getDrawingCache();
        if(b2 != null) {
            Log.i("DrawView onDraw()", "bitmap2 is not null, drawing cache bitmap to canvas");
            c.drawBitmap(b2, 0, 0, paint);
        }


        for(TouchPoint tp : touchPoints){
            paint.setColor(Color.BLUE);
            c.drawCircle(tp.getX(), tp.getY(), radius, paint);

        }
        c.drawCircle(250, 250, 50, paint);
        b2 = getDrawingCache();
       // bitmap = getBitmapFromView();
        touchPointList.clear();
        try {
           //getDrawingCache().compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(new File(savePath + "/sketch0.jpg")));
        } catch (Exception e) {
            Log.e("Error--------->", e.toString());
        }
        super.onDraw(c);
    }



    public Bitmap getBitmapFromView() {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(300, 300,Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = this.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        this.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    @Override
    public boolean performClick(){
        super.performClick();
        return true;
    }
}
