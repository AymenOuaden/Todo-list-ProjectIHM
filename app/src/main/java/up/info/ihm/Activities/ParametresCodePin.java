package up.info.ihm.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import up.info.ihm.R;

public class ParametresCodePin extends AppCompatActivity {
    Button btn_change_code_pin;
    Switch btn_required_code_pin;
    SharedPrefrences sharedPrefrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres_code_pin);

        sharedPrefrences = new SharedPrefrences(getApplicationContext());
        btn_required_code_pin = findViewById(R.id.switch_required_pin);
        btn_change_code_pin = findViewById(R.id.btn_change_code_pin);
        if (sharedPrefrences.isRequieredPin())
            btn_required_code_pin.setChecked(true);
        else
            btn_required_code_pin.setChecked(false);

        btn_required_code_pin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPrefrences.setIsRequieredPin(isChecked);
                btn_required_code_pin.setChecked(isChecked);
            }
        });

        btn_change_code_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefrences.setCodePin(0);
                sharedPrefrences.setCodePinOperation();
                goToCreateCodePin();
            }
        });
    }

    private void goToCreateCodePin() {
        Intent intent = new Intent(this, CreateCodePin.class);
        startActivity(intent);
    }
}
