package com.jacstuff.sketchy.paintview.helpers;

import android.content.Context;
import android.graphics.Paint;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.brushes.styles.DashedStyle;
import com.jacstuff.sketchy.brushes.styles.DoubleEdgeStyle;
import com.jacstuff.sketchy.brushes.styles.FillStyle;
import com.jacstuff.sketchy.brushes.styles.JaggedStyle;
import com.jacstuff.sketchy.brushes.styles.OutlineStyle;
import com.jacstuff.sketchy.brushes.styles.SpikedStyle;
import com.jacstuff.sketchy.brushes.styles.Style;
import com.jacstuff.sketchy.brushes.styles.TranslateStyle;
import com.jacstuff.sketchy.brushes.styles.WavyStyle;
import com.jacstuff.sketchy.paintview.PaintGroup;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.HashMap;
import java.util.Map;

public class StyleHelper {

    private PaintGroup paintGroup;
    private final Context context;
    private Map<String, Paint.Cap> strokeCapMap;
    private Map<String, Paint.Join> strokeJoinMap;
    private Map <BrushStyle, Style> styleMap;
    private Style currentStyle;
    private final PaintView paintView;
    private final MainViewModel viewModel;


    public StyleHelper(Context context, PaintView paintView, MainViewModel viewModel){
        this.context = context;
        this.paintView = paintView;
        this.viewModel = viewModel;
        setupStrokeMap();
        setupJoinMap();
    }


    public void init(PaintGroup paintGroup){
        this.paintGroup = paintGroup;
        setupStyleMap();
    }


    public void setStokeCap(String type){
        Paint.Cap strokeCap = strokeCapMap.get(type);
        if(strokeCap != null){
            paintGroup.setStrokeCap(strokeCap);
        }
    }


    public void setStrokeJoin(String type){
        Paint.Join strokeJoin = strokeJoinMap.get(type);
        if(strokeJoin != null){
            paintGroup.setStrokeJoin(strokeJoin);
        }
    }


    public void setBrushStyle(BrushStyle brushStyle){
        currentStyle = styleMap.get(brushStyle);
        initCurrentStyle();
        paintView.getCurrentBrush().setStyle(currentStyle);
    }


    public Style getCurrentStyle(){
        return currentStyle;
    }


    public void initCurrentStyle(){
        currentStyle.init(paintGroup, (int)paintGroup.getLineWidth());
    }


    private void setupStrokeMap(){
        strokeCapMap = new HashMap<>(3);
        put(strokeCapMap, R.string.stroke_cap_spinner_round_item,  Paint.Cap.ROUND);
        put(strokeCapMap, R.string.stroke_cap_spinner_square_item, Paint.Cap.SQUARE);
        put(strokeCapMap, R.string.stroke_cap_spinner_butt_item,   Paint.Cap.BUTT);
    }


    private void setupJoinMap(){
        strokeJoinMap = new HashMap<>(3);
        put(strokeJoinMap, R.string.stroke_join_spinner_mitre_item, Paint.Join.MITER);
        put(strokeJoinMap, R.string.stroke_join_spinner_round_item, Paint.Join.ROUND);
        put(strokeJoinMap, R.string.stroke_join_spinner_bevel_item, Paint.Join.BEVEL);
    }


    private void setupStyleMap(){
        styleMap = new HashMap<>();
        styleMap.put(BrushStyle.FILL, new FillStyle());
        styleMap.put(BrushStyle.OUTLINE,           new OutlineStyle());
        styleMap.put(BrushStyle.BROKEN_OUTLINE,    new DashedStyle(paintGroup, viewModel));
        styleMap.put(BrushStyle.JAGGED,            new JaggedStyle(paintGroup));
        styleMap.put(BrushStyle.WAVY,              new WavyStyle(paintGroup, viewModel));
        styleMap.put(BrushStyle.SPIKED,            new SpikedStyle());
        styleMap.put(BrushStyle.DOUBLE_EDGE,       new DoubleEdgeStyle(paintGroup));
        styleMap.put(BrushStyle.TRANSLATE,         new TranslateStyle(paintGroup));

        currentStyle = styleMap.get(BrushStyle.FILL);
    }


    private <T> void put(Map<String, T> map, int id, T item){
        String str = getStr(id);
        map.put(str, item);
    }


    private String getStr(int resId){
        return context.getResources().getString(resId);
    }

}
