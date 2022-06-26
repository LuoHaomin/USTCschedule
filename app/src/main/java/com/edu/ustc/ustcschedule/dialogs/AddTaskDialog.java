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
import androidx.fragment.app.DialogFragment;

import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.SQL.MainDatabaseHelper;
import com.edu.ustc.ustcschedule.SQL.MySchedule;
import com.edu.ustc.ustcschedule.SQL.MyTodolist;
import com.edu.ustc.ustcschedule.editText.DateEditText;
import com.edu.ustc.ustcschedule.editText.TimeEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTaskDialog extends DialogFragment {
    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */
    private int importance=1;
    private int is_repeat=0;
    private int period=7;
    //private final SimpleDateFormat format_date = new SimpleDateFormat("yyyy年MM月dd日",Locale.CHINA);
    //private final SimpleDateFormat format_time = new SimpleDateFormat("HH:mm",Locale.CHINA);
    private final SimpleDateFormat format_full = new SimpleDateFormat("yyyy年MM月dd日HH:mm",Locale.CHINA);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        View view = inflater.inflate(R.layout.fragment_add_event_task, container, false);
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
        Button save_todo = view.findViewById(R.id.add_task_save_btn);
        save_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=((EditText)getView().findViewById(R.id.edit_title)).getText().toString();
                name.replace(" ","");//去空格

                if(name.length()==0) {
                    Toast toast =Toast.makeText(getContext(),"名称不能为空",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    TimeEditText edit_start_time=(TimeEditText)getView().findViewById(R.id.start_time);
                    String start_time_str=edit_start_time.getText().toString();
                    TimeEditText edit_end_time=(TimeEditText)getView().findViewById(R.id.end_time);
                    String end_time_str=edit_end_time.getText().toString();
                    if(start_time_str.compareTo(end_time_str)>0)
                    {
                        Toast toast =Toast.makeText(getContext(),"开始时间不能晚于结束时间",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else
                    {
                        try {
                            setFromTaskDialog(getView());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dismiss();
                    }

                }
            }
        });
        return view;
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

    public MySchedule setFromTaskDialog(View view) throws ParseException {
        EditText edit_title=(EditText)view.findViewById(R.id.edit_title);
        String name=edit_title.getText().toString();


        DateEditText date_text=(DateEditText)view.findViewById(R.id.date_day_text);
        String date_str=date_text.getText().toString();

        TimeEditText edit_start_time=(TimeEditText)view.findViewById(R.id.start_time);
        String start_time_str=edit_start_time.getText().toString();
        TimeEditText edit_end_time=(TimeEditText)view.findViewById(R.id.end_time);
        String end_time_str=edit_end_time.getText().toString();

        Date date1=new Date();
        date1=format_full.parse(date_str+start_time_str);
        Date date2=new Date();
        date2=format_full.parse(date_str+end_time_str);
        long starting_time=date1.getTime();
        long ending_time=date2.getTime();


        EditText place_edit=(EditText)view.findViewById(R.id.place_edit);
        String place=place_edit.getText().toString();

        EditText teacher_edit=(EditText)view.findViewById(R.id.teacher_edit);
        String description=teacher_edit.getText().toString();




        int is_finish=0;
        MySchedule task=new MySchedule(name,starting_time,ending_time,importance,is_repeat,period,place ,description ,is_finish);
        MainDatabaseHelper db_helper=new MainDatabaseHelper(getContext());
        SQLiteDatabase db=db_helper.getWritableDatabase();
        task.toDatabase(db);

        return task;
    }
}
