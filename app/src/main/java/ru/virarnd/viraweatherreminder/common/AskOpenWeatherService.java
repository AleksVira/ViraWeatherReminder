package ru.virarnd.viraweatherreminder.common;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.virarnd.viraweatherreminder.BuildConfig;
import ru.virarnd.viraweatherreminder.openWeather.Main;
import ru.virarnd.viraweatherreminder.openWeather.OpenWeatherApi;
import ru.virarnd.viraweatherreminder.openWeather.WeatherRequest;

import static ru.virarnd.viraweatherreminder.common.WeatherForecastRequester.*;

public class AskOpenWeatherService extends IntentService {

    private final static String TAG = AskOpenWeatherService.class.getName();

    public static final String INTENT_RESULT = "5NPQ5H.RESULT";
    public static final String CURRENT_FORECAST = "YEQT4.DAILY";

    public static final String ACTION_ASK_CURRENT_WEATHER = "ask current weather";
//    public static final String ACTION_BAZ = "ru.virarnd.viraweatherreminder.common.action.BAZ";

    private LocalBroadcastManager localBroadcastManager;

    private OpenWeatherApi openWeatherApi;
    private final static String API_KEY = "569c8996e7ee1eb8f2ba9dcf15f54125";
//    private final static String API_KEY = "29fb8d6056fe0f462c0731feda71d342";

//    private OpenWeatherQuery openWeatherQuery;

    public AskOpenWeatherService() {
        super("AskOpenWeatherService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null && intent.getExtras().containsKey(ACTION_LABEL)) {
            final String actionLabel = intent.getExtras().getString(ACTION_LABEL);
            if (ACTION_ASK_CURRENT_WEATHER.equals(actionLabel)) {
                final int cityId = intent.getIntExtra(CITY_ID, 0);
                Log.d(TAG, "Запрашивается текущая погода и это может занять какое-то время");
                requestOpenWeatherServer(cityId);


//            } else if (ACTION_BAZ.equals(actionLabel)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionBaz(param1, param2);
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        return super.onStartCommand(intent, flags, startId);
    }

    private void requestOpenWeatherServer(int cityId) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel((BuildConfig.DEBUG) ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                .build();


        String requestId = String.valueOf(cityId);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        openWeatherApi = retrofit.create(OpenWeatherApi.class);
        openWeatherApi.loadWeatherByCityId(requestId, API_KEY)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequest> call, @NonNull Response<WeatherRequest> response) {
                        try {
                            String cityName = response.body().getName();
                            Log.d(TAG, "Пришел ответ от сервера " + cityName);
                            Main main = response.body().getMain();
                            CurrentWeather nowWeather = new CurrentWeather.Builder((int) response.body().getId(), cityName)
                                    .nowTemp(convertKelvinToCelsius(main.getTemp_max()))
                                    .windDirection(windDirectionConverter(response.body().getWind().getDeg())).windSpeed((int) response.body().getWind().getSpeed())
                                    .humidity(main.getHumidity())
                                    .pressure(main.getPressure())
                                    .weatherConditions(response.body().getWeather()[0].getId())
                                    .icon(response.body().getWeather()[0].getIcon())
                                    .build();
                            Log.d(TAG, "Результат получен! Город: " + nowWeather.getCity().getName() + ", температура " + nowWeather.getNowTemp());
                            Intent intent = new Intent(INTENT_RESULT);
                            intent.putExtra(CURRENT_FORECAST, nowWeather);
                            localBroadcastManager.sendBroadcast(intent);
                        } catch (final Throwable throwable) {
                            Log.e(TAG, throwable.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<WeatherRequest> call, @NonNull Throwable throwable) {
                        Log.e(TAG, "Ошибка в ответе от сервера!");

                    }
                });

    }

    private int convertKelvinToCelsius(float tempK) {
        return (int) Math.round(tempK - 273.15);
    }

    private int windDirectionConverter(float deg) {
        if (deg > 0 && deg <= 22.5) {
            return 0;
        } else if (deg > 22.5 && deg <= 67.5) {
            return 1;
        } else if (deg > 67.5 && deg <= 112.5) {
            return 2;
        } else if (deg > 112.5 && deg <= 157.5) {
            return 3;
        } else if (deg > 157.5 && deg <= 202.5) {
            return 4;
        } else if (deg > 202.5 && deg <= 247.5) {
            return 5;
        } else if (deg > 247.5 && deg <= 292.5) {
            return 6;
        } else if (deg > 292.5 && deg <= 337.5) {
            return 7;
        } else {
            return 8;
        }
    }


//    public interface OpenWeatherQuery {
//        void currentWeather(CurrentWeather currentWeather);
//    }

}
