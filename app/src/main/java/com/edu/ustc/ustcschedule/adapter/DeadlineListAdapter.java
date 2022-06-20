package com.edu.ustc.ustcschedule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.SQL.MyDeadLine;
import com.edu.ustc.ustcschedule.SQL.MyTodolist;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class DeadlineListAdapter extends BaseAdapter {
    private static final int TYPE_COUNT=1;
    private Context mContext;
    private List<MyDeadLine> mData = null;


    public DeadlineListAdapter(Context mContext, List<MyDeadLine> mData) {
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
        Calendar ca=Calendar.getInstance(Locale.CHINA);
        ca.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date=new Date();
        long day_start=date.getTime();//清除小时和分钟
        long day_end=day_start+86400*1000;
        final SimpleDateFormat format_day = new SimpleDateFormat("yyyy/MM/dd",Locale.CHINA);
        final SimpleDateFormat format_time = new SimpleDateFormat("HH:mm",Locale.CHINA);


        DeadlineListAdapter.ViewHolder holder = null;
        if(convertView == null){

            holder = new DeadlineListAdapter.ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_deadline_list_item, parent, false);
            holder.left_card = (CardView)  convertView.findViewById(R.id.left);
            convertView.setTag(R.id.Tag_DDL,holder);

        }else{
            holder = (DeadlineListAdapter.ViewHolder) convertView.getTag(R.id.Tag_DDL);

        }

        MyDeadLine ddl = mData.get(position);
        long left_days=(ddl.getStartingTime()-ca.getTimeInMillis())/(86400*1000);

        //设置下控件的值
        ((TextView)holder.left_card.findViewById(R.id.left_text)).setText(ddl.getName());
        ((TextView)holder.left_card.findViewById(R.id.left_days_text)).setText(Long.toString(left_days));


        /*switch (ddl.getImportance()){

            case 3:
                //((ImageView)holder.left_card.findViewById(R.id.left_label)).setBackgroundResource(R.drawable.yellow_label_light);

                break;
            default:
                break;
        }*/
        return convertView;
    }


    //ViewHolder
    private static class ViewHolder{
        CardView left_card;
    }
}
