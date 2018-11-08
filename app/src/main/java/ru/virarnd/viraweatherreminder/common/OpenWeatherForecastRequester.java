package ru.virarnd.viraweatherreminder.common;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import static ru.virarnd.viraweatherreminder.common.AskOpenWeatherService.ACTION_ASK_DAILY_FORECAST;

public class OpenWeatherForecastRequester implements WeatherDataRequester {

    private final static String TAG = OpenWeatherForecastRequester.class.getName();

    static final String ACTION_LABEL = "Action label";
    static final String CITY_ID = "CITY_ID";

    @Override
    public DailyForecast getCurrentForecast(int Id) {
        Context context = MyApp.getContext();
        Intent intent = new Intent(context, AskOpenWeatherService.class);
        intent.putExtra(ACTION_LABEL, ACTION_ASK_DAILY_FORECAST);
        intent.putExtra(CITY_ID, Id);
        context.startService(intent);
        Log.d(TAG, "Запрос на погоду отправлен в сервис!");
/*
        DailyForecast spb1 = new DailyForecast.Builder(2, "Saint PetersburG")
                .dayTemp(15).nightTemp(10)
                .windDirection(6).windSpeed(6)
                .humidity(95)
                .pressure(966.06)
                .weatherConditions(3)
                .build();
*/
        return null;
    }



    private void startAskForecastService(Context context) {
        Intent intent = new Intent(context, AskOpenWeatherService.class);
        context.startService(intent);
    }

    private void stopAskForecastService(Context context) {
        Intent intent = new Intent(context, AskOpenWeatherService.class);
        context.stopService(intent);
    }

    @Override
    public DailyForecast getForecastByDate(int Id, Date date) {
        return null;
    }

    @Override
    public ArrayList<DailyForecast> getFiveDaysForecast(int Id) {
        return null;
    }


}
