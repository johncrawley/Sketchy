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
import com.jacstuff.sketchy.paintview.helpers.StyleHelper;


public class StyleButtonsConfigurator extends AbstractButtonConfigurator<BrushStyle> implements ButtonsConfigurator<BrushStyle>{


    public StyleButtonsConfigurator(MainActivity activity, PaintView paintView){
        super(activity, paintView);
        setupCapSelection();
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



    private void setupCapSelection(){
        Spinner spinner = activity.findViewById(R.id.strokeCapSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.stroke_cap_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String)adapterView.getItemAtPosition(i);
                paintHelperManager.getStyleHelper().setStokeCap(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(itemSelectedListener);

    }
}
