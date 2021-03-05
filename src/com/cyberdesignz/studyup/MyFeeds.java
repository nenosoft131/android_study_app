package com.cyberdesignz.studyup;

import java.util.ArrayList;

import com.cyberdesignz.studyup.adapter.FeedsListAdapter;
import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.info.FeedsInfo;
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
import android.widget.ListView;
import android.widget.Toast;

public class MyFeeds extends Activity {

    String user_id;
    private static final int GET_FEEDS = 1;
    private ArrayList<FeedsInfo> feedlist;
    private PreferenceHelper prefObj;
    private ProgressDialog loginDialog;
    ListView feedlist_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.my_feeds);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        feedlist_view = (ListView) findViewById(R.id.listfeed);
        prefObj = new PreferenceHelper(this, PreferenceHelper.CurrentUser);
        user_id = prefObj.getPref(PreferenceHelper.Id, "");
        showDialog(GET_FEEDS);

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub

        super.onStart();
        IntentFilter filter = new IntentFilter(Utils.ACTION_GET_FEEDS);
        registerReceiver(Feedslistreciver, filter);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        unregisterReceiver(Feedslistreciver);
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case GET_FEEDS:
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

            case GET_FEEDS:

                new StudyUpTask(this).execute(
                        String.valueOf(StudyUpTask.GET_FEEDS), user_id);

                break;

        }

    }

    public void onAddNotesClick(View v) {
        finish();
        Intent intent = new Intent(getBaseContext(), AddNotes.class);
        startActivity(intent);
    }

    public void OnBuddyClick(View v) {
        Intent i = new Intent(MyFeeds.this, mybuddies.class);
        startActivity(i);
    }

    public void onReviewNotesClick(View v) {
        finish();
        Intent intent = new Intent(getBaseContext(), ReviewNotes.class);
        startActivity(intent);

    }

    public void onMyClassesClick(View v) {
        Intent i = new Intent(MyFeeds.this, MyClassesActivity.class);
        startActivity(i);

    }

    public void onMyFeedsClick(View v) {

    }

    private BroadcastReceiver Feedslistreciver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            try {

                final Bundle extra = intent.getExtras();
                ArrayList<FeedsInfo> feedslist = (ArrayList<FeedsInfo>) extra
                        .getSerializable("FeedsList");
                feedlist = feedslist;
                initView();
                removeDialog(GET_FEEDS);

            } catch (Exception e) {

            }

        }
    };

    public void initView() {

        if (feedlist.size() == 0) {
            Toast.makeText(getBaseContext(), "List is empty", Toast.LENGTH_LONG)
                    .show();
        } else {

            FeedsListAdapter adapter = new FeedsListAdapter(getBaseContext(),
                    R.layout.my_feeds, feedlist);
            feedlist_view.setAdapter(adapter);

        }
        // eventListView.setSelection(firstPosition);
        // removeDialog(GET_EVENT);
    }
}
