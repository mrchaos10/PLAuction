package com.example.plauction.RestClientImpl;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;

public class JsonArrayReq extends JsonArrayRequest {
    public JsonArrayReq(int method, String url, JSONArray jsonObject, Response.Listener<JSONArray> successListener, Response.ErrorListener errorListener){
        super(method,url,jsonObject,successListener,errorListener);
        setRetryPolicy(new DefaultRetryPolicy(1000,1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public JsonArrayReq(int method, String url, JSONArray jsonObject, Response.Listener<JSONArray> successListener, Response.ErrorListener errorListener, int timeOut, int retries){
        super(method,url,jsonObject,successListener,errorListener);
        setRetryPolicy(new DefaultRetryPolicy(timeOut,retries, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            JSONArray jsonResponse = new JSONArray(jsonString);
            return Response.success(jsonResponse,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JSONException e) {
            return Response.error(new ParseError(e));
        }
    }
}
