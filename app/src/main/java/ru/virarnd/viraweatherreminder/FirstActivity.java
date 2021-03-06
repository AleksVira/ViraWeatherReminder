package ru.virarnd.viraweatherreminder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ru.virarnd.viraweatherreminder.common.City;
import ru.virarnd.viraweatherreminder.common.CurrentWeather;
import ru.virarnd.viraweatherreminder.common.ForecastHistory;
import ru.virarnd.viraweatherreminder.common.MyApp;
import ru.virarnd.viraweatherreminder.common.Notification;
import ru.virarnd.viraweatherreminder.sms.SmsSendReceiver;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static ru.virarnd.viraweatherreminder.sms.SmsSender.SMS_SENT;

public class FirstActivity extends AppCompatActivity implements CityListFragment.OnCityListFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener,
        NotificationsFragment.OnListFragmentInteractionListener,
        ForecastFragment.OnForecastFragmentInteractionListener,
        HistoryForecastFragment.OnListFragmentInteractionListener,
        CreateNewNotificationFragment.OnNewNotificationInteractionListener,
        CustomDatePickerFragment.DatePickedListener,
        CustomTimePickerFragment.TimePickedListener,
        TimerPickerFragment.TimerPickedListener {


    private final static String TAG = FirstActivity.class.getName();
    public final static String PARCEL = "RM46";
    public final int SMS_READ_PERMISSION_REQUEST = 101;
    public final int SMS_RECEIVE_PERMISSION_REQUEST = 111;
    public final int SMS_SEND_PERMISSION_REQUEST = 121;

    private CityListFragment cityListFragment;
    private NotificationsFragment notificationsFragment;

    private CreateNewNotificationFragment createNewNotificationFragment;
    private WeatherPresenter weatherPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        weatherPresenter = WeatherPresenter.getInstance();
        weatherPresenter.attachMainView(this);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        checkPermissionForSms();

    }

    private void checkPermissionForSms() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission for reading SMS is not granted");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, SMS_READ_PERMISSION_REQUEST);
        } else {
            Log.d(TAG, "Permission for reading is granted");
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission for receive SMS is not granted");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, SMS_RECEIVE_PERMISSION_REQUEST);
        } else {
            Log.d(TAG, "Permission for receive is granted");
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission for sending is not granted");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_SEND_PERMISSION_REQUEST);
            weatherPresenter.setStatusOfSendSmsButton(false);
        } else {
            weatherPresenter.setStatusOfSendSmsButton(true);
            Log.d(TAG, "Permission for sending is granted");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SMS_READ_PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permission for reading SMS has been granted");
                    Toast.makeText(MyApp.getContext(), "Wow, you can read SMS!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "Permission for reading SMS has been denied or request cancelled");
                    Toast.makeText(MyApp.getContext(), "You can not read SMS!", Toast.LENGTH_LONG).show();
                }
                break;
            case SMS_RECEIVE_PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permission for receive SMS has been granted");
                    Toast.makeText(MyApp.getContext(), "Wow, you can receive SMS!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "Permission for receiving SMS has been denied or request cancelled");
                    Toast.makeText(MyApp.getContext(), "You can not receive SMS!", Toast.LENGTH_LONG).show();
                }
                break;
            case SMS_SEND_PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permission for sending SMS has been granted");
                    Toast.makeText(MyApp.getContext(), "Wow, you can send SMS!", Toast.LENGTH_SHORT).show();
                    weatherPresenter.setStatusOfSendSmsButton(true);
                } else {
                    Log.d(TAG, "Permission for sending SMS has been denied or request cancelled");
                    Toast.makeText(MyApp.getContext(), "You can not send SMS!", Toast.LENGTH_LONG).show();
                    weatherPresenter.setStatusOfSendSmsButton(false);
                }
                break;
            default:
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_show_notifications:
                weatherPresenter.openNotificationsFragment();
                break;
            case R.id.action_settings:
                weatherPresenter.openSettingsFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        weatherPresenter.detachMainView();
        super.onDestroy();
    }

    @Override
    public void onFragmentInteraction(int cityId) {
        Log.d(TAG, "Получен ID = " + cityId);
        weatherPresenter.setCityAndShowDetail(cityId);
    }

    @Override
    public void onButtonClick(int buttonId) {
        weatherPresenter.sendButtonPressed(buttonId);
    }

    @Override
    public void onFragmentForecastHistoryButtonClick(int cityId) {
        weatherPresenter.sendButtonPressedAndCityId(cityId);
    }

    @Override
    public void onFragmentForecastSendSmsButtonClick(CurrentWeather weather) {
        // Отправляю сам себе погоду через СМС
        weatherPresenter.sendCurrentWeatherBySms("+79281866472", weather);
    }

    @Override
    public void onFabClick() {
        weatherPresenter.sendNewNotificationFabClicked();
    }


    public void showForecast(int cityId, CurrentWeather cityCurrentWeather, boolean statusOfSendSmsButton) {
        ForecastFragment forecastFragment = ForecastFragment.newInstance(cityId, cityCurrentWeather, statusOfSendSmsButton);
        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager().beginTransaction().replace(R.id.childFrame, forecastFragment, ForecastFragment.TAG).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, forecastFragment, ForecastFragment.TAG).addToBackStack(ForecastFragment.TAG).commit();
        }
    }

    public void showNotifications(ArrayList<Notification> notificationList) {
        notificationsFragment = NotificationsFragment.newInstance(notificationList);
        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager().beginTransaction().replace(R.id.childFrame, notificationsFragment, NotificationsFragment.TAG).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, notificationsFragment, NotificationsFragment.TAG).addToBackStack(NotificationsFragment.TAG).commit();
        }
    }

    public void showHistoryForecast(int cityId) {
        HistoryForecastFragment historyForecastFragment = HistoryForecastFragment.newInstance(cityId);
        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager().beginTransaction().replace(R.id.childFrame, historyForecastFragment, HistoryForecastFragment.TAG).addToBackStack(HistoryForecastFragment.TAG).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, historyForecastFragment, HistoryForecastFragment.TAG).addToBackStack(HistoryForecastFragment.TAG).commit();
        }
    }

    public void showCreateNewNotificationFragment() {
        createNewNotificationFragment = CreateNewNotificationFragment.newInstance();
        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager().beginTransaction().replace(R.id.childFrame, createNewNotificationFragment, CreateNewNotificationFragment.TAG).addToBackStack(CreateNewNotificationFragment.TAG).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, createNewNotificationFragment, CreateNewNotificationFragment.TAG).addToBackStack(CreateNewNotificationFragment.TAG).commit();
        }
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
    public void onHistoryListFragmentInteraction(CurrentWeather item) {
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
        if (candidate.equals(MyApp.getContext().getString(R.string.select_new_city))) {
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
        getSupportFragmentManager().popBackStack();
        ;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_about:
//            showAboutFragment();
                break;
            case R.id.nav_feedback:
//            showFeedbackFragment();
                break;
            default:
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void tryUpdateCurrentForecast(CurrentWeather currentWeather) {
        // Проверить активен ли сейчас фрагмент ForecastFragment. Если да, отправить в него новый прогноз и показать его.
        ForecastFragment forecastFragment = (ForecastFragment) getSupportFragmentManager().findFragmentByTag(ForecastFragment.TAG);
        if (forecastFragment != null && forecastFragment.isVisible()) {
            // Если на экране показан фрагмент с тем же самым городом, обновляю фрагмент
            if (forecastFragment.cityOnScreenIsTheSame(currentWeather)) {
                forecastFragment.updateForecastData(currentWeather);
            }
        }
    }


}
