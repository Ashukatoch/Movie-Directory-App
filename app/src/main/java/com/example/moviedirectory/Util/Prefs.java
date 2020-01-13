package com.example.moviedirectory.Util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs
{
    SharedPreferences sharedPreferences;

    public Prefs(Activity activity) {
        sharedPreferences=activity.getPreferences(activity.MODE_PRIVATE);
    }
    public void setsearch(String search)
    {
        sharedPreferences.edit().putString("search",search).commit();
    }
    public String getsearch()
    {
        return sharedPreferences.getString("search","Batman");
    }
}
