package com.edu.ustc.ustcschedule.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.edu.ustc.ustcschedule.ClassPopUpMenu;
import com.edu.ustc.ustcschedule.MainActivity;
import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.ClassTextInit;
import com.edu.ustc.ustcschedule.databinding.FragmentDeadlineBinding;

public class DeadlineFragment extends Fragment {

    private FragmentDeadlineBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDeadlineBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ClassTextInit textInit = new ClassTextInit();
        ClassPopUpMenu popUpMenu = new ClassPopUpMenu();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        textInit.initText(view);
        view.findViewById(R.id.side_bar).setOnClickListener(view1 -> MainActivity.result.openDrawer());
        view.findViewById(R.id.add_events).setOnClickListener(view2 -> popUpMenu.onAddEvent(view2, fragmentManager));
    }
}
