package com.bhatt.ramani.svnitevents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhatt.ramani.svnitevents.models.People;

public class PeopleDetailActivity extends ActionBarActivity {

    private People mPeople;
    ImageButton letscall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.bhatt.ramani.svnitevents.R.layout.people_detail_view);
        letscall = (ImageButton) findViewById(R.id.ibCall);
        if (getIntent().getExtras() == null) {
            return;
        }

        mPeople = (People) getIntent().getExtras().getSerializable("people");

        if (mPeople == null) {
            return;
        }

        TextView name = (TextView) findViewById(com.bhatt.ramani.svnitevents.R.id.people_name);
        TextView role = (TextView) findViewById(com.bhatt.ramani.svnitevents.R.id.people_role);
        TextView textBio = (TextView) findViewById(com.bhatt.ramani.svnitevents.R.id.people_abstract);
        ImageView headshot = (ImageView) findViewById(com.bhatt.ramani.svnitevents.R.id.people_headshot);

        name.setText(mPeople.getName());
        role.setText(mPeople.getRole());
        textBio.setText(Html.fromHtml(mPeople.getBio()));
        headshot.setImageResource(Utils.getImageResource(mPeople.getName()));
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
            String peopleTwitter = mPeople.getTwitterHandle();
            if (peopleTwitter != null) {
                extraText = peopleTwitter + " " + extraText;
            }
            intent.putExtra(Intent.EXTRA_TEXT, extraText);
            item.setIntent(intent);
        } else {
            item.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }
    public void callme(View v) {
        String posted_by = mPeople.getPhone();

        String uri = "tel:" + posted_by.trim() ;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
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
                    Intent ourIntent = new Intent(PeopleDetailActivity.this, ourClass);
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
                    Intent ourIntent = new Intent(PeopleDetailActivity.this, ourClass1);
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

