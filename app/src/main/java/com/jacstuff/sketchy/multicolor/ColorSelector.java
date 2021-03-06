package com.jacstuff.sketchy.multicolor;

import android.graphics.Color;

import java.util.List;

public interface ColorSelector {

    void reset();

    void set(List<Integer> colorList);

    void set(int color);

    void nextPattern();

    void add(int id, List<Integer> shades);

    void remove(int id);

    void resetCurrentIndex();

    int getNextColor();

    String getCurrentPatternLabel();

}
