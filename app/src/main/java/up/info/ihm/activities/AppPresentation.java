package up.info.ihm.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import up.info.ihm.R;

public class AppPresentation extends AppCompatActivity {
    Button btn_next, btn_leave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_presentation);
        btn_next = findViewById(R.id.btn_next);
        btn_leave = findViewById(R.id.btn_leave);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNext();
            }
        });
        btn_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitApp();
            }
        });
    }

    private void goToNext() {
        Intent intent = new Intent(this, CreateCodePin.class);
        startActivity(intent);
    }

    public void exitApp() {
        finish();
    }
}
