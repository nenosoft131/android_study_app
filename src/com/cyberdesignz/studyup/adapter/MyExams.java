/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberdesignz.studyup.adapter;

/**
 *
 * @author arshad
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cyberdesignz.studyup.AddMarks;
import com.cyberdesignz.studyup.FacebookActivity;
import com.cyberdesignz.studyup.MyClassActivity;
import com.cyberdesignz.studyup.MyExamActivity;
import com.cyberdesignz.studyup.R;
import com.cyberdesignz.studyup.UpdateExam;
import com.cyberdesignz.studyup.global;
import com.cyberdesignz.studyup.info.ExamInfo;

import java.util.ArrayList;

/**
 * @author arshad
 */
public class MyExams extends ArrayAdapter<ExamInfo> {
    public static ArrayList<ExamInfo> examlist;
    private Context currentContext;
    private ExamInfo current_exam;
    public static String name;

    public MyExams(Context context, int textViewResourceId,
                   ArrayList<ExamInfo> myExams) {

        // TODO Auto-generated constructor stub
        super(context, textViewResourceId, myExams);
        this.examlist = myExams;

        this.currentContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyExams.ViewHolder holder;
        final int no = position;
        current_exam = (ExamInfo) examlist.get(position);
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) currentContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.examlistviewlayout, null);
            holder = new MyExams.ViewHolder();
            holder.tv_name = (TextView) convertView
                    .findViewById(R.id.item_classname);
            holder.tv_professor = (TextView) convertView
                    .findViewById(R.id.item_professor);
            holder.tv_timeSlot = (TextView) convertView
                    .findViewById(R.id.item_timeSlot);
            holder.tv_marks = (TextView) convertView
                    .findViewById(R.id.txt_marks);
            holder.button = (Button) convertView.findViewById(R.id.btn_share);
            convertView.setOnClickListener(new OnClickListener() {

                public void onClick(View arg0) {

                    Intent intent1 = new Intent(currentContext,
                            UpdateExam.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent1.putExtra("pos", no);
                    currentContext.startActivity(intent1);
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (MyExams.ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(current_exam.getName());
        holder.tv_professor.setText(current_exam.getComments());
        holder.tv_marks.setText(current_exam.getMarks());
        holder.tv_timeSlot.setText(current_exam.getTime());
        holder.button.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                final String items[] = {"Facebook", "Archive"};

                AlertDialog.Builder ab = new AlertDialog.Builder(currentContext);
                ab.setTitle("Select Option");

                ab.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int choice) {
                        if (choice == 0) {
                            Intent i = new Intent(currentContext,
                                    FacebookActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra("subject", "Subject Name:	"
                                    + examlist.get(no).getName());
                            i.putExtra("detail", "Marks:	"
                                    + examlist.get(no).getMarks() + "	Time:	"
                                    + examlist.get(no).getTime());
                            currentContext.startActivity(i);

                        } else if (choice == 1) {

                        }
                    }
                });
                ab.show();

            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_marks;
        TextView tv_timeSlot;
        TextView tv_professor;
        Button button;

    }
}
