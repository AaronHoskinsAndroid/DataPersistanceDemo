package examples.aaronhoskins.com.datapersistancedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText etUserInputForSharedPref;
    TextView tvDisplayForCurrentSharPrefValue;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUserInputForSharedPref = findViewById(R.id.etUserInputForSharedPref);
        tvDisplayForCurrentSharPrefValue = findViewById(R.id.tvValueOfSharedPref);
        //get the shared pref I need
        sharedPreferences = getSharedPreferences("sample_pref", MODE_PRIVATE);
        getAndDisplayValueOfSharedPref();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSaveToSharedPref:
                saveToSharedPref();
                getAndDisplayValueOfSharedPref();
                break;
            case R.id.btnStartPhoneDbActivity:
                startActivity(new Intent(this, DatabaseActivity.class));
                break;
        }

    }

    public void saveToSharedPref() {
        final String valueToSave = etUserInputForSharedPref.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("value", valueToSave);
        editor.apply(); //Async, no return value
        //editor.commit();  //Sync, boolean return
    }

    public void getAndDisplayValueOfSharedPref() {
        final String valueToDisplay = sharedPreferences.getString("value", "No value saved yet");
        tvDisplayForCurrentSharPrefValue.setText(valueToDisplay);
    }
}
