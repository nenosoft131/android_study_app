package com.cyberdesignz.studyup;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.io.ServiceThreadHandler;
import com.cyberdesignz.studyup.utils.UIUtils;
import com.cyberdesignz.studyup.utils.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity {

	private static final int PICTURE_CAPTURE_CAMERA = 1;
	private static final int PICTURE_SELECT_GALLERY = 2;

	ServiceThreadHandler servicesThreadHandler;
	private Bitmap selectedPic = null;
	static final int ID_DATEPICKER = 3;
	static final int TIME_DIALOG_ID = 4;
	static final int SUCCESS_DIALOG = 5;
	private int myYear, myMonth, myDay;
	private File tempFile = null;
	boolean checkExist = false;
	private ProgressDialog signupDialog;
	String imageEncoded;
	private PreferenceHelper prefObj;

	public static String SUCCESS = "success";
	public static String ERROR = "error";
	public static String EXIST = "exist";

	private static final int SIGNUP_DIALOG = 0;
	private static final int ERROR_DIALOG = 1;
	private static final int EXIST_DIALOG = 2;

	ImageView iv_register_activity_picture;
	TextView et_register_activity_name;
	TextView et_register_activity_email;
	TextView et_register_activity_userName;
	TextView et_register_activity_password;
	Button btn_register_activity_dateOfBirth;
	TextView et_register_activity_city;
	TextView et_register_activity_state;
	TextView et_register_activity_country;
	//TextView et_register_activity_phoneNumber;
	MultipartEntity reqEntity;

	String email;
	String name;
	String userName;
	String password;
	String gender;
	String birthDate = null;
	String city;
	String state;
	String country;
	String id;
	//String phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.register);

		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		prefObj = new PreferenceHelper(this, PreferenceHelper.CurrentUser);
		initView();

	}

	public void initView() {

		iv_register_activity_picture = (ImageView) findViewById(R.id.iv_register_picture);
		et_register_activity_name = (TextView) findViewById(R.id.et_register_name);
		et_register_activity_email = (TextView) findViewById(R.id.et_register_email);
		et_register_activity_userName = (TextView) findViewById(R.id.et_register_user_name);
		et_register_activity_password = (TextView) findViewById(R.id.et_register_password);
		btn_register_activity_dateOfBirth = (Button) findViewById(R.id.btn_register_date_of_birth);
		btn_register_activity_dateOfBirth
				.setOnClickListener(datePickerButtonOnClickListener);
		et_register_activity_city = (TextView) findViewById(R.id.et_register_city);
		et_register_activity_state = (TextView) findViewById(R.id.et_register_state);
		et_register_activity_country = (TextView) findViewById(R.id.et_register_country);
		//et_register_activity_phoneNumber = (TextView) findViewById(R.id.et_register_phone_number);

	}

	private Button.OnClickListener datePickerButtonOnClickListener = new Button.OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			final Calendar c = Calendar.getInstance();
			myYear = c.get(Calendar.YEAR);
			myMonth = c.get(Calendar.MONTH);
			myDay = c.get(Calendar.DAY_OF_MONTH);
			showDialog(ID_DATEPICKER);
		}
	};

	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case ID_DATEPICKER:

			return new DatePickerDialog(this, myDateSetListener, myYear,
					myMonth, myDay);

		case SIGNUP_DIALOG:

			signupDialog = new ProgressDialog(this);
			signupDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			signupDialog.setMessage("Siging up .....");
			signupDialog.setIndeterminate(true);
			signupDialog.setCancelable(true);
			return signupDialog;

		case ERROR_DIALOG:

			AlertDialog.Builder b1 = new Builder(this);
			b1.setTitle("Soory");
			b1.setMessage("Error in login");
			b1.setCancelable(false);
			b1.setPositiveButton("OK", new OnClickListener() {

				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					removeDialog(ERROR_DIALOG);
					Login();

				}
			});
			return b1.create();

		case EXIST_DIALOG:
			AlertDialog.Builder b2 = new Builder(this);
			b2.setTitle("E-mail already exist!");
			b2.setMessage("Email already exists Give another Userid.");
			b2.setCancelable(true);
			b2.setPositiveButton("OK", new OnClickListener() {

				public void onClick(DialogInterface arg0, int arg1) {

					removeDialog(EXIST_DIALOG);

				}
			});
			return b2.create();

		case SUCCESS_DIALOG:
			AlertDialog.Builder b3 = new Builder(this);
			b3.setTitle("Congratulations !");
			b3.setMessage("Successfully login :-)");
			b3.setCancelable(true);
			b3.setPositiveButton("Ok", new OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					removeDialog(SUCCESS_DIALOG);
					AfterRegister();
				}
			});
			return b3.create();

		default:
			return null;
		}

	}

	;

	public void AfterRegister() {

		Intent intent = new Intent(Register.this, AddNotes.class);
		intent.putExtra("activity", "register");
		intent.putExtra("email", et_register_activity_email.getText()
				.toString());
		startActivity(intent);
		finish();

	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		// TODO Auto-generated method stub
		super.onPrepareDialog(id, dialog);

		switch (id) {

		case SIGNUP_DIALOG:

			email = et_register_activity_email.getText().toString();
			name = et_register_activity_name.getText().toString();
			userName = et_register_activity_userName.getText().toString();
			password = et_register_activity_password.getText().toString();

			gender = "male";
			city = et_register_activity_city.getText().toString();
			state = et_register_activity_state.getText().toString();
			country = et_register_activity_country.getText().toString();
			//phone = et_register_activity_phoneNumber.getText().toString();

			new RegisterClass().execute();

		}
	}

	// / Send Image File
	public void sendImage() {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(Utils.SERVER);
			// File imeg = new File(imagePath);

			// ContentBody cb = new InputStreamBody(imageStream, "temp.png");
			MultipartEntity reqEntity = new MultipartEntity();
			if (tempFile != null) {
				ContentBody cb = new FileBody(tempFile);
				reqEntity.addPart("Filedata", cb);
			}
			reqEntity.addPart("action", new StringBody("signup"));
			reqEntity.addPart("email", new StringBody(email));
			reqEntity.addPart("name", new StringBody(name));
			reqEntity.addPart("user_name", new StringBody(userName));
			reqEntity.addPart("password", new StringBody(password));
			reqEntity.addPart("gender", new StringBody(gender));
			reqEntity.addPart("birthday", new StringBody(birthDate));
			reqEntity.addPart("city", new StringBody(city));
			reqEntity.addPart("state", new StringBody(state));
			reqEntity.addPart("country", new StringBody(country));
			//reqEntity.addPart("phone", new StringBody(phone));
			reqEntity.addPart("source", new StringBody("indriod"));
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
			checkExist = st
					.contains("Email already exists Username already exists");
			Log.d("asd", "dsa");
			if (tempFile != null) {
				tempFile.delete();

			}

		}
		// if(d) Log.i(E, "Send response:\n" + s); }
		catch (Exception e) { //
			Log.e("here", "Error while sending: " + e.toString()); // return
			// ERROR;
		}
	}

	private void CreateImageFile(Bitmap selectedImage2) {
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		selectedImage2.compress(CompressFormat.JPEG, 100, bos);
		byteArray = bos;
		// you can create a new file name "test.jpg" in sdcard folder.
		tempFile = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "temp.jpg");
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

	public void RegisterUser(String statusCode) {

		Message myMessage = new Message();
		myMessage.obj = statusCode;
		mHandler.sendMessage(myMessage);

	}

	public void Login() {

		Intent intent = new Intent(Register.this, AddNotes.class);
		startActivity(intent);
		finish();

	}

	public void LoginScreen() {

		Intent intent = new Intent(Register.this, Signin.class);
		startActivity(intent);
		finish();

	}

	public final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			String loginmsg = (String) msg.obj;
			if (loginmsg.equalsIgnoreCase(SUCCESS)) {
				removeDialog(SIGNUP_DIALOG);
				showDialog(SUCCESS_DIALOG);
				// LoadPref(userID);
			} else if (loginmsg.equalsIgnoreCase(ERROR)) {
				removeDialog(SIGNUP_DIALOG);
				showDialog(ERROR_DIALOG);
			} else if (loginmsg.equalsIgnoreCase(EXIST)) {
				removeDialog(SIGNUP_DIALOG);
				showDialog(EXIST_DIALOG);
			}
		}

	};

	public void onRegisterclick(View v) {
		email = et_register_activity_email.getText().toString();
		name = et_register_activity_name.getText().toString();
		userName = et_register_activity_userName.getText().toString();
		password = et_register_activity_password.getText().toString();
		gender = "male";
		city = et_register_activity_city.getText().toString();
		state = et_register_activity_state.getText().toString();
		country = et_register_activity_country.getText().toString();
		//phone = et_register_activity_phoneNumber.getText().toString();
		if (email.length() == 0) {
			Toast.makeText(getBaseContext(), "Please enter email",
					Toast.LENGTH_LONG).show();
		} else if (Utils.isEmailValid(email) == false) {

			Toast.makeText(getBaseContext(),
					"Please enter a valid email address", Toast.LENGTH_LONG)
					.show();

		} else if (birthDate == null) {

			Toast.makeText(getBaseContext(),
					"Please Select your date of Birth", Toast.LENGTH_LONG)
					.show();
		} else if (userName.length() == 0) {
			Toast.makeText(getBaseContext(), "Please enter User Name",
					Toast.LENGTH_LONG).show();
		} else if (password.length() == 0) {
			Toast.makeText(getBaseContext(), "Please enter Password",
					Toast.LENGTH_LONG).show();
		} else {

			if (selectedPic != null) {
				selectedPic = UIUtils.ResizeBitmap(selectedPic);
				CreateImageFile(selectedPic);
			}
			showDialog(SIGNUP_DIALOG);
		}

	}

	public void afterLogin() {

		Intent intent = new Intent(this, AddNotes.class);
		startActivity(intent);
		finish();

	}

	public void LoadPref() {

		prefObj.setUserPref(PreferenceHelper.Id, id);
		prefObj.setUserPref(PreferenceHelper.Email, email);
		prefObj.setUserPref(PreferenceHelper.Name, name);
		prefObj.setUserPref(PreferenceHelper.UserName, userName);
		prefObj.setUserPref(PreferenceHelper.Password, password);
		prefObj.setUserPref(PreferenceHelper.Gender, gender);
		prefObj.setUserPref(PreferenceHelper.DateOfBirth, birthDate);
		prefObj.setUserPref(PreferenceHelper.City, city);
		prefObj.setUserPref(PreferenceHelper.State, state);
		prefObj.setUserPref(PreferenceHelper.Country, country);
		// prefObj.setUserPref(PreferenceHelper.P,name);

	}

	private DatePickerDialog.OnDateSetListener myDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			birthDate = String.valueOf(year) + "-"
					+ String.valueOf(monthOfYear + 1) + "-"
					+ String.valueOf(dayOfMonth);

			btn_register_activity_dateOfBirth.setText(birthDate);

		}
	};

	public void onPictureClick(View v) {

		v.setEnabled(false);
		final CharSequence[] items = { "Take a picture", "Select a picture",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Add Picture");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				if (item == 0) {
					Intent intent = new Intent(
							android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(intent, PICTURE_CAPTURE_CAMERA);
				} else if (item == 1) {
					Intent i = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(i, PICTURE_SELECT_GALLERY);
				}

			}
		});

		AlertDialog alert = builder.create();
		alert.show();
		v.setEnabled(true);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);

		switch (requestCode) {

		case PICTURE_CAPTURE_CAMERA:
			if (resultCode == RESULT_OK) {

				selectedPic = (Bitmap) intent.getExtras().get("data");
				iv_register_activity_picture.setImageBitmap(selectedPic);
			}
			break;

		case PICTURE_SELECT_GALLERY:
			if (resultCode == RESULT_OK) {
				Uri selectPicUri = intent.getData();
				InputStream reader;
				try {
					reader = getContentResolver().openInputStream(selectPicUri);
					selectedPic = BitmapFactory.decodeStream(reader);
					iv_register_activity_picture.setImageBitmap(selectedPic);
				} catch (FileNotFoundException e) {

				}

			}
			break;
		}

	}

	public void onBackclick(View v) {

		finish();

	}

	class RegisterClass extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost postRequest = new HttpPost(Utils.SERVER);
				MultipartEntity reqEntity = new MultipartEntity();
				if (tempFile != null) {
					ContentBody cb = new FileBody(tempFile);
					reqEntity.addPart("Filedata", cb);
				}

				reqEntity.addPart("action", new StringBody("signup"));
				reqEntity.addPart("email", new StringBody(email));
				reqEntity.addPart("name", new StringBody(name));
				reqEntity.addPart("user_name", new StringBody(userName));
				reqEntity.addPart("password", new StringBody(password));
				reqEntity.addPart("gender", new StringBody(gender));
				reqEntity.addPart("birthday", new StringBody(birthDate));
				reqEntity.addPart("city", new StringBody(city));
				reqEntity.addPart("state", new StringBody(state));
				reqEntity.addPart("country", new StringBody(country));
				//reqEntity.addPart("phone", new StringBody(phone));
				reqEntity.addPart("source", new StringBody("indriod"));
				postRequest.setEntity(reqEntity);
				HttpResponse response = httpClient.execute(postRequest);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								response.getEntity().getContent(), "UTF-8"));
				String sResponse;
				StringBuilder s = new StringBuilder();

				while ((sResponse = reader.readLine()) != null) {
					s = s.append(sResponse);
				}
				JSONObject json;
				String st = s.toString();
				JSONObject jsonResponse = new JSONObject(st);
				JSONArray mtUsers = jsonResponse.getJSONArray("Response");
				json = mtUsers.getJSONObject(0);
				id = json.getString("id");
				checkExist = st.contains("Username already exists");
				Log.d("asd", "dsa");
				if (tempFile != null) {
					tempFile.delete();

				}

			}
			// if(d) Log.i(E, "Send response:\n" + s); }
			catch (Exception e) { //
				Log.e("here", "Error while sending: " + e.toString()); // return
				// ERROR;
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (checkExist) {
				removeDialog(SIGNUP_DIALOG);
				showDialog(EXIST_DIALOG);

			} else {
				LoadPref();
				removeDialog(SIGNUP_DIALOG);
				showDialog(SUCCESS_DIALOG);
			}

		}

	}

}
