package com.edu.ustc.ustcschedule.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.edu.ustc.ustcschedule.dialogs.AddTodoDialog;


public class MainDatabaseHelper extends SQLiteOpenHelper {


    private static final String DB_NAME="USTC_schedule";
    private static final int DB_VERSION=1;


    public MainDatabaseHelper(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //取数据时不用管时区
        db.execSQL("CREATE TABLE SCHEDULE (_id INTEGER PRIMARY KEY AUTOINCREMENT,IS_FINISH INTEGER, NAME TEXT,START_TIME INTEGER,END_TIME INTEGER, TIME_LENGTH INTEGER," +
                "IMPORTANCE INTEGER,IS_REPEAT INTEGER,PERIOD INTEGER, PLACE TEXT,DESCRIPTION TEXT);");
        db.execSQL("CREATE TABLE DDL (_id INTEGER PRIMARY KEY AUTOINCREMENT,IS_FINISH INTEGER,NAME TEXT,START_TIME INTEGER, WORK_LOAD INTEGER," +
                "IMPORTANCE INTEGER,IS_REPEAT INTEGER,PERIOD INTEGER, PLACE TEXT,DESCRIPTION TEXT);");
        db.execSQL("CREATE TABLE TODO (_id INTEGER PRIMARY KEY AUTOINCREMENT,IS_FINISH INTEGER,NAME TEXT,START_TIME INTEGER, WORK_LOAD INTEGER," +
                "IMPORTANCE INTEGER,IS_REPEAT INTEGER,PERIOD INTEGER, PLACE TEXT,DESCRIPTION TEXT);");
    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ;
    }
}





