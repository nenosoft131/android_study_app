package com.cyberdesignz.studyup.adapter;

import com.cyberdesignz.studyup.R;
import com.cyberdesignz.studyup.adapter.buddieslistadapter.buddyHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class daystimeslotadapter extends ArrayAdapter<String> {

    Context currentContext;
    String timeslot[];

    String days[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
            "Saturday", "Sunday"};

    public daystimeslotadapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        // TODO Auto-generated constructor stub
        currentContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        buddyHolder buddysholder = null;

        if (convertView == null) {

            LayoutInflater iv = (LayoutInflater) currentContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = iv.inflate(R.layout.dayslayout, null);
            buddysholder = new buddyHolder();
            buddysholder.days = (TextView) convertView
                    .findViewById(R.id.tv_days);
            buddysholder.time = (TextView) convertView
                    .findViewById(R.id.tv_time);

            convertView.setTag(buddysholder);

        } else {

            buddysholder = (buddyHolder) convertView.getTag();
        }

        buddysholder.days.setText(days[position]);
        buddysholder.time.setText(timeslot[position]);

        return convertView;
    }

    public class buddyHolder {

        TextView days;
        TextView time;

    }
}
