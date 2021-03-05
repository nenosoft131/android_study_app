package com.cyberdesignz.studyup.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyberdesignz.studyup.R;
import com.cyberdesignz.studyup.info.mybuddiesinfo;
import com.cyberdesignz.studyup.utils.ImageLoader;
import com.cyberdesignz.studyup.utils.Utils;

public class buddieslistadapter extends ArrayAdapter<mybuddiesinfo> {
	private ArrayList<mybuddiesinfo> buddylist;
	private mybuddiesinfo currentbuddy;
	private Context currentContext;
	Utils util;
	ImageLoader imageloader;
	String image;

	public buddieslistadapter(Context baseContext, int mybuddies,
			ArrayList<mybuddiesinfo> buddylist) {
		super(baseContext, mybuddies, buddylist);
		this.buddylist = buddylist;
		this.currentContext = baseContext;
		this.imageloader = new ImageLoader(baseContext, R.drawable.picture_man);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		buddyHolder buddysholder = null;
		if (buddylist == null || (position + 1) > buddylist.size()) //
			return convertView;

		currentbuddy = buddylist.get(position);
		String s = currentbuddy.getImage();
		image = util.IMAGE_SERVER + s;

		if (convertView == null) {

			LayoutInflater iv = (LayoutInflater) currentContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = iv.inflate(R.layout.buddylayout, null);
			buddysholder = new buddyHolder();
			buddysholder.user_name = (TextView) convertView
					.findViewById(R.id.tv_name);
			buddysholder.birthday = (TextView) convertView
					.findViewById(R.id.buddytextview);

			buddysholder.image = (ImageView) convertView
					.findViewById(R.id.buddyimage);

			convertView.setTag(buddysholder);

		} else {

			buddysholder = (buddyHolder) convertView.getTag();
		}
		String name = currentbuddy.getUser_name();
		buddysholder.user_name.setText(name);
		buddysholder.birthday.setText("Current Status : "
				+ currentbuddy.getStatus());
		buddysholder.image.setTag(imageloader);
		imageloader.displayImage(image, buddysholder.image);

		return convertView;
	}

	public class buddyHolder {

		TextView user_name;
		TextView birthday;
		ImageView image;

	}

}
