package up.info.ihm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import up.info.ihm.R;
import up.info.ihm.sharedPreferences.SharedPrefrences;

public class CreateCodePin extends AppCompatActivity {

    Button btn_num_1, btn_num_2, btn_num_3, btn_num_4, btn_num_5, btn_num_6, btn_num_7, btn_num_8, btn_num_9, btn_num_0;
    Button first_pin, second_pin, thirth_pin, fourth_pin, btn_delete, btn_validate;
    int co = 0;
    TextView output_code_pin, msg_show;
    String code_pin_s = "";
    int code_pin_1 = 99999, code_pin_2 = 99999;
    SharedPrefrences sharedPrefrences;
    String language, mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_code_pin);
        sharedPrefrences = new SharedPrefrences(getApplicationContext());
        language = sharedPrefrences.getAppLanguage();
        mode = sharedPrefrences.getMode();
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
        btn_validate = findViewById(R.id.btn_validate);
        output_code_pin = findViewById(R.id.output_code_pin);
        msg_show = findViewById(R.id.msg_show);
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
        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNext();
            }
        });
    }

    private void changeBackButtonPin(String operation) {
        switch (co) {
            case 1:
                if (operation.equals("fill"))
                    first_pin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.Primary));
                else
                    first_pin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
                break;
            case 2:
                if (operation.equals("fill"))
                    second_pin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.Primary));
                else
                    second_pin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
                break;
            case 3:
                if (operation.equals("fill"))
                    thirth_pin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.Primary));
                else
                    thirth_pin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
                break;
            case 4:
                if (operation.equals("fill"))
                    fourth_pin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.Primary));
                else
                    fourth_pin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
                break;
            default:
                Log.w("Pin number", "The pin number is out of range");
        }
    }

    private void checkCodePin() {
        if (code_pin_1 == 99999) {
            code_pin_1 = Integer.parseInt(code_pin_s);
            while (co > 0) {
                changeBackButtonPin("empty");
                co--;
            }
            output_code_pin.setText(getResources().getString(R.string.confirm_pin));
            code_pin_s = "";
        } else {
            if (Integer.parseInt(code_pin_s) == code_pin_1) {
                msg_show.setText(getResources().getString(R.string.code_the_same));
                msg_show.setVisibility(View.VISIBLE);
                btn_validate.setVisibility(View.VISIBLE);
                code_pin_2 = code_pin_1;
                btn_delete.setVisibility(View.INVISIBLE);
            } else
                toastShort(getResources().getString(R.string.codes_not_the_same));
        }
    }

    private void goToNext() {
        sharedPrefrences.setCodePin(code_pin_1);
        Intent intent;
        if (sharedPrefrences.isRequieredPin())
            intent = new Intent(this, Login.class);
        else
            intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void toastShort(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
