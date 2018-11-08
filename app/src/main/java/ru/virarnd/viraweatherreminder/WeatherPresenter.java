package ru.virarnd.viraweatherreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import ru.virarnd.viraweatherreminder.common.City;
import ru.virarnd.viraweatherreminder.common.DailyForecast;
import ru.virarnd.viraweatherreminder.common.ForecastHistory;
import ru.virarnd.viraweatherreminder.common.MyApp;
import ru.virarnd.viraweatherreminder.common.Notification;
import ru.virarnd.viraweatherreminder.common.Settings;

import static ru.virarnd.viraweatherreminder.common.AskOpenWeatherService.DAILY_FORECAST;
import static ru.virarnd.viraweatherreminder.common.AskOpenWeatherService.INTENT_RESULT;

class WeatherPresenter {

    private final static String TAG = WeatherPresenter.class.getName();

    // TODO Сейчас все команды передаются отдельно. Можно ли это улучшить? Через какой-то интерфейс или другой класс...
    // Чтобы можно было подключить другой View с таким же набором команд. Или оставить как есть?


    // Набор констант для определения состояния переключателей в настройках
    final static int SHOW_WIND_SPEED_ON = 388;
    final static int SHOW_WIND_SPEED_OFF = 535;
    final static int SHOW_PRESSURE_ON = 48;
    final static int SHOW_PRESSURE_OFF = 794;
    final static int SHOW_HUMIDITY_ON = 707;
    final static int SHOW_HUMIDITY_OFF = 425;

    final static int TEMPERATURE_C = 783;
    final static int TEMPERATURE_F = 3821;
    final static int WIND_SPEED_MS = 209;
    final static int WIND_SPEED_MH = 973;
    final static int PRESSURE_MM = 991;
    final static int PRESSURE_MBAR = 33;


    private static WeatherPresenter instance = null;
    private FirstActivity firstActivity;
    private Settings settings = Settings.getInstance();
    private final Model model = Model.getInstance();
    private ArrayList<Notification> notificationList;

    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver broadcastReceiver;

    private WeatherPresenter() {
    }

    static WeatherPresenter getInstance() {
        if (instance == null) {
            instance = new WeatherPresenter();
        }
        return instance;
    }


    ArrayList<City> getLastUsedCityList() {
        return model.getLastUsedCityList();
    }

    ArrayList<City> getLastUsedCityListWithSelectNew() {
        return model.getLastUsedCityListWithSelectNew();
    }

    void setCityAndShowDetail(int cityId) {
        if (cityId != 0) {
            DailyForecast cityDailyForecast = model.getForecastByCityId(cityId);
            firstActivity.showForecast(cityId, cityDailyForecast);
        }
    }

    void attachMainView(FirstActivity firstActivity) {
        this.firstActivity = firstActivity;
        localBroadcastManager = LocalBroadcastManager.getInstance(MyApp.getContext());
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "Got service result!");
                if (intent != null && intent.getExtras().containsKey(DAILY_FORECAST)) {
                    DailyForecast dailyForecast = intent.getParcelableExtra(DAILY_FORECAST);
                    WeatherPresenter.this.firstActivity.tryUpdateCurrentForecast(dailyForecast);
                }
            }
        };
        localBroadcastManager.registerReceiver(broadcastReceiver, new IntentFilter(INTENT_RESULT));

    }

    void detachMainView() {
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
        if (firstActivity != null) {
            firstActivity = null;
        }
    }

    void attachSettingsView(SettingsActivity settingsActivity) {


    }




    public void sendCheckBoxState(int condition) {
        switch (condition) {
            case SHOW_WIND_SPEED_ON:
                settings.setWindSpeedVisible(true);
                break;
            case SHOW_WIND_SPEED_OFF:
                settings.setWindSpeedVisible(false);
                break;
            case SHOW_PRESSURE_ON:
                settings.setPressureVisible(true);
                break;
            case SHOW_PRESSURE_OFF:
                settings.setPressureVisible(false);
                break;
            case SHOW_HUMIDITY_ON:
                settings.setHumidityVisible(true);
                break;
            case SHOW_HUMIDITY_OFF:
                settings.setHumidityVisible(false);
                break;
            case TEMPERATURE_C:
                settings.setTemperatureUnit(firstActivity.getApplicationContext().getString(R.string.celcius));
                break;
            case TEMPERATURE_F:
                settings.setTemperatureUnit(firstActivity.getApplicationContext().getString(R.string.fahrenheit));
                break;
            case WIND_SPEED_MS:
                settings.setWindSpeedUnit(firstActivity.getApplicationContext().getString(R.string.speed_ms));
                break;
            case WIND_SPEED_MH:
                settings.setWindSpeedUnit(firstActivity.getApplicationContext().getString(R.string.speed_miles_hour));
                break;
            case PRESSURE_MM:
                settings.setPressureUnit(firstActivity.getApplicationContext().getString(R.string.pressure_mm));
                break;
            case PRESSURE_MBAR:
                settings.setPressureUnit(firstActivity.getApplicationContext().getString(R.string.pressure_mb));
                break;
            default:
                break;
        }
    }

    void sendButtonPressed(int buttonId) {
        if (buttonId == R.id.btNotifications) {
            openNotificationsFragment();
        }
    }

    void openNotificationsFragment() {
        notificationList = model.getNotificationsList();
        firstActivity.showNotifications(notificationList);
    }

    void openSettingsFragment() {
        // TODO Добавить новый фрагмент "Настройки"
    }


    void sendButtonPressedAndCityId(int buttonId, int cityId) {
        if (buttonId == R.id.btHistory) {
            firstActivity.showHistoryForecast(cityId);
        }
    }

    ForecastHistory getForecastHistoryByCityId(int cityId) {
        return model.getForecastHistoryByCityId(cityId, new GregorianCalendar());
    }

    void sendNewNotificationFabClicked() {
        firstActivity.showCreateNewNotificationFragment();
    }

    City askPresenterCityByName(String cityName) {
        return model.getCityIdByName(cityName);
    }

    boolean checkNewNotification(String newNotificationName, City newNotificationCity, GregorianCalendar newNotificationDate) {
        Notification testNotification = new Notification(newNotificationName, newNotificationCity, newNotificationDate);
        for (Notification notification : notificationList) {
            if (testNotification.equals(notification)) {
                return true;
            }
        }
        return false;
    }

    public void addNewNotification(String newNotificationName, City newNotificationCity, GregorianCalendar newNotificationDate, GregorianCalendar newCheckNotificationTime) {
        Notification newNotification = new Notification(newNotificationName, newNotificationCity, newNotificationDate, newCheckNotificationTime);
        notificationList.add(newNotification);
        firstActivity.closeCreationFragment();
    }

/*
    @Override
    public void onCurrentForecastUpdate(DailyForecast forecast) {
        firstActivity.tryUpdateCurrentForecast(forecast);
    }
*/

}
