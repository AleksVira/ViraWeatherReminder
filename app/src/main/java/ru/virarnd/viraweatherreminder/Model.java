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
        switch (townName) {
            case "Moscow":
                forecast = dataStorage.getCurrentForecast(1);
                break;
            case "Saint Petersburg":
                forecast = dataStorage.getCurrentForecast(2);
                break;
            case "Rostov-na-Donu":
                forecast = dataStorage.getCurrentForecast(3);
                break;
            default:
                forecast = dataStorage.getCurrentForecast(1);
        }
        return forecast;
    }
}
