package up.info.ihm.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import up.info.ihm.models.Task;

public class SQLiteDB extends SQLiteOpenHelper {

    public SQLiteDB(Context context) {
        super(context, "AITODO", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS TASK(id INTEGER PRIMARY KEY NOT NULL , nom VARCHAR(30), description VARCHAR(500), date_debut VARCHAR(8), date_fin VARCHAR(8)," +
                " priority VARCHAR(30), categorie VARCHAR(30), state VARCHAR(30))";
        sqLiteDatabase.execSQL(sql);
    }

    public void addDefaultTasksTest() {
        if (countRaws() == 0) {
            addTask(new Task("wraiting song", "By understanding the elements of songwriting, you can learn to write great songs that are moving and memorable", null, null, Task.Priority.MOYEN, Task.Categorie.PERSONNEL));
            addTask(new Task("IHM", "End of Ihm Project", new Date(), null, Task.Priority.MOYEN, Task.Categorie.ETUDE));
            updateTask(2, Task.State.TERMINE);
            addTask(new Task("Natation", "nat nat nat", null, new Date(), Task.Priority.HAUT, Task.Categorie.SPORT));
        }
    }

    public void addTask(Task task) {
        String dateS = "";
        if (task.getDate_debut() != null)
            dateS = task.getDate_debut().toString();
        String dateF = "";
        if (task.getDate_fin() != null)
            dateF = task.getDate_fin().toString();

        Task.State state = Task.State.AUCUNE;
        if (task.getDate_debut() == null && task.getDate_fin() == null)
            state = Task.State.EN_COURS;
        else if (task.getDate_debut() != null && task.getDate_fin() == null)
            if (task.getDate_debut().compareTo(new Date()) <= 0)
                state = Task.State.EN_COURS;
            else
                state = Task.State.NON_COMMENCE;
        else if (task.getDate_debut() == null && task.getDate_fin() != null)
            if (task.getDate_fin().compareTo(new Date()) < 0)
                state = Task.State.EN_RETARD;
            else state = Task.State.EN_COURS;
        else if (task.getDate_debut() != null && task.getDate_fin() != null)
            if (new Date().compareTo(task.getDate_debut()) < 0)
                state = Task.State.NON_COMMENCE;
            else if (new Date().compareTo(task.getDate_fin()) > 0)
                state = Task.State.EN_RETARD;
            else
                state = Task.State.EN_COURS;
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO TASK VALUES (?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (countRaws() + 1));
        statement.bindString(2, task.getNom());
        statement.bindString(3, task.getDescription());
        statement.bindString(4, dateS);
        statement.bindString(5, dateF);
        statement.bindString(6, task.getPriority().toString());
        statement.bindString(7, task.getCategorie().toString());
        statement.bindString(8, state.toString());
        statement.executeInsert();
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        String selectQuery = "SELECT * FROM TASK";
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setNom(cursor.getString(1));
                task.setDescription(cursor.getString(2));
                String str_date_start = cursor.getString(3);
                SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                Date date_start = null;
                if (str_date_start.length() != 0) {
                    try {
                        date_start = sf.parse(str_date_start);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                task.setDate_debut(date_start);
                String str_date_end = cursor.getString(4);
                Date date_end = null;
                if (str_date_end.length() != 0) {
                    try {
                        date_end = sf.parse(str_date_end);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                task.setDate_fin(date_end);
                task.setPriority(getPriority(cursor.getString(5)));
                task.setCategorie(getCategory(cursor.getString(6)));
                task.setState(getState(cursor.getString(7)));
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        for (Task t : taskList) {
            Log.w("tasksssssssss", t.toString());
        }
        return taskList;
    }

    public List<Task> getAllTasksByCategorieAndState(String taskCategorie, String taskState) {
        List<Task> taskList = new ArrayList<>();
        String selectQueryBase = "SELECT * FROM TASK";
        String selectQueryCategorie = "", selectQueryState = "";
        String selectQuery;

        if (!taskCategorie.equals("all"))
            selectQueryCategorie = "categorie = '" + taskCategorie + "'";
        if (!taskState.equals("all"))
            selectQueryState = "state = '" + taskState + "'";

        selectQuery = selectQueryBase;
        if (selectQueryCategorie.length() > 0) {
            selectQuery = selectQuery + " " + "WHERE" + " " + selectQueryCategorie;
        }
        if (selectQueryState.length() > 0) {
            if (selectQueryCategorie.length() > 0)
                selectQuery = selectQuery + " " + "AND" + " " + selectQueryState;
            else
                selectQuery = selectQuery + " " + "WHERE" + " " + selectQueryState;
        }
        selectQuery = selectQuery + " ;";
        Log.d("bd bd ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setNom(cursor.getString(1));
                task.setDescription(cursor.getString(2));
                String str_date_start = cursor.getString(3);
                SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

                Date date_start = null;
                if (str_date_start.length() != 0) {
                    try {
                        date_start = sf.parse(str_date_start);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                task.setDate_debut(date_start);
                String str_date_end = cursor.getString(4);
                Date date_end = null;
                if (str_date_end.length() != 0) {
                    try {
                        date_end = sf.parse(str_date_end);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                task.setDate_fin(date_end);
                task.setPriority(getPriority(cursor.getString(5)));
                task.setCategorie(getCategory(cursor.getString(6)));
                task.setState(getState(cursor.getString(7)));
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        for (Task t : taskList) {
            Log.w("tasksssssssss", t.toString());
        }
        return taskList;
    }

    public Task getTaskById(int id_task) {
        Task task = new Task();
        String selectQuery = "SELECT * FROM TASK WHERE id = " + id_task + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            task.setId(Integer.parseInt(cursor.getString(0)));
            task.setNom(cursor.getString(1));
            task.setDescription(cursor.getString(2));
            String str_date_start = cursor.getString(3);
            SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

            Date date_start = null;
            if (str_date_start.length() != 0) {
                try {
                    date_start = sf.parse(str_date_start);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            task.setDate_debut(date_start);
            String str_date_end = cursor.getString(4);
            Date date_end = null;
            if (str_date_end.length() != 0) {
                try {
                    date_end = sf.parse(str_date_end);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            task.setDate_fin(date_end);
            task.setPriority(getPriority(cursor.getString(5)));
            task.setCategorie(getCategory(cursor.getString(6)));
            task.setState(getState(cursor.getString(7)));
        }
        return task;
    }

    private int countRaws() {
        int co = 0;
        String selectQuery = "SELECT COUNT (*) FROM TASK";
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            co = cursor.getInt(0);
        }
        System.out.println("count  = " + co);
        return co;
    }

    private boolean existTask(int id_task) {
        String selectQuery = "SELECT * FROM TASK WHERE  id = " + id_task + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);
        int co = 0;
        if (cursor.moveToFirst()) {
            co = 1;
        }
        return (co == 1);
    }

    public boolean updateTask(int id_task, Task.State state_task) {
        if (existTask(id_task)) {
            String updateQuery = " UPDATE TASK SET state = '" + state_task + "' WHERE  id = " + id_task + ";";
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(updateQuery);
            return true;
        }
        return false;
    }

    public boolean deleteTask(int id_task) {
        if (existTask(id_task)) {
            String deleteQuery = "DELETE FROM TASK WHERE  id = " + id_task + ";";
            Log.d("del", deleteQuery);
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(deleteQuery);
            return true;
        }
        return false;
    }

    public boolean updateTask(Task task) {
        String dateS = "";
        if (task.getDate_debut() != null)
            dateS = task.getDate_debut().toString();
        String dateF = "";
        if (task.getDate_fin() != null)
            dateF = task.getDate_fin().toString();

        Task.State state = Task.State.AUCUNE;
        if (task.getDate_debut() == null && task.getDate_fin() == null)
            state = Task.State.EN_COURS;
        else if (task.getDate_debut() != null && task.getDate_fin() == null)
            if (task.getDate_debut().compareTo(new Date()) <= 0)
                state = Task.State.EN_COURS;
            else
                state = Task.State.NON_COMMENCE;
        else if (task.getDate_debut() == null && task.getDate_fin() != null)
            if (task.getDate_fin().compareTo(new Date()) < 0)
                state = Task.State.EN_RETARD;
            else state = Task.State.EN_COURS;
        else if (task.getDate_debut() != null && task.getDate_fin() != null)
            if (new Date().compareTo(task.getDate_debut()) < 0)
                state = Task.State.NON_COMMENCE;
            else if (new Date().compareTo(task.getDate_fin()) > 0)
                state = Task.State.EN_RETARD;
            else
                state = Task.State.EN_COURS;
        String updateQuery = " UPDATE TASK SET nom = '" + task.getNom() + "', description = '" + task.getDescription() + "', " +
                "date_debut = '" + dateS + "', date_fin = '" + dateF + "', " +
                "categorie = '" + task.getCategorie() + "', priority = '" + task.getPriority() + "', state = '" + state + "'  " +
                "WHERE id = " + task.getId();
        Log.w("up", updateQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(updateQuery);
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private Task.Priority getPriority(String str_priority) {
        switch (str_priority.toUpperCase()) {
            case "HAUT":
                return Task.Priority.HAUT;
            case "BAS":
                return Task.Priority.BAS;
            default:
                return Task.Priority.MOYEN;
        }
    }

    private Task.State getState(String str_state) {
        switch (str_state.toUpperCase()) {
            case "NON_COMMENCE":
                return Task.State.NON_COMMENCE;
            case "EN_COURS":
                return Task.State.EN_COURS;
            case "TERMINE":
                return Task.State.TERMINE;
            case "EN_RETARD":
                return Task.State.EN_RETARD;
            default:
                return Task.State.AUCUNE;
        }
    }

    private Task.Categorie getCategory(String str_category) {
        switch (str_category.toUpperCase()) {
            case "PERSONNEL":
                return Task.Categorie.PERSONNEL;
            case "COURSES":
                return Task.Categorie.COURSES;
            case "ETUDE":
                return Task.Categorie.ETUDE;
            case "SPORT":
                return Task.Categorie.SPORT;
            default:
                return Task.Categorie.AUTRE;
        }
    }
}
