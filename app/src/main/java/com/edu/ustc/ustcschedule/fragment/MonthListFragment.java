package com.edu.ustc.ustcschedule.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.SQL.BasicSchedule;
import com.edu.ustc.ustcschedule.SQL.MainDatabaseHelper;
import com.edu.ustc.ustcschedule.SQL.MyDeadLine;
import com.edu.ustc.ustcschedule.SQL.MySchedule;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class MonthListFragment extends Fragment {

    Calendar ca=Calendar.getInstance(Locale.CHINA);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ca.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        View view=inflater.inflate(R.layout.fragment_month_list, container, false);
        ListView layout=(ListView) view.findViewById(R.id.month_ListView);

        MainDatabaseHelper db_helper=new MainDatabaseHelper(getContext());
        SQLiteDatabase db=db_helper.getReadableDatabase();
/*
        Cursor cursor=db.query("SCHEDULE",new String[]{"_id","IS_FINISH","NAME" ,"START_TIME" ,"END_TIME","TIME_LENGTH",
                        "IMPORTANCE" ,"IS_REPEAT" ,"PERIOD" , "PLACE" ,"DESCRIPTION"  } ,
                "START_TIME>"+day_start_str+" AND START_TIME<"+day_end_str+" AND END_TIME<"+day_end_str+" AND END_TIME>"+day_start_str,
                null,null,null,"START_TIME ASC");
        cursor.moveToFirst();
        for(int i=0;i< cursor.getCount();i++) {
            MySchedule schedule=new MySchedule(cursor);
            add_schedule(layout, schedule, inflater, container);
            cursor.moveToNext();
        }


        Cursor cursor_repeat=db.query("SCHEDULE",new String[]{"_id","IS_FINISH","NAME" ,"START_TIME" ,"END_TIME","TIME_LENGTH",
                        "IMPORTANCE" ,"IS_REPEAT" ,"PERIOD" , "PLACE" ,"DESCRIPTION"  } ,
                "IS_REPEAT=1",
                null,null,null,"START_TIME ASC");
        cursor_repeat.moveToFirst();
        for(int i=0;i< cursor_repeat.getCount();i++) {
            MySchedule schedule=new MySchedule(cursor_repeat);
            boolean is_today=false;
            is_today=is_today_fun(schedule);
            if(is_today)
            {
                add_schedule(layout, schedule, inflater, container);
            }
            cursor_repeat.moveToNext();
        }

        Cursor ddl_cursor=db.query("DDL",new String[]{"_id","IS_FINISH","NAME" ,"START_TIME" ,"WORK_LOAD",
                        "IMPORTANCE" ,"IS_REPEAT" ,"PERIOD" , "PLACE" ,"DESCRIPTION"  } ,
                "START_TIME>"+day_start_str+" AND START_TIME<"+day_end_str,
                null,null,null,"START_TIME ASC");
        ddl_cursor.moveToFirst();
        for(int i=0;i< ddl_cursor.getCount();i++) {
            MyDeadLine ddl=new MyDeadLine(ddl_cursor);
            add_DDL(layout, ddl, inflater, container);
            ddl_cursor.moveToNext();
        }

        Cursor ddl_cursor_repeat=db.query("DDL",new String[]{"_id","IS_FINISH","NAME" ,"START_TIME" ,"WORK_LOAD",
                        "IMPORTANCE" ,"IS_REPEAT" ,"PERIOD" , "PLACE" ,"DESCRIPTION"  } ,
                "IS_REPEAT=1",
                null,null,null,"START_TIME ASC");
        ddl_cursor_repeat.moveToFirst();
        for(int i=0;i< ddl_cursor_repeat.getCount();i++) {
            MyDeadLine ddl=new MyDeadLine(ddl_cursor_repeat);
            boolean is_today=false;
            is_today=is_today_fun(ddl);
            if(is_today)
            {
                add_DDL(layout, ddl, inflater, container);
            }
            ddl_cursor_repeat.moveToNext();
        }
        */

        return view;
    }

    public boolean is_today_fun(BasicSchedule schedule)
    {
        boolean is_today=false;
        long starting_time=schedule.getStartingTime();
        Calendar temp_ca=Calendar.getInstance(Locale.CHINA);
        temp_ca.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        temp_ca.setTimeInMillis(starting_time);
        if(schedule.getPeriod()==1)
            is_today=true;
        if(schedule.getPeriod()==7&&(ca.get(Calendar.DAY_OF_WEEK) ==temp_ca.get(Calendar.DAY_OF_WEEK)))
            is_today=true;
        if(schedule.getPeriod()==30)
        {
            if(ca.get(Calendar.DAY_OF_MONTH) ==temp_ca.get(Calendar.DAY_OF_MONTH))
                is_today = true;
            if(ca.getActualMaximum(Calendar.DAY_OF_MONTH) <temp_ca.get(Calendar.DAY_OF_MONTH))//超过一个月最大天数
            {
                if(ca.getActualMaximum(Calendar.DAY_OF_MONTH)==ca.get(Calendar.DAY_OF_MONTH))
                    is_today=true;
            }
        }
        if(schedule.getPeriod()==365)
        {
            if((ca.get(Calendar.DAY_OF_MONTH) ==temp_ca.get(Calendar.DAY_OF_MONTH))&&
                    (ca.get(Calendar.MONTH)==temp_ca.get(Calendar.MONTH)))
            {
                is_today=true;
            }
            if(ca.getActualMaximum(Calendar.DAY_OF_MONTH) <temp_ca.get(Calendar.DAY_OF_MONTH)&&
                    (ca.get(Calendar.MONTH)==temp_ca.get(Calendar.MONTH)))//2月29日
            {
                is_today=true;
            }
        }
        return is_today;
    }
}