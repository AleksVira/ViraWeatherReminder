package ru.virarnd.viraweatherreminder;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ru.virarnd.viraweatherreminder.common.Forecast;

import static ru.virarnd.viraweatherreminder.FirstActivity.FORECAST;


public class ForecastFragment extends Fragment {

    private Forecast forecast;

    public static ForecastFragment newInstance(Forecast forecast) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(FORECAST, forecast);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(FORECAST)) {
            forecast = getArguments().getParcelable(FORECAST);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        TextView tvTownName = view.findViewById(R.id.tvTownName);
        TextView tvTemperature = view.findViewById(R.id.tvTemperature);
        TextView tvWind = view.findViewById(R.id.tvWind);
        TextView tvPressure = view.findViewById(R.id.tvPressure);
        TextView tvHumidity = view.findViewById(R.id.tvHumidity);

        tvTownName.setText(forecast.getCity().getName());
        tvTemperature.setText(String.valueOf(forecast.getDayTemp()));
        tvWind.setText(String.valueOf(forecast.getWindSpeed()));
        tvPressure.setText(String.valueOf(forecast.getPressure()));
        tvHumidity.setText(String.valueOf(forecast.getHumidity()));

        return view;
    }



}
