package com.cyberdesignz.studyup;

import java.util.ArrayList;

import com.cyberdesignz.studyup.info.ExamInfo;
import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AddMarks extends TabActivity {

    public static ArrayList<ExamInfo> exams;
    public static String class_id;
    global glob;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        class_id = getIntent().getExtras().getString("class_id");
        glob = ((global) getApplication());
        exam();

    }

    public void fin() {
        finish();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub

        finish();
        super.onPause();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub

        super.onStop();

    }

    private void exam() {
        setContentView(R.layout.addmarks);
        glob.update();
        TabHost tab = getTabHost();
        tab.setBackgroundColor(Color.BLUE);
        // tab.getTabWidget().getChildAt(1).setBackgroundColor(Color.RED);
        TabSpec exam = tab.newTabSpec("Exam");
        exam.setIndicator("Exam");
        Intent si = new Intent(AddMarks.this, Exam_Marks.class);
        exam.setContent(si);

        TabSpec test = tab.newTabSpec("Test");
        test.setIndicator("Test");
        Intent pi = new Intent(AddMarks.this, Test_Marks.class);
        test.setContent(pi);

        TabSpec Assig = tab.newTabSpec("Assignment");
        Assig.setIndicator("Assignment");
        Intent ai = new Intent(AddMarks.this, Assignmenst_marks.class);
        Assig.setContent(ai);

        TabSpec quiz = tab.newTabSpec("Quiz");
        quiz.setIndicator("Quizees");
        Intent qi = new Intent(AddMarks.this, quiz_marks.class);
        quiz.setContent(qi);

        tab.addTab(exam);
        tab.addTab(test);
        tab.addTab(Assig);
        tab.addTab(quiz);
    }

}
