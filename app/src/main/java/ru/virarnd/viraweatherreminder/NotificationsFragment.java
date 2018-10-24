package ru.virarnd.viraweatherreminder;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

    private OnListFragmentInteractionListener mListener;
    private ArrayList<Notification> notificationArrayList;
    private MyNotificationsRecyclerViewAdapter myNotificationsRecyclerViewAdapter;

    public MyNotificationsRecyclerViewAdapter getMyNotificationsRecyclerViewAdapter() {
        return myNotificationsRecyclerViewAdapter;
    }

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
        myNotificationsRecyclerViewAdapter = new MyNotificationsRecyclerViewAdapter(notificationArrayList, mListener);
        recyclerView.setAdapter(myNotificationsRecyclerViewAdapter);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new FabNewNotifyListener());
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnListFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onNotificationsListFragmentInteraction(int item);

        void onFabClick();
    }


    private class FabNewNotifyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mListener.onFabClick();
        }
    }
}
