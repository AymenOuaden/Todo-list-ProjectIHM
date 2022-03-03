package up.info.ihm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import up.info.ihm.R;
import up.info.ihm.db.SQLiteDB;
import up.info.ihm.models.Task;
import up.info.ihm.sharedPreferences.SharedPrefrences;

public class AddTaskActivity extends AppCompatActivity {

    private EditText nom, description;
    private Button btn_add_task;
    private TextView start_date, end_date;
    private TextView start_time, end_time;
    private RadioButton btn_personal, btn_study, btn_sport, btn_shooping, btn_other;
    private RadioButton btn_high, btn_medium, btn_low;
    private DatePicker datePickerStart, datePickerEnd;
    private TimePicker timePickerStart, timePickerEnd;
    Date date_start = null, date_end = null;
    SQLiteDB sqLiteDB;
    int yearS, monthS, dayS, hourS, minuteS;
    int yearE, monthE, dayE, hourE, minuteE;
    SharedPrefrences sharedPrefrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_add_task);
        sharedPrefrences = new SharedPrefrences(getApplicationContext());
        nom = findViewById(R.id.nom);
        description = findViewById(R.id.description);
        btn_high = findViewById(R.id.btn_high);
        btn_medium = findViewById(R.id.btn_medium);
        btn_low = findViewById(R.id.btn_low);
        btn_personal = findViewById(R.id.btn_personal);
        btn_study = findViewById(R.id.btn_study);
        btn_sport = findViewById(R.id.btn_sport);
        btn_shooping = findViewById(R.id.btn_shooping);
        btn_other = findViewById(R.id.btn_other);
        start_date = findViewById(R.id.start_date);
        start_time = findViewById(R.id.start_time);
        datePickerStart = findViewById(R.id.dateStartPicker);
        timePickerStart = findViewById(R.id.timeStartPicker);
        end_date = findViewById(R.id.end_date);
        end_time = findViewById(R.id.end_time);
        datePickerEnd = findViewById(R.id.dateEndPicker);
        timePickerEnd = findViewById(R.id.timeEndPicker);
        btn_add_task = findViewById(R.id.btn_add_task);
        sqLiteDB = new SQLiteDB(getApplicationContext());
        // Date
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        final Calendar calendar = new GregorianCalendar(timeZone);
        calendar.setTimeInMillis(System.currentTimeMillis());
        yearS = calendar.get(Calendar.YEAR);
        monthS = calendar.get(Calendar.MONTH) + 1;
        dayS = calendar.get(Calendar.DAY_OF_MONTH);
        yearE = yearS;
        monthE = monthS;
        dayE = dayS;

        // Start date
        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datePickerStart.getVisibility() != View.VISIBLE)
                    datePickerStart.setVisibility(View.VISIBLE);
                else
                    datePickerStart.setVisibility(View.GONE);
                timePickerStart.setVisibility(View.GONE);
                datePickerEnd.setVisibility(View.GONE);
                timePickerEnd.setVisibility(View.GONE);
                start_date.setText(dayS + "-" + monthS + "-" + yearS);
            }
        });
        this.datePickerStart.init(yearS, monthS, dayS, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                yearS = year;
                monthS = month + 1;
                dayS = dayOfMonth;
                start_date.setText(dayOfMonth + "-" + monthS + "-" + year);
            }
        });
        // End date
        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datePickerEnd.getVisibility() != View.VISIBLE)
                    datePickerEnd.setVisibility(View.VISIBLE);
                else
                    datePickerEnd.setVisibility(View.GONE);
                timePickerStart.setVisibility(View.GONE);
                datePickerStart.setVisibility(View.GONE);
                timePickerEnd.setVisibility(View.GONE);
                end_date.setText(dayE + "-" + monthE + "-" + yearE);
            }
        });
        this.datePickerEnd.init(yearE, monthE, dayE, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                yearE = year;
                monthE = month + 1;
                dayE = dayOfMonth;
                end_date.setText(dayOfMonth + "-" + monthE + "-" + year);
            }
        });
        // time
        hourS = calendar.get(Calendar.HOUR);
        minuteS = calendar.get(Calendar.MINUTE);
        hourE = hourS;
        minuteE = minuteS;

        // start time
        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timePickerStart.getVisibility() != View.VISIBLE)
                    timePickerStart.setVisibility(View.VISIBLE);
                else
                    timePickerStart.setVisibility(View.GONE);
                datePickerStart.setVisibility(View.GONE);
                start_time.setText(hourS + ":" + minuteS);
            }
        });
        this.timePickerStart.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minuteT) {
                hourS = hourOfDay;
                minuteS = minuteT;
                start_time.setText(hourOfDay + ":" + minuteT);

            }
        });
        // end time
        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timePickerEnd.getVisibility() != View.VISIBLE)
                    timePickerEnd.setVisibility(View.VISIBLE);
                else
                    timePickerEnd.setVisibility(View.GONE);
                datePickerEnd.setVisibility(View.GONE);
                end_time.setText(hourE + ":" + minuteE);
            }
        });
        // start time
        this.timePickerEnd.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minuteT) {
                end_time.setText(hourOfDay + ":" + minuteT);
                hourE = hourOfDay;
                minuteE = minuteT;
            }
        });

        btn_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nom.getText()))
                    toastShort("Empty name");
                else {
                    if (start_date.getText().length() != 0) {
                        String day_s = "" + dayS, month_s = "" + monthS + 1, hour_s = "" + hourS, minute_s = "" + minuteS;
                        if (dayS < 10)
                            day_s = "0" + dayS;
                        if (monthS < 10)
                            month_s = "0" + monthS;
                        if (hourS < 10)
                            hour_s = "0" + hourS;
                        if (minuteS < 10)
                            minute_s = "0" + minuteS;
                        String str_date_start = "" + yearS + "-" + month_s + "-" + day_s + "T" + hour_s + ":" + minute_s + ":00";
                        Log.e("date str ", str_date_start);
                        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        try {
                            date_start = sf.parse(str_date_start);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if (end_date.getText().length() != 0) {
                        String day_e = "" + dayE, month_e = "" + monthE + 1, hour_e = "" + hourE, minute_e = "" + minuteE;
                        if (dayE < 10)
                            day_e = "0" + dayE;
                        if (monthE < 10)
                            month_e = "0" + monthE;
                        if (hourE < 10)
                            hour_e = "0" + hourE;
                        if (minuteE < 10)
                            minute_e = "0" + minuteE;
                        String str_date_end = "" + yearE + "-" + month_e + "-" + day_e + "T" + hour_e + ":" + minute_e + ":00";
                        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        try {
                            date_end = sf.parse(str_date_end);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    Task.Categorie categorie = getCategory();
                    Task.Priority priority = getPriority();
                    Task task = new Task(nom.getText().toString(), description.getText().toString(), date_start, date_end, priority, categorie);
                    Log.w("before appl add", task.toString());
                    addTask(task);
                    Intent intent = new Intent();
                    intent.putExtra("taskAdded", true);
                    int id = ((List<Task>) sqLiteDB.getAllTasks()).size();
                    intent.putExtra("taskID", id);
                    intent.putExtra("taskCategory", task.getCategorie());
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
    }

    private void goToMain(Task task) {
        Intent intent = new Intent(getApplication(), MainActivity.class);
        intent.putExtra("taskAdded", true);
        intent.putExtra("taskID", task.getId());
        intent.putExtra("taskCategory", task.getCategorie());
        setResult(RESULT_OK, intent);
        startActivity(intent);
    }

    private Task.Priority getPriority() {
        if (btn_high.isChecked())
            return Task.Priority.HAUT;
        else if (btn_medium.isChecked())
            return Task.Priority.MOYEN;
        else
            return Task.Priority.BAS;
    }

    private Task.Categorie getCategory() {
        if (btn_personal.isChecked())
            return Task.Categorie.PERSONNEL;
        else if (btn_study.isChecked())
            return Task.Categorie.ETUDE;
        else if (btn_sport.isChecked())
            return Task.Categorie.SPORT;
        else if (btn_shooping.isChecked())
            return Task.Categorie.COURSES;
        else
            return Task.Categorie.AUTRE;
    }

    private void addTask(Task task) {
        sqLiteDB.addTask(task);
    }

    private void getTasks() {
        List<Task> tasks = sqLiteDB.getAllTasks();

        for (Task task : tasks) {
            Log.w("tasks", task.toString());
        }
    }

    public void toastShort(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}