package com.jacstuff.sketchy.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;

import java.util.function.Consumer;

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


    public class FragmentUtils {



        public static void showDialog(Fragment parentFragment, DialogFragment dialogFragment, String tag, Bundle bundle){
            var fragmentManager = parentFragment.getParentFragmentManager();
            var fragmentTransaction = fragmentManager.beginTransaction();
            removePreviousFragmentTransaction(fragmentManager, tag, fragmentTransaction);
            dialogFragment.setArguments(bundle);
            dialogFragment.show(fragmentTransaction, tag);
        }


        public static void loadFragment(Fragment parentFragment, Fragment fragment, String tag, Bundle bundle){
            var fragmentManager = parentFragment.getParentFragmentManager();
            var fragmentTransaction = fragmentManager.beginTransaction();
            removePreviousFragmentTransaction(fragmentManager, tag, fragmentTransaction);
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.pop_enter, R.anim.pop_exit )
                    .replace(R.id.fragment_container, fragment, tag)
                    .addToBackStack(null)
                    .commit();
        }


        public static void onBackButtonPressed(Fragment parentFragment, Runnable action){
            var callback = new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    action.run();
                }
            };
            parentFragment.requireActivity()
                    .getOnBackPressedDispatcher()
                    .addCallback(parentFragment.getViewLifecycleOwner(), callback);
        }


        public static void loadFragment(Fragment parentFragment, Fragment fragment, String tag){
            loadFragment(parentFragment, fragment, tag, new Bundle());
        }


        private static void removePreviousFragmentTransaction(FragmentManager fragmentManager, String tag, FragmentTransaction fragmentTransaction){
            var prev = fragmentManager.findFragmentByTag(tag);
            if (prev != null) {
                fragmentTransaction.remove(prev);
            }
            fragmentTransaction.addToBackStack(null);
        }


        /*
        public static void setListener(Fragment fragment, MessageKey key, Consumer<Bundle> consumer){
            fragment.getParentFragmentManager().setFragmentResultListener(key.toString(), fragment, (requestKey, bundle) -> consumer.accept(bundle));
        }


        public static void sendMessage(Fragment fragment, MessageKey key){
            sendMessage(fragment, key.toString(), new Bundle());
        }

         */


        public static void sendMessage(Fragment fragment, String key, Bundle bundle){
            fragment.getParentFragmentManager().setFragmentResult(key, bundle);
        }


        public static int getInt(Bundle bundle, Enum<?> tag){
            return bundle.getInt(tag.toString());
        }


        public static String getStr(Bundle bundle, Enum<?> tag){
            return bundle.getString(tag.toString());
        }


        public static boolean getBoolean(Bundle bundle, Enum<?> tag){
            return  bundle.getBoolean(tag.toString());
        }


    }
}
