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

import ru.virarnd.viraweatherreminder.common.City;

import static ru.virarnd.viraweatherreminder.FirstActivity.PARCEL;

public class CityListFragment extends Fragment {

    private final static String TAG = CityListFragment.class.getName();

    private ArrayList<City> cityList;
    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_list, container, false);

        Bundle bundle = getArguments();
        cityList = bundle.getParcelableArrayList(PARCEL);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new CityListRecyclerViewAdapter(cityList, mListener));
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onCitySelected(int cityId) {
        if (mListener != null) {
            mListener.onFragmentInteraction(cityId);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int cityId);
    }
}
