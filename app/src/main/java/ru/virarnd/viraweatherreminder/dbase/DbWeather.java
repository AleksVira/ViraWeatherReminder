package ru.virarnd.viraweatherreminder.dbase;

/**
 * Класс для хранения старого прогноза в базе данных.
 * Хранит не весь прогноз, пока только один "срез", в который входят:
 * cityId
 * cityName
 * дата (строка в формате yyyymmdd)
 * Последние полученные данные из CurrentWeather для этой даты:
 * температура
 * скорость ветра
 * давление
 * Хранится или запрошенная CurrentWeather, или какой-то кусочек из полученного прогноза (дневной?)
 */

public class DbWeather {

    private int id;
    private int cityId;
    private String cityName;
    private String dbDate;
    private int temperature;
    private int windSpeed;
    private float pressure;


}
