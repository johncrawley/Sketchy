package com.jacstuff.sketchy.ui;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class LoadPhotoDialogFragment extends DialogFragment {

    public final static String WIDTH_TAG = "width";
    public final static String HEIGHT_TAG = "height";
    public final static String PHOTO_FILE_PATH_TAG = "photo_file_path";
    private MainActivity activity;
    private int previewWidth, previewHeight;
    private String photoFilePath;
    private Bundle bundle;
    private final int PREVIEW_SCALE_FACTOR = 2;
    private LoadPhotoPreview loadPhotoPreview;


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
        }
    }


    private void setupDialog(){
        Dialog dialog =  getDialog();
        if(dialog != null){
            dialog.setTitle(activity.getString(R.string.adjust_photo_dialog_title));
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
        loadPictureIntoPreview();
    }


    private void loadPictureIntoPreview(){
        if(photoFilePath == null){
            return;
        }
        File photoFile = new File(photoFilePath);
        try {
            FileInputStream fis = new FileInputStream(photoFile);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            loadPhotoPreview.loadAndDrawBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(photoFile.exists()){
            photoFile.delete();
        }
    }


    private void setupOkButton(View parentView){
        Button saveButton = parentView.findViewById(R.id.loadPhotoOkButton);
        saveButton.setOnClickListener((View v) -> dismiss());

        Button rotateButton = parentView.findViewById(R.id.rotateImageButton);
        rotateButton.setOnClickListener((View v) -> loadPhotoPreview.rotate());
    }


    @Override
    public void dismiss(){
        super.dismiss();
        PaintView paintView = activity.getPaintView();
        loadPhotoPreview.drawAmendedBitmapTo(paintView);
    }


}