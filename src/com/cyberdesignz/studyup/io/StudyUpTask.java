package com.cyberdesignz.studyup.io;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.cyberdesignz.studyup.AddMarks;
import com.cyberdesignz.studyup.AddNotes;
import com.cyberdesignz.studyup.PostComments;
import com.cyberdesignz.studyup.global;
import com.cyberdesignz.studyup.adapter.FeedsListAdapter;
import com.cyberdesignz.studyup.adapter.markslist_adapter;
import com.cyberdesignz.studyup.helper.PreferenceHelper;
import com.cyberdesignz.studyup.info.ClassInfo;
import com.cyberdesignz.studyup.info.CommentsInfo;
import com.cyberdesignz.studyup.info.Deadlineinfo;
import com.cyberdesignz.studyup.info.ExamInfo;
import com.cyberdesignz.studyup.info.FeedsInfo;
import com.cyberdesignz.studyup.info.NoteInfo;
import com.cyberdesignz.studyup.info.SearchfriendsInfo;
import com.cyberdesignz.studyup.info.SubjectInfo;
import com.cyberdesignz.studyup.info.UserInfo;
import com.cyberdesignz.studyup.info.classnotesInfo;
import com.cyberdesignz.studyup.info.mybuddiesinfo;
import com.cyberdesignz.studyup.utils.Helper;
import com.cyberdesignz.studyup.utils.JsonParser;
import com.cyberdesignz.studyup.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StudyUpTask extends AsyncTask<String, Object, Integer> {

	private String TAG = "AsyncTask";
	global glob;
	private Context context = null;

	private int ServiceAction;

	public static final int ADD_NOTE = 1;
	private PreferenceHelper prefObj;
	public static final int SIGN_UP = 2;
	public static final int GET_ID = 3;
	public static final int GET_CLASS = 4;
	public static final int GET_NOTES = 5;
	public static final int GET_FEEDS = 6;
	public static final int POST_COMMENTS = 7;
	public static final int GET_COMMENTS = 8;
	public static final int LIKE_COMMENT = 9;
	public static final int LIKE_LIST = 10;

	public static final int ADD_CLASS = 11;

	public static final int GET_CLASS_BY_USER_ID = 12;

	public static final int GET_EXAM = 13;

	public static final int ADD_EXAM = 14;
	public static final int GET_DEDLINE = 15;
	public static final int GET_BUDDIES = 16;
	public static final int SEARCH_FRIENDS = 17;
	public static final int INVITE_FRIENDS = 18;
	public static final int ADD_FRIENDS = 19;
	public static final int REMOVE_FRIENDS = 20;
	public static final int GET_SUBJECTS = 21;
	public static final int UPDATE_EXAM = 22;
	public static final int GET_CLASS_NOTES = 23;
	private boolean isJArray = false;

	private int _taskStatus = 0;

	JsonParser jsonParser;
	public Bitmap myBitmap;

	private String class_id;

	public static final String SERVER = Utils.SERVER;

	public static final String add_note = SERVER + "?action=addNotes";
	public static final String add_class = SERVER + "?action=addClass";

	public static final String get_id = SERVER + "?action=getUserByEmail";
	public static final String get_class = SERVER + "?action=getClass";
	public static final String get_subject = SERVER + "?action=getSubject";
	public static final String get_exam = SERVER + "?action=getExam";
	public static final String update_exam = SERVER + "?action=updateExam";
	public static final String add_exam = SERVER + "?action=addExam";
	public static final String get_deadline = SERVER + "?action=getDeadLine";

	public static final String get_class_by_user_id = SERVER
			+ "?action=getClass_userID";
	public static final String get_notes = SERVER + "?action=getNotes";
	public static final String get_feeds = SERVER + "?action=getMyFeeds";
	public static final String post_comments = SERVER
			+ "?action=addCommentToNote";
	public static final String get_comments = SERVER + "?action=getComments";
	public static final String like_comments = SERVER + "?action=approveNotes";
	public static final String like_list = SERVER + "?action=getNotesApproves";
	public static final String get_buddies = SERVER + "?action=getMyFriends";
	public static final String find_friends = SERVER + "?action=find_friend";
	public static final String add_friends = SERVER + "?action=add_friend";
	public static final String get_classnotes = SERVER
			+ "?action=getClassNotes";
	// public static final String reomve_friends = SERVER +
	// "?action=find_friend";
	public static final String invite_friends = SERVER
			+ "?action=friend_invite";
	ClassInfo classinfo;
	SubjectInfo subjectinfo;
	NoteInfo noteinfo;
	FeedsInfo feedsinfo;
	CommentsInfo commentinfo;
	UserInfo user_Info;
	String posts = null;
	Deadlineinfo deadlineinfo;
	ExamInfo examinfo;
	String Invitaionresponse;
	String addresponse;
	mybuddiesinfo buddiesinfo;
	classnotesInfo classnotesinfo;
	SearchfriendsInfo serachfriendsinfo;
	private List<UserInfo> userInfo = new ArrayList<UserInfo>();
	private List<classnotesInfo> classnotesInfo = new ArrayList<classnotesInfo>();
	private List<Deadlineinfo> deadlineInfo = new ArrayList<Deadlineinfo>();
	private List<ClassInfo> classInfo = new ArrayList<ClassInfo>();
	private List<SubjectInfo> SubjInfo = new ArrayList<SubjectInfo>();
	private List<NoteInfo> noteInfo = new ArrayList<NoteInfo>();
	private List<FeedsInfo> feedsInfo = new ArrayList<FeedsInfo>();
	private List<CommentsInfo> commentsInfo = new ArrayList<CommentsInfo>();
	private List<ExamInfo> examInfo = new ArrayList<ExamInfo>();
	private List<mybuddiesinfo> mybuddies = new ArrayList<mybuddiesinfo>();
	private List<SearchfriendsInfo> searchfriendsinfo = new ArrayList<SearchfriendsInfo>();

	// private GroupInfo objGroups;

	public StudyUpTask(Context context) {

		this.context = context;

	}

	@Override
	protected Integer doInBackground(String... params) {
		// TODO Auto-generated method stub

		String FeedURL = "";

		ServiceAction = Integer.parseInt(params[0]);

		if (ServiceAction == ADD_NOTE) {

			FeedURL = add_note;
			FeedURL = FeedURL + "&user_id=" + Helper.EncodeURL(params[1])
					+ "&class_id=" + params[2] + "&topic=" + params[3]
					+ "&subject=" + Helper.EncodeURL(params[4])
					+ "&notes_text=" + Helper.EncodeURL(params[5].trim())
					+ "&notes_image=" + params[6] + "&notes_audio=" + params[7];

		} else if (ServiceAction == ADD_CLASS) {
			FeedURL = add_class;
			FeedURL = FeedURL + "&class_name=" + Helper.EncodeURL(params[1])
					+ "&professor_name=" + Helper.EncodeURL(params[2])
					+ "&books=" + Helper.EncodeURL(params[4]) + "&time_slot="
					+ Helper.EncodeURL(params[3]) + "&user_id="
					+ Helper.EncodeURL(params[5]) + "&class_id="
					+ "&subject_id=" + Helper.EncodeURL(params[6]);

		} else if (ServiceAction == GET_ID) {

			FeedURL = get_id;
			FeedURL = FeedURL + "&email=" + params[1];

		} else if (ServiceAction == UPDATE_EXAM) {

			FeedURL = update_exam;
			FeedURL = FeedURL + "&exam_id=" + params[1] + "&exam_name="
					+ Helper.EncodeURL(params[2]) + "&time_slot="
					+ Helper.EncodeURL(params[3]) + "&marks="
					+ Helper.EncodeURL(params[4]) + "&exam_type="
					+ Helper.EncodeURL(params[5]) + "&comment="
					+ Helper.EncodeURL(params[6]) + "&user_id="
					+ Helper.EncodeURL(params[7]);

		} else if (ServiceAction == GET_CLASS) {
			isJArray = true;
			FeedURL = get_class + "&user_id=" + Helper.EncodeURL(params[1]);

		} else if (ServiceAction == GET_CLASS_NOTES) {
			isJArray = true;
			FeedURL = get_classnotes + "&class_id=" + params[1] + "&user_id="
					+ Helper.EncodeURL(params[2]);

		} else if (ServiceAction == GET_SUBJECTS) {
			isJArray = true;
			FeedURL = get_subject + "&user_id=" + params[1];

		} else if (ServiceAction == GET_EXAM) {
			isJArray = true;
			FeedURL = get_exam;
			FeedURL = FeedURL + "&class_id=" + Helper.EncodeURL(params[1])
					+ "&user_id=" + Helper.EncodeURL(params[2]);

		} else if (ServiceAction == ADD_EXAM) {

			FeedURL = add_exam;
			this.class_id = Helper.EncodeURL(params[1]);
			FeedURL = FeedURL + "&class_id=" + Helper.EncodeURL(params[1])
					+ "&exam_name=" + Helper.EncodeURL(params[2])
					+ "&time_slot=" + Helper.EncodeURL(params[3]) + "&comment="
					+ Helper.EncodeURL(params[4]) + "&exam_type="
					+ Helper.EncodeURL(params[5]) + "&user_id="
					+ Helper.EncodeURL(params[6]);

		} else if (ServiceAction == GET_CLASS_BY_USER_ID) {
			isJArray = true;
			FeedURL = get_class_by_user_id;
			FeedURL = FeedURL + "&user_id=" + Helper.EncodeURL(params[1]);

		} else if (ServiceAction == GET_NOTES) {
			isJArray = true;
			FeedURL = get_notes;
			FeedURL = FeedURL + "&user_id=" + params[1];

		} else if (ServiceAction == GET_FEEDS) {
			isJArray = true;
			FeedURL = get_feeds;
			FeedURL = FeedURL + "&user_id=" + params[1];

		} else if (ServiceAction == POST_COMMENTS) {
			isJArray = true;
			FeedURL = post_comments;
			FeedURL = FeedURL + "&user_id=" + params[1] + "&notes_id="
					+ params[2] + "&comment=" + Helper.EncodeURL(params[3]);

		} else if (ServiceAction == GET_COMMENTS) {
			isJArray = true;
			FeedURL = get_comments;
			FeedURL = FeedURL + "&notes_id=" + params[1] + "&user_id="
					+ Helper.EncodeURL(params[2]);

		} else if (ServiceAction == LIKE_COMMENT) {
			isJArray = true;
			FeedURL = like_comments;
			FeedURL = FeedURL + "&user_id=" + params[1] + "&notes_id="
					+ params[2];

		} else if (ServiceAction == GET_BUDDIES) {
			isJArray = true;
			FeedURL = get_buddies;
			FeedURL = FeedURL + "&user_id=" + params[1];

		} else if (ServiceAction == LIKE_LIST) {
			isJArray = true;
			FeedURL = like_list;
			FeedURL = FeedURL + "&notes_id=" + params[1] + "&user_id="
					+ Helper.EncodeURL(params[2]);

		} else if (ServiceAction == GET_DEDLINE) {
			isJArray = true;
			FeedURL = get_deadline;
			FeedURL = FeedURL + "&user_id=" + params[1];

		} else if (ServiceAction == INVITE_FRIENDS) {
			isJArray = true;
			FeedURL = invite_friends;
			FeedURL = FeedURL + "&user_id=" + params[1] + "&email_id="
					+ params[2];

		} else if (ServiceAction == SEARCH_FRIENDS) {
			isJArray = true;
			FeedURL = find_friends;
			FeedURL = FeedURL + "&user_id=" + params[1] + "&search_string="
					+ params[2];

		} else if (ServiceAction == ADD_FRIENDS) {
			isJArray = true;
			FeedURL = add_friends;
			FeedURL = FeedURL + "&user_id=" + params[1] + "&friend_id="
					+ params[2] + "&make_friend=" + params[3];

		}

		if (isJArray) {
			jsonParser = new JsonParser();
			try {
				HandleResponseArray(jsonParser.GetJasonObject(FeedURL, false));
			} catch (Exception e) {
				System.out.println("");
			}
			System.out.println("");
		} else {
			jsonParser = new JsonParser();
			try {
				HandleResponse(jsonParser.GetJasonObject(FeedURL, false));
			} catch (JSONException ex) {
				Logger.getLogger(StudyUpTask.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}

		return _taskStatus;
	}

	@Override
	protected void onPostExecute(Integer result) {

		if (ServiceAction == GET_CLASS) {

			Intent intent = new Intent(Utils.ACTION_GET_CLASS);
			intent.putExtra("CLassList", (ArrayList<ClassInfo>) classInfo);
			if (context != null) {
				context.sendBroadcast(intent);
			}
		} else if (ServiceAction == GET_CLASS_BY_USER_ID) {

			Intent intent = new Intent(Utils.ACTION_GET_CLASS_BYID);
			intent.putExtra("classList", (ArrayList<ClassInfo>) classInfo);
			if (context != null) {
				context.sendBroadcast(intent);
			}
		} else if (ServiceAction == UPDATE_EXAM) {

			if (markslist_adapter.test) {
				glob = glob = ((global) context.getApplicationContext());
				Intent intent1 = new Intent(context, AddMarks.class);
				intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent1.putExtra("class_id", glob.class_id);
				context.startActivity(intent1);
			}

		} else if (ServiceAction == GET_SUBJECTS) {

			Intent intent = new Intent(Utils.ACTION_GET_SUBJECT);
			intent.putExtra("subjectsList", (ArrayList<SubjectInfo>) SubjInfo);
			if (context != null) {
				context.sendBroadcast(intent);
			}
		} else if (ServiceAction == ADD_CLASS) {

			Intent intent = new Intent(Utils.ACTION_ADD_CLASS);
			intent.putExtra("CLassObject", classinfo);
			if (context != null) {
				context.sendBroadcast(intent);
			}

		} else if (ServiceAction == GET_CLASS_NOTES) {

			Intent intent = new Intent(Utils.ACTION_GET_CLASSNOTES);
			intent.putExtra("classnotes",
					(ArrayList<classnotesInfo>) classnotesInfo);
			if (context != null) {
				context.sendBroadcast(intent);
			}

		} else if (ServiceAction == GET_EXAM) {

			Intent intent = new Intent(Utils.ACTION_GET_EXAM);
			intent.putExtra("examlist", (ArrayList<ExamInfo>) examInfo);
			if (context != null) {
				context.sendBroadcast(intent);
			}

		} else if (ServiceAction == GET_NOTES) {

			Intent intent = new Intent(Utils.ACTION_GET_NOTE);
			intent.putExtra("NoteList", (ArrayList<NoteInfo>) noteInfo);
			if (context != null)
				context.sendBroadcast(intent);
		} else if (ServiceAction == GET_FEEDS) {

			Intent intent = new Intent(Utils.ACTION_GET_FEEDS);
			intent.putExtra("FeedsList", (ArrayList<FeedsInfo>) feedsInfo);
			if (context != null)
				context.sendBroadcast(intent);
		} else if (ServiceAction == GET_COMMENTS) {

			Intent intent = new Intent(Utils.ACTION_GET_COMMENTS);
			intent.putExtra("CommentsList",
					(ArrayList<CommentsInfo>) commentsInfo);
			if (context != null)
				context.sendBroadcast(intent);
		} else if (ServiceAction == LIKE_LIST) {

			Intent intent = new Intent(Utils.ACTION_GET_LIKE);
			intent.putExtra("LikeList", (ArrayList<UserInfo>) userInfo);
			if (context != null)
				context.sendBroadcast(intent);
		} else if (ServiceAction == LIKE_COMMENT) {

			Intent intent = new Intent(Utils.ACTION_GET_LIKECOMMENTS);
			intent.putExtra("likecomments", posts);
			if (context != null)
				context.sendBroadcast(intent);
		} else if (ServiceAction == GET_DEDLINE) {

			Intent intent = new Intent(Utils.ACTION_GET_DEADLINES);
			intent.putExtra("deadlines", (ArrayList<Deadlineinfo>) deadlineInfo);
			if (context != null)
				context.sendBroadcast(intent);
		} else if (ServiceAction == GET_BUDDIES) {

			Intent intent = new Intent(Utils.ACTION_GET_buddies);
			intent.putExtra("getbuddies", (ArrayList<mybuddiesinfo>) mybuddies);
			if (context != null)
				context.sendBroadcast(intent);
		} else if (ServiceAction == INVITE_FRIENDS) {

			Intent intent = new Intent(Utils.ACTION_GET_INVITATION);
			intent.putExtra("invitaionresponse", Invitaionresponse);
			if (context != null)
				context.sendBroadcast(intent);
		} else if (ServiceAction == SEARCH_FRIENDS) {

			Intent intent = new Intent(Utils.ACTION_SEARCH_FRIENDS);
			intent.putExtra("searchfriends",
					(ArrayList<SearchfriendsInfo>) searchfriendsinfo);
			if (context != null)
				context.sendBroadcast(intent);
		} else if (ServiceAction == ADD_FRIENDS) {

			Intent intent = new Intent(Utils.ACTION_ADD_FRIEND);
			String s = addresponse;
			intent.putExtra("addresponses", s);
			if (context != null)
				context.sendBroadcast(intent);
		}
		super.onPostExecute(result);

	}

	private void HandleResponseArray(JSONObject jObj) {

		if (jObj == null) {
			Log.d(TAG, "JSON object is null");
			return;
		}
		if (ServiceAction == ADD_CLASS) {
			GetClass(jObj);
		}
		if (ServiceAction == GET_CLASS || ServiceAction == GET_CLASS_BY_USER_ID) {

			GetClass(jObj);
		} else if (ServiceAction == GET_SUBJECTS) {

			GetSub(jObj);

		} else if (ServiceAction == GET_EXAM) {

			GetExam(jObj);

		} else if (ServiceAction == GET_NOTES) {

			GetNotes(jObj);
		} else if (ServiceAction == GET_FEEDS) {

			GetFeeds(jObj);
		} else if (ServiceAction == POST_COMMENTS) {

			POSTCOMMENTS(jObj);
		} else if (ServiceAction == GET_CLASS_NOTES) {

			GetClassNotes(jObj);
		} else if (ServiceAction == GET_COMMENTS) {

			GEtCOMMENTS(jObj);
		} else if (ServiceAction == LIKE_LIST) {

			GETLIKELIST(jObj);
		} else if (ServiceAction == LIKE_COMMENT) {

			GETLIKECOMMENT(jObj);
		} else if (ServiceAction == GET_DEDLINE) {

			GETDEADLINE(jObj);
		} else if (ServiceAction == GET_BUDDIES) {

			GETBUDDIES(jObj);
		} else if (ServiceAction == INVITE_FRIENDS) {

			INVITEFRIENDS(jObj);
		} else if (ServiceAction == SEARCH_FRIENDS) {

			SEARCHFREIND(jObj);
		} else if (ServiceAction == ADD_FRIENDS) {

			ADD_FRIENDS(jObj);
		}
	}

	private void GetClassNotes(JSONObject jObj) {
		isJArray = true;
		try {
			JSONArray posts = jObj.getJSONArray("Response");
			for (int i = 0; i < posts.length(); i++) {
				JSONObject objJson = new JSONObject();
				objJson = posts.getJSONObject(i);
				if (objJson != null)
					loadClassNotes(objJson);
			}
			_taskStatus = GET_NOTES;

		} catch (Exception exp) {

			_taskStatus = GET_NOTES;
			Log.d(TAG, exp.getMessage());

		}

	}

	private void loadClassNotes(JSONObject objJson) {
		try {

			classnotesinfo = new classnotesInfo();
			classnotesinfo.setId(objJson.getString("id"));
			classnotesinfo.setClass_id(objJson.getString("user_id"));
			classnotesinfo.setDate_added(objJson.getString("date_added"));
			classnotesinfo.setSubject(objJson.getString("subject"));

			if (isJArray)
				classnotesInfo.add(classnotesinfo);

		} catch (Exception exp) {

			Log.d(TAG,
					"Exception in method: ServicesHandlerThread.LoadPostInfo()");

		}

	}

	private void GetSub(JSONObject jObj) {
		isJArray = true;
		try {
			JSONArray posts = jObj.getJSONArray("Response");
			for (int i = 0; i < posts.length(); i++) {

				JSONObject objJson = new JSONObject();
				objJson = posts.getJSONObject(i);
				if (objJson != null)
					LoadSubjInfo(objJson);
			}
			_taskStatus = GET_CLASS;

		} catch (Exception exp) {

			_taskStatus = GET_CLASS;
			Log.d(TAG, exp.getMessage());

		}

	}

	private void LoadSubjInfo(JSONObject objJson) {
		try {

			subjectinfo = new SubjectInfo();
			subjectinfo.setSub_id(objJson.getString("id"));
			subjectinfo.setSub_name(objJson.getString("name"));

			if (isJArray)
				SubjInfo.add(subjectinfo);

		} catch (Exception exp) {

			Log.d(TAG,
					"Exception in method: ServicesHandlerThread.LoadPostInfo()");

		}

	}

	private void ADD_FRIENDS(JSONObject jObj) {

		// TODO Auto-generated method stub

		isJArray = true;
		try {
			JSONArray posts = jObj.getJSONArray("Response");

			addresponse = posts.toString();

		} catch (Exception e) {

		}

	}

	private void SEARCHFREIND(JSONObject jObj) {

		// TODO Auto-generated method stub

		isJArray = true;
		try {
			JSONArray posts = jObj.getJSONArray("Response");
			for (int i = 0; i < posts.length(); i++) {

				JSONObject objJson = new JSONObject();
				objJson = posts.getJSONObject(i);
				if (objJson != null)
					Loadsearchfriends(objJson);
			}
			_taskStatus = SEARCH_FRIENDS;

		} catch (Exception exp) {

			_taskStatus = SEARCH_FRIENDS;
			Log.d(TAG, exp.getMessage());

		}

	}

	private void Loadsearchfriends(JSONObject objJson) {
		// TODO Auto-generated method stub
		serachfriendsinfo = new SearchfriendsInfo();

		try {

			serachfriendsinfo.setId(objJson.getString("id"));
			serachfriendsinfo.setName(objJson.getString("name"));
			serachfriendsinfo.setUser_name(objJson.getString("name"));
			serachfriendsinfo.setEmail(objJson.getString("email"));
			serachfriendsinfo.setImage(objJson.getString("image"));
			serachfriendsinfo.setGender(objJson.getString("gender"));
			serachfriendsinfo.setCity(objJson.getString("city"));
			serachfriendsinfo.setCountry(objJson.getString("country"));
			serachfriendsinfo.setState(objJson.getString("state"));
			serachfriendsinfo.setPhone(objJson.getString("phone"));
			serachfriendsinfo.setStatus(objJson.getString("status"));
			serachfriendsinfo.setIsMyFriend(objJson.getString("is_myfriend"));
			serachfriendsinfo.setDOB(objJson.getString("birthday"));
			if (isJArray)
				searchfriendsinfo.add(serachfriendsinfo);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void INVITEFRIENDS(JSONObject jObj) {

		// TODO Auto-generated method stub

		isJArray = true;
		try {
			JSONArray posts = jObj.getJSONArray("Response");

			Invitaionresponse = posts.toString();

		} catch (Exception e) {

		}

	}

	private void GETBUDDIES(JSONObject jObj) {

		// TODO Auto-generated method stub

		isJArray = true;
		try {
			JSONArray posts = jObj.getJSONArray("Response");
			for (int i = 0; i < posts.length(); i++) {

				JSONObject objJson = new JSONObject();
				objJson = posts.getJSONObject(i);
				if (objJson != null)
					Loadbuddy(objJson);
			}
			_taskStatus = GET_BUDDIES;

		} catch (Exception exp) {

			_taskStatus = GET_BUDDIES;
			Log.d(TAG, exp.getMessage());

		}

	}

	private void Loadbuddy(JSONObject objJson) {
		// TODO Auto-generated method stub
		buddiesinfo = new mybuddiesinfo();

		try {

			buddiesinfo.setId(objJson.getString("id"));
			buddiesinfo.setName(objJson.getString("name"));
			buddiesinfo.setUser_name(objJson.getString("name"));
			buddiesinfo.setEmail(objJson.getString("email"));
			buddiesinfo.setImage(objJson.getString("image"));
			buddiesinfo.setGender(objJson.getString("gender"));
			buddiesinfo.setCity(objJson.getString("city"));
			buddiesinfo.setCountry(objJson.getString("country"));
			buddiesinfo.setState(objJson.getString("state"));
			buddiesinfo.setPhone(objJson.getString("phone"));
			buddiesinfo.setBirthday(objJson.getString("birthday"));
			buddiesinfo.setStatus(objJson.getString("status"));
			if (isJArray)
				mybuddies.add(buddiesinfo);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void GETDEADLINE(JSONObject jObj) {

		// TODO Auto-generated method stub

		isJArray = true;
		try {
			JSONArray posts = jObj.getJSONArray("Response");
			for (int i = 0; i < posts.length(); i++) {

				JSONObject objJson = new JSONObject();
				objJson = posts.getJSONObject(i);
				if (objJson != null)
					LoadDeadlines(objJson);
			}
			_taskStatus = GET_DEDLINE;

		} catch (Exception exp) {

			_taskStatus = GET_DEDLINE;
			Log.d(TAG, exp.getMessage());

		}

	}

	private void LoadDeadlines(JSONObject objJson) {
		// TODO Auto-generated method stub
		deadlineinfo = new Deadlineinfo();

		try {

			deadlineinfo.setDeadline_id(objJson.getString("id"));
			deadlineinfo.setDeadline_name(objJson.getString("name"));
			deadlineinfo.setDeadline_type(objJson.getString("type"));
			deadlineinfo.setDeadline_time(objJson.getString("time"));
			// deadlineinfo.setDeadline_class_id(objJson.getString("class_id"));
			deadlineinfo.setDeadline_marks(objJson.getString("marks"));
			if (isJArray)
				deadlineInfo.add(deadlineinfo);
			deadlineInfo = deadlineInfo;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void GETLIKECOMMENT(JSONObject jObj) {
		isJArray = true;

		try {
			posts = jObj.getString("State");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void GEtCOMMENTS(JSONObject jObj) {
		isJArray = true;
		try {
			JSONArray posts = jObj.getJSONArray("Response");
			for (int i = 0; i < posts.length(); i++) {

				JSONObject objJson = new JSONObject();
				objJson = posts.getJSONObject(i);
				if (objJson != null)
					LoadFeedsComments(objJson);
			}
			_taskStatus = GET_COMMENTS;

		} catch (Exception exp) {

			_taskStatus = GET_COMMENTS;
			Log.d(TAG, exp.getMessage());

		}

	}

	private void GETLIKELIST(JSONObject jObj) {
		isJArray = true;
		try {
			JSONArray posts = jObj.getJSONArray("Response");
			for (int i = 0; i < posts.length(); i++) {

				JSONObject objJson = new JSONObject();
				objJson = posts.getJSONObject(i);
				if (objJson != null)
					LoadLikeComments(objJson);
			}
			_taskStatus = LIKE_LIST;

		} catch (Exception exp) {

			_taskStatus = LIKE_LIST;
			Log.d(TAG, exp.getMessage());

		}

	}

	private void LoadFeedsComments(JSONObject objJson) {
		// TODO Auto-generated method stub
		commentinfo = new CommentsInfo();

		try {

			JSONObject Jobject_comments = objJson.getJSONObject("comments");
			JSONObject Jobject_userDate = objJson.getJSONObject("user_data");
			commentinfo.setComment(Jobject_comments.getString("comments"));

			commentinfo.setCommentUser_image(Jobject_userDate
					.getString("image"));
			commentinfo.setCommentuser_email(Jobject_userDate
					.getString("email"));
			commentinfo.setCommentUser_name(Jobject_userDate.getString("name"));
			commentinfo.setDate_commented(Jobject_comments
					.getString("date_commented"));
			if (isJArray)
				commentsInfo.add(commentinfo);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void LoadLikeComments(JSONObject objJson) {
		// TODO Auto-generated method stub
		user_Info = new UserInfo();

		try {

			// JSONObject Jobject_comments = objJson.getJSONObject("comments");
			JSONObject Jobject_userDate = objJson.getJSONObject("user_data");
			user_Info.setName((Jobject_userDate.getString("name")));
			user_Info.setImageName(Jobject_userDate.getString("image"));

			if (isJArray)
				userInfo.add(user_Info);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void POSTCOMMENTS(JSONObject jObj) {
		isJArray = true;

		JSONArray posts;
		try {
			posts = jObj.getJSONArray("Response");
			for (int i = 0; i < posts.length(); i++) {

				String comment_status = posts.getString(i);
				if (comment_status.equals("Commented")) {
					PostComments.check_status = true;
					System.out.println("Ok");
				} else {
					System.out.println("error");
					PostComments.check_status = false;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void GetFeeds(JSONObject jObj) {
		isJArray = true;
		try {
			JSONArray posts = jObj.getJSONArray("Response");
			for (int i = 0; i < posts.length(); i++) {

				JSONObject objJson = new JSONObject();
				objJson = posts.getJSONObject(i);
				if (objJson != null)
					LoadFeeds(objJson);
			}
			_taskStatus = GET_FEEDS;

		} catch (Exception exp) {

			_taskStatus = GET_FEEDS;
			Log.d(TAG, exp.getMessage());

		}

	}

	private void GetClass(JSONObject jObj) {
		isJArray = true;
		try {
			JSONArray posts = jObj.getJSONArray("Response");
			for (int i = 0; i < posts.length(); i++) {

				JSONObject objJson = new JSONObject();
				objJson = posts.getJSONObject(i);
				if (objJson != null)
					LoadClassInfo(objJson);
			}
			_taskStatus = GET_CLASS;

		} catch (Exception exp) {

			_taskStatus = GET_CLASS;
			Log.d(TAG, exp.getMessage());

		}

	}

	private void GetExam(JSONObject jObj) {

		isJArray = true;
		try {
			JSONArray posts = jObj.getJSONArray("Response");
			for (int i = 0; i < posts.length(); i++) {

				JSONObject objJson = new JSONObject();
				objJson = posts.getJSONObject(i);
				if (objJson != null)
					LoadExamInfo(objJson);
			}
			_taskStatus = GET_EXAM;

		} catch (Exception exp) {

			_taskStatus = GET_EXAM;
			Log.d(TAG, exp.getMessage());

		}
	}

	private void GetNotes(JSONObject jObj) {
		isJArray = true;
		try {
			JSONArray posts = jObj.getJSONArray("Response");
			for (int i = 0; i < posts.length(); i++) {
				JSONObject objJson = new JSONObject();
				objJson = posts.getJSONObject(i);
				if (objJson != null)
					LoadNotesInfo(objJson);
			}
			_taskStatus = GET_NOTES;

		} catch (Exception exp) {

			_taskStatus = GET_NOTES;
			Log.d(TAG, exp.getMessage());

		}

	}

	private void LoadFeeds(JSONObject objJson) {
		// TODO Auto-generated method stub
		feedsinfo = new FeedsInfo();

		try {

			JSONObject Jobject_userdata = objJson.getJSONObject("user_data");
			JSONObject Jobject_notes_data = objJson.getJSONObject("notes_data");
			feedsinfo.setUsername(Jobject_userdata.getString("username"));

			feedsinfo.setNotedata_id(Jobject_notes_data.getString("id"));
			feedsinfo.setFeed_date(objJson.getString("feed_date"));
			feedsinfo.setFeed_type(objJson.getString("feed_type"));
			feedsinfo.setNotedata_topic(Jobject_notes_data.getString("topic"));
			feedsinfo.setNotedata_subject(Jobject_notes_data
					.getString("subject"));
			feedsinfo.setImage(Jobject_userdata.getString("image"));
			// feedsinfo.setFeed_id(objJson.getString("feed_id"));
			Jobject_userdata.getString("name");
			feedsinfo.setName(Jobject_userdata.getString("name"));
			feedsinfo.setEmail(Jobject_userdata.getString("email"));
			feedsinfo.setBirthday(Jobject_userdata.getString("birthday"));
			feedsinfo.setGender(Jobject_userdata.getString("gender"));
			feedsinfo.setCity(Jobject_userdata.getString("city"));
			feedsinfo.setState(Jobject_userdata.getString("state"));
			feedsinfo.setCountry(Jobject_userdata.getString("country"));
			feedsinfo.setPhone(Jobject_userdata.getString("phone"));
			feedsinfo.setApproves_count(objJson.getString("approves_count"));

			if (isJArray)
				feedsInfo.add(feedsinfo);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void LoadExamInfo(JSONObject objJSON) {

		try {

			examinfo = new ExamInfo();
			examinfo.setId(objJSON.getString("id"));
			examinfo.setName(objJSON.getString("name"));
			examinfo.setType(objJSON.getString("type"));
			examinfo.setTime(objJSON.getString("time"));
			// examinfo.setClass_id(objJSON.getString("class_id"));
			examinfo.setComments(objJSON.getString("comment"));
			examinfo.setMarks(objJSON.getString("marks"));

			if (isJArray)
				examInfo.add(examinfo);

		} catch (Exception exp) {

			Log.d(TAG,
					"Exception in method: ServicesHandlerThread.LoadPostInfo()");

		}

	}

	public void LoadClassInfo(JSONObject objJSON) {
		try {

			classinfo = new ClassInfo();
			classinfo.setClassId(objJSON.getString("id"));
			classinfo.setClassName(objJSON.getString("class_name"));

			if (objJSON.getString("professor_name") != null) {
				classinfo
						.setProfessor_name(objJSON.getString("professor_name"));
			}
			if (objJSON.getString("time_slot") != null) {
				classinfo.setTime_slot(objJSON.getString("time_slot"));
			}
			if (objJSON.getString("books") != null) {
				String temp = objJSON.getString("books");
				classinfo.setBooks(Arrays.asList(temp.split(",")));

			}

			if (isJArray)
				classInfo.add(classinfo);

		} catch (Exception exp) {

			Log.d(TAG,
					"Exception in method: ServicesHandlerThread.LoadPostInfo()");

		}

	}

	public void LoadNotesInfo(JSONObject objJSON) {
		try {

			noteinfo = new NoteInfo();
			noteinfo.setId(objJSON.getString("id"));
			noteinfo.setClassId(objJSON.getString("course_name"));
			noteinfo.setTopic(objJSON.getString("topic"));
			noteinfo.setAudioNotes(objJSON.getString("audio_file_name"));
			noteinfo.setDateAdded(objJSON.getString("created"));
			noteinfo.setNotesImage(objJSON.getString("image_file_name"));
			noteinfo.setNotesText(objJSON.getString("body"));
			noteinfo.setReviewed(objJSON.getString("reviewed"));
			noteinfo.setSubject(objJSON.getString("subject"));
			noteinfo.setTopic(objJSON.getString("topic"));
			noteinfo.setUserId(objJSON.getString("user_id"));

			if (isJArray)
				noteInfo.add(noteinfo);

		} catch (Exception exp) {

			Log.d(TAG,
					"Exception in method: ServicesHandlerThread.LoadPostInfo()");

		}

	}

	private void GetID(JSONObject jObj) {

		try {

			JSONObject objj = jObj.getJSONObject("Response");
			AddNotes.user_id = objj.getString("id");

			Log.d("asd", "dasd");
			Log.d("asd", "dasd");
			Log.d("asd", "dasd");
			Log.d("asd", "dasd");
			Log.d("asd", "dasd");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void LoadID(JSONObject objJSON) {

		try {

			objJSON.getString("id");
			Log.d("asd", "asdd");
			Log.d("asd", "asdd");
			Log.d("asd", "asdd");
			Log.d("asd", "asdd");
			Log.d("asd", "asdd");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void HandleResponse(JSONObject objJSON) throws JSONException {
		if (objJSON == null) {
			// set message
			Log.d(TAG, "JSON object is null");
			return;
		}

		if (ServiceAction == ADD_CLASS) {

			boolean b = objJSON.getBoolean("State");
			if (b) {
				LoadClassInfo(objJSON.getJSONObject("Response"));
			}

		} else if (ServiceAction == GET_ID) {

			GetID(objJSON);
		} else if (ServiceAction == LIKE_COMMENT) {

			GetLike(objJSON);
		} else if (ServiceAction == ADD_EXAM) {
			boolean b = objJSON.getBoolean("State");
			if (b) {
				prefObj = new PreferenceHelper(context,
						PreferenceHelper.CurrentUser);
				String user_id = prefObj.getPref(PreferenceHelper.Id, "");
				new StudyUpTask(context).execute(StudyUpTask.GET_EXAM + "",
						this.class_id, user_id);
			} else {
				Intent intent = new Intent(Utils.ACTION_NOTIFY_ERROR);
				intent.putExtra("desc", objJSON.getString("Response"));
				if (context != null) {
					context.sendBroadcast(intent);
				}

			}

		}

	}

	public void GetLike(JSONObject obj) {

		try {
			obj.getString("Response");

			FeedsListAdapter.check_approved = true;

			Log.d("asd", "dsas");
		} catch (Exception e) {
			Log.d("", "");

		}

	}

}
