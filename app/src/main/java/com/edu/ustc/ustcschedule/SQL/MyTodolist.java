package com.edu.ustc.ustcschedule.SQL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

public class MyTodolist extends BasicSchedule{



    //默认不添加备注,重要性为1,创建时必须填写编号,时间,地点和内容
    public MyTodolist (String name,long starting_time,int importance,int is_repeat,int period,String place,String description,int category,int is_finish){
        super(name,starting_time,importance,is_repeat,period,place,description,is_finish);
        this.setCategory(category);

    }
    public MyTodolist(Cursor cursor){
        super(cursor);
        this.setFromCursor(cursor);


    }

    public ContentValues getContentValues(){
        ContentValues info=super.getContentValues();
        return info;
    }

    public Map getMap() {
        Map<String, Object> info = super.getMap();

        return info;
    }

    public void toDatabase(SQLiteDatabase db)
    {
        ContentValues info=this.getContentValues();
        db.insert("TODO",null,info);

    }

    public void setFromCursor(Cursor cursor)
    {
        super.setFromCursor(cursor);


    }





}
