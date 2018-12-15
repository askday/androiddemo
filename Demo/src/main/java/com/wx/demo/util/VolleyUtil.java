package com.wx.demo.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class VolleyUtil {
    private static final VolleyUtil ourInstance = new VolleyUtil();

    public static VolleyUtil getInstance() {
        return ourInstance;
    }

    private static final String baseUrl = "http://192.168.1.40:3005/";
    RequestQueue mQueue;


    private VolleyUtil() {
    }


    public void init(Context context) {
        mQueue = Volley.newRequestQueue(context);
    }

    public void loadData(String path, final Listener listener) {
        String url = baseUrl + path;
        LogUtil.d("=======do list request======");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                listener.onSuccess(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                listener.onError(error);
                                LogUtil.d(error.toString());
                            }
                        });
        mQueue.add(jsonObjectRequest);
    }

    public void loadData(String path, JSONObject params, final Listener listener) {
        String url = baseUrl + path;
        LogUtil.d("=======do list request======");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, params,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                listener.onSuccess(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                listener.onError(error);
                            }
                        });
        mQueue.add(jsonObjectRequest);
    }

    public interface Listener<T> {

        void onSuccess(T reponse);

        void onError(T reponse);

    }

}
