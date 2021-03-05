package com.cyberdesignz.studyup;

import java.util.Calendar;
import java.util.Date;

import com.cyberdesignz.studyup.adapter.MyExams;
import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.io.StudyUpTask;
import com.cyberdesignz.studyup.utils.DateTimePicker;
import com.cyberdesignz.studyup.utils.Utils;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class UpdateExam extends Activity implements
		DateTimePicker.ICustomDateTimeListener, View.OnFocusChangeListener {
	EditText name, time, comments;
	int pos;
	String user_id;
	private PreferenceHelper prefObj;
	DateTimePicker dateTimePicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.update_exams);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		prefObj = new PreferenceHelper(this, PreferenceHelper.CurrentUser);
		user_id = prefObj.getPref(PreferenceHelper.Id, "");
		pos = getIntent().getExtras().getInt("pos");
		name = (EditText) findViewById(R.id.ed_first);
		time = (EditText) findViewById(R.id.ed_second);
		comments = (EditText) findViewById(R.id.edt_comments);
		name.setText(MyExams.examlist.get(pos).getName());
		time.setText(MyExams.examlist.get(pos).getTime());
		comments.setText(MyExams.examlist.get(pos).getComments());
		time.setOnFocusChangeListener(this);
	}

	public void update(View v) {

		new StudyUpTask(this).execute(
				Integer.toString(StudyUpTask.UPDATE_EXAM), MyExams.examlist
						.get(pos).getId(), name.getText().toString(), time
						.getText().toString(), MyExams.examlist.get(pos)
						.getMarks(), MyExams.examlist.get(pos).getType(),
				comments.getText().toString(),user_id);

		finish();

	}

	public void onSet(Calendar calendarSelected, Date dateSelected, int year,
			String monthFullName, String monthShortName, int monthNumber,
			int date, String weekDayFullName, String weekDayShortName,
			int hour24, int hour12, int min, int sec, String AM_PM) {

		time.setText(DateFormat.format("dd-MM-yyyy hh:mm", dateSelected));

		// /ed_marks.requestFocus();
	}

	public void onCancel() {
		// TODO Auto-generated method stub

	}

	public void onFocusChange(View arg0, boolean arg1) {
		if (arg1) {
			if (time.isEnabled()) {
				dateTimePicker = new DateTimePicker(this, this);
				dateTimePicker.set24HourFormat(true);
				dateTimePicker.showDialog();
			}
		}
	}
}
