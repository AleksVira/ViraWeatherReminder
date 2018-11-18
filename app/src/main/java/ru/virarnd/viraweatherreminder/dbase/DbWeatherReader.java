package ru.virarnd.viraweatherreminder.dbase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Closeable;

import ru.virarnd.viraweatherreminder.common.CurrentWeather;

class DbWeatherReader implements Closeable {

    private Cursor cursor;
    private final SQLiteDatabase database;
    private final String[] weatherAllColumns = {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_CITY_ID,
            DatabaseHelper.COLUMN_CITY_NAME,
            DatabaseHelper.COLUMN_CITY_NAME_RUS,
            DatabaseHelper.COLUMN_DATE,
            DatabaseHelper.COLUMN_TEMPERATURE,
            DatabaseHelper.COLUMN_WIND_SPEED,
            DatabaseHelper.COLUMN_PRESSURE
    };


    DbWeatherReader(SQLiteDatabase database) {
        this.database = database;
    }


    void open() {
        query();
        cursor.moveToFirst();
    }

    void query() {
        cursor = database.query(DatabaseHelper.TABLE_NAME,
                weatherAllColumns, null, null, null, null, null);
    }

    int getCount() {
        return cursor.getCount();
    }

    CurrentWeather getWeatherByPosition(int position) {
        cursor.moveToPosition(position);
        return cursorToCurrentWeather();
    }

    private CurrentWeather cursorToCurrentWeather() {
        CurrentWeather weather = new CurrentWeather.Builder(cursor.getInt(1), cursor.getString(2))
                .nowTemp(5).windSpeed(6).pressure(7)
                .build();
        return weather;
    }

    @Override
    public void close() {
        cursor.close();
    }


}
