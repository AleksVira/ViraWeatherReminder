package ru.virarnd.viraweatherreminder.common;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

public class MyApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApp.context;
    }
}
