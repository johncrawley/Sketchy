package com.jacstuff.sketchy.viewmodel;

import android.graphics.Color;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.settings.placement.PlacementType;
import com.jacstuff.sketchy.paintview.helpers.gradient.GradientColorType;
import com.jacstuff.sketchy.paintview.helpers.shadow.ShadowOffsetType;
import com.jacstuff.sketchy.paintview.helpers.size.sequence.proximity.ProximityFocalPoint;
import com.jacstuff.sketchy.paintview.helpers.size.sequence.proximity.ProximityType;
import com.jacstuff.sketchy.paintview.history.HistoryItem;
import com.jacstuff.sketchy.viewmodel.controls.ColorSequenceControls;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel implements ControlsHolder {


    private ColorSequenceControls colorSequenceControls;

    public ColorSequenceControls getColorSequenceControls(){
        return this.colorSequenceControls;
    }

    public void setColorSequenceControls(ColorSequenceControls colorSequenceControls){
        this.colorSequenceControls = colorSequenceControls;
    }

    public boolean isFirstExecution = true;
    public int lastClickedColorButtonId;
    public ArrayDeque<HistoryItem> bitmapHistoryItems;
    public String mostRecentColorButtonKey;
    public String mostRecentShadeButtonKey;
    public boolean isMostRecentClickAShade;
    public List<String> selectedShadeButtonKeys;
    public Map<Integer, Integer> seekBarValue;

    public Map<ButtonCategory, Integer> settingsButtonsClickMap;


    public boolean isKaleidoscopeCentred = true;

    public boolean isDrawOnMoveModeEnabled = true;

    public int brushSize;
    public int brushSizeSetBySeekBar;
    public int sizeSequenceMin = 5;
    public int sizeSequenceMax;
    public int sizeSequenceIncrement = 2;
    public boolean isSizeSequenceRepeated = true;
    public boolean isSizeSequenceResetOnTouchUp = true;
    public boolean isProximityInverted = false;
    public ProximityFocalPoint sizeSequenceProximityFocalPoint;
    public ProximityType proximityType;

    public int gradient;
    public int gradientMaxLength;
    public int radialGradientRadius=1;
    public int clampRadialGradientRadius = 10;
    public int linearGradientLength = 100;
    public int radialGradientOffsetX = 0;
    public int radialGradientOffsetY = 0;
    public GradientColorType gradientColorType = GradientColorType.SELECTED;
    public boolean snapRectangleToEdge =true;


    public PlacementType placementType = PlacementType.NORMAL;
    public boolean isPlacementQuantizationLocked = false;
    public float placementQuantizationFactor;
    public float placementQuantizationSavedBrushSize;
    public boolean isPlacementQuantizationLineWidthIncluded = true;
    public int randomPlacementMaxDistancePercentage;
    public float randomPlacementMaxDistance = 1;


    public int shadowSize = 1;
    public int shadowDistance =1;
    public float shadowOffsetX;
    public float shadowOffsetY;
    public ShadowOffsetType shadowOffsetType = ShadowOffsetType.USE_SHAPE_WIDTH;

    public int colorAlpha = 0;
    public int secondaryColor = Color.WHITE;
    public String textBrushText = "";
    public int color = Color.BLACK;
    public int previousColor = Color.WHITE;

    public boolean isInfinityModeEnabled = false;

    public int astroidShapeCurveRate = 100;
    public boolean useSeekBarAngle = false;
    public int textSkewSeekBarProgress = 100;
    public int textSpacingSeekBarProgress = 1;
    public int angle = 0;


    public MainViewModel(){
        seekBarValue = new HashMap<>();
    }
}
