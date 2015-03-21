package br.com.rodrigo.ijk;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationService extends IntentService {

	private static final String TAG = "MyNotificationService";

	private static final int MY_NOTIFICATION_ID = 1;

	public NotificationService(){
		super("");
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		Log.d(TAG, "NotificationService onHandleIntent: " + arg0);
		
		Intent mNotificationIntent = new Intent(getApplicationContext(), MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, mNotificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

		// Define the Notification's expanded message and Intent:
		Notification.Builder notificationBuilder = new Notification.Builder(
				getApplicationContext())
		.setTicker("Time for a Selfie")
		.setSmallIcon(R.drawable.ic_action_camera)
		.setAutoCancel(true)
		.setContentIntent(pi)
		.setContentTitle("Let's take a selfie!");
		
		// Pass the Notification to the NotificationManager:
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(MY_NOTIFICATION_ID, notificationBuilder.build());
	}

	@Override
	public void onCreate() {
		// Re-scheduling
		Log.d(TAG, "NotificationService onCreate: Re-scheduling");
		
		Intent intentService = new Intent(getApplicationContext(), NotificationService.class);
		PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 2222, intentService,
		PendingIntent.FLAG_CANCEL_CURRENT);
		
		// Registering our pending intent with AlarmManager
		// Current time +2min
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 2);
		
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
		
		super.onCreate();
	}


}
