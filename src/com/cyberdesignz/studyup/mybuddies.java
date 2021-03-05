package com.cyberdesignz.studyup;

import java.util.ArrayList;

import com.cyberdesignz.studyup.adapter.buddieslistadapter;
import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.info.mybuddiesinfo;
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
import android.widget.ListView;
import android.widget.Toast;

public class mybuddies extends Activity {

    ListView mybuddieslistview;
    String user_id;
    private static final int GET_BUDDIES = 1;
    private ArrayList<mybuddiesinfo> buddylist;
    private PreferenceHelper prefObj;
    private ProgressDialog loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mybuddies);
        mybuddieslistview = (ListView) findViewById(R.id.listView_buddies);
        prefObj = new PreferenceHelper(this, PreferenceHelper.CurrentUser);
        user_id = prefObj.getPref(PreferenceHelper.Id, "");
        showDialog(GET_BUDDIES);

    }

    public void onMyFriendsClick(View v) {

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        IntentFilter filter = new IntentFilter(Utils.ACTION_GET_buddies);
        registerReceiver(Buddieslistreciver, filter);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        //
        unregisterReceiver(Buddieslistreciver);
        finish();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case GET_BUDDIES:
                loginDialog = new ProgressDialog(this);
                loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                loginDialog.setMessage("loading feeds...");
                loginDialog.setIndeterminate(true);
                loginDialog.setCancelable(true);
                return loginDialog;

        }
        return null;
    }


    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {

        switch (id) {

            case GET_BUDDIES:

                new StudyUpTask(this).execute(
                        String.valueOf(StudyUpTask.GET_BUDDIES), user_id);

                break;

        }

    }

    public void OnFindFriendClick(View v) {
        Intent i = new Intent(mybuddies.this, searchfriend.class);
        startActivity(i);

    }

    public void onInvite(View v) {
        Intent i = new Intent(mybuddies.this, InviteFriends.class);
        startActivity(i);
    }

    private BroadcastReceiver Buddieslistreciver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            try {

                final Bundle extra = intent.getExtras();
                ArrayList<mybuddiesinfo> buddyslist = (ArrayList<mybuddiesinfo>) extra
                        .getSerializable("getbuddies");
                buddylist = buddyslist;
                initView();
                removeDialog(GET_BUDDIES);

            } catch (Exception e) {

            }

        }

        private void initView() {

            if (buddylist.size() == 0) {
                Toast.makeText(getBaseContext(), "List is empty",
                        Toast.LENGTH_LONG).show();
            } else {

                buddieslistadapter adapter = new buddieslistadapter(
                        getBaseContext(), R.layout.mybuddies, buddylist);
                mybuddieslistview.setAdapter(adapter);

            }
            // eventListView.setSelection(firstPosition);
            // removeDialog(GET_EVENT);
        }
    };

}
