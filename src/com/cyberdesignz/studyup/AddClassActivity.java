/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberdesignz.studyup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.info.ClassInfo;
import com.cyberdesignz.studyup.info.SubjectInfo;
import com.cyberdesignz.studyup.io.StudyUpTask;
import com.cyberdesignz.studyup.utils.DateTimePicker;
import com.cyberdesignz.studyup.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AddClassActivity extends Activity {
	Button btn_addBook;
	global app;
	List<String> books;
	String time = "";
	private PreferenceHelper prefObj;
	String subject_id;
	int ind;
	boolean checker = false;
	String dayunchnage[] = { "Monday", "Tuesday", "Wednesday", "Thursday",
			"Friday" };
	String days[] = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
	String da[] = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
	String day2[] = { "", "", "", "", "" };
	String day[] = { "", "", "", "", "" };
	public static String start_t[] = { "", "", "", "", "" };
	public static String stop_t[] = { "", "", "", "", "" };
	ArrayList<String> starttime = new ArrayList<String>();
	ArrayList<String> stoptime = new ArrayList<String>();
	ListView lv_books, tv_time;
	ArrayAdapter<String> adapter, adapter2;
	AutoCompleteTextView actv_className, auto_subjectname;
	EditText ed_professor;
	EditText ed_timeSlot;
	boolean timechecker = false;

	ProgressDialog mDialog;
	boolean newentry;
	private List<ClassInfo> classList;
	private List<SubjectInfo> subList;
	private List<String> subData;
	private List<String> subID;
	private List<String> classData;
	private List<String> classID;
	EditText ed_addBook;
	String user_id, class_id;
	global glob;
	Button start_time;
	ArrayAdapter<String> aut_sub;
	DateTimePicker dateTimePicker;
	boolean submitcase;
	public static String subjectname = "";

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		load();
	}

	private void load() {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addclass_layout);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		glob = (global) getApplication();
		submitcase = false;
		prefObj = new PreferenceHelper(this, PreferenceHelper.CurrentUser);
		user_id = prefObj.getPref(PreferenceHelper.Id, "");
		app = (global) getApplicationContext();
		btn_addBook = (Button) findViewById(R.id.btn_addBook);
		actv_className = (AutoCompleteTextView) findViewById(R.id.ed_class);
		auto_subjectname = (AutoCompleteTextView) findViewById(R.id.actv_subject);
		ed_professor = (EditText) findViewById(R.id.ed_profes);
		tv_time = (ListView) findViewById(R.id.lv_Time);
		start_time = (Button) findViewById(R.id.button_start);

		udatelist();
		// ed_timeSlot = (EditText) findViewById(R.id.ed_timeSlot);

		adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, da);
		tv_time.setAdapter(adapter2);

		ed_addBook = (EditText) findViewById(R.id.ed_addBook);
		books = new ArrayList<String>();
		lv_books = (ListView) findViewById(R.id.lv_books);
		newentry = false;
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, books);
		lv_books.setAdapter(adapter);

		mDialog = new ProgressDialog(this);
		mDialog.setMessage("Please wait...");
		mDialog.setCancelable(false);
		mDialog.show();

		new StudyUpTask(this).execute(
				Integer.toString(StudyUpTask.GET_SUBJECTS), user_id);
		new StudyUpTask(this).execute(StudyUpTask.GET_CLASS + "", user_id);

	}

	private void udatelist() {
		for (int i = 0; i < 5; i++) {
			starttime.add("");
			stoptime.add("");
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		ed_professor.requestFocus();

		if (timeslot.check) {
			for (int i = 0; i < days.length; i++) {
				da[i] = days[i] + " " + app.starttime.get(i) + " - "
						+ app.stoptime.get(i);
				start_t[i] = app.starttime.get(i);
				stop_t[i] = app.stoptime.get(i);
			}

			adapter2.notifyDataSetChanged();
			timeslot.check = false;
		} /*
		 * if (checker) { for (int i = 0; i < da.length; i++) { da[i] =
		 * dayunchnage[i]; } adapter2.notifyDataSetChanged(); }
		 * adapter2.notifyDataSetChanged();
		 */

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		for (int i = 0; i < glob.starttime.size(); i++) {
			glob.starttime.put(i, "");
			glob.stoptime.put(i, "");
		}
		for (int i = 0; i < days.length; i++) {
			da[i] = days[i] + " " + app.starttime.get(i) + " - "
					+ app.stoptime.get(i);
			start_t[i] = app.starttime.get(i);
			stop_t[i] = app.stoptime.get(i);
		}

		adapter2.notifyDataSetChanged();
		super.onRestart();
	}

	public void clear(View view) {

		ed_professor.setEnabled(true);
		// ed_timeSlot.setEnabled(true);
		ed_addBook.setEnabled(true);
		actv_className.setText("");
		ed_professor.setText("");
		// ed_timeSlot.setText("");
		books.clear();
		adapter.notifyDataSetChanged();
		newentry = true;

	}

	public void addBook(View view) {

		if (ed_addBook.getText().length() != 0) {
			books.add(ed_addBook.getText().toString());
			ed_addBook.setText("");
			adapter.notifyDataSetChanged();

		}

	}

	public void submitClass(View view) {

		StudyUpTask sut = new StudyUpTask(this);

		String subjcts = "";
		for (String item : books) {
			subjcts += item;
			subjcts += ",";

		}
		if (auto_subjectname.getText().toString().length() == 0) {
			Toast.makeText(AddClassActivity.this, "Enter Subject Name",
					Toast.LENGTH_SHORT).show();
			return;

		} else {
			subjectname = auto_subjectname.getText().toString();

		}

		if (subjcts.length() != 0) {
			subjcts = subjcts.substring(0, subjcts.length() - 1);
			subjcts += ",";
		}
		submitcase = true;
		if (actv_className.getText().toString().length() == 0) {

			Toast.makeText(AddClassActivity.this, "EnterClassName",
					Toast.LENGTH_SHORT).show();
			return;
		} else if (ed_professor.getText().toString().length() == 0) {

			Toast.makeText(AddClassActivity.this, "Enter Professor Name",
					Toast.LENGTH_SHORT).show();
			return;
		} else if (subjcts.length() == 0) {
			subjcts = "";
		}

		for (int o = 0; o < start_t.length; o++) {

			if (o == start_t.length) {
				if (!(start_t[o].equals(""))) {
					adddays(o);

					time = time + start_t[o] + "-" + stop_t[o];
				} else {
					if (!(stop_t[o].equals(""))) {
						adddays(o);
					}

					time = time + start_t[o] + stop_t[o];
				}
			} else {
				if (!(start_t[o].equals(""))) {
					adddays(o);

					time = time + start_t[o] + "-" + stop_t[o] + ",";
				} else {
					if (!(stop_t[o].equals(""))) {
						adddays(o);

					}
					time = time + start_t[o] + stop_t[o] + ",";
				}
			}
		}

		user_id = prefObj.getPref(PreferenceHelper.Id, "");
		sut.execute(StudyUpTask.ADD_CLASS + "", actv_className.getText()
				.toString(), ed_professor.getText().toString(), time, subjcts,
				user_id, subject_id);

	}

	private void adddays(int o) {
		if (o == 0) {
			time = time + "Monday ";

		} else if (o == 1) {
			time += "  Tuesday ";

		} else if (o == 2) {
			time += "  Wednesday ";
		} else if (o == 3) {
			time += "  Thursday ";
		} else if (o == 4) {
			time += "  Friday ";
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		for (int i = 0; i < glob.starttime.size(); i++) {
			glob.starttime.put(i, "");
			glob.stoptime.put(i, "");
		}
		super.onBackPressed();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub

		IntentFilter filter = new IntentFilter(Utils.ACTION_GET_CLASS);
		registerReceiver(classListReceiver, filter);
		IntentFilter filter2 = new IntentFilter(Utils.ACTION_ADD_CLASS);
		registerReceiver(classListReceiver, filter2);
		IntentFilter filter3 = new IntentFilter(Utils.ACTION_GET_SUBJECT);
		registerReceiver(subjlist, filter3);
		super.onStart();

	}

	BroadcastReceiver subjlist = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			final Bundle extra = intent.getExtras();

			ClassInfo ci = (ClassInfo) extra.getSerializable("CLassObject");

			@SuppressWarnings("unchecked")
			ArrayList<SubjectInfo> newSubList = (ArrayList<SubjectInfo>) extra
					.getSerializable("subjectsList");
			subList = newSubList;
			subData = new ArrayList<String>();
			subID = new ArrayList<String>();
			for (SubjectInfo obj : subList) {
				subData.add(obj.getSub_name());
				subID.add(obj.getSub_id());

			}

			setsSuggestion();

			mDialog.cancel();

		}

	};

	private void setsSuggestion() {
		if (subList != null && subList.size() > 0) {

			// ClassAdapter myAdapter;
			// myAdapter=new ClassAdapter(this,
			// android.R.layout.simple_dropdown_item_1line,
			// (ArrayList)classList);
			// actv_className.setAdapter(myAdapter);
			// (this,android.R.id.text1, classList);
			final ArrayAdapter<String> aut_sub = new ArrayAdapter<String>(this,
					R.layout.auto, R.id.text1, subData);
			auto_subjectname.setAdapter(aut_sub);

			auto_subjectname
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@SuppressLint("NewApi")
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int position, long id) {
							user_id = subData.get(position);
							subject_id = subID.get(position);
							aut_sub.notifyDataSetChanged();

						}
					});
		}

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub

		unregisterReceiver(classListReceiver);
		unregisterReceiver(subjlist);

		super.onStop();
	}

	public void OnTimeslotclick(View v) {

		Intent i = new Intent(AddClassActivity.this, timeslot.class);
		startActivity(i);

	}

	public void OnDeasLineClick(View v) {

		Intent i = new Intent(AddClassActivity.this, DeadlinesActivity.class);
		startActivity(i);
		this.finish();
	}

	public void OnMyClassClick(View v) {

		Intent i = new Intent(AddClassActivity.this, MyClassesActivity.class);
		startActivity(i);
		this.finish();
	}

	public void setSuggestion() {
		if (classList != null && classList.size() > 0) {

			// ClassAdapter myAdapter;
			// myAdapter=new ClassAdapter(this,
			// android.R.layout.simple_dropdown_item_1line,
			// (ArrayList)classList);
			// actv_className.setAdapter(myAdapter);
			// (this,android.R.id.text1, classList);
			ArrayAdapter<String> tvadapter = new ArrayAdapter<String>(this,
					R.layout.auto, R.id.text1, classData);

			actv_className.setAdapter(tvadapter);

			actv_className
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@SuppressLint("NewApi")
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int position, long id) {

							class_id = classID.get(position);
							String abc = (String) arg0
									.getItemAtPosition(position);

							for (ClassInfo ci : classList) {
								if (ci.getClassName().equals(abc)) {

									ed_professor.setText(ci.getProfessor_name());
									books.clear();
									for (String book : ci.getBooks()) {
										books.add(book);
									}
									adapter.notifyDataSetChanged();

									newentry = false;
								}
							}

						}
					});
		}

	}

	private BroadcastReceiver classListReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			try {

				final Bundle extra = intent.getExtras();

				if (submitcase == true) {
					ClassInfo ci = (ClassInfo) extra
							.getSerializable("CLassObject");
					if (ci == null) {
						Toast.makeText(AddClassActivity.this, "Already Added",
								Toast.LENGTH_SHORT).show();

					} else {
						actv_className.setText("");
						ed_professor.setText("");
						auto_subjectname.setText("");
						start_t = day2;
						stop_t = day2;
						checker = true;
						books.clear();
						adapter.notifyDataSetChanged();
						Intent i = new Intent(AddClassActivity.this,
								MyClassActivity.class);
						i.putExtra("ci", ci);
						startActivity(i);

					}

					submitcase = false;
				} else {
					@SuppressWarnings("unchecked")
					ArrayList<ClassInfo> newClassList = (ArrayList<ClassInfo>) extra
							.getSerializable("CLassList");
					classList = newClassList;
					classData = new ArrayList<String>();
					classID = new ArrayList<String>();
					for (ClassInfo obj : classList) {
						classData.add(obj.getClassName());
						classID.add(obj.getClassId());
						Log.e("MySpecial Debugger", obj.getClassName());

					}

					setSuggestion();

					mDialog.cancel();

				}
			} catch (Exception e) {

				Log.d("asd", "asd");

			}

		}
	};

	public void onCancel() {

	}

}
