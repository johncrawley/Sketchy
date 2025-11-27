package com.jacstuff.sketchy.multicolor;

import java.util.List;

public interface ColorSelector {

    void reset();

    void setColorList(List<Integer> colorList);

    void setColorList(int color);

    void updateRangeIndexes();

    void add(int id, List<Integer> shades);

    void remove(int id);
    void removeAllBut (int id);

    void resetCurrentIndex();

    int getNextColor();

}
