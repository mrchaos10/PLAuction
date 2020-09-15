package com.example.plauction.Constants;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Constants {
    public static boolean initialized = false;
    public static Typeface monte;
    public static final String BASE_URL ="http://35.200.193.251";
    public static final String PORT = "80";
    public static final String AUCTION_TEAMS_URI="https://itsplbabydb-9b52.restdb.io/rest/its-pl-baby-teams";
    public static final String API_KEY="5f59f3f2c5e01c1e033b8e4d";
    public static final String BASE_BOOTSTRAP_URL="https://fantasy.premierleague.com/api/bootstrap-static/";
    public static final String BASE_PLAYER_INFO_URL="https://fantasy.premierleague.com/api/element-summary/";
    public static final String PLAYER_IMAGE_URL="https://resources.premierleague.com/premierleague/photos/players/110x140/p";
    public static final String ELEMENT_SUMMARIES_URI="https://pl-players-history.herokuapp.com/";
    //public static final String ELEMENT_SUMMARIES_URI="http://192.168.43.210:5000/";
    public static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
            .enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
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
