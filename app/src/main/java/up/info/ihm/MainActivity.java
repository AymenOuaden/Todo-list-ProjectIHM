package up.info.ihm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_num_1,btn_num_2,btn_num_3,btn_num_4,btn_num_5,btn_num_6,btn_num_7,btn_num_8,btn_num_9, btn_num_0;
    Button first_pin,second_pin,thirth_pin,fourth_pin;
    int co=0;
    String code_pin_s="";
    int code_pin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        btn_num_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code_pin_s+="0";
                co++;
                changeBackButtonPin();
                if(co==4)
                    checkCodePin();
            }
        });
        btn_num_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code_pin_s+="1";
                co++;
                changeBackButtonPin();
                if (co==4)
                    checkCodePin();
            }
        });
        btn_num_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code_pin_s+="2";
                co++;
                changeBackButtonPin();
                if (co==4)
                    checkCodePin();
            }
        });
        btn_num_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code_pin_s+="3";
                co++;
                changeBackButtonPin();
                if (co==4)
                    checkCodePin();
            }
        });
        btn_num_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code_pin_s+="4";
                co++;
                changeBackButtonPin();
                if (co==4)
                    checkCodePin();
            }
        });
        btn_num_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code_pin_s+="5";
                co++;
                changeBackButtonPin();
                if (co==4)
                    checkCodePin();
            }
        });
        btn_num_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code_pin_s+="6";
                co++;
                changeBackButtonPin();
                if (co==4)
                    checkCodePin();
            }
        });
        btn_num_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code_pin_s+="7";
                co++;
                changeBackButtonPin();
                if (co==4)
                    checkCodePin();
            }
        });
        btn_num_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code_pin_s+="8";
                co++;
                changeBackButtonPin();
                if (co==4)
                    checkCodePin();
            }
        });
        btn_num_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code_pin_s+="9";
                co++;
                changeBackButtonPin();
                if (co==4)
                    checkCodePin();
            }
        });
    }

    private void changeBackButtonPin(){
        switch (co){
            case 1:
                first_pin.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.Primary));
                break;
            case 2:
                second_pin.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.Primary));

                break;
            case 3:
                thirth_pin.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.Primary));
                break;
            case 4:
                fourth_pin.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.Primary));
                break;
            default:
                Log.w("Pin number","The pin number is out of range");
        }
    }
    private void checkCodePin(){
        if(Integer.parseInt(code_pin_s)==1234)
            System.out.println("log ok");
        else
            System.out.println("log not ok");
    }
}
