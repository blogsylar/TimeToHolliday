package ru.macdroid.timetoholliday.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ru.macdroid.timetoholliday.model.Constants;
import ru.macdroid.timetoholliday.model.DBHelper;
import ru.macdroid.timetoholliday.R;
import ru.macdroid.timetoholliday.model.EventsConstructor;

public class DetailActivity extends Activity implements View.OnClickListener {

    private Handler handler;
    private Runnable runnable;
    private TextView hollidayEventName, hollidayEventStartDate, hollidayDaysLeft, hollidayHoursLeft, hollidayMinutesLeft, hollidaySecondsLeft;
    private ImageView hollidayEventPicture;
    private String dateRemain;
    private String timeRemain;
    private String eventName;
    private String eventId;
    private LinearLayout lDays, lTimerEnd;
    private long diff, days, hours, minutes, seconds;
    private SimpleDateFormat dateFormat;
    private Date eventDate, currentDate;
    private Button addTask;
    private ListView lstTask;
    private DBHelper dbHelper;
    private ArrayAdapter<String> adapter;
    Cursor cursor;
    ContentValues contentValues;
    SQLiteDatabase sqLiteDatabase;
    EventsConstructor eventsConstructor;
    String evId, evName, evDate, evDateInSql, evTime, evPicture;
    String eventIdFromAdapter, eventNameFromAdapter, eventDateInSqlFromAdapter, eventDateFromAdapter, eventTimeFromAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initUI();
        initLinksObjects();
        initVars();
        countDown();
        loadTaskList();
        //loadEventsList();
    }

    private void initLinksObjects() {

        dbHelper = new DBHelper(this);
    }

    private void loadTaskList() {

        ArrayList<String> taskList = dbHelper.getTaskList();

        if (adapter == null) {
            adapter = new ArrayAdapter<String>(this, R.layout.row, R.id.taskTitle, taskList);
            lstTask.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addAll(taskList);
            adapter.notifyDataSetChanged();
        }
    }


    private void initVars() {

        eventIdFromAdapter = getIntent().getStringExtra("id");
        eventNameFromAdapter = getIntent().getStringExtra("name");
        eventDateInSqlFromAdapter = getIntent().getStringExtra("dateInSql").replace(".","");
        eventDateFromAdapter = getIntent().getStringExtra("date");
        eventTimeFromAdapter = getIntent().getStringExtra("time").replace(":", "");

        Log.d("happy", "date: " + eventDateInSqlFromAdapter);


        hollidayEventName.setText(eventNameFromAdapter);

    }

    private void initUI() {

        hollidayEventName = (TextView) findViewById(R.id.hollidayEventName);
        hollidayEventStartDate = (TextView) findViewById(R.id.hollidayEventStartDate);
        hollidayDaysLeft = (TextView) findViewById(R.id.hollidayDaysLeft);
        hollidayHoursLeft = (TextView) findViewById(R.id.hollidayHoursLeft);
        hollidayMinutesLeft = (TextView) findViewById(R.id.hollidayMinutesLeft);
        hollidaySecondsLeft = (TextView) findViewById(R.id.hollidaySecondsLeft);
        hollidayEventPicture = (ImageView) findViewById(R.id.hollidayEventPicture);
        lstTask = (ListView) findViewById(R.id.lstTask);
        lDays = findViewById(R.id.lDays);
        lTimerEnd = findViewById(R.id.lTimerEnd);
        addTask = (Button) findViewById(R.id.btnAddTask);
        addTask.setOnClickListener(this);
    }


    public void countDown() {

        handler = new Handler();
        runnable = new Runnable() {

            @Override
            public void run() {

                handler.postDelayed(this, 1000);

                try {

                    dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                    eventDate = dateFormat.parse(eventDateInSqlFromAdapter + eventTimeFromAdapter);

                    currentDate = new Date();

                    if (!currentDate.after(eventDate)) {

                        diff = eventDate.getTime() - currentDate.getTime();

                        days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);

                        hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);

                        minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);

                        seconds = diff / 1000;

                        hollidayDaysLeft.setText("" + String.format("%02d", days));
                        hollidayHoursLeft.setText("" + String.format("%02d", hours));
                        hollidayMinutesLeft.setText("" + String.format("%02d", minutes));
                        hollidaySecondsLeft.setText("" + String.format("%02d", seconds));

                    } else {

                        Toast.makeText(DetailActivity.this, "HOREY!!!!!!!", Toast.LENGTH_SHORT).show();
                        lDays.setVisibility(View.GONE);
                        lTimerEnd.setVisibility(View.VISIBLE);
                        handler.removeCallbacks(runnable);
                        handler.removeMessages(0);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        handler.postDelayed(runnable, 0);
    }

    public void deleteTask(View view) {

        View parent = (View) view.getParent();
        TextView taskTitle = (TextView) findViewById(R.id.taskTitle);
        String task = String.valueOf(taskTitle.getText());
        dbHelper.deleteTask(task);
        loadTaskList();

    }

    public void loadEventsList() {

        sqLiteDatabase = dbHelper.getWritableDatabase();
        contentValues = new ContentValues();

        cursor = sqLiteDatabase.query(Constants.DB_TABLE_HOLIDAY, null, String.valueOf(1), null, null, null, null);

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                int eventId = cursor.getColumnIndex(Constants.DB_COLUMN_EVENT_ID);
                int eventName = cursor.getColumnIndex(Constants.DB_COLUMN_EVENT_NAME);
                int eventDate = cursor.getColumnIndex(Constants.DB_COLUMN_EVENT_DATE);
                int eventDateInSql = cursor.getColumnIndex(Constants.DB_COLUMN_EVENT_DATE_IN_SQL);
                int eventTime = cursor.getColumnIndex(Constants.DB_COLUMN_EVENT_TIME);
                int eventPicture = cursor.getColumnIndex(Constants.DB_COLUMN_EVENT_PICTURE);

                do {

                    evId = cursor.getString(eventId);
                    evName = cursor.getString(eventName);
                    evDate = cursor.getString(eventDate);
                    evDateInSql = cursor.getString(eventDateInSql);
                    evTime = cursor.getString(eventTime);
                    evPicture = cursor.getString(eventPicture);

                    //eventsConstructor = new EventsConstructor(evId, evName, evDate, evDateInSql, evTime, evPicture);



                    Log.d("happy", "DATABASE: " + evName + " " + evDate + " " + evDateInSql + " " + evTime + " " + evPicture);


                } while (cursor.moveToNext());

            } else {
                Log.d("happy", "0 rows");
            }

            cursor.close();
            sqLiteDatabase.close();

        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnAddTask:
                final EditText etTask = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add new task")
                        .setMessage("Add to your list")
                        .setView(etTask)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String task = String.valueOf(etTask.getText());
                                dbHelper.insertNewTask(task);
                                loadTaskList();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();

                dialog.show();
                break;
        }
    }
}