package com.jacstuff.sketchy.brushes;

public enum AngleType {
    ZERO(0),
    THIRTY(30),
    FORTY_FIVE(45),
    SIXTY(60),
    NINTY(90),
    ONE_THREE_FIVE(135),
    ONE_FIFTY(150),
    ONE_EIGHTY(180),
    TWO_TWENTY_FIVE(225),
    TWO_SEVENTY(270),
    THREE_FIFTEEN(315),
    MINUS_FIFTEEN(-15,true),
    PLUS_FIFTEEN(15,true),
    MINUS_THIRTY(-30, true),
    PLUS_THIRTY(30, true),
    MINUS_NINTY(-90, true),
    PLUS_NINTY(90, true),
    RANDOM(0),
    OTHER(0);

    private final int angleValue;
    private final boolean isIncremental;
    public int get(){
        return angleValue;
    }

    public boolean isIncremental(){
        return this.isIncremental;
    }

    AngleType(int angleValue){
        this(angleValue, false);
    }

    AngleType(int angleValue, boolean isIncremental){
        this.angleValue = angleValue;
        this.isIncremental = isIncremental;
    }

}
