package com.cyberdesignz.studyup;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

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
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.info.ClassInfo;
import com.cyberdesignz.studyup.info.NoteInfo;
import com.cyberdesignz.studyup.io.StudyUpTask;
import com.cyberdesignz.studyup.utils.Utils;

public class UpdateNote extends Activity {
	private static final String LOG_TAG = "AudioRecordTest";
	static final int SUCCESS_DIALOG = 1;
	static final int ERROR_DIALOG = 2;
	static final int UPLOADING_PROCESS = 3;
	ProgressBar pbar_recording;
	TextView text_recording;
	boolean check_recording = false;
	boolean check_playing = false;
	Bitmap pic;
	boolean check = false;
	static Bitmap image;
	boolean clearit, mplaying = false;
	static Bitmap btmap_freehand;
	Button erasebutton;

	static final int UPDATING_NOTE_PROCESS = 4;
	TextView tv_updateHeading;
	private File tempFile_image2 = null;

	String note_image_name;

	EditText et_activity_subject;

	String note_id;

	private String[] classData;

	String user_email;
	EditText et_activity_topic;
	EditText et_activity_note;
	byte[] check_Byte;
	String twoTimesencoded;
	private String audio_string;
	private String Audio_Server;
	private MediaPlayer mPlayer = null;
	private MediaRecorder mRecorder = null;
	public static String user_id;
	private static String mFileName = null;
	private File tempFile = null;
	byte[] check_Byte1;

	private ProgressDialog mProgressDialog;
	private ProgressDialog mProgressDialog1;

	private ArrayList<ClassInfo> classList;
	private ArrayList<String> classNamesList;

	NoteInfo note_info;

	String subject;
	String classStr;
	String topic;
	String note;
	String user_idc;
	String class_id;
	String noteImage;
	String noteAudio;
	Button Btn_startRec;
	Button Btn_stopRec;
	Button Btn_startPlay;
	Button Btn_stopPlay;
	Button Btn_updateButton;
	ProgressBar progressBar;
	TextView progressText;
	Button btn_stop_playing;
	public static Bitmap imgBatmap;
	AutoCompleteTextView tv_Auto_class;
	boolean checkpaus = false;
	View v2;
	public static boolean checkUpate = false;

	String path;
	boolean check_NoteAdded = false; // check either note is added
	boolean check_NoteUpdate = false;

