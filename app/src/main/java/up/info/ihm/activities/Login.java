package up.info.ihm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import up.info.ihm.R;
import up.info.ihm.sharedPreferences.SharedPrefrences;

public class Login extends AppCompatActivity {

    Button btn_num_1, btn_num_2, btn_num_3, btn_num_4, btn_num_5, btn_num_6, btn_num_7, btn_num_8, btn_num_9, btn_num_0;
    Button first_pin, second_pin, thirth_pin, fourth_pin, btn_delete;
    int co = 0;
    String code_pin_s = "";
    int code_pin;
    SharedPrefrences sharedPrefrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefrences = new SharedPrefrences(getApplicationContext());
        btn_num_0 = findViewById(R.id.button_0);
        btn_num_1 = findViewById(R.id.button_1);
        btn_num_2 = findViewById(R.id.button_2);
        btn_num_3 = findViewById(R.id.button_3);
        btn_num_4 = findViewById(R.id.button_4);
        btn_num_5 = findViewById(R.id.button_5);
        btn_num_6 = findViewById(R.id.button_6);
        btn_num_7 = findViewById(R.id.button_7);
        btn_num_8 = findViewById(R.id.button_8);
        btn_num_9 = findViewById(R.id.button_9);
        first_pin = findViewById(R.id.first_pin);
        second_pin = findViewById(R.id.second_pin);
        thirth_pin = findViewById(R.id.third_pin);
        fourth_pin = findViewById(R.id.fourth_pin);
        btn_delete = findViewById(R.id.btn_delete);

        btn_num_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (co < 4) {
                    co++;
                    code_pin_s += "0";
                    changeBackButtonPin("fill");
                }
                if (co == 4)
                    checkCodePin();
            }
        });
        btn_num_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (co < 4) {
                    co++;
                    code_pin_s += "1";
                    changeBackButtonPin("fill");
                }
                if (co == 4)
                    checkCodePin();
            }
        });
        btn_num_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (co < 4) {
                    co++;
                    code_pin_s += "2";
                    changeBackButtonPin("fill");
                }
                if (co == 4)
                    checkCodePin();
            }
        });
        btn_num_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (co < 4) {
                    co++;
                    code_pin_s += "3";
                    changeBackButtonPin("fill");
                }
                if (co == 4)
                    checkCodePin();
            }
        });
        btn_num_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (co < 4) {
                    co++;
                    code_pin_s += "4";
                    changeBackButtonPin("fill");
                }
                if (co == 4)
                    checkCodePin();
            }
        });
        btn_num_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (co < 4) {
                    co++;
                    code_pin_s += "5";
                    changeBackButtonPin("fill");
                }
                if (co == 4)
                    checkCodePin();
            }
        });
        btn_num_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (co < 4) {
                    co++;
                    code_pin_s += "6";
                    changeBackButtonPin("fill");
                }
                if (co == 4)
                    checkCodePin();
            }
        });
        btn_num_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (co < 4) {
                    co++;
                    code_pin_s += "7";
                    changeBackButtonPin("fill");
                }
                if (co == 4)
                    checkCodePin();
            }
        });
        btn_num_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (co < 4) {
                    co++;
                    code_pin_s += "8";
                    changeBackButtonPin("fill");
                }
                if (co == 4)
                    checkCodePin();
            }
        });
        btn_num_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (co < 4) {
                    co++;
                    code_pin_s += "9";
                    changeBackButtonPin("fill");
                }
                if (co == 4)
                    checkCodePin();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code_pin_s.length() > 0)
                    code_pin_s = code_pin_s.substring(0, code_pin_s.length() - 1);
                if (co > 0) {
                    changeBackButtonPin("empty");
                    co--;
                }
            }
        });
    }

    private void changeBackButtonPin(String operation) {
        switch (co) {
            case 1:
                if (operation.equals("fill"))
                    first_pin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.Secondary));
                else
                    first_pin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.Gray));
                break;
            case 2:
                if (operation.equals("fill"))
                    second_pin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.Secondary));
                else
                    second_pin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.Gray));
                break;
            case 3:
                if (operation.equals("fill"))
                    thirth_pin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.Secondary));
                else
                    thirth_pin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.Gray));
                break;
            case 4:
                if (operation.equals("fill"))
                    fourth_pin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.Secondary));
                else
                    fourth_pin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.Gray));
                break;
            default:
                Log.w("Pin number", "The pin number is out of range");
        }
    }

    private void checkCodePin() {
        if (Integer.parseInt(code_pin_s) == sharedPrefrences.getCodePin()) {
            code_pin = Integer.parseInt(code_pin_s);
            goToDashboard();
        } else
            toastShort(getResources().getString(R.string.wrong_pin));
    }

    private void goToDashboard() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void toastShort(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
