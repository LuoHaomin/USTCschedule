package com.edu.ustc.ustcschedule.fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.SQL.MainDatabaseHelper;
import com.edu.ustc.ustcschedule.SQL.MySchedule;
import com.edu.ustc.ustcschedule.dialogs.DeleteDialog;

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
        final SimpleDateFormat format_day = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
        final SimpleDateFormat format_time = new SimpleDateFormat("HH:mm");

        View view= inflater.inflate(R.layout.fragment_day_list, container, false);

        MainDatabaseHelper db_helper=new MainDatabaseHelper(getContext());
        SQLiteDatabase db=db_helper.getReadableDatabase();
        Cursor cursor=db.query("SCHEDULE",new String[]{"_id","IS_FINISH","NAME" ,"START_TIME" ,"END_TIME","TIME_LENGTH",
                "IMPORTANCE" ,"IS_REPEAT" ,"PERIOD" , "PLACE" ,"DESCRIPTION"  } ,
                "START_TIME>"+day_start_str+" AND START_TIME<"+day_end_str+" AND END_TIME<"+day_end_str+" AND END_TIME>"+day_start_str,
                null,null,null,"START_TIME ASC");

        cursor.moveToFirst();

        ConstraintLayout layout=(ConstraintLayout)view.findViewById(R.id.day_list_layout);
        for(int i=0;i< cursor.getCount();i++)
        {
            View schedule_view=inflater.inflate(R.layout.fragment_day_list_item, container, false);
            MySchedule schedule=new MySchedule(cursor);

            long starting_time=Math.max(schedule.getStartingTime(),day_start);
            long ending_time=Math.min(schedule.getEndingTime(),day_end);

            double height=(Math.abs(ending_time-starting_time))/72000;
            double pos=(Math.min(starting_time,ending_time)%86400000)/72000+6.5;//6是line到layout顶部的高度


            CardView card=(CardView)schedule_view.findViewById(R.id.lesson_card_day);
            card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DeleteDialog deleteDialog = new DeleteDialog();
                    deleteDialog.show(getParentFragmentManager(), "delete");
                    return false;
                }
            });
            ConstraintLayout.LayoutParams card_params = (ConstraintLayout.LayoutParams) card.getLayoutParams();
            ((TextView)card.findViewById(R.id.lesson_text_day)).setText(schedule.getName());
            ((TextView)card.findViewById(R.id.lesson_teacher)).setText(schedule.getDescription());
            ((TextView)card.findViewById(R.id.lesson_place)).setText(schedule.getPlace());
            //String starting_time_str=format_time.format(starting_time);
            //String ending_time_str=format_time.format(ending_time);
            ((TextView)schedule_view.findViewById(R.id.start_time_text)).setText(format_time.format(starting_time));
            ((TextView)schedule_view.findViewById(R.id.end_time_text)).setText(format_time.format(ending_time));



            double magnify_ratio=(float)card_params.height/100.0;
            card_params.height=(int)(magnify_ratio*height);//放大倍数乘值
            card_params.topMargin=(int)(magnify_ratio*pos);

            //schedule_view.layout(0,100,schedule_view.getRight()-schedule_view.getLeft(),170);
            layout.addView(schedule_view);
            //schedule_view.setLayoutParams(card_params);



            cursor.moveToNext();
        }
        //layout.addView();


        return view;
    }
}