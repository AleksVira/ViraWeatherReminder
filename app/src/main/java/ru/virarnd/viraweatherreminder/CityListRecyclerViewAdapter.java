package ru.virarnd.viraweatherreminder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.virarnd.viraweatherreminder.common.City;

import java.util.List;

public class CityListRecyclerViewAdapter extends RecyclerView.Adapter<CityListRecyclerViewAdapter.ViewHolder> {

    private final static String TAG = CityListRecyclerViewAdapter.class.getName();

    private List<City> cities;
    private CityListFragment.OnFragmentInteractionListener mListener;
    private int row_index = -1;

    public CityListRecyclerViewAdapter(List<City> cities, CityListFragment.OnFragmentInteractionListener listener) {
        this.mListener = listener;
        this.cities = cities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_city_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = cities.get(position);
//        holder.mIdView.setText(cities.get(position).id);
        holder.mContentView.setText(cities.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Выбран город " + holder.mItem.getName());
                Log.d(TAG, "ID = " + holder.mItem.getId());
                mListener.onFragmentInteraction(holder.mItem.getId());
//                mPresenter.setCityAndShowDetail(holder.mItem);
                row_index = holder.getAdapterPosition();
                notifyDataSetChanged();

            }
        });

        if (row_index == position) {
            holder.mView.setBackgroundColor(holder.mView.getContext().getResources().getColor(R.color.colorPrimary));
        } else {
            holder.mView.setBackgroundColor(holder.mView.getContext().getResources().getColor(R.color.primaryLight));
        }

    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public City mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.cityName);
        }

    }
}
