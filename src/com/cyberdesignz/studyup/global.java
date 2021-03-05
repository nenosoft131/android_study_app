package com.cyberdesignz.studyup;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Application;

public class global extends Application {

    public final Map<Integer, String> stoptime = new LinkedHashMap<Integer, String>();
    public final Map<Integer, String> starttime = new LinkedHashMap<Integer, String>();
    public String class_id;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        udatelist();

    }

    private void udatelist() {
        for (int i = 0; i < 5; i++) {
            starttime.put(i, "");
            stoptime.put(i, "");
        }

    }

    public void update() {
        class_id = AddMarks.class_id;
    }
}
