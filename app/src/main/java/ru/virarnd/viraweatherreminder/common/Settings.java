package ru.virarnd.viraweatherreminder.common;

import android.content.SharedPreferences;

import ru.virarnd.viraweatherreminder.R;

import static android.content.Context.MODE_PRIVATE;

public class Settings {

    private final static String SHARED_PREFERENCES_SETTINGS = "settings_preferences";

    // Настройки параметров отображения подробного прогноза
    private boolean isTemperatureVisible = true;
    private boolean isWindSpeedVisible;
    private boolean isPressureVisible;
    private boolean isHumidityVisible;

    // Настройки единиц измерения параметров подробного прогноза
    private String temperatureUnit;
    private String windSpeedUnit;
    private String pressureUnit;
    private String humidityUnit;

    private static Settings instance;

    private Settings() {
    }

/*
    public Settings loadSettings() {
        SharedPreferences globalPreferences = MyApp.getContext().getSharedPreferences(SHARED_PREFERENCES_SETTINGS, MODE_PRIVATE);
        Settings mySettings = getInstance();

        if (globalPreferences != null) {
//            isWindSpeedVisible = globalPreferences.getBoolean("WindSpeedVisible", false);
//            isPressureVisible = globalPreferences.getBoolean("PressureState", false);
//            isHumidityVisible = globalPreferences.getBoolean("HumidityState", false);

//            temperatureUnit = globalPreferences.getString("TemperatureUnit", MyApp.getContext().getString(R.string.celcius));
//            windSpeedUnit = globalPreferences.getString("WindSpeedUnit", MyApp.getContext().getString(R.string.speed_ms));
//            pressureUnit = globalPreferences.getString("PressureUnit", MyApp.getContext().getString(R.string.pressure_mb));
//            humidityUnit = MyApp.getContext().getString(R.string.percents);

            mySettings.setWindSpeedVisible(globalPreferences.getBoolean("WindSpeedVisible", false));
            mySettings.setPressureVisible(globalPreferences.getBoolean("PressureState", false));
            mySettings.setHumidityVisible(globalPreferences.getBoolean("HumidityState", false));

            mySettings.setTemperatureUnit(globalPreferences.getString("TemperatureUnit", MyApp.getContext().getString(R.string.celcius)));
            mySettings.setWindSpeedUnit(globalPreferences.getString("WindSpeedUnit", MyApp.getContext().getString(R.string.speed_ms)));
            mySettings.setPressureUnit(globalPreferences.getString("PressureUnit", MyApp.getContext().getString(R.string.pressure_mb)));
            mySettings.setHumidityUnit(MyApp.getContext().getString(R.string.percents));
//            instance.humidityUnit = MyApp.getContext().getString(R.string.percents);
        }

        return mySettings;


    }
*/

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
            // Влажность всегда в процентах
//            instance.humidityUnit = MyApp.getContext().getString(R.string.percents);
        }
        return instance;

    }

    public boolean isWindSpeedVisible() {
        return isWindSpeedVisible;
    }

    public void setWindSpeedVisible(boolean windSpeedVisible) {
        isWindSpeedVisible = windSpeedVisible;
    }

    public boolean isPressureVisible() {
        return isPressureVisible;
    }

    public void setPressureVisible(boolean pressureVisible) {
        isPressureVisible = pressureVisible;
    }

    public boolean isHumidityVisible() {
        return isHumidityVisible;
    }

    public void setHumidityVisible(boolean humidityVisible) {
        isHumidityVisible = humidityVisible;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public String getWindSpeedUnit() {
        return windSpeedUnit;
    }

    public void setWindSpeedUnit(String windSpeedUnit) {
        this.windSpeedUnit = windSpeedUnit;
    }

    public String getPressureUnit() {
        return pressureUnit;
    }

    public void setPressureUnit(String pressureUnit) {
        this.pressureUnit = pressureUnit;
    }

    public String getHumidityUnit() {
        return humidityUnit;
    }

    public void setHumidityUnit(String humidityUnit) {
        this.humidityUnit = humidityUnit;
    }

}
