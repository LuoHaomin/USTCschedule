package com.edu.ustc.ustcschedule.fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.SQL.MainDatabaseHelper;
import com.edu.ustc.ustcschedule.SQL.MySchedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DayListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Calendar ca=Calendar.getInstance(Locale.CHINA);
        long day_start=(ca.getTimeInMillis()/86400/1000)*1000*86400;//清除小时和分钟
        long day_end=day_start+86400*1000;
        String day_start_str=Long.toString(day_start);
        String day_end_str=Long.toString(day_end);
        final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);

        View view= inflater.inflate(R.layout.fragment_day_list, container, false);

        MainDatabaseHelper db_helper=new MainDatabaseHelper(getContext());
        SQLiteDatabase db=db_helper.getReadableDatabase();
        Cursor cursor=db.query("SCHEDULE",new String[]{"_id","IS_FINISH","NAME" ,"START_TIME" ,"END_TIME","TIME_LENGTH",
                "IMPORTANCE" ,"IS_REPEAT" ,"PERIOD" , "PLACE" ,"DESCRIPTION"  } ,"START_TIME>"+day_start_str+" AND END_TIME<"+day_end_str,null,null,null,"START_TIME ASC");

        cursor.moveToFirst();

        ConstraintLayout layout=(ConstraintLayout)view.findViewById(R.id.day_list_layout);
        for(int i=0;i< cursor.getCount();i++)
        {
            View schedule_view=inflater.inflate(R.layout.fragment_day_list_item, container, false);
            MySchedule schedule=new MySchedule(cursor);

            long starting_time=schedule.getStartingTime();
            long ending_time=schedule.getEndingTime();
            int height=(int)(Math.abs(ending_time-starting_time))/72000;
            int pos=(int)(Math.min(starting_time,ending_time)%86400000)/72000;


            CardView card=(CardView)schedule_view.findViewById(R.id.lesson_card_day);
            ConstraintLayout.LayoutParams card_params = (ConstraintLayout.LayoutParams) card.getLayoutParams();
            card_params.height=(int)((float)card_params.height/100.0*height);
            //card.setMinimumHeight();
            card_params.topMargin=(int)((float)card_params.topMargin/100.0*pos);

            layout.addView(schedule_view,card_params);

            cursor.moveToNext();
        }
        //layout.addView();


        return view;
    }
}