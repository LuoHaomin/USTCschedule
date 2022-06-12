package com.edu.ustc.ustcschedule.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.edu.ustc.ustcschedule.R;

public class CheckUpdateDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String message = getString(R.string.current_version) + ":" + "\n" + getString(R.string.version)+"\n";
        builder.setTitle(R.string.check_update)
                .setMessage(message)
                .setPositiveButton(R.string.update, (dialog, id) -> {
                    // TODO 检查更新后禁止点按
                })
                .setNegativeButton(R.string.OK, (dialogInterface, i) -> {

                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}