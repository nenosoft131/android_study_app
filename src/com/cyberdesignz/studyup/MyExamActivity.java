/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberdesignz.studyup;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.cyberdesignz.studyup.adapter.MyExams;
import com.cyberdesignz.studyup.adapter.markslist_adapter;
import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.info.ExamInfo;
import com.cyberdesignz.studyup.io.StudyUpTask;
import com.cyberdesignz.studyup.utils.DateTimePicker;
import com.cyberdesignz.studyup.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author arshad
 */
public class MyExamActivity extends Activity implements
		View.OnFocusChangeListener, DateTimePicker.ICustomDateTimeListener {

	EditText ed_name;
	EditText ed_comment;
	EditText ed_timeSlot;
	ListView lv_exams;
	TextView title;
	int type;
	boolean t = false;
	String classID, type1;

	DateTimePicker dateTimePicker;
	public static ArrayList<ExamInfo> examList;
	MyExams classesAdapter;

	public static final int EXAMS = 1;
	public static final int ASSIGNMENTS = 2;
	public static final int QUIZZEZ = 3;
	public static final int TESTS = 4;
	String class_name, user_id;
	private PreferenceHelper prefObj;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.exam_layout);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		prefObj = new PreferenceHelper(this, PreferenceHelper.CurrentUser);
		user_id = prefObj.getPref(PreferenceHelper.Id, "");
		class_name = getIntent().getExtras().getString("classname");
		ed_name = (EditText) findViewById(R.id.ed_first);
		ed_timeSlot = (EditText) findViewById(R.id.ed_second);
		ed_comment = (EditText) findViewById(R.id.edt_comments);
		lv_exams = (ListView) findViewById(R.id.lv_exams);
		title = (TextView) findViewById(R.id.tv_text);

		markslist_adapter.test = false;

		run();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		// finish();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (t) {

			new StudyUpTask(this).execute(StudyUpTask.GET_EXAM + "", classID,
					user_id);
		}
		t = false;
	}

	private void run() {
		examList = new ArrayList<ExamInfo>();

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			Toast.makeText(this, "NO Class AND Type Info", Toast.LENGTH_LONG)
					.show();

		} else {
			type1 = extras.getString("type1");
			type = extras.getInt("type");
			if (type1.equals("Quiz")) {
				title.setText("My Quizzes");

			} else if (type1.equals("Exam")) {
				title.setText("My Exams");

			} else if (type1.equals("Assignment")) {
				title.setText("My Assignments");

			} else if (type1.equals("Test")) {
				title.setText("My Tests");

			}
			ed_timeSlot.setHint(type1 + " Date & Time");

			classID = extras.getString("class_id");
			ed_timeSlot.setOnFocusChangeListener(this);
			classesAdapter = new MyExams(MyExamActivity.this,
					R.layout.examlistviewlayout, (ArrayList<ExamInfo>) examList);
			lv_exams.setAdapter(classesAdapter);
			new StudyUpTask(this).execute(StudyUpTask.GET_EXAM + "", classID,
					user_id);
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub

		IntentFilter filter = new IntentFilter(Utils.ACTION_GET_EXAM);
		registerReceiver(examListReceiver, filter);
		IntentFilter filter2 = new IntentFilter(Utils.ACTION_NOTIFY_ERROR);
		registerReceiver(errorReceiver, filter2);

		super.onStart();

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub

		unregisterReceiver(examListReceiver);
		unregisterReceiver(errorReceiver);
		super.onStop();
	}

	private BroadcastReceiver errorReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			final Bundle extra = arg1.getExtras();
			String desc = extra.getString("desc");
			Toast.makeText(MyExamActivity.this, desc, Toast.LENGTH_LONG).show();
		}
	};

	private BroadcastReceiver examListReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			try {

				final Bundle extra = intent.getExtras();
				@SuppressWarnings("unchecked")
				ArrayList<ExamInfo> _examList;
				_examList = (ArrayList<ExamInfo>) extra
						.getSerializable("examlist");
				examList.clear();

				for (ExamInfo ei : _examList) {
					switch (MyExamActivity.this.type) {
					case MyExamActivity.EXAMS:
						if (ei.getType().equals("Exam")) {
							examList.add(ei);
						}
						break;
					case MyExamActivity.ASSIGNMENTS:
						if (ei.getType().equals("Assignment")) {
							examList.add(ei);
						}
						break;
					case MyExamActivity.QUIZZEZ:
						if (ei.getType().equals("Quiz")) {
							examList.add(ei);
						}
						break;
					case MyExamActivity.TESTS:
						if (ei.getType().equals("Test")) {
							examList.add(ei);
						}
						break;
					default:
						break;

					}

				}
				ed_name.setText(class_name + "\t" + type1 + " "
						+ (examList.size() + 1));
				classesAdapter.notifyDataSetChanged();
				t = true;
			} catch (Exception e) {

				Log.d("asd", "asd");

			}

		}
	};

	public void onFocusChange(View arg0, boolean arg1) {
		if (arg1) {
			if (ed_timeSlot.isEnabled()) {
				dateTimePicker = new DateTimePicker(this, this);
				dateTimePicker.set24HourFormat(true);
				dateTimePicker.showDialog();
			}
		}
	}

	public void onSet(Calendar calendarSelected, Date dateSelected, int year,
			String monthFullName, String monthShortName, int monthNumber,
			int date, String weekDayFullName, String weekDayShortName,
			int hour24, int hour12, int min, int sec, String AM_PM) {

		ed_timeSlot
				.setText(DateFormat.format("dd-MM-yyyy hh:mm", dateSelected));

		// /ed_marks.requestFocus();
	}

	public void onCancel() {

	}

	public void submit(View view) {
		String exam_type = "";
		switch (type) {
		case EXAMS:
			exam_type = "Exam";
			break;
		case ASSIGNMENTS:
			exam_type = "Assignment";
			break;
		case TESTS:
			exam_type = "Test";
			break;
		case QUIZZEZ:
			exam_type = "Quiz";
			break;

		}
		if (ed_name.getText().toString().length() == 0) {
			Toast.makeText(this, "Enter the name", Toast.LENGTH_SHORT).show();
			return;
		}

		if (ed_timeSlot.getText().toString().length() == 0) {
			Toast.makeText(this, "Enter the Time Slot", Toast.LENGTH_SHORT)
					.show();
			return;
		}

		new StudyUpTask(this).execute(StudyUpTask.ADD_EXAM + "", classID,
				ed_name.getText().toString(), ed_timeSlot.getText().toString(),
				ed_comment.getText().toString(), exam_type, user_id);
		ed_name.setText("");
		ed_timeSlot.setText("");
		ed_comment.setText("");
	}

	public void set() {

	}

}
