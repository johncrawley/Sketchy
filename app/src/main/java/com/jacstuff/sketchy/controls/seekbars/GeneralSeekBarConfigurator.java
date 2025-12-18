package com.jacstuff.sketchy.controls.seekbars;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jacstuff.sketchy.R;

import java.util.function.Consumer;

public class GeneralSeekBarConfigurator {

    public GeneralSeekBarConfigurator(){

    }


    public void setup(View parentView,
                      Context context,
                      int seekBarLayoutId,
                      int labelStrId,
                      int defaultValue,
                      Consumer<Integer> pogressConsumer,
                      Consumer<Integer> progressFinishedConsumer){

        ViewGroup layout = parentView.findViewById(seekBarLayoutId);
        SeekBar seekBar = layout.findViewById(R.id.seekBar);
        setupLabel(context, layout, labelStrId);

        TextView progressText = layout.findViewById(R.id.seekBarCurrentValueText);

        seekBar.setProgress(defaultValue);
        progressText.setText("" + defaultValue);

        seekBar.setOnSeekBarChangeListener( createSeekBarChangeListener(progressText, p -> {}, pogressConsumer, progressFinishedConsumer));
    }


    private SeekBar.OnSeekBarChangeListener createSeekBarChangeListener(TextView amountTextView,
                                                                        Consumer<Integer> progressStartedConsumer,
                                                                        Consumer<Integer> progressChangedConsumer,
                                                                        Consumer<Integer> progressFinishedConsumer){
        return new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progressChangedConsumer != null){
                    progressChangedConsumer.accept(progress);
                    var progressStr = "" + progress;
                    amountTextView.setText(progressStr);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
              //  viewModel.seekBarValue.put(seekBarId, progress);
                if(progressFinishedConsumer != null){
                    progressFinishedConsumer.accept(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(progressStartedConsumer != null){
                    progressStartedConsumer.accept(seekBar.getProgress());
                }
            }
        };
    }


    private void setupLabel(Context context, View parentView, int strId){
        TextView seekBarLabel = parentView.findViewById(R.id.seekBarLabel);
        var label = context.getString(strId);
        seekBarLabel.setText(label);
    }
}
