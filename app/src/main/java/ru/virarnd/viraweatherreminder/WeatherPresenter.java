package ru.virarnd.viraweatherreminder;

import java.util.ArrayList;

import ru.virarnd.viraweatherreminder.common.City;
import ru.virarnd.viraweatherreminder.common.Forecast;

class WeatherPresenter {

    private static WeatherPresenter instance = null;
    private FirstActivity activity;
    private final Model model = new Model();

    static WeatherPresenter getInstance() {
        if (instance == null) {
            instance = new WeatherPresenter();
        }
        return instance;
    }


    public ArrayList<City> getLastUsedCityList() {
        return model.getLastUsedCityList();
    }

    public void setCityAndShowDetail(int cityId) {
        if (cityId != 0) {
            Forecast cityForecast = model.getForecastById(cityId);
            activity.showForecast(cityForecast);
        }
    }

    public void attachView(FirstActivity firstActivity) {
        activity = firstActivity;
    }

    public void detachView() {
        if (activity != null) {
            activity = null;
        }
    }


}
