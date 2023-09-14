package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;

    private boolean isHold = false; // Add a boolean to track hold state

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 10000 && !isHold) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                }
            }
        });
    }

    public void startChronometer(View v) {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
            isHold = false;
            Toast.makeText(MainActivity.this, "Start! ", Toast.LENGTH_SHORT).show();
        }
    }

    public void stopChronometer(View v) {
        if (running) {
            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime()); // Reset the chronometer to 0
            pauseOffset = 0;
            running = false;
            isHold = false;
            Toast.makeText(MainActivity.this, "Stop! ", Toast.LENGTH_SHORT).show();
        }
    }

    public void holdChronometer(View v) {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
            isHold = true;
            Toast.makeText(MainActivity.this, "Hold! ", Toast.LENGTH_SHORT).show();
        }
    }

}
