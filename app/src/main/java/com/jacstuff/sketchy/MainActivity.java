package com.jacstuff.sketchy;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jacstuff.sketchy.brushes.BrushFactory;
import com.jacstuff.sketchy.controls.colorbuttons.ButtonReferenceStore;
import com.jacstuff.sketchy.controls.colorbuttons.ColorButtonClickHandler;
import com.jacstuff.sketchy.controls.ButtonLayoutParams;
import com.jacstuff.sketchy.controls.colorbuttons.ColorButtonLayoutCreator;
import com.jacstuff.sketchy.controls.colorbuttons.ColorCreator;
import com.jacstuff.sketchy.controls.seekbars.SeekBarConfigurator;
import com.jacstuff.sketchy.controls.settings.SettingsButtonsConfigurator;
import com.jacstuff.sketchy.controls.settings.menu.ConnectedLineIconModifier;
import com.jacstuff.sketchy.io.ImageSaver;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.paintview.history.DrawHistory;
import com.jacstuff.sketchy.ui.ColorPickerSeekBarConfigurator;
import com.jacstuff.sketchy.ui.EditColorFragment;
import com.jacstuff.sketchy.ui.LoadImageDialogFragment;
import com.jacstuff.sketchy.ui.UserColorStore;
import com.jacstuff.sketchy.utils.Toaster;
import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.viewmodel.ViewModelHelper;
import com.jacstuff.sketchy.tasks.ColorAutoScroller;
import com.jacstuff.sketchy.ui.SettingsPopup;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public String SHARED_PREFS_NAME = "SketchtyPrefs";
    private final String SAVED_ORIENTATION = "savedOrientation";
    private final String SAVED_WAS_APP_STOPPED_PROPERLY = "wasAppStoppedProperly";
    private PaintView paintView;
    private ImageSaver imageSaver;
    private ColorButtonClickHandler colorButtonClickHandler;
    private Toaster toaster;
    private SettingsButtonsConfigurator settingsButtonsConfigurator;
    private ViewModelHelper viewModelHelper;
    private SettingsPopup settingsPopup;
    private MainViewModel viewModel;
    private ButtonReferenceStore buttonReferenceStore;
    private PaintHelperManager paintHelperManager;
    private ActivityResultLauncher<Intent> saveSketchActivityResultLauncher, cameraActivityResultLauncher;
    private ColorButtonLayoutCreator colorButtonLayoutCreator;
    private SeekBarConfigurator seekBarConfigurator;
    private ButtonLayoutParams colorButtonLayoutParams;
    private ColorPickerSeekBarConfigurator colorPickerSeekBarConfigurator;
    private String currentPhotoPath;
    private DrawHistory drawHistory;
    private ConnectedLineIconModifier connectedLineIconModifier;
    private Map<Integer, Runnable> menuActions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingsPopup = new SettingsPopup(findViewById(R.id.includedSettingsLayout), this);
        buttonReferenceStore = new ButtonReferenceStore();
        toaster = new Toaster(MainActivity.this);
        colorButtonLayoutParams = new ButtonLayoutParams(MainActivity.this, R.integer.color_button_selected_border_width);
        imageSaver = new ImageSaver(this);
        seekBarConfigurator = new SeekBarConfigurator(this);
        SHARED_PREFS_NAME = getString(R.string.shared_prefs_name);
        setupViewModel();
        colorPickerSeekBarConfigurator = new ColorPickerSeekBarConfigurator(this, viewModel);
        setupPaintViewAndDefaultSelections();
        initPaintHelperManager();
        setupColorAndShadeButtons();
        setupSettingsButtons();
        viewModelHelper.init(colorButtonClickHandler, paintView);
        setupColorAutoScroll();
        initActivityResultLaunchers();
    }


    private void initActivityResultLaunchers(){
        initSaveFileResultLauncher();
        initActivityResultLauncherForCamera();
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
        int id = v.getId();
        releasePaintViewTouch(id);
        settingsPopup.dismiss(v);
        colorButtonClickHandler.onClick(v);
    }


    private void releasePaintViewTouch(int currentViewId){
        if(currentViewId != paintView.getId() && currentViewId != R.id.brushSizeSeekBar){
            paintView.onTouchUp();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menuitems, menu);
        menuActions = new HashMap<>();
        menuActions.put(R.id.action_new,    () -> paintView.resetCanvas());
        menuActions.put(R.id.action_undo,   () -> paintView.undo());
        menuActions.put(R.id.action_open,           this::startDialogForOpenDocument);
        menuActions.put(R.id.action_save,           this::startSaveDocumentActivity);
        menuActions.put(R.id.action_take_picture,   this :: checkPermissionAndStartCamera);
        menuActions.put(R.id.action_about,          this :: startAboutActivity);
        menuActions.put(R.id.action_share,          this :: shareSketch);
        menuActions.put(R.id.action_reset_colors,   this :: showResetColorsDialog);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Runnable runnable = menuActions.get(item.getItemId());
        if(runnable != null) {
            runnable.run();
        }
        return true;
    }


    private void startAboutActivity(){
        startActivity(new Intent(this, AboutDialogActivity.class));
    }


    private void showResetColorsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.reset_colors_dialog_title);
        builder.setMessage(R.string.reset_colors_dialog_message);
        builder.setCancelable(false);

        builder.setPositiveButton(R.string.reset_colors_confirmation_button, (dialogInterface, i) ->{
            dialogInterface.dismiss();
            UserColorStore.resetColorsToDefault(MainActivity.this);
            colorButtonLayoutCreator.reloadButtons();
        } );

        builder.setNegativeButton(R.string.reset_colors_cancel_button, (dialogInterface, i) -> dialogInterface.dismiss());
        builder.show();
    }


    public void startEditColorFragment(int color, int index){
        String tag = "edit_color";
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(tag);
        if (prev != null) {
            fragmentTransaction.remove(prev);
        }
        fragmentTransaction.addToBackStack(null);
        Bundle bundle = new Bundle();
        bundle.putInt(EditColorFragment.ORIGINAL_COLOR_TAG, color);
        bundle.putInt(EditColorFragment.COLOR_INDEX_TAG, index);
        EditColorFragment editColorFragment = EditColorFragment.newInstance();
        editColorFragment.setArguments(bundle);
        editColorFragment.show(fragmentTransaction, tag);
    }


    public void startLoadPhotoPreviewFragment(String photoFilePath){
        startLoadPhotoPreviewFragment(photoFilePath, false);
    }


    public void startLoadPhotoPreviewFragment(String photoFilePath, boolean isLoadingFromFile){
        String tag = "load_photo_preview";
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(tag);
        if (prev != null) {
            fragmentTransaction.remove(prev);
        }
        fragmentTransaction.addToBackStack(null);
        Bundle bundle = new Bundle();
        bundle.putBoolean(LoadImageDialogFragment.IS_FROM_FILE, isLoadingFromFile);
        bundle.putString(LoadImageDialogFragment.PHOTO_FILE_PATH_TAG, photoFilePath);
        LoadImageDialogFragment loadPhotoDialogFragment = LoadImageDialogFragment.newInstance();
        loadPhotoDialogFragment.setArguments(bundle);
        loadPhotoDialogFragment.show(fragmentTransaction, tag);
    }


    public ButtonLayoutParams getColorButtonLayoutParams(){
        return this.colorButtonLayoutParams;
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


    public SeekBarConfigurator getSeekBarConfigurator(){
        return this.seekBarConfigurator;
    }


    public ViewModelHelper getViewModelHelper(){
        return this.viewModelHelper;
    }


    public SettingsPopup getSettingsPopup(){
        return this.settingsPopup;
    }


    public PaintView getPaintView(){
        return this.paintView;
    }


    public ColorButtonLayoutCreator getColorButtonLayoutCreator(){
        return this.colorButtonLayoutCreator;
    }


    public ConnectedLineIconModifier getConnectLineIconModifier(){
        return connectedLineIconModifier;
    }


    public void toast(int resId) {
        toaster.toast(resId);
    }


    public void paintTouchUp(){
        paintView.onTouchUp();
    }


    private void setupSettingsButtons(){
        settingsButtonsConfigurator = new SettingsButtonsConfigurator(this, paintView);
    }


    void setupColorAndShadeButtons(){
        ColorCreator.loadUserColorsAndAddTo(viewModel.mainColors, this);
        colorButtonClickHandler = new ColorButtonClickHandler(this, colorButtonLayoutParams);
        colorButtonLayoutCreator = new ColorButtonLayoutCreator(this, colorButtonLayoutParams, colorButtonClickHandler);
        colorButtonLayoutCreator.addColorButtonLayouts();
        colorButtonClickHandler.setShadeLayoutsMap(colorButtonLayoutCreator.getShadeLayoutsMap());
        colorButtonClickHandler.setReusableShadesLayoutHolder(colorButtonLayoutCreator.getReusableShadesLayoutHolder());
        colorButtonClickHandler.onClick(colorButtonLayoutCreator.getDefaultColorButton());
    }


    public void replaceColorButton(int index, int amendedColor){
        colorButtonLayoutCreator.replaceColorButton(index, amendedColor);
    }


    private void setupColorAutoScroll() {
        if (viewModel.isFirstExecution){
            new ColorAutoScroller(findViewById(R.id.colorScrollView));
        }
    }


    private void setupPaintViewAndDefaultSelections(){
        paintView = findViewById(R.id.paintView);
       connectedLineIconModifier = new ConnectedLineIconModifier(paintView, viewModel, MainActivity.this);
        drawHistory = new DrawHistory(MainActivity.this, viewModel, connectedLineIconModifier);
        connectedLineIconModifier.setDrawHistory(drawHistory);
        BrushFactory brushFactory = new BrushFactory(this);
        final LinearLayout linearLayout = findViewById(R.id.paintViewLayout);
        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                paintView.init(settingsPopup, brushFactory, viewModel, drawHistory);
                settingsButtonsConfigurator.selectDefaults();
                viewModelHelper.onResume();
            }
        });
        colorPickerSeekBarConfigurator.setupOnCreation(R.id.gradientColorPickerSeekBar);
        colorPickerSeekBarConfigurator.setupOnCreation(R.id.shadowColorPickerSeekBar);
    }


    private void initSaveFileResultLauncher(){
        saveSketchActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result == null || result.getData() == null){
                    return;
                }
                imageSaver.saveImageToFile(result.getData(), paintView);
            });
    }


    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    startTakePictureActivity();
                } else {
                    Toast.makeText(MainActivity.this, R.string.camera_permission_not_given_text, Toast.LENGTH_LONG).show();
                }
            });


    private void checkPermissionAndStartCamera(){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
           startTakePictureActivity();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }


    private void startTakePictureActivity(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = createTempImageFile();
        if (photoFile == null) {
            Toast.makeText(MainActivity.this, R.string.unable_to_create_temporary_file_text, Toast.LENGTH_SHORT).show();
            return;
        }
        Uri photoURI = FileProvider.getUriForFile(this, "com.jcrawley.android.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        cameraActivityResultLauncher.launch(intent);
    }


    private void startDialogForOpenDocument(){
        startLoadPhotoPreviewFragment(currentPhotoPath, true);
    }


    private void initActivityResultLauncherForCamera(){
        cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result == null){
                        return;
                    }
                    if (result.getResultCode() == RESULT_OK) {
                        startLoadPhotoPreviewFragment(currentPhotoPath);
                    }
                });
    }


    public void removeTemporaryPhotoFile() {
        if(currentPhotoPath == null){
            return;
        }
        File file = new File(currentPhotoPath);
        if (file.exists()) {
            boolean wasFileDeleted = file.delete();
            Log.d("removeTempPhotoFile", "wasFileDeleted: " + wasFileDeleted);
        }
    }


    private void startSaveDocumentActivity(){
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_TITLE, "sketch");
        saveSketchActivityResultLauncher.launch(intent);
    }


    private File createTempImageFile(){
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return photoFile;
    }


    private void shareSketch(){
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.setType("image/*");
        i.putExtra(Intent.EXTRA_STREAM, getImageUri(MainActivity.this, paintView.getBitmap()));
        startActivity(Intent.createChooser(i, getString(R.string.share_to)));
    }


    private File createImageFile() throws IOException {
        String imageFileName = "saved_photo_" + System.currentTimeMillis() + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir );
        currentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }


    public Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, getString(R.string.shared_image_title), null);
        return Uri.parse(path);
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
