package com.bhatt.ramani.svnitevents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bhatt.ramani.svnitevents.models.Session;

import java.text.SimpleDateFormat;
import java.util.List;

public class ScheduleListAdapter extends ArrayAdapter<Session>{

    private LayoutInflater mLayoutInflater;

    public ScheduleListAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_2);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = mLayoutInflater.inflate(com.bhatt.ramani.svnitevents.R.layout.schedule_item, parent, false);
        } else {
            view = convertView;
        }

        Session session = getItem(position);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa");
        String startTime = simpleDateFormat.format(session.getStartTime());
        String endTime = simpleDateFormat.format(session.getEndTime());

        ((TextView) view.findViewById(com.bhatt.ramani.svnitevents.R.id.block_title)).setText(session.getTitle());
        ((TextView) view.findViewById(com.bhatt.ramani.svnitevents.R.id.block_time)).setText(startTime);
        ((TextView) view.findViewById(com.bhatt.ramani.svnitevents.R.id.block_endtime)).setText(endTime);

        //List<People> = session.getPeoples();
        TextView eventPeople = (TextView) view.findViewById(com.bhatt.ramani.svnitevents.R.id.block_subtitle);
        eventPeople.setText(session.getSpace());

        return view;
    }

    public void setData(List<Session> data) {
        if (data != null) {
            clear();
            for (Session session : data) {
                add(session);
            }
        }
    }
}
