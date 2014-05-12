package com.bhatt.ramani.svnitevents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhatt.ramani.svnitevents.models.Session;
import com.bhatt.ramani.svnitevents.models.People;

import java.text.SimpleDateFormat;
import java.util.List;

public class SessionDetailActivity extends ActionBarActivity {

    private Session mSession;
    private boolean hasPeoples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.bhatt.ramani.svnitevents.R.layout.fragment_session_detail);

        if (getIntent().getExtras() == null) {
            finish();
        }

        mSession = (Session) getIntent().getExtras().getSerializable("session");

        if (mSession == null) {
            finish();
        }

        TextView title = (TextView) findViewById(com.bhatt.ramani.svnitevents.R.id.session_title);
        TextView subtitleTextView = (TextView) findViewById(com.bhatt.ramani.svnitevents.R.id.session_subtitle);
        TextView textAbstract = (TextView) findViewById(com.bhatt.ramani.svnitevents.R.id.session_abstract);
        TextView urlView = (TextView) findViewById(R.id.url_reg);
        urlView.setMovementMethod(LinkMovementMethod.getInstance());
        try {
            if (!mSession.getRegUrl().isEmpty())
                urlView.setText(Html.fromHtml("<a href=\"" + mSession.getRegUrl() + "\">" + "Register here" + "</a>"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        final ViewGroup peoplesGroup = (ViewGroup)findViewById(com.bhatt.ramani.svnitevents.R.id.session_people_block);
        final LayoutInflater layoutInflater = getLayoutInflater();

        for (People people : mSession.getPeoples()) {
            final View peopleView = layoutInflater.inflate(com.bhatt.ramani.svnitevents.R.layout.people_detail, peoplesGroup, false);
            final TextView peopleHeaderView = (TextView) peopleView.findViewById(com.bhatt.ramani.svnitevents.R.id.people_header);
            final ImageView peopleImageView = (ImageView) peopleView
                    .findViewById(com.bhatt.ramani.svnitevents.R.id.people_image);
            final TextView peopleAbstractView = (TextView) peopleView
                    .findViewById(com.bhatt.ramani.svnitevents.R.id.people_abstract);


            peopleHeaderView.setText(people.getName());
            peopleAbstractView.setText(Html.fromHtml(people.getBio()));
            peopleImageView.setImageResource(Utils.getImageResource(people.getName()));

            hasPeoples = true;
            peoplesGroup.addView(peopleView);
        }

        peoplesGroup.setVisibility(hasPeoples ? View.VISIBLE : View.GONE);

        title.setText(mSession.getTitle());
        textAbstract.setText(Html.fromHtml(mSession.getEventSummary()));
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM d");
        String date = simpleDateFormat.format(mSession.getStartTime());
        String startTime = simpleTimeFormat.format(mSession.getStartTime());
        String endTime = simpleTimeFormat.format(mSession.getEndTime());
        String subtitle = date + " " + startTime + " - " + endTime + " - " + mSession.getSpace();
        subtitleTextView.setText(subtitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.bhatt.ramani.svnitevents.R.menu.main, menu);
       //make scringo button invisible
        MenuItem scringoitem = menu.findItem(R.id.scringobutton);
        scringoitem.setVisible(false);

        //this.invalidateOptionsMenu();

        MenuItem item = menu.findItem(com.bhatt.ramani.svnitevents.R.id.menu_item_share);
        Intent intent = Utils.findTwitterClient(getPackageManager());
        if (intent != null) {
            String extraText = "#svnitevents ";
            List<People> peopleList = mSession.getPeoples();
            String peopleTwitter = "";
            for (People people : peopleList) {
                if (people.getTwitterHandle() != null) {
                    peopleTwitter += people.getTwitterHandle() + " ";
                }
            }

            if (!peopleTwitter.equals("")) {
                extraText = peopleTwitter + " " + extraText;
            }
            intent.putExtra(Intent.EXTRA_TEXT, extraText);
            item.setIntent(intent);
        } else {
            item.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }
    int check=0;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.scringobutton:
                if (check == 0) {
                    //scringo.openSidebar();
                    check = 1;
                } else if (check == 1) {
                    //scringo.closeSidebar();

                    check = 0;
                }
                return true;
            case R.id.about_us:
                Class ourClass;
                try {
                    ourClass = Class.forName("com.bhatt.ramani.svnitevents.AboutUs");
                    Intent ourIntent = new Intent(SessionDetailActivity.this, ourClass);
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
                    Intent ourIntent = new Intent(SessionDetailActivity.this, ourClass1);
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
