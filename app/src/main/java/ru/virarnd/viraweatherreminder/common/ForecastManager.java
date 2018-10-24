package ru.virarnd.viraweatherreminder.common;

import java.util.Stack;

public class ForecastManager {
    private final WeatherDataRequester weatherDataRequester;
    private Stack<String> lastUsedCities;                   // Стэк для хранения последних использованных городов, размер 5

    public ForecastManager() {
        weatherDataRequester = new SimpleWeatherDataStorage();
        lastUsedCities = new Stack<>();
        lastUsedCities.setSize(5);
    }

    public Stack<String> getLastUsedCities() {
        return lastUsedCities;
    }

    public void setLastUsedCities(Stack<String> lastUsedCities) {
        this.lastUsedCities = lastUsedCities;
    }

    public City[] getLastUsedCitiesArray() {
        // Для примитивного случая достаточно взять список городов из заполненных данных
        // В более сложном случае надо этим стэком управлять: создавать (или восстанавливать сохраненный), добавлять и извлекать элементы.
        if (weatherDataRequester instanceof SimpleWeatherDataStorage) {
            return ((SimpleWeatherDataStorage) weatherDataRequester).getCitiesArray();
        }
        return lastUsedCities.toArray(new City[lastUsedCities.size()]);
    }

    // Возвращает прогноз на один из пяти дней относительно текущего
    // dayFromCurrent -- сдвиг относительно текущего дня
    public DailyForecast getForecastByCityIdAndDay(int cityId, int dayFromCurrent) {
        return weatherDataRequester.getFiveDaysForecast(cityId).get(dayFromCurrent);
    }

    public DailyForecast getCurrentForecastById(int cityId) {
        return weatherDataRequester.getCurrentForecast(cityId);
    }
}
