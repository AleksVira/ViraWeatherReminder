package ru.virarnd.viraweatherreminder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class TwoActivityMain extends AppCompatActivity {

    private static final String TAG = TwoActivityMain.class.getSimpleName();

    private EditText editText;
    private CheckBox cbWind, cbPressure, cbHumidity, cbMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_activity_main);

        String instanceState;
        if (savedInstanceState == null) {
            instanceState = "Первый запуск!";
        } else {
            instanceState = "Повторный запуск!";
        }
        Log.d(TAG, instanceState);

        editText = findViewById(R.id.editText);
        cbWind = findViewById(R.id.cbWind);
        cbPressure = findViewById(R.id.cbPressure);
        cbHumidity = findViewById(R.id.cbHumidity);

        cbMessage = findViewById(R.id.cbMessage);
        final TwoActivityPresenter presenter = TwoActivityPresenter.getInstance();
        presenter.setFirstActivity(this);

        editText.setOnKeyListener(new EditTextListener(this));

    }


   private class EditTextListener implements View.OnKeyListener {
        TwoActivityMain parentActivity;

        EditTextListener(TwoActivityMain twoActivityMain) {
            this.parentActivity = twoActivityMain;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String text = editText.getText().toString().trim();
                if (!text.equals("")) {
                    TwoActivityPresenter presenter = TwoActivityPresenter.getInstance();
                    presenter.sendRequestData(text, cbWind.isChecked(), cbPressure.isChecked(), cbHumidity.isChecked(), cbMessage.isChecked());
                }
                return true;
            }
            return false;
        }
    }
}

