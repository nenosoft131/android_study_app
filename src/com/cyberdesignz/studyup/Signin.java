package com.cyberdesignz.studyup;

import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.info.UserInfo;
import com.cyberdesignz.studyup.io.ServiceThreadHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class Signin extends Activity {

    EditText et_sign_activity_email;
    EditText et_sign_activity_password;
    String email;
    String password;
    private PreferenceHelper prefObj;

    ServiceThreadHandler servicesThreadHandler;
    private static String TAG = "Login";
    public static String ERROR = "error";
    public static String SUCCESS = "success";
    private ProgressDialog loginDialog;

    private static final int INTERNET_DIALOG = 0;
    private static final int LOGIN_DIALOG = 1;
    private static final int ERROR_DIALOG = 2;
    private static final int SIGNUP_DIALOG = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.login);

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initview();
        prefObj = new PreferenceHelper(this, PreferenceHelper.CurrentUser);
        String id = prefObj.getPref(PreferenceHelper.Id, "");
        if (prefObj.getPref(PreferenceHelper.Id, "").length() > 0) {
            Intent intent = new Intent(Signin.this, AddNotes.class);
            startActivity(intent);
        }

    }

    public void initview() {

        et_sign_activity_email = (EditText) findViewById(R.id.et_login_email);
        et_sign_activity_password = (EditText) findViewById(R.id.et_login_password);

    }

    public void onLoginclick(View v) {

        email = et_sign_activity_email.getText().toString();
        password = et_sign_activity_password.getText().toString();

        if (email.length() == 0) {
            et_sign_activity_email.setError("Error");

        } else if (password.length() == 0) {

            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT)
                    .show();

        } else {
            showDialog(LOGIN_DIALOG);
        }

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        // prefObj.ClearPreferences();
        // finish();

    }

    public void onRegisterClick(View v) {

        Intent intent = new Intent(getBaseContext(), Register.class);
        startActivity(intent);

    }

    public void ValidatedUser(boolean b, String reponse, UserInfo userObj) {
        if (userObj != null) {
            try {

                prefObj.setUserPref(PreferenceHelper.Id, userObj.getUserId());
                // prefObj.setUserPref(PreferenceHelper.UserID, user)
                prefObj.setUserPref(PreferenceHelper.City, userObj.getCity());
                prefObj.setUserPref(PreferenceHelper.ContactNo,
                        userObj.getPhoneNo());
                prefObj.setUserPref(PreferenceHelper.Country,
                        userObj.getCountry());
                prefObj.setUserPref(PreferenceHelper.DateOfBirth,
                        userObj.getDate_of_birth());
                prefObj.setUserPref(PreferenceHelper.Email, userObj.getEmail());
                prefObj.setUserPref(PreferenceHelper.Gender,
                        userObj.getGender());
                prefObj.setUserPref(PreferenceHelper.Name, userObj.getName());
                prefObj.setUserPref(PreferenceHelper.Password,
                        userObj.getPassword());
                prefObj.setUserPref(PreferenceHelper.State, userObj.getState());
                prefObj.setUserPref(PreferenceHelper.UserName,
                        userObj.getUserName());

            } catch (Exception exp) {
                Log.d(TAG, "Exp: " + exp.getMessage());
            }

        }

        Message myMessage = new Message();
        myMessage.obj = reponse;
        mHandler.sendMessage(myMessage);
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case LOGIN_DIALOG:
                loginDialog = new ProgressDialog(this);
                loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                loginDialog.setMessage("Signing i. ..");
                loginDialog.setIndeterminate(true);
                loginDialog.setCancelable(true);
                return loginDialog;

            case SIGNUP_DIALOG:
                AlertDialog.Builder b3 = new Builder(this);
                b3.setTitle("konto oprettet!");
                b3.setMessage("Nu kan du logge...");
                b3.setNeutralButton("OK", new OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        removeDialog(SIGNUP_DIALOG);
                    }
                });
                b3.setCancelable(true);

                return b3.create();

            case ERROR_DIALOG:
                AlertDialog.Builder b2 = new Builder(this);
                b2.setTitle("Sorry!");
                b2.setMessage("Wrong User ID or Password!");
                b2.setNeutralButton("OK", new OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        removeDialog(ERROR_DIALOG);
                    }
                });
                b2.setCancelable(true);

                return b2.create();

        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {

        switch (id) {

            case LOGIN_DIALOG:
                servicesThreadHandler = new ServiceThreadHandler(this,
                        ServiceThreadHandler.GET_SIGNIN, email.trim(), password);
                servicesThreadHandler.start();
                break;

        }

    }

    public final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            String loginmsg = (String) msg.obj;
            if (loginmsg.equals(SUCCESS)) {

                removeDialog(LOGIN_DIALOG);
                Intent intent = new Intent(Signin.this, AddNotes.class);
                startActivity(intent);
                et_sign_activity_email.setText("");
                et_sign_activity_password.setText("");

            } else if (loginmsg.equals(ERROR)) {
                removeDialog(LOGIN_DIALOG);
                showDialog(ERROR_DIALOG);
            }

        }

    };

    public void onForgetPasswordClick(View v) {

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        //finish();
        super.onStop();
    }

}
