package com.jacstuff.sketchy.paintview.history;

import android.graphics.Bitmap;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;


public class DrawHistory {

    private List<HistoryItem> history;
    private int currentIndex;
    private int id;
    private PointF lineUpCoordinates = new PointF(0,0);
    //private final List<ConnectedBrushIconModifier> iconModifiers;
    //List<ConnectedBrushIconModifier> iconModifiers


    public DrawHistory() {
        history = new ArrayList<>(50);
    }


    public boolean isEmpty() {
        return history.isEmpty();
    }


    public void push(Bitmap bitmap, int screenOrientation, boolean isLowOnMemory) {
        log("Entered push() size: " + history.size() + " is low on memory: " + isLowOnMemory);
        boolean hasEnoughSpace = removeItemIfLowOnMemory(isLowOnMemory);
        if (hasEnoughSpace) {
            log("push() there is enough space to save a history");
            if(!isLatest()){
                log("push() removing all future items");
                removeAllFutureItems();
            }
            var historyItem = new HistoryItem(Bitmap.createBitmap(bitmap), screenOrientation, ++id);
            copyOldBrushStatesTo(historyItem);
            historyItem.setConnectedLineUpCoordinates(lineUpCoordinates.x, lineUpCoordinates.y);
            history.add(historyItem);
            log("history item added, ID: " +  id);
            printItems();
            currentIndex = history.size() - 1;
        } else{
            log("not enough space to save history");
        }
    }


    private void copyOldBrushStatesTo(HistoryItem historyItem){
        log("entered copyOldBrushStatesTo()");
        if(history.isEmpty() || currentIndex >= history.size()){
            log("exiting copyOldBrushStatesTo() history is empty or current index is greater than size()");
            return;
        }
        var currentItem = history.get(currentIndex);
        if(currentItem != null){
            var existingConnectedLineState = currentItem.getConnectedLineState();
            historyItem.setConnectedLineState(existingConnectedLineState);
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


    public HistoryItem assignPrevious() {
        log("entered assignPrevious()");
        if (history.isEmpty()) {
            log("assignPrevious(): history is empty");
            return null;
        }
        currentIndex = Math.max(0, currentIndex - 1);
        log("assignPrevious() UNDO");
        printItems();
        log("assignPrevious() currentIndex = " + currentIndex);
        return history.get(currentIndex);
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
        return  history.get(currentIndex);
    }


    public HistoryItem getCurrent() {
        if (null != history && !history.isEmpty()) {
            return getLatestItem();
        }
        return null;
    }


    public void updateNewestItemWithState() {
        if (history.isEmpty()) {
            return;
        }
        var historyItem = getLatestItem();
        if (historyItem == null) {
            return;
        }
    //    historyItem.updateViewModelState(viewModel);
    }


    private HistoryItem getLatestItem() {
        return getLast();
    }


    private HistoryItem getLast() {
        int index = currentIndex < history.size() ? currentIndex : history.size() -1;
        return history.get(index);
    }

/*
    private void assignToViewModel(HistoryItem historyItem) {
        if (historyItem != null) {
            historyItem.assignSavedStateTo(viewModel);
        }
    }


    private void updateIcons(HistoryItem historyItem) {
        if (historyItem != null) {
            for (var iconModifier : iconModifiers) {
                iconModifier.updateIcon();
            }
        }
    }
*/
}

