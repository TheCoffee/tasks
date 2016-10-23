package in.ghostcode.tasks;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Coffee on 10/16/16.
 */

public class RecyclerViewTasksAdapter
        extends RecyclerView.Adapter<RecyclerViewTasksAdapter.ViewHolder> {
    private TasksSQLHelper tasksSQLHelper;

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
        final int taskPosition = position;
        final Task task = tasks.get(position);
        holder.taskTitle.setText(task.getTitle());
        holder.taskCategory.setText(task.getCategory());
        holder.statusCheckBox.setChecked(task.getStatus());

        tasksSQLHelper = new TasksSQLHelper(context);

        holder.statusCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                task.setStatus(isChecked);
                tasksSQLHelper.updateStatus(task.getId(), task.getStatus());
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasks.remove(taskPosition);
                notifyDataSetChanged();
                tasksSQLHelper.deleteTask(task.getId());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean checked = holder.statusCheckBox.isChecked();
                holder.statusCheckBox.setChecked(!checked);
//                task.setStatus(!checked);

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_new_task, null);

                final TextView dialogTaskTitle = (TextView) dialogView.findViewById(R.id.dialog_task_title);
                dialogTaskTitle.setText(task.getTitle());

                final Spinner dialogSpinner = (Spinner) dialogView.findViewById(R.id.spinner_task_category);
                String[] categories = context.getResources().getStringArray(R.array.task_categories);
                int pos = 0;
                for (int i = 0; i < categories.length; i++) {
                    if (categories[i].equals(task.getCategory())) {
                        pos = i;
                    }
                }
                dialogSpinner.setSelection(pos);

                builder.setView(dialogView);
                builder.setTitle("Update task");

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = dialogTaskTitle.getText().toString();
                        Boolean update = false;
                        if (!title.equals("") && !title.equals(task.getTitle())) {
                            update = true;
                            tasksSQLHelper.updateTitle(task.getId(), title);
                            task.setTitle(title);
                        } else {
                            Toast.makeText(context, "No change in title", Toast.LENGTH_SHORT).show();
                        }

                        int pos = dialogSpinner.getSelectedItemPosition();
                        if (pos != 0) {
                            String category = dialogSpinner.getSelectedItem().toString();
                            if (!category.equals(task.getCategory())) {
                                update = true;
                                tasksSQLHelper.updateCategory(task.getId(), category);
                                task.setCategory(category);
                            } else {
                                Toast.makeText(context, "No change in category", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(context, "Category not chosen", Toast.LENGTH_SHORT).show();
                        }

                        if (update) {
//                            tasks.add(taskPosition, task);
                            RecyclerViewTasksAdapter.this.notifyDataSetChanged();
                        }
                    }
                });

                builder.show();
                return true;
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
        ImageView deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            taskTitle = (TextView) itemView.findViewById(R.id.task_title);
            taskCategory = (TextView) itemView.findViewById(R.id.task_category);
            statusCheckBox = (CheckBox) itemView.findViewById(R.id.status_check_box);
            deleteButton = (ImageView) itemView.findViewById(R.id.delete_button);
        }

    }
}
