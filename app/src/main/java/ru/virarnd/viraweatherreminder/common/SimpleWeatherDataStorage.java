package ru.virarnd.viraweatherreminder.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class SimpleWeatherDataStorage implements WeatherDataRequester {

    private final ArrayList<ArrayList<DailyForecast>> forecastData;


    public DailyForecast getCurrentForecast(int searchId) {
        for (int i = 0; i < forecastData.size(); i++) {
            if (forecastData.get(i).get(0).getCity().getId() == searchId) {
                return forecastData.get(i).get(0);
            }
        }
        return null;
    }

    public DailyForecast getForecastByDate(int Id, Date date) {
        return null;
    }

    public ArrayList<DailyForecast> getFiveDaysForecast(int searchId) {
        for (int i = 0; i < forecastData.size(); i++) {
            if (forecastData.get(i).get(0).getCity().getId() == searchId) {
                return forecastData.get(i);
            }
        }
        return null;
    }

    // Класс содержит данные о прогнозе погоды для нескольких фиксированных городов (для начала три города), умеет отдавать прогноз погоды на условный день по этим трём городам.
    SimpleWeatherDataStorage() {
        forecastData = new ArrayList<>();
        DailyForecast moscow1 = new DailyForecast.Builder(1, "Moscow")
                .dayTemp(25).nightTemp(12)
                .windDirection(1).windSpeed(8)
                .humidity(85)
                .pressure(966.06)
                .weatherConditions(0)
                .build();
        DailyForecast moscow2 = new DailyForecast.Builder(1, "Moscow")
                .dayTemp(23).nightTemp(10)
                .windDirection(1).windSpeed(9)
                .humidity(89)
                .pressure(971.15)
                .weatherConditions(0)
                .build();
        DailyForecast moscow3 = new DailyForecast.Builder(1, "Moscow")
                .dayTemp(20).nightTemp(9)
                .windDirection(2).windSpeed(6)
                .humidity(80)
                .pressure(956.06)
                .weatherConditions(1)
                .build();
        DailyForecast moscow4 = new DailyForecast.Builder(1, "Moscow")
                .dayTemp(15).nightTemp(9)
                .windDirection(1).windSpeed(12)
                .humidity(100)
                .pressure(980.06)
                .weatherConditions(2)
                .build();
        DailyForecast moscow5 = new DailyForecast.Builder(1, "Moscow")
                .dayTemp(30).nightTemp(25)
                .windDirection(6).windSpeed(1)
                .humidity(80)
                .pressure(940.06)
                .weatherConditions(0)
                .build();
        ArrayList<DailyForecast> moscowWeather = new ArrayList<>(Arrays.asList(moscow1, moscow2, moscow3, moscow4, moscow5));
        forecastData.add(moscowWeather);

        DailyForecast spb1 = new DailyForecast.Builder(2, "Saint Petersburg")
                .dayTemp(15).nightTemp(10)
                .windDirection(6).windSpeed(6)
                .humidity(95)
                .pressure(966.06)
                .weatherConditions(3)
                .build();
        DailyForecast spb2 = new DailyForecast.Builder(2, "Saint Petersburg")
                .dayTemp(16).nightTemp(13)
                .windDirection(6).windSpeed(7)
                .humidity(96)
                .pressure(956.06)
                .weatherConditions(4)
                .build();
        DailyForecast spb3 = new DailyForecast.Builder(2, "Saint Petersburg")
                .dayTemp(17).nightTemp(14)
                .windDirection(6).windSpeed(8)
                .humidity(97)
                .pressure(976.06)
                .weatherConditions(3)
                .build();
        DailyForecast spb4 = new DailyForecast.Builder(2, "Saint Petersburg")
                .dayTemp(18).nightTemp(15)
                .windDirection(6).windSpeed(9)
                .humidity(98)
                .pressure(986.06)
                .weatherConditions(4)
                .build();
        DailyForecast spb5 = new DailyForecast.Builder(2, "Saint Petersburg")
                .dayTemp(19).nightTemp(16)
                .windDirection(2).windSpeed(1)
                .humidity(99)
                .pressure(960.06)
                .weatherConditions(1)
                .build();
        ArrayList<DailyForecast> spbWeather = new ArrayList<>(Arrays.asList(spb1, spb2, spb3, spb4, spb5));
        forecastData.add(spbWeather);

        DailyForecast rostov1 = new DailyForecast.Builder(3, "Rostov-na-Donu")
                .dayTemp(30).nightTemp(28)
                .windDirection(7).windSpeed(3)
                .humidity(90)
                .pressure(960.16)
                .weatherConditions(0)
                .build();
        DailyForecast rostov2 = new DailyForecast.Builder(3, "Rostov-na-Donu")
                .dayTemp(32).nightTemp(28)
                .windDirection(6).windSpeed(4)
                .humidity(80)
                .pressure(961.11)
                .weatherConditions(1)
                .build();
        DailyForecast rostov3 = new DailyForecast.Builder(3, "Rostov-na-Donu")
                .dayTemp(32).nightTemp(28)
                .windDirection(5).windSpeed(5)
                .humidity(78)
                .pressure(962.22)
                .weatherConditions(0)
                .build();
        DailyForecast rostov4 = new DailyForecast.Builder(3, "Rostov-na-Donu")
                .dayTemp(33).nightTemp(28)
                .windDirection(4).windSpeed(0)
                .humidity(83)
                .pressure(963.33)
                .weatherConditions(0)
                .build();
        DailyForecast rostov5 = new DailyForecast.Builder(3, "Rostov-na-Donu")
                .dayTemp(34).nightTemp(28)
                .windDirection(3).windSpeed(1)
                .humidity(85)
                .pressure(964.44)
                .weatherConditions(0)
                .build();
        ArrayList<DailyForecast> rostovWeather = new ArrayList<>(Arrays.asList(rostov1, rostov2, rostov3, rostov4, rostov5));
        forecastData.add(rostovWeather);
    }

    public City[] getCitiesArray() {
        City[] cities = new City[forecastData.size()];
        for (int i = 0; i < forecastData.size(); i++) {
            cities[i] = forecastData.get(i).get(0).getCity();
        }
        return cities;
    }


}
