package com.jacstuff.sketchy.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.jacstuff.sketchy.MainActivity;

public class FragmentHelper {


    public static void startColorSettingsFragment(MainActivity activity){
        ColorSettingsDialogFragment colorSettingsDialogFragment = ColorSettingsDialogFragment.newInstance();
        showDialogFragment(activity, "colorSettings", colorSettingsDialogFragment);
    }


    public static void startOptionsFragment(MainActivity activity){
        OptionsDialogFragment fragment = OptionsDialogFragment.newInstance();
        showDialogFragment(activity, "optionsDialog", fragment);
    }


    private static void showDialogFragment(MainActivity activity, String tag, DialogFragment fragment){
        Bundle bundle = new Bundle();
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        removePreviousFragmentTransaction(activity, tag, fragmentTransaction);

        fragment.setArguments(bundle);
        fragment.show(fragmentTransaction, tag);
    }


    private static void removePreviousFragmentTransaction(MainActivity activity, String tag, FragmentTransaction fragmentTransaction){
        Fragment prev = activity.getSupportFragmentManager().findFragmentByTag(tag);
        if (prev != null) {
            fragmentTransaction.remove(prev);
        }
        fragmentTransaction.addToBackStack(null);
    }
}
