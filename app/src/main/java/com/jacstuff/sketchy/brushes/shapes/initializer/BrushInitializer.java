package com.jacstuff.sketchy.brushes.shapes.initializer;

import android.app.Activity;
import android.content.Context;

import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public interface BrushInitializer {

    void init(Activity activity, MainViewModel viewModel, PaintHelperManager paintHelperManager);
    void initialize();
    void deInitialize();
}
