package com.kamikikai.timemeister;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class TimerActivity extends Activity implements TimerPreference {

    private static final String TAG = TimerActivity.class.getSimpleName();
    private Alarm alm;
    private String timerKey;
    TimerView tv;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alm = new Alarm("err", 5, 0, R.drawable.button_main_sleep, 0);
        tv = new TimerView(this);
        pref = getSharedPreferences(Res.PREF_KEY, Activity.MODE_PRIVATE);

        // mainからのintent
        Intent intent = getIntent();
        if (intent != null) {
            alm = (Alarm) intent.getSerializableExtra("alarm");
            timerKey = intent.getStringExtra("key");
        } else if (intent.getAction() == "notificationtotimer") {
            tv.setTime(intent.getIntExtra("time", 5));
        }

        //Timer flag
        if (checkPref(timerKey)) {
            //タイマー起動時
            readPref(timerKey);
            Calendar cal = Calendar.getInstance();
            tv.setTime((int) ((pref.getLong(timerKey + "time", 0) - cal.getTimeInMillis()) / 1000));
        } else {
            //起動してない時
            Toast.makeText(getApplication(), "" + alm.getHour(alm.getTime()) + "時間" + alm.getMinute(alm.getTime()) + "分" + alm.getSecond(alm.getTime()) + "秒のタイマーを起動しました。", Toast.LENGTH_SHORT).show();
            tv.setTime(alm.getTime());
            setAlarm();
        }
        tv.setIntentCode(alm.getIntentCode());
        tv.setTimerKey(timerKey);
        tv.setIcon(Res.drawableButtonTapped.get(alm.getIconId(alm.getIcon())));
        setContentView(tv);
    }

    @Override
    public void onResume(){
        super.onResume();
        //Timer flag
        if (checkPref(timerKey)) {
            //タイマー起動時
            readPref(timerKey);
            Calendar cal = Calendar.getInstance();
            tv.setTime((int) ((pref.getLong(timerKey + "time", 0) - cal.getTimeInMillis()) / 1000));
        }
    }

    public void setAlarm() {
        // Alarmのセット
        Intent intentA = new Intent(TimerActivity.this, AlarmReceiver.class);
        intentA.putExtra("icon", Res.drawableButton.get(alm.getIconId(alm.getIcon())));
        intentA.putExtra("name", alm.getName());
        intentA.putExtra("key",timerKey);
         PendingIntent sender = PendingIntent.getBroadcast(TimerActivity.this,
                alm.getIntentCode(), intentA, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.SECOND, alm.getSecond(alm.getTime()));
        cal.add(Calendar.MINUTE, alm.getMinute(alm.getTime()));
        cal.add(Calendar.HOUR, alm.getHour(alm.getTime()));

        writePref(cal, timerKey, true);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);

    }

    @Override
    public boolean checkPref(String key) {
        if (pref.getBoolean(key + "flag", false)) return true;
        return false;
    }

    @Override
    public void writePref(Calendar cal, String key, boolean flag) {
        //SharedPreferenceに登録
        editor = pref.edit();
        editor.putLong(key + "time", cal.getTimeInMillis());
        editor.putBoolean(key + "flag", flag);
        editor.commit();
    }

    @Override
    public void readPref(String key) {

    }
}
