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
    private Button btNotificationsList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_list, container, false);

        Bundle bundle = getArguments();
        cityList = bundle.getParcelableArrayList(PARCEL);

        // Список городов
        RecyclerView recyclerView = view.findViewById(R.id.notificationsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new CityListRecyclerViewAdapter(cityList, cityListListener));

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

        void onButtonClick(int buttonId);
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            cityListListener.onButtonClick(view.getId());
        }
    }
}
