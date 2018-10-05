package ru.virarnd.viraweatherreminder;

import ru.virarnd.viraweatherreminder.common.Forecast;
import ru.virarnd.viraweatherreminder.common.SimpleWeatherDataStorage;

public class Model {
    private SimpleWeatherDataStorage dataStorage;

    public Model() {
        this.dataStorage = new SimpleWeatherDataStorage();
    }

    public Forecast getForecastByTownName(String townName) {
        Forecast forecast;
        if (townName.equals("Moscow") || townName.equals("Москва")) {
            forecast = dataStorage.getCurrentForecast(1);
        } else if (townName.equals("Saint Petersburg") || townName.equals("Санкт-Петербург")) {
            forecast = dataStorage.getCurrentForecast(2);
        } else if (townName.equals("Rostov-na-Donu") || townName.equals("Ростов-на-Дону")) {
            forecast = dataStorage.getCurrentForecast(3);
        } else {
            forecast = dataStorage.getCurrentForecast(1);
        }
        return forecast;
    }
}
