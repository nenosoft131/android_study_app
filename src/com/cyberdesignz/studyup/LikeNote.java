package com.cyberdesignz.studyup;

import java.util.ArrayList;

import com.cyberdesignz.studyup.adapter.LikeListAdapter;
import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.info.UserInfo;
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
import android.widget.ProgressBar;
import android.widget.Toast;

public class LikeNote extends Activity {

	ListView lst_userName;
	private String TAG;
	String note_id;
	String image_name, user_id;

	private static final int GET_LIKE_LIST = 1;
	private ProgressDialog loginDialog;
	private ArrayList<UserInfo> userList;

	ProgressBar pbar_lodingList;
	PreferenceHelper prefObj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.likenote);
		prefObj = new PreferenceHelper(this, PreferenceHelper.CurrentUser);
		user_id = prefObj.getPref(PreferenceHelper.Id, "");
		lst_userName = (ListView) findViewById(R.id.like_list);
		pbar_lodingList = (ProgressBar) findViewById(R.id.progressBar1_likenote_loading);

		Intent intent = getIntent();
		if (intent.hasExtra("notes_Ids")) {
			note_id = intent.getExtras().getString("note_ids");
			image_name = intent.getExtras().getString("image_name");

		}
		showDialog(GET_LIKE_LIST);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		IntentFilter filter = new IntentFilter(Utils.ACTION_GET_LIKE);
		registerReceiver(getLikeReceiver, filter);
		IntentFilter filter2 = new IntentFilter(Utils.ACTION_GET_LIKECOMMENTS);
		registerReceiver(getLikeCommentsReceiver, filter2);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		unregisterReceiver(getLikeReceiver);
		unregisterReceiver(getLikeCommentsReceiver);
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
	}

	public void Oncancelclick(View v) {
		finish();

	}

	private BroadcastReceiver getLikeCommentsReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			final Bundle extra = intent.getExtras();
			String res = (String) extra.getSerializable("likecomments");
			if (res.contains("true")) {
				Toast.makeText(LikeNote.this, "Successfully Like",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(LikeNote.this, "Already Like",
						Toast.LENGTH_SHORT).show();
			}
		}

		;
	};

	private BroadcastReceiver getLikeReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			try {

				final Bundle extra = intent.getExtras();
				ArrayList<UserInfo> newUserList = (ArrayList<UserInfo>) extra
						.getSerializable("LikeList");
				userList = newUserList;
				initView();
				// removeDialog(GET_NOTE);

			} catch (Exception esss) {

				String asd = esss.toString();
				Log.d("asd", esss.toString());
				Log.d(TAG, "Error while getting group list");

			}

		}
	};

	public void initView() {

		if (userList.size() == 0) {
			Toast.makeText(getBaseContext(), "List is empty ",
					Toast.LENGTH_LONG).show();
		} else {
			try {
				LikeListAdapter adapter = new LikeListAdapter(getBaseContext(),
						R.layout.likenote, userList);
				lst_userName.setAdapter(adapter);
			} catch (Exception ex) {

				String sas = ex.toString();
				Log.d("asd", "dsa");

			}
			// lst_userName.setAdapter(adapter);
		}
		pbar_lodingList.setVisibility(View.INVISIBLE);
		removeDialog(GET_LIKE_LIST);
		// eventListView.setSelection(firstPosition);
		// removeDialog(GET_EVENT);
	}

	public void onLikeClick(View v) {
		callLikelist();
	}

	public void callLikelist() {

		String s = getIntent().getExtras().getString("notes_Ids");
		String user_id = prefObj.getPref(PreferenceHelper.Id, "");
		new StudyUpTask(getBaseContext()).execute(
				String.valueOf(StudyUpTask.LIKE_COMMENT), user_id, s);

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {

		case GET_LIKE_LIST:
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
		// TODO Auto-generated method stub
		super.onPrepareDialog(id, dialog);
		switch (id) {

		case GET_LIKE_LIST:

			String s = getIntent().getExtras().getString("notes_Ids");
			new StudyUpTask(getBaseContext()).execute(
					String.valueOf(StudyUpTask.LIKE_LIST), s, user_id);
			break;

		}

	}

}
