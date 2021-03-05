package com.cyberdesignz.studyup;

import java.util.ArrayList;

import com.cyberdesignz.studyup.adapter.MyExams;
import com.cyberdesignz.studyup.adapter.markslist_adapter;
import com.cyberdesignz.studyup.info.ExamInfo;
import com.cyberdesignz.studyup.io.StudyUpTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

public class quiz_marks extends Activity {
    ArrayList<ExamInfo> testarray = new ArrayList<ExamInfo>();
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_marks_layout);
        listview = (ListView) findViewById(R.id.list_layout);
        this.testarray = Exam_Marks.quizarray;
        init();

    }

    private void init() {

        if (this.testarray.isEmpty()) {
            Toast.makeText(quiz_marks.this, "No Record Found",
                    Toast.LENGTH_SHORT).show();
        } else {
            markslist_adapter maksadap = new markslist_adapter(quiz_marks.this,
                    R.layout.exam_marks_layout, this.testarray);
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
