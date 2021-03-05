package com.cyberdesignz.studyup.utils;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

public class Helper {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    static Context mContext;

    public static int GetIntFromString(String val) {
        try {
            return Integer.parseInt(val);
        } catch (Exception exp) {
            return 0;
        }
    }

    public static String GetStringFromInt(int val) {
        try {
            return new Integer(val).toString();
        } catch (Exception exp) {
            return "";
        }
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.NO_WRAP);
        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }

    public static String EncodeURL(String url) {
        try {
            url = URLEncoder.encode(url);
            return url;
        } catch (Exception exp) {
            return url;
        }
    }

    public static Date GetDate(String string) {
        try {
            return dateFormat.parse(string);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return null;
        }
    }

}
