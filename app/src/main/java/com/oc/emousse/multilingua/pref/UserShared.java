package com.oc.emousse.multilingua.pref;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.oc.emousse.multilingua.LoginActivity;
import com.oc.emousse.multilingua.database.User;

import java.util.HashMap;

import io.realm.Realm;

/**
 * Created by emousse on 08/11/2016.
 */

public class UserShared {
    private static UserShared userShared;

    private  SharedPreferences sharedPreferences;
    private Context _context;
    private Editor editor;
    private static final String PREF_NAME = "UserShared";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_LAST_LESSON = "LastLessonTimestamp";

    private UserShared(Context context){
        this._context = context;
        sharedPreferences = _context.getSharedPreferences(PREF_NAME,0);
        editor = sharedPreferences.edit();
    }

    public static UserShared getInstance(Context context){
        if(userShared == null)
            userShared = new UserShared(context);
        return userShared;
    }

    //save timestamp in user shared
    public boolean setLastLessonTimestamp(long timestamp){
        editor.putLong(KEY_LAST_LESSON,timestamp);
        return editor.commit();
    }

    //get last timestamp
    public long getLastLessonTimestamp(){
        //by defaut "old" timestamp for unlock first lesson
        return sharedPreferences.getLong(KEY_LAST_LESSON, 1465171200);
    }

    public void createLoginSession(String email, String name){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NAME, name);
        editor.commit();
    }

    public String getEmail(){
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, sharedPreferences.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, sharedPreferences.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    public void logout(){
        //clear all data from shared preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }
}
