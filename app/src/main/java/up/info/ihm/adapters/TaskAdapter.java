package up.info.ihm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import up.info.ihm.R;
import up.info.ihm.models.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private Context context;
    private List tasks;
    private ItemClickListener itemClickListener;

    public TaskAdapter(Context ct, List tasks, ItemClickListener itemClickListener) {
        context = ct;
        this.tasks = tasks;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.taskName_textView.setText(((Task) tasks.get(holder.getLayoutPosition())).getNom());
        holder.taskState_textView.setText(String.valueOf(((Task) tasks.get(holder.getLayoutPosition())).getState()));
        Task.State state = ((Task) tasks.get(holder.getLayoutPosition())).getState();
        switch (state) {
            case TERMINE:
                holder.taskState_textView.setText(context.getString(R.string.completed_state));
                break;
            case EN_COURS:
                holder.taskState_textView.setText(context.getString(R.string.doing_state));
                break;
            case NON_COMMENCE:
                holder.taskState_textView.setText(context.getString(R.string.notStarted_state));
                break;
            case EN_RETARD:
                holder.taskState_textView.setText(context.getString(R.string.late_state));
                break;
            case AUCUNE:
                holder.taskState_textView.setText(context.getString(R.string.none_state));
                break;
        }
        if (state == Task.State.TERMINE) {
            holder.task_chekBox.setChecked(true);
        } else {
            holder.task_chekBox.setChecked(false);
        }
        holder.task_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.displayTask(holder.getLayoutPosition());
            }
        });

        holder.task_chekBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheked) {
                itemClickListener.updateTask(holder.getLayoutPosition(), ((Task) tasks.get(holder.getLayoutPosition())).getId(), isCheked);
            }
        });

        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.deleteTask(holder.getLayoutPosition(), ((Task) tasks.get(holder.getLayoutPosition())).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView taskName_textView;
        TextView taskState_textView;
        CheckBox task_chekBox;
        Button delete_button;
        ConstraintLayout task_layout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName_textView = itemView.findViewById(R.id.id_task_textview);
            taskState_textView = itemView.findViewById(R.id.id_textView_state);
            task_chekBox = itemView.findViewById(R.id.id_task_checkBox);
            delete_button = itemView.findViewById(R.id.id_delete_btn);
            task_layout = itemView.findViewById(R.id.id_task_layout);

        }
    }

    public interface ItemClickListener {

        void displayTask(int position);

        void deleteTask(int position, int taskID);

        void updateTask(int position, int taskID, boolean isCheked);
    }

    @Override
    public long getItemId(int position) {
        return ((Task) tasks.get(position)).getId();
    }

}
