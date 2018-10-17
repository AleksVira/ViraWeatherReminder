package ru.virarnd.viraweatherreminder;

import java.util.ArrayList;

import ru.virarnd.viraweatherreminder.common.City;
import ru.virarnd.viraweatherreminder.common.Forecast;
import ru.virarnd.viraweatherreminder.common.Notification;
import ru.virarnd.viraweatherreminder.common.Settings;

class WeatherPresenter {

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
    private FirstActivity activity;
    private Settings settings = Settings.getInstance();
    private final Model model = new Model();

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

    void setCityAndShowDetail(int cityId) {
        if (cityId != 0) {
            Forecast cityForecast = model.getForecastById(cityId);
            activity.showForecast(cityForecast);
        }
    }

    void attachView(FirstActivity firstActivity) {
        activity = firstActivity;
    }

    void detachView() {
        if (activity != null) {
            activity = null;
        }
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
                settings.setTemperatureUnit(activity.getApplicationContext().getString(R.string.celcius));
                break;
            case TEMPERATURE_F:
                settings.setTemperatureUnit(activity.getApplicationContext().getString(R.string.fahrenheit));
                break;
            case WIND_SPEED_MS:
                settings.setWindSpeedUnit(activity.getApplicationContext().getString(R.string.speed_ms));
                break;
            case WIND_SPEED_MH:
                settings.setWindSpeedUnit(activity.getApplicationContext().getString(R.string.speed_miles_hour));
                break;
            case PRESSURE_MM:
                settings.setPressureUnit(activity.getApplicationContext().getString(R.string.pressure_mm));
                break;
            case PRESSURE_MBAR:
                settings.setPressureUnit(activity.getApplicationContext().getString(R.string.pressure_mb));
                break;
            default:
                break;


        }
    }

    public void sendButtonPressed(int buttonId) {
        if (buttonId == R.id.btNotifications) {
            ArrayList<Notification> notificationList = model.getNotificationsList();
            activity.showNotifications(notificationList);
        }


    }
}
