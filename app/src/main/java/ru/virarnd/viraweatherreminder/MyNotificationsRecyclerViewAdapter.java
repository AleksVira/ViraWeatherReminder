package ru.virarnd.viraweatherreminder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.virarnd.viraweatherreminder.NotificationsFragment.OnListFragmentInteractionListener;
import ru.virarnd.viraweatherreminder.common.Notification;

import java.text.SimpleDateFormat;
import java.util.List;

public class MyNotificationsRecyclerViewAdapter extends RecyclerView.Adapter<MyNotificationsRecyclerViewAdapter.ViewHolder> {

    private final static String TAG = MyNotificationsRecyclerViewAdapter.class.getName();

    private final List<Notification> notificationList;
    private final OnListFragmentInteractionListener mListener;

    //TODO Привязать к фрагменту, реализовать слушателя
    MyNotificationsRecyclerViewAdapter(List<Notification> notifications, OnListFragmentInteractionListener listener) {
        notificationList = notifications;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notifications_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Notification item = notificationList.get(position);
        holder.mItem = item;
        holder.tvEventName.setText(item.getEventName());
        holder.tvCity.setText(item.getCity().getName());
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM yy  HH:mm");
        sdf.setCalendar(item.getEventDate());
        String eventDateString = sdf.format(item.getEventDate().getTime());
        holder.tvEventDate.setText(eventDateString);
        sdf.setCalendar(item.getCheckDate());
        String checkDateString = sdf.format(item.getCheckDate().getTime());
        holder.tvCheckDate.setText(checkDateString);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onNotificationsListFragmentInteraction(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Notification mItem;
        final View mView;
        final TextView tvEventName;
        final TextView tvCity;
        final TextView tvEventDate;
        final TextView tvCheckDate;

        ViewHolder(View view) {
            super(view);
            mView = view;
            tvEventName = view.findViewById(R.id.tvEventName);
            tvCity = view.findViewById(R.id.tvCityName);
            tvEventDate = view.findViewById(R.id.tvEventDate);
            tvCheckDate = view.findViewById(R.id.tvCheckDate);
        }
        }
}
