package br.com.rodrigo.ijk;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.ListActivity;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	private static final String TAG = "RodrigoDailySelfie";

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;

	private Uri fileUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);

		// Create a new Adapter containing a list of colors
		// Set the adapter on this ListActivity's built-in ListView
		SelfieAdapter adapter = new SelfieAdapter(this, getSelfieList());
		setListAdapter(adapter);
		
		ListView lv = getListView();
		
		// Enable filtering when the user types in the virtual keyboard
		//lv.setTextFilterEnabled(true);
		
		// Set an setOnItemClickListener on the ListView
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Display a Toast message indicting the selected item
				Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	private List<Selfie> getSelfieList() {
		List<Selfie> selfies = new ArrayList<Selfie>();
		Selfie s1 = new Selfie(1L, new Date(), "file1.jpg");
		Selfie s2 = new Selfie(2L, new Date(), "file2.jpg");
		Selfie s3 = new Selfie(3L, new Date(), "file3.jpg");
		selfies.add(s1);
		selfies.add(s2);
		selfies.add(s3);
		return selfies;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				Log.d(TAG, "Picture ok");
				Toast.makeText(this, "Image saved to:\n" + fileUri.getPath(), Toast.LENGTH_LONG).show();

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
	 * Create a file Uri for saving an image 
	 * 
	 * {@link http://developer.android.com/guide/topics/media/camera.html}
	 * */
	private static Uri getOutputMediaFileUri(){
		return Uri.fromFile(getOutputMediaFile());
	}

	/** 
	 * Create a File for saving an image
	 * 
	 * {@link http://developer.android.com/guide/topics/media/camera.html}
	 * */
	private static File getOutputMediaFile(){
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

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
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			fileUri = getOutputMediaFileUri(); // create a file to save the image
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
			startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			return true;

		case R.id.action_settings:
			Log.d(TAG, "Action settings selected");
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}
	}

}
