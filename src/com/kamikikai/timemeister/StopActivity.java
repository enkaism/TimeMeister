package com.kamikikai.timemeister;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class StopActivity extends Activity implements OnClickListener{

	private static final String TAG = StopActivity.class.getSimpleName();
	private ImageView icon;
    private int iconId;
    private Button buttonStop;
    private MediaPlayer mp;
    private AudioManager am;
    private String key;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop);
        buttonStop = (Button) findViewById(R.id.buttonStop);
        buttonStop.setOnClickListener(this);
		// mainからのintent
		Intent intent = getIntent();
		if (intent != null) {
			Log.d(TAG, intent.toString());
			Log.d(TAG, ""+intent.getIntExtra("icon", 22));
            key = intent.getStringExtra("key");
            iconId = intent.getIntExtra("icon",0);
		}
        buttonStop.setBackgroundResource(iconId);
		Toast.makeText(getApplication(), "タイマー終了しました。", Toast.LENGTH_SHORT).show();
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,(int)(am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)/2),0);
        mp = MediaPlayer.create(this,R.raw.normal2);
        mp.start();

		

	}

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mp.isPlaying()){
            mp.stop();
        }
        prefFlagReset();
    }

    public void onClick(View v) {
        if(mp.isPlaying()){
            mp.stop();
        }
        prefFlagReset();
        finish();
    }

    private void prefFlagReset(){
        SharedPreferences pref = getSharedPreferences(Res.PREF_KEY,MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key + "flag",false);
        editor.commit();
    }
}
