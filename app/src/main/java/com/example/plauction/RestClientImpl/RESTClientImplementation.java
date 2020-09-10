package com.example.plauction.RestClientImpl;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.plauction.Entities.AuctionTeamsEntity;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import static com.example.plauction.Constants.Constants.API_KEY;
import static com.example.plauction.Constants.Constants.AUCTION_TEAMS_URI;


public class RESTClientImplementation {
    static RequestQueue requestQueue;

    private static boolean isNetworkError(VolleyError err) {
        return err instanceof TimeoutError || err instanceof NetworkError;
    }

    //REST Call for normal login
    public static void getAuctionTeams(final AuctionTeamsEntity.OnListLoad onListLoad, Context context){
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        String url = AUCTION_TEAMS_URI;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
                AuctionTeamsEntity[] auctionTeamsEntities = gson.fromJson(response.toString(), AuctionTeamsEntity[].class);
                onListLoad.onListLoaded(200,auctionTeamsEntities,new VolleyError());

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
                if (isNetworkError(error)) {
                    onListLoad.onListLoaded(700, null, new VolleyError());
                } else {
                    onListLoad.onListLoaded(error.networkResponse.statusCode, null, new VolleyError());
                }
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("x-apikey", API_KEY);
                return headers;
            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(1000,1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    };

}
