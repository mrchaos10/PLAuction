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
import com.example.plauction.Entities.BootstrapEntity;

import com.example.plauction.Entities.ElementHistoryEntity;
import com.example.plauction.Entities.ElementSummariesEntity;
import com.example.plauction.Entities.HistoryEntity;
import com.example.plauction.Entities.PlayerInfoEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.plauction.Constants.Constants.*;
import static com.example.plauction.Constants.Constants.API_KEY;
import static com.example.plauction.Constants.Constants.AUCTION_TEAMS_URI;


public class RESTClientImplementation {
    static RequestQueue requestQueue;

    private static boolean isNetworkError(VolleyError err) {
        return err instanceof TimeoutError || err instanceof NetworkError;
    }
    //REST CALL FOR BOOTSTRAP STATIC
    public static void getBootstrapStatic(final BootstrapEntity.OnListLoad onListLoad, Context context){
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        String url = BASE_BOOTSTRAP_URL;

        JsonBaseRequest jsonObjectRequest = new JsonBaseRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                BootstrapEntity bootstrapEntity = null;
                bootstrapEntity=gson.fromJson(response.toString(), BootstrapEntity.class);
                onListLoad.onListLoaded(200,bootstrapEntity,new VolleyError());

            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
                if (isNetworkError(error)) {
                    onListLoad.onListLoaded(700, null, new VolleyError());
                }
            }

        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(1000,1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    };

    //REST CALL FOR ELEMENT SUMMARY
    public static void getElementSummary(final ElementHistoryEntity.OnListLoad onListLoad, Context context, Integer playerID){
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        String url = BASE_PLAYER_INFO_URL+playerID+'/';

        JsonBaseRequest jsonObjectRequest = new JsonBaseRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                ElementHistoryEntity elementHistoryEntity = null;
                elementHistoryEntity =gson.fromJson(response.toString(), ElementHistoryEntity.class);
                onListLoad.onListLoaded(200, elementHistoryEntity,new VolleyError());
            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
                if (isNetworkError(error)) {
                    onListLoad.onListLoaded(700, null, new VolleyError());
                }
            }

        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(1000,1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    };

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


    public static String getQueryParams(AuctionTeamsEntity[] auctionTeamsEntities)
    {
        StringBuilder params= new StringBuilder();

        for(AuctionTeamsEntity a: auctionTeamsEntities)
        {
            for(PlayerInfoEntity p: a.getPlayerInfo())
            {
                params.append("pid=").append(p.getPlayerId()).append("&");
            }
        }

        return params.toString();
    }

    public static void getElementSummaries(final ElementSummariesEntity.RestClientInterface onResponseLoad, Context context, AuctionTeamsEntity[] auctionTeamsEntities) {
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        String url = ELEMENT_SUMMARIES_URI;
        String queryParams=getQueryParams(auctionTeamsEntities);
        url=url+"?"+queryParams;
        JsonBaseRequest jsonBaseRequest = new JsonBaseRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                response.remove("code");
                Type listType = new TypeToken<Map<String, List<HistoryEntity>>>() {}.getType();
                Map<String, List<HistoryEntity>> m = gson.fromJson(response.toString(), listType);
                onResponseLoad.onResponseLoaded(200,null, m);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(isNetworkError(error)){
                    onResponseLoad.onResponseLoaded(700,new VolleyError(), null);
                }
                else {
                    onResponseLoad.onResponseLoaded(error.networkResponse.statusCode,new VolleyError(), null);
                }
            }
        });

        requestQueue.add(jsonBaseRequest);
    }

}
