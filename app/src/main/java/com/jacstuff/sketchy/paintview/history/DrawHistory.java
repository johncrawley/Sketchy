package com.jacstuff.sketchy.paintview.history;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.settings.menu.ConnectedBrushIconModifier;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;


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
        log("entered push()");
        boolean hasEnoughSpace = removeItemIfLowOnMemory(bitmap, isLowOnMemory);
        if (hasEnoughSpace) {
            if(!isLatest()){
                history = history.subList(0, currentIndex + 1);
            }
            history.add(new HistoryItem(Bitmap.createBitmap(bitmap),
                    screenOrientation));
            currentIndex = history.size() - 1;
        }
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
        var item = history.get(currentIndex);
        return item;
    }


    private void log(String msg){
        System.out.println("^^^ DrawHistory: " + msg);
    }


    public HistoryItem getNext() {
        if (history.isEmpty()) {
            return null;
        }
        currentIndex = Math.min(history.size() - 1, currentIndex + 1);
        var item = history.get(currentIndex);
        return item;
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
        var historyItem = getLast();
        return historyItem;
    }


    private HistoryItem getLast() {
        // cannot use getLast() on minSdk < 35
        return history.get(history.size() - 1);
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

