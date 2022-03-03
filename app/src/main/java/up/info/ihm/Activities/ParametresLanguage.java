package up.info.ihm.Activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import up.info.ihm.R;

public class ParametresLanguage extends AppCompatActivity {
    RadioButton btn_french, btn_english;
    SharedPrefrences sharedPrefrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres_language);

        sharedPrefrences = new SharedPrefrences(getApplicationContext());
        btn_french = findViewById(R.id.btn_french);
        btn_english = findViewById(R.id.btn_english);
        if (sharedPrefrences.getAppLanguage().equals("fr"))
            btn_french.setChecked(true);
        else
            btn_english.setChecked(true);

        btn_french.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPrefrences.getAppLanguage().equals("fr")) {
                    sharedPrefrences.setAppLanguage("fr");
                    setLanguage("fr");
                }
            }
        });

        btn_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPrefrences.getAppLanguage().equals("en")) {
                    sharedPrefrences.setAppLanguage("en");
                    setLanguage("en");
                }
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void setLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        finish();
        startActivity(getIntent());
    }

}
