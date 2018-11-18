package ru.virarnd.viraweatherreminder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import ru.virarnd.viraweatherreminder.common.MyApp;
import ru.virarnd.viraweatherreminder.common.Settings;

import static ru.virarnd.viraweatherreminder.WeatherPresenter.PRESSURE_MBAR;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.PRESSURE_MM;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.SHOW_HUMIDITY_OFF;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.SHOW_HUMIDITY_ON;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.SHOW_PRESSURE_OFF;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.SHOW_PRESSURE_ON;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.SHOW_WIND_SPEED_OFF;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.SHOW_WIND_SPEED_ON;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.TEMPERATURE_C;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.TEMPERATURE_F;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.WIND_SPEED_MH;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.WIND_SPEED_MS;

public class SettingsActivity extends AppCompatActivity {

    private final static String TAG = SettingsActivity.class.getName();

    private WeatherPresenter weatherPresenter;
    private SettingsActivity settingsActivity;

    private boolean cbWindState;
    private boolean cbPressureState;
    private boolean cbHumidityState;
    private String swTemperatureUnit;
    private String swWindSpeedUnit;
    private String swPressureSUnit;
    private CheckBox cbWindSpeed;
    private CheckBox cbPressure;
    private SwitchCompat swWindSpeed;
    private SwitchCompat swPressure;
    private TextView tvWindSpeedUnitMs;
    private TextView tvWindSpeedUnitMh;
    private TextView tvPressureUnitMm;
    private TextView tvPressureUnitMbar;

    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsActivity = new SettingsActivity();

        weatherPresenter = WeatherPresenter.getInstance();
        weatherPresenter.attachSettingsView(this);

        settings = weatherPresenter.loadSettings();
//        Settings.getInstance().loadSettings();

        // Если первый запуск, то беру Settings из SharedPreferences
//        if (savedInstanceState == null) {
//            settings = weatherPresenter.loadSettings();
//        } else {
//            settings = Settings.getInstance().loadSettings();
//        }


        cbWindState = settings.isWindSpeedVisible();
        cbPressureState = settings.isPressureVisible();
        cbHumidityState = settings.isHumidityVisible();
        swTemperatureUnit = settings.getTemperatureUnit();
        swWindSpeedUnit = settings.getWindSpeedUnit();
        swPressureSUnit = settings.getPressureUnit();

        // Настройки прогноза
        cbWindSpeed = findViewById(R.id.cbWind);
        cbPressure = findViewById(R.id.cbPressure);
        CheckBox cbHumidity = findViewById(R.id.cbHumidity);
        cbWindSpeed.setChecked(cbWindState);
        cbPressure.setChecked(cbPressureState);
        cbHumidity.setChecked(cbHumidityState);

        SwitchCompat swTemp = findViewById(R.id.swTemp);
        swWindSpeed = findViewById(R.id.swWind);
        tvWindSpeedUnitMs = findViewById(R.id.tv_speed_ms);
        tvWindSpeedUnitMh = findViewById(R.id.tv_temp_speed_miles_h);
        swPressure = findViewById(R.id.swPressure);
        tvPressureUnitMm = findViewById(R.id.tv_pressure_mm);
        tvPressureUnitMbar = findViewById(R.id.tv_pressure_mb);
        swTemp.setChecked(swTemperatureUnit.equals(MyApp.getContext().getApplicationContext().getString(R.string.fahrenheit)));
        swWindSpeed.setChecked(swWindSpeedUnit.equals(MyApp.getContext().getApplicationContext().getString(R.string.speed_miles_hour)));
        swPressure.setChecked(swPressureSUnit.equals(MyApp.getContext().getApplicationContext().getString(R.string.pressure_mb)));

