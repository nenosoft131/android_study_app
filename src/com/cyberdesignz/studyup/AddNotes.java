package com.cyberdesignz.studyup;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.info.ClassInfo;
import com.cyberdesignz.studyup.info.NoteInfo;
import com.cyberdesignz.studyup.io.StudyUpTask;
import com.cyberdesignz.studyup.utils.Utils;
import com.uraroji.garage.android.mp3recvoice.RecMicToMp3;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.location.GpsStatus.NmeaListener;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AddNotes extends Activity {
	private static final String LOG_TAG = "AudioRecordTest";
	static final int SUCCESS_DIALOG = 1;
	static final int ERROR_DIALOG = 2;
	static final int UPLOADING_PROCESS = 3;
	static final int UPDATING_NOTE_PROCESS = 4;
	TextView tv_updateHeading, Tv_addHeading;
	AutoCompleteTextView class_data;
	private String[] classData;

	private File tempFile_image = null;

	public static Bitmap btmap_freehand;

	EditText classdata;
	EditText et_activity_subject;
	boolean record = false;
	String note_id;
	int fnmae1, fname2 = 0;
	int Read_count = 0;
	String user_email;
	EditText et_activity_topic;
	EditText et_activity_note;
	byte[] check_Byte;
	String twoTimesencoded;
	static Bitmap image;
	boolean pcheck = true;
	ProgressDialog pb;
	int finalfile;

	private MediaPlayer mPlayer = null;
	private MediaRecorder mRecorder = null;
	public static String user_id;
	private static String mFileName = null;
	private static String mFileName2 = null;
	private static String mFileName3 = null;
	private static String Audio_file = null;
	private File tempFile = null;
	byte[] check_Byte1;
	private ProgressDialog mProgressDialog;
	private ProgressDialog mProgressDialog1;

	private ArrayList<ClassInfo> classList;
	NoteInfo note_info;

	String subject;
	String classStr;
	String topic;
	String note, mFileNamefinal;
	String user_idc;
	String class_id;
	String noteImage;
	String noteAudio;
	Button Btn_startRec;
	Button Btn_stopRec;
	Button Btn_startPlay;
	Button Btn_stopPlay;
	Button btn_pause;
	Button rec_pause;
	boolean audioChecker, mStartPlaying2 = false;
	boolean audioCheckert = false;
	boolean captureimage = false;
	ProgressBar pbar_recording;
	TextView text_recording;
	View v2;
	Draw d;
	int count_namer;

	Button Btn_updateButton, Btn_addButton;

	boolean check_NoteAdded = false; // check either note is added
	boolean check_NoteUpdate = false;

	private PreferenceHelper prefObj;
	RecMicToMp3 mRecMicToMp3_one;
	RecMicToMp3 mRecMicToMp3_two;
	RecMicToMp3 mRecMicToMp3_final;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mRecMicToMp3_one = new RecMicToMp3(
				Environment.getExternalStorageDirectory() + "/one.mp3", 8000);
		mRecMicToMp3_two = new RecMicToMp3(
				Environment.getExternalStorageDirectory() + "/two.mp3", 8000);
		mRecMicToMp3_final = new RecMicToMp3(
				Environment.getExternalStorageDirectory() + "/final.mp3", 20000);

		setContentView(R.layout.add_notes);
		v2 = findViewById(R.id.view1);

		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		// this is for update/review notes
		prefObj = new PreferenceHelper(this, PreferenceHelper.CurrentUser);
		user_id = prefObj.getPref(PreferenceHelper.Id, "");
		Intent intent = getIntent();
		if (intent.hasExtra("note")) {
			try {

				initview();
				et_activity_note.setText(intent.getExtras().getString("note"));
				et_activity_subject.setText(intent.getExtras().getString(
						"subject"));
				et_activity_topic
						.setText(intent.getExtras().getString("topic"));
				note_id = intent.getExtras().getString("note_id");

				callSpinner();

				classdata = (EditText) findViewById(R.id.et_addnote_class);

				tv_updateHeading.setVisibility(View.VISIBLE);
				Tv_addHeading.setVisibility(View.INVISIBLE);

				Btn_addButton.setVisibility(View.GONE);
				Log.d("asd", "das");

			} catch (Exception ex) {

				Log.d("asd", "das");
				Log.d("asd", "das");
				Log.d("asd", "das");

			}
		} else {
			initview();
			// mFileName
			Boolean isSDPresent = android.os.Environment
					.getExternalStorageState().equals(
							android.os.Environment.MEDIA_MOUNTED);
			if (isSDPresent) {
				mFileName = Environment.getExternalStorageDirectory()
						.getAbsolutePath();
				mFileName += "/one.mp3";
				mFileName2 = Environment.getExternalStorageDirectory()
						.getAbsolutePath();
				mFileName2 += "/two.mp3";
				mFileName3 = Environment.getExternalStorageDirectory()
						.getAbsolutePath();
				mFileName3 += "/final.mp3";
			} else {

				Toast.makeText(this, "Plz insert SD Card", Toast.LENGTH_SHORT)
						.show();
				// finish();

			}

			//

			callSpinner();
			//

			// ////////////////////////////////

			user_email = prefObj.getPref(PreferenceHelper.Email, "");
			prefObj.getPref(PreferenceHelper.Id, "");
			// spinner call
			// Btn_startPlay.setVisibility(View.INVISIBLE);
			Btn_stopPlay.setVisibility(View.INVISIBLE);
			getIntent();
			if (intent.hasExtra("activity")) {
				Log.d("dasd", "asd");
				user_email = intent.getExtras().getString("email");
				callForId();
			}

		}

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub

		IntentFilter filter = new IntentFilter(Utils.ACTION_GET_CLASS);
		registerReceiver(classListReceiver, filter);
		super.onStart();

	}

	public void Onpauseclick(View v) {
		Btn_stopPlay.setVisibility(View.INVISIBLE);
		Btn_startPlay.setVisibility(View.VISIBLE);
		pcheck = false;
		mPlayer.pause();

	}

	private void CreateImageFile(Bitmap selectedImage2) {
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		selectedImage2.compress(CompressFormat.JPEG, 100, bos);
		byteArray = bos;
		// you can create a new file name "test.jpg" in sdcard folder.
		tempFile_image = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "temp.jpg");
		try {
			tempFile_image.createNewFile();
			@SuppressWarnings("resource")
			FileOutputStream fo = new FileOutputStream(tempFile_image);
			fo.write(byteArray.toByteArray());
			tempFile_image.getPath();
		} catch (IOException e) {

			e.printStackTrace();
		}
		// write the bytes in file
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub

		unregisterReceiver(classListReceiver);

		super.onStop();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		callSpinner();
		super.onResume();

	}

	public void OnBuddyClick(View v) {
		Intent i = new Intent(AddNotes.this, mybuddies.class);
		startActivity(i);
	}

	public void OnWriteClick(View v) {
		image = getBitmapFromView();
		Draw d = new Draw(getBaseContext());
		d.onwriteclick();
	}

	public void OnEraseClick(View v) {
		image = getBitmapFromView();
		Draw d = new Draw(getBaseContext());
		d.Oneraseclick();
	}

	public void onInvite(View v) {
		image = getBitmapFromView();
		Draw d = new Draw(getBaseContext());
		d.Oneraseclick();
	}

	public void onClearClick(View v) {
		// image = getBitmapFromView();
		Draw.checkremove = true;
		Draw d = new Draw(getBaseContext());
		d.OnClearClick();
		v2.invalidate();

	}

	public void callSpinner() {

		new StudyUpTask(this).execute(String.valueOf(StudyUpTask.GET_CLASS),
				user_id);

	}

	public void OnTypeClick(View v) {

		v2.setVisibility(View.INVISIBLE);
		et_activity_note.setVisibility(View.VISIBLE);
	}

	public void OnFreeHandClick(View v) {
		// et_activity_note.setVisibility(View.INVISIBLE);
		v2.setVisibility(View.VISIBLE);
		captureimage = true;
		// Intent intent = new Intent(getBaseContext(), Freehand.class);
		// startActivity(intent);
		// overridePendingTransition(R.anim.out, R.anim.in);

	}

	public void onMyClassesClick(View v) {
		Intent i = new Intent(AddNotes.this, MyClassesActivity.class);
		startActivity(i);

	}

	public void onLogoutClick(View v) {

		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					// Yes button clicked
					prefObj.ClearPreferences();
					finish();
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					// No button clicked
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to Log out ?")
				.setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();

	}

	private BroadcastReceiver classListReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			try {

				final Bundle extra = intent.getExtras();
				@SuppressWarnings("unchecked")
				ArrayList<ClassInfo> newClassList = (ArrayList<ClassInfo>) extra
						.getSerializable("CLassList");
				classList = newClassList;

				classData = new String[classList.size()];

				for (int i = 0; i < classList.size(); i++) {

					classData[i] = classList.get(i).getClassName();
				}
				emailView();

			} catch (Exception e) {

				Log.d("asd", "asd");
				Log.d("asd", "asd");

				Log.d("asd", "asd");
				Log.d("asd", "asd");
				Log.d("asd", "asd");

			}

		}
	};

	public void emailView() {

		if (classData != null && classData.length > 0) {
			ArrayAdapter<String> adapterNames = new ArrayAdapter<String>(this,
					R.layout.email_line, classData);
			class_data.setAdapter(adapterNames);
		}

	}

	public void initview() {

		et_activity_subject = (EditText) findViewById(R.id.et_addnote_subject);
		et_activity_topic = (EditText) findViewById(R.id.et_addnote_topic);
		et_activity_note = (EditText) findViewById(R.id.et_addnote_note);
		Btn_startRec = (Button) findViewById(R.id.btnStartRec);
		Btn_stopRec = (Button) findViewById(R.id.btnStopRec);
		Btn_startPlay = (Button) findViewById(R.id.btnStartPlaying);
		Btn_stopPlay = (Button) findViewById(R.id.btnStopPlaying);
		class_data = (AutoCompleteTextView) findViewById(R.id.et_addnote_class);
		pbar_recording = (ProgressBar) findViewById(R.id.progressBar1_recording);
		text_recording = (TextView) findViewById(R.id.tv_recording);
		btn_pause = (Button) findViewById(R.id.btn_pause);
		rec_pause = (Button) findViewById(R.id.rec_pause);

		Btn_addButton = (Button) findViewById(R.id.btn_addnote_submitbtn);

		Tv_addHeading = (TextView) findViewById(R.id.textView1);
	}

	public void initUpdateView(NoteInfo info) {

	}

	// ////////////////////////////////////////////////////////////////////////////////////////
	// This code is for Audio recording
	// ////////////////////////////////////////////////////////////////////////////////////////
	private void onRecord(boolean start) {
		if (start) {
			startRecording();
		} else {
			stopRecording();
		}
	}

	private void onPlay(boolean start) {
		if (start) {
			mStartPlaying2 = true;
			startPlaying();
		} else {
			stopPlaying();
		}
	}

	private void startPlaying() {
		if (pcheck) {
			mPlayer = new MediaPlayer();
			Btn_startRec.setVisibility(View.INVISIBLE);
			Btn_stopRec.setVisibility(View.INVISIBLE);
			btn_pause.setVisibility(View.VISIBLE);
			mPlayer.setOnCompletionListener(new OnCompletionListener() {

				public void onCompletion(MediaPlayer arg0) {
					Btn_stopPlay.setVisibility(View.INVISIBLE);
					Btn_startPlay.setVisibility(View.VISIBLE);
					btn_pause.setVisibility(View.INVISIBLE);
					mPlayer.stop();
					pcheck = true;
					// check_playing = false;
					// Btn_stopRec.setVisibility(View.VISIBLE);
					Btn_startRec.setVisibility(View.VISIBLE);

				}
			});

			new play().execute();

		} else {
			btn_pause.setVisibility(View.VISIBLE);
			mPlayer.start();

		}

	}

	private void stopPlaying() {
		Btn_startRec.setVisibility(View.VISIBLE);
		pcheck = true;
		mPlayer.release();
		btn_pause.setVisibility(View.INVISIBLE);
		mPlayer = null;
		Btn_startPlay.setVisibility(View.VISIBLE);
	}

	private void startRecording() {

		record = true;
		Read_count++;
		mRecMicToMp3_one = new RecMicToMp3(
				Environment.getExternalStorageDirectory() + "/one" + 0
						+ Read_count + ".mp3", 8000);
		// Btn_startPlay.setVisibility(View.INVISIBLE);
		pbar_recording.setVisibility(View.VISIBLE);
		text_recording.setVisibility(View.VISIBLE);
		rec_pause.setVisibility(View.VISIBLE);

		mRecMicToMp3_one.start();

	}

	public void onrecpauseclick(View v) throws IOException {
		mRecMicToMp3_one.stop();
		rec_pause.setVisibility(View.INVISIBLE);
		pbar_recording.setVisibility(View.INVISIBLE);
		text_recording.setVisibility(View.INVISIBLE);
		Btn_startRec.setVisibility(View.VISIBLE);
	}

	private void testpause() throws IOException {
		pbar_recording.setVisibility(View.INVISIBLE);
		text_recording.setVisibility(View.INVISIBLE);
		if (audioChecker) {

			Btn_startPlay.setVisibility(View.VISIBLE);
			pbar_recording.setVisibility(View.INVISIBLE);
			text_recording.setVisibility(View.INVISIBLE);
			audioChecker = true;
			mRecorder = null;
			// // This code is to convert the byte array

		} else {
			audioChecker = true;
		}
		Btn_stopRec.setVisibility(View.INVISIBLE);
		Btn_startRec.setVisibility(View.VISIBLE);

	}

	public void merg() {

		try {

			FileInputStream fistream2;
			FileInputStream fistream1;
			boolean c = false;
			FileOutputStream fostream;

			int divid = Read_count / 2;
			if (Read_count % 2 != 0) {
				mFileName = Environment.getExternalStorageDirectory()
						.getAbsolutePath();
				mFileName += "/one" + 0 + (Read_count + 1) + ".mp3";
				fostream = new FileOutputStream(mFileName);
				divid = (Read_count + 1) / 2;
				Read_count++;
				c = true;
			}

			for (int s = 0; s < divid; s++) {

				if (!c) {
					if (Read_count % 2 != 0) {
						mFileName = Environment.getExternalStorageDirectory()
								.getAbsolutePath();
						mFileName += "/one" + s + (Read_count + 1) + ".mp3";
						fostream = new FileOutputStream(mFileName);
						Read_count++;
					} else {
						Read_count++;
					}
				}
				fnmae1++;
				finalfile = divid;
				int finaltname = 1;
				for (int count = 1; count < Read_count; count++) {
					count_namer++;
					fname2++;
					mFileName = Environment.getExternalStorageDirectory()
							.getAbsolutePath();
					mFileName += "/one" + s + count + ".mp3";
					count++;
					mFileName2 = Environment.getExternalStorageDirectory()
							.getAbsolutePath();
					mFileName2 += "/one" + s + count + ".mp3";
					mFileName3 = Environment.getExternalStorageDirectory()
							.getAbsolutePath();
					mFileName3 += "/one" + (s + 1) + finaltname + ".mp3";
					finaltname++;

					fistream1 = new FileInputStream(mFileName);
					fistream2 = new FileInputStream(mFileName2);
					fostream = new FileOutputStream(mFileName3);

					SequenceInputStream sistream = new SequenceInputStream(
							fistream1, fistream2);

					int temp;

					while ((temp = sistream.read()) != -1) {
						// System.out.print( (char) temp ); // to print at DOS
						// prompt
						fostream.write(temp); // to write to file
					}
					fostream.close();
					sistream.close();
					fistream1.close();
					fistream2.close();
					c = false;
				}
				Read_count = Read_count / 2;
			}

		} catch (Exception e) {

		}

	}

	private void stopRecording() {
		mRecMicToMp3_one.stop();
		pbar_recording.setVisibility(View.INVISIBLE);
		text_recording.setVisibility(View.INVISIBLE);
		rec_pause.setVisibility(View.INVISIBLE);
		Btn_startPlay.setVisibility(View.VISIBLE);

	}

	@SuppressWarnings("unused")
	private void CreateSoundFile(Bitmap selectedImage2) {
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		byteArray = bos;
		// you can create a new file name "test.jpg" in sdcard folder.

		tempFile = new File(mFileName);
		try {
			tempFile.createNewFile();
			@SuppressWarnings("resource")
			FileOutputStream fo = new FileOutputStream(tempFile);
			fo.write(byteArray.toByteArray());
			tempFile.getPath();
		} catch (IOException e) {

			e.printStackTrace();
		}
		// write the bytes in file
	}

	class PlayButton extends Button {

		boolean mStartPlaying = true;

		OnClickListener clicker = new OnClickListener() {
			public void onClick(View v) {
				onPlay(mStartPlaying);
				if (mStartPlaying) {
					setText("Stop playing");
				} else {
					setText("Start playing");
				}
				mStartPlaying = !mStartPlaying;
			}
		};

		public PlayButton(Context ctx) {
			super(ctx);
			setText("Start playing");
			setOnClickListener(clicker);
		}
	}

	public byte[] inputStreamToByteArray(InputStream inStream)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int bytesRead;
		while ((bytesRead = inStream.read(buffer)) > 0) {
			baos.write(buffer, 0, bytesRead);
		}
		return baos.toByteArray();
	}

	public void onStartRec(View v) {
		v.setVisibility(View.INVISIBLE);
		Btn_stopRec.setVisibility(View.VISIBLE);
		startRecording();
	}

	public void onStopRec(View v) {

		v.setVisibility(View.INVISIBLE);

		Btn_startRec.setVisibility(View.VISIBLE);
		stopRecording();

	}

	public void onStartPlay(View v) {
		v.setVisibility(View.INVISIBLE);
		Btn_stopPlay.setVisibility(View.VISIBLE);
		startPlaying();

	}

	public void onStopPlay(View v) {
		v.setVisibility(View.INVISIBLE);
		Btn_startPlay.setVisibility(View.VISIBLE);
		stopPlaying();
	}

	// //////////////////////////////////////////////////////////////////////////////////////////
	// /////////// End Audio code
	// //////////////////////////////////////////////////////////////////////////////////////////

	public void callForId() {
		new StudyUpTask(this).execute(String.valueOf(StudyUpTask.GET_ID),
				user_email.trim()); /*
									 * if (user_id != null) {
									 * 
									 * prefObj.setUserPref(PreferenceHelper.Id,
									 * user_id); }
									 */
	}

	class play extends AsyncTask<Void, Void, Void> {

		ProgressDialog d;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			d = ProgressDialog.show(AddNotes.this, "Please Wait",
					"Merging Audios...", true);
		}

		@Override
		protected Void doInBackground(Void... params) {
			// perform the required operation of fetching contents
			merg();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			d.dismiss();
			try {
				mFileNamefinal = Environment.getExternalStorageDirectory()
						.getAbsolutePath();
				mFileNamefinal += "/one" + finalfile + 1 + ".mp3";

				mPlayer.setDataSource(mFileNamefinal);
				mPlayer.prepare();
				mPlayer.start();
				Read_count = 0;

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	class eval extends AsyncTask<Void, Void, Void> {

		ProgressDialog d;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			d = ProgressDialog.show(AddNotes.this, "Please Wait",
					"Merging Audios...", true);
		}

		@Override
		protected Void doInBackground(Void... params) {
			// perform the required operation of fetching contents
			merg();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			d.dismiss();
			showDialog(UPLOADING_PROCESS);

		}
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public void onAddNotesClick(View v) {
		if (!isOnline()) {
			Toast.makeText(AddNotes.this, "Error Network Connection",
					Toast.LENGTH_SHORT).show();
			return;
		}

		image = getBitmapFromView();

		subject = et_activity_subject.getText().toString();
		topic = et_activity_topic.getText().toString();
		note = et_activity_note.getText().toString();
		user_idc = prefObj.getPref(PreferenceHelper.Id, "");
		// class_id = "2";
		noteImage = "";
		noteAudio = "";

		if (subject.length() == 0) {
			et_activity_subject.setError(getString(R.string.app_name));
		} else if (topic.length() == 0) {
			Toast.makeText(getBaseContext(), "Please enter Topic",
					Toast.LENGTH_LONG).show();
		} else {

			if (btmap_freehand != null) {

				// ///// btmap_freehand =
				// UIUtils.ResizeBitmap(btmap_freehand);
				Log.d("asd", "dsasd");
				CreateImageFile(btmap_freehand);

			}

			if (record) {
				new eval().execute();
			} else {
				showDialog(UPLOADING_PROCESS);
			}
		}

		// now call the required Activity

	}

	public void onUpdateNotesClick(View v) {
		subject = et_activity_subject.getText().toString();
		topic = et_activity_topic.getText().toString();
		note = et_activity_note.getText().toString();
		user_idc = prefObj.getPref(PreferenceHelper.Id, "");
		// class_id = "2";
		noteImage = "";
		noteAudio = "";

		if (subject.length() == 0) {
			Toast.makeText(getBaseContext(), "Please enter Subject",
					Toast.LENGTH_LONG).show();
		} else if (topic.length() == 0) {
			Toast.makeText(getBaseContext(), "Please enter Topic",
					Toast.LENGTH_LONG).show();
		} else {

			showDialog(UPDATING_NOTE_PROCESS);
		}
	}

	protected Dialog onCreateDialog(int id) {

		switch (id) {

		case UPLOADING_PROCESS:
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage("Uploading Note ..");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setCancelable(true);
			mProgressDialog.show();
			return mProgressDialog;

		case UPDATING_NOTE_PROCESS:

			mProgressDialog1 = new ProgressDialog(this);
			mProgressDialog1.setMessage("Uploading Note ..");
			mProgressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog1.setCancelable(true);
			mProgressDialog1.show();
			return mProgressDialog1;

		case ERROR_DIALOG:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Your Note was Not Added Try Again :-( ")
					.setTitle("Sorry !")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									removeDialog(ERROR_DIALOG);
								}
							});
			return builder.create();

		case SUCCESS_DIALOG:
			AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
			builder1.setMessage("Your note was Successfully Added :-)")
					.setCancelable(false)
					.setTitle("Congratulations !")
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									removeDialog(SUCCESS_DIALOG);

									RemoveData();
								}
							});

			return builder1.create();

		default:
			return null;
		}

	}

	;

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		// TODO Auto-generated method stub
		super.onPrepareDialog(id, dialog);

		switch (id) {

		case UPLOADING_PROCESS:
			new UploadAudioNote().execute(String
					.valueOf(UploadAudioNote.ADD_NOTE));
			break;

		case UPDATING_NOTE_PROCESS:
			new UploadAudioNote().execute(String
					.valueOf(UploadAudioNote.UPDATE_NOTE));

			break;
		}
	}

	public void RemoveData() {
		et_activity_note.setText("");
		et_activity_subject.setText("");
		et_activity_topic.setText("");
		class_data.setText("");
		v2.setVisibility(View.INVISIBLE);
		// classdata.setText("");

		et_activity_note.setVisibility(View.VISIBLE);

	}

	public void sendAudioImage() {
		try {
			CreateAudioFile();
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(Utils.SERVER);
			MultipartEntity reqEntity = new MultipartEntity();
			if (tempFile != null && check_Byte != null) {
				ContentBody cb = new FileBody(tempFile);
				reqEntity.addPart("AudioFile", cb);
			}
			// Adding parameter
			reqEntity.addPart("action", new StringBody("addNotes"));
			reqEntity.addPart("user_id", new StringBody(user_idc));
			reqEntity.addPart("class_id", new StringBody(class_id));
			reqEntity.addPart("topic", new StringBody(topic));
			reqEntity.addPart("subject", new StringBody(subject));
			reqEntity.addPart("notes_text", new StringBody(note));

			postRequest.setEntity(reqEntity);
			HttpResponse response = httpClient.execute(postRequest);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			String sResponse;
			StringBuilder s = new StringBuilder();

			while ((sResponse = reader.readLine()) != null) {
				s = s.append(sResponse);
			}
			String st = s.toString();
			if (st.contains("Successfully Added")) {

				check_NoteAdded = true;

			}

			Log.d("asd", "dsa");
			if (tempFile != null) {
				// tempFile.delete();
			}
		} catch (Exception ex) {

			Log.d("as", ex.toString());

		}
	}

	private void CreateAudioFile() {
		mFileNamefinal = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		mFileNamefinal += "/one" + finalfile + 1 + ".mp3";
		tempFile = new File(mFileNamefinal);
		try {
			tempFile.createNewFile();
			// FileOutputStream fo = new FileOutputStream(tempFile);
			// fo.write(check_Byte);
			tempFile.getPath();
		} catch (Exception e) {

			e.printStackTrace();
		}
		// write the bytes in file
	}

	public void onReviewNotesClick(View v) {

		Intent intent = new Intent(getBaseContext(), ReviewNotes.class);
		startActivity(intent);

	}

	public void onMyFeedsClick(View v) {

		Intent intent = new Intent(getBaseContext(), MyFeeds.class);
		startActivity(intent);
	}

	public Bitmap getBitmapFromView() {
		View view = findViewById(R.id.view1);
		Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(),
				view.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(returnedBitmap);
		Drawable bgDrawable = view.getBackground();
		if (bgDrawable != null)
			bgDrawable.draw(canvas);
		else
			canvas.drawColor(Color.WHITE);
		view.draw(canvas);
		// updtae = returnedBitmap;
		return returnedBitmap;
	}

	class UploadAudioNote extends AsyncTask<String, String, String> {

		public static final int ADD_NOTE = 1;
		public static final int UPDATE_NOTE = 2;

		private int ServiceAction;

		public UploadAudioNote() {
			// TODO Auto-generated constructor stub

		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			ServiceAction = Integer.parseInt(params[0]);

			if (ServiceAction == ADD_NOTE) {
				try {

					if (record) {
						CreateAudioFile();

					} else {
						tempFile = null;
					}
					// Draw d = new Draw(getBaseContext());

					// Bitmap image2 = d.updtae;
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost postRequest = new HttpPost(Utils.SERVER);
					MultipartEntity reqEntity = new MultipartEntity();
					if (tempFile != null || check_Byte != null) {
						ContentBody cb = new FileBody(tempFile);
						reqEntity.addPart("AudioFile", cb);
					}
					if (captureimage) {

						CreateImageFile(image);
						captureimage = false;
					}

					if (tempFile_image != null) {
						ContentBody cb = new FileBody(tempFile_image);
						reqEntity.addPart("NOTESDATA", cb);
					}
					// Adding parameter
					reqEntity.addPart("action", new StringBody("addNotes"));
					reqEntity.addPart("user_id", new StringBody(user_idc));
					String clasname = class_data.getText().toString();
					reqEntity.addPart("class_id", new StringBody(clasname));
					reqEntity.addPart("topic", new StringBody(topic));
					reqEntity.addPart("subject", new StringBody(subject));
					reqEntity.addPart("notes_text", new StringBody(note));
					reqEntity.addPart("source", new StringBody("indriod"));
					postRequest.setEntity(reqEntity);
					HttpResponse response = httpClient.execute(postRequest);
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(response.getEntity()
									.getContent(), "UTF-8"));
					String sResponse;
					StringBuilder s = new StringBuilder();

					while ((sResponse = reader.readLine()) != null) {
						s = s.append(sResponse);
					}
					String st = s.toString();
					if (st.contains("Successfully Added")) {
						check_NoteAdded = true;
					}
					if (tempFile != null) {
						// tempFile.delete();
					}
					if (tempFile_image != null) {
						// tempFile_image.delete();
						// tempFile.delete();
					}
				} catch (Exception ex) {
					Log.d("as", ex.toString());
				}

			}
			if (ServiceAction == UPDATE_NOTE) {

				try {
					CreateAudioFile();
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost postRequest = new HttpPost(Utils.SERVER);
					MultipartEntity reqEntity = new MultipartEntity();
					if (tempFile != null) {
						ContentBody cb = new FileBody(tempFile);
						reqEntity.addPart("AudioFile", cb);
					}

					// Adding parameter
					reqEntity.addPart("action", new StringBody("reviewNotes"));
					reqEntity.addPart("user_id", new StringBody(user_idc));
					reqEntity.addPart("class_id", new StringBody(class_id));
					reqEntity.addPart("notes_id", new StringBody(note_id));
					reqEntity.addPart("topic", new StringBody(topic));
					reqEntity.addPart("subject", new StringBody(subject));
					reqEntity.addPart("notes_text", new StringBody(note));
					postRequest.setEntity(reqEntity);
					HttpResponse response = httpClient.execute(postRequest);
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(response.getEntity()
									.getContent(), "UTF-8"));
					String sResponse;
					StringBuilder s = new StringBuilder();

					while ((sResponse = reader.readLine()) != null) {
						s = s.append(sResponse);
					}
					String st = s.toString();
					if (st.contains("Successfully Reviewed")) {
						check_NoteUpdate = true;
					}

					if (tempFile != null) {
						// tempFile.delete();
					}

				} catch (Exception ex) {

					Log.d("asd", "dsa");
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (ServiceAction == ADD_NOTE) {
				if (check_NoteAdded == true) {
					String F_name;
					for (int s = 0; s < fnmae1 + 1; s++) {
						for (int k = 1; k < fname2 + 1; k++) {
							if (record) {
								F_name = Environment
										.getExternalStorageDirectory()
										.getAbsolutePath();
								F_name += "/one" + s + k + ".mp3";
								File file = new File(F_name);
								file.delete();
								Btn_startPlay.setVisibility(View.INVISIBLE);
							}
						}
					}
					removeDialog(UPLOADING_PROCESS);
					record = false;
					Read_count = 0;
					showDialog(SUCCESS_DIALOG);

				} else {
					removeDialog(UPLOADING_PROCESS);
					showDialog(ERROR_DIALOG);

				}
			}
			if (ServiceAction == UPDATE_NOTE) {
				if (check_NoteUpdate == true) {
					removeDialog(UPDATING_NOTE_PROCESS);
					showDialog(SUCCESS_DIALOG);

				} else {
					removeDialog(UPDATING_NOTE_PROCESS);
					showDialog(ERROR_DIALOG);

				}
			}

		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		String F_name;
		for (int s = 0; s < 25; s++) {
			for (int k = 1; k < 25 + 1; k++) {

				F_name = Environment.getExternalStorageDirectory()
						.getAbsolutePath();
				F_name += "/one" + s + k + ".mp3";
				File file = new File(F_name);
				if (file.exists()) {
					file.delete();
				}

			}
		}
		if (mRecMicToMp3_one.isRecording()) {
			mRecMicToMp3_one.stop();
		}
		if (mStartPlaying2) {
			mPlayer.stop();
		}

		super.onBackPressed();
	}

}
