package com.edu.ustc.ustcschedule.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.SQL.MainDatabaseHelper;
import com.edu.ustc.ustcschedule.SQL.MyDeadLine;
import com.edu.ustc.ustcschedule.SQL.MyTodolist;
import com.edu.ustc.ustcschedule.adapter.DeadlineListAdapter;
import com.edu.ustc.ustcschedule.adapter.TodoListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DeadlineListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_deadline_list, container, false);
        Calendar ca=Calendar.getInstance(Locale.CHINA);
        ca.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date=new Date();
        long day_start=date.getTime();;//清除小时和分钟
        String day_start_str=Long.toString(day_start);

        MainDatabaseHelper db_helper=new MainDatabaseHelper(getContext());
        SQLiteDatabase db=db_helper.getReadableDatabase();
        Cursor cursor=db.query("DDL",new String[]{"_id","IS_FINISH","NAME" ,"START_TIME" ,"WORK_LOAD",
                "IMPORTANCE" ,"IS_REPEAT" ,"PERIOD" , "PLACE" ,"DESCRIPTION"  } ,"START_TIME>"+day_start_str,null,null,null,"START_TIME ASC");
        cursor.moveToFirst();

        List<MyDeadLine> listitem = new ArrayList<MyDeadLine>();

        for(int i=0;i<cursor.getCount();i++){
            MyDeadLine todo=new MyDeadLine(cursor);
            listitem.add(todo);
            cursor.moveToNext();
        }
        //创建一个simpleAdapter
        //SimpleAdapter myAdapter = new SimpleAdapter(getContext(), listitem, R.layout.fragment_todolist_item, new String[]{"NAME"}, new int[]{R.id.todo_name});
        DeadlineListAdapter myAdapter=new DeadlineListAdapter(getContext(),listitem);
        myAdapter.notifyDataSetChanged();
        ListView listView = (ListView) view.findViewById(R.id.deadline_ListView);
        listView.setAdapter(myAdapter);
        return view;
    }
}
