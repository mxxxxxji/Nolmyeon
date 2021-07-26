package com.example.nolmyeon;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class PreferencesManager {
    String PREFERENCES_NAME = "NOLMYEON";



    public void setPreferences(Context context, String key, int value){
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putInt(key, value);

        prefEditor.commit();
    }

    public int getIntPreferences(Context context, String key){
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return pref.getInt(key, 0);
    }

    public void setPreferences(Context context, String key, String value){
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putString(key, value);

        prefEditor.commit();
    }

    public String getStringPreferences(Context context, String key){
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return pref.getString(key, "");
    }


    public void setPreferences(Context context, String key, Float value){
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putFloat(key, value);

        prefEditor.commit();
    }

    public Float getFloatPreferences(Context context, String key){
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return pref.getFloat(key, 0.0F);
    }
}
