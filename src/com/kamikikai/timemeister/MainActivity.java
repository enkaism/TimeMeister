package com.kamikikai.timemeister;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements OnClickListener, OnLongClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Map<String, Alarm> alarmMap;
    private Button bt[];
    private TextView tv[],tv2[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = new Button[6];
        tv = new TextView[6];
        tv2 = new TextView[6];
        //setButtonListener
        bt[0] = (Button) findViewById(R.id.button1);
        bt[0].setOnClickListener(this);
        bt[0].setOnLongClickListener(this);
        bt[1] = (Button) findViewById(R.id.button2);
        bt[1].setOnClickListener(this);
        bt[1].setOnLongClickListener(this);
        bt[2] = (Button) findViewById(R.id.button3);
        bt[2].setOnClickListener(this);
        bt[2].setOnLongClickListener(this);
        bt[3] = (Button) findViewById(R.id.button4);
        bt[3].setOnClickListener(this);
        bt[3].setOnLongClickListener(this);
        bt[4] = (Button) findViewById(R.id.button5);
        bt[4].setOnClickListener(this);
        bt[4].setOnLongClickListener(this);
        bt[5] = (Button) findViewById(R.id.button6);
        bt[5].setOnClickListener(this);
        bt[5].setOnLongClickListener(this);

        tv[0] = (TextView) findViewById(R.id.textView);
        tv[1] = (TextView) findViewById(R.id.textView2);
        tv[2] = (TextView) findViewById(R.id.textView3);
        tv[3] = (TextView) findViewById(R.id.textView4);
        tv[4] = (TextView) findViewById(R.id.textView5);
        tv[5] = (TextView) findViewById(R.id.textView6);

        tv2[0] = (TextView) findViewById(R.id.textView7);
        tv2[1] = (TextView) findViewById(R.id.textView8);
        tv2[2] = (TextView) findViewById(R.id.textView9);
        tv2[3] = (TextView) findViewById(R.id.textView10);
        tv2[4] = (TextView) findViewById(R.id.textView11);
        tv2[5] = (TextView) findViewById(R.id.textView12);

        alarmMap = readAlarm();
        for (int i = 0; i < 6; i++) {
            String st,hour,minute,second;
            switch (i) {
                case 0:
                    st = "a1";
                    break;
                case 1:
                    st = "a2";
                    break;
                case 2:
                    st = "a3";
                    break;
                case 3:
                    st = "a4";
                    break;
                case 4:
                    st = "a5";
                    break;
                case 5:
                    st = "a6";
                    break;
                default:
                    st = "a1";
                    break;
            }
            bt[i].setBackgroundResource(alarmMap.get(st).getIcon());
            if(alarmMap.get(st).getHour(alarmMap.get(st).getTime())<10){
                hour = "0"+alarmMap.get(st).getHour(alarmMap.get(st).getTime());
            } else {
                hour = ""+alarmMap.get(st).getHour(alarmMap.get(st).getTime());
            }
            if(alarmMap.get(st).getMinute(alarmMap.get(st).getTime())<10){
                minute = "0"+alarmMap.get(st).getMinute(alarmMap.get(st).getTime());
            } else {
                minute = ""+alarmMap.get(st).getMinute(alarmMap.get(st).getTime());
            }
            if(alarmMap.get(st).getSecond(alarmMap.get(st).getTime())<10){
                second = "0"+alarmMap.get(st).getSecond(alarmMap.get(st).getTime());
            } else {
                second = ""+alarmMap.get(st).getSecond(alarmMap.get(st).getTime());
            }
            tv[i].setText(hour+":"+minute+":"+second);
            if(checkPref(st)){
                tv2[i].setTextColor(Color.rgb(234,84,20));
                tv2[i].setText("●");
            } else {
                tv2[i].setText("");
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        alarmMap = readAlarm();
        for (int i = 0; i < 6; i++) {
            String st,hour,minute,second;
            switch (i) {
                case 0:
                    st = "a1";
                    break;
                case 1:
                    st = "a2";
                    break;
                case 2:
                    st = "a3";
                    break;
                case 3:
                    st = "a4";
                    break;
                case 4:
                    st = "a5";
                    break;
                case 5:
                    st = "a6";
                    break;
                default:
                    st = "a1";
                    break;
            }
            bt[i].setBackgroundResource(alarmMap.get(st).getIcon());
            if(alarmMap.get(st).getHour(alarmMap.get(st).getTime())<10){
                hour = "0"+alarmMap.get(st).getHour(alarmMap.get(st).getTime());
            } else {
                hour = ""+alarmMap.get(st).getHour(alarmMap.get(st).getTime());
            }
            if(alarmMap.get(st).getMinute(alarmMap.get(st).getTime())<10){
                minute = "0"+alarmMap.get(st).getMinute(alarmMap.get(st).getTime());
            } else {
                minute = ""+alarmMap.get(st).getMinute(alarmMap.get(st).getTime());
            }
            if(alarmMap.get(st).getSecond(alarmMap.get(st).getTime())<10){
                second = "0"+alarmMap.get(st).getSecond(alarmMap.get(st).getTime());
            } else {
                second = ""+alarmMap.get(st).getSecond(alarmMap.get(st).getTime());
            }
            tv[i].setText(hour+":"+minute+":"+second);
            if(checkPref(st)){
                tv2[i].setTextColor(Color.rgb(234,84,20));
                tv2[i].setText(" ●");
            } else {
                tv2[i].setText("");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClick(View v) {
        Intent intent = new Intent("maintotimer");
        switch (v.getId()) {
            case R.id.button1:
                intent.putExtra("alarm", alarmMap.get("a1"));
                intent.putExtra("key", "a1");
                break;
            case R.id.button2:
                intent.putExtra("alarm", alarmMap.get("a2"));
                intent.putExtra("key", "a2");
                break;
            case R.id.button3:
                intent.putExtra("alarm", alarmMap.get("a3"));
                intent.putExtra("key", "a3");
                break;
            case R.id.button4:
                intent.putExtra("alarm", alarmMap.get("a4"));
                intent.putExtra("key", "a4");
                break;
            case R.id.button5:
                intent.putExtra("alarm", alarmMap.get("a5"));
                intent.putExtra("key", "a5");
                break;
            case R.id.button6:
                intent.putExtra("alarm", alarmMap.get("a6"));
                intent.putExtra("key", "a6");
                break;
        }
        intent.setClassName(this.getPackageName(), TimerActivity.class.getName());
        startActivity(intent);
    }

    public boolean onLongClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.button1:
                intent.putExtra("editor", alarmMap.get("a1"));
                intent.putExtra("editorNum", "a1");
                break;
            case R.id.button2:
                intent.putExtra("editor", alarmMap.get("a2"));
                intent.putExtra("editorNum", "a2");
                break;
            case R.id.button3:
                intent.putExtra("editor", alarmMap.get("a3"));
                intent.putExtra("editorNum", "a3");
                break;
            case R.id.button4:
                intent.putExtra("editor", alarmMap.get("a4"));
                intent.putExtra("editorNum", "a4");
                break;
            case R.id.button5:
                intent.putExtra("editor", alarmMap.get("a5"));
                intent.putExtra("editorNum", "a5");
                break;
            case R.id.button6:
                intent.putExtra("editor", alarmMap.get("a6"));
                intent.putExtra("editorNum", "a6");
                break;
        }

        intent.setClassName(this.getPackageName(), EditActivity.class.getName());
        startActivity(intent);
        return true;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Alarm> readAlarm() {
        Map<String, Alarm> data = new HashMap<String, Alarm>();
        try {
            FileInputStream fis = openFileInput("Alarm.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            data = (Map<String, Alarm>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            Log.d(TAG, "InError");
            writeAlarm();
            data = readAlarm();
        }

        return data;
    }

    public void writeAlarm() {
        Map<String, Alarm> data = new HashMap<String, Alarm>();
        data.put("a1", new Alarm("カップラーメン", 180, 0,
                R.drawable.image_main_noodle, 0));
        data.put("a2", new Alarm("テレビ", 3600, 0,
                R.drawable.image_main_tv, 1));
        data.put("a3", new Alarm("勉強", 3000, 0,
                R.drawable.image_main_study, 2));
        data.put("a4", new Alarm("休憩", 600, 0,
                R.drawable.image_main_teatime, 3));
        data.put("a5", new Alarm("火", 1800, 0,
                R.drawable.image_main_fire, 4));
        data.put("a6", new Alarm("仮眠", 3000, 0,
                R.drawable.image_main_sleep, 5));
        try {
            FileOutputStream fos = openFileOutput("Alarm.dat", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
        } catch (Exception e) {
            Log.d(TAG, "OutError");
        }
    }

    public void setNotification(Alarm alm) {

        //notification
        Intent intentB = new Intent(MainActivity.this, NotificationChangeService.class);
        intentB.putExtra("second", alm.getSecond(alm.getTime()));
        intentB.putExtra("minute", alm.getMinute(alm.getTime()));
        intentB.putExtra("hour", alm.getHour(alm.getTime()));
        startService(intentB);
        Log.d(TAG, "" + alm.getTime());
    }

    private boolean checkPref(String key) {
        SharedPreferences pref = getSharedPreferences(Res.PREF_KEY,Activity.MODE_PRIVATE);
        if (pref.getBoolean(key + "flag", false)) return true;
        return false;
    }
}