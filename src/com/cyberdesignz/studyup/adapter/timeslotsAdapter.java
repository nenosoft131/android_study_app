package com.cyberdesignz.studyup.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cyberdesignz.studyup.AddClassActivity;
import com.cyberdesignz.studyup.R;
import com.cyberdesignz.studyup.global;
import com.cyberdesignz.studyup.timepickerclass;
import com.cyberdesignz.studyup.utils.DateTimePicker;
import com.cyberdesignz.studyup.utils.Utils;
import com.cyberdesignz.studyup.utils.DateTimePicker.ICustomDateTimeListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class timeslotsAdapter extends ArrayAdapter<String> implements
        ICustomDateTimeListener {

    Context currentContext;
    String timeslot[];
    DateTimePicker dateTimePicker;
    boolean arraycheck = false;
    global app;
    String days[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
            "Saturday", "Sunday"};
    Map<Integer, String> items;
    Map<Integer, String> enditems;

    public timeslotsAdapter(Context context, int textViewResourceId,
                            String[] Days, Map<Integer, String> items,
                            Map<Integer, String> enditems) {
        super(context, textViewResourceId, Days);
        // TODO Auto-generated constructor stub
        currentContext = context;
        IntentFilter filter = new IntentFilter(Utils.ACTION_getlist);
        currentContext.registerReceiver(listreciver, filter);
        this.items = items;
        this.enditems = enditems;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        timeslotholder ts_holder = null;
        final int pos = position;
        if (convertView == null) {

            LayoutInflater iv = (LayoutInflater) currentContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = iv.inflate(R.layout.timeslot, null);
            ts_holder = new timeslotholder();
            ts_holder.days_name = (TextView) convertView
                    .findViewById(R.id.txt_dayname);
            ts_holder.start_time = (TextView) convertView
                    .findViewById(R.id.txt_starttime);
            ts_holder.stop_time = (TextView) convertView
                    .findViewById(R.id.txt_stoptime);
            ts_holder.start_time_button = (Button) convertView
                    .findViewById(R.id.btn_start_time);
            ts_holder.stop_time_button = (Button) convertView
                    .findViewById(R.id.btn_end_time);

            ts_holder.start_time_button.setTag(position);
            ts_holder.stop_time_button.setTag(position);

            convertView.setTag(ts_holder);

        } else {

            ts_holder = (timeslotholder) convertView.getTag();
        }

        ts_holder.days_name.setText(days[position]);
        ts_holder.start_time.setText(this.items.get(pos));
        ts_holder.stop_time.setText(this.enditems.get(pos));

        return convertView;
    }

    public class timeslotholder {

        TextView days_name;
        TextView start_time;
        TextView stop_time;
        Button start_time_button;
        Button stop_time_button;

    }

    public void onSet(Calendar calendarSelected, Date dateSelected, int year,
                      String monthFullName, String monthShortName, int monthNumber,
                      int date, String weekDayFullName, String weekDayShortName,
                      int hour24, int hour12, int min, int sec, String AM_PM) {
        // TODO Auto-generated method stub

    }

    public void onCancel() {
        // TODO Auto-generated method stub

    }

    BroadcastReceiver listreciver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            System.out.println("");
            notifyDataSetChanged();

        }

    };

}
