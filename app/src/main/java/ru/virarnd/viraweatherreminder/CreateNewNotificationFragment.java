package ru.virarnd.viraweatherreminder;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import ru.virarnd.viraweatherreminder.common.City;
import ru.virarnd.viraweatherreminder.common.MyApp;

public class CreateNewNotificationFragment extends Fragment {

    private final static String TAG = CreateNewNotificationFragment.class.getName();

    private OnNewNotificationInteractionListener newNotificationListener;

    private TextInputEditText tiEventName;
    private AutoCompleteTextView acSelectCity;
    private TextInputEditText tiEventDate;
    private TextInputEditText tiEventTime;
    private TextInputEditText tiTimer;
    private Button btCreate;

    private String newNotificationName;
    private City newNotificationCity;
    private GregorianCalendar newNotificationDate;
    private GregorianCalendar newCheckNotificationTime;

    private ArrayList<City> cityArrayList;
    private ArrayAdapter<String> adapter;

    // TODO Позже будет понятно, какие еще нужны будут аргументы для передачи во фрагмент
    public static CreateNewNotificationFragment newInstance() {
        CreateNewNotificationFragment myFragment = new CreateNewNotificationFragment();
        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newNotificationDate = (GregorianCalendar) Calendar.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_notification, container, false);

        tiEventName = view.findViewById(R.id.tiEventName);
        acSelectCity = view.findViewById(R.id.acSelectCity);
        btCreate = view.findViewById(R.id.btCreate);

        // Слушатель на название события
        tiEventName.addTextChangedListener(new MyTextWatcherListener(tiEventName));

