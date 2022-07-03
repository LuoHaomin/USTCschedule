package com.edu.ustc.ustcschedule.SQL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Time;
import java.util.Date;
import java.util.Map;

public class MySchedule extends BasicSchedule{


    private long EndingTime;
    private long TimeLength;

    //默认不添加备注,重要性为1,创建时必须填写编号,起止时间,地点和内容
    public MySchedule (String name,long starting_time,long ending_time,int importance,int is_repeat,int period,String place,String description,int is_finish){
        super(name,starting_time,importance,is_repeat,period,place,description,is_finish);
        this.EndingTime = ending_time;
        this.TimeLength=EndingTime-super.getStartingTime();
    }

    public MySchedule(Cursor cursor){
        super(cursor);
        this.setFromCursor(cursor);


    }

    public ContentValues getContentValues(){
        ContentValues info=super.getContentValues();
        info.put("END_TIME",EndingTime);
        info.put("TIME_LENGTH",TimeLength);

        return info;
    }

    public Map getMap() {
        Map<String, Object> info = super.getMap();
        info.put("END_TIME",EndingTime);
        info.put("TIME_LENGTH",TimeLength);
        return info;
    }

    public void toDatabase(SQLiteDatabase db)
    {
        ContentValues info=this.getContentValues();
        db.insert("SCHEDULE",null,info);

    }

    public void updateDatabase(SQLiteDatabase db,long new_starting_time,long new_ending_time)
    {
        this.setStartingTime(new_starting_time);
        this.setEndingTime(new_ending_time);
        ContentValues info=this.getContentValues();

        db.update("SCHEDULE",info,"_id = ?",new String[] {Integer.toString(this.getId())});
    }

    public void setFromCursor(Cursor cursor)
    {
        super.setFromCursor(cursor);

        EndingTime=cursor.getLong(cursor.getColumnIndexOrThrow("END_TIME"));
        TimeLength=cursor.getLong(cursor.getColumnIndexOrThrow("TIME_LENGTH"));
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
