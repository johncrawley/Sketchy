package com.jacstuff.sketchy;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


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
    Context context;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            paintView = findViewById(R.id.paintView);
            context = this;
            PaintViewSingleton paintViewSingleton = PaintViewSingleton.getInstance();
            paintViewSingleton.setPaintView(paintView);
            seekBar = findViewById(R.id.seekBar);
            setupActionbar();
            initPaintView();
            setupStyleButtons();
            setupShapeButtons();
            setupButtonListeners();
            setupPaintActionsMap();
            setupDefaultSelections();
            setupBrushSizeSeekBar();
            setupShadeButtons();
        }

        private void initPaintView(){
            deriveScreenDimensions();
            int paintViewLayoutMargin = getDimension(R.dimen.paint_view_layout_margin);
            int paintViewMargin = getDimension(R.dimen.paint_view_margin);
            int paintViewLayoutPadding = getDimension(R.dimen.paint_view_layout_padding);
            int actionBarHeight = getDimension(R.dimen.action_bar_height);
            int totalMargin = (paintViewMargin + paintViewLayoutMargin + paintViewLayoutPadding) * 2;
            int paintViewWidth = screenWidth - totalMargin;
            int paintViewHeight = ((screenHeight-actionBarHeight) /2) - ( (paintViewMargin + paintViewLayoutMargin) * 2 );
            paintView.init(paintViewWidth, paintViewHeight);
        }


        private int getDimension(int dimensionCode){
            return (int) getResources().getDimension(dimensionCode);
        }


        private void setupShadeButtons(){
            shadeScrollLayout = findViewById(R.id.colorShadeScrollView);
            shadeLayoutMap = new HashMap<>();
            assignColors();
            Color previousColor = null;
            for(String key : colors.keySet()){
                Color currentColor = colors.get(key);
                addColorButton(currentColor, key);
                addShadesToLayoutMap(key, currentColor);
            }
        }


        private void addShadesToLayoutMap( String key, Color currentColor){
            LinearLayout shadeLayout = new LinearLayout(this);
            List<Color> darkShades = createShades(this::createDecrementedColor, currentColor);

            Collections.reverse(darkShades);
            darkShades.remove(darkShades.size()-1);
            List<Color> lightShades= createShades(this::createIncrementedColor, currentColor);
            darkShades.addAll(lightShades);
            int index = 1;
            for(Color color : darkShades) {
                String text = " " + index;
                addShadeButton(shadeLayout, color, text);
                index++;
            }
            shadeLayoutMap.put(key, shadeLayout);

        }


        private List<Color> createShades(Function<Color, Color> colorCreator, Color baseColor){

            final int NUMBER_OF_SHADES_PER_COLOR = 10;
            List<Color> shades = new ArrayList<>();
            Color currentColor = baseColor;
            Color previousColor = null;
            for(int i = 0; i < NUMBER_OF_SHADES_PER_COLOR; i++){
                if(currentColor == null || currentColor.equals(previousColor)){
                    break;
                }
                previousColor = currentColor;
                shades.add(currentColor);
                currentColor = colorCreator.apply(currentColor);
            }
            return shades;
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
            addColor("light_blue", Color.argb(255,0,130,255));
            addColor("orange", Color.argb(255,255,106,0));
            addColor("purple", Color.argb(255,178,0,255));
            addColor("brown", Color.argb(255,127,51,0));
            addColor("lime", Color.argb(255,0,255,144));
            addColor("gold", Color.argb(255,255,215,0));
            addColor("peach", Color.argb(255,255,229,180));
            addColor("beige", Color.argb(255,245,245,220));
            addColor("teal", Color.argb(255,0,128,128));
            addColor("olive", Color.argb(255,128,128,0));
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
            button.setTextAppearance(R.style.ShadeButtonText);
            button.setOnClickListener(this);
            layout.addView(button);
        }

        private final float SHADE_INCREMENT = 0.08f;

    private Color modifyColor(Function<Float, Float> valueFunction, Color currentColor){
        float r = valueFunction.apply(currentColor.red());
        float g = valueFunction.apply(currentColor.green());
        float b = valueFunction.apply(currentColor.blue());
        return Color.valueOf(r,g,b);
    }
    private Color createIncrementedColor(Color currentColor){
        return modifyColor(this::incIfWithinLimit, currentColor);
    }

    private Color createDecrementedColor(Color currentColor){
        return modifyColor(this::decIfAboveZero, currentColor);
    }

    private float incIfWithinLimit(float currentValue) {
        currentValue += SHADE_INCREMENT;
        return Math.min(1.0f, currentValue);
    }

    private float decIfAboveZero(float currentValue) {
        currentValue -= SHADE_INCREMENT;
        return Math.max(0.0f, currentValue);
    }



        private final int NO_TAG_FOUND = -1;

        private void handleColorButtonClicks(View view){
            int tag = getButtonTypeTag(view);
            if(tag == NO_TAG_FOUND){
                return;
            }
            if (tag == R.string.button_type_color) {
                handleMainColorButtonClick(view);
            }
            else if(tag ==  R.string.button_type_shade){
                handleShadeButtonClick(view);
            }
        }


        private int getButtonTypeTag(View view){
            Object tagObj = view.getTag(R.string.tag_button_type);
            if(tagObj == null) {
                return NO_TAG_FOUND;
            }
            return (int)tagObj;
        }


        private void handleMainColorButtonClick(View view){
            String key = (String) view.getTag();
            LinearLayout shadeLayout = shadeLayoutMap.get(key);
            if (shadeLayout != null) {
                shadeScrollLayout.removeAllViews();
                shadeScrollLayout.addView(shadeLayout);
            }
        }

        private void handleShadeButtonClick(View view){
            Color color = (Color)view.getTag(R.string.tag_button_color);
            if(color == null){
                return;
            }
            paintView.setCurrentColor(color.toArgb());

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

        final int SAVE_FILE_ACTIVITY_CODE = 101;

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_save:

                    //Intent intent = new Intent(this, SaveSketchActivity.class);
                   // startActivity(intent);

                    // save dialog test

                    Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/jpeg"); //not needed, but maybe usefull
                    intent.putExtra(Intent.EXTRA_TITLE, "sketch"); //not needed, but maybe usefull
                    startActivityForResult(intent, SAVE_FILE_ACTIVITY_CODE);
                    return true;
                default:
                    // If we got here, the user's action was not recognized.
                    // Invoke the superclass to handle it.
                    return super.onOptionsItemSelected(item);

            }
        }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SAVE_FILE_ACTIVITY_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();

            //just as an example, I am writing a String to the Uri I received from the user:

            try {
                OutputStream output = context.getContentResolver().openOutputStream(uri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                paintView.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                output.write(byteArray);
                output.flush();
                output.close();

                Toast.makeText(context, "SKETCH SAVED", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(context, "ERROR, UNABLE TO SAVE FILE", Toast.LENGTH_SHORT).show();
            }
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
