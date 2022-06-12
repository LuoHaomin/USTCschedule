package com.edu.ustc.ustcschedule.SQL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyDeadLine extends BasicSchedule{

    private long WorkLoad;

    //默认不添加备注,创建时必须填写编号,时间和内容
    public MyDeadLine (String name,long starting_time,int importance,int is_repeat,int period,String place,String description,long work_load,int is_finish){
        super(name,starting_time,importance,is_repeat,period,place,description,is_finish);
        this.WorkLoad=work_load;


    }

    public MyDeadLine(Cursor cursor){
        super(cursor);
        this.setFromCursor(cursor);


    }

    public ContentValues getContentValues(){
        ContentValues info=super.getContentValues();
        info.put("WORK_LOAD",WorkLoad);

        return info;
    }

    public Map getMap() {
        Map<String, Object> info = super.getMap();
        info.put("WORK_LOAD",WorkLoad);
        return info;
    }

    public void toDatabase(SQLiteDatabase db)
    {
        ContentValues info=this.getContentValues();
        db.insert("DDL",null,info);

    }

    public void setFromCursor(Cursor cursor)
    {
        super.setFromCursor(cursor);

        WorkLoad=cursor.getInt(cursor.getColumnIndexOrThrow("WORK_LOAD"));
    }


}
