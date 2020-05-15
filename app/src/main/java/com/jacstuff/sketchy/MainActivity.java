package com.jacstuff.sketchy;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{//} implements {// View.OnTouchListener {

    private int screenWidth;
    private int screenHeight;
    private SeekBar seekBar;
    private PaintView paintView;
    private List<Integer> styleButtonIds = Arrays.asList(R.id.brokenOutlineStyleButton, R.id.fillStyleButton, R.id.outlineStyleButton);
    private List<Integer> shapeButtonIds = Arrays.asList(R.id.squareShapeButton, R.id.circleShapeButton);
    private LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120, 120);
    private HorizontalScrollView shadeScrollLayout;
    private Map<String, LinearLayout> shadeLayoutMap;
    private Map<String, Color> colors;
    Map<Integer, Integer> colorButtonMap = new HashMap<>();
    private Map<Integer, Procedure> paintActionsMap;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            paintView = findViewById(R.id.paintView);
            PaintViewSingleton paintViewSingleton = PaintViewSingleton.getInstance();
            paintViewSingleton.setPaintView(paintView);
            seekBar = findViewById(R.id.seekBar);
            deriveScreenDimensions();
            paintView.init(screenWidth, (screenHeight/2));
            setupActionbar();

            setupStyleButtons();
            setupShapeButtons();
            setupButtonListeners();
            setupPaintActionsMap();
            setupDefaultSelections();
            setupBrushSizeSeekBar();
            setupShadeButtons();
        }


        private void setupShadeButtons(){
            shadeScrollLayout = findViewById(R.id.colorShadeScrollView);
            shadeLayoutMap = new HashMap<>();
            assignColors();

            for(String key : colors.keySet()){
                Color currentColor = colors.get(key);
                addColorButton(currentColor, key);
                LinearLayout shadeLayout = new LinearLayout(this);

                for(int i = 0; i < 12; i++){
                    if(currentColor == null){
                        continue;
                    }
                    currentColor = createIncrementedColor(currentColor);
                    String text = key.charAt(0) + " " + i;
                    addShadeButton(shadeLayout, currentColor, text);
                }
                shadeLayoutMap.put(key, shadeLayout);
            }
        }


        private void assignColors(){
            colors = new HashMap<>();
            addColor( "blue", Color.BLUE);
            addColor( "red", Color.RED);
            addColor( "yellow", Color.YELLOW);
            addColor( "gray", Color.GRAY);
            addColor( "black", Color.BLACK);
            addColor( "white", Color.WHITE);
            addColor( "green", Color.GREEN);
            addColor( "magenta", Color.MAGENTA);
            addColor( "cyan", Color.CYAN);
        }


        private void addColor(String key, int colorCode){
            colors.put(key, Color.valueOf(colorCode));
        }


        private void addColorButton(Color currentColor, String key){

            LinearLayout colorButtonGroupLayout = findViewById(R.id.colorButtonGroup);
            Button button = new Button(this);
            if(currentColor == null){
                return;
            }
            int currentArgb = currentColor.toArgb();
            button.setBackgroundColor(currentArgb);
            button.setTag(key);
            button.setTag(R.string.tag_button_type, R.string.button_type_color);
            button.setOnClickListener(this);
            button.setLayoutParams(layoutParams);
            colorButtonGroupLayout.addView(button);
        }

        private void addShadeButton(LinearLayout layout, Color color, String text){
            Button button = new Button(this);
            button.setTag(R.string.tag_button_type, R.string.button_type_shade);
            button.setTag(R.string.tag_button_color, color);
            button.setLayoutParams(layoutParams);
            button.setBackgroundColor(color.toArgb());
            button.setText(text);
            button.setOnClickListener(this);
            layout.addView(button);
        }


        private Color createIncrementedColor(Color currentColor){
           float r = incIfWithinLimit(currentColor.red());
           float g = incIfWithinLimit(currentColor.green());
           float b = incIfWithinLimit(currentColor.blue());
           return Color.valueOf(r,g,b);
        }


        private void handleColorButtonClicks(View v){
            Object tag = v.getTag(R.string.tag_button_type);
            if(tag != null) {
                if ((int)tag == (int) R.string.button_type_color) {
                    String key = (String) v.getTag();
                    LinearLayout shadeLayout = shadeLayoutMap.get(key);
                    if (shadeLayout != null) {
                        shadeScrollLayout.removeAllViews();
                        shadeScrollLayout.addView(shadeLayout);
                    }
                }
                else if((int)tag == (int)R.string.button_type_shade){
                    Color color = (Color)v.getTag(R.string.tag_button_color);
                    if(color == null){
                        return;
                    }
                    paintView.setCurrentColor(color.toArgb());
                }
            }
        }


        private float incIfWithinLimit(float currentValue) {
            currentValue += 0.08f;
            return Math.min(1.0f, currentValue);
        }


        private void setupBrushSizeSeekBar(){
            SeekBar seekBar = findViewById(R.id.seekBar);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                   paintView.setBrushSize(seekBar.getProgress());
                }
            });
        }


        private void setupDefaultSelections(){
            paintView.setBrushSize(seekBar.getProgress());
            switchSelection(R.id.fillStyleButton, styleButtonIds);
            paintView.setStyleToFill();
            switchSelection(R.id.circleShapeButton, shapeButtonIds);
            paintView.setBrushShape(BrushShape.CIRCLE);
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

                    Intent intent = new Intent(this, SaveSketchActivity.class);
                    startActivity(intent);
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


        public void onClick(View v){
            handleColorButtonClicks(v);
            int viewId = v.getId();

            Procedure procedure = paintActionsMap.get(viewId);
            if(procedure != null){
                procedure.execute();
            }
            switchSelection(v.getId(), styleButtonIds);
            switchSelection(v.getId(), shapeButtonIds);
            //switchSelection(v.getId(), colorButtonIds);



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


    private void deriveScreenDimensions(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
    }


}
