package com.jacstuff.sketchy.multicolor;

import android.graphics.Color;
import android.util.Log;

import com.jacstuff.sketchy.multicolor.pattern.EvenNumbersPattern;
import com.jacstuff.sketchy.multicolor.pattern.FirstToLastPattern;
import com.jacstuff.sketchy.multicolor.pattern.FourColoursStartingAt;
import com.jacstuff.sketchy.multicolor.pattern.MiddleToEndPattern;
import com.jacstuff.sketchy.multicolor.pattern.MiddleToStartPattern;
import com.jacstuff.sketchy.multicolor.pattern.MulticolorPattern;
import com.jacstuff.sketchy.multicolor.pattern.OddNumbersPattern;
import com.jacstuff.sketchy.multicolor.pattern.RandomPattern;
import com.jacstuff.sketchy.multicolor.pattern.ReversiblePattern;

import java.util.ArrayList;
import java.util.List;

public class MulticolorHandler {

    private List<Color> colors;
    private boolean isEnabled = false;
    private MulticolorPattern currentMulticolorPattern;
    private List<MulticolorPattern> multicolorPatterns;
    private int currentPatternIndex = 0;
    private int currentColor;

    public MulticolorHandler(){
        initPatterns();
    }


    private void initPatterns(){
        multicolorPatterns = new ArrayList<>();
        multicolorPatterns.add(new FirstToLastPattern());
        multicolorPatterns.add(new ReversiblePattern());
        multicolorPatterns.add(new MiddleToEndPattern());
        multicolorPatterns.add(new MiddleToStartPattern());
        multicolorPatterns.add(new OddNumbersPattern());
        multicolorPatterns.add(new EvenNumbersPattern());
        multicolorPatterns.add(new FourColoursStartingAt(0));
        multicolorPatterns.add(new FourColoursStartingAt(1));
        multicolorPatterns.add(new FourColoursStartingAt(2));
        multicolorPatterns.add(new FourColoursStartingAt(3));
        multicolorPatterns.add(new FourColoursStartingAt(4));
        multicolorPatterns.add(new RandomPattern());
        currentMulticolorPattern = multicolorPatterns.get(currentPatternIndex);
    }


    public int getNextColor(){
        int currentIndex = currentMulticolorPattern.getNextIndex(colors.size());
        return colors.get(currentIndex).toArgb();
    }


    private void chooseNextPattern(){
        currentPatternIndex = isMaxedOut(currentPatternIndex) ? 0 : currentPatternIndex + 1;
        currentMulticolorPattern = multicolorPatterns.get(currentPatternIndex);
        currentMulticolorPattern.resetIndex();
    }

    private void setToDefaultPattern(){
        currentPatternIndex = 0;
        currentMulticolorPattern = multicolorPatterns.get(currentPatternIndex);
    }

    private boolean isMaxedOut(int index){
        return index >= multicolorPatterns.size() -1;
    }

    public boolean isEnabled(){
        return isEnabled;
    }

    public void resetIndex(){
        currentMulticolorPattern.resetIndex();
    }

    public void disable(){
        isEnabled = false;
    }



    public void enable(List<Color> inputList){
        int newestSampleColor = getFirstColorInList(inputList);
        if(isEnabled && newestSampleColor == currentColor){
            chooseNextPattern();
        }
        else{
            setToDefaultPattern();
        }
        isEnabled = true;
        colors = inputList;
        currentColor = newestSampleColor;
    }


    private int getFirstColorInList(List<Color> colorList){
        if(colorList == null){
            return 0;
        }
        return colorList.get(0).toArgb();
    }



}
