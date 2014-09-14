package io.bxbxbai.feedlistviewdemo.utils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import io.bxbxbai.feedlistviewdemo.AppController;

/**
 * Created by baia on 14-9-13.
 */
public class RequestManager {
    public static RequestQueue mRequestQueue = Volley.newRequestQueue(AppController.getInstance());

    private RequestManager() {
        // no instances
    }

    public static RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        mRequestQueue.add(request);
    }

    public static void cancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);
    }
}

