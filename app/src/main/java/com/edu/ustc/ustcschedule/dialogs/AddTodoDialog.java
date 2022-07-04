package com.edu.ustc.ustcschedule.dialogs;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.adapters.AdapterViewBindingAdapter;
import androidx.fragment.app.DialogFragment;

import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.SQL.MainDatabaseHelper;
import com.edu.ustc.ustcschedule.SQL.MyDeadLine;
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

    private boolean is_DDL=true;//false则为task
    private int importance=1;
    private int is_repeat=0;
    private int period=7;
    private final SimpleDateFormat format_date = new SimpleDateFormat("yyyy年MM月dd日",Locale.CHINA);
    private final SimpleDateFormat format_time = new SimpleDateFormat("HH:mm",Locale.CHINA);
    private final SimpleDateFormat format_full = new SimpleDateFormat("yyyy年MM月dd日HH:mm",Locale.CHINA);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment

        View view = inflater.inflate(R.layout.fragment_add_event_todo, container, false);
        Spinner edit_type=(Spinner)view.findViewById(R.id.edit_type);
        edit_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                switch(pos){
                    case 0:
                        is_DDL=true;
                        break;
                    case 1:
                        is_DDL=false;//task
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }

        });

        Spinner importance_spinner=(Spinner)view.findViewById(R.id.importance_spinner);
        importance_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                importance=3-pos;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }

        });

        Spinner edit_is_repeat=(Spinner)view.findViewById(R.id.edit_is_repeat);
        edit_is_repeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if(pos==0)
                {
                    is_repeat=0;
                }
                else
                {
                    is_repeat=1;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        Spinner edit_period=(Spinner)view.findViewById(R.id.edit_period);
        edit_period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                switch(pos){
                    case 0:
                        period=1;
                        break;
                    case 1:
                        period=7;
                        break;
                    case 2:
                        period=30;//仅作为一个标记符号，不代表周期真的是30天
                        break;
                    case 3:
                        period=365;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

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
                String name=((EditText)getView().findViewById(R.id.edit_title)).getText().toString();
                name.replace(" ","");//去空格

                if(name.length()!=0) {
                    try {
                        setFromTodoDialog(getView());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dismiss();
                }
                else
                {
                    Toast toast =Toast.makeText(getContext(),"名称不能为空",Toast.LENGTH_SHORT);
                    toast.show();
                }
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

    public void setFromTodoDialog(View view) throws ParseException {
        EditText edit_title=(EditText)view.findViewById(R.id.edit_title);
        String name=edit_title.getText().toString();






        DateEditText date_text=(DateEditText)view.findViewById(R.id.date_day_text);
        String date_str=date_text.getText().toString();

        TimeEditText time_text=(TimeEditText)view.findViewById(R.id.time);
        String time_str=time_text.getText().toString();

        Date date=new Date();
        date=format_full.parse(date_str+time_str);
        long starting_time=date.getTime();

        //String name="";



        String place="";
        String description="";
        int is_finish=0;
        int workload=0;

        EditText edit_workload = (EditText)view.findViewById(R.id.edit_workload);
        String s = edit_workload.getText().toString();
        if (s.equals("") || s.equals("."))
        {
            workload=0;
        }
        else
        {
            workload= Integer.parseInt(s);
        }

        if(is_DDL) {
            MyDeadLine ddl=new MyDeadLine(name, starting_time, importance, is_repeat, period, place, description,workload, is_finish);
            MainDatabaseHelper db_helper = new MainDatabaseHelper(getContext());
            SQLiteDatabase db = db_helper.getWritableDatabase();
            ddl.toDatabase(db);
        }
        else
        {

            MyTodolist todo = new MyTodolist(name, starting_time, importance, is_repeat, period, place, description,workload, is_finish);
            MainDatabaseHelper db_helper = new MainDatabaseHelper(getContext());
            SQLiteDatabase db = db_helper.getWritableDatabase();
            todo.toDatabase(db);
        }


    }
}
