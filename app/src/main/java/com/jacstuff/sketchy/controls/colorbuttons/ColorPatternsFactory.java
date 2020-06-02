package com.jacstuff.sketchy.controls.colorbuttons;

import android.content.Context;
import android.content.res.Resources;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.multicolor.pattern.EvenNumbersPattern;
import com.jacstuff.sketchy.multicolor.pattern.FirstToLastPattern;
import com.jacstuff.sketchy.multicolor.pattern.FourColoursStartingAt;
import com.jacstuff.sketchy.multicolor.pattern.MiddleToEndPattern;
import com.jacstuff.sketchy.multicolor.pattern.MiddleToStartPattern;
import com.jacstuff.sketchy.multicolor.pattern.MulticolorPattern;
import com.jacstuff.sketchy.multicolor.pattern.OddNumbersPattern;
import com.jacstuff.sketchy.multicolor.pattern.ReversiblePattern;

import java.util.ArrayList;
import java.util.List;

class ColorPatternsFactory {

    private Resources res;

    ColorPatternsFactory(Context context){
        res = context.getResources();
    }


    List<MulticolorPattern> createColorPatterns(){

        List<MulticolorPattern> colorPatterns = new ArrayList<>();
        colorPatterns.add(new FirstToLastPattern( get(R.string.pattern_label_first_to_last)));
        colorPatterns.add(new ReversiblePattern( get(R.string.pattern_label_over_and_back)));
        colorPatterns.add(new MiddleToEndPattern( get(R.string.pattern_label_middle_to_end)));
        colorPatterns.add(new MiddleToStartPattern( get(R.string.pattern_label_middle_to_start)));
        colorPatterns.add(new OddNumbersPattern( get(R.string.pattern_label_caterpillar)));
        colorPatterns.add(new EvenNumbersPattern( get(R.string.pattern_label_even_selection)));
        colorPatterns.add(new FourColoursStartingAt(0,  get(R.string.pattern_label_ice_cream)));
        colorPatterns.add(new FourColoursStartingAt(1, get(R.string.pattern_label_fresh)));
        colorPatterns.add(new FourColoursStartingAt(2, get(R.string.pattern_label_humbug)));
        colorPatterns.add(new FourColoursStartingAt(3, get(R.string.pattern_label_spooky)));
        colorPatterns.add(new FourColoursStartingAt(4, get(R.string.pattern_label_icing)));

        return colorPatterns;
    }

    private String get(int stringId){
        return res.getString(stringId);
    }


    List<MulticolorPattern> createShadePatterns(){
        List<MulticolorPattern> shadePatterns = new ArrayList<>();
        shadePatterns.add(new FirstToLastPattern( get(R.string.pattern_label_first_to_last)));
        shadePatterns.add(new ReversiblePattern( get(R.string.pattern_label_pulse)));
        shadePatterns.add(new MiddleToEndPattern( get(R.string.pattern_label_to_light)));
        shadePatterns.add(new MiddleToStartPattern( get(R.string.pattern_label_to_dark)));
        shadePatterns.add(new OddNumbersPattern( get(R.string.pattern_label_snake)));
        shadePatterns.add(new FourColoursStartingAt(0, get(R.string.pattern_label_macaron)));

        return shadePatterns;
    }

}
