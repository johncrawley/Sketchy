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
    String photoFilePath;


    public static LoadPhotoDialogFragment newInstance() {
        return new LoadPhotoDialogFragment();
    }
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_load_photo, container, false);
        Dialog dialog =  getDialog();
        bundle = getArguments();
        if(bundle == null){
            return rootView;
        }
        previewWidth = getBundleInt(WIDTH_TAG) / 2;
        previewHeight = getBundleInt(HEIGHT_TAG) / 2;
        photoFilePath = bundle.getString(PHOTO_FILE_PATH_TAG);
        LinearLayout photoPreviewLayout = rootView.findViewById(R.id.photoPreviewLayout);
        View photoPreview = photoPreviewLayout.getChildAt(0);
        photoPreview.setLayoutParams(new LinearLayout.LayoutParams(previewWidth, previewHeight));

        activity = (MainActivity)getActivity();

        setupOkButton(rootView);
        if(dialog != null){
            dialog.setTitle(activity.getString(R.string.adjust_photo_dialog_title));
        }

        return rootView;
    }


    private int getBundleInt(String key){
        if(bundle == null){
            return 500;
        }
        return bundle.getInt(key);
    }

    LoadPhotoPreview loadPhotoPreview;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadPhotoPreview = view.findViewById(R.id.loadPhotoPreview);
        loadPhotoPreview.init(previewWidth, previewHeight);
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
            loadPhotoPreview.loadBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(photoFile.exists()){
            photoFile.delete();
        }
    }


    private void setupOkButton(View parentView){
        Button saveButton = parentView.findViewById(R.id.loadPhotoOkButton);
        saveButton.setOnClickListener((View v) ->dismiss());
    }


    @Override
    public void dismiss(){
        super.dismiss();
        PaintView paintView = activity.getPaintView();
        loadPhotoPreview.drawAmendedBitmapTo(paintView);
    }


}