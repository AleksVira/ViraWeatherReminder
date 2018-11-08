package ru.virarnd.viraweatherreminder.common;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

public class MyApp extends Application {

    private static MyApp instance;

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }

    public static Context getContext(){
//        return instance;
         return instance.getApplicationContext();
    }

/*
    public static MyApp getInstance() {
        if (instance == null) {
            instance = new MyApp();
        }
        return instance;
    }
*/
}


