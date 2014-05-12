package com.bhatt.ramani.svnitevents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterStreamFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Status>> {

    private static final String TAG = "TwitterStreamFragment";

    private Twitter mTwitter;
    private TwitterStreamAdapter mAdapter;
    public EventLruCache mMemoryCache;

    public static TwitterStreamFragment findOrCreateTwitterStreamFragment(FragmentManager fm) {
        TwitterStreamFragment twitterStreamFragment  = (TwitterStreamFragment) fm.findFragmentByTag(TAG);
        if (twitterStreamFragment == null) {
            twitterStreamFragment = new TwitterStreamFragment();
        }
        return twitterStreamFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        mAdapter = new TwitterStreamAdapter(getActivity(), mMemoryCache);
        setListAdapter(mAdapter);

        String consumerKey = getString(com.bhatt.ramani.svnitevents.R.string.consumer_key);
        String consumerSecret = getString(com.bhatt.ramani.svnitevents.R.string.consumer_secret);
        String accessToken= getString(com.bhatt.ramani.svnitevents.R.string.access_token);
        String accessTokenSecret = getString(com.bhatt.ramani.svnitevents.R.string.access_token_secret);

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(consumerKey);
        configurationBuilder.setOAuthConsumerSecret(consumerSecret);
        configurationBuilder.setOAuthAccessToken(accessToken);
        configurationBuilder.setOAuthAccessTokenSecret(accessTokenSecret);
        mTwitter = new TwitterFactory(configurationBuilder.build()).getInstance();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText("No tweets");
        setListShown(false);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mAdapter.isEmpty()) {
            getLoaderManager().initLoader(0, null, this).forceLoad();
        } else {
            setListShown(true);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Status status = mAdapter.getItem(position);
        String screenName = status.getUser().getScreenName();
        Intent intent = Utils.findTwitterClient(getActivity().getPackageManager());
        if (intent != null) {
            intent.putExtra(Intent.EXTRA_TEXT, "@" + screenName + " #svnitevents ");
            startActivity(intent);
        }
    }

    @Override
    public Loader<List<Status>> onCreateLoader(int i, Bundle bundle) {
        return new TwitterStreamLoader(getActivity(), mTwitter);
    }

    @Override
    public void onLoadFinished(Loader<List<Status>> listLoader, List<Status> statuses) {
        mAdapter.setData(statuses);

        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Status>> listLoader) {
    }
}
