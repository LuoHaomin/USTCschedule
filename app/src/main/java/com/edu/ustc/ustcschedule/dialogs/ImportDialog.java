package com.edu.ustc.ustcschedule.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.edu.ustc.ustcschedule.R;

public class ImportDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.import_courses)
                .setMessage(R.string.import_courses_text)
                .setPositiveButton(R.string.OK, (dialog, id) -> {
//                    TODO: 导入课程
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {

                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}