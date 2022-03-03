package up.info.ihm.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import up.info.ihm.R;

public class DarkLightMode extends AppCompatActivity {
    RadioButton btn_light, btn_dark;
    SharedPrefrences sharedPrefrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dark_light_mode);


        sharedPrefrences = new SharedPrefrences(getApplicationContext());
        btn_light = findViewById(R.id.btn_light);
        btn_dark = findViewById(R.id.btn_dark);
        if (sharedPrefrences.getMode().equals("light"))
            btn_light.setChecked(true);
        else
            btn_dark.setChecked(true);

        btn_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPrefrences.getMode().equals("light")) {
                    sharedPrefrences.setMode("light");
                    finish();
                    startActivity(getIntent());
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                }
            }
        });

        btn_dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPrefrences.getMode().equals("dark")) {
                    sharedPrefrences.setMode("dark");
                    finish();
                    startActivity(getIntent());
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                }
            }
        });

    }


}
