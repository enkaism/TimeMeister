package com.kamikikai.timemeister;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.*;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.Calendar;

public class TimerView extends SurfaceView implements SurfaceHolder.Callback,
        Runnable {
    private Thread thread;
    private SurfaceHolder holder;

    private int screenWidth, screenHeight;
    private float ballx;
    private float bally;
    private static double dtheta;
    private int count = 0;
    private int time = 30;
    private int TIME;
    private int radius;
    private RectF oval, rectStop, rectBack;
    private boolean isRunning;
    private String timerKey;
    private int intentCode;
    private Calendar cal;
    private long calPrev;

    private Bitmap icon, background, stop, pause, back, number[], coron, hosei, whitebar;

    public TimerView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        holder.setFixedSize(getWidth(), getHeight());

        cal = Calendar.getInstance();
        calPrev = 0;
        // setProperties
        icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.button_main_egg);

        background = BitmapFactory.decodeResource(getResources(),
                R.drawable.background_timer);

        stop = BitmapFactory.decodeResource(getResources(),
                R.drawable.button_timer_stop);

        back = BitmapFactory.decodeResource(getResources(),
                R.drawable.button_timer_back);

        coron = BitmapFactory.decodeResource(getResources(), R.drawable.image_timer_colon);
        hosei = BitmapFactory.decodeResource(getResources(), R.drawable.image_timer_hosei);
        whitebar = BitmapFactory.decodeResource(getResources(), R.drawable.image_timer_whitebar);
        number = new Bitmap[10];
        for (int i = 0; i < 10; i++) {
            int n = R.drawable.image_timer_0;
            switch (i) {
                case 0:
                    n = R.drawable.image_timer_0;
                    break;
                case 1:
                    n = R.drawable.image_timer_1;
                    break;
                case 2:
                    n = R.drawable.image_timer_2;
                    break;
                case 3:
                    n = R.drawable.image_timer_3;
                    break;
                case 4:
                    n = R.drawable.image_timer_4;
                    break;
                case 5:
                    n = R.drawable.image_timer_5;
                    break;
                case 6:
                    n = R.drawable.image_timer_6;
                    break;
                case 7:
                    n = R.drawable.image_timer_7;
                    break;
                case 8:
                    n = R.drawable.image_timer_8;
                    break;
                case 9:
                    n = R.drawable.image_timer_9;
                    break;
            }
            number[i] = BitmapFactory.decodeResource(getResources(), n);
        }

        dtheta = Math.PI * 12 / 180;
        radius = 8;
        ballx = (float) Math.cos(dtheta) * 200;
        bally = (float) Math.sin(dtheta) * 200;
        oval = new RectF(20.0f, 20.0f, 100.0f, 100.0f);
        isRunning = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int f, int w, int h) {
        screenWidth = w;
        screenHeight = h;

        thread = new Thread(this);
        thread.start();
        isRunning = true;

        rectStop = new RectF(layoutRectf(back, (float) 0.75, 0), layoutRectf(
                back, (float) 0.85, 1), layoutRectf(back, (float) 0.70, 2),
                layoutRectf(back, (float) 0.85, 3));
        rectBack = new RectF(layoutRectf(back, (float) 0.25, 0), layoutRectf(
                back, (float) 0.85, 1), layoutRectf(back, (float) 0.30, 2),
                layoutRectf(back, (float) 0.85, 3));


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread = null;
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {

            if (time > 0) {
                count ++;
                if (count > 60) {
                    count = 0;
                    time--;
                }
            }
            doDraw(holder);
        }
    }

    private void doDraw(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            Paint paint = new Paint();
            canvas.drawColor(Color.BLACK);
            paint.setColor(0xC2F8FF00);
            paint.setAlpha(180);
            paint.setAntiAlias(true);

            // drawBackground
            canvas.drawBitmap(background, 0, 0, paint);

            // drawCircles around Arc

            for (int i = 0; i < 30; i++) {
                if (count / 2 + 4 > i + 1 && count / 2 < i + 1) {
                    radius += count*60/1000 % 4;
                } else {
                    radius -= count*60/1000 % 4;
                    if (radius < 8)
                        radius = 8;
                }
                ballx = (float) Math.cos(dtheta * i) * 200;
                bally = (float) Math.sin(dtheta * i) * 200;

                canvas.drawCircle((float) (ballx + screenWidth * 0.5), (float) (bally + screenHeight * 0.5), radius, paint);
            }

            // drawArc around Icon
            paint.setStyle(Style.STROKE);
            paint.setStrokeWidth(10);
            oval.set((float) (layoutRectf(icon, (float) 0.5, 0) - 15),
                    (float) (layoutRectf(icon, (float) 0.5, 1) - 15),
                    (float) (layoutRectf(icon, (float) 0.5, 2) + 15),
                    (float) (layoutRectf(icon, (float) 0.5, 3) + 15));
            canvas.drawArc(oval, (float) 0, (float) 360 * time / TIME, false,
                    paint);

            // drawBitmap and Text
            paint.setStyle(Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawRect(0,(float)(screenHeight*0.15-number[0].getHeight()-10),screenWidth,(float)(screenHeight*0.15+number[0].getHeight()+10),paint);

            drawDigits(canvas, paint);
            canvas.drawBitmap(back, layoutRectf(back, (float) 0.30, 0),
                    layoutRectf(back, (float) 0.85, 1), paint);
            canvas.drawBitmap(icon, layoutRectf(icon, (float) 0.5, 0),
                    layoutRectf(icon, (float) 0.5, 1), paint);
            canvas.drawBitmap(stop, layoutRectf(stop, (float) 0.70, 0),
                    layoutRectf(stop, (float) 0.85, 1), paint);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    private float layoutRectf(Bitmap b, float rate, int type) {
        switch (type) {
            case 0:
                return screenWidth * rate - b.getWidth() / 2;
            case 1:
                return screenHeight * rate - b.getHeight() / 2;
            case 2:
                return screenWidth * rate + b.getWidth() / 2;
            case 3:
                return screenHeight * rate + b.getHeight() / 2;

        }
        return screenWidth * rate;
    }

    private void drawDigits(Canvas canvas, Paint paint) {
        int second1 = getSecond(1);
        int second2 = getSecond(2);
        int minute1 = getMinute(1);
        int minute2 = getMinute(2);
        int hour1 = getHour(1);
        int hour2 = getHour(2);

        canvas.drawBitmap(number[hour2],layoutRectf(number[0],(float)0.2,0),layoutRectf(number[0],(float)0.15,1),paint);
        canvas.drawBitmap(number[hour1],layoutRectf(number[0],(float)0.2,0)+number[0].getWidth(),layoutRectf(number[0],(float)0.15,1),paint);
        canvas.drawBitmap(coron,layoutRectf(number[0],(float)0.2,0)+number[0].getWidth()*2,layoutRectf(number[0],(float)0.15,1),paint);

        canvas.drawBitmap(number[minute2],layoutRectf(number[0],(float)0.2,0)+number[0].getWidth()*3,layoutRectf(number[0],(float)0.15,1),paint);
        canvas.drawBitmap(number[minute1],layoutRectf(number[0],(float)0.2,0)+number[0].getWidth()*4,layoutRectf(number[0],(float)0.15,1),paint);
        canvas.drawBitmap(coron,layoutRectf(number[0],(float)0.2,0)+number[0].getWidth()*5,layoutRectf(number[0],(float)0.15,1),paint);
        canvas.drawBitmap(number[second2],layoutRectf(number[0],(float)0.2,0)+number[0].getWidth()*6,layoutRectf(number[0],(float)0.15,1),paint);
        canvas.drawBitmap(number[second1],layoutRectf(number[0],(float)0.2,0)+number[0].getWidth()*7,layoutRectf(number[0],(float)0.15,1),paint);

    }

    private int getSecond(int rank) {
        if (rank == 1) {
            return time % 60 % 10;
        } else {
            return time % 60 / 10;
        }
    }

    private int getMinute(int rank) {
        if (rank == 1) {
            return (time / 60) % 60 % 10;
        } else {
            return (time / 60) % 60 / 10;
        }
    }

    private int getHour(int rank) {
        if (rank == 1) {
            return (time / 3600) % 10;
        } else {
            return (time / 3600) / 10;
        }
    }

    public void setTime(int time) {
        this.time = time;
        this.TIME = time;
    }

    public void setBitmap(int bitmap) {
        this.icon = BitmapFactory.decodeResource(getResources(), bitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        RectF rectE = new RectF(e.getX(), e.getY(), e.getX() + 1, e.getY() + 1);
        Activity act = (Activity) getContext();
        if (rectStop.contains(rectE)) {
            isRunning = isRunning ? false : true;
            act.stopService(new Intent(act, NotificationChangeService.class));
            Toast.makeText(getContext(), "タイマーをキャンセルしました", Toast.LENGTH_SHORT)
                    .show();
            cancelAlarm();
            act.finish();

        } else if (rectBack.contains(rectE)) {
            act.finish();
        }
        return false;
    }

    private void cancelAlarm() {
        //timer stop
        Intent intentA = new Intent(getContext(), AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(getContext(),
                intentCode, intentA, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

        //writePref(cal,timerKey,false);
        AlarmManager am = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);
        //Shared Preferenceの初期化
        SharedPreferences pref = getContext().getSharedPreferences(Res.PREF_KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(timerKey + "flag", false);
        editor.commit();
    }

    public void setTimerKey(String timerKey) {
        this.timerKey = timerKey;
    }

    public void setIntentCode(int intentCode) {
        this.intentCode = intentCode;
    }
    public void setIcon(int icon){
        this.icon = BitmapFactory.decodeResource(getResources(),icon);
    }
}
