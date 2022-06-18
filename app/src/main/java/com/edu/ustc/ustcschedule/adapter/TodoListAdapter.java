package com.edu.ustc.ustcschedule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.SQL.MyTodolist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TodoListAdapter extends BaseAdapter {


    //private static final int TYPE_HOMEWORK = 0;
    //private static final int TYPE_READING= 1;
    //private static final int TYPE_FITTING= 2;
    //private static final int TYPE_DDL= 3;



    private static final int TYPE_COUNT=1;
    private Context mContext;
    private List<MyTodolist> mData = null;


    public TodoListAdapter(Context mContext, List<MyTodolist> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //多布局的核心，通过这个判断类别
    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    //类别数目
    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //int type = getItemViewType(position);
        ViewHolder holder = null;
        if(convertView == null){

            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_todolist_item, parent, false);
            holder.todo_label = (ImageView) convertView.findViewById(R.id.todo_label);
            holder.todo_name = (TextView) convertView.findViewById(R.id.todo_name);
            holder.todo_type_label = (ImageView) convertView.findViewById(R.id.todo_type_label);
            holder.todo_type = (TextView) convertView.findViewById(R.id.todo_type);
            holder.todo_checkbutton=(ImageButton)convertView.findViewById(R.id.todo_checkbutton);
            convertView.setTag(R.id.Tag_todo,holder);



        }else{

            holder = (ViewHolder) convertView.getTag(R.id.Tag_todo);



        }

        MyTodolist todo = mData.get(position);

        //设置下控件的值
        holder.todo_name.setText(todo.getName());
        holder.todo_label.setBackgroundResource(R.drawable.blue_label_light);

        holder.todo_type_label.setBackgroundResource(R.drawable.ic_type_blue);
        String work_load_string=Integer.toString((int)todo.getWorkLoad())+"h";
        holder.todo_type.setText(work_load_string);
        if(todo.getIsFinish()==0)
            holder.todo_checkbutton.setBackgroundResource(R.drawable.ic_checkbutton_off);
        else
            holder.todo_checkbutton.setBackgroundResource(R.drawable.ic_checkbutton_on);

        switch (todo.getImportance()){
            case 1:


                holder.todo_label.setBackgroundResource(R.drawable.green_label_light);

                holder.todo_type_label.setBackgroundResource(R.drawable.ic_type_green);


                break;
            case 3:
                holder.todo_label.setBackgroundResource(R.drawable.yellow_label_light);

                holder.todo_type_label.setBackgroundResource(R.drawable.ic_type_yellow);

                break;
            default:
                break;
        }
        return convertView;
    }


    //ViewHolder
    private static class ViewHolder{
        ImageView todo_label;
        TextView todo_name;
        ImageView todo_type_label;
        TextView todo_type;
        ImageButton todo_checkbutton;
    }

}
