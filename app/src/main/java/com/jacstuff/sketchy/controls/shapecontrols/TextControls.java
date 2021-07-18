package com.jacstuff.sketchy.controls.shapecontrols;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.paintview.PaintGroup;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class TextControls {

    private final EditText textInput;
    private final MainActivity activity;
    private final MainViewModel viewModel;
    private final PaintGroup paintGroup;
    private final SeekBar skewSeekBar;
    private final SeekBar spacingSeekBar;


     public TextControls(MainActivity activity, PaintGroup paintGroup) {
         this.activity = activity;
         textInput = activity.findViewById(R.id.textShapeInput);
         skewSeekBar = activity.findViewById(R.id.textSkewSeekBar);
         spacingSeekBar = activity.findViewById(R.id.textSpacingSeekBar);
         this.viewModel = activity.getViewModel();
         this.paintGroup = paintGroup;

         setupEditTextActionListener();
         setupSeekBarListeners();
         setupCheckboxListener(paintGroup);
         initializeDefaultSeekBarValues();
     }


     private void setupEditTextActionListener(){

         textInput.setOnEditorActionListener(new EditText.OnEditorActionListener() {

             final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

             @Override
             public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                 if(imm == null){
                     return false;
                 }
                 if (actionId == EditorInfo.IME_ACTION_DONE) {
                     imm.hideSoftInputFromWindow(textInput.getWindowToken(), 0);
                 }
                 return true;
             }
         });


         textInput.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 viewModel.textBrushText = charSequence.toString();
             }

             @Override
             public void afterTextChanged(Editable editable) {
             }
         });
     }


     private void initializeDefaultSeekBarValues(){
        // skewSeekBar.setProgress(viewModel.textSkewSeekBarProgress);
        // setSkew(skewSeekBar);
         spacingSeekBar.setProgress(viewModel.textSpacingSeekBarProgress);
         setSpacing(spacingSeekBar);
     }


    // paint.setLinearText(true);
    // paint.setSubpixelText(true);
    // paint.setElegantTextHeight(true);


    private void setupSeekBarListeners(){

        SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int id = seekBar.getId();
                if(id == R.id.textSkewSeekBar){
                    setSkew(seekBar);
                }
                else if(id == R.id.textSpacingSeekBar){
                    setSpacing(seekBar);
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
        };

        skewSeekBar.setOnSeekBarChangeListener(seekBarListener);
        spacingSeekBar.setOnSeekBarChangeListener(seekBarListener);
    }


    private void setSkew(SeekBar seekBar){
        float skew = (seekBar.getProgress() - 100) / -100f;
        paintGroup.setTextSkewX(skew);
        viewModel.textSkewSeekBarProgress = (int)skew;
    }


    private void setSpacing(SeekBar seekBar){
        float spacing = (seekBar.getProgress()) / 100f;
        paintGroup.setLetterSpacing(spacing);
        viewModel.textSpacingSeekBarProgress = (int)spacing;
    }


    private void setupCheckboxListener(final PaintGroup paintGroup){

        CheckBox.OnCheckedChangeListener checkedChangeListener = new CheckBox.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int id = compoundButton.getId();
                if(id == R.id.checkboxTextBold){
                    paintGroup.setTextBold(b);
                }
                else if(id ==  R.id.checkboxTextStrikethrough){
                    paintGroup.setStrikeThrough(b);
                }
                else if(id == R.id.checkboxTextUnderline){
                    paintGroup.setTextUnderline(b);
                }
            }
        };
        assignCheckBoxListener(checkedChangeListener, R.id.checkboxTextBold, R.id.checkboxTextStrikethrough, R.id.checkboxTextUnderline);
    }


    private void assignCheckBoxListener(CheckBox.OnCheckedChangeListener onCheckedChangeListener, int ... ids){
         for(int id : ids){
             CheckBox checkbox = activity.findViewById(id);
             checkbox.setOnCheckedChangeListener(onCheckedChangeListener);
         }
    }

}
