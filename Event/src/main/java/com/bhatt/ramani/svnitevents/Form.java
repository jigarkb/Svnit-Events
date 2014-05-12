package com.bhatt.ramani.svnitevents;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Jigar on 02-04-2014.
 */
public class Form extends Activity {
    WebView myWebView;
    String url = "https://docs.google.com/forms/d/126OyTJNOqFBVu7851jUoMpI0xKYPiHXkBT99dGsuvFc/viewform";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.form);


        myWebView = (WebView) findViewById(R.id.webView1);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl(url);
    }
}