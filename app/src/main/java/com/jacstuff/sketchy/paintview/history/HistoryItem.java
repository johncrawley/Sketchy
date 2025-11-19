package com.jacstuff.sketchy.paintview.history;

import android.graphics.Bitmap;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.shapes.twostep.TrianglePoints;
import com.jacstuff.sketchy.controls.settings.menu.ConnectedBrushState;

public class HistoryItem {
    private final Bitmap bitmap;
    private PointF connectedLinePreviousDown;
    private final int savedOrientation;
    private ConnectedBrushState connectedLineState;
    private ConnectedBrushState connectedTriangleState;
    private TrianglePoints trianglePoints;
    private final int id;

    public HistoryItem(Bitmap bitmap, int savedOrientation, int id){
        this.bitmap = bitmap;
        this.savedOrientation = savedOrientation;
        this.connectedLineState = new ConnectedBrushState();
        this.connectedTriangleState = new ConnectedBrushState();
        connectedLinePreviousDown = new PointF(0,0);
        trianglePoints = new TrianglePoints();
        this.id = id;
    }


    public int getId(){
        return id;
    }


    public void createTrianglePointsFrom(HistoryItem historyItem){
        trianglePoints = new TrianglePoints(historyItem.getTrianglePoints());
    }


    public TrianglePoints getTrianglePoints(){
        return trianglePoints;
    }


    public Bitmap getBitmap(){
        return this.bitmap;
    }


    public int getOrientation(){
        return this.savedOrientation;
    }


    public void setConnectedLineUpCoordinates(float x, float y){
        this.connectedLinePreviousDown = new PointF(x, y);
    }


    public void addFirstPoints(PointF ...points){
        trianglePoints.addPoints(points);
    }

    public void clearTrianglePoints(){
        trianglePoints.clear();
    }


    public ConnectedBrushState getConnectedLineState(){
        return connectedLineState;
    }


    public ConnectedBrushState getConnectedTriangleState(){
        return connectedTriangleState;
    }


    public void setConnectedLineState(ConnectedBrushState cbs){
        connectedLineState = new ConnectedBrushState(cbs);
    }


    public void setConnectedTriangleState(ConnectedBrushState cbs){
        connectedTriangleState = new ConnectedBrushState(cbs);
    }


    public PointF getLineUpCoordinates(){
        return connectedLinePreviousDown;
    }

}