        // Каждому элементу настроек -- по слушателю
        CheckBoxAndSwitchListener listener = new CheckBoxAndSwitchListener();
        cbWindSpeed.setOnCheckedChangeListener(listener);
        cbPressure.setOnCheckedChangeListener(listener);
        cbHumidity.setOnCheckedChangeListener(listener);
        swTemp.setOnCheckedChangeListener(listener);
        swWindSpeed.setOnCheckedChangeListener(listener);
        swPressure.setOnCheckedChangeListener(listener);

//        cbWindSpeed.setOnCheckedChangeListener(new CheckBoxAndSwitchListener());
//        cbPressure.setOnCheckedChangeListener(new CheckBoxAndSwitchListener());
//        cbHumidity.setOnCheckedChangeListener(new CheckBoxAndSwitchListener());
//        swTemp.setOnCheckedChangeListener(new CheckBoxAndSwitchListener());
//        swWindSpeed.setOnCheckedChangeListener(new CheckBoxAndSwitchListener());
//        swPressure.setOnCheckedChangeListener(new CheckBoxAndSwitchListener());

        checkWindGroup();
        checkPressureGroup();
    }

    @Override
    protected void onDestroy() {
        weatherPresenter.detachSettingsView();
        super.onDestroy();
    }


    public void onCheckboxChanged(int boxId, boolean isChecked) {
        int condition = 0;
        switch (boxId) {
            case R.id.cbWind:
                condition = isChecked ? SHOW_WIND_SPEED_ON : SHOW_WIND_SPEED_OFF;
                break;
            case R.id.cbPressure:
                condition = isChecked ? SHOW_PRESSURE_ON : SHOW_PRESSURE_OFF;
                break;
            case R.id.cbHumidity:
                condition = isChecked ? SHOW_HUMIDITY_ON : SHOW_HUMIDITY_OFF;
                break;
            case R.id.swTemp:
                condition = isChecked ? TEMPERATURE_F : TEMPERATURE_C;
                break;
            case R.id.swWind:
                condition = isChecked ? WIND_SPEED_MH : WIND_SPEED_MS;
                break;
            case R.id.swPressure:
                condition = isChecked ? PRESSURE_MBAR : PRESSURE_MM;
                break;
            default:
                Log.e(TAG, "Кнопка настроек не определена!");
                break;
        }
        weatherPresenter.sendCheckBoxState(condition);

    }


    private class CheckBoxAndSwitchListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // Если выключаются параметры wind и pressure, гасить единицы измерений рядом.
            // Если включаются -- наоборот, активировать.
            switch (buttonView.getId()) {
                case R.id.cbWind:
                    checkWindGroup();
                    break;
                case R.id.cbPressure:
                    checkPressureGroup();
                    break;
                default:
                    break;
            }
            onCheckboxChanged(buttonView.getId(), isChecked);
        }
    }

    private void checkPressureGroup() {
        if (!cbPressure.isChecked()) {
            swPressure.setVisibility(View.INVISIBLE);
            tvPressureUnitMm.setVisibility(View.INVISIBLE);
            tvPressureUnitMbar.setVisibility(View.INVISIBLE);
        } else {
            swPressure.setVisibility(View.VISIBLE);
            tvPressureUnitMm.setVisibility(View.VISIBLE);
            tvPressureUnitMbar.setVisibility(View.VISIBLE);
        }
    }

    private void checkWindGroup() {
        if (!cbWindSpeed.isChecked()) {
            swWindSpeed.setVisibility(View.INVISIBLE);
            tvWindSpeedUnitMs.setVisibility(View.INVISIBLE);
            tvWindSpeedUnitMh.setVisibility(View.INVISIBLE);
        } else {
            swWindSpeed.setVisibility(View.VISIBLE);
            tvWindSpeedUnitMs.setVisibility(View.VISIBLE);
            tvWindSpeedUnitMh.setVisibility(View.VISIBLE);
        }
    }

/*
    private Settings loadFromSharedPreferences() {
        settings = Settings.getInstance();
        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);

        settings.setWindSpeedVisible(sharedPref.getBoolean("WindSpeedVisible", false));
        settings.setPressureVisible(sharedPref.getBoolean("PressureState", false));
        settings.setHumidityVisible(sharedPref.getBoolean("HumidityState", false));

        settings.setTemperatureUnit(sharedPref.getString("TemperatureUnit", getApplicationContext().getString(R.string.celcius)));
        settings.setWindSpeedUnit(sharedPref.getString("WindSpeedUnit", getApplicationContext().getString(R.string.speed_ms)));
        settings.setPressureUnit(sharedPref.getString("PressureUnit", getApplicationContext().getString(R.string.pressure_mb)));

        return settings;
    }
*/



}
