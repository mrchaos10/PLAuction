package com.example.plauction.Common;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.plauction.Constants.Constants;
import com.example.plauction.Entities.Elements;
import com.example.plauction.Entities.Playerinfo;
import com.example.plauction.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Map;


public class CommonFunctions {

    private  static Map<Integer, Elements> playerIdToElementMap_;

    // General toasts ..............

    public static void makeToast(String message, Activity activity) {
        if(activity!=null){
            LayoutInflater inflater = LayoutInflater.from(activity.getApplicationContext());
            View toastLayout = inflater.inflate(R.layout.custom_toast, null);
            TextView toastMessage = (TextView) toastLayout.findViewById(R.id.toastMessage);
            toastMessage.setText(message);
            Toast toast = new Toast(activity.getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(toastLayout);

            toast.setGravity(Gravity.BOTTOM,0,0);
            toast.show();
        }
    }


    public static void makeToast(String message, Context context) {
        if(context !=null){
            LayoutInflater inflater = LayoutInflater.from(context);
            View toastLayout = inflater.inflate(R.layout.custom_toast, null);
            TextView toastMessage = (TextView) toastLayout.findViewById(R.id.toastMessage);
            toastMessage.setText(message);
            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(toastLayout);

            toast.setGravity(Gravity.TOP,0,0);
            toast.show();
        }
    }

    // Snack bar - message only

    public static  Snackbar makeSnackBar(String message, View view) {
        int duration = 1000;
        Snackbar snackbar = Snackbar.make(view, message, duration);
        View v = snackbar.getView();
        TextView tv =v.findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTypeface(Constants.monte);
        return snackbar;
    }

    public static int isEmpty(EditText e){
        String text = e.getText().toString();
        if(text.length()==0){
            return 1;
        }
        else
            return 0;
    }


    public static String getErrorMessage(int code, Context context) {
        String message;
        if(code == 401) {
            message = context.getString(R.string.unauthorized);
        }
        else if (code == 400) {
            message = context.getString(R.string.invalidData);
        }
        else if (code == 500 ) {
            message = context.getString(R.string.unexpectedError);
        }
        else if(code == 700) {
            message = context.getString(R.string.noConnection);
        }
        else
        {
            message = context.getString(R.string.unexpectedError);
        }

        return message;
    }

    public static Map<Integer, Elements> getPlayerIdToElementMap_() {
        return playerIdToElementMap_;
    }

    public static void setPlayerIdToElementMap_(Map<Integer, Elements> playerIdToElementMap_) {
        CommonFunctions.playerIdToElementMap_ = playerIdToElementMap_;
    }


    public static int getTeamTotalSum(ArrayList<Playerinfo> playerinfoArrayList)
    {
        if(playerIdToElementMap_ == null || playerIdToElementMap_.size() == 0)
            return 0;

        int sum =0;
        for(Playerinfo player: playerinfoArrayList)
        {
            if(!playerIdToElementMap_.containsKey(player.getPlayerId()))
            {
                sum+=0;
            }
            else
            {
                sum+=playerIdToElementMap_.get(player.getPlayerId()).getTotal_points();
            }
        }
        return sum;
    }

}
