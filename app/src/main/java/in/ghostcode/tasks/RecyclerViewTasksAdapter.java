package in.ghostcode.tasks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Coffee on 10/16/16.
 */

public class RecyclerViewTasksAdapter
        extends RecyclerView.Adapter<RecyclerViewTasksAdapter.ViewHolder> {

    private ArrayList<Task> tasks = new ArrayList<>();
    private Context context;

    public RecyclerViewTasksAdapter(ArrayList<Task> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Task task = tasks.get(position);
        holder.taskTitle.setText(task.getTitle());
        holder.taskCategory.setText(task.getCategory());
        holder.statusCheckBox.setChecked(task.getStatus());

        holder.statusCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                task.setStatus(isChecked);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Boolean checked = holder.statusCheckBox.isChecked();
                holder.statusCheckBox.setChecked(!checked);
                task.setStatus(!checked);

            }
        });

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle, taskCategory;
        CheckBox statusCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            taskTitle = (TextView) itemView.findViewById(R.id.task_title);
            taskCategory = (TextView) itemView.findViewById(R.id.task_category);
            statusCheckBox = (CheckBox) itemView.findViewById(R.id.status_check_box);
        }
    }
}
