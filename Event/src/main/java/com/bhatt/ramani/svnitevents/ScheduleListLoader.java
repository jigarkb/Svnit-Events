package com.bhatt.ramani.svnitevents;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.bhatt.ramani.svnitevents.models.Session;

import java.util.List;

public class ScheduleListLoader extends AsyncTaskLoader<List<Session>> {
    public ScheduleListLoader(Context context) {
        super(context);
    }

    @Override
    public List<Session> loadInBackground() {
        EventHttpClient httpClient = new EventHttpClient(EventHttpClient.UrlType.SCHEDULE);
        String data = httpClient.getConferenceData();
        if (data != null && !"".equals(data)) {
            return ScheduleJSONParser.getSchedule(data);
        }
        return null;
    }
}
