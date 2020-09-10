package com.example.plauction.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.plauction.Constants.Constants;

import java.util.ArrayList;

public class TeamListSpinnerAdapter extends ArrayAdapter<String> {

    Context mContext;
    ArrayList<String> teams;
    Spinner spinner;
    public TeamListSpinnerAdapter(Context context, int resourceId, ArrayList<String> teams, Spinner spinner){
        super(context, resourceId, teams);
        this.mContext = context;
        this.teams = teams;
        this.spinner = spinner;
    }
    @Override
    public boolean isEnabled(int position){
        if(position == 0)
        {
            // Disabling the SELECT TEAM item from Spinner
            return false;
        }
        else
        {
            return true;
        }
    }
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        tv.setTypeface(Constants.monte);
        tv.setGravity(Gravity.CENTER);
        if(position == 0){
            // Set the hint text color gray
            tv.setTextColor(Color.GRAY);
        }
        else {
            tv.setTextColor(Color.BLACK);
        }
        return view;
    }
    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView text = (TextView) view;
        text.setGravity(Gravity.CENTER);
        text.setTypeface(Constants.monte);
        return view;
    }
}
