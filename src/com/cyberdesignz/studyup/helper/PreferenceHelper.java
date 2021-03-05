package com.cyberdesignz.studyup.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PreferenceHelper {

    private SharedPreferences sharedPreference;
    private Context mContext;
    private String TAG = "Preference";

    public static final String UserName = "username";
    public static final String CurrentUser = "CurrentUser";
    public static final String UserID = "userid";
    public static final String Name = "Name";
    public static final String Email = "email";

    public static final String Password = "password";
    public static final String Gender = "gender";
    public static final String DateOfBirth = "dateOfBirth";
    public static final String ContactNo = "contactno";

    public static final String City = "city";
    public static final String State = "state";
    public static final String Country = "country";

    public static final String PictureUrl = "picture";

    public static final String Id = "id";

    public static final String ClassName = "classname";

    // constructor
    public PreferenceHelper(Context context, String prefName) {
        mContext = context;
        sharedPreference = mContext.getSharedPreferences(prefName,
                Context.MODE_PRIVATE);
    }

    public String getPreferenceString(String key, String defaultVal) {
        return sharedPreference.getString(key, defaultVal);
    }

    /**
     * Whether set user preference
     */
    public void setUserPref(String key, String value) {
        setPreferencesString(key, value);
    }

    /**
     * Get user preference
     */

    public String getPref(String key, String defaultVal) {
        return sharedPreference.getString(key, defaultVal);
    }

    public void setPreferencesString(String key, String value) {
        try {

            SharedPreferences.Editor editor = sharedPreference.edit();
            editor = sharedPreference.edit();

            editor.putString(key, value);
            editor.commit();

        } catch (Exception exp) {
            Log.d(TAG, "Exp: " + exp.getMessage());
        }

    }

    public void ClearPreferences() {
        try {
            SharedPreferences.Editor editor = sharedPreference.edit();
            editor = sharedPreference.edit();

            editor.remove(UserName);
            editor.remove(CurrentUser);
            editor.remove(UserID);
            editor.remove(Name);
            editor.remove(Email);
            editor.remove(Password);
            editor.remove(Gender);
            editor.remove(DateOfBirth);
            editor.remove(ContactNo);
            editor.remove(City);
            editor.remove(State);
            editor.remove(Country);
            editor.remove(PictureUrl);
            getPref(PreferenceHelper.Id, "");
            editor.remove(Id);
            getPref(PreferenceHelper.Id, "");
            editor.remove(ClassName);
            editor.commit();
            getPref(PreferenceHelper.Id, "");
            System.out.print("asdsadsad");

        } catch (Exception exp) {
            Log.d(TAG, "Exp: " + exp.getMessage());
        }

    }

    public void ClearPreferencesID() {
        try {
            SharedPreferences.Editor editor = sharedPreference.edit();
            editor = sharedPreference.edit();

            getPref(PreferenceHelper.Id, "");
            editor.remove(Id);
            getPref(PreferenceHelper.Id, "");

            editor.commit();
            getPref(PreferenceHelper.Id, "");
            System.out.print("asdsadsad");

        } catch (Exception exp) {
            Log.d(TAG, "Exp: " + exp.getMessage());
        }

    }

}
