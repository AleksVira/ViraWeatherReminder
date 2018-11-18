package ru.virarnd.viraweatherreminder;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ru.virarnd.viraweatherreminder.common.City;
import ru.virarnd.viraweatherreminder.common.CurrentWeather;
import ru.virarnd.viraweatherreminder.common.ForecastHistory;
import ru.virarnd.viraweatherreminder.common.ForecastDataManager;
import ru.virarnd.viraweatherreminder.common.MyApp;
import ru.virarnd.viraweatherreminder.common.Notification;
import ru.virarnd.viraweatherreminder.common.Settings;

import static android.content.Context.MODE_PRIVATE;

public class Model {

    private final static String TAG = Model.class.getName();
    public final static String SHARED_PREFERENCES_SETTINGS = "settings_preferences";

    private static Model instance;
//    private Settings settings;
    private final ForecastDataManager forecastDataManager;
    private ArrayList<Notification> notifications;
    private SharedPreferences globalPreferences;

    private Model() {
        this.forecastDataManager = new ForecastDataManager();
//        this.settings = Settings.getInstance();

    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public CurrentWeather getForecastByTownName(String townName) {
        CurrentWeather currentWeather;
        if (townName.equals("Moscow") || townName.equals("Москва")) {
            currentWeather = forecastDataManager.getCurrentForecastById(524901);
        } else if (townName.equals("Saint Petersburg") || townName.equals("Санкт-Петербург")) {
            currentWeather = forecastDataManager.getCurrentForecastById(498817);
        } else if (townName.equals("Rostov-na-Donu") || townName.equals("Ростов-на-Дону")) {
            currentWeather = forecastDataManager.getCurrentForecastById(501175);
        } else {
            currentWeather = forecastDataManager.getCurrentForecastById(524901);
        }
        return currentWeather;
    }

    public City getCityById(int cityId) {
        switch (cityId) {
            case 524901:
                return new City(524901, "Moscow");
            case 498817:
                return new City(498817, "Saint Petersburg");
            case 501175:
                return new City(501175, "Rostov-na-Donu");
            default:
                return new City(524901, "Moscow");
        }
    }

    City getCityIdByName(String testName) {
        switch (testName) {
            case "Moscow":
                return new City(524901, "Moscow");
            case "Saint Petersburg":
                return new City(498817, "Saint Petersburg");
            case "Rostov-na-Donu":
                return new City(501175, "Rostov-na-Donu");
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
        cities.add(new City(524901, "Moscow"));
        cities.add(new City(501175, "Rostov-na-Donu"));
        cities.add(new City(498817, "Saint Petersburg"));
        return cities;
    }

    public CurrentWeather getForecastByCityId(int cityId) {
        return forecastDataManager.getCurrentForecastById(cityId);
    }

    public ArrayList<Notification> getNotificationsList() {
        if (notifications == null) {
            notifications = new ArrayList<>();
            notifications.add(new Notification("Asd", new City(524901, "Moscow", ""),
                    new GregorianCalendar(2018, Calendar.OCTOBER, 28, 19, 0, 0),
                    new GregorianCalendar(2018, Calendar.OCTOBER, 28, 17, 0, 0)));

            notifications.add(new Notification("Прогулка в парке", new City(524901, "Москва", "ru"),
                    new GregorianCalendar(2018, Calendar.OCTOBER, 20, 15, 15, 0),
                    new GregorianCalendar(2018, Calendar.OCTOBER, 20, 13, 15, 0)));

            notifications.add(new Notification("Пикник", new City(524901, "Москва", "ru"),
                    new GregorianCalendar(2018, Calendar.OCTOBER, 15, 11, 0, 0),
                    new GregorianCalendar(2018, Calendar.OCTOBER, 15, 9, 0, 0)));

            notifications.add(new Notification("Дача", new City(524901, "Москва", "ru"),
                    new GregorianCalendar(2018, Calendar.NOVEMBER, 9, 9, 0, 0),
                    new GregorianCalendar(2018, Calendar.NOVEMBER, 9, 7, 0, 0)));

            notifications.add(new Notification("Пробежка", new City(524901, "Москва", "ru"),
                    new GregorianCalendar(2018, Calendar.OCTOBER, 25, 7, 0, 0),
                    new GregorianCalendar(2018, Calendar.OCTOBER, 25, 5, 0, 0)));

            notifications.add(new Notification("Прогулка в парке", new City(524901, "Москва", "ru"),
                    new GregorianCalendar(2018, Calendar.OCTOBER, 25, 19, 0, 0),
                    new GregorianCalendar(2018, Calendar.OCTOBER, 25, 16, 30, 0)));

            notifications.add(new Notification("Пробежка", new City(524901, "Москва", "ru"),
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

            CurrentWeather dailyHistoryForecast = forecastDataManager.getForecastByCityIdAndDay(cityId, i);

            history.getForecastMap().put(myCal, dailyHistoryForecast);
        }
//        Log.d(TAG, "History finished");
        return history;
    }

    Settings loadCommonSharedPreferences() {
        Settings mySettings = Settings.getInstance();
        SharedPreferences globalPreferences = MyApp.getContext().getSharedPreferences(SHARED_PREFERENCES_SETTINGS, MODE_PRIVATE);

        mySettings.setWindSpeedVisible(globalPreferences.getBoolean("WindSpeedVisible", false));
        mySettings.setPressureVisible(globalPreferences.getBoolean("PressureState", false));
        mySettings.setHumidityVisible(globalPreferences.getBoolean("HumidityState", false));

        mySettings.setTemperatureUnit(globalPreferences.getString("TemperatureUnit", MyApp.getContext().getString(R.string.celcius)));
        mySettings.setWindSpeedUnit(globalPreferences.getString("WindSpeedUnit", MyApp.getContext().getString(R.string.speed_ms)));
        mySettings.setPressureUnit(globalPreferences.getString("PressureUnit", MyApp.getContext().getString(R.string.pressure_mb)));
        mySettings.setHumidityUnit(MyApp.getContext().getString(R.string.percents));

        return mySettings;
    }


    public void tryUpdateRecordInDb(CurrentWeather currentWeather, String today) {
        forecastDataManager.addRecordCurrentWeather(currentWeather, today);
    }
}
