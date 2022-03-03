package up.info.ihm.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

import up.info.ihm.R;

public class WelcomeScreen extends AppCompatActivity {
    SharedPrefrences sharedPrefrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int time = 3500;
        sharedPrefrences = new SharedPrefrences(getApplicationContext());
        setLanguage();
        setContentView(R.layout.activity_welcome_screen);
        if (sharedPrefrences.getMode().equals("light"))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (sharedPrefrences.getCodePin() == 99999 && sharedPrefrences.getCodePinOperation().equals("createCodePin")) {
                    intent = new Intent(WelcomeScreen.this, AppPresentation.class);
                } else if (sharedPrefrences.getCodePin() == 9999 && sharedPrefrences.getCodePinOperation().equals("updatePinCode")) {
                    intent = new Intent(WelcomeScreen.this, CreateCodePin.class);
                } else {
                    if (sharedPrefrences.isRequieredPin())
                        intent = new Intent(WelcomeScreen.this, Login.class);
                    else
                        intent = new Intent(WelcomeScreen.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, time);
    }

    @SuppressWarnings("deprecation")
    private void setLanguage() {
        String language = sharedPrefrences.getAppLanguage();
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}
