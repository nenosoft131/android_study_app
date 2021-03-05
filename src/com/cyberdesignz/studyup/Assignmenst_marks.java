package com.cyberdesignz.studyup;

import java.util.ArrayList;

import com.cyberdesignz.studyup.adapter.markslist_adapter;
import com.cyberdesignz.studyup.info.ExamInfo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

public class Assignmenst_marks extends Activity {
    ArrayList<ExamInfo> testarray = new ArrayList<ExamInfo>();
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_marks_layout);
        listview = (ListView) findViewById(R.id.list_layout);
        this.testarray = Exam_Marks.assigarray;
        init();

    }

    private void init() {

        if (this.testarray.isEmpty()) {
            Toast.makeText(Assignmenst_marks.this, "No Record Found",
                    Toast.LENGTH_SHORT).show();
        } else {
            markslist_adapter maksadap = new markslist_adapter(
                    Assignmenst_marks.this, R.layout.exam_marks_layout,
                    this.testarray);
            listview.setAdapter(maksadap);

        }
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        testarray.clear();
    }
}
