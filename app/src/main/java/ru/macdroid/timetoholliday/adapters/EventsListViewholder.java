package ru.macdroid.timetoholliday.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.macdroid.timetoholliday.R;

public class EventsListViewholder extends RecyclerView.ViewHolder{

    TextView eventIdAdapter, eventNameAdapter, eventDateAdapter, eventTimeAdapter, eventDateInSqlAdapter;
    ImageView eventPictureAdapter;
    LinearLayout lMain;

    public EventsListViewholder(@NonNull View itemView) {
        super(itemView);

        eventIdAdapter = (TextView) itemView.findViewById(R.id.eventIdAdapter);
        eventNameAdapter = (TextView) itemView.findViewById(R.id.eventNameAdapter);
        eventDateAdapter = (TextView) itemView.findViewById(R.id.eventDateAdapter);
        eventDateInSqlAdapter = (TextView) itemView.findViewById(R.id.eventDateInSqlAdapter);
        eventTimeAdapter = (TextView) itemView.findViewById(R.id.eventTimeAdapter);
        eventPictureAdapter = (ImageView) itemView.findViewById(R.id.eventPictureAdapter);
        lMain = itemView.findViewById(R.id.lMain);


    }






}
