package com.edu.ustc.ustcschedule.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.edu.ustc.ustcschedule.ClassTextInit;
import com.edu.ustc.ustcschedule.MainActivity;
import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.databinding.FragmentDayContentBinding;
import com.edu.ustc.ustcschedule.databinding.FragmentScheduleDayBinding;
import com.edu.ustc.ustcschedule.dialogs.DeleteDialog;

public class DayContentFragment extends Fragment {

    private FragmentDayContentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDayContentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.lesson_card_day).setOnLongClickListener(view1 -> {
            DeleteDialog deleteDialog = new DeleteDialog();
            deleteDialog.show(getParentFragmentManager(), "delete");
            return false;
        });
    }
}