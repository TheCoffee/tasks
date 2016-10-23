package in.ghostcode.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Coffee on 10/23/16.
 */

public class TasksSQLHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tasks";
    private static final int DATABASE_VERSION = 1;

    private static final String TASKS_TABLE = "tasks";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_STATUS = "status";

    public TasksSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // Create Database
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Tables
        String query = "CREATE TABLE " + TASKS_TABLE + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_TITLE + " TEXT," +
                KEY_CATEGORY + " TEXT," +
                KEY_STATUS + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade the tables
        // Get the values from the old table
        db.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE);
        onCreate(db);
        // Insert values from the old table in the new table
    }

    // CRUD operations
    public void insertIntoDB(String title, String category, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, title);
        contentValues.put(KEY_CATEGORY, category);
        contentValues.put(KEY_STATUS, status);

        db.insert(TASKS_TABLE, null, contentValues);
        db.close();
    }

    public ArrayList<Task> getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM " + TASKS_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                task.setId(id);
                task.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                task.setCategory(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));
                String status = cursor.getString(cursor.getColumnIndex(KEY_STATUS));
                task.setStatus(Boolean.valueOf(status));
                tasks.add(task);
            } while (cursor.moveToNext());
        }

        return tasks;
    }

    public void updateStatus(int id, Boolean status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_STATUS, String.valueOf(status));

        db.update(TASKS_TABLE, contentValues, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateTitle(int id, String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, title);

        db.update(TASKS_TABLE, contentValues, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateCategory(int id, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CATEGORY, category);

        db.update(TASKS_TABLE, contentValues, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TASKS_TABLE, KEY_ID + "=?", new String[] {id +""});
        db.close();
    }


}
