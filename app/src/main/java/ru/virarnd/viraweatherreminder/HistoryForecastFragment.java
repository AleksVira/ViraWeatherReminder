package ru.virarnd.viraweatherreminder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import ru.virarnd.viraweatherreminder.common.DailyForecast;
import ru.virarnd.viraweatherreminder.common.ForecastHistory;

public class HistoryForecastFragment extends Fragment {

    private static final String HISTORY_CITY_ID = "BTHS8BSI";
    private OnListFragmentInteractionListener historyListener;
    private ForecastHistory forecastHistory;
    private ArrayList<GregorianCalendar> items;
    private int cityId;

    public static HistoryForecastFragment newInstance(int cityId) {
        HistoryForecastFragment myFragment = new HistoryForecastFragment();
        Bundle args = new Bundle();
        args.putInt(HISTORY_CITY_ID, cityId);
        myFragment.setArguments(args);
        return myFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(HISTORY_CITY_ID)) {
            this.cityId = getArguments().getInt(HISTORY_CITY_ID);
        }
        forecastHistory = ((FirstActivity) getActivity()).askHistoryForecast(cityId);
        items = forecastHistory.getForecastDaysList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historyforecast_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyHistoryForecastRecyclerViewAdapter(items, historyListener, forecastHistory));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            historyListener = (OnListFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        historyListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onHistoryListFragmentInteraction(DailyForecast item);
    }
}
