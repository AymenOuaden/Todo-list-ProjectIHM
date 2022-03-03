package up.info.ihm.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import up.info.ihm.R;
import up.info.ihm.sharedPreferences.SharedPrefrences;

public class Parametres extends AppCompatActivity {

    Button btn_code_pin, btn_langue, btn_mode;
    SharedPrefrences sharedPrefrences;
    String language, mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefrences = new SharedPrefrences(getApplicationContext());
        setLanguage();
        mode = sharedPrefrences.getMode();
        setContentView(R.layout.activity_parametres);
        btn_code_pin = findViewById(R.id.btn_code_pin);
        btn_langue = findViewById(R.id.btn_langue);
        btn_mode = findViewById(R.id.btn_mode);
        btn_code_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCodePinParamatre();
            }
        });
        btn_langue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLanguageParamatre();
            }
        });
        btn_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToModeParametre();
            }
        });

    }

    private void goToCodePinParamatre() {
        Intent intent = new Intent(this, ParametresCodePin.class);
        startActivity(intent);
    }

    private void goToLanguageParamatre() {
        Intent intent = new Intent(this, ParametresLanguage.class);
        startActivity(intent);
    }

    private void goToModeParametre() {
        Intent intent = new Intent(this, DarkLightMode.class);
        startActivity(intent);
    }

    @SuppressWarnings("deprecation")
    private void setLanguage() {
        language = sharedPrefrences.getAppLanguage();
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!language.equals(sharedPrefrences.getAppLanguage())) {
            finish();
            startActivity(getIntent());
        }
        if (!mode.equals(sharedPrefrences.getMode())) {
            finish();
            startActivity(getIntent());
        }
    }

}
