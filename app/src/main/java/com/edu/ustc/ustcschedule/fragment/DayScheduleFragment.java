package com.edu.ustc.ustcschedule.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.databinding.FragmentScheduleDayBinding;

public class DayScheduleFragment extends Fragment {

    private FragmentScheduleDayBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentScheduleDayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton jump_fragment_schedule_week = view.findViewById(R.id.day_mode);
        jump_fragment_schedule_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_schedule_day_to_week);
            }
        });
    }
}
