package in.ghostcode.tasks;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewTasksAdapter tasksAdapter;
    private ArrayList<Task> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tasks.add(new Task("Get PS4 CDs", "Personal", false));
        tasks.add(new Task("Buy 1L Milk", "Shopping", false));
        tasks.add(new Task("Finish assessment prep", "College", true));
        tasks.add(new Task("Do DevDroid Assignment", "Others", false));

        recyclerView = (RecyclerView) findViewById(R.id.tasks_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tasksAdapter = new RecyclerViewTasksAdapter(tasks, MainActivity.this);
        recyclerView.setAdapter(tasksAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_new_task, null);
                final TextView dialogTaskTitle = (TextView) dialogView.findViewById(R.id.dialog_task_title);
                final Spinner dialogSpinner = (Spinner) dialogView.findViewById(R.id.spinner_task_category);

                builder.setView(dialogView);
                builder.setTitle("Enter a new task");

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder. setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = dialogTaskTitle.getText().toString();
                        int pos = dialogSpinner.getSelectedItemPosition();
                        if(pos!=0 && !title.equals("")) {
                            String category = dialogSpinner.getSelectedItem().toString();
                            tasks.add(new Task(title, category, false));
                            tasksAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(MainActivity.this, "Select a title and category", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.show();


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
