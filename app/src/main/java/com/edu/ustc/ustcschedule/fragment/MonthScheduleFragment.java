package com.edu.ustc.ustcschedule.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.edu.ustc.ustcschedule.MainActivity;
import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.databinding.FragmentScheduleMonthBinding;

public class MonthScheduleFragment extends Fragment {

    private FragmentScheduleMonthBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentScheduleMonthBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.month_mode).setOnClickListener(view1 -> Navigation.findNavController(
                requireView()).navigate(R.id.action_schedule_month_to_day));
        view.findViewById(R.id.side_bar).setOnClickListener(view2 -> MainActivity.result.openDrawer());
    }
}
