package com.edu.ustc.ustcschedule.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.edu.ustc.ustcschedule.R;

import java.util.Objects;

public class SignInDialog extends DialogFragment {
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_sign_in, null);
        builder.setTitle(R.string.sign_in)
                .setView(view)
                .setPositiveButton(R.string.sign_in, (dialog, id) -> {
                    EditText idEditText = view.findViewById(R.id.ID);
                    EditText pwdEditText = view.findViewById(R.id.password);
                    Toast.makeText(requireContext(), "id:"+idEditText.getText()+";pwd:"+pwdEditText.getText(),Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                    Objects.requireNonNull(SignInDialog.this.getDialog()).cancel();
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}