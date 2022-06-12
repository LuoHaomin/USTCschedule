package com.edu.ustc.ustcschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.edu.ustc.ustcschedule.fragment.SettingFragment;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.setting_container, new SettingFragment())
                .commit();

    }
}