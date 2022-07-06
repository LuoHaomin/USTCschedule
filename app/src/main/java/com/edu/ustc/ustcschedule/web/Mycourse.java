package com.edu.ustc.ustcschedule.web;

import com.edu.ustc.ustcschedule.SQL.MySchedule;

import java.lang.String;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Mycourse {
    String courseName;
    int weekday;
    int startUnit;
    int endUnit;
    String room;
    String teachers;
    final SimpleDateFormat format_time = new SimpleDateFormat("HH:mm", Locale.CHINA);
    String[] course_start= {"07:50","08:40","09:45","10:35","11:25","14:00","14:50","15:55","16:45","17:35","19:30","20:20","21:10"};
    String[] course_end={"08:35","09:25","10:30","11:20","12:10","14:45","15:35","16:40","17:30","18:20","20:15","21:05","21:55"};


    public MySchedule to_schedule()
    {
        int new_weekday=weekday%7+1;
        Date date1=new Date();
        Date date2=new Date();
        try {
            date1=format_time.parse(course_start[startUnit-1]);
            date2=format_time.parse(course_end[endUnit-1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar ca1=Calendar.getInstance(Locale.CHINA);
        Calendar ca2=Calendar.getInstance(Locale.CHINA);
        Calendar ca3=Calendar.getInstance(Locale.CHINA);
        ca3.setTimeInMillis(ca3.getTimeInMillis()-ca3.getTimeInMillis()%(3600*1000));

        ca3.set(Calendar.DAY_OF_WEEK,new_weekday);
        ca3.set(Calendar.HOUR_OF_DAY,0);
        ca1.setTimeInMillis(ca3.getTimeInMillis()+date1.getTime()+8*3600*1000);
        ca2.setTimeInMillis(ca3.getTimeInMillis()+date2.getTime()+8*3600*1000);
        MySchedule schedule=new MySchedule(courseName,ca1.getTimeInMillis(), ca2.getTimeInMillis(), 4,1,7, room, teachers,0);
        return schedule;
    }
    public Mycourse()
    {
        ;
    }
    public Mycourse(String ans){
        this.courseName = ans;
    }

    public int getWeekday() {
        return weekday;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getRoom() {
        return room;
    }

    public int getStartUnit() {
        return startUnit;
    }

    public int getEndUnit() {
        return endUnit;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setEndUnit(int endUnit) {
        this.endUnit = endUnit;
    }

    public void setStartUnit(int startUnit) {
        this.startUnit = startUnit;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }
}
