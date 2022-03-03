package up.info.ihm.activities;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.util.Date;

import up.info.ihm.R;
import up.info.ihm.db.SQLiteDB;
import up.info.ihm.models.Task;
import up.info.ihm.sharedPreferences.SharedPrefrences;

public class TaskDetailsActivity extends AppCompatActivity {

    private int id;
    private String StartingDate;
    private String EndingDate;
    private EditText nameTextView;
    private EditText descriptionTextView;
    private EditText StartingDateTextView;
    private EditText EndingDateTextView;
    private EditText PriorityTextView;

    private SQLiteDB sqLiteDB;
    SharedPrefrences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_task_details);
        sqLiteDB = new SQLiteDB(getApplicationContext());
        sharedPreferences = new SharedPrefrences(getApplicationContext());

        Intent intent = this.getIntent();
        String name = intent.getStringExtra("taskName");
        String description = intent.getStringExtra("taskDescription");
        StartingDate = intent.getStringExtra("taskStartingDate");
        EndingDate = intent.getStringExtra("taskEndingDate");
        String priority = intent.getStringExtra("taskPriority");
        id = intent.getIntExtra("taskId", 0);

        nameTextView = findViewById(R.id.id_taskName);
        descriptionTextView = findViewById(R.id.id_taskDescription);
        StartingDateTextView = findViewById(R.id.id_task_date_start);
        EndingDateTextView = findViewById(R.id.id_task_date_finish);
        PriorityTextView = findViewById(R.id.id_task_priority);

        nameTextView.setText(name);
        descriptionTextView.setText(description);
        StartingDateTextView.setText(StartingDate);
        EndingDateTextView.setText(EndingDate);
        PriorityTextView.setText(priority);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void updateButtonOnClick(View V) {
        Task oldTask;
        Task newTask;
        Date Sdate = null;
        Date Edate = null;
        Task.Priority priority;
        if ((oldTask = sqLiteDB.getTaskById(id)) != null) {

            StartingDate = StartingDateTextView.getText().toString();
            EndingDate = EndingDateTextView.getText().toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
            if (!StartingDate.equals("none")) {
                try {
                    Sdate = dateFormat.parse(StartingDate.trim());
                } catch (ParseException pe) {
                    toast(getString(R.string.toast_msg1));
                    return;
                }
            }
            if (!EndingDate.equals("none")) {
                try {
                    Edate = dateFormat.parse(EndingDate.trim());
                } catch (ParseException pe) {
                    toast(getString(R.string.toast_msg1));
                    return;
                }
            }

            if (sharedPreferences.getAppLanguage().equals("fr")) {
                switch (PriorityTextView.getText().toString()) {
                    case "haut":
                        priority = Task.Priority.HAUT;
                        break;
                    case "moyen":
                        priority = Task.Priority.MOYEN;
                        break;
                    case "bas":
                        priority = Task.Priority.BAS;
                        break;
                    default:
                        toast(getString(R.string.toast_msg2));
                        return;
                }
            } else {
                switch (PriorityTextView.getText().toString()) {
                    case "high":
                        priority = Task.Priority.HAUT;
                        break;
                    case "medium":
                        priority = Task.Priority.MOYEN;
                        break;
                    case "low":
                        priority = Task.Priority.BAS;
                        break;
                    default:
                        toast(getString(R.string.toast_msg2));
                        return;
                }
            }

            newTask = new Task(nameTextView.getText().toString(), descriptionTextView.getText().toString(), Sdate, Edate, priority, oldTask.getCategorie());
            newTask.setState(oldTask.getState());
            newTask.setId(id);
            if (oldTask.compare(newTask)) {
                toast(getString(R.string.toast_msg3));
            } else {
                if (sqLiteDB.updateTask(newTask)) {
                    toast(getString(R.string.toast_msg4));
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    toast(getString(R.string.toast_msg5));
                }
            }
        } else {
            toast(getString(R.string.toast_msg6));
        }
    }
}