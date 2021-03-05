/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberdesignz.studyup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.cyberdesignz.studyup.R;
import com.cyberdesignz.studyup.info.ClassInfo;

import java.util.ArrayList;

/**
 * @author arshad
 */
public class MyClasses extends ArrayAdapter<ClassInfo> {
    private ArrayList<ClassInfo> myClasses;
    private Context currentContext;
    private ClassInfo current_class;

    public MyClasses(Context context, int textViewResourceId,
                     ArrayList<ClassInfo> myCLasses) {

        // TODO Auto-generated constructor stub
        super(context, textViewResourceId, myCLasses);
        this.myClasses = myCLasses;

        this.currentContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        current_class = (ClassInfo) myClasses.get(position);
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) currentContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.myclass_node, null);
            holder = new ViewHolder();
            holder.tv_className = (TextView) convertView
                    .findViewById(R.id.item_classname);
            holder.tv_professor = (TextView) convertView
                    .findViewById(R.id.item_professor);
            holder.tv_timeSlot = (TextView) convertView
                    .findViewById(R.id.item_timeSlot);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_className.setText(current_class.getClassName());
        holder.tv_professor.setText(current_class.getProfessor_name());
        holder.tv_timeSlot.setText(current_class.getTime_slot());

        return convertView;
    }

    static class ViewHolder {
        TextView tv_className;
        TextView tv_professor;
        TextView tv_timeSlot;

    }
}
