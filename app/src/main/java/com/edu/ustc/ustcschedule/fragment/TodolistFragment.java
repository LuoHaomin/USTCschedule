package com.edu.ustc.ustcschedule.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.SQL.MainDatabaseHelper;
import com.edu.ustc.ustcschedule.SQL.MyTodolist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TodolistFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_todolist, container, false);

        MainDatabaseHelper db_helper=new MainDatabaseHelper(getContext());
        SQLiteDatabase db=db_helper.getReadableDatabase();
        Cursor cursor=db.query("TODO",new String[]{"_id","IS_FINISH","NAME" ,"START_TIME" ,
                "IMPORTANCE" ,"IS_REPEAT" ,"PERIOD" , "PLACE" ,"DESCRIPTION" ,"CATEGORY" } ,null,null,null,null,"START_TIME ASC");
        cursor.moveToFirst();
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();

        while (!cursor.isLast()){
            MyTodolist todo=new MyTodolist(cursor);
            Map<String, Object> showitem = todo.getMap();

            listitem.add(showitem);
            cursor.moveToNext();
        }
        MyTodolist todo=new MyTodolist(cursor);
        Map<String, Object> showitem = todo.getMap();

        listitem.add(showitem);

        //创建一个simpleAdapter
        SimpleAdapter myAdapter = new SimpleAdapter(getContext(), listitem, R.layout.fragment_todolist_item, new String[]{"NAME"}, new int[]{R.id.todo_name});
        myAdapter.notifyDataSetChanged();
        ListView listView = (ListView) view.findViewById(R.id.todo_ListView);
        listView.setAdapter(myAdapter);
        return view;
    }
}
