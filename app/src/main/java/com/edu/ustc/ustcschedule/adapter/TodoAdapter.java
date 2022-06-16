package com.edu.ustc.ustcschedule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.SQL.MyTodolist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TodoAdapter extends BaseAdapter {

    //定义两个类别标志
    private static final int TYPE_HOMEWORK = 0;
    private static final int TYPE_READING= 1;
    private static final int TYPE_FITTING= 1;
    private static final int TYPE_READING= 1;



    private static final int TYPE_COUNT=2;
    private Context mContext;
    private List<MyTodolist> mData = null;


    public TodoAdapter(Context mContext, List<MyTodolist> mData) {
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
        return (int)mData.get(position).get("CATEGORY");
    }

    //类别数目
    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolder holder = null;
        if(convertView == null){

            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_todolist_item, parent, false);
            holder.todo_label = (ImageView) convertView.findViewById(R.id.todo_label);
            holder.todo_name = (TextView) convertView.findViewById(R.id.todo_name);
            holder.todo_type_label = (ImageView) convertView.findViewById(R.id.todo_type_label);
            holder.todo_type = (TextView) convertView.findViewById(R.id.todo_type);
            convertView.setTag(R.id.Tag,holder);



        }else{

            holder = (ViewHolder) convertView.getTag(R.id.Tag);



        }

        MyTodolist todo = mData.get(position);
        //设置下控件的值
        holder.todo_name.setText(todo.getName());
        switch (type){
            case TYPE_HOMEWORK:

                if(app != null){
                    holder.todo_label.setImageResource(app.getaIcon());

                    holder.todo_label.setImageResource(app.getaIcon());
                    holder.todo_name.setText(app.getaName());
                }
                break;
            case TYPE_BOOK:

                if(book != null){
                    holder2.txt_bname.setText(book.getbName());
                    holder2.txt_bauthor.setText(book.getbAuthor());
                }
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
    }

}
