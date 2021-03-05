package com.cyberdesignz.studyup.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyberdesignz.studyup.R;
import com.cyberdesignz.studyup.addfriend;
import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.info.SearchfriendsInfo;
import com.cyberdesignz.studyup.utils.ImageLoader;
import com.cyberdesignz.studyup.utils.Utils;

public class searchfriendsadapter extends ArrayAdapter<SearchfriendsInfo> {
    private ArrayList<SearchfriendsInfo> buddylist;
    private SearchfriendsInfo currentbuddy;
    private Context currentContext;
    private PreferenceHelper prefObj;
    Utils util;
    ImageLoader imageloader;
    String image;

    public searchfriendsadapter(Context baseContext, int mybuddies,
                                ArrayList<SearchfriendsInfo> buddylist) {
        super(baseContext, mybuddies, buddylist);
        this.buddylist = buddylist;
        this.currentContext = baseContext;
        this.imageloader = new ImageLoader(baseContext, R.drawable.picture_man);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final int no = position;
        buddyHolder buddysholder = null;
        if (buddylist == null || (position + 1) > buddylist.size()) //
            return convertView;

        currentbuddy = buddylist.get(position);
        String s = currentbuddy.getImage();
        image = util.IMAGE_SERVER + s;

        if (convertView == null) {

            LayoutInflater iv = (LayoutInflater) currentContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = iv.inflate(R.layout.searchfriendslayout, null);
            buddysholder = new buddyHolder();
            buddysholder.user_name = (TextView) convertView
                    .findViewById(R.id.buddytextview);
            buddysholder.DOB = (TextView) convertView
                    .findViewById(R.id.birthday);

            buddysholder.image = (ImageView) convertView
                    .findViewById(R.id.buddyimage);
            buddysholder.addfreind = (Button) convertView
                    .findViewById(R.id.add_friend);

            convertView.setTag(buddysholder);

        } else {

            buddysholder = (buddyHolder) convertView.getTag();
        }
        String name = currentbuddy.getUser_name();

        String status = currentbuddy.getIsMyFriend();
        if (status.equals("YES")) {
            buddysholder.addfreind.setVisibility(View.INVISIBLE);

        } else {

            buddysholder.addfreind.setVisibility(View.VISIBLE);
        }
        buddysholder.user_name.setText(name);
        buddysholder.DOB.setText(currentbuddy.getDOB());
        buddysholder.image.setTag(imageloader);
        imageloader.displayImage(image, buddysholder.image);
        buddysholder.addfreind.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub\
                Intent i = new Intent(currentContext, addfriend.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("friend", buddylist.get(no).getId().toString());
                currentContext.startActivity(i);

            }
        });

        return convertView;
    }

    public class buddyHolder {

        TextView user_name;
        TextView DOB;
        ImageView image;
        Button addfreind;

    }

}
