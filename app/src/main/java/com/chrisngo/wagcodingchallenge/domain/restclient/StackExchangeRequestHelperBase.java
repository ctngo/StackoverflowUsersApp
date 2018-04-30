package com.chrisngo.wagcodingchallenge.domain.restclient;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chrisngo.wagcodingchallenge.R;
import com.chrisngo.wagcodingchallenge.util.Constants;

import java.io.UnsupportedEncodingException;

public class StackExchangeRequestHelperBase {
    private Context context;
    private RequestQueue requestQueue;
    private RequestCompletedListener requestCompletedListener;
    private static final String TAG = StackExchangeRequestHelperBase.class.getSimpleName();
    private static final String GET = Constants.GET;

    public StackExchangeRequestHelperBase(Context context, RequestCompletedListener requestCompletedListener) {
        this.context = context;
        this.requestCompletedListener = requestCompletedListener;
    }

    /**
     * Requests String response using specified URL
     * @param url API request URL
     */
    public void requestStringGet(final String url) {
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                requestCompletedListener.onRequestCompleted(GET, true, response, null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Try to get response from the cache otherwise display error message.
                final Cache cache = getRequestQueue().getCache();
                final Cache.Entry entry = cache.get(url);
                if(entry != null) {
                    try {
                        String cachedResponse = new String(entry.data, "UTF-8");
                        requestCompletedListener.onRequestCompleted(GET, true, cachedResponse, null);
                    } catch (UnsupportedEncodingException e) {
                        Log.e(TAG, "UnsupportedEncodingException");
                    }
                }
                else {
                    Log.e(TAG, "No cache for current request");
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(context,
                                context.getString(R.string.error_network_timeout),
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null) {
                            requestCompletedListener.onRequestCompleted(GET, false, null, String.valueOf(networkResponse.statusCode));
                        }
                    }
                }
            }
        });
        addToRequestQueue(request);
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    private <T> void addToRequestQueue(Request<T> req) {
        req.setRetryPolicy(new DefaultRetryPolicy());
        getRequestQueue().add(req);
    }
}
