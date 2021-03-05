package com.cyberdesignz.studyup;

import java.util.ArrayList;

import com.cyberdesignz.studyup.adapter.searchfriendsadapter;
import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.info.SearchfriendsInfo;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class searchfriend extends Activity {

    EditText search_Friend;
    ListView searchFriendlistview;
    String user_id;
    public static String friend_id;
    private static final int SEARCH_FRIEND = 1;
    private ArrayList<SearchfriendsInfo> friendslist;
    private PreferenceHelper prefObj;
    private ProgressDialog loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.searchfriends);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        search_Friend = (EditText) findViewById(R.id.edt_searchfriend);
        searchFriendlistview = (ListView) findViewById(R.id.listView_serachfriends);
        prefObj = new PreferenceHelper(this, PreferenceHelper.CurrentUser);
        user_id = prefObj.getPref(PreferenceHelper.Id, "");

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        IntentFilter filter = new IntentFilter(Utils.ACTION_SEARCH_FRIENDS);
        registerReceiver(SearchedFriends, filter);

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();

        unregisterReceiver(SearchedFriends);
        finish();

    }

    public void OnFrienlistclick(View v) {
        Intent i = new Intent(searchfriend.this, mybuddies.class);
        startActivity(i);
    }

    public void onInvite(View v) {
        Intent i = new Intent(searchfriend.this, InviteFriends.class);
        startActivity(i);
    }

    public void OnSearchClick(View v) {

        showDialog(SEARCH_FRIEND);

    }

    private BroadcastReceiver SearchedFriends = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            try {

                final Bundle extra = intent.getExtras();
                ArrayList<SearchfriendsInfo> Friendslist = (ArrayList<SearchfriendsInfo>) extra
                        .getSerializable("searchfriends");
                friendslist = Friendslist;
                initView();
                removeDialog(SEARCH_FRIEND);

            } catch (Exception e) {

            }

        }

        private void initView() {

            if (friendslist.size() == 0) {
                Toast.makeText(getBaseContext(), "List is empty",
                        Toast.LENGTH_LONG).show();
            } else {

                searchfriendsadapter adapter = new searchfriendsadapter(
                        getBaseContext(), R.layout.searchfriends, friendslist);
                searchFriendlistview.setAdapter(adapter);

            }
            // eventListView.setSelection(firstPosition);
            // removeDialog(GET_EVENT);
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case SEARCH_FRIEND: {
                loginDialog = new ProgressDialog(this);
                loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                loginDialog.setMessage("Searching Frinds...");
                loginDialog.setIndeterminate(true);
                loginDialog.setCancelable(true);

                return loginDialog;

            }

        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {

        switch (id) {

            case SEARCH_FRIEND: {

                new StudyUpTask(this).execute(
                        String.valueOf(StudyUpTask.SEARCH_FRIENDS), user_id,
                        search_Friend.getText().toString());
                break;
            }

        }

    }
}
