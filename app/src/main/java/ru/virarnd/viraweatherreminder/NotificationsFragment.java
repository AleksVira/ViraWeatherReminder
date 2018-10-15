package ru.virarnd.viraweatherreminder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.virarnd.viraweatherreminder.common.Notification;

import java.util.ArrayList;

import static ru.virarnd.viraweatherreminder.FirstActivity.NOTIFICATIONS_LIST;

public class NotificationsFragment extends Fragment {

    private ArrayList<Notification> notificationArrayList;
    private OnListFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications_list, container, false);

        Bundle bundle = getArguments();
        notificationArrayList = bundle.getParcelableArrayList(NOTIFICATIONS_LIST);

        // Set the adapter
        RecyclerView recyclerView = view.findViewById(R.id.notificationsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new MyNotificationsRecyclerViewAdapter(notificationArrayList, mListener));
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(int item);
    }
}
