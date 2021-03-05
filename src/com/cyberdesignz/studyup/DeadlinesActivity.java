package com.cyberdesignz.studyup;

import java.util.ArrayList;

import com.cyberdesignz.studyup.adapter.Deadlinelistadapter;
import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.info.Deadlineinfo;
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

public class DeadlinesActivity extends Activity {
    String user_id;
    PreferenceHelper prefObj;
    private static final int GET_DEADLINES = 1;
    private ProgressDialog deadlinesDialog;
    private ArrayList<Deadlineinfo> deadlines;
    ListView deadlinelist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.upcoming_deadlines);
        prefObj = new PreferenceHelper(this, PreferenceHelper.CurrentUser);
        user_id = prefObj.getPref(PreferenceHelper.Id, "");

        deadlinelist = (ListView) findViewById(R.id.deadlinelist);
        // user_id = prefObj.getPref(PreferenceHelper.Id, "");
        showDialog(GET_DEADLINES);

    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case GET_DEADLINES:
                deadlinesDialog = new ProgressDialog(this);
                deadlinesDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                deadlinesDialog.setMessage("loading Deadlines...");
                deadlinesDialog.setIndeterminate(true);
                deadlinesDialog.setCancelable(true);
                return deadlinesDialog;

        }
        return null;
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        IntentFilter filter2 = new IntentFilter(Utils.ACTION_GET_DEADLINES);
        registerReceiver(Deadlinereciver, filter2);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        unregisterReceiver(Deadlinereciver);
    }

    public void OnAddClassClick(View v) {

        Intent i = new Intent(DeadlinesActivity.this, AddClassActivity.class);
        startActivity(i);
        this.finish();
    }

    public void OnMyClassClick(View v) {

        Intent i = new Intent(DeadlinesActivity.this, MyClassesActivity.class);
        startActivity(i);
        this.finish();
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {

        switch (id) {

            case GET_DEADLINES:

                new StudyUpTask(this).execute(
                        String.valueOf(StudyUpTask.GET_DEDLINE), user_id);

                break;

        }

    }

    BroadcastReceiver Deadlinereciver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            try {

                final Bundle extra = intent.getExtras();
                ArrayList<Deadlineinfo> deadline = (ArrayList<Deadlineinfo>) extra
                        .getSerializable("deadlines");
                deadlines = deadline;
                initView();
                removeDialog(GET_DEADLINES);

            } catch (Exception e) {

            }

        }

        private void initView() {

            if (deadlines.size() == 0) {
                Toast.makeText(getBaseContext(), "List is empty",
                        Toast.LENGTH_LONG).show();
            } else {

                Deadlinelistadapter adapter = new Deadlinelistadapter(
                        getBaseContext(), R.layout.upcoming_deadlines,
                        deadlines);
                deadlinelist.setAdapter(adapter);

            }
            // eventListView.setSelection(firstPosition);
            // removeDialog(GET_EVENT);
        }
    };
}