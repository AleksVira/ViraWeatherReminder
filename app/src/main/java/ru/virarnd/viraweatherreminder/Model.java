package ru.virarnd.viraweatherreminder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ru.virarnd.viraweatherreminder.common.City;
import ru.virarnd.viraweatherreminder.common.Forecast;
import ru.virarnd.viraweatherreminder.common.ForecastManager;
import ru.virarnd.viraweatherreminder.common.Notification;
import ru.virarnd.viraweatherreminder.common.Settings;

class Model {
    private Settings settings;
    private final ForecastManager forecastManager;

    public Model() {
        this.forecastManager = new ForecastManager();
        this.settings = Settings.getInstance();

        // TODO Позже настройки будут считываться из сохраненных данных
        this.settings = settings.defaultSettings();
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

    public ArrayList<Notification> getNotificationsList() {
        ArrayList<Notification> notifications = new ArrayList<>();

        notifications.add(new Notification("Прогулка в парке", new City(1, "Москва", "ru"),
                new GregorianCalendar(2018, Calendar.OCTOBER, 20, 15, 15, 0),
                new GregorianCalendar(2018, Calendar.OCTOBER, 20, 13, 15, 0)));

        notifications.add(new Notification("Пикник", new City(1, "Москва", "ru"),
                new GregorianCalendar(2018, Calendar.OCTOBER, 15, 11, 0, 0),
                new GregorianCalendar(2018, Calendar.OCTOBER, 15, 9, 0, 0)));

        notifications.add(new Notification("Дача", new City(1, "Москва", "ru"),
                new GregorianCalendar(2018, Calendar.NOVEMBER, 9, 9, 0, 0),
                new GregorianCalendar(2018, Calendar.NOVEMBER, 9, 7, 0, 0)));

        notifications.add(new Notification("Пробежка", new City(1, "Москва", "ru"),
                new GregorianCalendar(2018, Calendar.OCTOBER, 25, 7, 0, 0),
                new GregorianCalendar(2018, Calendar.OCTOBER, 25, 5, 0, 0)));

        notifications.add(new Notification("Прогулка в парке", new City(1, "Москва", "ru"),
                new GregorianCalendar(2018, Calendar.OCTOBER, 25, 19, 0, 0),
                new GregorianCalendar(2018, Calendar.OCTOBER, 25, 16, 30, 0)));

        notifications.add(new Notification("Пробежка", new City(1, "Москва", "ru"),
                new GregorianCalendar(2018, Calendar.OCTOBER, 22, 7, 0, 0),
                new GregorianCalendar(2018, Calendar.OCTOBER, 22, 5, 0, 0)));

        notifications.add(new Notification("Прогулка в парке", new City(1, "Москва", "ru"),
                new GregorianCalendar(2018, Calendar.OCTOBER, 22, 19, 0, 0),
                new GregorianCalendar(2018, Calendar.OCTOBER, 22, 16, 30, 0)));
        return notifications;
    }
}
