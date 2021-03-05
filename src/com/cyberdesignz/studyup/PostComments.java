package com.cyberdesignz.studyup;

import java.util.ArrayList;

import com.cyberdesignz.studyup.adapter.CommentsAdapter;
import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.info.CommentsInfo;
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class PostComments extends Activity {

	Button post, cancel;
	String user_id;
	EditText comments;
	ListView comment_listview;
	ProgressBar pbar_lodingList;
	ArrayList<CommentsInfo> commentslist;
	public static boolean check_status;
	private static final int POST_COMMENTS = 1;
	private PreferenceHelper prefObj;
	private ProgressDialog loginDialog;
	String feed_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.postcomments);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		prefObj = new PreferenceHelper(this, PreferenceHelper.CurrentUser);
		user_id = prefObj.getPref(PreferenceHelper.Id, "");
		pbar_lodingList = (ProgressBar) findViewById(R.id.progressBar1);
		comments = (EditText) findViewById(R.id.edt_comment);
		comment_listview = (ListView) findViewById(R.id.comment_list);

		feed_id = getIntent().getExtras().getString("feed_id");
		post = (Button) findViewById(R.id.comment_post);
		cancel = (Button) findViewById(R.id.comment_cancel);

		getComments();
		post.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(POST_COMMENTS);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				removeDialog(POST_COMMENTS);
				getComments();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});

	}

	public void getComments() {

		new StudyUpTask(this).execute(String.valueOf(StudyUpTask.GET_COMMENTS),
				feed_id, user_id);

	}

	BroadcastReceiver getcomments = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			try {

				final Bundle extra = intent.getExtras();
				ArrayList<CommentsInfo> commentlist = (ArrayList<CommentsInfo>) extra
						.getSerializable("CommentsList");
				commentslist = commentlist;
				initView();
				pbar_lodingList.setVisibility(View.INVISIBLE);

			} catch (Exception e) {

			}

		}

		private void initView() {

			if (commentslist.size() == 0) {
				Toast.makeText(getBaseContext(), "List is empty",
						Toast.LENGTH_LONG).show();
			} else {

				CommentsAdapter adapter = new CommentsAdapter(getBaseContext(),
						R.layout.comments_layout, commentslist);
				comment_listview.setAdapter(adapter);

			}
			// eventListView.setSelection(firstPosition);
			// removeDialog(GET_EVENT);
		}

	};

	protected Dialog onCreateDialog(int id) {
		switch (id) {

		case POST_COMMENTS:
			loginDialog = new ProgressDialog(this);
			loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			loginDialog.setMessage("Posting .....");
			loginDialog.setIndeterminate(true);
			loginDialog.setCancelable(true);
			return loginDialog;

		}
		return null;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {

		switch (id) {

		case POST_COMMENTS:

			new StudyUpTask(this).execute(
					String.valueOf(StudyUpTask.POST_COMMENTS), user_id,
					feed_id, comments.getText().toString());

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (check_status == true) {

				Toast.makeText(getBaseContext(), "Comment Posted",
						Toast.LENGTH_SHORT).show();
				comments.setText("");

			} else {

				Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT)
						.show();

			}

			break;

		}

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		IntentFilter filter = new IntentFilter(Utils.ACTION_GET_COMMENT);
		registerReceiver(getcomments, filter);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		unregisterReceiver(getcomments);
	}

}
