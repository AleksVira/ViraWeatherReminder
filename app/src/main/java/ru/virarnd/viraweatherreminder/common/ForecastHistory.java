package ru.virarnd.viraweatherreminder.common;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.TreeMap;

public class ForecastHistory {
    private City city;
    private TreeMap<GregorianCalendar, CurrentWeather> forecastMap;

    public ForecastHistory(City city) {
        this.city = city;
        this.forecastMap = new TreeMap<>();
    }

    public City getCity() {
        return city;
    }

    public TreeMap<GregorianCalendar, CurrentWeather> getForecastMap() {
        return forecastMap;
    }

    public ArrayList<GregorianCalendar> getForecastDaysList() {
        return new ArrayList<>(forecastMap.keySet());
    }



}
