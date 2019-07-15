package com.prototype.guavatimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timerTextView;
    SeekBar timerSeekBar;
    CountDownTimer countDownTimer;
    boolean counterActive = false;
    Button timerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.timerTextView);
        timerButton = findViewById(R.id.timerButton);

        //600 seconds = 10 minutes, 30 seconds
        int maxTime = 600;
        int startTime = 30;
        timerSeekBar.setMax(maxTime);
        timerSeekBar.setProgress(startTime);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void updateTimer(int progress) {
        int minutes = progress / 60;
        int seconds = progress % 60;

        String secondString = Integer.toString(seconds);

        if (seconds <= 9) {
            secondString = "0" + secondString;
        }

        timerTextView.setText(minutes + ":" + secondString);
    }

    public void timerClicked(View view) {

        if (counterActive) {
            resetTimer();
        } else {
            counterActive = true;
            timerButton.setText("STOP");
            timerSeekBar.setEnabled(false);
            //+100 seconds for cancelling the time to call this function, so when timer finishes, it finishes right away (with no delay)
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alert);
                    mPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    private void resetTimer() {
        timerTextView.setText("0:30");
        timerSeekBar.setEnabled(true);
        timerSeekBar.setProgress(30);
        timerButton.setText("START");
        countDownTimer.cancel();
        counterActive=false;
    }
}
