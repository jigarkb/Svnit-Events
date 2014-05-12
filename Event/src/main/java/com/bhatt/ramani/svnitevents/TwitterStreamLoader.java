package com.bhatt.ramani.svnitevents;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TwitterStreamLoader extends AsyncTaskLoader<List<Status>> {
    Twitter mTwitter;

    public TwitterStreamLoader(Context context, Twitter twitter) {
        super(context);
        mTwitter = twitter;
    }

    @Override
    public List<Status> loadInBackground() {
        Query query = new Query("#svnitevents");
        try {
            QueryResult result = mTwitter.search(query);
            return result.getTweets();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
