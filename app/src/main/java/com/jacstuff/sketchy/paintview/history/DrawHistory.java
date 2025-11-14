package com.jacstuff.sketchy.paintview.history;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;


public class DrawHistory {

    private List<HistoryItem> history;
    private int currentIndex;
    //private final List<ConnectedBrushIconModifier> iconModifiers;
    //List<ConnectedBrushIconModifier> iconModifiers


    public DrawHistory() {
        history = new ArrayList<>(50);
    }


    public boolean isEmpty() {
        return history.isEmpty();
    }


    public void push(Bitmap bitmap, int screenOrientation, boolean isLowOnMemory) {
        boolean hasEnoughSpace = removeItemIfLowOnMemory(bitmap, isLowOnMemory);
        if (hasEnoughSpace) {
            if(!isLatest()){
                removeAllFutureItems();
            }
            var historyItem = new HistoryItem(Bitmap.createBitmap(bitmap), screenOrientation);
            copyOldBrushStatesTo(historyItem);
            history.add(historyItem);
            currentIndex = history.size() - 1;
        }
    }


    private void copyOldBrushStatesTo(HistoryItem historyItem){
        if(history.isEmpty() || currentIndex >= history.size()){
            return;
        }
        var currentItem = history.get(currentIndex);
        if(currentItem != null){
            var existingConnectedLineState = currentItem.getConnectedLineState();
            historyItem.setConnectedLineState(existingConnectedLineState);
        }
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


    private boolean removeItemIfLowOnMemory(Bitmap bitmap, boolean isLowOnMemory) {
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


    public HistoryItem getPrevious() {
        log("entered getPrevious()");
        if (history.isEmpty()) {
            log("getPrevious(): history is empty");
            return null;
        }
        currentIndex = Math.max(0, currentIndex - 1);
        return history.get(currentIndex);
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
        int index = history.size() - 1;
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

