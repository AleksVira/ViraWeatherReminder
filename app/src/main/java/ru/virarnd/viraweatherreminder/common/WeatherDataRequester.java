package ru.virarnd.viraweatherreminder.common;

import java.util.ArrayList;
import java.util.Date;

public interface WeatherDataRequester {

    DailyForecast getCurrentForecast(int Id);

    DailyForecast getForecastByDate(int Id, Date date);

    ArrayList<DailyForecast> getFiveDaysForecast(int Id);

}
