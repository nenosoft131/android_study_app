package com.cyberdesignz.studyup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.FaceDetector.Face;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionDefaultAudience;
import com.facebook.SessionState;
import com.facebook.android.Facebook;

import java.util.Arrays;
import java.util.List;

public class FacebookActivity extends Activity {

	String subject;
	String Detail;
	Session session;
	private static final List<String> PERMISSIONS = Arrays.asList(
			"publish_stream", "user_notes");
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		subject = getIntent().getExtras().getString("subject");
		Detail = getIntent().getExtras().getString("detail");
		shareOnFacebook();

		TextView tv = new TextView(this);
		tv.setText("Hello");
		setContentView(tv);

	}

	private void openFacebookSesson() {
		if (session == null) {
			session = new Session(FacebookActivity.this);
			System.out.println("");
		}
		if (session.getState().isOpened()) {
			session.getState().isClosed();
		}
		Session.OpenRequest sessionOpenRequest = new Session.OpenRequest(this);
		sessionOpenRequest.setPermissions(PERMISSIONS).setDefaultAudience(
				SessionDefaultAudience.EVERYONE);

		session.addCallback(new Session.StatusCallback() {

			public void call(Session session, SessionState state,
					Exception exception) {
				if (session.getState().isOpened()) {
					share();
				}
			}
		});
		session.openForPublish(sessionOpenRequest);

	}

	private void shareOnFacebook() {
		if (session == null || !session.getState().isOpened()) {
			openFacebookSesson();
		} else {
			share();

		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (session != null) {
			session.onActivityResult(this, requestCode, resultCode, data);

		}
	}

	private void share() {
		if (!session.getState().isOpened()) {
			return;
		}

		Bundle parameters = new Bundle();

		parameters.putString("subject", subject);
		parameters.putString("message", Detail);
		pDialog = new ProgressDialog(FacebookActivity.this);
		pDialog.setMessage("Updating to facebook...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();
		Request request = new Request(session, "me/notes", parameters,
				HttpMethod.POST, new Request.Callback() {

					public void onCompleted(Response response) {
						pDialog.dismiss();
						if (response.getError() != null) {
							Toast.makeText(FacebookActivity.this,
									response.getError().getErrorMessage(),
									Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(FacebookActivity.this, "Shared",
									Toast.LENGTH_LONG).show();

						}
						finish();

					}
				});
		request.executeAsync();

	}
}