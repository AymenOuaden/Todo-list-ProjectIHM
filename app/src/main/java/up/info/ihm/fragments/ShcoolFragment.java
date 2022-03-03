package up.info.ihm.fragments;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import up.info.ihm.activities.TaskDetailsActivity;
import up.info.ihm.R;
import up.info.ihm.adapters.TaskAdapter;
import up.info.ihm.db.SQLiteDB;
import up.info.ihm.models.Task;


public class ShcoolFragment extends Fragment implements TaskAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private List tasks;
    private TaskAdapter taskAdapter;
    private SQLiteDB sqLiteDB;

    public ShcoolFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        sqLiteDB = new SQLiteDB(getActivity().getApplicationContext());
        tasks = new ArrayList<Task>();
        tasks = sqLiteDB.getAllTasksByCategorieAndState((Task.Categorie.ETUDE.toString()), "all");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shcool, container, false);
        recyclerView = view.findViewById(R.id.id_shcool_recyclerView);
        // a modifer todo
        tasks = new ArrayList<Task>();
        tasks = sqLiteDB.getAllTasksByCategorieAndState(Task.Categorie.ETUDE.toString(), "all");

        taskAdapter = new TaskAdapter(getContext(), tasks, this);
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }


    public void filterByAllTask() {

        tasks.clear();
        List tmp_tasks = sqLiteDB.getAllTasksByCategorieAndState((Task.Categorie.ETUDE).toString(), "all");
        tasks.addAll(tmp_tasks);
        if (!recyclerView.isComputingLayout()) {
            taskAdapter.notifyDataSetChanged();
        }
    }

    public void filterByNotStatedTask() {

        tasks.clear();
        List tmp_tasks = sqLiteDB.getAllTasksByCategorieAndState((Task.Categorie.ETUDE).toString(), (Task.State.NON_COMMENCE).toString());
        tasks.addAll(tmp_tasks);
        if (!recyclerView.isComputingLayout()) {
            taskAdapter.notifyDataSetChanged();
        }
    }

    public void filterByDoingTask() {

        tasks.clear();
        List tmp_tasks = sqLiteDB.getAllTasksByCategorieAndState((Task.Categorie.ETUDE).toString(), (Task.State.EN_COURS).toString());
        tasks.addAll(tmp_tasks);
        if (!recyclerView.isComputingLayout()) {
            taskAdapter.notifyDataSetChanged();
        }
    }

    public void filterByLateTask() {

        tasks.clear();
        List tmp_tasks = sqLiteDB.getAllTasksByCategorieAndState((Task.Categorie.ETUDE).toString(), (Task.State.EN_RETARD).toString());
        tasks.addAll(tmp_tasks);
        if (!recyclerView.isComputingLayout()) {
            taskAdapter.notifyDataSetChanged();
        }
    }

    public void filterByCompletedTask() {

        tasks.clear();
        List tmp_tasks = sqLiteDB.getAllTasksByCategorieAndState((Task.Categorie.ETUDE).toString(), (Task.State.TERMINE).toString());
        tasks.addAll(tmp_tasks);
        if (!recyclerView.isComputingLayout()) {
            taskAdapter.notifyDataSetChanged();
        }
    }

    public void refreshAddingTask(int id) {

        this.tasks.add(sqLiteDB.getTaskById(id));
        if (!recyclerView.isComputingLayout()) {
            this.taskAdapter.notifyItemInserted(taskAdapter.getItemCount());
        }

    }

    @Override
    public void displayTask(int position) {

        Intent intent = new Intent(getContext(), TaskDetailsActivity.class);
        intent.putExtra("taskName", ((Task) tasks.get(position)).getNom());
        intent.putExtra("taskDescription", ((Task) tasks.get(position)).getDescription());
        intent.putExtra("taskId", ((Task) tasks.get(position)).getId());

        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date taskStartingDate = ((Task) tasks.get(position)).getDate_debut();
        Date taskFinishingDate = ((Task) tasks.get(position)).getDate_fin();
        if (taskStartingDate != null) {
            intent.putExtra("taskStartingDate", formatter.format(taskStartingDate));
        } else {
            intent.putExtra("taskStartingDate", "none");
        }
        if (taskFinishingDate != null) {
            intent.putExtra("taskEndingDate", formatter.format(taskFinishingDate));
        } else {
            intent.putExtra("taskEndingDate", "none");
        }
        Task.Priority priority = ((Task) tasks.get(position)).getPriority();
        String taskPriority;
        switch (priority) {
            case BAS:
                taskPriority = getString(R.string.lowPriority);
                break;
            case HAUT:
                taskPriority = getString(R.string.highPriority);
                break;
            case MOYEN:
                taskPriority = getString(R.string.mediumPriority);
                break;
            default:
                taskPriority = "none";
                break;
        }
        intent.putExtra("taskPriority", taskPriority);
        startActivityForResult(intent, 2);
    }

    @Override
    public void updateTask(int position, int taskID, boolean isCheked) {

        if (isCheked) {
            if (sqLiteDB.updateTask(taskID, Task.State.TERMINE)) {
                Task editedTask = (Task) tasks.get(position);
                editedTask.setState(Task.State.TERMINE);
                if (!recyclerView.isComputingLayout()) {
                    taskAdapter.notifyDataSetChanged();
                }
            } else {
                // Erreur task non trouvée
                Log.d("database", "updateTask: could not edit the state of the task Id = " + taskID);
            }
        } else {
            if (sqLiteDB.updateTask(taskID, Task.State.EN_COURS)) {
                Task editedTask = (Task) tasks.get(position);
                editedTask.setState(Task.State.EN_COURS);
                if (!recyclerView.isComputingLayout()) {
                    taskAdapter.notifyDataSetChanged();
                }
            } else {
                // Erreur task non trouvée
                Log.d("database", "updateTask: could not edit the state of the task Id = " + taskID);
            }
        }
    }


    @Override
    public void deleteTask(int position, int taskID) {

        if (sqLiteDB.deleteTask(taskID)) {
            tasks.remove(position);
            if (!recyclerView.isComputingLayout()) {
                taskAdapter.notifyItemRemoved(position);
            }
        } else {
            Log.d("database", "deleteTask: could not delete the task Id = " + taskID);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (!recyclerView.isComputingLayout()) {
                List tmp_tasks = sqLiteDB.getAllTasksByCategorieAndState((Task.Categorie.ETUDE.toString()), "all");
                tasks.clear();
                tasks.addAll(tmp_tasks);
                taskAdapter.notifyDataSetChanged();
            }
        }
    }

}