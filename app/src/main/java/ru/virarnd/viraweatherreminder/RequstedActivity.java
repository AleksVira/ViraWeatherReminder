package ru.virarnd.viraweatherreminder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.virarnd.viraweatherreminder.common.Forecast;

import static ru.virarnd.viraweatherreminder.TwoActivityPresenter.HUMIDITY_LBL;
import static ru.virarnd.viraweatherreminder.TwoActivityPresenter.MESSAGE_LBL;
import static ru.virarnd.viraweatherreminder.TwoActivityPresenter.PRESSURE_LBL;
import static ru.virarnd.viraweatherreminder.TwoActivityPresenter.TEXT;
import static ru.virarnd.viraweatherreminder.TwoActivityPresenter.WIND_LBL;
import static ru.virarnd.viraweatherreminder.TwoActivityPresenter.getInstance;

public class RequstedActivity extends AppCompatActivity {
    private static final String TAG = RequstedActivity.class.getSimpleName();

    private String townName;
    private Forecast forecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requsted);

        Model model = new Model();

        TextView tvTownName = findViewById(R.id.tvTownName);
        TextView tvTemperature = findViewById(R.id.tvTemperature);
        TextView tvWind = findViewById(R.id.tvWind);
        TextView tvPressure = findViewById(R.id.tvPressure);
        TextView tvHumidity = findViewById(R.id.tvHumidity);
        TextView tvWindLabel = findViewById(R.id.tvWindLabel);
        TextView tvPressureLabel = findViewById(R.id.tvPressureLabel);
        TextView tvHumidityLabel = findViewById(R.id.tvHumidityLabel);
        Button btSend = findViewById(R.id.btSend);

        // Исходя из переданных через Intent параметров, показываю или прячу поля с данными.
        if (getInstance() != null) {
            townName = getIntent().getExtras().getString(TEXT);
            boolean showWind = getIntent().getExtras().getBoolean(WIND_LBL);
            boolean showPressure = getIntent().getExtras().getBoolean(PRESSURE_LBL);
            boolean showHumidity = getIntent().getExtras().getBoolean(HUMIDITY_LBL);
            boolean showMessageButton = getIntent().getExtras().getBoolean(MESSAGE_LBL);

            forecast = model.getForecastByTownName(townName);

            tvTownName.setText(townName);
            tvTemperature.setText(String.valueOf(forecast.getDayTemp()));
            if (!showWind) {
                tvWind.setVisibility(View.GONE);
                tvWindLabel.setVisibility(View.GONE);
            } else {
                tvWind.setText(String.valueOf(forecast.getWindSpeed()));
            }

            if (!showPressure) {
                tvPressure.setVisibility(View.GONE);
                tvPressureLabel.setVisibility(View.GONE);
            } else {
                tvPressure.setText(String.valueOf(forecast.getPressure()));
            }

            if (!showHumidity) {
                tvHumidity.setVisibility(View.GONE);
                tvHumidityLabel.setVisibility(View.GONE);
            } else {
                tvHumidity.setText(String.valueOf(forecast.getHumidity()));
            }

            if (!showMessageButton) {
                btSend.setVisibility(View.GONE);
            }
        } else {
            Log.e(TAG, "Пустой интент!");
        }

    }

    public void onClickSend(View view) {
        String number = "+71234567890";
        String message = townName + ", t = " + forecast.getDayTemp() + "°С";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null));
        intent.putExtra("sms_body", message);
        startActivity(intent);
    }
}
