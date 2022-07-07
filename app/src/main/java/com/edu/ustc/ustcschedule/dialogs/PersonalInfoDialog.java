package com.edu.ustc.ustcschedule.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import com.edu.ustc.ustcschedule.R;

import java.util.Arrays;

public class PersonalInfoDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String id = sharedPreferences.getString("id_no", "");
        byte[] id_byte = id.getBytes();
        int id_len = sharedPreferences.getInt("id_len", 0);
        for (int i = 0; i < id_byte.length; i++) {
            id_byte[i] = (byte) (id_byte[i] ^ (byte) 50);
        }
        String message = getString(R.string.name) + " : " + sharedPreferences.getString("name", "未知") + "\n" + getString(R.string.ID) + " : " + new String(id_byte) + "\n";
        builder.setTitle(R.string.personal_info)
                .setMessage(message)
                .setPositiveButton(R.string.OK, (dialog, id1) -> {
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}