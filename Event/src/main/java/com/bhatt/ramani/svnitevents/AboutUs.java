package com.bhatt.ramani.svnitevents;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Jigar on 26/3/14.
 */
public class AboutUs extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        TextView urlView = (TextView) findViewById(R.id.url_jig);
        urlView.setMovementMethod(LinkMovementMethod.getInstance());
        TextView urlView2 = (TextView) findViewById(R.id.url_jay);
        urlView2.setMovementMethod(LinkMovementMethod.getInstance());
    }
    public void callme2(View v) {
        String posted_by = "+917600065373";
        String uri = "tel:" + posted_by.trim() ;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }
    public void callme3(View v) {
        String posted_by = "+919913387341";
        String uri = "tel:" + posted_by.trim() ;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }
}
