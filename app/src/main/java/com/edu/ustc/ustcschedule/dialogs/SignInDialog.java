package com.edu.ustc.ustcschedule.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import com.edu.ustc.ustcschedule.R;

import java.util.Objects;

import javax.security.auth.callback.Callback;

public class SignInDialog extends DialogFragment {

    SharedPreferences sharedPreferences = null;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        try {
//            Callback callback = (Callback) getParentFragment();
//        } catch (ClassCastException e) {
//            throw new ClassCastException("Calling fragment must implement Callback interface");
//        }
//    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_sign_in, null);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        builder.setTitle(R.string.sign_in)
                .setView(view)
                .setPositiveButton(R.string.sign_in, (dialog, id) -> {
                    EditText idEditText = view.findViewById(R.id.ID);
                    EditText pwdEditText = view.findViewById(R.id.password);
                    editor.putString("id", String.valueOf(idEditText.getText()));
                    editor.putString("pwd", String.valueOf(pwdEditText.getText()));
                    editor.apply();
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                    editor.putString("id", "");
                    editor.putString("pwd", "");
                    editor.apply();
                    Objects.requireNonNull(SignInDialog.this.getDialog()).cancel();
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}