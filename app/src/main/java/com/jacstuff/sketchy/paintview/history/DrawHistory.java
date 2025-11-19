package com.jacstuff.sketchy.paintview.history;

import android.graphics.Bitmap;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.shapes.twostep.TrianglePoints;
import com.jacstuff.sketchy.controls.settings.menu.ConnectedBrushState;

import java.util.ArrayList;
import java.util.List;


public class DrawHistory {

    private List<HistoryItem> history;
    private int currentIndex;
    private int id;
    private PointF lineUpCoordinates = new PointF(0,0);
    private PointF trianglePoint1 , trianglePoint2, trianglePoint3;

    private final ConnectedBrushState lineState, triangleState;


    public DrawHistory() {
        history = new ArrayList<>(50);
        lineState = new ConnectedBrushState();
        triangleState = new ConnectedBrushState();
        trianglePoint1 = TrianglePoints.getDefaultPoint();
        trianglePoint2 = TrianglePoints.getDefaultPoint();
        trianglePoint3 = TrianglePoints.getDefaultPoint();
    }


    public ConnectedBrushState getLineState(){
        return lineState;
    }


    public PointF getLineUpCoordinates(){
        return lineUpCoordinates;
    }


    public ConnectedBrushState getTriangleState(){
        return triangleState;
    }


    public boolean isEmpty() {
        return history.isEmpty();
    }


    public void setConnectedLineMode(boolean isEnabled){
        lineState.setConnectedModeEnabled(isEnabled);
        if(!isEnabled){
            resetConnectedLine();
        }
    }


    public void setConnectedTriangleMode(boolean isEnabled){
        triangleState.setConnectedModeEnabled(isEnabled);
        if(!isEnabled){
            resetConnectedTriangles();
        }
    }


    public void resetConnectedLine(){
        lineState.setFirstItemDrawn(false);
    }


    public void resetConnectedTriangles(){
        triangleState.setFirstItemDrawn(false);
    }


    public void push(Bitmap bitmap, int screenOrientation, boolean isLowOnMemory) {
        boolean hasEnoughSpace = removeItemIfLowOnMemory(isLowOnMemory);
        if (hasEnoughSpace) {
            if(!isLatest()){
                removeAllFutureItems();
            }
            var historyItem = new HistoryItem(Bitmap.createBitmap(bitmap), screenOrientation, ++id);
           // copyOldBrushStatesTo(historyItem);
            setConnectedLineCoordinates(historyItem);
            addTrianglePointsTo(historyItem);
            history.add(historyItem);
            printItems();
            currentIndex = history.size() - 1;
        }
    }


    private void setConnectedLineCoordinates(HistoryItem item){
        var lineState = item.getConnectedLineState();
        lineState.setConnectedModeEnabled(this.lineState.isConnectedModeEnabled());
        if(this.lineState.isConnectedModeEnabled()){
            lineState.setFirstItemDrawn(true);
            this.lineState.setFirstItemDrawn(true);
            item.setConnectedLineUpCoordinates(lineUpCoordinates.x, lineUpCoordinates.y);

        }
    }


    public void saveThirdTrianglePoint(PointF p3){
        trianglePoint3 = p3;
    }


    private void addTrianglePointsTo(HistoryItem historyItem){
        var currentItem = getCurrent();
        var triangleState = historyItem.getConnectedTriangleState();
        triangleState.setConnectedModeEnabled(this.triangleState.isConnectedModeEnabled());
        if(!this.triangleState.isConnectedModeEnabled()){
            return;
        }
        if(currentIndex > 1
                && currentItem != null
                && this.triangleState.isFirstItemDrawn()){
            historyItem.createTrianglePointsFrom(currentItem);
        }
        if(!this.triangleState.isFirstItemDrawn()){
            historyItem.clearTrianglePoints();
        }
        historyItem.addFirstPoints(trianglePoint1, trianglePoint2, trianglePoint3);
        triangleState.setFirstItemDrawn(true);
        this.triangleState.setFirstItemDrawn(true);
    }


    public void addTrianglePoints(PointF p1, PointF p2){
        this.trianglePoint1 = p1;
        this.trianglePoint2 = p2;
    }


    private void copyOldBrushStatesTo(HistoryItem historyItem){
        if(history.isEmpty() || currentIndex >= history.size()){
            return;
        }
        var currentItem = history.get(currentIndex);
        if(currentItem != null){
            var lineState = currentItem.getConnectedLineState();
            historyItem.setConnectedLineState(lineState);

            var triangleState = currentItem.getConnectedTriangleState();
            historyItem.setConnectedTriangleState(triangleState);
        }
    }


    public void saveLineUpCoordinates(float x, float y){
        lineUpCoordinates = new PointF(x,y);
    }


    private void removeAllFutureItems(){
        history = history.subList(0, currentIndex + 1);
    }


    private boolean isLatest(){
        return history.isEmpty() || currentIndex == history.size() -1;
    }


    public int size() {
        return history == null ? 0 : history.size();
    }


    private boolean removeItemIfLowOnMemory( boolean isLowOnMemory) {
        if (isLowOnMemory) {
            if (history.isEmpty()) {
                //nothing to remove and not enough memory to save the latest bitmap
                return false;
            }
            history = history.subList(1, history.size());
            currentIndex--;
        }
        return true;
    }


    public boolean isConnectedTriangleModeEnabled(){
        return triangleState.isConnectedModeEnabled();
    }


    public HistoryItem assignPrevious() {
        currentIndex = Math.max(0, currentIndex - 1);
        var item = history.isEmpty() ? null : history.get(currentIndex);
        assignStateFrom(item);
        return item;
    }


    private void assignStateFrom(HistoryItem item){
        if(item != null){
            triangleState.updateFrom(item.getConnectedTriangleState());
            lineState.updateFrom(item.getConnectedLineState());
            lineUpCoordinates = item.getLineUpCoordinates();
        }
    }


    public void printItems(){
        var str = new StringBuilder("printItems() : ");
        for(var item : history){
            str.append(" ");
            str.append(item.getId());
        }
        log(str.toString());

    }


    private void log(String msg){
        System.out.println("^^^ DrawHistory: " + msg);
    }


    public HistoryItem getNext() {
        if (history.isEmpty()) {
            return null;
        }
        currentIndex = Math.min(history.size() - 1, currentIndex + 1);
        var item =  history.get(currentIndex);
        assignStateFrom(item);
        return item;
    }


    public HistoryItem getCurrent() {
        if (null != history && !history.isEmpty()) {
            return getLatestItem();
        }
        return null;
    }


    public boolean shouldDrawConnectedTriangle(){
        return triangleState.isConnectedModeEnabled() && isFirstTriangleDrawn();
    }


    public boolean isFirstTriangleDrawn(){
        var currentItem = getCurrent();
        return currentItem != null
                && currentItem.getConnectedTriangleState().isFirstItemDrawn();

    }


    private HistoryItem getLatestItem() {
        return getLast();
    }


    private HistoryItem getLast() {
        int index = currentIndex < history.size() ? currentIndex : history.size() -1;
        return history.get(index);
    }

}

