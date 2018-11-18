package ru.virarnd.viraweatherreminder.openWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {

    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("q") String cityCountry, @Query("appid") String keyApi);

    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeatherByCityId(@Query("id") String cityId, @Query("appid") String keyApi);


}
