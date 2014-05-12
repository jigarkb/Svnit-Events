package com.bhatt.ramani.svnitevents;

import com.bhatt.ramani.svnitevents.models.People;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PeopleJSONParser {
    public static List<People> getPeoples(String data) {
        List<People> peoples = new ArrayList<People>();

        try {
        JSONObject jsonObject = new JSONObject(data);
        JSONArray peoplesArray = jsonObject.getJSONArray("peoples");

            for (int i = 0;i < peoplesArray.length(); i++) {
                People people = new People();
                JSONObject peopleJSON = peoplesArray.getJSONObject(i);
                people.setPeopleBio(peopleJSON.getString("people_bio"));
                people.setBio(peopleJSON.getString("people_bio"));
                people.setPhone(peopleJSON.getString("contact_no"));
                people.setName(peopleJSON.getString("name"));
                people.setRole(peopleJSON.getString("role"));
                people.setTwitterHandle(peopleJSON.getString("twitter"));

                peoples.add(people);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return peoples;
    }

}
