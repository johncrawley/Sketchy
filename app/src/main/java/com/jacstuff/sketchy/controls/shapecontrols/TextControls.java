package com.jacstuff.sketchy.controls.shapecontrols;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.model.TextControlsDto;
import com.jacstuff.sketchy.paintview.PaintGroup;

public class TextControls {

    private EditText textInput;
    private Activity activity;
    private TextControlsDto textControlsDto;


     public TextControls(Activity activity, TextControlsDto textControlsDto, PaintGroup paintGroup) {
         this.activity = activity;
         textInput = activity.findViewById(R.id.textShapeInput);
         SeekBar skewSeekBar = activity.findViewById(R.id.textSkewSeekBar);
         this.textControlsDto = textControlsDto;

         setupEditTextActionListener();
         setupListener(skewSeekBar, paintGroup);
     }





     private void setupEditTextActionListener(){

         textInput.setOnEditorActionListener(new EditText.OnEditorActionListener() {
             @Override
             public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                     InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                     if(imm == null){
                         return false;
                     }
                     imm.hideSoftInputFromWindow(textInput.getWindowToken(), 0);
                     String contents = textInput.getText().toString();
                     textControlsDto.setText(contents);
                     return true;
                 }
                 return false;
             }
         });
     }

    // paint.setLinearText(true);
    // paint.setFakeBoldText(true);
    // paint.setSubpixelText(true);
    // paint.setLetterSpacing(0.5f);
    // paint.setElegantTextHeight(true);


    private void setupListener(SeekBar seekBar, final PaintGroup paintGroup){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                float skew = (seekBar.getProgress() - 100) / -100f;
                paintGroup.setTextSkewX(skew);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //do nothing
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               //do nothing
            }

        });
    }



}



