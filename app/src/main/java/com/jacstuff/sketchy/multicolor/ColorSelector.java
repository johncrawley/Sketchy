package com.jacstuff.sketchy.multicolor;

import android.graphics.Color;

import java.util.List;

public interface ColorSelector {

    void reset();

    void set(List<Color> colorList);

    void set(Color color);

    void nextPattern();


    void resetCurrentIndex();

    int getNextColor();

}
