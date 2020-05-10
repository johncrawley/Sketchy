package com.jacstuff.sketchy;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SettingsView, View.OnClickListener{//} implements {// View.OnTouchListener {


    //private DrawSurface drawSurface;
    //private StateManager stateManager;
    private int screenWidth;
    private int screenHeight;
    private SeekBar seekBar;
    private PaintView paintView;
    private List<Integer> styleButtonIds = Arrays.asList(R.id.brokenOutlineStyleButton, R.id.fillStyleButton, R.id.outlineStyleButton);
    private List<Integer> shapeButtonIds = Arrays.asList(R.id.squareShapeButton, R.id.circleShapeButton);

    Map<Integer, Integer> colorButtonMap = new HashMap<>();
    private Map<Integer, Procedure> paintActionsMap;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            paintView = findViewById(R.id.paintView);
            seekBar = findViewById(R.id.seekBar);
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int viewHeight = paintView.getHeight();
            paintView.init(metrics, viewHeight,this);
            deriveScreenDimensions();
            paintView.setMinimumHeight(screenHeight / 2);
            setupActionbar();

            setupButtonList();
            setupStyleButtons();
            setupShapeButtons();
            setupButtonListeners();
            setupPaintActionsMap();
            setupDefaultSelections();
        }

        private void setupDefaultSelections(){
            switchSelection(R.id.fillStyleButton, styleButtonIds);
            paintView.setStyleToFill();
            switchSelection(R.id.circleShapeButton, shapeButtonIds);
            paintView.setBrushShape(BrushShape.CIRCLE);
            setCurrentColor(R.id.blackButton);
            switchSelection(R.id.blackButton, colorButtonIds);
        }

        private void setupButtonListeners(){

            List<Integer> widgetIds = Arrays.asList(
                    R.id.brokenOutlineStyleButton,
                    R.id.fillStyleButton,
                    R.id.outlineStyleButton,
                    R.id.squareShapeButton,
                    R.id.circleShapeButton,
                    R.id.seekBar);

            for(int id: widgetIds){
                findViewById(id).setOnClickListener(this);
            }


        }

        private void setupPaintActionsMap(){

            paintActionsMap = new HashMap<>();

            paintActionsMap.put(R.id.brokenOutlineStyleButton, () -> paintView.setStyleToBrokenOutline());
            paintActionsMap.put(R.id.fillStyleButton, () -> paintView.setStyleToFill());
            paintActionsMap.put(R.id.outlineStyleButton, () -> paintView.setStyleToOutline());
            paintActionsMap.put(R.id.squareShapeButton, () -> paintView.setBrushShape(BrushShape.SQUARE));
            paintActionsMap.put(R.id.circleShapeButton, () -> paintView.setBrushShape(BrushShape.CIRCLE));

        }

        private void setupActionbar(){
            setSupportActionBar(findViewById(R.id.toolbar));
            getSupportActionBar();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuitems, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveImage();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    private void setupShapeButtons(){
            for(int id: shapeButtonIds){
                findViewById(id).setOnClickListener(this);
            }

        }


        private void setupStyleButtons(){

            for(int id: styleButtonIds){

                findViewById(id).setOnClickListener(this);
            }
        }

        private List<Integer> colorButtonIds; // used to iterate through for selecting & deselecting buttons

        private void setupButtonList() {
            colorButtonIds = new ArrayList<>();

            colorButtonMap.put(R.id.blueButton, Color.BLUE);
            colorButtonMap.put(R.id.redButton, Color.RED);
            colorButtonMap.put(R.id.greenButton, Color.GREEN);
            colorButtonMap.put(R.id.grayButton, Color.GRAY);
            colorButtonMap.put(R.id.yellowButton, Color.YELLOW);
            colorButtonMap.put(R.id.orangeButton, Color.argb(255,255,106,0));
            colorButtonMap.put(R.id.purpleButton, Color.argb(255,127,0,55));
            colorButtonMap.put(R.id.blackButton, Color.BLACK);
            colorButtonMap.put(R.id.whiteButton, Color.WHITE);
            colorButtonMap.put(R.id.lightBlueButton, Color.argb(255,0,148,255));




            for(int key:  colorButtonMap.keySet()){
                colorButtonIds.add(key);
                findViewById(key).setOnClickListener(this);
            }
        }


        public void onClick(View v){
            int viewId = v.getId();

            setCurrentColor(viewId);

            Procedure procedure = paintActionsMap.get(viewId);
            if(procedure != null){
                procedure.execute();
            }
            switchSelection(v.getId(), styleButtonIds);
            switchSelection(v.getId(), shapeButtonIds);
            switchSelection(v.getId(), colorButtonIds);

        }

        private void setCurrentColor(int viewId){
            if(colorButtonMap.containsKey(viewId)){
                Integer color = colorButtonMap.get(viewId);
                if(color != null){
                    paintView.setCurrentColor(color);
                }
            }
        }

        private void switchSelection(int viewId, List<Integer> buttons){
            for(int buttonId : buttons){
                if(viewId == buttonId){
                    switchSelectionToButton(buttonId, buttons);
                    break;
                }
            }
        }

    private void switchSelectionToButton(int buttonId, List<Integer> buttonList){
        selectButton(buttonId);
        deselectOtherButtons(buttonId, buttonList);
    }


    private void selectButton(int buttonId){
        ImageButton styleButton = findViewById(buttonId);
        styleButton.setSelected(true);
        styleButton.setBackgroundColor(Color.DKGRAY);
    }


    private void deselectOtherButtons(int selectedButtonId, List<Integer> buttonList){
        for(int buttonId : buttonList){
            if(buttonId == selectedButtonId){
                continue;
            }
            deselectButton(buttonId);
        }
    }

    private void deselectButton(int buttonId){
        ImageButton button = findViewById(buttonId);
        button.setSelected(false);
        button.setBackgroundColor(Color.LTGRAY);
    }


        public int getBrushWidth(){
            return seekBar.getProgress();
        }

        /*
        @Override
        protected void onPause(){
            super.onPause();
            SharedPreferences settings = getSharedPreferences(IMAGE_KEY,0);
            SharedPreferences.Editor editor = settings.edit();


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            paintView.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            //bmp.recycle();

            // Necessary to clear first if we save preferences onPause.
            editor.clear();

           editor.putString("Metric", paintView.getBitmap().);
           editor.pu
            editor.commit();
        }
        */

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
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        Log.i("MainActivity", "derived dimensions : width : " + screenWidth + " height: " + screenHeight);
    }


}
