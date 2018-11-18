package ru.virarnd.viraweatherreminder.dbase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import ru.virarnd.viraweatherreminder.common.CurrentWeather;

// Класс установки базы данных, создать базу даных, если ее нет, проапгрейдить ее
public class DatabaseHelper extends SQLiteOpenHelper {

    private final static String TAG = DatabaseHelper.class.getName();

    private static final String DATABASE_NAME = "weatherhistory.db";
    private static final int DATABASE_VERSION = 1;

    // название таблицы
    public static final String TABLE_NAME = "weatherhistory";
    // столбцы
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_CITY_ID = "cityId";
    public static final String COLUMN_CITY_NAME = "cityName";
    public static final String COLUMN_CITY_NAME_RUS = "cityNameRus";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TEMPERATURE = "temperature";
    public static final String COLUMN_WIND_SPEED = "windSpeed";
    public static final String COLUMN_PRESSURE = "pressure";
//    public static final String COLUMN_HUMIDITY = "humidity";          // Отключенная влажность пока -- как индикатор того что загрузка из БД была сделана


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // вызывается при попытке доступа к базе данных, но когда еще эта база данных не создана
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CITY_ID + " TEXT," + COLUMN_CITY_NAME + " TEXT," + COLUMN_DATE + " TEXT,"
                + COLUMN_TEMPERATURE + " INTEGER," + COLUMN_WIND_SPEED + " INTEGER,"
                + COLUMN_PRESSURE + " REAL);");
    }

    // вызывается, когда необходимо обновление базы данных
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        if ((oldVersion == 1) && (newVersion == 2)) {
            String upgradeQuery = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " +
                    COLUMN_CITY_NAME_RUS + " TEXT";
            db.execSQL(upgradeQuery);
        }
    }


    public int getIdByCityAndDate(SQLiteDatabase db, int cityId, String dateString) {
        showDbState(db);
        int result = -1;
        String[] columns = {COLUMN_ID, COLUMN_CITY_ID, COLUMN_DATE};
        String selection = COLUMN_CITY_ID + " = ? AND " + COLUMN_DATE + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(cityId), dateString};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            result = cursor.getInt(idIndex);
            Log.d(TAG, "ID = " + result);
            cursor.close();
            return result;
        } else {
            Log.d(TAG, "Ни одной строки в таблице не найдено!");
        }
        cursor.close();
        return result;
    }

    private void showDbState(SQLiteDatabase db) {
        // Метод для контроля за состоянием базы
        // TODO Удалить метод
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID}, null, null, null, null, null);
        Log.d(TAG, "Сейчас в таблице " + cursor.getCount() + " строк");
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            do {
                Log.d(TAG, "Found ID = " + cursor.getInt(idIndex));
            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "Ни одной строки в таблице не найдено!");
        }
        cursor.close();
    }

    public CurrentWeather getWeatherByCityIdAndDate(SQLiteDatabase db, int cityId, String dateString) {
        String[] columns = {COLUMN_CITY_ID, COLUMN_CITY_NAME, COLUMN_TEMPERATURE, COLUMN_WIND_SPEED, COLUMN_PRESSURE, COLUMN_DATE};
        String selection = COLUMN_CITY_ID + " = ? AND " + COLUMN_DATE + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(cityId), dateString};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(COLUMN_CITY_NAME);
            int tempIndex = cursor.getColumnIndex(COLUMN_TEMPERATURE);
            int windIndex = cursor.getColumnIndex(COLUMN_WIND_SPEED);
            int pressureIndex = cursor.getColumnIndex(COLUMN_PRESSURE);
            CurrentWeather weather = new CurrentWeather.Builder(cityId, cursor.getString(nameIndex))
                    .nowTemp(cursor.getInt(tempIndex))
                    .windSpeed(cursor.getInt(windIndex))
                    .pressure(cursor.getDouble(pressureIndex))
                    .build();
            Log.d(TAG, "Город " + cursor.getString(nameIndex) + ", температура = " + cursor.getInt(tempIndex) + ", ветер = " + cursor.getInt(windIndex) + ", давление = "+ cursor.getDouble(pressureIndex));
            cursor.close();
            return weather;
        } else {
            Log.d(TAG, "Ни одной строки в таблице не найдено!");
        }
        cursor.close();
        return null;
    }


}
