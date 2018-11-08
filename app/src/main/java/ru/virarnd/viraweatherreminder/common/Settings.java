package ru.virarnd.viraweatherreminder.common;

import android.content.Context;

import ru.virarnd.viraweatherreminder.R;

public class Settings {

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

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
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

    public Settings defaultSettings() {
        Settings defaultSettings = Settings.getInstance();
        isWindSpeedVisible = true;
        isPressureVisible = true;
        isHumidityVisible = true;

        temperatureUnit = MyApp.getContext().getString(R.string.celcius);
        temperatureUnit = MyApp.getContext().getString(R.string.celcius);
        windSpeedUnit = MyApp.getContext().getString(R.string.speed_ms);
//        pressureUnit = MyApp.getContext().getString(R.string.pressure_mm);
        pressureUnit = MyApp.getContext().getString(R.string.pressure_mb);
        humidityUnit = MyApp.getContext().getString(R.string.percents);

        return defaultSettings;
    }


}
