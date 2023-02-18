package com.matter.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView clockTextView = findViewById(R.id.clockTextView);
        TextView dateTextView = findViewById(R.id.dateTextView);

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM", Locale.getDefault());
        String dateString = dateFormat.format(date);
        String dayOfWeek = dateString.split(" ")[1];
        String month = dateString.split(" ")[2];

        SpannableString spannableString = new SpannableString(dateString);

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.YELLOW);
        spannableString.setSpan(colorSpan, dateString.indexOf(dayOfWeek), dateString.indexOf(month), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        dateTextView.setText(spannableString);


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Date currentTime = new Date();
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                String timeString = timeFormat.format(currentTime);

                SpannableString spannableString = new SpannableString(timeString);

                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.YELLOW);
                spannableString.setSpan(colorSpan, spannableString.length() - 2, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                runOnUiThread(() -> clockTextView.setText(spannableString));
            }
        }, 0, 1000);

        Animation anim = new TranslateAnimation(0, 0, 0, -20);
        anim.setDuration(1000);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.REVERSE);
        clockTextView.startAnimation(anim);
        dateTextView.startAnimation(anim);
    }
}