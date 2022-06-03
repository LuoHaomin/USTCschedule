package com.edu.ustc.ustcschedule.SQL;

import android.content.ContentValues;

import java.sql.Time;
import java.util.Date;

public class MySchedule extends BasicSchedule{


    private long EndingTime;
    private long TimeLength;

    //默认不添加备注,重要性为1,创建时必须填写编号,起止时间,地点和内容
    public MySchedule (String name,long starting_time,long ending_time,int importance,boolean is_repeat,int period,String place,String description){
        super(name,starting_time,importance,is_repeat,period,place,description);
        this.EndingTime = ending_time;
        this.TimeLength=EndingTime-super.getStartingTime();
    }

    public ContentValues getContentValues(){
        ContentValues info=super.getContentValues();
        info.put("END_TIME",EndingTime);
        info.put("TIME_LENGTH",TimeLength);

        return info;
    }


    public long getEndingTime() {
        return EndingTime;
    }

    public void setEndingTime(long endingTime) {
        EndingTime = endingTime;
    }

    public long getTimeLength() {
        return TimeLength;
    }

    public void setTimeLength(long timeLength) {
        TimeLength = timeLength;
    }
}
