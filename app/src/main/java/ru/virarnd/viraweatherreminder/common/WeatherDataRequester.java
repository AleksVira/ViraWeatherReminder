package ru.virarnd.viraweatherreminder.common;

import java.util.ArrayList;
import java.util.Date;

public interface WeatherDataRequester {

    CurrentWeather askNewCurrentForecast(int Id);

    CurrentWeather getForecastByDate(int Id, Date date);

    ArrayList<CurrentWeather> getFiveDaysForecast(int Id);

}
