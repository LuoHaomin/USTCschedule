package com.edu.ustc.ustcschedule.SQL;

import java.sql.Date;
import java.sql.Time;

public class BasicSchedule {

    private int No;//弃用
    private String Name;

    private long StartingTime;

    private int Importance;
    private boolean IsRepeat;
    private int Period;
    private String Place;
    private String Description;



    //日期和时间采用Java自带的时间和日期类,使用setYear等函数修改
    public BasicSchedule(String name,long starting_time,int importance,boolean is_repeat,int period,String place,String description){
        No=0;//弃用
        Name=name;
        StartingTime=starting_time;
        Importance=importance;
        IsRepeat=is_repeat;
        Period=period;
        Place=place;
        Description=description;


    }

    public int getNo() {
        return No;
    }

    public void setNo(int schNo) {
        No = schNo;
    }


    public long getStartingTime() {
        return StartingTime;
    }

    public void setStartingTime(long startingTime) {
        StartingTime = startingTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public int getPeriod() {
        return Period;
    }

    public void setPeriod(int period) {
        Period = period;
    }

    public boolean isRepeat() {
        return IsRepeat;
    }

    public void setRepeat(boolean repeat) {
        IsRepeat = repeat;
    }

    public int getImportance() {
        return Importance;
    }

    public void setImportance(int importance) {
        Importance = importance;
    }
}
