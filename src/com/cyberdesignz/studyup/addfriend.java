package com.cyberdesignz.studyup;

import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.io.StudyUpTask;
import com.cyberdesignz.studyup.utils.Utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

public class addfriend extends Activity {

    private PreferenceHelper prefObj;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        String friend_id = getIntent().getExtras().getString("friend");
        prefObj = new PreferenceHelper(this, PreferenceHelper.CurrentUser);
        user_id = prefObj.getPref(PreferenceHelper.Id, "");
        new StudyUpTask(this).execute(String.valueOf(StudyUpTask.ADD_FRIENDS),
                user_id, friend_id, Integer.toString(1));
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        IntentFilter filter = new IntentFilter(Utils.ACTION_ADD_FRIEND);
        registerReceiver(invita_re, filter);
    }

    private BroadcastReceiver invita_re = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            Bundle extra = intent.getExtras();
            String res = (String) extra.get("addresponses");
            if (res.equals(null)) {
                Toast.makeText(addfriend.this, "Invitaion not sent",
                        Toast.LENGTH_SHORT).show();
            } else if (res.contains("Friend added Successfully")) {

                Toast.makeText(addfriend.this, "Friend added Successfully",
                        Toast.LENGTH_SHORT).show();

            } else if (res.contains("Already Friends")) {
                Toast.makeText(addfriend.this, "Already Friends",
                        Toast.LENGTH_SHORT).show();

            }
            finish();
            Intent i = new Intent(addfriend.this, mybuddies.class);
            startActivity(i);
        }

    };

}
