package com.cyberdesignz.studyup.io;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

import com.cyberdesignz.studyup.Register;
import com.cyberdesignz.studyup.Signin;
import com.cyberdesignz.studyup.info.UserInfo;
import com.cyberdesignz.studyup.utils.JsonParser;
import com.cyberdesignz.studyup.utils.Utils;

public class ServiceThreadHandler extends Thread {

	private String TAG = "Service Handler";

	private Signin loginActivity;
	private Register registerActivity;

	private int ServiceAction;
	JsonParser jsonParser;
	public static final int GET_SIGNUP = 0;
	public static final int GET_SIGNIN = 1;

	private String _Name;
	private String _Email;
	private String _UserName;
	private String _Password;
	private String _gender;
	private String _dateOfBirth;
	private String _city;
	private String _state;
	private String _coutry;
	private String _PhoneNumber;
	private String _image;

	private UserInfo objUserInfo;
	private List<UserInfo> userInfo = new ArrayList<UserInfo>();

	private boolean isJArray = false;
	private boolean isPost = false;
	public static boolean alreadyrunning = false;

	public static String SERVER = Utils.SERVER;
	public static final String user_login = SERVER + "?action=login";
	public static final String user_register = SERVER + "?action=signup";

	public ServiceThreadHandler(Activity activity, int action, String email,
			String password) {
		ServiceAction = action;

		if (activity.getComponentName().getClassName().contains("Signin")) {
			this.loginActivity = (Signin) activity;

			_Password = password;
			_Email = email;
		}

	}

	public ServiceThreadHandler(Activity activity, int action, String strName,
			String strEmail, String strUserName, String strPassword,
			String strgender, String strDateOfBirth, String strCity,
			String strState, String strCountry, String strPhoneNumber,
			String image) {

		ServiceAction = action;
		if (activity.getComponentName().getClassName().contains("Register")) {

			this.registerActivity = (Register) activity;
			_Name = strName;
			_Email = strEmail;
			_UserName = strUserName;
			_Password = strPassword;
			_gender = strgender;
			_dateOfBirth = strDateOfBirth;
			_city = strCity;
			_state = strState;
			_coutry = strCountry;
			_PhoneNumber = strPhoneNumber;
			_image = image;

		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (alreadyrunning) {
			return;
		}

		alreadyrunning = true;
		String FeedUrl = "";

		if (ServiceAction == GET_SIGNIN) {
			isJArray = true;
			FeedUrl = user_login;
			FeedUrl = FeedUrl + "&email=" + _Email.trim() + "&password="
					+ _Password.trim();

		} else if (ServiceAction == GET_SIGNUP) {
			isJArray = true;
			isPost = true;
			FeedUrl = user_register;
			FeedUrl = FeedUrl + "&email=" + _Email + "&name=" + _Name
					+ "&user_name=" + _UserName + "&password=" + _Password
					+ "&gender=" + _gender + "&birthday=" + _dateOfBirth
					+ "&city=" + _city + "&state=" + _state + "&country="
					+ _coutry + "&phone=" + _PhoneNumber + "&status=" + "1"
					+ "&image=" + _image;
		}

		if (isJArray) {
			jsonParser = new JsonParser();
			if (isPost)
				HandleResponseArray(jsonParser.GetJasonObject(FeedUrl, true));
			else
				HandleResponseArray(jsonParser.GetJasonObject(FeedUrl, false));
		} else {
			jsonParser = new JsonParser();
			HandleResponse(jsonParser.GetJasonObject(FeedUrl, false));
		}

		alreadyrunning = false;

	}

	public void HandleResponse(JSONObject objJSON) {
		if (objJSON != null) { /*
								 * if(ServiceAction == GET_SIGNUP) {
								 * SignupUser(objJSON); }
								 */
			if (ServiceAction == GET_SIGNUP) {
				SignupUser(objJSON);
			}

		}
	}

	private void HandleResponseArray(JSONObject jObj) {

		if (jObj == null) {
			Log.d(TAG, "JSON object is null");
			return;
		}

		if (ServiceAction == GET_SIGNIN) {
			SigninUser(jObj);
		} else if (ServiceAction == GET_SIGNUP) {
			SignupUser(jObj);
		}

	}

	private void SignupUser(JSONObject objJSON) {
		// TODO Auto-generated method stub
		isJArray = true;

		try {
			JSONArray posts = objJSON.getJSONArray("Response");
			for (int i = 0; i < posts.length(); i++) {
				JSONObject objJson = new JSONObject();
				objJson = posts.getJSONObject(i);
				if (objJson != null)
					LoadUserInfo(objJson);

				// send a message back to signin activity

			}
			registerActivity.RegisterUser(loginActivity.SUCCESS);

		} catch (Exception exp) {

			Log.d(TAG, exp.getMessage());

		}

	}

	public void SigninUser(JSONObject objJSON) {

		try {
			JSONArray posts = objJSON.getJSONArray("Response");
			for (int i = 0; i < posts.length(); i++) {

				JSONObject objJson = new JSONObject();
				objJson = posts.getJSONObject(i);
				if (objJson != null)
					LoadUserInfo(objJson);
				// send a message back to signin activity
			}

		} catch (Exception exp) {

			Log.d(TAG, exp.getMessage());

		}

	}

	private void LoadUserInfo(JSONObject objJSON) {
		try {
			if (objJSON.has("email")) {
				objUserInfo = new UserInfo();
				objUserInfo.setUserId(objJSON.getString("id"));
				objUserInfo.setName(objJSON.getString("name"));
				objUserInfo.setUserName(objJSON.getString("username"));
				objUserInfo.setEmail(objJSON.getString("email"));
				objUserInfo.setPassword(objJSON.getString("password"));
				objUserInfo.setGender(objJSON.getString("gender"));
				objUserInfo.setDate_of_birth(objJSON.getString("birthday"));
				objUserInfo.setCity(objJSON.getString("city"));
				objUserInfo.setState(objJSON.getString("state"));
				objUserInfo.setCountry(objJSON.getString("country"));
				objUserInfo.setPhoneNo(objJSON.getString("phone"));

				if (isJArray)
					userInfo.add(objUserInfo);
				loginActivity.ValidatedUser(true, loginActivity.SUCCESS,
						objUserInfo);
			} else {

				loginActivity.ValidatedUser(true, loginActivity.ERROR,
						objUserInfo);

			}

		} catch (Exception exp) {
			Log.d(TAG,
					"Exception in method: ServicesHandlerThread.LoadUserInfo()");
		}
	}

}
