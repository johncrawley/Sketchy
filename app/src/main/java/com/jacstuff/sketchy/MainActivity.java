package com.jacstuff.sketchy;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jacstuff.sketchy.controls.colorbuttons.ButtonReferenceStore;
import com.jacstuff.sketchy.controls.colorbuttons.ColorButtonClickHandler;
import com.jacstuff.sketchy.controls.ButtonLayoutParams;
import com.jacstuff.sketchy.controls.colorbuttons.ColorButtonLayoutPopulator;
import com.jacstuff.sketchy.controls.colorbuttons.ColorCreator;
import com.jacstuff.sketchy.controls.seekbars.SeekBarConfigurator;
import com.jacstuff.sketchy.controls.settingsbuttons.SettingsButtonsConfigurator;
import com.jacstuff.sketchy.controls.shapecontrols.TextControls;
import com.jacstuff.sketchy.io.ImageSaver;
import com.jacstuff.sketchy.model.TextControlsDto;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.PaintViewConfigurator;
import com.jacstuff.sketchy.paintview.helpers.GradientHelper;
import com.jacstuff.sketchy.paintview.helpers.KaleidoscopeHelper;
import com.jacstuff.sketchy.settings.ViewModelHelper;
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
    private ColorButtonClickHandler colorButtonClickHandler;
    private Toast colorPatternToast;
    private SettingsButtonsConfigurator settingsButtonsConfigurator;
    private ViewModelHelper viewModelHelper;
    private SettingsPopup settingsPopup;
    private TextControlsDto textControlsDto;
    private MainViewModel viewModel;
    private ButtonReferenceStore buttonReferenceStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonReferenceStore = new ButtonReferenceStore();
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModelHelper = new ViewModelHelper( viewModel, this);
        textControlsDto = new TextControlsDto();
        assignViews();
        initImageSaver();
        setupActionbar();
        configurePaintView();
        setupSettingsButtons();
        setupColorAndShadeButtons();
        new SeekBarConfigurator(this, paintView);
        new TextControls(this, textControlsDto, paintView.getPaintGroup());
        viewModelHelper.init(colorButtonClickHandler, paintView);
        setupColorAutoScroll();
    }


    @Override
    protected void onPause(){
        super.onPause();
        if(viewModelHelper == null){
            return;
        }
        viewModelHelper.onPause();
    }


    @Override
    public void onClick(View v){
        settingsPopup.dismiss(v);
        colorButtonClickHandler.handleColorButtonClicks(v);
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
            case R.id.action_undo:
                paintView.undo();
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


    public MainViewModel getViewModel(){
        return this.viewModel;
    }


    public ViewModelHelper getViewModelHelper(){
        return this.viewModelHelper;
    }


    private void setupSettingsButtons(){
        settingsPopup = new SettingsPopup((ViewGroup)findViewById(R.id.includedSettingsLayout), this);
        settingsButtonsConfigurator = new SettingsButtonsConfigurator(this, paintView);
    }


    public ButtonReferenceStore getButtonReferenceStore(){
        return buttonReferenceStore;
    }


    void setupColorAndShadeButtons(){
        List<Integer> colors = ColorCreator.generate();
        colorButtonClickHandler = new ColorButtonClickHandler(this, paintView, colorButtonLayoutParams, shadesScrollView);
        colorButtonClickHandler.setColorsMap(colors);
        ColorButtonLayoutPopulator layoutPopulator = new ColorButtonLayoutPopulator(this, colorButtonLayoutParams, colors);
        colorButtonClickHandler.setMultiColorShades(layoutPopulator.getMultiColorShades());
        layoutPopulator.addColorButtonLayoutsTo(colorButtonGroupLayout);
        colorButtonClickHandler.setShadeLayoutsMap(layoutPopulator.getShadeLayoutsMap());
        colorButtonClickHandler.handleColorButtonClicks(getDefaultButton());
    }


    private void setupColorAutoScroll(){
        new ColorAutoScroller((HorizontalScrollView)findViewById(R.id.colorScrollView));
    }


    private void initImageSaver(){
        imageSaver = new ImageSaver(this);
    }



    public SettingsPopup getSettingsPopup(){
        return this.settingsPopup;
    }




    public void toastPattern(String msg){
        if(colorPatternToast != null){
            colorPatternToast.cancel();
        }
        colorPatternToast = Toast.makeText(MainActivity.this, getString(R.string.pattern_toast_prefix) +  msg, Toast.LENGTH_SHORT);
        colorPatternToast.show();
    }


    private void configurePaintView(){
        paintView = findViewById(R.id.paintView);
        paintView.initBrushes();
        paintView.setKaleidoscopeHelper(new KaleidoscopeHelper(viewModel));
        paintView.setGradientHelper(new GradientHelper(viewModel, getResources().getInteger(R.integer.gradient_radius_max)));
        setupPaintViewAndDefaultSelections(this);
    }


    private void setupPaintViewAndDefaultSelections(final MainActivity mainActivity){
        final LinearLayout linearLayout = findViewById(R.id.paintViewLayout);
        ViewTreeObserver vto = linearLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width  = linearLayout.getMeasuredWidth();
                int height = linearLayout.getMeasuredHeight();

                PaintViewConfigurator paintViewConfigurator = new PaintViewConfigurator(mainActivity, width, height);
                paintViewConfigurator.configure(viewModel, paintView, settingsPopup, textControlsDto);
                settingsButtonsConfigurator.selectDefaults();
                viewModelHelper.onResume();
            }
        });

    }


    private void assignViews(){
        shadesScrollView =  findViewById(R.id.colorShadeScrollView);
        colorButtonGroupLayout = findViewById(R.id.colorButtonGroup);
    }


    private View getDefaultButton(){
        View defaultButton = colorButtonGroupLayout.findViewWithTag(R.string.tag_button_default_color);
        if(defaultButton == null){
            defaultButton = colorButtonGroupLayout.findViewWithTag(R.string.tag_button_color_button);
        }
        return defaultButton;
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


    public void loadPreferences()
    {
        SharedPreferences prefs = getSharedPreferences("myPref",0);
        prefs.getString("myStoreName","defaultValue");
    }

    public void savePreferences(String thePreference)
    {
        SharedPreferences.Editor editor = getSharedPreferences("myPref",0).edit();
        editor.putString("myStoreName", thePreference);
        editor.commit();
    }

}
