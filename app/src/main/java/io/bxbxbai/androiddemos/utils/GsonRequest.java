package io.bxbxbai.androiddemos.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by baia on 14-9-14.
 */
public class GsonRequest<T> extends Request<T> {

    private final Gson gson = new Gson();
    private Class<T> mClazz;
    private Response.Listener<T> mListener;
    private Map<String, String> mHeader;

    public GsonRequest(String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, clazz, null, listener, errorListener);
    }

    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> header,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mClazz = clazz;
        mHeader = header;
        mListener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeader != null ? mHeader : super.getHeaders();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(gson.fromJson(json, mClazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
