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
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.jacstuff.sketchy.controls.brushSize.BrushSizeConfig;
import com.jacstuff.sketchy.controls.colorbuttons.ButtonClickHandler;
import com.jacstuff.sketchy.controls.colorbuttons.ButtonLayoutParams;
import com.jacstuff.sketchy.controls.colorbuttons.ColorButtonLayoutPopulator;
import com.jacstuff.sketchy.controls.colorbuttons.ColorCreator;
import com.jacstuff.sketchy.controls.settingsbuttons.SettingsButtonsConfigurator;

import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SeekBar seekBar;
    private PaintView paintView;
    private HorizontalScrollView shadesScrollView;
    final int SAVE_FILE_ACTIVITY_CODE = 101;
    private ImageSaver imageSaver;
    private LinearLayout colorButtonGroupLayout;
    private ButtonLayoutParams buttonLayoutParams = new ButtonLayoutParams(120, 120, 15);
    private ButtonClickHandler buttonClickHandler;
    private ColorButtonLayoutPopulator layoutPopulator;
    private BrushSizeConfig brushSizeConfig;

    private SettingsButtonsConfigurator settingsButtonsConfigurator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        initImageSaver();
        setupActionbar();
        setupBrushSizeSeekBar();
        configurePaintView();
        setupSettingsButtons();
        setupButtonListeners();
        setupDefaultSelections();
        setupButtonClickHandler();
        setupColorAndShadeButtons();
        assignRecentButtons();
    }


    private void initImageSaver(){
        imageSaver = new ImageSaver(this);
    }

    private void setupSettingsButtons(){
        settingsButtonsConfigurator = new SettingsButtonsConfigurator(this);
        settingsButtonsConfigurator.setupShapeAndStyleButtons(paintView);
    }


    private void configurePaintView(){
        paintView = findViewById(R.id.paintView);
        PaintViewConfigurator paintViewConfigurator = new PaintViewConfigurator(this, this.getWindowManager());
        paintViewConfigurator.configure(paintView, brushSizeConfig);
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
        selectRecentColorButton(paintViewSingleton);
        selectRecentShadeButton(paintViewSingleton);
        selectRecentShapeAndStyle(paintViewSingleton);
    }


    private void selectRecentColorButton(PaintViewSingleton pvs ){
        Button mostRecentColorButton = layoutPopulator.getButton(pvs.getMostRecentColor());
        clickButtonIfNotNull(mostRecentColorButton);
    }


    private void selectRecentShadeButton(PaintViewSingleton pvs ){
        if(pvs.wasMostRecentClickAShade()){
            Button mostRecentShadeButton = layoutPopulator.getButton(pvs.getMostRecentShade());
            clickButtonIfNotNull(mostRecentShadeButton);
        }
    }


    private void selectRecentShapeAndStyle(PaintViewSingleton pvs){
        settingsButtonsConfigurator.clickOnView(pvs.getMostRecentBrushShapeId());
        settingsButtonsConfigurator.clickOnView(pvs.getMostRecentBrushStyleId());
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
        buttonClickHandler.setColorsMap(colors);
        layoutPopulator = new ColorButtonLayoutPopulator(this, buttonLayoutParams, colors);
        buttonClickHandler.setMultiColorShades(layoutPopulator.getMultiColorShades());
        layoutPopulator.addColorButtonLayoutsTo(colorButtonGroupLayout);
        buttonClickHandler.setShadeLayoutsMap(layoutPopulator.getShadeLayoutsMap());
        buttonClickHandler.handleColorButtonClicks(colorButtonGroupLayout.findViewWithTag(R.string.tag_button_default_color));
    }


    private void setupBrushSizeSeekBar(){
        SeekBar seekBar = findViewById(R.id.seekBar);
        brushSizeConfig = new BrushSizeConfig(MainActivity.this, seekBar, BrushShape.CIRCLE, BrushStyle.FILL);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                if(paintView != null){
                    paintView.setBrushSize(seekBar.getProgress());
                }
            }
        });
    }


    private void setupDefaultSelections(){
        paintView.setBrushSize(seekBar.getProgress());
    }


    private void setupButtonListeners(){
        findViewById(R.id.seekBar).setOnClickListener(this);
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
        settingsButtonsConfigurator.handleButtonClick(viewId);
    }

}
