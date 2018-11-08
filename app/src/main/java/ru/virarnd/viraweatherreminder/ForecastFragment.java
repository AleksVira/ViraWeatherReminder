package ru.virarnd.viraweatherreminder;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ru.virarnd.viraweatherreminder.common.DailyForecast;
import ru.virarnd.viraweatherreminder.common.MyApp;
import ru.virarnd.viraweatherreminder.common.Settings;

import static ru.virarnd.viraweatherreminder.FirstActivity.CITY_ID;
import static ru.virarnd.viraweatherreminder.FirstActivity.FORECAST;


public class ForecastFragment extends Fragment {

    public final static String TAG = ForecastFragment.class.getName();

    private DailyForecast dailyForecast;
    private Settings settings;
    private OnForecastFragmentInteractionListener forecastFragmentListener;
    private Button btHistory;
    private TextView tvDeviceTemperature;
    private TextView tvDeviceHumidity;
    private TextView tvTownName;
    private TextView tvTemperature;
    private TextView tvTempLabel;
    private TextView tvWind;
    private TextView tvWindLabel;
    private TextView tvPressure;
    private TextView tvPressureLabel;
    private TextView tvHumidity;
    private TextView tvHumidityLabel;
    private TextView tvDeviceTemperatureLabel;
    private TextView tvDeviceHumidityLabel;
    private boolean temperatureSensorIsPresent;
    private boolean humiditySensorIsPresent;
    private SensorManager sensorManager;
    private Sensor sensorTemperature;
    private Sensor sensorHumidity;
    private int currentCityId;

    public static ForecastFragment newInstance(int cityId, DailyForecast dailyForecast) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(FORECAST, dailyForecast);
        arguments.putInt(CITY_ID, cityId);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(FORECAST)) {
            dailyForecast = getArguments().getParcelable(FORECAST);
            currentCityId = getArguments().getInt(CITY_ID);
        }
        settings = Settings.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        tvTownName = view.findViewById(R.id.tvTownName);
        tvTemperature = view.findViewById(R.id.tvTemperature);
        tvTempLabel = view.findViewById(R.id.tvTemperatureLabel);
        tvWind = view.findViewById(R.id.tvWind);
        tvWindLabel = view.findViewById(R.id.tvWindLabel);
        tvPressure = view.findViewById(R.id.tvPressure);
        tvPressureLabel = view.findViewById(R.id.tvPressureLabel);
        tvHumidity = view.findViewById(R.id.tvHumidity);
        tvHumidityLabel = view.findViewById(R.id.tvHumidityLabel);
        tvDeviceTemperature = view.findViewById(R.id.tv_device_temperature);
        tvDeviceTemperatureLabel = view.findViewById(R.id.label_device_temperature);
        tvDeviceHumidity = view.findViewById(R.id.tv_device_humidity);
        tvDeviceHumidityLabel = view.findViewById(R.id.label_device_humidity);


        // Если прогноз пока не пришел, ставлю прогноз-заглушку
        if (dailyForecast == null) {
            dailyForecast = new DailyForecast.Builder(currentCityId, "обновляется...")
                    .dayTemp(0).nightTemp(0)
                    .windDirection(0).windSpeed(0)
                    .humidity(0)
                    .pressure(0)
                    .weatherConditions(0)
                    .build();
        }

//        currentCityId = dailyForecast.getCity().getId();
        updateForecastData(dailyForecast);

        // Кнопка "История" и слушатель
        btHistory = view.findViewById(R.id.btHistory);
        btHistory.setOnClickListener(new btClickListener());

        // В зависимости от наличия сенсоров, показываю температуру и влажность на приборе
        checkSensors();
        if (temperatureSensorIsPresent) {
        } else {
            tvDeviceTemperature.setVisibility(View.GONE);
            tvDeviceTemperatureLabel.setVisibility(View.GONE);
        }
        if (humiditySensorIsPresent) {
//            sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            sensorManager.registerListener(listenerHumidity, sensorHumidity, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            tvDeviceHumidity.setVisibility(View.GONE);
            tvDeviceHumidityLabel.setVisibility(View.GONE);
        }

        return view;
    }

    private void checkSensors() {
        StringBuilder sensorResult = new StringBuilder("");
        sensorManager = (SensorManager) MyApp.getContext().getSystemService(Context.SENSOR_SERVICE);
        sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        temperatureSensorIsPresent = (sensorTemperature != null);
        sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        humiditySensorIsPresent = (sensorTemperature != null);
        if (!temperatureSensorIsPresent && !humiditySensorIsPresent) {
            sensorResult.append("Сенсоры температуры и влажности не доступны");
        } else {
            sensorResult.append("Доступны сенсоры: ");
            if (temperatureSensorIsPresent && humiditySensorIsPresent) {
                sensorResult.append("температуры и влажности");
            } else if (temperatureSensorIsPresent) {
                sensorResult.append("температуры");
            } else {
                sensorResult.append("влажности");
            }
        }
        Toast.makeText(MyApp.getContext(), sensorResult.toString(), Toast.LENGTH_LONG).show();
    }


    private class btClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btHistory) {
                forecastFragmentListener.onForecastButtonClick(view.getId(), dailyForecast.getCity().getId());
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            forecastFragmentListener = (OnForecastFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnForecastFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        forecastFragmentListener = null;
        sensorManager.unregisterListener(listenerTemperature, sensorTemperature);
        sensorManager.unregisterListener(listenerHumidity, sensorHumidity);
    }

    public interface OnForecastFragmentInteractionListener {
        void onForecastButtonClick(int buttonId, int cityId);
    }

    // Слушатель датчика температуры
    private final SensorEventListener listenerTemperature = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            tvDeviceTemperature.setText(String.valueOf(event.values[0]));
        }
    };

    // Слушатель датчика влажности
    private final SensorEventListener listenerHumidity = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            tvDeviceHumidity.setText(String.valueOf(event.values[0]));
        }
    };

    public void updateForecastData(DailyForecast dailyForecast) {
        if (currentCityId == dailyForecast.getCity().getId()) {
            tvTownName.setText(dailyForecast.getCity().getName());
            tvTempLabel.setText(MyApp.getContext().getString(R.string.t_c));
            String tempLine = String.valueOf(dailyForecast.getDayTemp()) + " " + settings.getTemperatureUnit();
            tvTemperature.setText(tempLine);

            // В зависимости от настроек, показываю или прячу параметры
            if (settings.isWindSpeedVisible()) {
                String windLine = String.valueOf(dailyForecast.getWindSpeed()) + " " + settings.getWindSpeedUnit();
                tvWind.setText(windLine);
            } else {
                tvWindLabel.setVisibility(View.GONE);
                tvWind.setVisibility(View.GONE);
            }
            if (settings.isPressureVisible()) {
                String pressureLine = String.valueOf(dailyForecast.getPressure()) + " " + settings.getPressureUnit();
                tvPressure.setText(pressureLine);
            } else {
                tvPressureLabel.setVisibility(View.GONE);
                tvPressure.setVisibility(View.GONE);
            }
            if (settings.isHumidityVisible()) {
                String humidityLine = String.valueOf(dailyForecast.getHumidity()) + " " + settings.getHumidityUnit();
                tvHumidity.setText(humidityLine);
            } else {
                tvHumidityLabel.setVisibility(View.GONE);
                tvHumidity.setVisibility(View.GONE);
            }
        } else {
            Log.d(TAG, "Получен прогноз для другого города, на экране не обновляю");
        }
    }


}
