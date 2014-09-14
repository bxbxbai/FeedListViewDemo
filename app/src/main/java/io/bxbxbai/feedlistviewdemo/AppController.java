package io.bxbxbai.feedlistviewdemo;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import io.bxbxbai.feedlistviewdemo.utils.LruBitmapCache;

/**
 * Created by baia on 14-9-11.
 */
public class AppController extends Application {
    private static final String TAG = "AppController";

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    LruBitmapCache mCache;

    private static AppController instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static AppController getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this);
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(getRequestQueue(), getLruBitmapCache());
        }
        return mImageLoader;
    }

    public LruBitmapCache getLruBitmapCache() {
        if (mCache == null) {
            mCache = new LruBitmapCache();
        }
        return mCache;
    }

    public <T> void addRequest(Request<T> request, String tag) {
        request.setTag(tag);
        getRequestQueue().add(request);
    }

    public <T> void addRequest(Request<T> request) {
        addRequest(request, TAG);
    }

    public void cancelPendingRequest(Object tag) {
        getRequestQueue().cancelAll(tag);
    }

}
