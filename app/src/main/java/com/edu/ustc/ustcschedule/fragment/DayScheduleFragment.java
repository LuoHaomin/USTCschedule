package com.edu.ustc.ustcschedule.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.edu.ustc.ustcschedule.MainActivity;
import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.databinding.FragmentScheduleDayBinding;
import com.edu.ustc.ustcschedule.dialogs.AddCourseDialog;
import com.edu.ustc.ustcschedule.dialogs.AddTaskDialog;
import com.edu.ustc.ustcschedule.dialogs.AddTodoDialog;

public class DayScheduleFragment extends Fragment {

    private FragmentScheduleDayBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentScheduleDayBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.day_mode).setOnClickListener(view1 -> Navigation.findNavController(
                requireView()).navigate(R.id.action_schedule_day_to_week));
        view.findViewById(R.id.add_events).setOnClickListener(this::onAddEvent);
        view.findViewById(R.id.side_bar).setOnClickListener(view2 -> MainActivity.result.openDrawer());
    }

    private void onAddEvent(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this.getContext(), view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.menu_add_event, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
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
            }
        });
        popupMenu.show();
    }
}