        // Слушатели для выбора города
        initAdapter(acSelectCity);
        acSelectCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals(MyApp.getAppContext().getString(R.string.select_new_city))) {
                    // TODO Пока выбрать новый город невозможно, но это будет делаться тут
                    showErrorSelectAnotherCity();
                }
                // Все остальные проверки см. в методе "addTextChangedListener"
            }
        });

        acSelectCity.setOnFocusChangeListener(new MyOnFocusChangeListener());
        acSelectCity.setOnClickListener(new MyOnClickListener());
        acSelectCity.addTextChangedListener(new MyTextWatcherListener(acSelectCity));

        // Установка даты
        tiEventDate = view.findViewById(R.id.tiEventDate);
        tiEventDate.setOnClickListener(new MyOnClickListener());
        tiEventDate.addTextChangedListener(new MyTextWatcherListener(tiEventDate));

        // Установка времени
        tiEventTime = view.findViewById(R.id.tiEvenTime);
        tiEventTime.setOnClickListener(new MyOnClickListener());
        tiEventTime.addTextChangedListener(new MyTextWatcherListener(tiEventTime));

        // Установка таймера
        tiTimer = view.findViewById(R.id.tiTimer);
        tiTimer.setOnClickListener(new MyOnClickListener());

        // Слушатель на кнопку "Создать"
        btCreate.setOnClickListener(new MyOnClickListener());

        return view;
    }

    public void setEventDate(Calendar calendar) {
        String myFormat = "dd.MM.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        tiEventDate.setText(sdf.format(calendar.getTime()));
        newNotificationDate.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void setEventTime(Calendar time) {
        String output = String.format("%02d:%02d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE));
        tiEventTime.setText(output);
        newNotificationDate.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
        newNotificationDate.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
    }

    public void setTimer(Calendar time) {
        String output = String.format("%2d:%02d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE));
        tiTimer.setText(output);
        // TODO Корректно обработать ввод времени срабатывания таймера: установить новую дату и время раньше на величину указанную в таймере
        // Установить время срабатывания, отнять часы/минуты от времени newNotificationDate
        newCheckNotificationTime = (GregorianCalendar) newNotificationDate.clone();
        newCheckNotificationTime.add(Calendar.HOUR_OF_DAY, -time.get(Calendar.HOUR_OF_DAY));
        newCheckNotificationTime.add(Calendar.MINUTE, -time.get(Calendar.MINUTE));
        String myFormat = "dd.MM.yyyy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        Log.d(TAG, "Таймер установлен на " + sdf.format(newCheckNotificationTime.getTime()));
    }


    private class MyTextWatcherListener implements TextWatcher {
        private View sourceView;

        private MyTextWatcherListener(View view) {
            this.sourceView = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
//            Log.d(TAG, "TextField has changed!");
            if (sourceView.getId() == R.id.tiEventName) {
                newNotificationName = tiEventName.getText().toString().trim();
                Log.d(TAG, "New text in name field: " + newNotificationName);
            }
            if (sourceView.getId() == R.id.acSelectCity) {
                String candidate = acSelectCity.getText().toString().trim();
                if (candidate.equals("")) {
                    acSelectCity.showDropDown();
                }
                newNotificationCity = newNotificationListener.checkCityNameAndGetKnown(candidate);
                if (newNotificationCity != null) {
                    acSelectCity.setError(null);
                    Log.d(TAG, "Выбран город: " + newNotificationCity.getName());
                }
            }
            checkAllFieldsToRepeatedNotification();
        }
    }

    private class MyOnFocusChangeListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if ((view.getId() == R.id.acSelectCity) && hasFocus) {
                if (acSelectCity.getText().toString().trim().equals("")) {
                    acSelectCity.showDropDown();
                }
            }
            if ((view.getId() == R.id.acSelectCity) && !hasFocus) {
                Log.d(TAG, "Start city name validation!");
                String candidate = acSelectCity.getText().toString().trim();
                newNotificationCity = newNotificationListener.checkCityNameAndGetKnown(candidate);
            }

            // После каждой смены фокуса брать название события, название города (если они не null), брать время и сравнивать с теми событиями, что уже записаны
            checkAllFieldsToRepeatedNotification();
        }

    }

    void showErrorSelectAnotherCity() {
        acSelectCity.setError("Неизвестный город, выберите другой");
    }

    void clearCityNameField() {
        acSelectCity.setText("");
    }


    public interface OnNewNotificationInteractionListener {
        City checkCityNameAndGetKnown(String candidate);

        ArrayList<City> askModelLastUsedCitiesWithSelectNew();

        void askEventDateFromDialog();

        void askEventTimeFromDialog();

        void askTimerFromDialog();

        boolean checkRepeatNotification(String newNotificationName, City newNotificationCity, GregorianCalendar newNotificationDate);

        void createNewNotification(String newNotificationName, City newNotificationCity, GregorianCalendar newNotificationDate, GregorianCalendar newCheckNotificationTime);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            newNotificationListener = (OnNewNotificationInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnNewNotificationInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        newNotificationListener = null;
    }

    private void initAdapter(AutoCompleteTextView autoCompleteTextView) {
        cityArrayList = newNotificationListener.askModelLastUsedCitiesWithSelectNew();
        ArrayList<String> lastUsedCities = new ArrayList<>();
        for (int i = 0; i < cityArrayList.size(); i++) {
            lastUsedCities.add(cityArrayList.get(i).getName());
        }
        // Назначаю адаптер
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, lastUsedCities);
        autoCompleteTextView.setAdapter(adapter);
    }

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case (R.id.acSelectCity):
                    acSelectCity.setError(null);
                    acSelectCity.showDropDown();
                    break;
                case (R.id.tiEventDate):
                    newNotificationListener.askEventDateFromDialog();
                    break;
                case (R.id.tiEvenTime):
                    newNotificationListener.askEventTimeFromDialog();
                    break;
                case (R.id.tiTimer):
                    newNotificationListener.askTimerFromDialog();
                    break;
                case (R.id.btCreate):
                    newNotificationListener.createNewNotification(newNotificationName, newNotificationCity, newNotificationDate, newCheckNotificationTime);
                    break;
                default:
            }
        }
    }

    public void checkAllFieldsToRepeatedNotification() {
        if (newNotificationName != null && newNotificationCity != null && newNotificationDate != null) {
            Log.d(TAG, "Старт проверки события на повтор");
            boolean isRepeatNotification = newNotificationListener.checkRepeatNotification(newNotificationName, newNotificationCity, newNotificationDate);
            if (isRepeatNotification) {
                showRepeatError();
            } else {
                clearRepeatError();
            }
        }
    }

    private void showRepeatError() {
        String errorMessage = "Уже есть такое событие!";
        acSelectCity.setError(errorMessage);
        tiEventName.setError(errorMessage);
        tiEventDate.setError(errorMessage);
        tiEventTime.setError(errorMessage);
    }

    private void clearRepeatError() {
        acSelectCity.setError(null);
        tiEventName.setError(null);
        tiEventDate.setError(null);
        tiEventTime.setError(null);
    }


}
