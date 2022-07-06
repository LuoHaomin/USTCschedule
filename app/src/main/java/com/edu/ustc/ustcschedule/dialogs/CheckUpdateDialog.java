package com.edu.ustc.ustcschedule.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.edu.ustc.ustcschedule.R;

import java.util.Objects;

public class CheckUpdateDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String message = getString(R.string.current_version) + " : " + getAppVersionName(requireContext()) + "\n" + getString(R.string.version) + " : " + getAppVersionName(requireContext()) +  "\n";
        builder.setTitle(R.string.check_update)
                .setMessage(message)
                .setPositiveButton(R.string.update, (dialog, id) -> {
                    Toast.makeText(requireContext(), "当前已是最新版本！", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.OK, (dialogInterface, i) -> {

                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public static String getAppVersionName(Context context) {
        String versionName = "";
        int versionCode = 0;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
}