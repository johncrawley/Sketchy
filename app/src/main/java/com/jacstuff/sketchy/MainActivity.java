package com.jacstuff.sketchy;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jacstuff.sketchy.controls.colorbuttons.ColorButtonClickHandler;
import com.jacstuff.sketchy.controls.ButtonLayoutParams;
import com.jacstuff.sketchy.controls.colorbuttons.ColorButtonLayoutPopulator;
import com.jacstuff.sketchy.controls.colorbuttons.ColorCreator;
import com.jacstuff.sketchy.controls.seekbars.SeekBarConfigurator;
import com.jacstuff.sketchy.controls.settingsbuttons.SettingsButtonsConfigurator;
import com.jacstuff.sketchy.io.ImageSaver;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.PaintViewConfigurator;
import com.jacstuff.sketchy.settings.PaintViewSingleton;
import com.jacstuff.sketchy.settings.ResumedActionsHelper;
import com.jacstuff.sketchy.tasks.ColorAutoScroller;
import com.jacstuff.sketchy.ui.SettingsPopup;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private PaintView paintView;
    private HorizontalScrollView shadesScrollView;
    private final int SAVE_FILE_ACTIVITY_CODE = 101;
    private final int CLEAR_CANVAS_ACTIVITY_CODE = 102;
    private final int LOAD_FILE_ACTIVITY_CODE = 103;
    private ImageSaver imageSaver;
    private LinearLayout colorButtonGroupLayout;
    private ButtonLayoutParams colorButtonLayoutParams = new ButtonLayoutParams(120, 120, 15);
    private ButtonLayoutParams settingsButtonLayoutParams = new ButtonLayoutParams(120, 120, 15, 2);
    private ColorButtonClickHandler buttonClickHandler;
    private ColorButtonLayoutPopulator layoutPopulator;
    private Toast colorPatternToast;
    private SettingsButtonsConfigurator settingsButtonsConfigurator;
    private ResumedActionsHelper resumedActionsHelper;
    private SettingsPopup settingsPopup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        initImageSaver();
        setupActionbar();
        configurePaintView();
        setupButtons();
        new SeekBarConfigurator(this, paintView);
        setupColorAutoScroll();
    }


    @Override
    protected void onPause(){
        super.onPause();
        resumedActionsHelper.onPause();
    }


    @Override
    public void onClick(View v){
        settingsPopup.dismiss();
        buttonClickHandler.handleColorButtonClicks(v);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuitems, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                startClearDialogIfChangesNotSaved();
                return true;
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SAVE_FILE_ACTIVITY_CODE && resultCode == Activity.RESULT_OK) {
            imageSaver.saveImageToFile(data, paintView);
            paintView.notifyPictureSaved();
        }
        else if(requestCode == CLEAR_CANVAS_ACTIVITY_CODE && resultCode == Activity.RESULT_OK){
            createNewSketch();
        }
    }


    public ButtonLayoutParams getSettingsButtonsLayoutParams(){
        return settingsButtonLayoutParams;
    }


    private void setupButtons(){
        setupSettingsButtons();
        setupDefaultSelections();
        setupColorAndShadeButtons();
        assignRecentButtons();
    }


    private void setupColorAutoScroll(){
        new ColorAutoScroller((HorizontalScrollView)findViewById(R.id.colorScrollView));
    }


    private void initImageSaver(){
        imageSaver = new ImageSaver(this);
    }


    private void setupSettingsButtons(){
        settingsPopup = new SettingsPopup((ViewGroup)findViewById(R.id.includedSettingsLayout), this);
        settingsButtonsConfigurator = new SettingsButtonsConfigurator(this, paintView);
    }


    public SettingsPopup getSettingsPopup(){
        return this.settingsPopup;
    }


    private void assignRecentButtons(){
        resumedActionsHelper = new ResumedActionsHelper( buttonClickHandler ,layoutPopulator, settingsButtonsConfigurator, paintView);
        resumedActionsHelper.onResume();
    }


    public void toastPattern(String msg){
        if(colorPatternToast != null){
            colorPatternToast.cancel();
        }
        colorPatternToast = Toast.makeText(MainActivity.this, getString(R.string.pattern_toast_prefix) +  msg, Toast.LENGTH_SHORT);
        colorPatternToast.show();
    }


    public void toast(int messageId){
        if(colorPatternToast != null){
            colorPatternToast.cancel();
        }
        colorPatternToast = Toast.makeText(MainActivity.this,  getResources().getString(messageId), Toast.LENGTH_SHORT);
        colorPatternToast.show();
    }


    private void configurePaintView(){
        paintView = findViewById(R.id.paintView);
        PaintViewConfigurator paintViewConfigurator = new PaintViewConfigurator(this, getWindowManager(), 1000, 1000);
        paintViewConfigurator.configure(paintView, settingsPopup);
        reconfigurePaintViewWithAssignedLayoutHeight(this);
        //assignSavedBitmap();
    }


    private void reconfigurePaintViewWithAssignedLayoutHeight(final MainActivity mainActivity){
        final LinearLayout linearLayout = findViewById(R.id.paintViewLayout);
        ViewTreeObserver vto = linearLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width  = linearLayout.getMeasuredWidth();
                int height = linearLayout.getMeasuredHeight();

                PaintViewConfigurator paintViewConfigurator = new PaintViewConfigurator(mainActivity, mainActivity.getWindowManager(), width, height);
                paintViewConfigurator.configure(paintView, settingsPopup);
                assignSavedBitmap();
            }
        });

    }


    private void assignSavedBitmap(){
        PaintViewSingleton paintViewSingleton = PaintViewSingleton.getInstance();
        Bitmap bitmap = paintViewSingleton.getBitmap();
        if(bitmap != null){
            paintView.setBitmap(bitmap);
        }
    }


    private void assignViews(){
        shadesScrollView =  findViewById(R.id.colorShadeScrollView);
        colorButtonGroupLayout = findViewById(R.id.colorButtonGroup);
    }


    void setupColorAndShadeButtons(){
        List<Integer> colors = ColorCreator.generate();
        buttonClickHandler = new ColorButtonClickHandler(this, paintView, colorButtonLayoutParams, shadesScrollView);
        buttonClickHandler.setColorsMap(colors);
        layoutPopulator = new ColorButtonLayoutPopulator(this, colorButtonLayoutParams, colors);
        buttonClickHandler.setMultiColorShades(layoutPopulator.getMultiColorShades());
        layoutPopulator.addColorButtonLayoutsTo(colorButtonGroupLayout);
        buttonClickHandler.setShadeLayoutsMap(layoutPopulator.getShadeLayoutsMap());
        buttonClickHandler.handleColorButtonClicks(getDefaultButton());
    }


    private View getDefaultButton(){
        View defaultButton = colorButtonGroupLayout.findViewWithTag(R.string.tag_button_default_color);
        if(defaultButton == null){
            defaultButton = colorButtonGroupLayout.findViewWithTag(R.string.tag_button_color_button);
        }
        return defaultButton;
    }


    private void setupDefaultSelections(){
      //  paintView.setBrushSize(brushSizeSeekBar.getProgress());
    }


    private void setupActionbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar();
    }


    void startClearDialogIfChangesNotSaved(){
        if(paintView.canvasWasModifiedSinceLastSaveOrReset()){
            startConfirmClearActivity();
            return;
        }
        createNewSketch();
    }


    private void createNewSketch(){
        paintView.resetCanvas();
    }


    private void startAboutActivity(){
        Intent intent = new Intent(this, AboutDialogActivity.class);
        startActivityForResult(intent, CLEAR_CANVAS_ACTIVITY_CODE);
    }


    private void startSaveDocumentActivity(){
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_TITLE, "sketch");
        startActivityForResult(intent, SAVE_FILE_ACTIVITY_CODE);
    }


    private void startOpenDocumentActivity(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, LOAD_FILE_ACTIVITY_CODE);
    }


    private void startConfirmClearActivity(){
        Intent intent = new Intent(this, ConfirmWipeDialogActivity.class);
        startActivityForResult(intent, CLEAR_CANVAS_ACTIVITY_CODE);
    }

}
