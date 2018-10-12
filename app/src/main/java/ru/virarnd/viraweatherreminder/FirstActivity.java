package ru.virarnd.viraweatherreminder;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import java.util.ArrayList;

import ru.virarnd.viraweatherreminder.common.City;
import ru.virarnd.viraweatherreminder.common.Forecast;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class FirstActivity extends AppCompatActivity implements CityListFragment.OnFragmentInteractionListener {

    private final static String TAG = FirstActivity.class.getName();
    public final static String PARCEL = "RM46";
    public final static String FORECAST = "XN7A";

    private CityListFragment cityListFragment;
    private ForecastFragment forecastFragment;
    private WeatherPresenter weatherPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        weatherPresenter = WeatherPresenter.getInstance();
        weatherPresenter.attachView(this);

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            ArrayList<City> cityList = weatherPresenter.getLastUsedCityList();
            bundle.putParcelableArrayList(PARCEL, cityList);

            cityListFragment = new CityListFragment();
            cityListFragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.mainFrame, cityListFragment);
            transaction.commit();
        }


    }

    @Override
    protected void onDestroy() {
        weatherPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onFragmentInteraction(int cityId) {
        Log.d(TAG, "Получен ID = " + cityId);
        weatherPresenter.setCityAndShowDetail(cityId);
    }

    public void showForecast(Forecast cityForecast) {
        forecastFragment = new ForecastFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(FORECAST, cityForecast);
        forecastFragment.setArguments(bundle);

        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager().beginTransaction().replace(R.id.childFrame, forecastFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, forecastFragment).addToBackStack("E7LY3SBO").commit();
        }
    }
}
