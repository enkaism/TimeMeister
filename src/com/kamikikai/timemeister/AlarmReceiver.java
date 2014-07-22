package com.kamikikai.timemeister;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver{
	
	private static final String TAG = AlarmReceiver.class.getSimpleName();
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG,intent.getStringExtra("name"));
		//send intent to stop activity
		intent.setClass(context, StopActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
	    // acquire wake lock
	    PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
	    PowerManager.WakeLock mWakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "SimpleTimer");
	    mWakelock.acquire(20000);
	    
	}
}
