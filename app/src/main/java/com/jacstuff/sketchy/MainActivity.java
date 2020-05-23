package com.jacstuff.sketchy;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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
    private ButtonClickHandler buttonClickHandler;
    private ColorButtonLayoutPopulator layoutPopulator;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            assignViews();
            initImageSaver();
            setupActionbar();
            configurePaintView();
            setupButtonListeners();
            setupPaintActionsMap();
            setupDefaultSelections();
            setupBrushSizeSeekBar();
            setupButtonClickHandler();
            setupColorAndShadeButtons();
            assignRecentButtons();
        }


        private void initImageSaver(){
            imageSaver = new ImageSaver(this);
        }


        private void configurePaintView(){
            paintView = findViewById(R.id.paintView);
            PaintViewConfigurator paintViewConfigurator = new PaintViewConfigurator(this, this.getWindowManager());
            paintViewConfigurator.configure(paintView);
            assignSavedBitmap();
        }


        private void assignSavedBitmap(){
            PaintViewSingleton paintViewSingleton = PaintViewSingleton.getInstance();
            Bitmap bitmap = paintViewSingleton.getBitmap();
            if(bitmap != null){
                paintView.setBitmap(bitmap);
            }
        }


        private void assignRecentButtons(){
            PaintViewSingleton paintViewSingleton = PaintViewSingleton.getInstance();
            Button mostRecentColorButton = layoutPopulator.getButton(paintViewSingleton.getMostRecentColor());
            clickButtonIfNotNull(mostRecentColorButton);
            if(paintViewSingleton.wasMostRecentClickAShade()){
                Button mostRecentShadeButton = layoutPopulator.getButton(paintViewSingleton.getMostRecentShade());
                clickButtonIfNotNull(mostRecentShadeButton);
            }
        }


        private void clickButtonIfNotNull(Button button){
            if(button == null){
                return;
            }
            buttonClickHandler.handleColorButtonClicks(button);
        }


        private void setupButtonClickHandler(){
            buttonClickHandler = new ButtonClickHandler(paintView, buttonLayoutParams, shadesScrollView);
        }


        private void assignViews(){
            shadesScrollView =  findViewById(R.id.colorShadeScrollView);
            seekBar = findViewById(R.id.seekBar);
            colorButtonGroupLayout = findViewById(R.id.colorButtonGroup);
        }


        void setupColorAndShadeButtons(){
            Map<String, Color> colors = ColorCreator.generate();
            buttonClickHandler.setColors(colors);
            layoutPopulator = new ColorButtonLayoutPopulator(this, buttonLayoutParams, colors);
            buttonClickHandler.setMultiColorShades(layoutPopulator.getMultiColorShades());
            layoutPopulator.addColorButtonLayoutsTo(colorButtonGroupLayout);
            buttonClickHandler.setShadeLayoutsMap(layoutPopulator.getShadeLayoutsMap());
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
        protected void onPause(){
            super.onPause();
            PaintViewSingleton paintViewSingleton = PaintViewSingleton.getInstance();
            paintViewSingleton.setBitmap(paintView.getBitmap());
            paintViewSingleton.setMostRecentColor(buttonClickHandler.getMostRecentButtonKey());
            paintViewSingleton.setMostRecentShade(buttonClickHandler.getMostRecentShadeKey());
            paintViewSingleton.setWasMostRecentClickAShade(buttonClickHandler.isMostRecentClickAShade());
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

}
