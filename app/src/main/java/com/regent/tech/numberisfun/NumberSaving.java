package com.regent.tech.numberisfun;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class NumberSaving {

    // Shared Preferences
    SharedPreferences preferences;

    // Context
    Context context;

    // Editor for Shared Preferences
    SharedPreferences.Editor editor;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared pref file name
    private static final String PREF_NAME = "NumberFact";

    public static final String KEY_NUMBER_FACTS = "number_facts";

    public NumberSaving(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void createSavingSession(String number_facts){
        editor.putString(KEY_NUMBER_FACTS, number_facts);

        editor.commit();
    }

    /**
     * Get stored data
     * @return hashMap
     */
    public HashMap<String, String> getNumberFact(){
        HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap.put(KEY_NUMBER_FACTS, preferences.getString(KEY_NUMBER_FACTS, null));

        return hashMap;
    }

    public void clearFacts(){
        editor.clear();
        editor.commit();
    }

}
