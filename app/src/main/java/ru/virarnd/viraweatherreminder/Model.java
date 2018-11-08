package ru.virarnd.viraweatherreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ru.virarnd.viraweatherreminder.common.City;
import ru.virarnd.viraweatherreminder.common.DailyForecast;
import ru.virarnd.viraweatherreminder.common.ForecastHistory;
import ru.virarnd.viraweatherreminder.common.ForecastManager;
import ru.virarnd.viraweatherreminder.common.MyApp;
import ru.virarnd.viraweatherreminder.common.Notification;
import ru.virarnd.viraweatherreminder.common.Settings;

import static ru.virarnd.viraweatherreminder.common.AskOpenWeatherService.DAILY_FORECAST;
import static ru.virarnd.viraweatherreminder.common.AskOpenWeatherService.INTENT_RESULT;

public class Model {

    private final static String TAG = Model.class.getName();

    private static Model instance;
    private Settings settings;
    private final ForecastManager forecastManager;
    private ArrayList<Notification> notifications;
//    private LocalBroadcastManager localBroadcastManager;
//    private BroadcastReceiver broadcastReceiver;

    private Model() {
        this.forecastManager = new ForecastManager();
        this.settings = Settings.getInstance();
/*
        this.localBroadcastManager = LocalBroadcastManager.getInstance(MyApp.getContext());
        this.broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "Got service result!");
                if (intent != null && intent.getExtras().containsKey(DAILY_FORECAST)) {
                    DailyForecast dailyForecast = intent.getParcelableExtra(DAILY_FORECAST);
                    onCurrentForecastUpdate(dailyForecast);
                }
            }
        };

        localBroadcastManager.registerReceiver(broadcastReceiver, new IntentFilter(INTENT_RESULT));
*/

        // TODO Позже настройки будут считываться из сохраненных данных
        this.settings = settings.defaultSettings();
    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public DailyForecast getForecastByTownName(String townName) {
        DailyForecast dailyForecast;
        if (townName.equals("Moscow") || townName.equals("Москва")) {
            dailyForecast = forecastManager.getCurrentForecastById(1);
        } else if (townName.equals("Saint Petersburg") || townName.equals("Санкт-Петербург")) {
            dailyForecast = forecastManager.getCurrentForecastById(2);
        } else if (townName.equals("Rostov-na-Donu") || townName.equals("Ростов-на-Дону")) {
            dailyForecast = forecastManager.getCurrentForecastById(3);
        } else {
            dailyForecast = forecastManager.getCurrentForecastById(1);
        }
        return dailyForecast;
    }

    public City getCityById(int cityId) {
        switch (cityId) {
            case 1:
                return new City(1, "Moscow");
            case 2:
                return new City(2, "Saint Petersburg");
            case 3:
                return new City(3, "Rostov-na-Donu");
            default:
                return new City(1, "Moscow");
        }
    }

    City getCityIdByName(String testName) {
        switch (testName) {
            case "Moscow":
                return new City(1, "Moscow");
            case "Saint Petersburg":
                return new City(2, "Saint Petersburg");
            case "Rostov-na-Donu":
                return new City(3, "Rostov-na-Donu");
            default:
                return null;
        }
    }

    ArrayList<City> getLastUsedCityListWithSelectNew() {
        ArrayList<City> cities = getLastUsedCityList();
        cities.add(new City(0, MyApp.getContext().getString(R.string.select_new_city)));
        return cities;
    }

    ArrayList<City> getLastUsedCityList() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City(1, "Moscow"));
        cities.add(new City(3, "Rostov-na-Donu"));
        cities.add(new City(2, "Saint Petersburg"));
        return cities;
    }

    public DailyForecast getForecastByCityId(int cityId) {
        return forecastManager.getCurrentForecastById(cityId);
    }

    public ArrayList<Notification> getNotificationsList() {
        if (notifications == null) {
            notifications = new ArrayList<>();
            notifications.add(new Notification("Asd", new City(1, "Moscow", ""),
                    new GregorianCalendar(2018, Calendar.OCTOBER, 28, 19, 0, 0),
                    new GregorianCalendar(2018, Calendar.OCTOBER, 28, 17, 0, 0)));

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
        }
        return notifications;
    }


    // Имитирую создание "истории прогноза" для города с определенным cityId. Создаю историю из записи за пять дней тому назад.
    ForecastHistory getForecastHistoryByCityId(int cityId, GregorianCalendar calendar) {
//        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yy");
        calendar.add(Calendar.DATE, -6);
        ForecastHistory history = new ForecastHistory(getCityById(cityId));
        for (int i = 0; i < 5; i++) {
            GregorianCalendar myCal = new GregorianCalendar(2018, 9, i + 10);
//            myCal = calendar;
//            myCal.add(Calendar.DATE, +1);
//            fmt.setCalendar(myCal);
//            String dateFormatted = fmt.format(myCal.getTime());
//            Log.d(TAG, dateFormatted);

            DailyForecast dailyHistoryForecast = forecastManager.getForecastByCityIdAndDay(cityId, i);

            history.getForecastMap().put(myCal, dailyHistoryForecast);
        }
//        Log.d(TAG, "History finished");
        return history;
    }


    public interface OnDailyForecastUpdate {
        void onCurrentForecastUpdate(DailyForecast forecast);

    }
}
