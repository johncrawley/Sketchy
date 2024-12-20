package com.jacstuff.sketchy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class AboutDialogActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_dialog);
        findViewById(R.id.appGraphic).setOnClickListener(this);
        addClickListeners();
    }

    private void addClickListeners(){
        addClickListener(R.id.aboutLayout);
        addClickListener(R.id.appGraphic);
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
