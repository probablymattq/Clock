package com.matter.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

public class AnalogClockView extends View {

    private Paint mPaint;
    private float mHourHandLength;
    private float mHourHandWidth;
    private int mHourHandColor;
    private float mMinuteHandLength;
    private float mMinuteHandWidth;
    private int mMinuteHandColor;
    private float mSecondHandLength;
    private float mSecondHandWidth;
    private int mSecondHandColor;
    private int mBackgroundColor;
    private int mCenterX;
    private int mCenterY;
    private int mDotRadius = 3;
    private int mDotColor = Color.WHITE;
    private float mDotDistance = 5;

    private final float DEFAULT_HOUR_HAND_LENGTH = 150;
    private final float DEFAULT_HOUR_HAND_WIDTH = 10;
    private final int DEFAULT_HOUR_HAND_COLOR = Color.WHITE;
    private final float DEFAULT_MINUTE_HAND_LENGTH = 210;
    private final float DEFAULT_MINUTE_HAND_WIDTH = 7;
    private final int DEFAULT_MINUTE_HAND_COLOR = Color.WHITE;
    private final float DEFAULT_SECOND_HAND_LENGTH = 230;
    private final float DEFAULT_SECOND_HAND_WIDTH = 4;
    private final int DEFAULT_SECOND_HAND_COLOR = Color.parseColor("#ffea00");

    private Handler mHandler = new Handler();
    private Runnable mUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
            mHandler.postDelayed(this, 1000);
        }
    };

    public AnalogClockView(Context context) {
        super(context);
        init(null, 0);
    }

    public AnalogClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
        mHandler.post(mUpdateRunnable);
    }

    public AnalogClockView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mHourHandLength = DEFAULT_HOUR_HAND_LENGTH;
        mHourHandWidth = DEFAULT_HOUR_HAND_WIDTH;
        mHourHandColor = DEFAULT_HOUR_HAND_COLOR;
        mMinuteHandLength = DEFAULT_MINUTE_HAND_LENGTH;
        mMinuteHandWidth = DEFAULT_MINUTE_HAND_WIDTH;
        mMinuteHandColor = DEFAULT_MINUTE_HAND_COLOR;
        mSecondHandLength = DEFAULT_SECOND_HAND_LENGTH;
        mSecondHandWidth = DEFAULT_SECOND_HAND_WIDTH;
        mSecondHandColor = DEFAULT_SECOND_HAND_COLOR;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(mBackgroundColor);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        float hourAngle = (hour * 30) + (minute / 2f);
        float minuteAngle = minute * 6;
        float secondAngle = second * 6;

        mPaint.setStrokeWidth(mHourHandWidth);
        mPaint.setColor(mHourHandColor);
        float hourHandEndX = (float) (mCenterX + mHourHandLength * Math.sin(Math.toRadians(hourAngle)));
        float hourHandEndY = (float) (mCenterY - mHourHandLength * Math.cos(Math.toRadians(hourAngle)));
        canvas.drawLine(mCenterX, mCenterY, hourHandEndX, hourHandEndY, mPaint);

        mPaint.setStrokeWidth(mMinuteHandWidth);
        mPaint.setColor(mMinuteHandColor);
        float minuteHandEndX = (float) (mCenterX + mMinuteHandLength * Math.sin(Math.toRadians(minuteAngle)));
        float minuteHandEndY = (float) (mCenterY - mMinuteHandLength * Math.cos(Math.toRadians(minuteAngle)));
        canvas.drawLine(mCenterX, mCenterY, minuteHandEndX, minuteHandEndY, mPaint);

        mPaint.setStrokeWidth(mSecondHandWidth);
        mPaint.setColor(mSecondHandColor);
        float secondHandEndX = (float) (mCenterX + mSecondHandLength * Math.sin(Math.toRadians(secondAngle)));
        float secondHandEndY = (float) (mCenterY - mSecondHandLength * Math.cos(Math.toRadians(secondAngle)));
        canvas.drawLine(mCenterX, mCenterY, secondHandEndX, secondHandEndY, mPaint);


        mPaint.setStrokeWidth(0);
        mPaint.setColor(mDotColor);
        for (int i = 0; i < 60; i++) {
            float angle = i * 6;
            float mClockRadius = Math.min(getWidth(), getHeight()) / 2f;
            float dotX = (float) (mCenterX + (mClockRadius - mDotDistance) * Math.sin(Math.toRadians(angle)));
            float dotY = (float) (mCenterY - (mClockRadius - mDotDistance) * Math.cos(Math.toRadians(angle)));
            if(i % 5 == 0) {
                mPaint.setColor(Color.parseColor("#ffea00"));
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setStrokeWidth(10);
                float lineEndX = (float) (mCenterX + (mClockRadius - mDotDistance - mDotRadius - 30) * Math.sin(Math.toRadians(angle)));
                float lineEndY = (float) (mCenterY - (mClockRadius - mDotDistance - mDotRadius - 30) * Math.cos(Math.toRadians(angle)));

                canvas.drawLine(dotX, dotY, lineEndX, lineEndY, mPaint);
            } else {
                mPaint.setColor(mDotColor);
                mPaint.setStyle(Paint.Style.FILL);
                mDotRadius = 3;
                canvas.drawCircle(dotX, dotY, mDotRadius, mPaint);
            }
        }
    }

    public void setHourHandLength(float length) {
        mHourHandLength = length;
        invalidate();
    }

    public void setHourHandWidth(float width) {
        mHourHandWidth = width;
        invalidate();
    }

    public void setHourHandColor(int color) {
        mHourHandColor = color;
        invalidate();
    }

    public void setMinuteHandLength(float length) {
        mMinuteHandLength = length;
        invalidate();
    }

    public void setMinuteHandWidth(float width) {
        mMinuteHandWidth = width;
        invalidate();
    }

    public void setMinuteHandColor(int color) {
        mMinuteHandColor = color;
        invalidate();
    }

    public void setSecondHandLength(float length) {
        mSecondHandLength = length;
        invalidate();
    }

    public void setSecondHandWidth(float width) {
        mSecondHandWidth = width;
        invalidate();
    }

    public void setSecondHandColor(int color) {
        mSecondHandColor = color;
        invalidate();
    }
    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
        invalidate();
    }
}