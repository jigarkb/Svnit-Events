package com.bhatt.ramani.svnitevents;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import twitter4j.Status;

public class TwitterStreamAdapter extends ArrayAdapter<Status> {

    private LayoutInflater mLayoutInflater;
    private EventLruCache mMemoryCache;


    public TwitterStreamAdapter(Context context, EventLruCache memoryCache) {
        super(context, android.R.layout.simple_list_item_2);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMemoryCache = memoryCache;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = mLayoutInflater.inflate(com.bhatt.ramani.svnitevents.R.layout.tweet_item_view, parent, false);
        } else {
            view = convertView;
        }

        Status status = getItem(position);
        if (status != null) {
            ImageView tweeterView = (ImageView) view.findViewById(com.bhatt.ramani.svnitevents.R.id.tweeter_image);
            try {
                URL url = new URL(status.getUser().getOriginalProfileImageURL());
                loadBitmap(url, tweeterView);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            ((TextView) view.findViewById(com.bhatt.ramani.svnitevents.R.id.twitter_handle)).setText(status.getUser().getName());
            ((TextView) view.findViewById(com.bhatt.ramani.svnitevents.R.id.tweet_text)).setText(status.getText());
        }
        return view;
    }

    public class FetchBitmapWorkerTask extends AsyncTask<URL, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewWeakReference;
        private URL url = null;

        public FetchBitmapWorkerTask(ImageView imageView) {
            imageViewWeakReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(URL... params) {
            url = params[0];
            try {
                InputStream inputStream = (InputStream) url.getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (mMemoryCache != null) {
                    mMemoryCache.addBitmapToMemoryCache(url.toString(), bitmap);
                }
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }
            if (imageViewWeakReference != null && bitmap != null) {
                final ImageView imageView = imageViewWeakReference.get();
                final FetchBitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<FetchBitmapWorkerTask> bitmapWorkerTaskWeakReference;

        public AsyncDrawable(Resources res, FetchBitmapWorkerTask bitmapWorkerTask) {
            super(res, (Bitmap) null);
            bitmapWorkerTaskWeakReference = new WeakReference<FetchBitmapWorkerTask>(bitmapWorkerTask);
        }

        public FetchBitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskWeakReference.get();
        }
    }

    public void loadBitmap(URL url, ImageView imageView) {
        if (cancelPotentialWork(url, imageView)) {
            Bitmap bitmap = null;
            if (mMemoryCache != null) {
                bitmap = mMemoryCache.getBitmapFromMemoryCache(url.toString());
            }
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                final FetchBitmapWorkerTask task = new FetchBitmapWorkerTask(imageView);
                final AsyncDrawable asyncDrawable = new AsyncDrawable(getContext().getResources(), task);
                imageView.setImageDrawable(asyncDrawable);
                task.execute(url);
            }
        }
    }

    private boolean cancelPotentialWork(URL url, ImageView imageView) {
        final FetchBitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final URL bitmapURL = bitmapWorkerTask.url;
            if (bitmapURL != url) {
                bitmapWorkerTask.cancel(true);
            } else {
                return false;
            }
        }
        return true;
    }

    private static FetchBitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    public void setData(List<Status> statuses) {
       if (statuses != null) {
            clear();
            for (Status status : statuses) {
                add(status);
            }
        }
    }
}
