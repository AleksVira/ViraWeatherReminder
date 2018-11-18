package ru.virarnd.viraweatherreminder.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class SimpleWeatherDataStorage implements WeatherDataRequester {

    private final ArrayList<ArrayList<CurrentWeather>> forecastData;


    public CurrentWeather askNewCurrentForecast(int searchId) {
        for (int i = 0; i < forecastData.size(); i++) {
            if (forecastData.get(i).get(0).getCity().getId() == searchId) {
                return forecastData.get(i).get(0);
            }
        }
        return null;
    }

    public CurrentWeather getForecastByDate(int Id, Date date) {
        return null;
    }

    public ArrayList<CurrentWeather> getFiveDaysForecast(int searchId) {
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
        CurrentWeather moscow1 = new CurrentWeather.Builder(1, "Moscow")
                .nowTemp(25)
                .windDirection(1).windSpeed(8)
                .humidity(85)
                .pressure(966.06)
                .weatherConditions(0)
                .build();
        CurrentWeather moscow2 = new CurrentWeather.Builder(1, "Moscow")
                .nowTemp(23)
                .windDirection(1).windSpeed(9)
                .humidity(89)
                .pressure(971.15)
                .weatherConditions(0)
                .build();
        CurrentWeather moscow3 = new CurrentWeather.Builder(1, "Moscow")
                .nowTemp(20)
                .windDirection(2).windSpeed(6)
                .humidity(80)
                .pressure(956.06)
                .weatherConditions(1)
                .build();
        CurrentWeather moscow4 = new CurrentWeather.Builder(1, "Moscow")
                .nowTemp(15)
                .windDirection(1).windSpeed(12)
                .humidity(100)
                .pressure(980.06)
                .weatherConditions(2)
                .build();
        CurrentWeather moscow5 = new CurrentWeather.Builder(1, "Moscow")
                .nowTemp(30)
                .windDirection(6).windSpeed(1)
                .humidity(80)
                .pressure(940.06)
                .weatherConditions(0)
                .build();
        ArrayList<CurrentWeather> moscowWeather = new ArrayList<>(Arrays.asList(moscow1, moscow2, moscow3, moscow4, moscow5));
        forecastData.add(moscowWeather);

        CurrentWeather spb1 = new CurrentWeather.Builder(2, "Saint Petersburg")
                .nowTemp(15)
                .windDirection(6).windSpeed(6)
                .humidity(95)
                .pressure(966.06)
                .weatherConditions(3)
                .build();
        CurrentWeather spb2 = new CurrentWeather.Builder(2, "Saint Petersburg")
                .nowTemp(16)
                .windDirection(6).windSpeed(7)
                .humidity(96)
                .pressure(956.06)
                .weatherConditions(4)
                .build();
        CurrentWeather spb3 = new CurrentWeather.Builder(2, "Saint Petersburg")
                .nowTemp(17)
                .windDirection(6).windSpeed(8)
                .humidity(97)
                .pressure(976.06)
                .weatherConditions(3)
                .build();
        CurrentWeather spb4 = new CurrentWeather.Builder(2, "Saint Petersburg")
                .nowTemp(18)
                .windDirection(6).windSpeed(9)
                .humidity(98)
                .pressure(986.06)
                .weatherConditions(4)
                .build();
        CurrentWeather spb5 = new CurrentWeather.Builder(2, "Saint Petersburg")
                .nowTemp(19)
                .windDirection(2).windSpeed(1)
                .humidity(99)
                .pressure(960.06)
                .weatherConditions(1)
                .build();
        ArrayList<CurrentWeather> spbWeather = new ArrayList<>(Arrays.asList(spb1, spb2, spb3, spb4, spb5));
        forecastData.add(spbWeather);

        CurrentWeather rostov1 = new CurrentWeather.Builder(3, "Rostov-na-Donu")
                .nowTemp(30)
                .windDirection(7).windSpeed(3)
                .humidity(90)
                .pressure(960.16)
                .weatherConditions(0)
                .build();
        CurrentWeather rostov2 = new CurrentWeather.Builder(3, "Rostov-na-Donu")
                .nowTemp(32)
                .windDirection(6).windSpeed(4)
                .humidity(80)
                .pressure(961.11)
                .weatherConditions(1)
                .build();
        CurrentWeather rostov3 = new CurrentWeather.Builder(3, "Rostov-na-Donu")
                .nowTemp(32)
                .windDirection(5).windSpeed(5)
                .humidity(78)
                .pressure(962.22)
                .weatherConditions(0)
                .build();
        CurrentWeather rostov4 = new CurrentWeather.Builder(3, "Rostov-na-Donu")
                .nowTemp(33)
                .windDirection(4).windSpeed(0)
                .humidity(83)
                .pressure(963.33)
                .weatherConditions(0)
                .build();
        CurrentWeather rostov5 = new CurrentWeather.Builder(3, "Rostov-na-Donu")
                .nowTemp(34)
                .windDirection(3).windSpeed(1)
                .humidity(85)
                .pressure(964.44)
                .weatherConditions(0)
                .build();
        ArrayList<CurrentWeather> rostovWeather = new ArrayList<>(Arrays.asList(rostov1, rostov2, rostov3, rostov4, rostov5));
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
