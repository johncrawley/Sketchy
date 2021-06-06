package com.jacstuff.sketchy.controls.shapecontrols;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private PaintGroup paintGroup;


     public TextControls(Activity activity, TextControlsDto textControlsDto, PaintGroup paintGroup) {
         this.activity = activity;
         textInput = activity.findViewById(R.id.textShapeInput);
         SeekBar skewSeekBar = activity.findViewById(R.id.textSkewSeekBar);
         this.textControlsDto = textControlsDto;
         this.paintGroup = paintGroup;

         setupEditTextActionListener();
         setupListener(skewSeekBar, paintGroup);
         setupCheckboxListener(paintGroup);
     }


     private void setupEditTextActionListener(){

         textInput.setOnEditorActionListener(new EditText.OnEditorActionListener() {


             InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

             @Override
             public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                 if(imm == null){
                     return false;
                 }

                 if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                     imm.hideSoftInputFromWindow(textInput.getWindowToken(), 0);
                 }
                 String contents = textInput.getText().toString();
                 textControlsDto.setText(contents);
                 return true;
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
                if(seekBar.getId() == R.id.textSkewSeekBar){
                    setSkew(seekBar);
                }
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

    private void setSkew(SeekBar seekBar){
        float skew = (seekBar.getProgress() - 100) / -100f;
        paintGroup.setTextSkewX(skew);
    }


    private void setupCheckboxListener(final PaintGroup paintGroup){

        CheckBox.OnCheckedChangeListener checkedChangeListener = new CheckBox.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switch (compoundButton.getId()){
                    case R.id.checkboxTextBold:
                        paintGroup.setTextBold(b);
                        break;
                    case R.id.checkboxTextStrikethrough:
                        paintGroup.setStrikeThrough(b);
                        break;
                    case R.id.checkboxTextUnderline:
                        paintGroup.setTextUnderline(b);
                }
            }

        };

        assignCheckBoxListener(checkedChangeListener, R.id.checkboxTextBold, R.id.checkboxTextStrikethrough, R.id.checkboxTextUnderline);

    }

    private void assignCheckBoxListener(CheckBox.OnCheckedChangeListener onCheckedChangeListener, int ... ids){
         for(int id : ids){
             CheckBox checkbox = (CheckBox)activity.findViewById(id);
             checkbox.setOnCheckedChangeListener(onCheckedChangeListener);
         }
    }



}



