package com.edu.ustc.ustcschedule.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.edu.ustc.ustcschedule.R;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class InfoDialog extends DialogFragment{

    private String name;
    private String place;
    private String description;
    private String importance;
    private String start_time;
    private String end_time;
    private final SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.CHINA);

    public InfoDialog setName(String name) {
        this.name = name;
        return this;
    }
    public InfoDialog setPlace(String place) {
        this.place = place;
        return this;
    }
    public InfoDialog setDescription(String description) {
        this.description = description;
        return this;
    }
    public InfoDialog setImportance(Integer importance) {
        switch( importance ){
            case 1:
                this.importance = "一般";
                break;
            case 2:
                this.importance = "重要";
                break;
            case 3:
                this.importance = "很重要";
                break;
            case 4:
                this.importance = "课程";
                break;
        }

        return this;
    }
    public InfoDialog setStartTime(Long start_time) {
        this.start_time = format.format(start_time);
        return this;
    }
    public InfoDialog setEndTime(String end_time) {
        this.end_time = format.format(end_time);
        return this;
    }

//    @NonNull
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Use the Builder class for convenient dialog construction
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        String message = this.importance+this.description+this.place+this.start_time+this.end_time;
//        builder.setTitle(this.name)
//                .setMessage(message)
//                .setPositiveButton(R.string.OK, (dialog, id) -> {
//                });
//        // Create the AlertDialog object and return it
//        return builder.create();
//    }
    public Dialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        String message;
        if (this.importance.equals("课程")) {
            message = "地点:"+this.place+"\n老师:"+this.description+"\n开始时间:"+this.start_time+"\n结束时间:"+this.end_time;
        } else {
            message = "地点:"+this.place+"\n重要性:"+this.importance+"\n开始时间:"+this.start_time+"\n结束时间:"+this.end_time+"\n描述:"+this.description;
        }
        builder.setTitle(this.name)
                .setMessage(message)
                .setPositiveButton(R.string.OK, ((dialogInterface, i) -> {}));
        return builder.create();
    }
}
