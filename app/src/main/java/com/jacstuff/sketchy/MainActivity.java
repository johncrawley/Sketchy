package com.jacstuff.sketchy;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.provider.MediaStore;
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
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.ui.ColorPickerSeekBarConfigurator;
import com.jacstuff.sketchy.ui.EditColorFragment;
import com.jacstuff.sketchy.ui.LoadPhotoDialogFragment;
import com.jacstuff.sketchy.ui.UserColorStore;
import com.jacstuff.sketchy.utils.Toaster;
import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.viewmodel.ViewModelHelper;
import com.jacstuff.sketchy.tasks.ColorAutoScroller;
import com.jacstuff.sketchy.ui.SettingsPopup;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public String SHARED_PREFS_NAME = "SketchtyPrefs";
    private final String SAVED_ORIENTATION = "savedOrientation";
    private final String SAVED_WAS_APP_STOPPED_PROPERLY = "wasAppStoppedProperly";
    private PaintView paintView;
    private ImageSaver imageSaver;
   // private LinearLayout colorButtonGroupLayout;
    private ColorButtonClickHandler colorButtonClickHandler;
    private Toaster toaster;
    private SettingsButtonsConfigurator settingsButtonsConfigurator;
    private ViewModelHelper viewModelHelper;
    private SettingsPopup settingsPopup;
    private MainViewModel viewModel;
    private ButtonReferenceStore buttonReferenceStore;
    private PaintHelperManager paintHelperManager;
    private ActivityResultLauncher<Intent> activityResultLauncher, loadImageActivityResultLauncher, cameraActivityResultLauncher;
    private ColorButtonLayoutCreator colorButtonLayoutCreator;
    private SeekBarConfigurator seekBarConfigurator;
    private ButtonLayoutParams colorButtonLayoutParams;
    private ColorPickerSeekBarConfigurator colorPickerSeekBarConfigurator;



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
        initActivityResultLauncher();
        initActivityResultLauncherForLoad();
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
        if(id != paintView.getId() && id != R.id.brushSizeSeekBar){
            paintView.onTouchUp();
        }
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
        else if(  id == R.id.action_take_picture) {
            checkPermissionAndStartCamera();
        }
        else if( id == R.id.action_about){
            startActivity(new Intent(this, AboutDialogActivity.class));
        }
        else if( id == R.id.action_share){
            shareSketch();
        }
        else if(id == R.id.action_reset_colors){
            showResetColorsDialog();
        }

        return true;
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
        String tag = "load_photo_preview";
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(tag);
        if (prev != null) {
            fragmentTransaction.remove(prev);
        }

        fragmentTransaction.addToBackStack(null);
        Bundle bundle = new Bundle();
        bundle.putInt(LoadPhotoDialogFragment.WIDTH_TAG, paintView.getWidth());
        bundle.putInt(LoadPhotoDialogFragment.HEIGHT_TAG, paintView.getHeight());
        bundle.putString(LoadPhotoDialogFragment.PHOTO_FILE_PATH_TAG, photoFilePath);
        LoadPhotoDialogFragment loadPhotoDialogFragment = LoadPhotoDialogFragment.newInstance();
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


    public void toast(int resId){ toaster.toast(resId); }


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
        BrushFactory brushFactory = new BrushFactory(this);
        final LinearLayout linearLayout = findViewById(R.id.paintViewLayout);
        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                paintView.init(settingsPopup, brushFactory);
                settingsButtonsConfigurator.selectDefaults();
                viewModelHelper.onResume();
            }
        });
        colorPickerSeekBarConfigurator.setupOnCreation(R.id.gradientColorPickerSeekBar);
        colorPickerSeekBarConfigurator.setupOnCreation(R.id.shadowColorPickerSeekBar);
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


    // Register the permissions callback, which handles the user's response to the
    // system permissions dialog. Save the return value, an instance of
    // ActivityResultLauncher, as an instance variable.
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    startTakePictureActivity();
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            });


    private void checkPermissionAndStartCamera(){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
           startTakePictureActivity();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
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


    private void startSaveDocumentActivity(){
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_TITLE, "sketch");
        activityResultLauncher.launch(intent);
    }


    private void startTakePictureActivity(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.jcrawley.android.fileprovider",
                    photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            cameraActivityResultLauncher.launch(intent);
        }
    }


    private void shareSketch(){
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.setType("image/*");
        i.putExtra(Intent.EXTRA_STREAM, getImageUri(MainActivity.this, paintView.getBitmap()));
        startActivity(Intent.createChooser(i, getString(R.string.share_to)));
    }

    String currentPhotoPath;


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
