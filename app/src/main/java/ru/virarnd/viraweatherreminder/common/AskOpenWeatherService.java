package ru.virarnd.viraweatherreminder.common;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import static ru.virarnd.viraweatherreminder.common.OpenWeatherForecastRequester.*;

public class AskOpenWeatherService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private final static String TAG = AskOpenWeatherService.class.getName();

    public static final String INTENT_RESULT = "5NPQ5H.RESULT";
    public static final String DAILY_FORECAST = "YEQT4.DAILY";

    public static final String ACTION_ASK_DAILY_FORECAST = "ask dayli forecast";
    public static final String ACTION_BAZ = "ru.virarnd.viraweatherreminder.common.action.BAZ";

    private LocalBroadcastManager localBroadcastManager;

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "ru.virarnd.viraweatherreminder.common.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "ru.virarnd.viraweatherreminder.common.extra.PARAM2";

    public AskOpenWeatherService() {
        super("AskOpenWeatherService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null && intent.getExtras().containsKey(ACTION_LABEL)) {
            final String actionLabel = intent.getExtras().getString(ACTION_LABEL);
            if (ACTION_ASK_DAILY_FORECAST.equals(actionLabel)) {
                final int cityId = intent.getIntExtra(CITY_ID, 0);
                Log.d(TAG, "Тут запрашивается погода и это может занять какое-то время");
                DailyForecast newDailyForecast = requestOpenWeatherServer(cityId);
//                double hardTaskResult = hardTask();
                Log.d(TAG, "Результат получен! " + newDailyForecast.getCity().getName());
                intent = new Intent(INTENT_RESULT);
                intent.putExtra(DAILY_FORECAST, newDailyForecast);
                localBroadcastManager.sendBroadcast(intent);
            } else if (ACTION_BAZ.equals(actionLabel)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        return super.onStartCommand(intent, flags, startId);
    }

    private DailyForecast requestOpenWeatherServer(int cityId) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DailyForecast spb1 = new DailyForecast.Builder(2, "Saint PetersburGGGG")
                .dayTemp(15).nightTemp(10)
                .windDirection(6).windSpeed(6)
                .humidity(95)
                .pressure(966.06)
                .weatherConditions(3)
                .build();
        return spb1;
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }



}
