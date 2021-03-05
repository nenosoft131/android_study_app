package com.cyberdesignz.studyup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash);

        Thread splashTimer = new Thread() {

            public void run() {
                try {
                    int splashTimer = 0;

                    while (splashTimer < 6000) {
                        sleep(100);
                        splashTimer = splashTimer + 100;
                    }

                    Intent intent;
                    intent = new Intent(Splash.this, Signin.class);
                    startActivity(intent);
                    finish();

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    Log.e("LongToast", "", e);
                } finally {
                    finish();
                }
            }
        };

        splashTimer.start();

    }

}
