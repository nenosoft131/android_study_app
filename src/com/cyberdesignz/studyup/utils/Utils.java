package com.cyberdesignz.studyup.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;

import android.util.Base64;
import android.util.Log;

public class Utils {

	public static String TAG = "Utils";

	public static final String ACTION_GET_GROUPS = "action_get_groups";
	public static final String ACTION_GET_TASKS = "action_get_task";
	public static final String ACTION_GET_EVENTS = "action_get_events";
	public static final String ACTION_GET_USERINFO = "action_get_userInfo";
	public static final String ACTION_POST = "action_post";
	public static final String ACTION_GET_POST = "action_get_post";
	public static final String ACTION_GET_COMMENT = "action_get_comments";
	public static final String ACTION_GET_CONTACT = "action_get_contacts";
	public static final String ACTION_GET_PERSONTAKETASK = "action_get_persontaketask";
	public static final String ACTION_GET_GROUPID = "action_get_groupid";
	public static final String ACTION_GET_FBOOKUSER = "action_get_fbuser";
	public static final String ACTION_GET_CLASSNOTES = "action_get_classnotes";
	public static final String ACTION_GET_CLASS_BYID = "action_get_classbyid";

	// Study up
	public static final String ACTION_GET_CLASS = "action_get_class";
	public static final String ACTION_GET_SUBJECT = "action_get_subjet";
	public static final String ACTION_GET_NOTE = "action_get_note";
	public static final String ACTION_ADD_CLASS = "action_add_class";
	public static final String ACTION_GET_EXAM = "action_get_exam";
	public static final String ACTION_NOTIFY_ERROR = "action_notify_error";

	public static final String ACTION_GET_FEEDS = "action_get_feeds";
	public static final String ACTION_POST_COMMENTS = "action_post_comments";
	public static final String ACTION_GET_COMMENTS = "action_get_comments";
	public static final String ACTION_GET_DEADLINES = "action_get_deadlines";
	public static final String ACTION_GET_LIKE = "action_get_likeist";
	public static final String ACTION_GET_LIKECOMMENTS = "action_get_likecomments";
	public static final String ACTION_GET_buddies = "action_get_buddies";
	public static final String ACTION_GET_INVITATION = "action_get_invitaion";
	public static final String ACTION_ADD_FRIEND = "action_get_invitaion";
	public static final String ACTION_SEARCH_FRIENDS = "action_search_friends";
	public static final String ACTION_getlist = "action_get_list";

	public static final String SERVER = "http://www.studyup.com/srv/webservice.php";
	public static final String AUDIO_SERVER = "http://www.studyup.com/srv/notes.audios/";
	public static final String IMAGE_SERVER = "http://www.studyup.com/srv/user.images/";
	public static final String IMAGE_SERVER_NOTES = "http://www.studyup.com/srv/notes.images/";

	public static final String CACHE_DIR_NAME = "__vimeo_v_cache"; // Why we use

	// this???

	public enum VideoQuality {
		MOBILE, SD, HD
	}

	;// ?????????????????

	public static boolean isEmailValid(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static File createCacheDir(Context context, String dirName) {
		File preparedDir;
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
				.getExternalStorageState())) {
			preparedDir = context.getDir(dirName /*
												 * +
												 * UUID.randomUUID().toString()
												 */, Context.MODE_PRIVATE);
			Log.i(TAG,
					"Cache dir initialized at SD card "
							+ preparedDir.getAbsolutePath());
		} else {
			preparedDir = context.getCacheDir();
			Log.i(TAG,
					"Cache dir initialized at phone storage "
							+ preparedDir.getAbsolutePath());
		}
		if (!preparedDir.exists()) {
			Log.i(TAG, "Cache dir not existed, creating");
			preparedDir.mkdirs();
		}
		return preparedDir;
	}

	public static void copyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	// bitmap to Base64 String

	public String bitmapToBase64(Bitmap bitmap) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
		byte[] image = stream.toByteArray();
		// System.out.println("byte array:"+image);
		String img_str = Base64.encodeToString(image, 0);
		return img_str;

	}

}
