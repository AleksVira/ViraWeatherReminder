package ru.virarnd.viraweatherreminder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.virarnd.viraweatherreminder.HistoryForecastFragment.OnListFragmentInteractionListener;
import ru.virarnd.viraweatherreminder.common.DailyForecast;
import ru.virarnd.viraweatherreminder.common.ForecastHistory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class MyHistoryForecastRecyclerViewAdapter extends RecyclerView.Adapter<MyHistoryForecastRecyclerViewAdapter.ViewHolder> {

    // TODO Адаптер слишком много знает про ForecastHistory, надо вынести в Presenter подготовку данных для адаптера

    private final List<DailyForecast> mValues;
    private final List<GregorianCalendar> mDates;
    private final OnListFragmentInteractionListener mListener;

    MyHistoryForecastRecyclerViewAdapter(List<GregorianCalendar> calendarList, OnListFragmentInteractionListener listener, ForecastHistory history) {
        mDates = calendarList;
        mValues = new ArrayList<>(history.getForecastMap().values());
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_historyforecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DailyForecast mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getCity().getName());
        holder.mContentView.setText(formattedDate(mDates.get(position)));
        holder.tvDayTemp.setText(String.valueOf(mItem.getDayTemp()));
        holder.tvNightTemp.setText(String.valueOf(mItem.getNightTemp()));
        holder.tvWindSpeed.setText(String.valueOf(mItem.getWindSpeed()));
        holder.tvPressure.setText(String.valueOf(mItem.getPressure()));
        holder.tvHumidity.setText(String.valueOf(mItem.getHumidity()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onHistoryListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    private String formattedDate(GregorianCalendar calendar) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yy");
        fmt.setCalendar(calendar);
        return fmt.format(calendar.getTime());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        DailyForecast mItem;
        final View mView;
        final TextView mIdView;
        final TextView mContentView;
        final TextView tvDayTemp;
        final TextView tvNightTemp;
        final TextView tvWindSpeed;
        final TextView tvPressure;
        final TextView tvHumidity;


        ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.city_name);
            mContentView = view.findViewById(R.id.date);
            tvDayTemp = view.findViewById(R.id.tvDayTemp);
            tvNightTemp = view.findViewById(R.id.tvNightTemp);
            tvWindSpeed = view.findViewById(R.id.tvWindSpeed);
            tvPressure = view.findViewById(R.id.tvPressure);
            tvHumidity = view.findViewById(R.id.tvHumidity);
        }
    }
}
