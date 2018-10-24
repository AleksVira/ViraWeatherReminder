package ru.virarnd.viraweatherreminder;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ru.virarnd.viraweatherreminder.common.City;
import ru.virarnd.viraweatherreminder.common.DailyForecast;
import ru.virarnd.viraweatherreminder.common.ForecastHistory;
import ru.virarnd.viraweatherreminder.common.MyApp;
import ru.virarnd.viraweatherreminder.common.Notification;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.PRESSURE_MBAR;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.PRESSURE_MM;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.SHOW_HUMIDITY_OFF;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.SHOW_HUMIDITY_ON;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.SHOW_PRESSURE_OFF;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.SHOW_PRESSURE_ON;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.SHOW_WIND_SPEED_OFF;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.SHOW_WIND_SPEED_ON;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.TEMPERATURE_C;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.TEMPERATURE_F;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.WIND_SPEED_MH;
import static ru.virarnd.viraweatherreminder.WeatherPresenter.WIND_SPEED_MS;

public class FirstActivity extends AppCompatActivity implements CityListFragment.OnCityListFragmentInteractionListener,
        NotificationsFragment.OnListFragmentInteractionListener,
        ForecastFragment.OnForecastFragmentInteractionListener,
        HistoryForecastFragment.OnListFragmentInteractionListener,
        CreateNewNotificationFragment.OnNewNotificationInteractionListener,
        CustomDatePickerFragment.DatePickedListener,
        CustomTimePickerFragment.TimePickedListener,
        TimerPickerFragment.TimerPickedListener {


    private final static String TAG = FirstActivity.class.getName();
    public final static String PARCEL = "RM46";
    public final static String FORECAST = "XN7A";

    private CityListFragment cityListFragment;
    private NotificationsFragment notificationsFragment;

    private CreateNewNotificationFragment createNewNotificationFragment;
    //    private ForecastFragment forecastFragment;
    private WeatherPresenter weatherPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        weatherPresenter = WeatherPresenter.getInstance();
        weatherPresenter.attachView(this);

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            ArrayList<City> cityList = weatherPresenter.getLastUsedCityListWithSelectNew();
            bundle.putParcelableArrayList(PARCEL, cityList);

            cityListFragment = new CityListFragment();
            cityListFragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.mainFrame, cityListFragment);
            transaction.commit();
        }
    }

    @Override
    protected void onDestroy() {
        weatherPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onFragmentInteraction(int cityId) {
        Log.d(TAG, "Получен ID = " + cityId);
        weatherPresenter.setCityAndShowDetail(cityId);
    }

    @Override
    public void onCheckboxChanged(int boxId, boolean isChecked) {
        int condition = 0;
        switch (boxId) {
            case R.id.cbWind:
                condition = isChecked ? SHOW_WIND_SPEED_ON : SHOW_WIND_SPEED_OFF;
                break;
            case R.id.cbPressure:
                condition = isChecked ? SHOW_PRESSURE_ON : SHOW_PRESSURE_OFF;
                break;
            case R.id.cbHumidity:
                condition = isChecked ? SHOW_HUMIDITY_ON : SHOW_HUMIDITY_OFF;
                break;
            case R.id.swTemp:
                condition = isChecked ? TEMPERATURE_F : TEMPERATURE_C;
                break;
            case R.id.swWind:
                condition = isChecked ? WIND_SPEED_MH : WIND_SPEED_MS;
                break;
            case R.id.swPressure:
                condition = isChecked ? PRESSURE_MBAR : PRESSURE_MM;
                break;
            default:
                Log.e(TAG, "Кнопка настроек не определена!");
                break;
        }
        weatherPresenter.sendCheckBoxState(condition);

    }

    @Override
    public void onButtonClick(int buttonId) {
        weatherPresenter.sendButtonPressed(buttonId);
    }

    @Override
    public void onForecastButtonClick(int buttonId, int cityId) {
        weatherPresenter.sendButtonPressedAndCityId(buttonId, cityId);
    }

    @Override
    public void onFabClick() {
        weatherPresenter.sendNewNotificationFabClicked();
    }


    public void showForecast(DailyForecast cityDailyForecast) {
        ForecastFragment forecastFragment = ForecastFragment.newInstance(cityDailyForecast);
        String tag = getTag(forecastFragment);
        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager().beginTransaction().replace(R.id.childFrame, forecastFragment, tag).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, forecastFragment).addToBackStack(tag).commit();
        }
    }

    public void showNotifications(ArrayList<Notification> notificationList) {
        notificationsFragment = NotificationsFragment.newInstance(notificationList);
        String tag = getTag(notificationsFragment);
        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager().beginTransaction().replace(R.id.childFrame, notificationsFragment, tag).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, notificationsFragment).addToBackStack(tag).commit();
        }
    }

    public void showHistoryForecast(int cityId) {
        HistoryForecastFragment historyForecastFragment = HistoryForecastFragment.newInstance(cityId);
        String tag = getTag(historyForecastFragment);
        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager().beginTransaction().replace(R.id.childFrame, historyForecastFragment).addToBackStack(tag).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, historyForecastFragment).addToBackStack(tag).commit();
        }
    }

    public void showCreateNewNotificationFragment() {
        createNewNotificationFragment = CreateNewNotificationFragment.newInstance();
        String tag = getTag(createNewNotificationFragment);
        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager().beginTransaction().replace(R.id.childFrame, createNewNotificationFragment).addToBackStack(tag).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, createNewNotificationFragment).addToBackStack(tag).commit();
        }
    }

    private static String getTag(Fragment fragment) {
        return fragment.getClass().getName();
    }

    // Запрашиваю историю прогноза за 5 дней, считая от текущего.
    public ForecastHistory askHistoryForecast(int cityId) {
        return weatherPresenter.getForecastHistoryByCityId(cityId);
    }

    @Override
    public void onNotificationsListFragmentInteraction(int item) {
        //TODO Определить реакцию списка на нажатие элемента
    }

    @Override
    public void onHistoryListFragmentInteraction(DailyForecast item) {
        //TODO Определить реакцию списка с историей погоды на нажатие элемента
    }

    public City askCityByName(String cityName) {
        return weatherPresenter.askPresenterCityByName(cityName);
    }

    public ArrayList<City> askModelLastUsedCities() {
        return weatherPresenter.getLastUsedCityList();
    }

    @Override
    public City checkCityNameAndGetKnown(String candidate) {
        // TODO Пока буду показывать ошибку и прошу выбрать другой город, позже будет возможность выбрать новый город из тех что доступны (в т.ч. в интернете)
        if (candidate.equals(MyApp.getAppContext().getString(R.string.select_new_city))) {
            createNewNotificationFragment.showErrorSelectAnotherCity();
            createNewNotificationFragment.clearCityNameField();
            return null;
        }
        if (isKnownCity(candidate)) {
            Log.d(TAG, "Ok, это известный город");
            return askCityByName(candidate);
        } else {
            createNewNotificationFragment.showErrorSelectAnotherCity();
            return null;
        }
    }

    private boolean isKnownCity(String candidate) {
        City candidateCity = askCityByName(candidate);
        return askModelLastUsedCitiesWithSelectNew().contains(candidateCity);
    }

    @Override
    public ArrayList<City> askModelLastUsedCitiesWithSelectNew() {
        return weatherPresenter.getLastUsedCityListWithSelectNew();
    }

    @Override
    public void askEventDateFromDialog() {
        DialogFragment dialogFragment = new CustomDatePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "DIALOG_DATE");
    }

    @Override
    public void askEventTimeFromDialog() {
        DialogFragment dialogFragment = new CustomTimePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "DIALOG_TIME");
    }

    @Override
    public void askTimerFromDialog() {
        DialogFragment dialogFragment = new TimerPickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "DIALOG_TIMER");
    }

    @Override
    public boolean checkRepeatNotification(String newNotificationName, City newNotificationCity, GregorianCalendar newNotificationDate) {
        return weatherPresenter.checkNewNotification(newNotificationName, newNotificationCity, newNotificationDate);
    }

    @Override
    public void createNewNotification(String newNotificationName, City newNotificationCity, GregorianCalendar newNotificationDate, GregorianCalendar newCheckNotificationTime) {
        weatherPresenter.addNewNotification(newNotificationName, newNotificationCity, newNotificationDate, newCheckNotificationTime);
    }

    @Override
    public void onDatePicked(Calendar calendar) {
        createNewNotificationFragment.setEventDate(calendar);
        createNewNotificationFragment.checkAllFieldsToRepeatedNotification();
    }

    @Override
    public void onTimePicked(Calendar time) {
        createNewNotificationFragment.setEventTime(time);
        createNewNotificationFragment.checkAllFieldsToRepeatedNotification();
    }

    @Override
    public void onTimerPicked(Calendar time) {
        createNewNotificationFragment.setTimer(time);
        createNewNotificationFragment.checkAllFieldsToRepeatedNotification();
    }

    public void closeCreationFragment() {
        // Закрываю фрагмент "Создать новое уведомление" и обновляю список уведомлений.
        notificationsFragment.getMyNotificationsRecyclerViewAdapter().notifyDataSetChanged();
        getSupportFragmentManager().popBackStack();;
    }
}
