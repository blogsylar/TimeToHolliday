package ru.macdroid.timetoholliday.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.macdroid.timetoholliday.R;
import ru.macdroid.timetoholliday.model.EventsConstructor;
import ru.macdroid.timetoholliday.views.DetailActivity;


public class EventListAdapter extends RecyclerView.Adapter<EventsListViewholder> {

    ArrayList<EventsConstructor> eventsArrayList;
    LayoutInflater inflater;
    EventsConstructor eventsConstructor;
    Intent intent;
    Context context;

    public EventListAdapter(Context context, ArrayList<EventsConstructor> eventsArrayList) {

        this.eventsArrayList = eventsArrayList;
        this.inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public EventsListViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.events, viewGroup, false);
        EventsListViewholder elVh = new EventsListViewholder(view);

        return elVh;
    }

    @Override
    public void onBindViewHolder(@NonNull final EventsListViewholder eventsListViewholder, int position) {

        eventsConstructor = eventsArrayList.get(position);

        eventsListViewholder.eventIdAdapter.setText(eventsConstructor.eId);
        eventsListViewholder.eventDateAdapter.setText(eventsConstructor.eDate);
        eventsListViewholder.eventDateInSqlAdapter.setText(eventsConstructor.eDateInSql);
        eventsListViewholder.eventTimeAdapter.setText(eventsConstructor.eTime);
        eventsListViewholder.eventNameAdapter.setText(eventsConstructor.eName);

        Picasso.get()
                .load(R.drawable.land)
                .resize(200, 200)
                .into(eventsListViewholder.eventPictureAdapter);


        eventsListViewholder.lMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idEventToDetail = eventsListViewholder.eventIdAdapter.getText().toString();
                String nameEventToDetail = eventsListViewholder.eventNameAdapter.getText().toString();
                String dateEventToDetail = eventsListViewholder.eventDateAdapter.getText().toString();
                String dateInSqlEventToDetail = eventsListViewholder.eventDateInSqlAdapter.getText().toString();
                String timeEventToDetail = eventsListViewholder.eventTimeAdapter.getText().toString();


                    intent = new Intent(v.getContext(), DetailActivity.class);

                    intent.putExtra("id", idEventToDetail);
                    intent.putExtra("name", nameEventToDetail);
                    intent.putExtra("date", dateEventToDetail);
                    intent.putExtra("dateInSql", dateInSqlEventToDetail);
                    intent.putExtra("time", timeEventToDetail);

                    v.getContext().startActivity(intent);

                }

        });


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return eventsArrayList.size();
    }
}
