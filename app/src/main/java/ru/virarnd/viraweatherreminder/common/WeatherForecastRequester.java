package ru.virarnd.viraweatherreminder.common;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import static ru.virarnd.viraweatherreminder.common.AskOpenWeatherService.ACTION_ASK_CURRENT_WEATHER;

public class WeatherForecastRequester implements WeatherDataRequester {

    private final static String TAG = WeatherForecastRequester.class.getName();

    static final String ACTION_LABEL = "Action label";
    static final String CITY_ID = "CITY_ID";

    @Override
    public CurrentWeather askNewCurrentForecast(int Id) {
        Context context = MyApp.getContext();
        Intent intent = new Intent(context, AskOpenWeatherService.class);
        intent.putExtra(ACTION_LABEL, ACTION_ASK_CURRENT_WEATHER);
        intent.putExtra(CITY_ID, Id);
        context.startService(intent);
        Log.d(TAG, "Запрос на погоду отправлен в сервис!");
        // TODO Делать запрос к БД и брать оттуда старый "текущий" прогноз
        // Запрос ушел на сервер, но могу возвращать данные из ранее сохраненных в БД, например.

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
    public CurrentWeather getForecastByDate(int Id, Date date) {
        return null;
    }

    @Override
    public ArrayList<CurrentWeather> getFiveDaysForecast(int Id) {
        return null;
    }


}
