package com.cyberdesignz.studyup.adapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyberdesignz.studyup.R;
import com.cyberdesignz.studyup.info.CommentsInfo;
import com.cyberdesignz.studyup.utils.ImageLoader;
import com.cyberdesignz.studyup.utils.Utils;

public class CommentsAdapter extends ArrayAdapter<CommentsInfo> {
    private ArrayList<CommentsInfo> commentlist;
    private CommentsInfo current_comment;
    Utils util;
    ImageLoader imageloader;
    String image;
    String time1;
    static int SECONDS_PER_DAY = 86400;
    private Context currentContext;
    long Hours_difference, minutes_difference, days_difference;

    public CommentsAdapter(Context context, int textViewResourceId,
                           ArrayList<CommentsInfo> commentslist) {

        // TODO Auto-generated constructor stub
        super(context, textViewResourceId, commentslist);
        this.commentlist = commentslist;
        this.currentContext = context;
        this.imageloader = new ImageLoader(context, R.drawable.picture_man);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (commentlist == null || (position + 1) > commentlist.size()) //
            return convertView;

        current_comment = commentlist.get(position);
        String s = current_comment.getComment();
        hold holder;

        if (convertView == null) {

            LayoutInflater iv = (LayoutInflater) currentContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = iv.inflate(R.layout.comments_layout, null);
            holder = new hold();
            holder.comments = (TextView) convertView
                    .findViewById(R.id.comment_text);
            holder.img_view = (ImageView) convertView
                    .findViewById(R.id.iv_comments_image);
            holder.tv_email = (TextView) convertView
                    .findViewById(R.id.tv_comments_email);
            holder.tv_name = (TextView) convertView
                    .findViewById(R.id.tv_comments_name);

            convertView.setTag(holder);

        } else {

            holder = (hold) convertView.getTag();
        }
        if (current_comment.getComment() != null) {
            holder.comments.setText(current_comment.getComment());

        }
        if (current_comment.getCommentUser_name() != null) {
            holder.tv_name.setText(current_comment.getCommentUser_name());
        }
        if (current_comment.getDate_commented() != null) {

            String days = Long.toString(days_difference);
            days = days.replace("-", "");
            time1 = current_comment.getDate_commented();

            holder.tv_email.setText(current_comment.getDate_commented());
        }

        if (current_comment.getCommentUser_image() != null) {

            holder.img_view.setTag(imageloader);
            String imageName = current_comment.getCommentUser_image();
            image = util.IMAGE_SERVER + imageName;
            imageloader.displayImage(image, holder.img_view);
        }

        return convertView;

    }

    private void getdifference() throws ParseException {
        // TODO Auto-generated method stub

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String time2 = dateFormat.format(date);
        System.out.println(dateFormat.format(date));

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date1 = format.parse(time2);
        Date date2 = format.parse(time1);
        Hours_difference = date2.getHours() - date1.getHours();
        minutes_difference = date2.getMinutes() - date1.getMinutes();
        days_difference = daysBetween(date1, date2);

        System.out.println("");

        System.out.println("");
    }

    public static long daysBetween(Date firstDate, Date secondDate) {

        // We only use the date part of the given dates
        long firstSeconds = truncateToDate(firstDate).getTime() / 1000;
        long secondSeconds = truncateToDate(secondDate).getTime() / 1000;

        // Just taking the difference of the millis.
        // These will not be exactly multiples of 24*60*60, since there
        // might be daylight saving time somewhere inbetween. However, we can
        // say that by adding a half day and rounding down afterwards, we always
        // get the full days.
        long difference = secondSeconds - firstSeconds;

        // Adding half a day
        if (difference >= 0) {
            difference += SECONDS_PER_DAY / 2; // plus half a day in seconds
        } else {
            difference -= SECONDS_PER_DAY / 2; // minus half a day in seconds
        }
        // Rounding down to days
        difference /= SECONDS_PER_DAY;

        return difference;
    }

    /**
     * Truncates a date to the date part alone.
     */
    @SuppressWarnings("deprecation")
    public static Date truncateToDate(Date d) {
        if (d instanceof java.sql.Date) {
            return d; // java.sql.Date is already truncated to date. And raises
            // an
            // Exception if we try to set hours, minutes or seconds.
        }
        d = (Date) d.clone();
        d.setHours(0);
        d.setMinutes(0);
        d.setSeconds(0);
        d.setTime(((d.getTime() / 1000) * 1000));
        return d;
    }

    @SuppressLint({"NewApi", "NewApi", "NewApi", "NewApi"})
    private int[] time(long seconds) {
        // TODO Auto-generated method stub

        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds)
                - (TimeUnit.SECONDS.toHours(seconds) * 60);

        int[] ints = {day, (int) hours, (int) minute};
        return ints;

    }

    class hold {
        TextView comments;
        ImageView img_view;
        TextView tv_email;
        TextView tv_name;

    }

}
