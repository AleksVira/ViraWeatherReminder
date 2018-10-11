package ru.virarnd.viraweatherreminder.common;

import java.util.ArrayList;
import java.util.Date;

public interface WeatherDataRequester {

    public Forecast getCurrentForecast(int Id);

    public Forecast getForecastByDate(int Id, Date date);

    public ArrayList<Forecast> getFiveDaysForecast(int Id);

}
