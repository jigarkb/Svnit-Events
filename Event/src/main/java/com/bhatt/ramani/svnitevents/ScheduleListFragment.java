package com.bhatt.ramani.svnitevents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.bhatt.ramani.svnitevents.models.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ScheduleListFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<List<Session>> {

    private ScheduleListAdapter mAdapter;
    private SimpleSectionedListAdapter mSectionedAdapter;
    private boolean mScrollToNow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mAdapter = new ScheduleListAdapter(getActivity());
        mSectionedAdapter = new SimpleSectionedListAdapter(getActivity(), com.bhatt.ramani.svnitevents.R.layout.list_item_schedule_header, mAdapter);
        setListAdapter(mSectionedAdapter);

        if (savedInstanceState == null) {
            mScrollToNow = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mAdapter.isEmpty()) {
            getLoaderManager().initLoader(0, null, this).forceLoad();
        } else {
            setListShown(true);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText("Schedule could not be loaded");
        setListShown(false);
    }

    @Override
    public Loader<List<Session>> onCreateLoader(int i, Bundle bundle) {
        return new ScheduleListLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Session>> listLoader, List<Session> sessions) {
        List<SimpleSectionedListAdapter.Section> sections =
                new ArrayList<SimpleSectionedListAdapter.Section>();

        int firstNowPosition = ListView.INVALID_POSITION;
        long currentTime = System.currentTimeMillis();
        long previousBlockStart = -1;

        for (int i = 0; i < sessions.size(); i++) {
            Session current = sessions.get(i);
            if(!Utils.isSameDayDisplay(previousBlockStart, current.getStartTime())) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM d");
                sections.add(new SimpleSectionedListAdapter.Section(i, simpleDateFormat.format(current.getStartTime())));
            }
            if (mScrollToNow && firstNowPosition == ListView.INVALID_POSITION
                    && ((current.getStartTime().getTime() > currentTime && currentTime < current.getEndTime().getTime()))) {
                firstNowPosition = i;
            }
            previousBlockStart = current.getStartTime().getTime();
            mAdapter.add(current);
        }

        SimpleSectionedListAdapter.Section[] dummy = new SimpleSectionedListAdapter.Section[sections.size()];
        mSectionedAdapter.setSections(sections.toArray(dummy));

        if (mScrollToNow && firstNowPosition != ListView.INVALID_POSITION) {
            firstNowPosition = mSectionedAdapter.positionToSectionedPosition(firstNowPosition);
            getListView().setSelectionFromTop(firstNowPosition, getResources().getDimensionPixelOffset(com.bhatt.ramani.svnitevents.R.dimen.list_scroll_top_offset));
            mScrollToNow = false;
        }

        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Session session = (Session) mSectionedAdapter.getItem(position);
        Intent i = new Intent(getActivity(), SessionDetailActivity.class);
        i.putExtra("session", session);
        startActivity(i);
    }

    @Override
    public void onLoaderReset(Loader<List<Session>> listLoader) {
    }


}
