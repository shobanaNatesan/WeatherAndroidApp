package com.example.shobanan.wear4weather.datamanager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 *
 * This class uses AsyncTask to load the image asynchronously for the given URL.
 * The interface function will load the bit map on the activity.
 */
public  class loadImageTask extends AsyncTask<String, Void, Bitmap> {

    public loadImageTask(Listener listener) {
        mListener = listener;
    }

    public interface Listener{
        void onImageLoaded(Bitmap bitmap);
        void onError();
    }

    private Listener mListener;

    @Override
    protected Bitmap doInBackground(String... args) {

        try {
            return BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        if (bitmap != null) {
            mListener.onImageLoaded(bitmap);
        } else {
            mListener.onError();
        }
    }
}

