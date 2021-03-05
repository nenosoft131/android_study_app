package com.cyberdesignz.studyup;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.io.StudyUpTask;
import com.cyberdesignz.studyup.utils.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class InviteFriends extends Activity {
    static ArrayList<String> friendslist = new ArrayList<String>();
    EditText edt_friends;

    String user_id;
    private static final int SEND_INVIT = 1;
    private PreferenceHelper prefObj;
    private ProgressDialog loginDialog;
    ListView feedlist_view;
    ListView list;
    String ids = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.invitefriends);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        edt_friends = (EditText) findViewById(R.id.editText1);
        list = (ListView) findViewById(R.id.list_invite);
        prefObj = new PreferenceHelper(this, PreferenceHelper.CurrentUser);
        user_id = prefObj.getPref(PreferenceHelper.Id, "");
        list.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                friendslist));

    }

    public void onaddclick(View v) {
        if (isEmailValid(edt_friends.getText().toString())) {
            friendslist.add(edt_friends.getText().toString());
            edt_friends.setText("");

            list.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1,
                    friendslist));
        } else {
            Toast.makeText(InviteFriends.this, "Invalid Email",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        IntentFilter filter = new IntentFilter(Utils.ACTION_GET_INVITATION);
        registerReceiver(invita_re, filter);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();

        unregisterReceiver(invita_re);
        finish();

    }

    public void OnFindFriendClick(View v) {
        Intent i = new Intent(InviteFriends.this, searchfriend.class);
        startActivity(i);

    }

    public void OnContacts(View v) {
        Intent i = new Intent(InviteFriends.this, reademailsfromcontacts.class);
        startActivity(i);
    }

    public void OnFrienlistclick(View v) {
        Intent i = new Intent(InviteFriends.this, mybuddies.class);
        startActivity(i);

    }

    public void OnSendinvitation(View v) {
        if (friendslist.isEmpty()) {
            Toast.makeText(InviteFriends.this, "No Email Entered",
                    Toast.LENGTH_SHORT).show();

        } else {
            showDialog(SEND_INVIT);
        }
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case SEND_INVIT:
                loginDialog = new ProgressDialog(this);
                loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                loginDialog.setMessage("Sending Invitaion.........");
                loginDialog.setIndeterminate(true);
                loginDialog.setCancelable(true);
                return loginDialog;

        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {

        switch (id) {

            case SEND_INVIT:

                for (int i = 0; i < friendslist.size(); i++) {
                    if ((friendslist.size() - 1) == i) {
                        ids = ids + friendslist.get(i).toString();
                    } else {
                        ids = ids + friendslist.get(i).toString() + ',';
                    }
                }

        }
        String m = ids;
        new StudyUpTask(this).execute(
                String.valueOf(StudyUpTask.INVITE_FRIENDS), user_id, ids);

    }

    private BroadcastReceiver invita_re = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            Bundle extra = intent.getExtras();
            String res = (String) extra.get("invitaionresponse");
            if (res.equals(null)) {
                Toast.makeText(InviteFriends.this, "Invitaion not sent",
                        Toast.LENGTH_SHORT).show();
            } else if (res.contains("Successfully invitation sent")) {
                removeDialog(SEND_INVIT);
                Toast.makeText(InviteFriends.this, "Successfully sent",
                        Toast.LENGTH_SHORT).show();
                removeinvitaion();

            } else {
                Toast.makeText(InviteFriends.this, "Invitaion not sent",
                        Toast.LENGTH_SHORT).show();

            }

        }

    };

    protected void removeinvitaion() {
        // TODO Auto-generated method stub
        friendslist.clear();
        list.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                friendslist));

    }

}
