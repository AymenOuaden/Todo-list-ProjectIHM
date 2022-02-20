package up.info.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeScreen extends AppCompatActivity {
    private static int time = 3500;
    SharedPrefrences sharedPrefrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        sharedPrefrences = new SharedPrefrences(getApplicationContext());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (sharedPrefrences.getCodePin() == 0) {
                    intent = new Intent(WelcomeScreen.this, AppPresentation.class);
                } else {
                    if (sharedPrefrences.isRequieredPin())
                        intent = new Intent(WelcomeScreen.this, MainActivity.class);
                    else
                        intent = new Intent(WelcomeScreen.this, Dashboard.class);
                }
                startActivity(intent);
                finish();
            }
        }, time);

    }
}
