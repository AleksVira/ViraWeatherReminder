package ru.virarnd.viraweatherreminder;

import android.content.Intent;

public class TwoActivityPresenter {
    final static String TEXT = "36I";
    final static String WIND_LBL = "74V7F41R";
    final static String PRESSURE_LBL = "X66";
    final static String HUMIDITY_LBL = "I7R822M";
    final static String MESSAGE_LBL = "D4HBVCB2";

    private static TwoActivityPresenter instance = null;
    private TwoActivityMain twoActivityMain;

    public static TwoActivityPresenter getInstance() {
        if (instance == null) {
            instance = new TwoActivityPresenter();
        }
        return instance;
    }

    public void sendRequestData(String townName, boolean windState, boolean pressureState, boolean humidityState, boolean messageState) {
        Intent intent = new Intent(twoActivityMain, RequstedActivity.class);
        intent.putExtra(TEXT, townName);
        intent.putExtra(WIND_LBL, windState);
        intent.putExtra(PRESSURE_LBL, pressureState);
        intent.putExtra(HUMIDITY_LBL, humidityState);
        intent.putExtra(MESSAGE_LBL, messageState);
        twoActivityMain.startActivity(intent);
    }

    public void setFirstActivity(TwoActivityMain parentActivity) {
        this.twoActivityMain = parentActivity;
    }
}
