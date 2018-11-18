package ru.virarnd.viraweatherreminder.common;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Stack;

import ru.virarnd.viraweatherreminder.dbase.WeatherHistoryDbHandler;

public class ForecastDataManager {

    private final static String TAG = ForecastDataManager.class.getName();

    private final WeatherDataRequester weatherDataRequester;
    private Stack<String> lastUsedCities;                   // Стэк для хранения последних использованных городов, размер 5
    private WeatherHistoryDbHandler dbSource;

    public ForecastDataManager() {
        weatherDataRequester = new WeatherForecastRequester();
//        weatherDataRequester = new SimpleWeatherDataStorage();
        lastUsedCities = new Stack<>();
        lastUsedCities.setSize(5);
    }

    public Stack<String> getLastUsedCities() {
        return lastUsedCities;
    }

    public void setLastUsedCities(Stack<String> lastUsedCities) {
        this.lastUsedCities = lastUsedCities;
    }

    public City[] getLastUsedCitiesArray() {
        // Для примитивного случая достаточно взять список городов из заполненных данных
        // В более сложном случае надо этим стэком управлять: создавать (или восстанавливать сохраненный), добавлять и извлекать элементы.
        if (weatherDataRequester instanceof SimpleWeatherDataStorage) {
            return ((SimpleWeatherDataStorage) weatherDataRequester).getCitiesArray();
        }
        return lastUsedCities.toArray(new City[lastUsedCities.size()]);
    }

    // Возвращает прогноз на один из пяти дней относительно текущего
    // dayFromCurrent -- сдвиг относительно текущего дня
    public CurrentWeather getForecastByCityIdAndDay(int cityId, int dayFromCurrent) {
        return weatherDataRequester.getFiveDaysForecast(cityId).get(dayFromCurrent);
    }

    public CurrentWeather getCurrentForecastById(int cityId) {
        //TODO На этом уровне сделать обращение к БД (посмотреть старый прогноз) и сразу же направить запрос в сервис на получение новых данных

        // Когда запрос нового прогноза только отправлен "наружу", возвращается null.
        // Тогда надо запросить погоду из ранее сохраненных данных, если они есть для этого дня.
        CurrentWeather weather = weatherDataRequester.askNewCurrentForecast(cityId);
        if (weather == null) {
            WeatherHistoryDbHandler db = historyDbOpened();
            String today = new SimpleDateFormat("yyyyMMdd", Locale.US).format(new Date());
            int checkResult = db.recordExistsByCityIdAndDate(cityId, today);
            if (checkResult > 0) {
                weather = db.getWeatherByCityIdAndDate(cityId, today);
                Log.d(TAG, "Получил прогноз из ранее сохраненного в БД.");
            }
            db.close();
        }
        return weather;
    }



    public void gotNewForecast(CurrentWeather currentWeather) {

    }

    public void addRecordCurrentWeather(CurrentWeather weather, String date) {
        WeatherHistoryDbHandler db = historyDbOpened();
        int checkResult = db.recordExistsByCityIdAndDate(weather.getCity().getId(), date);
        if (checkResult > 0) {
            db.replaceWeatherRecord(checkResult, weather, date);
        } else {
            db.addCurrentWeather(weather, date);
        }
        db.close();
    }


    private WeatherHistoryDbHandler historyDbOpened() {
        dbSource = new WeatherHistoryDbHandler(MyApp.getContext());
        dbSource.openRead();
        return dbSource;
    }
}
