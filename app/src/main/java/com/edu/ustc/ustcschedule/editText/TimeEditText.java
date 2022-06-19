package com.edu.ustc.ustcschedule.editText;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.loper7.date_time_picker.DateTimeConfig;
import com.loper7.date_time_picker.dialog.CardDatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


import com.edu.ustc.ustcschedule.R;

public class TimeEditText extends androidx.appcompat.widget.AppCompatEditText {
    private final static String TAG = "EditTextWithDate";
    private final Context mContext;
    private Calendar c=Calendar.getInstance(Locale.CHINA);
    private boolean datePickerEnabled = true;
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat format = new SimpleDateFormat("HH:mm",Locale.CHINA);
    public Long chosenTime=c.getTimeInMillis();

    public TimeEditText(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public TimeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public TimeEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    private void init() {
        c = Calendar.getInstance(Locale.CHINA);
        //c.add(Calendar.HOUR,8);

        setText(format.format(c.getTimeInMillis()));
        this.setEnabled(false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            if (rect.contains(eventX, eventY)) {
                if (datePickerEnabled) {
                    int[] displayArray = {DateTimeConfig.HOUR, DateTimeConfig.MIN};
                    new CardDatePickerDialog.Builder(mContext)
                            .setTitle("选择时间")
                            .setDisplayType(displayArray)
                            .showBackNow(false)
                            .setDefaultTime(c.getTimeInMillis())
                            .setWrapSelectorWheel(false)
                            .setThemeColor(getResources().getColor(R.color.blue))
                            .showDateLabel(false)
                            .showFocusDateInfo(false)
                            .setTouchHideable(true)
                            .setOnChoose("确定", aLong -> {
                                setText(format.format(aLong));
                                chosenTime = aLong;
                                return null;
                            })
                            .build().show();
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public boolean isDatePickerEnabled() {
        return datePickerEnabled;
    }

    public void setDatePickerEnabled(boolean datePickerEnabled) {
        this.datePickerEnabled = datePickerEnabled;
        if (!datePickerEnabled) {
            setText("");
        }
    }

    public  Long getChosenTime() {
        return chosenTime;
    }
}