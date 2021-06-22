package com.jacstuff.sketchy.controls.settings;

public interface ButtonsConfigurator <T> {

   void handleClick(int viewId, T actionType);
   void handleDefaultClick(int viewId, T actionType);
}
