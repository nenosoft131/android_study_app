package com.cyberdesignz.studyup.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.cyberdesignz.studyup.LikeNote;
import com.cyberdesignz.studyup.PostComments;
import com.cyberdesignz.studyup.Profile;
import com.cyberdesignz.studyup.R;
import com.cyberdesignz.studyup.helper.Helper;
import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.info.FeedsInfo;
import com.cyberdesignz.studyup.io.StudyUpTask;
import com.cyberdesignz.studyup.utils.ImageLoader;
import com.cyberdesignz.studyup.utils.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedsListAdapter extends ArrayAdapter<FeedsInfo> {

	private ArrayList<FeedsInfo> feedslist;
	private FeedsInfo currentfeed;
	private Context currentContext;
	private PreferenceHelper prefObj;
	int timearray[] = new int[3];
	Utils util;
	public static boolean check_approved = false;

	Bitmap myBitmap;
	String image;
	long Hours_difference, minutes_difference, days_difference;
	static int SECONDS_PER_DAY = 86400;
	String time1;
	Helper helper;
	StudyUpTask task;
	ImageLoader imageloader;
	String user_id;

	public FeedsListAdapter(Context context, int rid,
			ArrayList<FeedsInfo> feedlist) {
		// TODO Auto-generated constructor stub
		super(context, rid, feedlist);
		this.feedslist = feedlist;
		this.currentContext = context;
		helper = new Helper();
		task = new StudyUpTask(currentContext);
		prefObj = new PreferenceHelper(currentContext,
				PreferenceHelper.CurrentUser);
		user_id = prefObj.getPref(PreferenceHelper.Id, "");
		this.imageloader = new ImageLoader(context, R.drawable.picture_man);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final int no = feedslist.size() - position - 1;
		FeedsHolder feedsholder = null;
		if (feedslist == null || (position + 1) > feedslist.size()) //
			return convertView;

		currentfeed = feedslist.get(no);
		String s = currentfeed.getImage();
		image = util.IMAGE_SERVER + s;

		if (convertView == null) {

			LayoutInflater iv = (LayoutInflater) currentContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = iv.inflate(R.layout.feeds_layout_new, null);
			feedsholder = new FeedsHolder();
			feedsholder.user_name = (TextView) convertView
					.findViewById(R.id.feed_user_name);
			feedsholder.date = (TextView) convertView
					.findViewById(R.id.feed_date);

			feedsholder.subject = (TextView) convertView
					.findViewById(R.id.feed_subject);
			feedsholder.submit = (Button) convertView
					.findViewById(R.id.feed_submit);
			feedsholder.image = (ImageView) convertView
					.findViewById(R.id.feed_image);
			feedsholder.like_count = (TextView) convertView
					.findViewById(R.id.tv_count_like);

			convertView.setTag(feedsholder);

		} else {

			feedsholder = (FeedsHolder) convertView.getTag();
		}

		if (currentfeed.getUsername() != null) {

			feedsholder.user_name.setText(currentfeed.getUsername()
					.toUpperCase());
			try {
				time1 = currentfeed.getFeed_date();
				time1 = time1.replace('-', '/');
				getdifference();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String days = Long.toString(days_difference);
			days = days.replace("-", "");
			String Hours = Long.toString(Hours_difference);
			Hours = Hours.replace("-", "");
			String minutes = Long.toString(minutes_difference);
			minutes = minutes.replace("-", "");
			if (days.equals(0)) {
				days = "";
			} else {
				days = days + "days ";
			}
			if (Hours.equals(0)) {
				Hours = "";
			} else {
				Hours = Hours + "Hours ";
			}
			feedsholder.date.setText(days + " ago");
			String c = currentfeed.getFeed_type();

			feedsholder.subject.setText(currentfeed.getFeed_type()
					+ " " + currentfeed.getNotedata_topic() + " in "
					+ currentfeed.getNotedata_subject());
			String chck = currentfeed.getApproves_count();

			feedsholder.like_count.setText(currentfeed.getApproves_count()
					+ " Like");

			feedsholder.image.setTag(imageloader);
			imageloader.displayImage(image, feedsholder.image);

			final String feedId = currentfeed.getNotedata_id();
			feedsholder.image.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent i = new Intent(currentContext, Profile.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.putExtra("list", feedslist);
					i.putExtra("position", no);
					currentContext.startActivity(i);
				}
			});
			feedsholder.user_name.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent i = new Intent(currentContext, Profile.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.putExtra("list", feedslist);
					i.putExtra("position", no);
					currentContext.startActivity(i);
				}
			});

			feedsholder.submit.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(currentContext, PostComments.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.putExtra("feed_id", feedId);
					currentContext.startActivity(i);

				}
			});

			feedsholder.like_count.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(currentContext, LikeNote.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					String noteId = currentfeed.getNotedata_id();
					intent.putExtra("notes_Ids", currentfeed.getNotedata_id());
					currentContext.startActivity(intent);

				}
			});
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

	@SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
	private int[] time(long seconds) {
		// TODO Auto-generated method stub

		int day = (int) TimeUnit.SECONDS.toDays(seconds);
		long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
		long minute = TimeUnit.SECONDS.toMinutes(seconds)
				- (TimeUnit.SECONDS.toHours(seconds) * 60);

		int[] ints = { day, (int) hours, (int) minute };
		return ints;

	}

	private void picture() {
		// TODO Auto-generated method stub
		try {
			myBitmap = BitmapFactory.decodeStream((InputStream) new URL(image)
					.getContent());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class FeedsHolder {

		TextView user_name;
		TextView date;

		TextView like_count;

		TextView subject;
		Button submit;
		ImageView image;

	}

}
