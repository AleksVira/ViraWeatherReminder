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

public class NotificationsFragment extends Fragment {
    public final static String NOTIFICATIONS_LIST = "HL1MJC5H";

    private ArrayList<Notification> notificationArrayList;
    private OnListFragmentInteractionListener mListener;

    public static NotificationsFragment newInstance(ArrayList<Notification> notificationList) {
        NotificationsFragment myFragment = new NotificationsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(NOTIFICATIONS_LIST, notificationList);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(NOTIFICATIONS_LIST)) {
            notificationArrayList = getArguments().getParcelableArrayList(NOTIFICATIONS_LIST);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications_list, container, false);

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
