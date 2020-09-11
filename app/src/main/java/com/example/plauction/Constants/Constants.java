package com.example.plauction.Constants;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;

public class Constants {
    public static boolean initialized = false;
    public static Typeface monte;
    public static final String BASE_URL ="http://35.200.193.251";
    public static final String PORT = "80";
    public static final String AUCTION_TEAMS_URI="https://itsplbabydb-9b52.restdb.io/rest/its-pl-baby-teams";
    public static final String API_KEY="5f59f3f2c5e01c1e033b8e4d";
    public static final String BASE_PLAYER_INFO_URL="https://fantasy.premierleague.com/api/element-summary/";
    public static final int CODE_SUCCESSFUL = 200;
    public static final int CODE_USER_NOT_FOUND = 401;

    //TODO:Move all of the API Status Code Constants to a different file imported here.
    public static final int CODE_INSUFFICIENT_FUNDS = 402;
    public static final int CODE_INSUFFICIENT_LIMIT = 405;
    public static final int CODE_INVALID_SESSION_DATA = 450;
    public static final int CODE_MESSAGE_NOT_SENT = 453;
    public static final int CODE_NO_INTERNET = 455;
    public static final int CODE_REQUEST_TIMEOUT = 456;
    public static void initializeConstants(Activity activity, Context context){
        if(!initialized){
            setFonts(context);
            initialized = true;
        }
    }


    public static void setFonts(Context context){
        monte = Typeface.createFromAsset(context.getAssets(),"font/monte.ttf");
    }


}
