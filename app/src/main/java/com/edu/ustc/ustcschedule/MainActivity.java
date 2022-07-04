package com.edu.ustc.ustcschedule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.edu.ustc.ustcschedule.SQL.BasicSchedule;
import com.edu.ustc.ustcschedule.SQL.MainDatabaseHelper;
import com.edu.ustc.ustcschedule.SQL.MyDeadLine;
import com.edu.ustc.ustcschedule.SQL.MySchedule;
import com.edu.ustc.ustcschedule.SQL.MyTodolist;
import com.edu.ustc.ustcschedule.databinding.ActivityMainBinding;
import com.edu.ustc.ustcschedule.dialogs.AboutDialog;
import com.edu.ustc.ustcschedule.dialogs.BorrowReminderDialog;
import com.edu.ustc.ustcschedule.dialogs.HelpDialog;
import com.edu.ustc.ustcschedule.dialogs.MoveDialog;
import com.edu.ustc.ustcschedule.dialogs.SaveTemplateDialog;
import com.edu.ustc.ustcschedule.dialogs.ShareDialog;
import com.edu.ustc.ustcschedule.share.utils.ShareBuilder;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    BottomNavigationView bottomNavigation;
    @SuppressLint("StaticFieldLeak")
    public static Drawer result;
    Calendar ca=Calendar.getInstance(Locale.CHINA);

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());

        bottomNavigation = findViewById(R.id.bottom_navigation);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigation, navController);
        result = onCrateDrawer();

        update_repeat();//更新重复事件的时间

    }

    public Drawer onCrateDrawer() {
        @SuppressLint("UseCompatLoadingForDrawables")
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withDrawerGravity(GravityCompat.END)
                .withSelectedItem(-1)
                .withDrawerWidthDp(180)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.move)
                                .withIdentifier(1)
                                .withIcon(getResources().getDrawable(R.drawable.ic_bulletpoint))
                                .withSelectable(false))
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.save_template)
                                .withIdentifier(2)
                                .withIcon(getResources().getDrawable(R.drawable.ic_save))
                                .withSelectable(false))
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.share)
                                .withIdentifier(3)
                                .withIcon(getResources().getDrawable(R.drawable.ic_share))
                                .withSelectable(false))
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.remind_book)
                                .withIdentifier(4)
                                .withIcon(getResources().getDrawable(R.drawable.ic_chat))
                                .withSelectable(false))
                .addDrawerItems(new DividerDrawerItem())
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.setting)
                                .withIdentifier(5)
                                .withIcon(getResources().getDrawable(R.drawable.ic_setting))
                                .withSelectable(false))
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.help)
                                .withIdentifier(6)
                                .withIcon(getResources().getDrawable(R.drawable.ic_help_circle))
                                .withSelectable(false))
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.about)
                                .withIdentifier(7)
                                .withIcon(getResources().getDrawable(R.drawable.ic_info_circle))
                                .withSelectable(false))
                .addDrawerItems(new DividerDrawerItem())
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.copyright)
                                .withEnabled(false)
                                .withTextColor(getResources().getColor(R.color.div_line)))
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    if (drawerItem!=null) {
                        if (drawerItem.getIdentifier() == 1) {
                            MoveDialog moveDialog = new MoveDialog();
                            getSupportFragmentManager().beginTransaction()
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .add(android.R.id.content, moveDialog)
                                    .addToBackStack(null).commit();
                        } else if (drawerItem.getIdentifier() == 2) {
                            SaveTemplateDialog saveTemplateDialog = new SaveTemplateDialog();
                            getSupportFragmentManager().beginTransaction()
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .add(android.R.id.content, saveTemplateDialog)
                                    .addToBackStack(null).commit();
                        } else if (drawerItem.getIdentifier() == 3) {
                            new ShareBuilder().setText("This is Share Content!\nThis is the content").setChooserTitle("分享").setShareType(ShareBuilder.SHARE_TEXT).build().share(this);
//                            ShareDialog shareDialog =  new ShareDialog();
//                            shareDialog.show(getSupportFragmentManager(), "share");
                        } else if (drawerItem.getIdentifier() == 4) {
                            BorrowReminderDialog borrowReminderDialog = new BorrowReminderDialog();
                            getSupportFragmentManager().beginTransaction()
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .add(android.R.id.content, borrowReminderDialog)
                                    .addToBackStack(null).commit();
                        } else if (drawerItem.getIdentifier() == 5) {
                            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                            startActivity(intent);
                        } else if (drawerItem.getIdentifier() == 6) {
                            HelpDialog helpDialog = new HelpDialog();
                            getSupportFragmentManager().beginTransaction()
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .add(android.R.id.content, helpDialog)
                                    .addToBackStack(null).commit();
                        } else if (drawerItem.getIdentifier() == 7) {
                            AboutDialog aboutDialog = new AboutDialog();
                            aboutDialog.show(getSupportFragmentManager(), "about");
                        }
                    }

                    return true;
                })
                .build();
        result.getDrawerLayout().setStatusBarBackgroundColor(getResources().getColor(R.color.div_line));
        return result;
    }
    public void update_repeat()
    {
        SimpleDateFormat format_day = new SimpleDateFormat("yyyy/MM/dd",Locale.CHINA);
        SimpleDateFormat format_time = new SimpleDateFormat("HH:mm",Locale.CHINA);
        Date date=new Date();
        long day_start=((date.getTime()+8*3600*1000)/(86400*1000))*(86400*1000)-8*3600*1000;//清除小时和分钟
        long day_end=day_start+86400*1000;
        String day_start_str=Long.toString(day_start);
        String day_end_str=Long.toString(day_end);

        MainDatabaseHelper db_helper=new MainDatabaseHelper(this);
        SQLiteDatabase db=db_helper.getReadableDatabase();
        Cursor cursor=db.query("SCHEDULE",new String[]{"_id","IS_FINISH","NAME" ,"START_TIME" ,"END_TIME","TIME_LENGTH",
                        "IMPORTANCE" ,"IS_REPEAT" ,"PERIOD" , "PLACE" ,"DESCRIPTION"  } , null,
                null,null,null,"START_TIME ASC");
        cursor.moveToFirst();
        for(int i=0;i< cursor.getCount();i++) {
            MySchedule schedule=new MySchedule(cursor);
            long new_time=newTimeForRepeat(schedule);
            long time_length=schedule.getEndingTime()-schedule.getStartingTime();
            schedule.updateDatabase(db, new_time,new_time+time_length);
            cursor.moveToNext();
        }

        Cursor ddl_cursor=db.query("DDL",new String[]{"_id","IS_FINISH","NAME" ,"START_TIME" ,"WORK_LOAD",
                        "IMPORTANCE" ,"IS_REPEAT" ,"PERIOD" , "PLACE" ,"DESCRIPTION"  } ,
                null, null,null,null,"START_TIME ASC");
        ddl_cursor.moveToFirst();
        for(int i=0;i< ddl_cursor.getCount();i++) {
            MyDeadLine ddl=new MyDeadLine(ddl_cursor);
            long new_time=newTimeForRepeat(ddl);
            ddl.updateDatabase(db,new_time);
            ddl_cursor.moveToNext();
        }

        Cursor todo_cursor=db.query("TODO",new String[]{"_id","IS_FINISH","NAME" ,"START_TIME" ,"WORK_LOAD",
                        "IMPORTANCE" ,"IS_REPEAT" ,"PERIOD" , "PLACE" ,"DESCRIPTION"  } ,
                null,null,null,null,"START_TIME ASC");
        todo_cursor.moveToFirst();
        for(int i=0;i<todo_cursor.getCount();i++){
            MyTodolist todo=new MyTodolist(todo_cursor);
            long new_time=newTimeForRepeat(todo);
            todo.updateDatabase(db,new_time);
            todo_cursor.moveToNext();
        }
    }
    public long newTimeForRepeat(BasicSchedule schedule)
    {
        ca=Calendar.getInstance(Locale.CHINA);
        long now=ca.getTimeInMillis();
        long starting_time=schedule.getStartingTime();
        Calendar temp_ca=Calendar.getInstance(Locale.CHINA);
        temp_ca.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        temp_ca.setTimeInMillis(starting_time);
        if(schedule.getPeriod()==1) {
            while (temp_ca.getTimeInMillis()<now)
            {
                temp_ca.add(Calendar.DATE,1);
            }
        }
        if(schedule.getPeriod()==7) {
            while (temp_ca.getTimeInMillis()<now)
            {
                temp_ca.add(Calendar.DATE,7);
            }
        }
        if(schedule.getPeriod()==30)
        {
            while (temp_ca.getTimeInMillis()<now)
            {
                temp_ca.add(Calendar.MONTH,1);
            }
        }
        if(schedule.getPeriod()==365)
        {
            while (temp_ca.getTimeInMillis()<now)
            {
                temp_ca.add(Calendar.YEAR,1);
            }
        }
        return temp_ca.getTimeInMillis();
    }
}