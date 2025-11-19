package com.jacstuff.sketchy.ui;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.controls.settings.menu.ConnectedBrushIconModifier;
import com.jacstuff.sketchy.controls.settings.menu.ConnectedBrushState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ConnectedBrushIconModifierHelper {

    private final MainActivity mainActivity;
    private ConnectedBrushIconModifier connectedLineIconModifier, connectedTriangleIconModifier;
    private List<ConnectedBrushIconModifier> iconModifiers;


    public ConnectedBrushIconModifierHelper(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        setupIconModifiers();
    }


    private void setupIconModifiers(){
        iconModifiers = new ArrayList<>();
        initLineIconModifier();
        initTriangleIconModifier();
    }


    public ConnectedBrushIconModifier getConnectedLineIconModifier(){
        return connectedLineIconModifier;
    }


    public ConnectedBrushIconModifier getConnectedTriangleIconModifier(){
        return connectedTriangleIconModifier;
    }


    public List<ConnectedBrushIconModifier> getIconModifiers(){
        return iconModifiers;
    }


    private void initLineIconModifier(){
        var viewModel = mainActivity.getViewModel();
        Supplier<ConnectedBrushState> connectedBrushStateSupplier = ()-> viewModel.drawHistory.getLineState();
        connectedLineIconModifier = new ConnectedBrushIconModifier(mainActivity, connectedBrushStateSupplier, BrushShape.LINE);
        connectedLineIconModifier.assignConnectedIconResId(R.drawable.button_shape_line_connected);
        connectedLineIconModifier.assignNormalIconId(R.drawable.button_shape_line);
        iconModifiers.add(connectedLineIconModifier);
    }


    private void initTriangleIconModifier(){
        var viewModel = mainActivity.getViewModel();
        Supplier<ConnectedBrushState> connectedBrushStateSupplier = ()-> viewModel.drawHistory.getTriangleState();
        connectedTriangleIconModifier = new ConnectedBrushIconModifier(mainActivity, connectedBrushStateSupplier,  BrushShape.TRIANGLE_ARBITRARY);
        connectedTriangleIconModifier.assignConnectedIconResId(R.drawable.button_shape_triangle_arbitrary_connected);
        connectedTriangleIconModifier.assignNormalIconId(R.drawable.button_shape_triangle_arbitrary);
        iconModifiers.add(connectedTriangleIconModifier);
    }


}
