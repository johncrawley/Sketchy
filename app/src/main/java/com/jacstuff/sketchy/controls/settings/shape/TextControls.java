package com.jacstuff.sketchy.controls.settings.shape;


import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.seekbars.SeekBarConfigurator;
import com.jacstuff.sketchy.controls.settings.SettingsUtils;
import com.jacstuff.sketchy.paintview.PaintGroup;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import androidx.core.content.res.ResourcesCompat;

public class TextControls {

    private final EditText textInput;
    private final MainActivity activity;
    private final MainViewModel viewModel;
    private final PaintGroup paintGroup;
    private final SeekBarConfigurator seekBarConfigurator;



     public TextControls(MainActivity activity, PaintGroup paintGroup, SeekBarConfigurator seekBarConfigurator) {
         this.activity = activity;
         textInput = activity.findViewById(R.id.textShapeInput);
         this.viewModel = activity.getViewModel();
         this.paintGroup = paintGroup;
         this.seekBarConfigurator = seekBarConfigurator;

         setupEditTextActionListener();
         setupCheckboxListener(paintGroup);
         configureSeekBars();
        // setupFontSpinner();
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


    // paint.setLinearText(true);
    // paint.setSubpixelText(true);
    // paint.setElegantTextHeight(true);

    private void configureSeekBars(){
        seekBarConfigurator.configure( R.id.textSkewSeekBar,
                R.integer.text_skew_seekbar_default,
                progress -> {
                    float skew = (progress - 100) / -100f;
                    paintGroup.setTextSkewX(skew);
                    viewModel.textSkewSeekBarProgress = (int)skew;
        });

        seekBarConfigurator.configure( R.id.textSpacingSeekBar,
                R.integer.text_spacing_seekbar_default,
                progress -> {
                    float spacing = (progress) / 100f;
                    paintGroup.setLetterSpacing(spacing);
                    viewModel.textSpacingSeekBarProgress = (int)spacing;
        });
    }


    private void setupCheckboxListener(final PaintGroup paintGroup){
        CheckBox.OnCheckedChangeListener checkedChangeListener = (compoundButton, b) -> {
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
        };
        assignCheckBoxListener(checkedChangeListener, R.id.checkboxTextBold, R.id.checkboxTextStrikethrough, R.id.checkboxTextUnderline);
    }

    private void setupFontSpinner(){
      /*
            SettingsUtils.setupSpinner2(activity,
                    R.id.gradientColorSpinner,
                    R.array.gradient_color_array,
                    R.array.gradient_color_values,
                    x -> paintHelperManager.getGradientHelper().setGradientColorType(x));
         */
        Typeface customTypeface = ResourcesCompat.getFont(activity, R.font.playfair_regular);
        paintGroup.setTypeface(customTypeface);


    }



    private void assignCheckBoxListener(CheckBox.OnCheckedChangeListener onCheckedChangeListener, int ... ids){
         for(int id : ids){
             CheckBox checkbox = activity.findViewById(id);
             checkbox.setOnCheckedChangeListener(onCheckedChangeListener);
         }
    }

}
