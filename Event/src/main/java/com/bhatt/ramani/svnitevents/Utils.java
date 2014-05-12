package com.bhatt.ramani.svnitevents;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils {

    public static int getImageResource(String name) {
        if (name.equals("Prof. A.J Shah")) {
            return R.drawable.ajshah;
        } else if (name.equals("Vijay Garg")) {
            return R.drawable.vijaygarg;
        } else if (name.equals("Kalyani Shelat")) {
            return R.drawable.kalyanishelat;
        } else if (name.equals("Rahul Gaur")) {
            return R.drawable.rahulgaur;
        } else if (name.equals("Anshul Katyal")) {
            return R.drawable.anshulkatyal;
        } else if (name.equals("Riddhima Gohil")) {
            return R.drawable.riddhimagohil;
        } else if (name.equals("Prashant Agarwal")) {
            return R.drawable.prashantagarwal;
        } else if (name.equals("Tushar Tarang")) {
            return R.drawable.placeholder;
        } else if (name.equals("Ankit Yadav")) {
            return R.drawable.ankityadav;
        } else if (name.equals("Shubhangi Agarwal")) {
            return R.drawable.shubhangiagarwal;
        } else if (name.equals("Shridhar Jariwala")) {
            return R.drawable.shridharagarwal;
        } else if (name.equals("Shatabdi Das")) {
            return R.drawable.shatabdidas;
        } else if (name.equals("Bhumi Mangukiya")) {
            return R.drawable.bhumimangukiya;
        } else if (name.equals("Sushrut Sardesai")) {
            return R.drawable.sushrutsardesai;
        } else if (name.equals("Tanya Bafna")) {
            return R.drawable.tanyabafna;
        } else if (name.equals("Harshita Gupta")) {
            return R.drawable.harshitagupta;
        }
        /*Ignis*/
        else if (name.equals("Brijesh Umrigar")) {
            return R.drawable.brijeshkumar;
        } else if (name.equals("Tanmay Patil")) {
            return R.drawable.tanmaypatil;
        } else if (name.equals("Yogendra Pratap")) {
            return R.drawable.yogendrapratapsingh;
        } else if (name.equals("Ruchika Singh")) {
            return R.drawable.ruchikasingh;
        } else if (name.equals("U D Premnadh")) {
            return R.drawable.placeholder;
        } else if (name.equals("Naitik Gandhi")) {
            return R.drawable.naitikgandhi;
        } else if (name.equals("Manzil Patel")) {
            return R.drawable.manzilpatel;
        }

        else {
            return R.drawable.placeholder;
        }
        //return 0;
    }

    public static Intent findTwitterClient(final PackageManager packageManager) {
        final String[] twitterApps = {
                "com.twitter.android",
                "com.twidroid",
                "com.handmark.tweetcaster",
                "com.thedeck.android" };
        Intent tweetIntent = new Intent();
        tweetIntent.setType("text/plain");
        tweetIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        List<ResolveInfo> list = packageManager.queryIntentActivities(
                tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);

        for (String app : twitterApps) {
            for (ResolveInfo resolveInfo : list) {
                String p = resolveInfo.activityInfo.packageName;
                if (p != null && p.startsWith(app)) {
                    tweetIntent.setPackage(p);
                    return tweetIntent;
                }
            }
        }
        return null;
    }

    public static boolean isSameDayDisplay(long time1, Date time2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTimeInMillis(time1);
        cal2.setTimeInMillis(time2.getTime());
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}
