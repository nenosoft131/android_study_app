package com.cyberdesignz.studyup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import com.cyberdesignz.studyup.adapter.MyClasses;
import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.info.ClassInfo;
import com.cyberdesignz.studyup.io.StudyUpTask;
import com.cyberdesignz.studyup.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MyClassesActivity extends Activity {
    private List<ClassInfo> classList;
    private List<String> classData;
    ListView myClasses;
    ProgressDialog mDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.myclasses_layout);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        myClasses = (ListView) findViewById(R.id.lv_classes);
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Please wait...");
        mDialog.setCancelable(false);
        mDialog.show();

        myClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ClassInfo ci = (ClassInfo) arg0.getItemAtPosition(arg2);
                Intent i = new Intent(MyClassesActivity.this,
                        MyClassActivity.class);
                i.putExtra("ci", ci);
                startActivity(i);
            }
        });

        PreferenceHelper prefObj = new PreferenceHelper(this,
                PreferenceHelper.CurrentUser);
        String user_id = prefObj.getPref(PreferenceHelper.Id, "");

        new StudyUpTask(this).execute(StudyUpTask.GET_CLASS_BY_USER_ID + "",
                user_id);
    }

    public void OnAddClassClick(View v) {

        Intent i = new Intent(MyClassesActivity.this, AddClassActivity.class);
        startActivity(i);
        this.finish();
    }

    public void OnDeasLineClick(View v) {

        Intent i = new Intent(MyClassesActivity.this, DeadlinesActivity.class);
        startActivity(i);
        this.finish();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub

        IntentFilter filter = new IntentFilter(Utils.ACTION_GET_CLASS_BYID);
        registerReceiver(classListReceiver, filter);
        super.onStart();

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub

        unregisterReceiver(classListReceiver);
        super.onStop();
    }

    public void emailView() {

        if (classList != null && classList.size() > 0) {
            MyClasses classesAdapter = new MyClasses(this,
                    R.layout.myclasses_layout, (ArrayList<ClassInfo>) classList);
            myClasses.setAdapter(classesAdapter);

        }

    }

    private BroadcastReceiver classListReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            try {

                final Bundle extra = intent.getExtras();
                @SuppressWarnings("unchecked")
                ArrayList<ClassInfo> newClassList = (ArrayList<ClassInfo>) extra
                        .getSerializable("classList");
                classList = newClassList;
                classData = new ArrayList<String>();
                for (ClassInfo obj : classList) {
                    classData.add(obj.getClassName());

                }

                emailView();

                mDialog.cancel();

            } catch (Exception e) {

                Log.d("asd", "asd");

            }

        }
    };
}
