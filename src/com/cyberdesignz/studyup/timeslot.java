package com.cyberdesignz.studyup;

import java.util.Calendar;

import com.cyberdesignz.studyup.adapter.timeslotsAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;

public class timeslot extends Activity {

    static boolean check = false;
    ListView list;
    global glob;
    boolean check2 = false;
    private TimePicker timePicker1;
    static final int TIME_DIALOG_ID = 999;
    ArrayAdapter<String> data;
    private int hour;
    private int minute;
    Integer jk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday"};
        super.onCreate(savedInstanceState);
        glob = ((global) getApplication());
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.timeslot_layout);
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        list = (ListView) findViewById(R.id.listView_timeslots);
        data = new timeslotsAdapter(this, R.layout.timeslot_layout, days,
                glob.starttime, glob.stoptime);
        list.setAdapter(data);

    }

    public void OnSubmitClick(View v) {
        AddClassActivity ad = new AddClassActivity();
        check = true;
        finish();

    }

    public void starttime(View view) {
        Button b = (Button) view;
        jk = (Integer) b.getTag();
        check2 = true;
        showDialog(TIME_DIALOG_ID);

    }

    public void endtime(View view) {

        Button b = (Button) view;
        jk = (Integer) b.getTag();
        check2 = false;
        showDialog(TIME_DIALOG_ID);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                // set time picker as current time
                return new TimePickerDialog(this, timePickerListener, hour, minute,
                        false);

        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour,
                              int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;

            if (check2) {

                glob.starttime.put(jk, (new StringBuilder().append(pad(hour))
                        .append(":").append(pad(minute)).toString()));
                data.notifyDataSetChanged();
                String i = glob.starttime.get(jk);
            } else {
                glob.stoptime.put(jk, (new StringBuilder().append(pad(hour))
                        .append(":").append(pad(minute)).toString()));
                data.notifyDataSetChanged();
            }
        }

        private String pad(int c) {
            if (c >= 10)
                return String.valueOf(c);
            else
                return "0" + String.valueOf(c);
        }
    };
}
