package com.jacstuff.sketchy.brushes.shapes.initializer;

import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public interface BrushInitializer {

    void init(MainViewModel viewModel, PaintHelperManager paintHelperManager);
    void initialize();
}
