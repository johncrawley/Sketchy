package com.jacstuff.sketchy;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private int screenWidth;
    private int screenHeight;
    private SeekBar seekBar;
    private PaintView paintView;
    private List<Integer> styleButtonIds = Arrays.asList(R.id.brokenOutlineStyleButton, R.id.fillStyleButton, R.id.outlineStyleButton);
    private List<Integer> shapeButtonIds = Arrays.asList(R.id.squareShapeButton, R.id.circleShapeButton);
    private HorizontalScrollView shadesScrollView;
    private Map<Integer, Procedure> paintActionsMap;
    final int SAVE_FILE_ACTIVITY_CODE = 101;
    private ImageSaver imageSaver;
    private LinearLayout colorButtonGroupLayout;
    private ButtonLayoutParams buttonLayoutParams = new ButtonLayoutParams(120, 120, 15);
    ButtonClickHandler buttonClickHandler;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            assignViews();
            imageSaver = new ImageSaver(this);
            PaintViewSingleton paintViewSingleton = PaintViewSingleton.getInstance();
            paintViewSingleton.setPaintView(paintView);
            setupActionbar();
            initPaintView();
            setupButtonListeners();
            setupPaintActionsMap();
            setupDefaultSelections();
            setupBrushSizeSeekBar();
            setupButtonClickHandler();
            setupColorAndShadeButtons();
        }


        private void setupButtonClickHandler(){
            buttonClickHandler = new ButtonClickHandler(paintView, buttonLayoutParams, shadesScrollView);
        }


        private void assignViews(){
            paintView = findViewById(R.id.paintView);
            shadesScrollView =  findViewById(R.id.colorShadeScrollView);
            seekBar = findViewById(R.id.seekBar);
            colorButtonGroupLayout = findViewById(R.id.colorButtonGroup);
        }


        void setupColorAndShadeButtons(){
            Map<String, Color> colors = ColorCreator.generate();
            buttonClickHandler.setColors(colors);
            ColorButtonLayoutPopulator layoutPopulator = new ColorButtonLayoutPopulator(this, buttonLayoutParams, colors);
            buttonClickHandler.setMultiColorShades(layoutPopulator.getMultiColorShades());
            layoutPopulator.addColorButtonLayoutsTo(colorButtonGroupLayout);
            buttonClickHandler.setShadesLayoutMap(layoutPopulator.getShadeLayoutsMap());
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
            paintView.setCurrentColor(Color.BLACK);
        }


        private int getDimension(int dimensionCode){
            return (int) getResources().getDimension(dimensionCode);
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
            setOnClickListenerFor(shapeButtonIds);
            setOnClickListenerFor(styleButtonIds);
            findViewById(R.id.seekBar).setOnClickListener(this);
        }


        private void setOnClickListenerFor(List<Integer> ids){
            for(int id: ids){
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
                    startSaveDocumentActivity();
                    return true;

                case R.id.action_about:
                    startAboutActivity();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }


        private void startAboutActivity(){
            Intent intent = new Intent(this, AboutDialogActivity.class);
            startActivity(intent);
        }


        private void startSaveDocumentActivity(){
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_TITLE, "sketch");
            startActivityForResult(intent, SAVE_FILE_ACTIVITY_CODE);
        }


        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == SAVE_FILE_ACTIVITY_CODE && resultCode == Activity.RESULT_OK) {
                imageSaver.saveImageToFile(data, paintView);
            }
        }


        public void onClick(View v){
            buttonClickHandler.handleColorButtonClicks(v);
            int viewId = v.getId();

            Procedure procedure = paintActionsMap.get(viewId);
            if(procedure != null){
                procedure.execute();
            }
            switchSelection(viewId, styleButtonIds);
            switchSelection(viewId, shapeButtonIds);
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