	private PreferenceHelper prefObj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.update_note);

		pbar_recording = (ProgressBar) findViewById(R.id.progressBar1_recording_update);
		text_recording = (TextView) findViewById(R.id.textView5_recording);

		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		Audio_Server = Utils.AUDIO_SERVER;
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
				audio_string = intent.getExtras().getString("audio_name");
				tv_Auto_class.setText(intent.getExtras().getString(
						("class_name")));
				note_image_name = intent.getExtras().getString("note_image");

				if (audio_string.length() != 0) {
					Audio_Server = Audio_Server + audio_string;
					path = Environment.getExternalStorageDirectory()
							.getAbsolutePath();
					new UploadAudioNote().execute(String
							.valueOf(UploadAudioNote.DOWNLOAD_AUDIO));
					Btn_startPlay.setVisibility(View.INVISIBLE);
					Btn_stopPlay.setVisibility(View.INVISIBLE);
				} else {
					progressBar.setVisibility(View.INVISIBLE);
					progressText.setVisibility(View.INVISIBLE);
					Btn_startPlay.setVisibility(View.INVISIBLE);
					Btn_stopPlay.setVisibility(View.INVISIBLE);
				}
				callSpinner();

				if (checkUpate) {
					CreateImageFile(imgBatmap);
					Log.d("das", "ad");
					checkUpate = false;

				}
				// tv_updateHeading.setVisibility(View.VISIBLE);

				// Btn_updateButton.setVisibility(View.VISIBLE);

				Log.d("asd", "das");

			} catch (Exception ex) {

				Log.d("asd", "das");
				Log.d("asd", "das");
				Log.d("asd", "das");

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

	public void onFreeHandClick(View v) {
		et_activity_note.setVisibility(View.VISIBLE);
		v2.setVisibility(View.VISIBLE);
	}

	public void OnTypeClick(View v) {

		v2.setVisibility(View.INVISIBLE);
		et_activity_note.setVisibility(View.VISIBLE);
	}

	public void OnshareClick(View v) {

		final String items[] = { "Facebook", "Archive" };

		AlertDialog.Builder ab = new AlertDialog.Builder(UpdateNote.this);
		ab.setTitle("Select Option");
		ab.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface d, int choice) {
				if (choice == 0) {
					Intent i = new Intent(UpdateNote.this,
							FacebookActivity.class);
					i.putExtra("subject", et_activity_subject.getText()
							.toString());
					i.putExtra("detail", et_activity_note.getText().toString());
					startActivity(i);

				} else if (choice == 1) {

				}
			}
		});
		ab.show();

	}

	public void OnWriteClick(View v) {
		image = getBitmapFromView();
		updateDraw d = new updateDraw(getBaseContext());
		d.onwriteclick();
	}

	public void OnEraseClick(View v) {
		image = getBitmapFromView();
		updateDraw d = new updateDraw(getBaseContext());
		d.Oneraseclick();
	}

	public void onClearClick(View v) {
		// image = getBitmapFromView();
		updateDraw.checkremove = true;
		updateDraw d = new updateDraw(getBaseContext());
		d.OnClearClick();
		v2.invalidate();

	}

	public Bitmap getBitmapFromView() {
		View view = findViewById(R.id.view2);
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
		super.onResume();

		Log.d("asd", "das");
		Log.d("asd", "das");

	}

	public void callSpinner() {

		new StudyUpTask(this).execute(String.valueOf(StudyUpTask.GET_CLASS),
				user_id);

	}

	public void emailView() {

		if (classData != null && classData.length > 0) {
			ArrayAdapter<String> adapterNames = new ArrayAdapter<String>(this,
					R.layout.email_line, classData);
			tv_Auto_class.setAdapter(adapterNames);
		}

	}

	public void onLogoutClick(View v) {

		prefObj.ClearPreferences();
		finish();

	}

	public void onCancelCick(View v) {

		finish();

	}

	private BroadcastReceiver classListReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			try {

				final Bundle extra = intent.getExtras();
				ArrayList<ClassInfo> newClassList = (ArrayList<ClassInfo>) extra
						.getSerializable("CLassList");
				classList = newClassList;

				classData = new String[classList.size()];

				for (int i = 0; i < classList.size(); i++) {

					classData[i] = classList.get(i).getClassName();
				}
				emailView();

				classNamesList = new ArrayList<String>();

				for (int i = 0; i < classList.size(); i++) {
					classNamesList.add(classList.get(i).getClassName());
				}
				ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
						getBaseContext(), android.R.layout.simple_spinner_item,
						classNamesList);
				adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			} catch (Exception e) {

				Log.d("asd", "asd");
				Log.d("asd", "asd");

				Log.d("asd", "asd");
				Log.d("asd", "asd");
				Log.d("asd", "asd");
				// Log.d(TAG, "Error while getting group list");

			}

		}
	};

	public void initview() {
		// s2 = (Spinner) findViewById(R.id.et_updatenote_class);

		updateDraw.updtae = null;
		Button ad = (Button) findViewById(R.id.btn_addnote_draw);
		ad.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				// et_activity_note.setVisibility(View.INVISIBLE);
				v2.setVisibility(View.VISIBLE);
				note_image_name = Utils.IMAGE_SERVER_NOTES + note_image_name;
				new imageloading().execute(note_image_name);

			}
		});
		v2 = findViewById(R.id.view2);
		erasebutton = (Button) findViewById(R.id.btn_erase);
		tv_Auto_class = (AutoCompleteTextView) findViewById(R.id.et_addnote_class);
		et_activity_subject = (EditText) findViewById(R.id.et_addnote_subject);
		et_activity_topic = (EditText) findViewById(R.id.et_addnote_topic);
		et_activity_note = (EditText) findViewById(R.id.et_addnote_note);
		Btn_startRec = (Button) findViewById(R.id.btnStartRec);
		Btn_stopRec = (Button) findViewById(R.id.btnStopRec);
		Btn_startPlay = (Button) findViewById(R.id.btnStartPlaying);
		Btn_stopPlay = (Button) findViewById(R.id.btnStopPlaying);
		btn_stop_playing = (Button) findViewById(R.id.btn_stopplaying);

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
			startPlaying();
		} else {
			stopPlaying();
		}
	}

	private void startPlaying() {

		check_playing = true;
		Btn_startRec.setVisibility(View.INVISIBLE);
		Btn_stopRec.setVisibility(View.INVISIBLE);
		btn_stop_playing.setVisibility(View.VISIBLE);

		mPlayer = new MediaPlayer();

		mPlayer.setOnCompletionListener(new OnCompletionListener() {

			public void onCompletion(MediaPlayer arg0) {
				Btn_stopPlay.setVisibility(View.INVISIBLE);
				Btn_startPlay.setVisibility(View.VISIBLE);
				
				// mPlayer.stop();
				check_playing = false;
				// mStartPlaying=true;
				// Btn_stopRec.setVisibility(View.VISIBLE);
				// Btn_startRec.setVisibility(View.VISIBLE);

			}
		});

		try {
			if (audio_string != null) { // audio_1353478442audiorecordtest.mp3

				playAfterDownload();

				// audio_string="";
			} else {

				Toast.makeText(this, "There is no audio for this note ",
						Toast.LENGTH_SHORT).show();

			}

		} catch (Exception e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
	}

	public void playAfterDownload() {
		try {
			// progressBar.setVisibility(View.INVISIBLE);
			// progressText.setVisibility(View.INVISIBLE);
			mPlayer.setDataSource(path + "/audio.mp3");
			mPlayer.prepare();
			mPlayer.start();

		} catch (IOException ex) {

		}
	}

	private void stopPlaying() {

		// Btn_startRec.setVisibility(View.VISIBLE);
		check_playing = false;
		mPlayer.release();
		mPlayer = null;
	}

	private void startRecording() {
		pbar_recording.setVisibility(View.VISIBLE);
		text_recording.setVisibility(View.VISIBLE);
		Btn_startPlay.setVisibility(View.INVISIBLE);
		if (check_playing) {
			Toast.makeText(this, "Please stop playing first", Toast.LENGTH_LONG)
					.show();

		} else {

			check_recording = true;
			mFileName = Environment.getExternalStorageDirectory()
					.getAbsolutePath();
			// mFileName += "/audiorecordtest.3gp";
			mFileName += "/audio.mp3";

			mRecorder = new MediaRecorder();
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mRecorder.setOutputFile(mFileName);
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			try {
				mRecorder.prepare();
			} catch (IOException e) {
				Log.e(LOG_TAG, "prepare() failed");
			}
			mRecorder.start();
		}
	}

	private void stopRecording() {
		pbar_recording.setVisibility(View.INVISIBLE);
		text_recording.setVisibility(View.INVISIBLE);
		Btn_startPlay.setVisibility(View.VISIBLE);
		check_recording = false;

		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
		// // This code is to convert the byte array
		try {
			// /////////
			InputStream in = new FileInputStream(mFileName);
			try {
				check_Byte = inputStreamToByteArray(in);
				String encodedImage = Base64.encodeToString(check_Byte,
						Base64.DEFAULT);
				check_Byte1 = encodedImage.getBytes();
				twoTimesencoded = Base64.encodeToString(check_Byte1,
						Base64.DEFAULT);
				Log.d("asds", "ds");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Btn_startPlay.setVisibility(View.VISIBLE);

	}

	private void CreateSoundFile(Bitmap selectedImage2) {
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		byteArray = bos;
		// you can create a new file name "test.jpg" in sdcard folder.

		tempFile = new File(mFileName);
		try {
			tempFile.createNewFile();
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
				// mStartPlaying = !mStartPlaying;
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

	public void onStartPlay(View v) {

		v.setVisibility(View.INVISIBLE);
		Btn_stopPlay.setVisibility(View.VISIBLE);
		Btn_stopPlay.setVisibility(View.VISIBLE);
		if (checkpaus) {
			mplaying = true;
			mPlayer.start();
		} else {
			startPlaying();
		}

	}

	public void OnstopPlaying(View v) {
		Btn_stopPlay.setVisibility(View.INVISIBLE);
		Btn_startPlay.setVisibility(View.VISIBLE);

		stopPlaying();
		btn_stop_playing.setVisibility(View.INVISIBLE);
		checkpaus = false;

	}

	public void onStopPlay(View v) {
		v.setVisibility(View.INVISIBLE);
		Btn_startPlay.setVisibility(View.VISIBLE);
		mPlayer.pause();
		checkpaus = true;

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (mplaying) {
			mPlayer.stop();

		}
		super.onBackPressed();

	}

	// //////////////////////////////////////////////////////////////////////////////////////////
	// /////////// End Audio code
	// //////////////////////////////////////////////////////////////////////////////////////////

	public void callForId() {
		new StudyUpTask(this).execute(String.valueOf(StudyUpTask.GET_ID),
				user_email.trim()); /*
									 * if (user_id != null) {
									 * prefObj.setUserPref(PreferenceHelper.Id,
									 * user_id); }
									 */
	}

	private void CreateImageFile(Bitmap selectedImage2) {
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		selectedImage2.compress(CompressFormat.JPEG, 100, bos);
		byteArray = bos;
		// you can create a new file name "test.jpg" in sdcard folder.
		tempFile_image2 = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "temp2.jpg");
		try {
			tempFile_image2.createNewFile();
			FileOutputStream fo = new FileOutputStream(tempFile_image2);
			fo.write(byteArray.toByteArray());
			tempFile_image2.getPath();
		} catch (IOException e) {

			e.printStackTrace();
		}
		// write the bytes in file
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
		} else if (note.length() == 0) {
			Toast.makeText(getBaseContext(), "Please enter Note",
					Toast.LENGTH_LONG).show();
		} else {
			if (check_recording) {
				Toast.makeText(this, "Please stop recording first",
						Toast.LENGTH_LONG).show();

			} else {
				image = getBitmapFromView();
				if (image != null) {

					// ///// btmap_freehand =
					// UIUtils.ResizeBitmap(btmap_freehand);
					Log.d("asd", "dsasd");
					CreateImageFile(image);

				}
				showDialog(UPDATING_NOTE_PROCESS);
			}
		}

	}

	protected Dialog onCreateDialog(int id) {

		switch (id) {

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
									ReviewNotes.checkResume = true;
									finish();
									// RemoveData();
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

	}

	public void sendAudioImage() {
		try {

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(Utils.SERVER);
			MultipartEntity reqEntity = new MultipartEntity();
			if (tempFile != null) {
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
				String asd = sResponse;
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

		tempFile = new File(mFileName);
		try {
			tempFile.createNewFile();
			FileOutputStream fo = new FileOutputStream(tempFile);
			fo.write(check_Byte);
			tempFile.getPath();
		} catch (Exception e) {

			e.printStackTrace();
		}
		// write the bytes in file
	}

	class UploadAudioNote extends AsyncTask<String, String, String> {

		public static final int UPDATE_NOTE = 2;
		public static final int DOWNLOAD_AUDIO = 3;

		private int ServiceAction;

		public UploadAudioNote() {
			// TODO Auto-generated constructor stub

		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			ServiceAction = Integer.parseInt(params[0]);

			if (ServiceAction == UPDATE_NOTE) {

				try {
					if (mFileName != null) {
						CreateAudioFile();
					}
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost postRequest = new HttpPost(Utils.SERVER);
					MultipartEntity reqEntity = new MultipartEntity();
					if (tempFile != null) {
						ContentBody cb = new FileBody(tempFile);
						reqEntity.addPart("AudioFile", cb);
					}

					// Adding parameter
					reqEntity.addPart("action", new StringBody("reviewNotes"));

					if (tempFile_image2 != null) {
						tempFile_image2 = new File(
								Environment.getExternalStorageDirectory()
										+ File.separator + "temp2.jpg");
						ContentBody cb2 = new FileBody(tempFile_image2);
						reqEntity.addPart("NOTESDATA", cb2);
					}

					reqEntity.addPart("user_id", new StringBody(user_idc));
					reqEntity.addPart("class_id", new StringBody(tv_Auto_class
							.getText().toString()));
					reqEntity.addPart("notes_id", new StringBody(note_id));
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
						String asd = sResponse;
						s = s.append(sResponse);
					}
					String st = s.toString();
					if (st.contains("Successfully Reviewed")) {
						check_NoteUpdate = true;
					}
					if (tempFile != null) {
						tempFile.delete();
					}
					if (tempFile_image2 != null) {
						tempFile_image2.delete();

						mFileName = null;
					}

				} catch (Exception ex) {

					Log.d("asd", "dsa");
				}
			}
			if (ServiceAction == DOWNLOAD_AUDIO) {
				int count;
				try {

					URL url = new URL(Audio_Server);
					URLConnection conexion = url.openConnection();
					conexion.connect();
					int lenghtOfFile = conexion.getContentLength();
					Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
					InputStream input = new BufferedInputStream(
							url.openStream());
					OutputStream output = new FileOutputStream(path
							+ "/audio.mp3");

					byte data[] = new byte[1024];

					long total = 0;

					while ((count = input.read(data)) != -1) {
						total += count;
						publishProgress(""
								+ (int) ((total * 100) / lenghtOfFile));
						output.write(data, 0, count);
					}

					output.flush();
					output.close();
					input.close();
				} catch (Exception e) {

				}

			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (ServiceAction == UPDATE_NOTE) {
				if (check_NoteUpdate == true) {
					removeDialog(UPDATING_NOTE_PROCESS);
					showDialog(SUCCESS_DIALOG);

				} else {
					removeDialog(UPDATING_NOTE_PROCESS);
					showDialog(ERROR_DIALOG);

				}
			}

			if (ServiceAction == DOWNLOAD_AUDIO) {
				Btn_startPlay.setVisibility(View.VISIBLE);
				// Btn_stopPlay.setVisibility(View.VISIBLE);
				// progressText.setVisibility(View.INVISIBLE);
				// progressBar.setVisibility(View.INVISIBLE);

			}

		}

	}

	public class imageloading extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			pic = loadBitmap(params[0]);

			return null;
		}

		public Bitmap loadBitmap(String url) {
			Bitmap bm = null;
			InputStream is = null;
			BufferedInputStream bis = null;
			try {
				URLConnection conn = new URL(url).openConnection();
				conn.connect();
				is = conn.getInputStream();
				bis = new BufferedInputStream(is, 8192);
				bm = BitmapFactory.decodeStream(bis);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return bm;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub

			super.onPostExecute(result);
			if (pic == null) {
				check = false;
			} else {
				check = true;
				updateDraw d = new updateDraw(getBaseContext());
				d.updtae = pic;
				v2 = findViewById(R.id.view2);
				v2.setVisibility(View.VISIBLE);
				v2.invalidate();
			}
			// again();

		}
	}
}