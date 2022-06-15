package com.edu.ustc.ustcschedule;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.PopupMenu;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.edu.ustc.ustcschedule.dialogs.AddCourseDialog;
import com.edu.ustc.ustcschedule.dialogs.AddTaskDialog;
import com.edu.ustc.ustcschedule.dialogs.AddTodoDialog;

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
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.menu_filter, popupMenu.getMenu());

        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(item -> {
            boolean check = item.isChecked();
            item.setChecked(!check);
            switch (item.getItemId()) {
                case R.id.filter_courses:
                    // TODO 修改显示与否
                    break;
                case R.id.filter_exercise:

                    break;
                case R.id.filter_homework:

                    break;
                case R.id.filter_todo:

                    break;
                case R.id.filter_least:

                    break;
                case R.id.filter_imp:

                    break;
                case R.id.filter_most:

                    break;
            }
            return false;
        });
        popupMenu.show();
    }
}
