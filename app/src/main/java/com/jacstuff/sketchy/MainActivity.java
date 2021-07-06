package com.jacstuff.sketchy;

import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.jacstuff.sketchy.controls.settings.SettingsButtonsConfigurator;
import com.jacstuff.sketchy.controls.shapecontrols.TextControls;
import com.jacstuff.sketchy.io.ImageSaver;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.PaintViewConfigurator;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.viewmodel.ViewModelHelper;
import com.jacstuff.sketchy.tasks.ColorAutoScroller;
import com.jacstuff.sketchy.ui.SettingsPopup;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private PaintView paintView;
    private ImageSaver imageSaver;
    private LinearLayout colorButtonGroupLayout;
    private final ButtonLayoutParams colorButtonLayoutParams = new ButtonLayoutParams(120, 120, 22);
    private final ButtonLayoutParams settingsButtonLayoutParams = new ButtonLayoutParams(120, 120, 15, 2);
    private ColorButtonClickHandler colorButtonClickHandler;
    private Toast colorPatternToast;
    private SettingsButtonsConfigurator settingsButtonsConfigurator;
    private ViewModelHelper viewModelHelper;
    private SettingsPopup settingsPopup;
    private MainViewModel viewModel;
    private ButtonReferenceStore buttonReferenceStore;
    private PaintHelperManager paintHelperManager;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher <Intent> loadImageActivityResultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonReferenceStore = new ButtonReferenceStore();
        setupViewModel();
        initImageSaver();
        setupActionbar();
        configurePaintView();
        initPaintHelperManager();
        setupSettingsButtons();
        setupColorAndShadeButtons();
        new SeekBarConfigurator(this, paintView);
        new TextControls(this, paintView.getPaintGroup());
        viewModelHelper.init(colorButtonClickHandler, paintView);
        setupColorAutoScroll();
        initActivityResultLauncher();
        initActivityResultLauncherForLoad();
    }


    @Override
    protected void onPause(){
        super.onPause();
        if(viewModelHelper != null){
            viewModelHelper.onPause();
        }
    }


    private void initPaintHelperManager(){
        paintHelperManager = new PaintHelperManager(this, viewModel);
        viewModelHelper.setPaintHelperManager(paintHelperManager);
        paintView.setPaintHelperManager(paintHelperManager);
    }


    public PaintHelperManager getPaintHelperManager(){
        return this.paintHelperManager;
    }


    private void setupViewModel(){
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModelHelper = new ViewModelHelper( viewModel, this);
    }


    @Override
    public void onClick(View v){
        settingsPopup.dismiss(v);
        colorButtonClickHandler.onClick(v);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuitems, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_new){
            paintView.resetCanvas();
        }
        else if(id == R.id.action_undo){
            paintView.undo();
        }
        else if( id == R.id.action_save) {
            startSaveDocumentActivity();
        }
        else if(  id == R.id.action_open) {
            startOpenDocumentActivity();
        }
        else if( id == R.id.action_about){
            startActivity(new Intent(this, AboutDialogActivity.class));
        }
        return true;
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
        colorButtonGroupLayout = findViewById(R.id.colorButtonGroup);
        colorButtonClickHandler = new ColorButtonClickHandler(this, paintView, colorButtonLayoutParams, (LinearLayout)findViewById(R.id.shadesButtonGroup));
        colorButtonClickHandler.setColorsMap(colors);
        ColorButtonLayoutPopulator layoutPopulator = new ColorButtonLayoutPopulator(this, colorButtonLayoutParams, colors);
        colorButtonClickHandler.setMultiColorShades(layoutPopulator.getMultiColorShades());
        layoutPopulator.addColorButtonLayoutsTo(colorButtonGroupLayout);
        colorButtonClickHandler.setShadeLayoutsMap(layoutPopulator.getShadeLayoutsMap());
        colorButtonClickHandler.onClick(getDefaultColorButton());
    }


    private void setupColorAutoScroll() {
        if (viewModel.isFirstExecution){
            new ColorAutoScroller(
                    (HorizontalScrollView) findViewById(R.id.colorScrollView));
        }
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
        setupPaintViewAndDefaultSelections(this);
    }


    private void setupPaintViewAndDefaultSelections(final MainActivity mainActivity){
        final LinearLayout linearLayout = findViewById(R.id.paintViewLayout);
        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                new PaintViewConfigurator(mainActivity, linearLayout.getMeasuredHeight())
                        .configure(viewModel, paintView, settingsPopup);
                settingsButtonsConfigurator.selectDefaults();
                viewModelHelper.onResume();
            }
        });
    }


    private View getDefaultColorButton(){
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


    private void initActivityResultLauncher(){
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result == null || result.getData() == null){
                            return;
                        }
                        imageSaver.saveImageToFile(result.getData(), paintView);
                    }
                });
    }


    private void initActivityResultLauncherForLoad(){
        loadImageActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result == null || result.getData() == null){
                            return;
                        }
                        imageSaver.loadImage(result.getData(), paintView);
                    }
                });
    }

    private void startSaveDocumentActivity(){
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_TITLE, "sketch");
        activityResultLauncher.launch(intent);
    }


    private void startOpenDocumentActivity(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        loadImageActivityResultLauncher.launch(intent);
    }


    /*
    public void loadPreferences(){
        SharedPreferences prefs = getSharedPreferences("myPref",0);
        prefs.getString("myStoreName","defaultValue");
    }


    public void savePreferences(String thePreference){
        SharedPreferences.Editor editor = getSharedPreferences("myPref",0).edit();
        editor.putString("myStoreName", thePreference);
        editor.commit();
    }
*/
}
