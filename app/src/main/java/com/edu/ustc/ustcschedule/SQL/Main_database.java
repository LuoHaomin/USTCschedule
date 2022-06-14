package com.edu.ustc.ustcschedule.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Main_database extends SQLiteOpenHelper {

    public enum Category {
        course,work_out,homework;
    }
    private static final String DB_NAME="USTC_schedule";
    private static final int DB_VERSION=1;


    Main_database(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SCHEDULE (_id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,START_TIME INTEGER,END_TIME INTEGER, TIME_LENGTH INTEGER," +
                "IMPORTANCE INTEGER,IS_REPEAT INTEGER,PERIOD INTEGER, PLACE TEXT,DESCRIPTION TEXT,CATEGORY INTEGER);");
        db.execSQL("CREATE TABLE DDL (_id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,START_TIME INTEGER, WORK_LOAD INTEGER," +
                "IMPORTANCE INTEGER,IS_REPEAT INTEGER,PERIOD INTEGER, PLACE TEXT,DESCRIPTION TEXT,CATEGORY INTEGER);");
        db.execSQL("CREATE TABLE TODO (_id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,START_TIME INTEGER, " +
                "IMPORTANCE INTEGER,IS_REPEAT INTEGER,PERIOD INTEGER, PLACE TEXT,DESCRIPTION TEXT,CATEGORY INTEGER);");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ;
    }
}





