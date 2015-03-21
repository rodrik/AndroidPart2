package br.com.rodrigo.ijk;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	private static final String TAG = "RodrigoDailySelfie";

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;

	private Uri fileUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set the adapter on this ListActivity's built-in ListView
		SelfieAdapter adapter = new SelfieAdapter(this);
		setListAdapter(adapter);

		ListView lv = getListView();

		// Set an setOnItemClickListener on the ListView
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				// Start Android's View Image Intent
				Selfie selfie = (Selfie)parent.getAdapter().getItem(position);
				Intent intent = new Intent();
				intent.setAction(android.content.Intent.ACTION_VIEW); 
				intent.setDataAndType(Uri.fromFile(selfie.getFile()),"image/*");

			}
		});
		
		scheduleNotificationService();
	}

	private void scheduleNotificationService() {
		Intent intentService = new Intent(getApplicationContext(), NotificationService.class);
		PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 2222, intentService,
		PendingIntent.FLAG_CANCEL_CURRENT);
		
		// Registering our pending intent with AlarmManager
		// Current time +2min
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 2);
		
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				Log.d(TAG, "Picture ok");
				Toast.makeText(this, "Image saved to:\n" + fileUri.getPath(), Toast.LENGTH_LONG).show();
				
				// Update ListView
				((BaseAdapter)getListAdapter()).notifyDataSetChanged();
				
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture, do nothing
				Log.d(TAG, "Picture cancelled");

			} else {
				// Weird error, do nothing
				Log.d(TAG, "Picture error");
				Toast.makeText(this, R.string.error_saving_picture, Toast.LENGTH_LONG).show();

			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/** 
	 * Create a File for saving an image
	 * 
	 * {@link http://developer.android.com/guide/topics/media/camera.html}
	 * */
	private static File getOutputMediaFile() {
		// To be safe, you should check that the SDCard is mounted
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return null;
		}

		File mediaStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (! mediaStorageDir.exists()){
			if (! mediaStorageDir.mkdirs()){
				Log.d(TAG, "Failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
				"IMG_"+ timeStamp + ".jpg");

		return mediaFile;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_camera:
			Log.d(TAG, "Action camera selected");
			
			// Start Android's Camera Intent
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File newFile = getOutputMediaFile();
			if(newFile==null) {
				Log.d(TAG, "SDCard error");
				Toast.makeText(this, R.string.error_accessing_storage, Toast.LENGTH_LONG).show();
				return false;
			}
			
			fileUri = Uri.fromFile(newFile); // create a file to save the image
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
			startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}
	}

}
