package com.cyberdesignz.studyup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;

import com.cyberdesignz.studyup.adapter.timeslotsAdapter;
import com.cyberdesignz.studyup.utils.DateTimePicker;
import com.cyberdesignz.studyup.utils.Utils;
import com.cyberdesignz.studyup.utils.DateTimePicker.ICustomDateTimeListener;

public class timepickerclass extends Activity implements
        ICustomDateTimeListener {
    DateTimePicker dateTimePicker;
    boolean starttimecheker;
    public static boolean cc = false;
    int postion;
    global app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        app = (global) getApplication();
        postion = getIntent().getExtras().getInt("pos");
        starttimecheker = getIntent().getExtras().getBoolean("check");
        onFocusChange(postion);
        cc = true;

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }

    public void onSet(Calendar calendarSelected, Date dateSelected, int year,
                      String monthFullName, String monthShortName, int monthNumber,
                      int date, String weekDayFullName, String weekDayShortName,
                      int hour24, int hour12, int min, int sec, String AM_PM) {

        if (starttimecheker) {
            //app.starttime.add(postion,
            //(String) DateFormat.format("hh:mm", dateSelected));

        } else {
            //app.stoptime.add(postion,
            //	(String) DateFormat.format("hh:mm", dateSelected));

        }

        Intent intent = new Intent(Utils.ACTION_getlist);

        this.sendBroadcast(intent);
        finish();

    }

    public void onCancel() {
        // TODO Auto-generated method stub

    }

    public void onFocusChange(int postion) {

        dateTimePicker = new DateTimePicker(timepickerclass.this, this);
        dateTimePicker.set24HourFormat(true);
        dateTimePicker.showDialog();
    }

}
