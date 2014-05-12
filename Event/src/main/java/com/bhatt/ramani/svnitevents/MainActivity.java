package com.bhatt.ramani.svnitevents;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import com.purplebrain.adbuddiz.sdk.AdBuddiz;
import com.scringo.Scringo;

public class MainActivity extends ActionBarActivity {

    private EventLruCache mMemoryCache;
    private DiskLruCache mDiskLruCache;
    private final Object mDiskCacheLock = new Object();
    private boolean mDiskCacheStarting = true;
    private static final int DISK_CACHE_SIZE = 1024 * 1024;
    private static final int APP_VERSION = 1;
    private static final int VALUE_COUNT = 1;
    public Scringo scringo;
    private static final boolean withSidebar = true;
    int check=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.bhatt.ramani.svnitevents.R.layout.activity_main);
        setTitle(com.bhatt.ramani.svnitevents.R.string.app_name);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager viewPager = (ViewPager) findViewById(com.bhatt.ramani.svnitevents.R.id.pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new EventLruCache(cacheSize);
       // new InitDiskCacheTask().execute(getCacheDir());
        Scringo.setAppId("BhzNXeIWqxgczoMquwXYTKlBf055FC13"); // Create an app at https://dev.scringo.com
        Scringo.setTwitterCredentials(getString(R.string.consumer_key), getString(R.string.consumer_secret));
        Scringo.setGoogleAppPublicKey(getString(R.string.publickey));

        scringo = new Scringo(this);
        scringo.init();
        scringo.addSidebar();
        scringo.disableSwipeToOpenSidebar();

        AdBuddiz.setPublisherKey("9074c2c6-97b5-4de9-b4ec-a7359bcc88f8");
        AdBuddiz.cacheAds(this);

        AdBuddiz.showAd(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PeopleListFragment peopleListFragment =
                PeopleListFragment.findOrCreatePeopleListFragment(getSupportFragmentManager());
        peopleListFragment.mMemoryCache = mMemoryCache;

        TwitterStreamFragment twitterStreamFragment = TwitterStreamFragment.findOrCreateTwitterStreamFragment(getSupportFragmentManager());
        twitterStreamFragment.mMemoryCache = mMemoryCache;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.bhatt.ramani.svnitevents.R.menu.main, menu);

        MenuItem item = menu.findItem(com.bhatt.ramani.svnitevents.R.id.menu_item_share);
        Intent intent = Utils.findTwitterClient(getPackageManager());
        if (intent != null) {
            intent.putExtra(Intent.EXTRA_TEXT, "#svnitevents ");
            item.setIntent(intent);
        } else {
            item.setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    PeopleListFragment peopleListFragment =
                            PeopleListFragment.findOrCreatePeopleListFragment(getSupportFragmentManager());
                    peopleListFragment.mMemoryCache = mMemoryCache;
                    return peopleListFragment;
                case 1:

                    return new ScheduleListFragment();
                case 2:
                    TwitterStreamFragment twitterStreamFragment =
                            TwitterStreamFragment.findOrCreateTwitterStreamFragment(getSupportFragmentManager());
                    twitterStreamFragment.mMemoryCache = mMemoryCache;
                    return twitterStreamFragment;
                default:
                    // this should never happen
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(com.bhatt.ramani.svnitevents.R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(com.bhatt.ramani.svnitevents.R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(com.bhatt.ramani.svnitevents.R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    class InitDiskCacheTask extends AsyncTask<File, Void, Void> {
        @Override
        protected Void doInBackground(File... params) {
            synchronized (mDiskCacheLock) {
                File cacheDir = params[0];
                try {
                    mDiskLruCache = DiskLruCache.open(cacheDir, APP_VERSION, VALUE_COUNT, DISK_CACHE_SIZE);
                    mDiskCacheStarting = false;
                    mDiskCacheLock.notifyAll();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        scringo.onStart();
        //scringo.disableSwipeToOpenSidebar();
    }

    @Override
    protected void onStop() {
        super.onStop();
        scringo.onStop();
    }

    @Override
    public void onBackPressed() {
        if (!scringo.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.scringobutton:
                if (check == 0) {
                    scringo.openSidebar();
                    check = 1;
                } else if (check == 1) {
                    scringo.closeSidebar();
                    check = 0;
                }
                return true;
            case R.id.about_us:
                Class ourClass;
                try {
                    ourClass = Class.forName("com.bhatt.ramani.svnitevents.AboutUs");
                    Intent ourIntent = new Intent(MainActivity.this, ourClass);
                    startActivity(ourIntent);
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
            case R.id.form:
                Class ourClass1;
                try {
                    ourClass1 = Class.forName("com.bhatt.ramani.svnitevents.Form");
                    Intent ourIntent = new Intent(MainActivity.this, ourClass1);
                    startActivity(ourIntent);
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
