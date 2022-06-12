package com.edu.ustc.ustcschedule.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.edu.ustc.ustcschedule.R;

public class PersonalInfoDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String message = getString(R.string.name) + ":" + "\n" + getString(R.string.ID) + ":" + "\n";
        builder.setTitle(R.string.personal_info)
                .setMessage(message)
                .setPositiveButton(R.string.OK, (dialog, id) -> {
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}