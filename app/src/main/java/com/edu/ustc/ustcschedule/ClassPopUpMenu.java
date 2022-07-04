package com.edu.ustc.ustcschedule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.PopupMenu;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.edu.ustc.ustcschedule.dialogs.AddCourseDialog;
import com.edu.ustc.ustcschedule.dialogs.AddTaskDialog;
import com.edu.ustc.ustcschedule.dialogs.AddTodoDialog;
import com.edu.ustc.ustcschedule.share.utils.Share;

public class ClassPopUpMenu {

    @SuppressLint("NonConstantResourceId")
    public void onAddEvent(View view, FragmentManager fragmentManager) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.menu_add_event, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(item -> {

            switch (item.getItemId()) {
                case R.id.add_course:
                    AddCourseDialog courseFragment = new AddCourseDialog();
                    // For a little polish, specify a transition animation
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    // To make it fullscreen, use the 'content' root view as the container
                    // for the fragment, which is always the root view for the activity
                    transaction.add(android.R.id.content, courseFragment)
                            .addToBackStack(null).commit();
                    break;
                case R.id.add_task:
                    AddTaskDialog taskFragment = new AddTaskDialog();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.add(android.R.id.content, taskFragment)
                            .addToBackStack(null).commit();
                    break;
                case R.id.add_todo:
                    AddTodoDialog todoFragment = new AddTodoDialog();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.add(android.R.id.content, todoFragment)
                            .addToBackStack(null).commit();
                    break;
            }
            return true;
        });
        popupMenu.show();
    }

    @SuppressLint("NonConstantResourceId")
    public void onFilter(View view) {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("filter", Context.MODE_MULTI_PROCESS);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.menu_filter, popupMenu.getMenu());
        popupMenu.getMenu().findItem(R.id.filter_task).setChecked(sharedPreferences.getBoolean("filter_task", true));
        popupMenu.getMenu().findItem(R.id.filter_ddl).setChecked(sharedPreferences.getBoolean("filter_ddl", true));
        popupMenu.getMenu().findItem(R.id.filter_todo).setChecked(sharedPreferences.getBoolean("filter_todo", true));
        popupMenu.getMenu().findItem(R.id.filter_courses).setChecked(sharedPreferences.getBoolean("filter_courses", true));
        popupMenu.getMenu().findItem(R.id.filter_least).setChecked(sharedPreferences.getBoolean("filter_least", true));
        popupMenu.getMenu().findItem(R.id.filter_imp).setChecked(sharedPreferences.getBoolean("filter_imp", true));
        popupMenu.getMenu().findItem(R.id.filter_most).setChecked(sharedPreferences.getBoolean("filter_most", true));

        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(item -> {
            boolean check = item.isChecked();
            item.setChecked(!check);
            switch (item.getItemId()) {
                case R.id.filter_task:
                    editor.putBoolean("filter_task", !check);
                    break;
                case R.id.filter_ddl:
                    editor.putBoolean("filter_ddl", !check);
                    break;
                case R.id.filter_todo:
                    editor.putBoolean("filter_todo", !check);
                    break;
                case R.id.filter_courses:
                    editor.putBoolean("filter_courses", !check);
                    break;
                case R.id.filter_least:
                    editor.putBoolean("filter_least", !check);
                    break;
                case R.id.filter_imp:
                    editor.putBoolean("filter_imp", !check);
                    break;
                case R.id.filter_most:
                    editor.putBoolean("filter_most", !check);
                    break;
            }
            editor.apply();
            return false;
        });
        popupMenu.show();
    }
}
