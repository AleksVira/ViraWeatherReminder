package ru.virarnd.viraweatherreminder;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ru.virarnd.viraweatherreminder.common.DailyForecast;
import ru.virarnd.viraweatherreminder.common.MyApp;
import ru.virarnd.viraweatherreminder.common.Settings;

import static ru.virarnd.viraweatherreminder.FirstActivity.FORECAST;


public class ForecastFragment extends Fragment {

    private DailyForecast dailyForecast;
    private Settings settings;
    private OnForecastFragmentInteractionListener forecastFragmentListener;
    private Button btHistory;

    public static ForecastFragment newInstance(DailyForecast dailyForecast) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(FORECAST, dailyForecast);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(FORECAST)) {
            dailyForecast = getArguments().getParcelable(FORECAST);
        }
        settings = Settings.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        TextView tvTownName = view.findViewById(R.id.tvTownName);
        TextView tvTemperature = view.findViewById(R.id.tvTemperature);
        TextView tvTempLabel = view.findViewById(R.id.tvTemperatureLabel);
        TextView tvWind = view.findViewById(R.id.tvWind);
        TextView tvWindLabel = view.findViewById(R.id.tvWindLabel);
        TextView tvPressure = view.findViewById(R.id.tvPressure);
        TextView tvPressureLabel = view.findViewById(R.id.tvPressureLabel);
        TextView tvHumidity = view.findViewById(R.id.tvHumidity);
        TextView tvHumidityLabel = view.findViewById(R.id.tvHumidityLabel);

        tvTownName.setText(dailyForecast.getCity().getName());
        tvTempLabel.setText(MyApp.getAppContext().getString(R.string.t_c));
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

        // Кнопка "История" и слушатель
        btHistory = view.findViewById(R.id.btHistory);
        btHistory.setOnClickListener(new btClickListener());


        return view;
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
    }

    public interface OnForecastFragmentInteractionListener {
        void onForecastButtonClick(int buttonId, int cityId);
    }

}
