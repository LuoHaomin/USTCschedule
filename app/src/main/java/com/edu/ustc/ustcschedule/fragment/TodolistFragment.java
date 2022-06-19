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
import androidx.fragment.app.FragmentManager;

import com.edu.ustc.ustcschedule.ClassPopUpMenu;
import com.edu.ustc.ustcschedule.MainActivity;
import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.ClassTextInit;
import com.edu.ustc.ustcschedule.SQL.MainDatabaseHelper;
import com.edu.ustc.ustcschedule.adapter.TodoListAdapter;
import com.edu.ustc.ustcschedule.databinding.FragmentTodolistBinding;

import com.edu.ustc.ustcschedule.SQL.MyTodolist;

import java.util.ArrayList;
import java.util.List;

public class TodolistFragment extends Fragment {

    private FragmentTodolistBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_todolist, container, false);

        MainDatabaseHelper db_helper=new MainDatabaseHelper(getContext());
        SQLiteDatabase db=db_helper.getReadableDatabase();
        Cursor cursor=db.query("TODO",new String[]{"_id","IS_FINISH","NAME" ,"START_TIME" ,"WORK_LOAD",
                "IMPORTANCE" ,"IS_REPEAT" ,"PERIOD" , "PLACE" ,"DESCRIPTION"  } ,null,null,null,null,"START_TIME ASC");
        cursor.moveToFirst();



        /*List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();

        while (!cursor.isLast()){
            MyTodolist todo=new MyTodolist(cursor);
            Map<String, Object> showitem = todo.getMap();

            listitem.add(showitem);
            cursor.moveToNext();
        }
        MyTodolist todo=new MyTodolist(cursor);
        Map<String, Object> showitem = todo.getMap();

        listitem.add(showitem);*/

        List<MyTodolist> listitem = new ArrayList<MyTodolist>();

        for(int i=0;i<cursor.getCount();i++){
            MyTodolist todo=new MyTodolist(cursor);
            listitem.add(todo);
            cursor.moveToNext();
        }
        //创建一个simpleAdapter
        //SimpleAdapter myAdapter = new SimpleAdapter(getContext(), listitem, R.layout.fragment_todolist_item, new String[]{"NAME"}, new int[]{R.id.todo_name});
        TodoListAdapter myAdapter=new TodoListAdapter(getContext(),listitem);
        myAdapter.notifyDataSetChanged();
        ListView listView = (ListView) view.findViewById(R.id.todo_ListView);
        listView.setAdapter(myAdapter);
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ClassTextInit textInit = new ClassTextInit();
        ClassPopUpMenu popUpMenu = new ClassPopUpMenu();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        textInit.initText(view);
        view.findViewById(R.id.side_bar).setOnClickListener(view2 -> MainActivity.result.openDrawer());
        view.findViewById(R.id.add_events).setOnClickListener(view3 -> popUpMenu.onAddEvent(view3, fragmentManager));
    }
}
