package com.bhatt.ramani.svnitevents;

import com.bhatt.ramani.svnitevents.models.People;
import com.bhatt.ramani.svnitevents.models.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleJSONParser {
    public static List<Session> getSchedule(String data) {
        List<Session> sessions = new ArrayList<Session>();

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray dayArray = jsonObject.getJSONArray("sessions");

            for (int i = 0; i < dayArray.length(); i++) {
                JSONArray sessionsArray = dayArray.getJSONObject(i).getJSONArray("sessions");
                for (int j = 0; j < sessionsArray.length(); j++) {
                    Session session = new Session();
                    JSONObject sessionJSON = sessionsArray.getJSONObject(j);
                    session.setTitle(sessionJSON.getString("title"));
                    String space = sessionJSON.getString("space");
                    space += ", NIT-Surat";

                    session.setSpace(space);
                    session.setEventSummary(sessionJSON.getString("abstract"));
                    session.setRegUrl(sessionJSON.getString("web_url"));
                    String startTimeEpoch = sessionJSON.getString("start_time_epoch");
                    long startLongEpoch = Long.parseLong(startTimeEpoch);
                    Date start = new Date(startLongEpoch * 1000);
                    session.setStartTime(start);

                    String endTimeEpoch = sessionJSON.getString("end_time_epoch");
                    long endLongEpoch = Long.parseLong(endTimeEpoch);
                    Date end = new Date(endLongEpoch * 1000);
                    session.setEndTime(end);

                    JSONArray peopleJSONArray = sessionJSON.getJSONArray("peoples");
                    List<People> peoples = PeopleJSONParser.getPeoples("{ \"peoples\":" + peopleJSONArray.toString() + "}");
                    session.setPeoples(peoples);
                    sessions.add(session);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sessions;
    }
}
