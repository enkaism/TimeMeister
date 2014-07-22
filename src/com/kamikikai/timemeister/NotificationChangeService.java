package com.kamikikai.timemeister;

import java.util.Calendar;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.ConditionVariable;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotificationChangeService extends Service {

	private static final String TAG = NotificationChangeService.class
			.getSimpleName();

	private NotificationManager mNotificationManager;
	private ConditionVariable mCondition;
	private static int WAIT_TIME = 1000;
	private Calendar calNow, calStop;
	private NotificationCompat.Builder mBuilder;
	private Runnable mTask = new Runnable() {
		public void run() {
			String time;
			calNow.setTimeInMillis(System.currentTimeMillis());
			time = "あと"
					+ (calStop.get(Calendar.HOUR) - calNow.get(Calendar.HOUR))
					+ "時間"
					+ (calStop.get(Calendar.MINUTE) - calNow.get(Calendar.MINUTE))
					+ "分"
					+ (calStop.get(Calendar.SECOND) - calNow.get(Calendar.SECOND))
					+ "秒で終了します。";
			showNotification(R.drawable.ic_launcher, time);
			//NotificationChangeService.this.stopSelf();
		}
	};

	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate");
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		mBuilder = new NotificationCompat.Builder(getApplicationContext());
		calNow = Calendar.getInstance();
		calStop = Calendar.getInstance();
		// サービスはメインスレッドで実行されるため、処理を止めないように更新処理を別スレッドに。
		Thread thread = new Thread(null, mTask, "NotifyingService");
		mCondition = new ConditionVariable(false);
		thread.start();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "onStartCommand Received start id " + startId + ": "
				+ intent);

		calStop.setTimeInMillis(System.currentTimeMillis());
		/*
		calStop.add(Calendar.SECOND, intent.getIntExtra("second", 0));
		calStop.add(Calendar.MINUTE, intent.getIntExtra("minute", 0));
		calStop.add(Calendar.HOUR, intent.getIntExtra("hour", 0));*/
		// 明示的にサービスの起動、停止が決められる場合の返り値
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		// サービスの停止時、通知内容を破棄する
		mNotificationManager.cancel(1);
		// スレッドを停止するため、ブロックを解除
		mCondition.open();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private void showNotification(int drawableId, CharSequence text) {
		Intent intent = new Intent("notificationtotimer");
		intent.setClass(this, TimerActivity.class);
		intent.putExtra("time", 20);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent, 0);
		mBuilder.setSmallIcon(R.drawable.ic_launcher).setContentTitle("tokei")
				.setContentText(text).setAutoCancel(true)
				.setContentIntent(contentIntent);

		mNotificationManager.notify(1, mBuilder.build());
	}

}
