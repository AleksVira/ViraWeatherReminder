package ru.virarnd.viraweatherreminder;

import java.util.ArrayList;

import ru.virarnd.viraweatherreminder.common.City;
import ru.virarnd.viraweatherreminder.common.Forecast;
import ru.virarnd.viraweatherreminder.common.ForecastManager;

public class Model {
    private ForecastManager forecastManager;

    public Model() {
        this.forecastManager = new ForecastManager();
    }

    public Forecast getForecastByTownName(String townName) {
        Forecast forecast;
        if (townName.equals("Moscow") || townName.equals("Москва")) {
            forecast = forecastManager.getCurrentForecastById(1);
        } else if (townName.equals("Saint Petersburg") || townName.equals("Санкт-Петербург")) {
            forecast = forecastManager.getCurrentForecastById(2);
        } else if (townName.equals("Rostov-na-Donu") || townName.equals("Ростов-на-Дону")) {
            forecast = forecastManager.getCurrentForecastById(3);
        } else {
            forecast = forecastManager.getCurrentForecastById(1);
        }
        return forecast;
    }

    public ArrayList<City> getLastUsedCityList() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City(0, "Select new city"));
        cities.add(new City(1, "Moscow"));
        cities.add(new City(3, "Rostov-na-Donu"));
        cities.add(new City(2, "Saint Petersburg"));
        return cities;
    }

    public Forecast getForecastById(int cityId) {
        return forecastManager.getCurrentForecastById(cityId);
    }
}
