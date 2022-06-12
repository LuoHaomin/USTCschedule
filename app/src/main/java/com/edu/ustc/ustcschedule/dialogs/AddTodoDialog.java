package com.edu.ustc.ustcschedule.dialogs;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.SQL.MainDatabaseHelper;
import com.edu.ustc.ustcschedule.SQL.MyTodolist;
import com.edu.ustc.ustcschedule.editText.DateEditText;
import com.edu.ustc.ustcschedule.editText.TimeEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTodoDialog extends DialogFragment {
    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */

    private final SimpleDateFormat format_date = new SimpleDateFormat("yyyy年MM月dd日");
    private final SimpleDateFormat format_time = new SimpleDateFormat("HH:mm", Locale.CHINA);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment

        View view = inflater.inflate(R.layout.fragment_add_event_todo, container, false);
        ImageButton close = view.findViewById(R.id.close_add_event_btn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        Button save_todo = view.findViewById(R.id.add_todo_save_btn);
        save_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    setFromTodoDialog(getView());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dismiss();
            }
        });
        return view;
    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }





    /** The system calls this only when creating the layout in a dialog. */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    public MyTodolist setFromTodoDialog(View view) throws ParseException {
        EditText edit_title=(EditText)view.findViewById(R.id.edit_title);
        String name=edit_title.getText().toString();


        Spinner edit_type=(Spinner)view.findViewById(R.id.edit_type);
        int category=edit_type.getId();


        DateEditText date_text=(DateEditText)view.findViewById(R.id.date_day_text);
        String date_str=date_text.getText().toString();

        TimeEditText time_text=(TimeEditText)view.findViewById(R.id.time);
        String time_str=time_text.getText().toString();

        Date date=new Date();
        date=format_date.parse(date_str);
        Date time=new Date();
        time=format_time.parse(time_str);
        long starting_time=date.getTime()+time.getTime();

        //String name="";

        int importance=0;
        int is_repeat=0;
        int period=0;
        String place="";
        String description="";
        int is_finish=0;
        MyTodolist todo=new MyTodolist(name,starting_time,importance,is_repeat,period,place ,description   ,category,is_finish);
        MainDatabaseHelper db_helper=new MainDatabaseHelper(getContext());
        SQLiteDatabase db=db_helper.getWritableDatabase();
        todo.toDatabase(db);

        return todo;
    }
}
