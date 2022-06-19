package com.edu.ustc.ustcschedule.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.edu.ustc.ustcschedule.R;

import java.util.Calendar;
import java.util.Locale;

public class WeekWeekdaysFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Calendar ca=Calendar.getInstance(Locale.CHINA);
        View view=inflater.inflate(R.layout.fragment_week_weekdays, container, false);
        TextView textView=(TextView) view.findViewById(R.id.text_mon);
        switch (ca.get(Calendar.DAY_OF_WEEK))
        {
            case 1:
                textView=(TextView) view.findViewById(R.id.text_mon);
                break;
            case 2:
                textView=(TextView) view.findViewById(R.id.text_tue);
                break;
            case 3:
                textView=(TextView) view.findViewById(R.id.text_wed);
                break;
            case 4:
                textView=(TextView) view.findViewById(R.id.text_thu);
                break;
            case 5:
                textView=(TextView) view.findViewById(R.id.text_fri);
                break;
            case 6:
                textView=(TextView) view.findViewById(R.id.text_sat);
                break;
            case 7:
                textView=(TextView) view.findViewById(R.id.text_sun);
                break;

        }
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        return view;
    }
}