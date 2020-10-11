package com.example.plauction.RestClientImpl;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class JsonBaseRequest extends JsonObjectRequest{
    public JsonBaseRequest(int method, String url, JSONObject jsonObject, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener){
        super(method,url,jsonObject,successListener,errorListener);
        setRetryPolicy(new DefaultRetryPolicy(30000,1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public JsonBaseRequest(int method, String url, JSONObject jsonObject, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener, int timeOut, int retries){
        super(method,url,jsonObject,successListener,errorListener);
        setRetryPolicy(new DefaultRetryPolicy(timeOut,retries, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        //return super.parseNetworkResponse(response);
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            JSONObject jsonResponse = new JSONObject(jsonString);
            String code = "{statusCode:"+response.statusCode+"}";
            jsonResponse.put("code", new JSONObject(code));
            return Response.success(jsonResponse,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JSONException e) {
            return Response.error(new ParseError(e));
        }
    }

}
