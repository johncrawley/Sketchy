package com.jacstuff.sketchy.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.paintview.PaintView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class LoadPhotoDialogFragment extends DialogFragment {

    public final static String WIDTH_TAG = "width";
    public final static String HEIGHT_TAG = "height";
    public final static String PHOTO_FILE_PATH_TAG = "photo_file_path";
    public final static String IS_FROM_FILE = "photo_file_path";
    private MainActivity activity;
    private int previewWidth, previewHeight;
    private String photoFilePath;
    private Bundle bundle;
    private final int PREVIEW_SCALE_FACTOR = 2;
    private LoadPhotoPreview loadPhotoPreview;
    private boolean isPhotoFromFile;
    private ActivityResultLauncher<Intent>  loadImageActivityResultLauncher;


    public static LoadPhotoDialogFragment newInstance() {
        return new LoadPhotoDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_load_photo, container, false);
        assignBundleData();
        assignLayoutParams(rootView);
        activity = (MainActivity)getActivity();
        setupOkButton(rootView);
        setupDialog();
        return rootView;
    }


    private void assignBundleData(){
        bundle = getArguments();
        if(bundle != null){
            previewWidth = getBundleInt(WIDTH_TAG) / PREVIEW_SCALE_FACTOR;
            previewHeight = getBundleInt(HEIGHT_TAG) / PREVIEW_SCALE_FACTOR;
            photoFilePath = bundle.getString(PHOTO_FILE_PATH_TAG);
            isPhotoFromFile = bundle.getBoolean(IS_FROM_FILE, false);
            log("Entered assignBundleData, photoFilePAth: " + photoFilePath);
        }
    }


    private void setupDialog(){
        Dialog dialog =  getDialog();
        if(dialog != null){
            int titleId = isPhotoFromFile ? R.string.adjust_image_dialog_title : R.string.adjust_photo_dialog_title;
            dialog.setTitle(activity.getString(titleId));
        }
    }


    private void assignLayoutParams(View rootView){
        View photoPreview = rootView.findViewById(R.id.loadPhotoPreview);
        photoPreview.setLayoutParams(new LinearLayout.LayoutParams(previewWidth, previewHeight));
    }


    private int getBundleInt(String key){
        if(bundle == null){
            return 500;
        }
        return bundle.getInt(key);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadPhotoPreview = view.findViewById(R.id.loadPhotoPreview);
        loadPhotoPreview.init(previewWidth, previewHeight, PREVIEW_SCALE_FACTOR);
        log("Entered onViewCreated()");
        if(isPhotoFromFile){
            log("isPhotoFromFile is true!");
            initLoadFileResultLauncher();
            startOpenDocumentActivity();
            return;
        }
        log("About to loadPictureIntoPReview");
        loadPhotoIntoPreview();
    }

    private void log(String msg){
        System.out.println("^^^ LoadPhotoDialogFragment : " + msg);
    }


    private void initLoadFileResultLauncher(){
        loadImageActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result == null){
                        return;
                    }
                    Intent data = result.getData();
                    if(data == null){
                        return;
                    }
                    Uri uri = data.getData();
                    if(uri == null){
                        return;
                    }
                    loadImage(data);
                });
    }


    public void loadImage(Intent data){
        Uri uri = data.getData();
        if(uri == null){
            return;
        }
        try{
            InputStream input = activity.getContentResolver().openInputStream(uri);
            if(input == null){
                return;
            }
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            loadPhotoPreview.loadAndDrawBitmap(bitmap, false);
        }catch (IOException e){
            activity.toast(R.string.toast_open_file_error);
        }
    }


    private void startOpenDocumentActivity(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        loadImageActivityResultLauncher.launch(intent);
    }


    private void loadPhotoIntoPreview(){
        if(photoFilePath == null){
            log("loadPhotoIntoPreview() photoFilePAth is null!");
            return;
        }
        File photoFile = new File(photoFilePath);
        try {
            InputStream inputStream = new FileInputStream(photoFile);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            log("loadPhotoIntoPreview() about to loadAndDrawBitmap!");
            loadPhotoPreview.loadAndDrawBitmap(bitmap);
        } catch (FileNotFoundException e) {
            log("loadPhotoIntoPreview() File not found!");
            e.printStackTrace();
        }
    }


    private void setupOkButton(View parentView){
        setupButtonClick(parentView, R.id.loadPhotoOkButton, this::dismiss);
        setupButtonClick(parentView, R.id.rotateImageButton, ()-> loadPhotoPreview.rotate());
    }


    private void setupButtonClick(View parentView, int viewId, Runnable runnable){
        Button button = parentView.findViewById(viewId);
        button.setOnClickListener((View v) -> runnable.run());
    }


    public void onDestroy(){
        super.onDestroy();
        activity.removeTemporaryPhotoFile();
    }


    @Override
    public void dismiss(){
        super.dismiss();
        PaintView paintView = activity.getPaintView();
        loadPhotoPreview.drawAmendedBitmapTo(paintView);
    }


}