package com.edu.ustc.ustcschedule.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.SQL.MainDatabaseHelper;

public class DeleteDialog extends DialogFragment {

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    int event_id;
    String table_name;

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public interface DeleteDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }


    // Use this instance of the interface to deliver action events
    DeleteDialogListener listener=new DeleteDialogListener() {
        @Override
        public void onDialogPositiveClick(DialogFragment dialog) {
            int id=((DeleteDialog)dialog).getEvent_id();
            String id_str=Integer.toString(id);
            String table_name=((DeleteDialog)dialog).getTable_name();
            MainDatabaseHelper db_helper=new MainDatabaseHelper(getContext());
            SQLiteDatabase db=db_helper.getWritableDatabase();
            db.delete(table_name,"_id=?",new String[]{"id_str"});

        }

        @Override
        public void onDialogNegativeClick(DialogFragment dialog) {
            //dismiss();
        }
    };

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (DeleteDialogListener)  context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context
                    + " must implement DeleteDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.delete_title)
                .setMessage(R.string.delete_text)
                .setPositiveButton(R.string.delete, (dialog, id) -> listener.onDialogPositiveClick(DeleteDialog.this))
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> listener.onDialogNegativeClick(DeleteDialog.this));
        // Create the AlertDialog object and return it

        return builder.create();
    }


}
