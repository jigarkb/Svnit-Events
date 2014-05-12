package com.bhatt.ramani.svnitevents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

public class EventHttpClient {
    private UrlType urlType;

    public EventHttpClient(UrlType urlType) {
        this.urlType = urlType;
    }

    private static final String PEOPLE_URL = "http://svnitevents.pancakeapps.com/people.json";
    private static final String SCHEDULE_URL = "http://svnitevents.pancakeapps.com/schedule.json";

    public enum UrlType {
        PEOPLE,
        SCHEDULE
    }

    public String getConferenceData(){
        InputStream inputStream = null;
        try {
            switch (urlType) {
                case PEOPLE:
                        inputStream = new URL(PEOPLE_URL).openStream();
                        break;

                case SCHEDULE:
                    inputStream = new URL(SCHEDULE_URL).openStream();
                    break;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            StringBuilder stringBuilder = new StringBuilder();
            int cp;
            while ((cp = reader.read()) != -1) {
                stringBuilder.append((char) cp);
            }
            inputStream.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
