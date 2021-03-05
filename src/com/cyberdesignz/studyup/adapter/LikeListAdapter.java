package com.cyberdesignz.studyup.adapter;

import java.util.ArrayList;

import com.cyberdesignz.studyup.R;
import com.cyberdesignz.studyup.info.UserInfo;
import com.cyberdesignz.studyup.utils.ImageLoader;
import com.cyberdesignz.studyup.utils.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LikeListAdapter extends ArrayAdapter<UserInfo> {

    private ArrayList<UserInfo> userlist;
    private Context currentContext;
    private UserInfo user_info;
    ImageLoader imageloader;
    String image;
    Utils util;

    public LikeListAdapter(Context context, int textViewResourceId,
                           ArrayList<UserInfo> userlist) {

        // TODO Auto-generated constructor stub
        super(context, textViewResourceId, userlist);
        this.userlist = userlist;
        this.currentContext = context;

        this.imageloader = new ImageLoader(context, R.drawable.picture_man);

    }

/*	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.d("das", "asdd");
		Log.d("das", "asdd");
		Log.d("das", "asdd");
		Log.d("das", "asdd");
		
		
		
		return super.getView(position, convertView, parent);
	}*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final int no = position;
        LikeListHolder like_holder = null;

        if (userlist == null || (position + 1) > userlist.size()) //
            return convertView;

        user_info = userlist.get(position);
        if (convertView == null) {

            LayoutInflater iv = (LayoutInflater) currentContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = iv.inflate(R.layout.like_layout, null);
            like_holder = new LikeListHolder();
            like_holder.tv_likeName = (TextView) convertView
                    .findViewById(R.id.tv_name);
            like_holder.img_name_like = (ImageView) convertView.findViewById(R.id.iv_manLike);


            convertView.setTag(like_holder);

        } else {

            like_holder = (LikeListHolder) convertView.getTag();
        }
        if (user_info.getName() != null) {

            like_holder.tv_likeName.setText(user_info.getName());
        }
        if (user_info.getImageName() != null) {
            image = util.IMAGE_SERVER + user_info.getImageName();
            like_holder.img_name_like.setTag(imageloader);
            imageloader.displayImage(image, like_holder.img_name_like);

        }

        return convertView;
    }

    class LikeListHolder {

        TextView tv_likeName;
        ImageView img_name_like;

    }

}
