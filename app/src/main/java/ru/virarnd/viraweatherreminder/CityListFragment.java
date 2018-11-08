package ru.virarnd.viraweatherreminder;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import ru.virarnd.viraweatherreminder.common.City;
import ru.virarnd.viraweatherreminder.common.MyApp;
import ru.virarnd.viraweatherreminder.common.Settings;

import static ru.virarnd.viraweatherreminder.FirstActivity.PARCEL;

public class CityListFragment extends Fragment {

    private final static String TAG = CityListFragment.class.getName();

    private ArrayList<City> cityList;
    private OnCityListFragmentInteractionListener cityListListener;
//    private OnCityListFragmentInteractionListener notificationsListListener;
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

    private Button btNotificationsList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Добавить чтение в переменные сохраненного состояния Settings
        // Пока считывать то, что есть по дефолту.

        Settings settings = Settings.getInstance();
        cbWindState = settings.isWindSpeedVisible();
        cbPressureState = settings.isPressureVisible();
        cbHumidityState = settings.isHumidityVisible();
        swTemperatureUnit = settings.getTemperatureUnit();
        swWindSpeedUnit = settings.getWindSpeedUnit();
        swPressureSUnit = settings.getPressureUnit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_list, container, false);

        Bundle bundle = getArguments();
        cityList = bundle.getParcelableArrayList(PARCEL);

        // Список городов
        RecyclerView recyclerView = view.findViewById(R.id.notificationsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new CityListRecyclerViewAdapter(cityList, cityListListener));

        // Настройки прогноза
        cbWindSpeed = view.findViewById(R.id.cbWind);
        cbPressure = view.findViewById(R.id.cbPressure);
        CheckBox cbHumidity = view.findViewById(R.id.cbHumidity);
        cbWindSpeed.setChecked(cbWindState);
        cbPressure.setChecked(cbPressureState);
        cbHumidity.setChecked(cbHumidityState);

        SwitchCompat swTemp = view.findViewById(R.id.swTemp);
        swWindSpeed = view.findViewById(R.id.swWind);
        tvWindSpeedUnitMs = view.findViewById(R.id.tv_speed_ms);
        tvWindSpeedUnitMh = view.findViewById(R.id.tv_temp_speed_miles_h);
        swPressure = view.findViewById(R.id.swPressure);
        tvPressureUnitMm = view.findViewById(R.id.tv_pressure_mm);
        tvPressureUnitMbar = view.findViewById(R.id.tv_pressure_mb);
        swTemp.setChecked(swTemperatureUnit.equals(MyApp.getContext().getApplicationContext().getString(R.string.fahrenheit)));
        swWindSpeed.setChecked(swWindSpeedUnit.equals(MyApp.getContext().getApplicationContext().getString(R.string.speed_miles_hour)));
        swPressure.setChecked(swPressureSUnit.equals(MyApp.getContext().getApplicationContext().getString(R.string.pressure_mb)));

        // Каждому элементу настроек -- по слушателю
        cbWindSpeed.setOnCheckedChangeListener(new CheckBoxAndSwitchListener());
        cbPressure.setOnCheckedChangeListener(new CheckBoxAndSwitchListener());
        cbHumidity.setOnCheckedChangeListener(new CheckBoxAndSwitchListener());
        swTemp.setOnCheckedChangeListener(new CheckBoxAndSwitchListener());
        swWindSpeed.setOnCheckedChangeListener(new CheckBoxAndSwitchListener());
        swPressure.setOnCheckedChangeListener(new CheckBoxAndSwitchListener());

        checkWindGroup();
        checkPressureGroup();

        btNotificationsList = view.findViewById(R.id.btNotifications);
        btNotificationsList.setOnClickListener(new ButtonListener());

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            cityListListener = (OnCityListFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnCityListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cityListListener = null;
    }

    public interface OnCityListFragmentInteractionListener {
        void onFragmentInteraction(int cityId);

        void onCheckboxChanged(int buttonId, boolean isChecked);

        void onButtonClick(int buttonId);
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
            cityListListener.onCheckboxChanged(buttonView.getId(), isChecked);
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

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            cityListListener.onButtonClick(view.getId());
        }
    }
}
