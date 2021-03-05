package com.cyberdesignz.studyup;

import java.util.ArrayList;

import com.cyberdesignz.studyup.adapter.NotesAdapter;
import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.info.NoteInfo;
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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

public class ReviewNotes extends Activity {
    private String TAG;

    private ArrayList<NoteInfo> noteList;
    String user_id;
    private PreferenceHelper prefObj;
    private static final int GET_NOTE = 1;
    private ListView eventListView;
    private ProgressDialog loginDialog;
    public static boolean checkResume = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.review_notes);

        eventListView = (ListView) findViewById(R.id.lv_calander_events);
        prefObj = new PreferenceHelper(this, PreferenceHelper.CurrentUser);
        user_id = prefObj.getPref(PreferenceHelper.Id, "");
        showDialog(GET_NOTE);

    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case GET_NOTE:
                loginDialog = new ProgressDialog(this);
                loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                loginDialog.setMessage("loading list ...");
                loginDialog.setIndeterminate(true);
                loginDialog.setCancelable(true);
                return loginDialog;

        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {

        switch (id) {

            case GET_NOTE:

                new StudyUpTask(this).execute(
                        String.valueOf(StudyUpTask.GET_NOTES), user_id);

                break;

        }

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        IntentFilter filter = new IntentFilter(Utils.ACTION_GET_NOTE);
        registerReceiver(reviewNotesReceiver, filter);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        unregisterReceiver(reviewNotesReceiver);
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

        if (checkResume)
            showDialog(GET_NOTE);

        checkResume = false;
    }

    private BroadcastReceiver reviewNotesReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            try {

                final Bundle extra = intent.getExtras();
                ArrayList<NoteInfo> newNoteList = (ArrayList<NoteInfo>) extra
                        .getSerializable("NoteList");
                noteList = newNoteList;
                initView();
                removeDialog(GET_NOTE);

            } catch (Exception e) {

                Log.d(TAG, "Error while getting group list");

            }

        }
    };

    public void initView() {

        if (noteList.size() == 0) {
            Toast.makeText(getBaseContext(), "List is empty please add note",
                    Toast.LENGTH_LONG).show();
        } else {
            NotesAdapter adapter = new NotesAdapter(getBaseContext(),
                    R.layout.review_notes, (ArrayList<NoteInfo>) noteList);
            eventListView.setAdapter(adapter);
        }
        // eventListView.setSelection(firstPosition);
        // removeDialog(GET_EVENT);
    }

    public void getReviews() {

        // new StudyUpTask(this).execute(String.valueOf(StudyUpTask.GET_CLASS));
        // user id is dummy

    }

    public void onbackclick(View v) {

        finish();
    }

    public void onAddNotesClick(View v) {
        finish();
        Intent intent = new Intent(getBaseContext(), AddNotes.class);
        startActivity(intent);

    }

    public void onMyClassesClick(View v) {
        Intent i = new Intent(ReviewNotes.this, MyClassesActivity.class);
        startActivity(i);

    }

    public void OnBuddyClick(View v) {
        Intent i = new Intent(ReviewNotes.this, mybuddies.class);
        startActivity(i);
    }

    public void onMyFeedsClick(View v) {
        finish();
        Intent intent = new Intent(getBaseContext(), MyFeeds.class);
        startActivity(intent);
    }

}
