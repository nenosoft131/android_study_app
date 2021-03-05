package com.cyberdesignz.studyup.adapter;

import java.util.ArrayList;

import com.cyberdesignz.studyup.R;
import com.cyberdesignz.studyup.global;
import com.cyberdesignz.studyup.info.classnotesInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class class_Notes_Adapter extends ArrayAdapter<classnotesInfo> {

    Context context;
    ArrayList<classnotesInfo> examlist;
    public String class_id;
    global glob;

    public class_Notes_Adapter(Context context, int textViewResourceId,
                               ArrayList<classnotesInfo> classList) {
        super(context, textViewResourceId, classList);
        // TODO Auto-generated constructor stub
        this.context = context;
        examlist = classList;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final int pos = position;
        if (examlist == null || (position + 1) > examlist.size()) //
            return convertView;

        final ListHolder holder;

        if (convertView == null) {

            LayoutInflater iv = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = iv.inflate(R.layout.class_notes_layout, null);
            holder = new ListHolder();
            holder.tv_Name = (TextView) convertView.findViewById(R.id.t_name);
            holder.tv_subject = (TextView) convertView
                    .findViewById(R.id.t_subj);
            holder.tv_time = (TextView) convertView.findViewById(R.id.t_time);

            convertView.setTag(holder);

        } else {

            holder = (ListHolder) convertView.getTag();
        }
        holder.tv_Name.setText(examlist.get(position).getClass_id());
        holder.tv_subject.setText(examlist.get(position).getSubject());
        holder.tv_time.setText(examlist.get(position).getDate_added());
        return convertView;

    }

    class ListHolder {

        TextView tv_Name;
        TextView tv_subject;
        TextView tv_time;

    }

}
