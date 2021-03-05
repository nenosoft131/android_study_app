package com.cyberdesignz.studyup;

import java.util.ArrayList;

import com.cyberdesignz.studyup.adapter.MyExams;
import com.cyberdesignz.studyup.adapter.markslist_adapter;
import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.info.ExamInfo;
import com.cyberdesignz.studyup.io.StudyUpTask;
import com.cyberdesignz.studyup.utils.Utils;

import android.app.Activity;
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

public class Exam_Marks extends Activity {

	ListView listview;
	MyExams classesAdapter;
	String class_id, user_id;
	private PreferenceHelper prefObj;
	ArrayList<ExamInfo> Examarraytext = new ArrayList<ExamInfo>();
	ArrayList<ExamInfo> Examarray = new ArrayList<ExamInfo>();
	public static ArrayList<ExamInfo> textarray = new ArrayList<ExamInfo>();
	public static ArrayList<ExamInfo> assigarray = new ArrayList<ExamInfo>();
	public static ArrayList<ExamInfo> quizarray = new ArrayList<ExamInfo>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.exam_marks_layout);
		prefObj = new PreferenceHelper(this, PreferenceHelper.CurrentUser);
		user_id = prefObj.getPref(PreferenceHelper.Id, "");

		listview = (ListView) findViewById(R.id.list_layout);

		this.class_id = AddMarks.class_id;
		new StudyUpTask(this).execute(StudyUpTask.GET_EXAM + "", this.class_id,
				user_id);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		IntentFilter filter = new IntentFilter(Utils.ACTION_GET_EXAM);
		registerReceiver(examListReceiver, filter);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		unregisterReceiver(examListReceiver);
		super.onStop();
		Examarray.clear();
		textarray.clear();
		assigarray.clear();
		quizarray.clear();

	}

	private BroadcastReceiver examListReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {

			try {

				final Bundle extra = intent.getExtras();
				@SuppressWarnings("unchecked")
				ArrayList<ExamInfo> _examList;
				_examList = (ArrayList<ExamInfo>) extra
						.getSerializable("examlist");
				Examarraytext = _examList;
				for (ExamInfo in : _examList) {

					if (in.getType().equals("Test")) {
						textarray.add(in);
					} else if (in.getType().equals("Exam")) {
						Examarray.add(in);
					} else if (in.getType().equals("Quiz")) {
						quizarray.add(in);
					} else if (in.getType().equals("Assignment")) {
						assigarray.add(in);
					}

				}

				init();

			} catch (Exception e) {

				Log.d("asd", "asd");

			}

		}

		private void init() {

			if (Examarray.isEmpty()) {

				Toast.makeText(Exam_Marks.this, "No Record Found",
						Toast.LENGTH_SHORT).show();
			} else {
				markslist_adapter maksadap = new markslist_adapter(
						Exam_Marks.this, R.layout.exam_marks_layout, Examarray);
				listview.setAdapter(maksadap);

			}
		}

	};
}