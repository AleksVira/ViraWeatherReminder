package ru.virarnd.viraweatherreminder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.virarnd.viraweatherreminder.common.City;
import ru.virarnd.viraweatherreminder.common.Forecast;

import static ru.virarnd.viraweatherreminder.TwoActivityPresenter.TEXT;
import static ru.virarnd.viraweatherreminder.TwoActivityPresenter.WIND_LBL;
import static ru.virarnd.viraweatherreminder.TwoActivityPresenter.PRESSURE_LBL;
import static ru.virarnd.viraweatherreminder.TwoActivityPresenter.HUMIDITY_LBL;
import static ru.virarnd.viraweatherreminder.TwoActivityPresenter.MESSAGE_LBL;

public class RequstedActivity extends AppCompatActivity {
    private TextView tvTownName, tvTemperature, tvWind, tvPressure, tvHumidity;
    private Button btSend;

    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requsted);

        model = new Model();

        tvTownName = findViewById(R.id.tvTownName);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvWind = findViewById(R.id.tvWind);
        tvPressure = findViewById(R.id.tvPressure);
        tvHumidity = findViewById(R.id.tvHumidity);
        btSend = findViewById(R.id.btSend);

        // Исходя из переданных через Intent параметров, показываю или прячу поля с данными.
        String townName = getIntent().getExtras().getString(TEXT);
        boolean showWind = getIntent().getExtras().getBoolean(WIND_LBL);
        boolean showPressure = getIntent().getExtras().getBoolean(PRESSURE_LBL);
        boolean showHumidity = getIntent().getExtras().getBoolean(HUMIDITY_LBL);
        boolean showMessageButton = getIntent().getExtras().getBoolean(MESSAGE_LBL);

        Forecast forecast = model.getForecastByTownName(townName);

        tvTownName.setText(townName);
        tvTemperature.setText(String.valueOf(forecast.getDayTemp()));
        if (!showWind) {
            tvWind.setVisibility(View.GONE);
        } else {
            tvWind.setText(String.valueOf(forecast.getWindSpeed()));
        }

        if (!showPressure) {
            tvPressure.setVisibility(View.GONE);
        } else {
            tvPressure.setText(String.valueOf(forecast.getPressure()));
        }

        if (!showHumidity) {
            tvHumidity.setVisibility(View.GONE);
        } else {
            tvHumidity.setText(String.valueOf(forecast.getHumidity()));
        }

        if (!showMessageButton) {
            btSend.setVisibility(View.GONE);
        }

    }

    public void onClickSend(View view) {

    }
}
