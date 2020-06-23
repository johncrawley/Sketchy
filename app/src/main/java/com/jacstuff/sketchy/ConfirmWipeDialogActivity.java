package com.jacstuff.sketchy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ConfirmWipeDialogActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_wipe_dialog);
        addClickListeners();
    }
    private void addClickListeners(){

        addClickListener(R.id.confirmClearCanvasButton);
        addClickListener(R.id.cancelClearCanvasButton);
    }

    private void addClickListener(int id){
        findViewById(id).setOnClickListener(this);
    }



    public void onClick(View view){


        Intent data = new Intent();
        int resultCode = Activity.RESULT_CANCELED;
        if(view.getId() == R.id.confirmClearCanvasButton){
            resultCode = Activity.RESULT_OK;
        }
        if (getParent() == null) {
            setResult(resultCode, data);
        } else {
            getParent().setResult(resultCode, data);
        }
        finish();


        finish();
    }
}
