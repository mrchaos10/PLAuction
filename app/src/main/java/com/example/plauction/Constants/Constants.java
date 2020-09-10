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
