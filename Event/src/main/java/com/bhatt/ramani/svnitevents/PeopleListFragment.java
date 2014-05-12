package com.bhatt.ramani.svnitevents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.bhatt.ramani.svnitevents.models.People;

import java.util.List;

public class PeopleListFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<List<People>> {

    private static final String TAG = "PeopleListFragment";

    private PeopleListAdapter mAdapter;
    //private SearchView mSearchView;
    //private String mCurFilter;
    public EventLruCache mMemoryCache;

    public static PeopleListFragment findOrCreatePeopleListFragment(FragmentManager fm) {
        PeopleListFragment peopleListFragment = (PeopleListFragment) fm.findFragmentByTag(TAG);
        if (peopleListFragment == null) {
            peopleListFragment = new PeopleListFragment();
        }
        return peopleListFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText("No People");
        setListShown(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        mAdapter = new PeopleListAdapter(getActivity(), mMemoryCache);
        setListAdapter(mAdapter);
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
        People people = mAdapter.getItem(position);
        Intent i = new Intent(getActivity(), PeopleDetailActivity.class);
        i.putExtra("people", people);
        startActivity(i);
    }

    @Override
    public Loader<List<People>> onCreateLoader(int i, Bundle bundle) {
        return new PeopleListLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<People>> listLoader, List<People> peoples) {
        mAdapter.setData(peoples);

        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<People>> listLoader) {
    }
}
