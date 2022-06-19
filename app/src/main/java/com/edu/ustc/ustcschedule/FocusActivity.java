package com.edu.ustc.ustcschedule;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class FocusActivity extends AppCompatActivity {

    private int seconds;
    private int seconds_origin;
    private boolean running;
    private boolean wasRunning;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        if (savedInstanceState != null){
            seconds= savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        Button button = findViewById(R.id.yes_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.input_time_hour).setVisibility(View.GONE);
                findViewById(R.id.input_time_minute).setVisibility(View.GONE);
                findViewById(R.id.yes_button).setVisibility(View.INVISIBLE);
                findViewById(R.id.time_view).setVisibility(View.VISIBLE);
                findViewById(R.id.start_button).setVisibility(View.VISIBLE);
                findViewById(R.id.stop_button).setVisibility(View.VISIBLE);
                running = true;
                runTimer();
            }
        });

    }


    @Override
    protected void onPause(){
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (wasRunning){
            running = true;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }


    public void onClickStart(View view){
        running = true;
    }
    public void onClickStop(View view){

        running = false;
    }
    public void onClickReset(View view){
        running = false;
        seconds = seconds_origin;
    }

    private void runTimer(){
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();

        EditText input_time_hour = (EditText) findViewById( R.id.input_time_hour );
        EditText input_time_minute = (EditText)findViewById(R.id.input_time_minute);

        int hour = Integer.parseInt(String.valueOf(input_time_hour.getText()));
        int minute = Integer.parseInt(String.valueOf(input_time_minute.getText()));
        seconds_origin = 3600* hour + 60* minute;
        seconds = seconds_origin;

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                @SuppressLint("DefaultLocale") String time = String.format("%d:%02d:%02d",hours,minutes,secs);

                timeView.setText(time);
                if(running){
                    seconds --;
                }
                if(seconds <= 0){
                    running = false;
                }

                handler.postDelayed(this,1000);
            }
        });
    }

}
