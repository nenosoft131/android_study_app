package com.cyberdesignz.studyup.helper;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.cyberdesignz.studyup.utils.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Helper {

    private static File cacheDir = null;
    public static final String CACHE_DIR_NAME = "__imgldcache";

    public static final int ALL_F = 0;
    public static final int FAV_F = 1;
    public static final int NEW_F = 2;
    public static final int ONLIN_F = 3;

    public Bitmap getBitmap(String url, Context context) {

        //get the dir to save cached images
        if (cacheDir == null) cacheDir = Utils.createCacheDir(context, CACHE_DIR_NAME);

        //I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename = String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);

        //from SD cache
        Bitmap b = decodeFile(f);

        if (b != null) {
            f.delete();
            return b;

        }
        //from web
        try {
            Bitmap bitmap = null;
            InputStream is = new URL(url).openStream();
            OutputStream os = new FileOutputStream(f);
            Utils.copyStream(is, os);
            os.close();
            bitmap = decodeFile(f);
            f.delete();
            return bitmap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f) {
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale++;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

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

    public static String EncodeURL(String url) {
        try {
            url = URLEncoder.encode(url);
            return url;
        } catch (Exception exp) {
            return url;
        }
    }

    public static String DecodeURL(String url) {
        try {
            url = URLDecoder.decode(url);
            return url;
        } catch (Exception exp) {
            return url;
        }
    }
}