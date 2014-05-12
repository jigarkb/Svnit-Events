package com.bhatt.ramani.svnitevents;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.bhatt.ramani.svnitevents.models.People;

import java.util.List;

public class PeopleListLoader extends AsyncTaskLoader<List<People>> {
    public PeopleListLoader(Context context) {
        super(context);
    }

    @Override
    public List<People> loadInBackground() {
        EventHttpClient httpClient = new EventHttpClient(EventHttpClient.UrlType.PEOPLE);
        String data = httpClient.getConferenceData();
        if (data != null && !"".equals(data)) {
            return PeopleJSONParser.getPeoples(data);
        }
        return null;
    }
}
