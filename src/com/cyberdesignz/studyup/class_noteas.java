package com.cyberdesignz.studyup;

import java.util.ArrayList;
import java.util.List;

import com.cyberdesignz.studyup.adapter.class_Notes_Adapter;
import com.cyberdesignz.studyup.adapter.markslist_adapter;
import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.info.ClassInfo;
import com.cyberdesignz.studyup.info.ExamInfo;
import com.cyberdesignz.studyup.info.classnotesInfo;
import com.cyberdesignz.studyup.io.StudyUpTask;
import com.cyberdesignz.studyup.utils.Utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

public class class_noteas extends Activity {

	String class_id, user_id;
	private ArrayList<classnotesInfo> classList;
	ListView list;
	PreferenceHelper prefObj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.class_notes);
		prefObj = new PreferenceHelper(this, PreferenceHelper.CurrentUser);
		user_id = prefObj.getPref(PreferenceHelper.Id, "");
		class_id = getIntent().getExtras().getString("class_id");
		list = (ListView) findViewById(R.id.l_notes);
		new StudyUpTask(this).execute(
				Integer.toString(StudyUpTask.GET_CLASS_NOTES), class_id,
				user_id);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub

		IntentFilter filter = new IntentFilter(Utils.ACTION_GET_CLASSNOTES);
		registerReceiver(recivenotes, filter);
		super.onStart();

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		unregisterReceiver(recivenotes);
		super.onStop();
	}

	BroadcastReceiver recivenotes = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			try {

				final Bundle extra = intent.getExtras();
				ArrayList<classnotesInfo> _examList;
				_examList = (ArrayList<classnotesInfo>) extra
						.getSerializable("classnotes");
				classList = _examList;
				emailView();

			} catch (Exception e) {

				Log.d("asd", "asd");

			}

		}
	};

	protected void emailView() {
		if (classList.isEmpty()) {
			Toast.makeText(class_noteas.this, "No Notes Found",
					Toast.LENGTH_SHORT).show();
			this.finish();

		} else {
			class_Notes_Adapter maksadap = new class_Notes_Adapter(
					class_noteas.this, R.layout.class_notes, classList);
			list.setAdapter(maksadap);
		}

	}

}
