package ru.virarnd.viraweatherreminder.common;

import java.util.ArrayList;
import java.util.Date;

interface WeatherDataRequester {

    Forecast getCurrentForecast(int Id);

    public Forecast getForecastByDate(int Id, Date date);

    public ArrayList<Forecast> getFiveDaysForecast(int Id);

}
