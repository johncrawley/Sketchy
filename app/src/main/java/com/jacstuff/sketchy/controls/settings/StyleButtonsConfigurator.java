package com.jacstuff.sketchy.controls.settings;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.PaintView;

import androidx.core.util.Consumer;


public class StyleButtonsConfigurator extends AbstractButtonConfigurator<BrushStyle> implements ButtonsConfigurator<BrushStyle>{


    public StyleButtonsConfigurator(MainActivity activity, PaintView paintView){
        super(activity, paintView);
        setupSpinners();
    }


    @Override
    public void configure(){
        buttonConfig = new ButtonConfigHandler<>(activity,
                this,
                ButtonCategory.STYLE,
                R.id.styleOptionsLayout);
        buttonConfig.add(R.id.brokenOutlineStyleButton, R.drawable.broken_outline_style_button, BrushStyle.BROKEN_OUTLINE);
        buttonConfig.add(R.id.fillStyleButton,          R.drawable.fill_style_button,           BrushStyle.FILL);
        buttonConfig.add(R.id.outlineStyleButton,       R.drawable.outline_style_button,        BrushStyle.OUTLINE);
        buttonConfig.add(R.id.jaggedStyleButton,        R.drawable.jagged_style_button,           BrushStyle.JAGGED);
        buttonConfig.add(R.id.wavyStyleButton,          R.drawable.button_style_wavy_b,               BrushStyle.WAVY);
        buttonConfig.add(R.id.doubleEdgeStyleButton,   R.drawable.double_edge_style_button,       BrushStyle.DOUBLE_EDGE);
        buttonConfig.add(R.id.spikedStyleButton,       R.drawable.spiked_style_button,            BrushStyle.SPIKED);
        buttonConfig.add(R.id.translateStyleButton,    R.drawable.translate_outline_style_button, BrushStyle.TRANSLATE);

        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.styleButton);
        buttonConfig.setDefaultSelection(R.id.fillStyleButton);
    }


    @Override
    public void handleClick(int viewId, BrushStyle brushStyle) {
        paintView.setBrushStyle(brushStyle);
    }


    private void setupSpinners(){
        setupSpinner(R.id.strokeCapSpinner, R.array.stroke_cap_array,  x -> paintHelperManager.getStyleHelper().setStokeCap(x));
        setupSpinner(R.id.strokeJoinSpinner,R.array.stroke_join_array, x -> paintHelperManager.getStyleHelper().setStrokeJoin(x));
    }


    private void setupSpinner(int spinnerId, int itemsArrayId, final Consumer<String> paintAction){
        Spinner spinner = activity.findViewById(spinnerId);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                itemsArrayId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String)adapterView.getItemAtPosition(i);

                System.out.println("s9 about to run action for item " + item);
                paintAction.accept(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(itemSelectedListener);

    }



}
