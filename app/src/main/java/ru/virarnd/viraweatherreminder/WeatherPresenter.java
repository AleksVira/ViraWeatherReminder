package ru.virarnd.viraweatherreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import ru.virarnd.viraweatherreminder.common.City;
import ru.virarnd.viraweatherreminder.common.CurrentWeather;
import ru.virarnd.viraweatherreminder.common.ForecastHistory;
import ru.virarnd.viraweatherreminder.common.MyApp;
import ru.virarnd.viraweatherreminder.common.Notification;
import ru.virarnd.viraweatherreminder.common.Settings;

import static android.content.Context.MODE_PRIVATE;
import static ru.virarnd.viraweatherreminder.Model.SHARED_PREFERENCES_SETTINGS;
import static ru.virarnd.viraweatherreminder.common.AskOpenWeatherService.CURRENT_FORECAST;
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
    private SettingsActivity settingsActivity;
    private final Settings settings = Settings.getInstance();
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
            CurrentWeather cityCurrentWeather = model.getForecastByCityId(cityId);
            firstActivity.showForecast(cityId, cityCurrentWeather);
        }
    }

    void attachMainView(FirstActivity firstActivity) {
        this.firstActivity = firstActivity;
        localBroadcastManager = LocalBroadcastManager.getInstance(MyApp.getContext());
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "Got service result!");
                if (intent != null && intent.getExtras().containsKey(CURRENT_FORECAST)) {
                    CurrentWeather currentWeather = intent.getParcelableExtra(CURRENT_FORECAST);
                    WeatherPresenter.this.firstActivity.tryUpdateCurrentForecast(currentWeather);

                    // Получаю сегодняшнюю дату в виде строки и обновляю в БД запись для этого города и этой даты
                    String today = new SimpleDateFormat("yyyyMMdd", Locale.US).format(new Date());
                    model.tryUpdateRecordInDb(currentWeather, today);
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
        this.settingsActivity = settingsActivity;
    }

    void detachSettingsView() {
        if (settingsActivity != null) {
            settingsActivity = null;
        }
    }



    public void sendCheckBoxState(int condition) {
        SharedPreferences globalPreferences = MyApp.getContext().getSharedPreferences(SHARED_PREFERENCES_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = globalPreferences.edit();

        switch (condition) {
            case SHOW_WIND_SPEED_ON:
                settings.setWindSpeedVisible(true);
                editor.putBoolean("WindSpeedVisible", true);
                break;
            case SHOW_WIND_SPEED_OFF:
                settings.setWindSpeedVisible(false);
                editor.putBoolean("WindSpeedVisible", false);
                break;
            case SHOW_PRESSURE_ON:
                settings.setPressureVisible(true);
                editor.putBoolean("PressureState", true);
                break;
            case SHOW_PRESSURE_OFF:
                settings.setPressureVisible(false);
                editor.putBoolean("PressureState", false);
                break;
            case SHOW_HUMIDITY_ON:
                settings.setHumidityVisible(true);
                editor.putBoolean("HumidityState", true);
                break;
            case SHOW_HUMIDITY_OFF:
                settings.setHumidityVisible(false);
                editor.putBoolean("HumidityState", false);
                break;
            case TEMPERATURE_C:
                settings.setTemperatureUnit(MyApp.getContext().getString(R.string.celcius));
                editor.putString("TemperatureUnit", MyApp.getContext().getString(R.string.celcius));
                break;
            case TEMPERATURE_F:
                settings.setTemperatureUnit(MyApp.getContext().getString(R.string.fahrenheit));
                editor.putString("TemperatureUnit", MyApp.getContext().getString(R.string.fahrenheit));
                break;
            case WIND_SPEED_MS:
                settings.setWindSpeedUnit(MyApp.getContext().getString(R.string.speed_ms));
                editor.putString("WindSpeedUnit", MyApp.getContext().getString(R.string.speed_ms));
                break;
            case WIND_SPEED_MH:
                settings.setWindSpeedUnit(MyApp.getContext().getString(R.string.speed_miles_hour));
                editor.putString("WindSpeedUnit", MyApp.getContext().getString(R.string.speed_miles_hour));
                break;
            case PRESSURE_MM:
                settings.setPressureUnit(MyApp.getContext().getString(R.string.pressure_mm));
                editor.putString("PressureUnit", MyApp.getContext().getString(R.string.pressure_mm));
                break;
            case PRESSURE_MBAR:
                settings.setPressureUnit(MyApp.getContext().getString(R.string.pressure_mb));
                editor.putString("PressureUnit", MyApp.getContext().getString(R.string.pressure_mb));
                break;
            default:
                break;
        }
        editor.apply();
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
    public Settings loadPrivateSettings(SettingsActivity settingsActivity) {
        return model.loadSettingsFromSharedPreferences(settingsActivity);
    }
*/

    public Settings loadSettings() {
        return model.loadCommonSharedPreferences();
    }

}
