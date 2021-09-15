package com.jacstuff.sketchy;

import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.jacstuff.sketchy.brushes.BrushFactory;
import com.jacstuff.sketchy.controls.colorbuttons.ButtonReferenceStore;
import com.jacstuff.sketchy.controls.colorbuttons.ColorButtonClickHandler;
import com.jacstuff.sketchy.controls.ButtonLayoutParams;
import com.jacstuff.sketchy.controls.colorbuttons.ColorButtonLayoutCreator;
import com.jacstuff.sketchy.controls.colorbuttons.ColorCreator;
import com.jacstuff.sketchy.controls.seekbars.SeekBarConfigurator;
import com.jacstuff.sketchy.controls.settings.SettingsButtonsConfigurator;
import com.jacstuff.sketchy.io.ImageSaver;
import com.jacstuff.sketchy.multicolor.ShadesStore;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.utils.Toaster;
import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.viewmodel.ViewModelHelper;
import com.jacstuff.sketchy.tasks.ColorAutoScroller;
import com.jacstuff.sketchy.ui.SettingsPopup;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String SHARED_PREFS_NAME = "SketchtyPrefs";
    private final String SAVED_ORIENTATION = "savedOrientation";
    private final String SAVED_WAS_APP_STOPPED_PROPERLY = "wasAppStoppedProperly";
    private PaintView paintView;
    private ImageSaver imageSaver;
    private LinearLayout colorButtonGroupLayout;
    private ColorButtonClickHandler colorButtonClickHandler;
    private Toaster toaster;
    private SettingsButtonsConfigurator settingsButtonsConfigurator;
    private ViewModelHelper viewModelHelper;
    private SettingsPopup settingsPopup;
    private MainViewModel viewModel;
    private ButtonReferenceStore buttonReferenceStore;
    private PaintHelperManager paintHelperManager;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private ActivityResultLauncher <Intent> loadImageActivityResultLauncher;
    private ColorButtonLayoutCreator colorButtonLayoutCreator;

    private final ButtonLayoutParams colorButtonLayoutParams = new ButtonLayoutParams(120, 120, 22);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonReferenceStore = new ButtonReferenceStore();
        toaster = new Toaster(MainActivity.this);
        imageSaver = new ImageSaver(this);
        setupViewModel();
        setupActionbar();
        setupPaintViewAndDefaultSelections(this);
        initPaintHelperManager();
        setupColorAndShadeButtons();
        setupSettingsButtons();
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
        paintHelperManager = new PaintHelperManager(this, paintView, viewModel);
        viewModelHelper.setPaintHelperManager(paintHelperManager);
        paintView.setPaintHelperManager(paintHelperManager);
    }


    private void setupViewModel(){
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
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


    public ButtonLayoutParams getColorButtonLayoutParams(){
        return this.colorButtonLayoutParams;
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


    public PaintHelperManager getPaintHelperManager(){
        return this.paintHelperManager;
    }


    public MainViewModel getViewModel(){
        return this.viewModel;
    }


    public ButtonReferenceStore getButtonReferenceStore(){
        return buttonReferenceStore;
    }


    public ViewModelHelper getViewModelHelper(){
        return this.viewModelHelper;
    }


    public SettingsPopup getSettingsPopup(){
        return this.settingsPopup;
    }


    public ColorButtonLayoutCreator getColorButtonLayoutCreator(){
        return this.colorButtonLayoutCreator;
    }


    public void toast(int resId){ toaster.toast(resId); }


    private void setupSettingsButtons(){
        settingsPopup = new SettingsPopup(findViewById(R.id.includedSettingsLayout), this);
        settingsButtonsConfigurator = new SettingsButtonsConfigurator(this, paintView);
    }


    void setupColorAndShadeButtons(){
        List<Integer> colors = ColorCreator.generate();
        colorButtonGroupLayout = findViewById(R.id.colorButtonGroup);
        colorButtonClickHandler = new ColorButtonClickHandler(this, colorButtonLayoutParams);
        colorButtonClickHandler.setColorsMap(colors);
        colorButtonLayoutCreator = new ColorButtonLayoutCreator(this, colorButtonLayoutParams, colors);
        ShadesStore shadesStore = new ShadesStore();
        shadesStore.setShades(colorButtonLayoutCreator.getMultiColorShadesForSequences());
        colorButtonClickHandler.setShadesStore(shadesStore);
        colorButtonLayoutCreator.addColorButtonLayoutsTo(colorButtonGroupLayout);
        colorButtonClickHandler.setShadeLayoutsMap(colorButtonLayoutCreator.getShadeLayoutsMap());
        colorButtonClickHandler.onClick(getDefaultColorButton());
    }


    private View getDefaultColorButton(){
        View defaultButton = colorButtonGroupLayout.findViewWithTag(R.string.tag_button_default_color);
        if(defaultButton == null){
            defaultButton = colorButtonGroupLayout.findViewWithTag(R.string.tag_button_color_button);
        }
        return defaultButton;
    }


    private void setupColorAutoScroll() {
        if (viewModel.isFirstExecution){
            new ColorAutoScroller(findViewById(R.id.colorScrollView));
        }
    }


    private void setupPaintViewAndDefaultSelections(final MainActivity mainActivity){
        paintView = findViewById(R.id.paintView);
        BrushFactory brushFactory = new BrushFactory(this);
        final LinearLayout linearLayout = findViewById(R.id.paintViewLayout);
        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                paintView.init(viewModel, settingsPopup, brushFactory);
                settingsButtonsConfigurator.selectDefaults();
                new SeekBarConfigurator(mainActivity, paintView);
                viewModelHelper.onResume();
            }
        });
    }


    private void setupActionbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar();
    }


    private void initActivityResultLauncher(){
        activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result == null || result.getData() == null){
                    return;
                }
                imageSaver.saveImageToFile(result.getData(), paintView);
            });
    }


    private void initActivityResultLauncherForLoad(){
        loadImageActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result == null || result.getData() == null){
                    return;
                }
                imageSaver.loadImage(result.getData(), paintView);
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


    public void onDestroy(){
        savePreferences();
        setWasAppStoppedProperlyProperty(true);
        super.onDestroy();
    }


    public void loadStoredImage(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_NAME,0);
        boolean wasAppStoppedProperly = prefs.getBoolean(SAVED_WAS_APP_STOPPED_PROPERLY, false);
        setWasAppStoppedProperlyProperty(false);
        if(!viewModel.isFirstExecution || !wasAppStoppedProperly){
            return;
        }
        prefs.getInt(SAVED_ORIENTATION, Configuration.ORIENTATION_PORTRAIT);
        imageSaver.loadImageFromCacheFile(paintView);
    }


    private void setWasAppStoppedProperlyProperty(boolean wasSaved){
        SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFS_NAME,0).edit();
        editor.putBoolean(SAVED_WAS_APP_STOPPED_PROPERLY, wasSaved);
        boolean success = editor.commit();
        System.out.println("app stopped properly property was saved! : " + success);
    }


    public void savePreferences(){
        SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFS_NAME,0).edit();
        editor.putInt(SAVED_ORIENTATION, getResources().getConfiguration().orientation);
        imageSaver.saveImageToCacheFile(paintView);
        editor.apply();
    }

}
