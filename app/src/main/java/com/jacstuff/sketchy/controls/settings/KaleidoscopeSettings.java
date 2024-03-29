package com.jacstuff.sketchy.controls.settings;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.PaintView;

public class KaleidoscopeSettings extends AbstractButtonConfigurator<Integer> implements ButtonsConfigurator<Integer>{


    public KaleidoscopeSettings(MainActivity activity, PaintView paintView){
        super(activity, paintView);
        subPanelManager.setDefaultLayout(R.id.kaleidoscopeOptionsInclude);
        subPanelManager.registerButtonWithoutPanel(R.id.kOffButton);
    }


    @Override
    public void configure(){
        buttonConfig = new ButtonConfigHandler<>(activity,
                this,
                ButtonCategory.KALEIDOSCOPE,
                R.id.kaleidoscopeOptionsLayout);

        add(R.id.kOffButton,1);
        add(R.id.k2Button,2);
        add(R.id.k3Button,3);
        add(R.id.k4Button,4);
        add(R.id.k5Button,5);
        add(R.id.k6Button,6);
        add(R.id.k7Button,7);
        add(R.id.k8Button,8);
        add(R.id.k9Button,9);
        add(R.id.k10Button,10);
        add(R.id.k12Button,12);
        add(R.id.k16Button, 16);

        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.kaleidoscopeButton);
        buttonConfig.setDefaultSelection(R.id.kOffButton);
        setupKaleidoscopeOptions();
    }


    private void add(int id, int action){
        buttonConfig.add(id, "K: " + action, action);
    }


    public void setupKaleidoscopeOptions(){
        setupSwitch(R.id.kaleidoscopeInfinityMode, b ->viewModel.isInfinityModeEnabled = b );
        setupSwitch(R.id.kaleidoscopeCentredSwitch, b ->viewModel.isKaleidoscopeCentred = b );
    }


    @Override
    public void handleClick(int viewId, Integer numberOfSegments){
        paintHelperManager.getKaleidoscopeHelper().setSegments(numberOfSegments);
    }

}