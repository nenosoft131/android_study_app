package com.cyberdesignz.studyup.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cyberdesignz.studyup.R;
import com.cyberdesignz.studyup.info.Deadlineinfo;

public class Deadlinelistadapter extends ArrayAdapter<Deadlineinfo> {

    private ArrayList<Deadlineinfo> deadlinelist;
    private Deadlineinfo currentdeadline;
    private Context currentContext;

    public Deadlinelistadapter(Context context, int textViewResourceId,
                               ArrayList<Deadlineinfo> deadline) {
        super(context, textViewResourceId, deadline);
        // TODO Auto-generated constructor stub
        this.deadlinelist = deadline;
        this.currentContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final int no = position;
        Deadlineholder deadlineholder = null;
        if (deadlinelist == null || (position + 1) > deadlinelist.size()) //
            return convertView;

        currentdeadline = deadlinelist.get(position);

        if (convertView == null) {

            LayoutInflater iv = (LayoutInflater) currentContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = iv.inflate(R.layout.deadline_layout, null);
            deadlineholder = new Deadlineholder();
            deadlineholder.name = (TextView) convertView
                    .findViewById(R.id.dl_name);
            deadlineholder.time = (TextView) convertView
                    .findViewById(R.id.dl_date);

            convertView.setTag(deadlineholder);

        } else {

            deadlineholder = (Deadlineholder) convertView.getTag();
        }

        deadlineholder.name.setText(currentdeadline.getDeadline_name());
        deadlineholder.time.setText(currentdeadline.getDeadline_time());

        return convertView;
    }

    public class Deadlineholder {

        TextView name;
        TextView time;

    }

}
