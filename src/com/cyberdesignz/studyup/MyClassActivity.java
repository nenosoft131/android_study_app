/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberdesignz.studyup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.cyberdesignz.studyup.info.ClassInfo;
import com.cyberdesignz.studyup.io.StudyUpTask;

/**
 * @author arshad
 */
public class MyClassActivity extends Activity {
	EditText tv[];
	Button btns[];
	ClassInfo ci;
	global glob;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.myclasslayout);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		Bundle extras = getIntent().getExtras();
		glob = (global) getApplication();
		if (extras == null) {
			Toast.makeText(this, "NO Class Info", Toast.LENGTH_LONG).show();

		} else {
			ci = (ClassInfo) (extras.getSerializable("ci"));
			tv = new EditText[5];
			tv[0] = (EditText) findViewById(R.id.class_name);
			tv[1] = (EditText) findViewById(R.id.prof_name);
			tv[2] = (EditText) findViewById(R.id.time_slot);
			tv[3] = (EditText) findViewById(R.id.ed_books);
			tv[4] = (EditText) findViewById(R.id.subject_name);
			btns = new Button[4];
			btns[0] = (Button) findViewById(R.id.btn_exam);
			btns[1] = (Button) findViewById(R.id.btn_assignment);
			btns[2] = (Button) findViewById(R.id.btn_quizzez);
			btns[3] = (Button) findViewById(R.id.btn_test);

			tv[0].setText("  " + ci.getClassName());
			tv[1].setText("  " + ci.getProfessor_name());
			tv[3].setText("  "
					+ (ci.getBooks().toString().replace("[", "")).replace("]",
							""));
			String s = ci.getTime_slot().toString();
			s = s.replace(",", "\n");
			tv[2].setText("  " + s);
			tv[4].setText("  " + AddClassActivity.subjectname);
			tv[2].setKeyListener(null);
		}

	}

	public void ClassNotesClick(View v) {
		Intent i = new Intent(MyClassActivity.this, class_noteas.class);
		i.putExtra("class_id", ci.getClassId());
		startActivity(i);

	}

	public void OnAddMarksclick(View v) {
		Intent i = new Intent(MyClassActivity.this, AddMarks.class);
		i.putExtra("class_id", ci.getClassId());
		startActivity(i);
	}

	public void clicked(View view) {

		Button caller = (Button) view;
		Intent i = new Intent(MyClassActivity.this, MyExamActivity.class);
		i.putExtra("classname", ci.getClassName());

		if (caller == btns[0]) {
			i.putExtra("type", MyExamActivity.EXAMS);
			i.putExtra("type1", "Exam");

		} else if (caller == btns[1]) {
			i.putExtra("type", MyExamActivity.ASSIGNMENTS);
			i.putExtra("type1", "Assignment");
		} else if (caller == btns[2]) {
			i.putExtra("type", MyExamActivity.QUIZZEZ);
			i.putExtra("type1", "Quiz");
		} else {
			i.putExtra("type", MyExamActivity.TESTS);
			i.putExtra("type1", "Test");

		}
		i.putExtra("class_id", ci.getClassId());
		startActivity(i);

	}
}
