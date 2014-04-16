package com.example.takingaphoto;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract.Root;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	public static final String DEBUGTAG = "TakingAPhoto";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private File imageFile;
		private static final int PHOTO_TAKEN = 0;
		private View rootView;
		
		public PlaceholderFragment() {
		}

		private void addSnapButtonListener() {
			Button snap = (Button) rootView.findViewById(R.id.snap);
			snap.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					
					// /storage/emulated/0/Pictures/passpoints_image
					File pictureDirectory = Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
					imageFile = new File(pictureDirectory, "passpoints_image");

					Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
					startActivityForResult(i, PHOTO_TAKEN);

				}
			});
		}
		
		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			if (requestCode == PHOTO_TAKEN) {
				Bitmap photo = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
				Log.d(MainActivity.DEBUGTAG, imageFile.getAbsolutePath());
				if (photo != null) {
					ImageView imageView = (ImageView)rootView.findViewById(R.id.image);
					imageView.setImageBitmap(photo);
				}
				else {
					Toast.makeText(getActivity(), "Unable to save photo file.", Toast.LENGTH_LONG).show();
				}
			}
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			this.rootView = rootView;
			addSnapButtonListener();

			return rootView;
		}
	}

}
