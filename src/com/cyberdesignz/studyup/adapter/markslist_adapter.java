package com.cyberdesignz.studyup.adapter;

import java.util.ArrayList;
import java.util.Currency;

import com.cyberdesignz.studyup.AddMarks;
import com.cyberdesignz.studyup.Exam_Marks;
import com.cyberdesignz.studyup.R;
import com.cyberdesignz.studyup.global;
import com.cyberdesignz.studyup.adapter.CommentsAdapter.hold;
import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.info.ExamInfo;
import com.cyberdesignz.studyup.io.StudyUpTask;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class markslist_adapter extends ArrayAdapter<ExamInfo> {

	Context context;
	ArrayList<ExamInfo> examlist;
	public String class_id;
	private PreferenceHelper prefObj;
	global glob;
	public static boolean test = false;

	public markslist_adapter(Context context, int textViewResourceId,
			ArrayList<ExamInfo> list) {
		super(context, textViewResourceId, list);
		// TODO Auto-generated constructor stub
		this.context = context;
		examlist = list;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final int pos = position;
		if (examlist == null || (position + 1) > examlist.size()) //
			return convertView;

		final ListHolder holder;

		if (convertView == null) {

			LayoutInflater iv = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = iv.inflate(R.layout.marks, null);
			holder = new ListHolder();
			holder.tv_likeName = (TextView) convertView
					.findViewById(R.id.text_name);
			holder.tv_class_name = (TextView) convertView
					.findViewById(R.id.text_classname);
			holder.marks = (EditText) convertView.findViewById(R.id.ed_marks);
			holder.submit = (Button) convertView.findViewById(R.id.submit);

			convertView.setTag(holder);

		} else {

			holder = (ListHolder) convertView.getTag();
		}
		holder.tv_class_name.setText(examlist.get(position).getName());
		holder.tv_likeName
				.setText("Marks:" + examlist.get(position).getMarks());
		holder.submit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				prefObj = new PreferenceHelper(context,
						PreferenceHelper.CurrentUser);
				String user_id = prefObj.getPref(PreferenceHelper.Id, "");
				new StudyUpTask(context).execute(StudyUpTask.UPDATE_EXAM + "",
						examlist.get(pos).getId().toString(), examlist.get(pos)
								.getName().toString(), examlist.get(pos)
								.getTime().toString(), holder.marks.getText()
								.toString(), examlist.get(pos).getType()
								.toString(), examlist.get(pos).getComments()
								.toString(), user_id);
				test = true;
				holder.marks.setText("");
				AddMarks m = new AddMarks();
				m.fin();

			}
		});
		convertView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder.marks.setVisibility(View.VISIBLE);
				holder.marks.requestFocus();
			}
		});

		return convertView;

	}

	class ListHolder {

		TextView tv_likeName;
		TextView tv_class_name;
		EditText marks;
		Button submit;

	}

}
