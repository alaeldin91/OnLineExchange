package com.example.onlineexchangeratecalcultor.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
     Context context;
    private final String PREFName = "SharedPreferences";
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public SharedPref(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREFName, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public void putString(String key, String value) {
        editor.putString(key, value).apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }
}
