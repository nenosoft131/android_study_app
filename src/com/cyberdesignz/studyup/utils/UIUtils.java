package com.cyberdesignz.studyup.utils;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.location.Location;
import android.widget.Button;

public class UIUtils extends Activity {
    private static Context contextThis;
    private static String _email;

    public static void SignOut(Context context, String email) {
        contextThis = context;
        _email = email;
        RemovePrefsLogin();
    }

    /**
     * Helper methods
     */
    public static void RemovePrefsLogin() {
        //update logout in database
        //Intent intent = new Intent(((ContextWrapper) contextThis).getBaseContext(), LoginService.class);
        //	intent.putExtra("email", _email);
        //	contextThis.startService(intent);

        // obtain user's remember login preference
        SharedPreferences mSharedPreferences = contextThis.getSharedPreferences("CurrentUser", MODE_PRIVATE);

        Editor mEditor = mSharedPreferences.edit();
        mEditor.remove("email");
        mEditor.remove("gender");
        mEditor.remove("password");
        mEditor.commit();

    }

    /**
     * End of helper methods
     */

    public void SetButtonBackgroud(int filter) {
        Button btnFar = null, btnNewest = null, btnAll = null, btnOnline = null;


    }

    public static String CalculateDistance(String startLat, String startLon, String endLat, String endLon) {
        try {

            String dis = "";
            float[] results = new float[1];
            Location.distanceBetween(Double.valueOf(startLat), Double.valueOf(startLon), Double.valueOf(endLat), Double.valueOf(endLon), results);

            float distance = results[0];
            distance = distance / 1000;

            DecimalFormat df = new DecimalFormat("#.##");

            if (distance > 1)
                dis = String.valueOf(df.format(distance)) + " km from you";
            else
                dis = String.valueOf(df.format(results[0])) + " meters from you";

            return dis;
        } catch (Exception exp) {
            return "Distance 0.0 km";
        }
    }

    /*
     * resize image
     * width: proposed width of image, default is 150
     * height: proposed height of image, default is 120
     */
    public static Bitmap ResizeBitmap(Bitmap bitmap, int width, int height) {
        int fixHeight = 800;
        int fixWidth = 800;
        Bitmap resizedBitmap;

        int tempHeight = bitmap.getHeight();
        int tempWidth = bitmap.getWidth();


        if (width > 0 && height > 0) {
            fixHeight = height;
            fixWidth = width;

            float scaleHeight = ((float) fixHeight) / tempHeight;
            float scaleWidth = ((float) fixWidth) / tempWidth;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);


            resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, fixWidth, fixHeight, matrix, true);
        } else if (tempHeight > 800 || tempWidth > 800) {

            float scaleHeight = ((float) fixHeight) / tempHeight;
            float scaleWidth = ((float) fixWidth) / tempWidth;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);

            resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, fixWidth, fixHeight, matrix, true);
        } else
            resizedBitmap = bitmap;

        return resizedBitmap;
    }

    /*
     * Resize if width and height is greater than 800
     */
    public static Bitmap ResizeBitmap(Bitmap bitmap) {
        return ResizeBitmap(bitmap, 0, 0);
    }

    public static byte[] ConvertImageToByte(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 90, baos);
        byte[] byteImage = baos.toByteArray();
        return byteImage;
    }


}
