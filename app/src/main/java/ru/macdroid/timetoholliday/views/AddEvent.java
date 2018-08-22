package ru.macdroid.timetoholliday.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.macdroid.timetoholliday.R;
import ru.macdroid.timetoholliday.model.DBHelper;
import ru.macdroid.timetoholliday.model.EventsConstructor;

public class AddEvent extends AppCompatActivity implements View.OnClickListener {

    EditText eventName, eventYear, eventMonth, eventDay, eventHour, eventMinute, eventPicture;
    Button btnAddEvent;
    String eName, eYear, eMonth, eDay, eHour, eMinute, ePicture;
    EventsConstructor eventsConstructor;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        initUI();
        initLinksObjects();

    }

    private void initLinksObjects() {
        dbHelper = new DBHelper(this);
    }

    private void initUI() {

        eventName = (EditText) findViewById(R.id.eventName);
        eventYear = (EditText) findViewById(R.id.eventYear);
        eventMonth = (EditText) findViewById(R.id.eventMonth);
        eventDay = (EditText) findViewById(R.id.eventDay);
        eventHour = (EditText) findViewById(R.id.eventHour);
        eventMinute = (EditText) findViewById(R.id.eventMinute);
        eventPicture = (EditText) findViewById(R.id.eventPicture);

        btnAddEvent = (Button) findViewById(R.id.btnAddEvent);
        btnAddEvent.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        eName = eventName.getText().toString();
        eYear = eventYear.getText().toString();
        eMonth = eventMonth.getText().toString();
        eDay = eventDay.getText().toString();
        eHour = eventHour.getText().toString();
        eMinute = eventMinute.getText().toString();
        ePicture = eventPicture.getText().toString();

        switch (view.getId()) {

            case R.id.btnAddEvent:

               // eventsConstructor = new EventsConstructor(eName, eYear, eMonth, eDay, eHour, eMinute, ePicture);

//                dbHelper.insertNewEvent(
//
//                        eventsConstructor.geteName(),
//                        eventsConstructor.geteYear(),
//                        eventsConstructor.geteMonth(),
//                        eventsConstructor.geteDay(),
//                        eventsConstructor.geteHour(),
//                        eventsConstructor.geteMinute(),
//                        eventsConstructor.getePicture()
//                );

                startActivity(new Intent(this, HollidayList.class));

                break;

        }

    }

}
