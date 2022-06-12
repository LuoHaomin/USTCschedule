package com.edu.ustc.ustcschedule;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ClassTextInit {
    public Void initText(View view) {
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat formatYear = new SimpleDateFormat("yyyy", Locale.CHINA);
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat formatMonth = new SimpleDateFormat("MM", Locale.CHINA);
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat formatDay = new SimpleDateFormat("dd", Locale.CHINA);
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat formatWeek = new SimpleDateFormat("EEEE", Locale.CHINA);


        Calendar c = Calendar.getInstance();
        TextView textYear = view.findViewById(R.id.text_current_year);
        textYear.setText(formatYear.format(c.getTimeInMillis()));
        TextView textMonth = view.findViewById(R.id.text_current_month);
        textMonth.setText(formatMonth.format(c.getTimeInMillis()));
        TextView textDay = view.findViewById(R.id.text_current_day);
        textDay.setText(formatDay.format(c.getTimeInMillis()));
        TextView textWeek = view.findViewById(R.id.text_current_week);
        textWeek.setText(formatWeek.format(c.getTimeInMillis()));
//        switch(c.get(Calendar.DAY_OF_WEEK)) {
//            case Calendar.SUNDAY:
//                textWeek.setText("日");
//            case Calendar.MONDAY:
//                textWeek.setText("一");
//            case Calendar.TUESDAY:
//                textWeek.setText("二");
//            case Calendar.WEDNESDAY:
//                textWeek.setText("三");
//            case Calendar.THURSDAY:
//                textWeek.setText("四");
//            case Calendar.FRIDAY:
//                textWeek.setText("五");
//            case Calendar.SATURDAY:
//                textWeek.setText("六");
//        }
        return null;
    }
}
