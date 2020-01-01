package com.notesapp.ghadacoder2015.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class AppControl extends Application {
    public static SharedPreferences sharedpreferences;
    public static SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedpreferences = getSharedPreferences("NotesApp", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();


    }

}