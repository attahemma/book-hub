package com.example.booklistactivity;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SpUtil {
    private SpUtil(){}

    public static final String PREF_NAME = "BookPreferences";
    public static final String POSITION = "position";
    public static final String QUERY = "query";

    public static SharedPreferences getSharedPrefs(Context context){
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getPrefData(Context context, String key){
        return getSharedPrefs(context).getString(key, "");
    }

    public static int getPrefInt(Context context, String key){
        return getSharedPrefs(context).getInt(key,0);
    }

    public static void writeStringData(Context context, String key, String value){
        SharedPreferences sp = getSharedPrefs(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void writeIntData(Context context, String key, int value){
        SharedPreferences sp = getSharedPrefs(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static ArrayList<String> getQueryList(Context context){
        ArrayList<String> queryList = new ArrayList<>();
        for (int i =0; i<=5;i++){
            String query = getSharedPrefs(context).getString(QUERY+String.valueOf(i), "");
            if (! query.isEmpty() ){
                query = query.replace(","," ");
                queryList.add(query.trim());
            }
        }
        return queryList;
    }
}
