package com.cyberdesignz.studyup;

import java.util.ArrayList;

import com.cyberdesignz.studyup.info.FeedsInfo;
import com.cyberdesignz.studyup.utils.ImageLoader;
import com.cyberdesignz.studyup.utils.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends Activity {

    TextView name, email, gender, state, country, dob, city, phoneno;
    FeedsInfo info;
    ImageView picture;
    Button cancel;
    int pos;
    private ArrayList<FeedsInfo> list;
    ImageLoader imageloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.profile_xlarge);
        this.imageloader = new ImageLoader(Profile.this, R.drawable.background);
        list = (ArrayList<FeedsInfo>) getIntent().getExtras().get("list");
        pos = getIntent().getExtras().getInt("position");
        init();
        update();
        cancel.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();

            }
        });
    }

    private void update() {
        // TODO Auto-generated method stub

        name.setText(list.get(pos).getName());
        email.setText(list.get(pos).getEmail());
        gender.setText(list.get(pos).getGender());
        state.setText(list.get(pos).getState());
        country.setText(list.get(pos).getCountry());
        dob.setText(list.get(pos).getBirthday());
        city.setText(list.get(pos).getCity());
        phoneno.setText(list.get(pos).getPhone());
        String url = Utils.IMAGE_SERVER + list.get(pos).getImage();
        picture.setTag(imageloader);
        imageloader.displayImage(url, picture);

    }

    private void init() {
        // TODO Auto-generated method stub
        name = (TextView) findViewById(R.id.prf_name);
        email = (TextView) findViewById(R.id.prf_email);
        gender = (TextView) findViewById(R.id.prf_gender);
        state = (TextView) findViewById(R.id.prf_state);
        country = (TextView) findViewById(R.id.prf_country);
        dob = (TextView) findViewById(R.id.prf_DOB);
        city = (TextView) findViewById(R.id.prf_city);
        phoneno = (TextView) findViewById(R.id.prf_phoneno);
        picture = (ImageView) findViewById(R.id.profile_picture);
        cancel = (Button) findViewById(R.id.button1);
    }

}
