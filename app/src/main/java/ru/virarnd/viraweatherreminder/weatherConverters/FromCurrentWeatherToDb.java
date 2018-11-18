package ru.virarnd.viraweatherreminder.weatherConverters;

import ru.virarnd.viraweatherreminder.common.CurrentWeather;
import ru.virarnd.viraweatherreminder.dbase.DbWeather;

public class FromCurrentWeatherToDb {

    /* Конвертер представления из CurrentWeather в DbWeather
     * (тот вид что хранится в базе данных)
     *
     **/

    private CurrentWeather currentWeather;

    public FromCurrentWeatherToDb(CurrentWeather sourceCurrentWeather) {
        this.currentWeather = sourceCurrentWeather;
    }

    public DbWeather convert(CurrentWeather currentWeather) {
        DbWeather result = new DbWeather();

        return result;
    }
}
