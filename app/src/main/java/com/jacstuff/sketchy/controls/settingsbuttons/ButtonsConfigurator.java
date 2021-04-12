package com.jacstuff.sketchy.controls.settingsbuttons;

public interface ButtonsConfigurator <T> {

   void handleClick(int viewId, T t);
   void saveSelection(int viewId);
}
