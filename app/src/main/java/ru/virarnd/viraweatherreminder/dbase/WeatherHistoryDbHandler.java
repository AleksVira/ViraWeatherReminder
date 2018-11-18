package ru.virarnd.viraweatherreminder.dbase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.Closeable;

import ru.virarnd.viraweatherreminder.common.CurrentWeather;

public class WeatherHistoryDbHandler implements Closeable {

    private final static String TAG = WeatherHistoryDbHandler.class.getName();

    private final DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
//    private DbWeatherReader dbWeatherReader;

    public WeatherHistoryDbHandler(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void openRead() {
        database = databaseHelper.getWritableDatabase();
//        dbWeatherReader = new DbWeatherReader(database);
//        dbWeatherReader.open();
    }

    @Override
    public void close(){
//        dbWeatherReader.close();
        databaseHelper.close();
    }

    public void addCurrentWeather(CurrentWeather weather, String stringDate) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CITY_ID, weather.getCity().getId());
        values.put(DatabaseHelper.COLUMN_CITY_NAME, weather.getCity().getName());
        values.put(DatabaseHelper.COLUMN_DATE, stringDate);
        values.put(DatabaseHelper.COLUMN_TEMPERATURE, weather.getNowTemp());
        values.put(DatabaseHelper.COLUMN_WIND_SPEED, weather.getWindSpeed());
        values.put(DatabaseHelper.COLUMN_PRESSURE, weather.getPressure());
        long insertId = database.insert(DatabaseHelper.TABLE_NAME, null, values);
        if (insertId >= 0) {
            Log.d(TAG, "В таблицу добавлена запись с _ID = " + insertId);
        } else {
            Log.e(TAG, "Ошибка при добавлении новой записи!");
        }
    }

    public CurrentWeather getWeatherByCityIdAndDate(int cityId, String dateString) {
        return databaseHelper.getWeatherByCityIdAndDate(database, cityId, dateString);
    }

    public void updateWeatherByCityIdAndDate(int cityId, String dateString) {

    }

    public void deleteWeatherByCityIdAndDate(int cityId, String dateString) {

    }



    public int recordExistsByCityIdAndDate(int cityId, String date) {
        return databaseHelper.getIdByCityAndDate(database, cityId, date);
    }

    public void replaceWeatherRecord(int id, CurrentWeather weather, String stringDate) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CITY_ID, weather.getCity().getId());
        values.put(DatabaseHelper.COLUMN_CITY_NAME, weather.getCity().getName());
        values.put(DatabaseHelper.COLUMN_DATE, stringDate);
        values.put(DatabaseHelper.COLUMN_TEMPERATURE, weather.getNowTemp());
        values.put(DatabaseHelper.COLUMN_WIND_SPEED, weather.getWindSpeed());
        values.put(DatabaseHelper.COLUMN_PRESSURE, weather.getPressure());
        String selection = DatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        long updateId = database.update(DatabaseHelper.TABLE_NAME, values, selection, selectionArgs);
        if (updateId >= 0) {
            Log.d(TAG, "В таблице обновлена запись с _ID = " + id);
        } else {
            Log.e(TAG, "Ошибка при обновлении записи!");
        }
    }
}
