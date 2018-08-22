package ru.macdroid.timetoholliday.views;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ru.macdroid.timetoholliday.model.Constants;
import ru.macdroid.timetoholliday.model.DBHelper;
import ru.macdroid.timetoholliday.R;
import ru.macdroid.timetoholliday.adapters.EventListAdapter;
import ru.macdroid.timetoholliday.model.EventsConstructor;

public class HollidayList extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String TAG = "happy";


    private DBHelper dbHelper;
    private RecyclerView eventsRecycler;
    ArrayList<EventsConstructor> eventsArrayList;
    EventListAdapter eventListAdapter;
    EventsConstructor eventsConstructor;
    Cursor cursor;
    SQLiteDatabase sqLiteDatabase;
    String evId, evName, evDate, evDateInSql, evTime, evPicture;
    FloatingActionButton fab;
    ContentValues contentValues;

    EditText eventName, eventPicture;
    TextView eventDate,eventTime;

    String eName;
    String eDate, eDateInSql;
    String eTime;
    String ePicture;

    ImageView ivPictureEvent;

    Button btnAddDateEventCalendar, btnAddTimeEventCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holliday_list);

        initUI();
        initLinksObjects();
        eventAdapter();
        loadEventsList();
    }



    private void initLinksObjects() {

        eventsArrayList = new ArrayList<EventsConstructor>();


        dbHelper = new DBHelper(this);


    }

    private void eventAdapter() {

        eventsRecycler.setLayoutManager(new LinearLayoutManager(this));
        eventListAdapter = new EventListAdapter(this, eventsArrayList);
        eventsRecycler.setAdapter(eventListAdapter);

    }

    private void initUI() {

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        eventsRecycler = (RecyclerView) findViewById(R.id.eventsRecycler);
    }

    public void loadEventsList() {

        sqLiteDatabase = dbHelper.getWritableDatabase();
        contentValues = new ContentValues();

        if (eventsArrayList != null) {
            eventsArrayList.clear();
        }

        cursor = sqLiteDatabase.query(Constants.DB_TABLE_HOLIDAY, null, null, null, null, null, null, null);

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

                    eventsConstructor = new EventsConstructor(evId, evName, evDate, evDateInSql, evTime, evPicture);

                    eventsArrayList.add(eventsConstructor);
                    eventListAdapter.notifyDataSetChanged();

                    //Log.d("happy", "DATABASE: " + evName + " " + evDate + " " + evDateInSql + " " + evTime + " " + evPicture);

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
           case R.id.fab:

               final AlertDialog.Builder addAlertBuilder = new AlertDialog.Builder(this);

               View alertView = LayoutInflater.from(this).inflate(R.layout.add_event_dialog, null);

               eventName = (EditText) alertView.findViewById(R.id.eventName);
               eventPicture = (EditText) alertView.findViewById(R.id.eventPicture);
               eventTime = (TextView) alertView.findViewById(R.id.eventTime);
               eventDate = (TextView) alertView.findViewById(R.id.eventDate);

               ivPictureEvent = (ImageView) alertView.findViewById(R.id.ivPictureEvent);

               btnAddDateEventCalendar = (Button) alertView.findViewById(R.id.btnAddDateEventCalendar);
               btnAddTimeEventCalendar = (Button) alertView.findViewById(R.id.btnAddTimeEventCalendar);

               btnAddDateEventCalendar.setOnClickListener(new View.OnClickListener() {

                   @Override
                   public void onClick(View view) {
                       DialogFragment datePicker = new DatePickerFragment();
                       datePicker.show(getSupportFragmentManager(), "datePicker");
                   }
               });

               eventDate.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       DialogFragment datePicker = new DatePickerFragment();
                       datePicker.show(getSupportFragmentManager(), "datePicker");
                   }
               });

               btnAddTimeEventCalendar.setOnClickListener(new View.OnClickListener() {

                   @Override
                   public void onClick(View view) {
                       DialogFragment timePicker = new TimePickerFragment();
                       timePicker.show(getSupportFragmentManager(), "timePicker");
                   }
               });

               eventTime.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       DialogFragment timePicker = new TimePickerFragment();
                       timePicker.show(getSupportFragmentManager(), "timePicker");
                   }
               });

               addAlertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                       contentValues = new ContentValues();
                       sqLiteDatabase = dbHelper.getWritableDatabase();

                       eName = eventName.getText().toString();
                       ePicture = "3";

                       dbHelper.insertNewEvent(

                               eName,
                               eDate,
                               eDateInSql,
                               eTime,
                               ePicture
                       );

//                       eventsConstructor = new EventsConstructor(eName, eDate, eDateInSql, eTime, ePicture);
//
//                       eventsArrayList.add(eventsConstructor);
//                       eventListAdapter.notifyDataSetChanged();

                       loadEventsList();

                       sqLiteDatabase.close();

                       dialogInterface.cancel();
                   }
               });

               addAlertBuilder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.cancel();
                   }
               });

               addAlertBuilder.setView(alertView);
               addAlertBuilder.show();
               break;

       }

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadEventsList();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        SimpleDateFormat formatInSql = new SimpleDateFormat("yyyy.MM.dd");
        String strDateInSql = formatInSql.format(calendar.getTime());

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String strDate = format.format(calendar.getTime());

        eventDate.setText(strDate);

        eDate = strDate;
        eDateInSql = strDateInSql;

    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        eTime = formatTime.format(calendar.getTime());


        eventTime.setText(eTime);

    }

    public void click(View view) {

        startActivity(new Intent(this, DetailActivity.class));
    }
}
