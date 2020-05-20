package com.jacstuff.sketchy;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.graphics.Bitmap.CompressFormat.JPEG;
import static android.os.Environment.DIRECTORY_PICTURES;

public class AboutDialogActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_dialog);
        findViewById(R.id.appName).setOnClickListener(this);
        addClickListeners();
    }

    private void addClickListeners(){
        addClickListener(R.id.aboutLayout);
        addClickListener(R.id.appName);
        addClickListener(R.id.appVersion);
        addClickListener(R.id.createdBy);
    }

    private void addClickListener(int id){
        findViewById(id).setOnClickListener(this);
    }



    public void onClick(View view){
        finish();
    }

}
