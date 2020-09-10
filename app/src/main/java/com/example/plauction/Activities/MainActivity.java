package com.example.plauction.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.android.volley.VolleyError;
import com.example.plauction.Adapters.TeamListSpinnerAdapter;
import com.example.plauction.Common.CommonFunctions;
import com.example.plauction.Entities.AuctionTeamsEntity;
import com.example.plauction.R;
import com.example.plauction.RestClientImpl.RESTClientImplementation;
import com.facebook.shimmer.ShimmerFrameLayout;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ShimmerFrameLayout shimmerFrameLayout;
    private RelativeLayout relativeLayout;
    CoordinatorLayout coordinatorLayout;

    private ArrayList<String> teamsList=new ArrayList<>();
    private void loadData(){
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);

        RESTClientImplementation.getAuctionTeams(new AuctionTeamsEntity.OnListLoad() {
            @Override
            public void onListLoaded(int code, final AuctionTeamsEntity[] auctionTeamsEntities, VolleyError volleyError) {
                teamsList.add("SELECT A TEAM");
                if(code == 200 && volleyError!=null){
                    // Fetch teams
                    for(int i=0; i<auctionTeamsEntities.length; i++)
                    {
                        teamsList.add(auctionTeamsEntities[i].getTeam_name().toUpperCase());
                    }
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    CommonFunctions.makeSnackBar("Teams Data Fetched",relativeLayout).show();
                }else if(code == 700){
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    CommonFunctions.makeToast("No internet connectivity",getApplicationContext());
                }else {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    CommonFunctions.makeToast(CommonFunctions.getErrorMessage(code, getApplicationContext()),getApplicationContext());
                }
            }
        }, getApplicationContext());
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout=findViewById(R.id.main_coordinate);
        shimmerFrameLayout = findViewById(R.id.shimmer_main);
        relativeLayout = findViewById(R.id.relative_main);
        shimmerFrameLayout.startShimmer();
        loadData();

        // Get reference of widgets from XML layout
        final Spinner spinner = (Spinner) findViewById(R.id.spinnerTeamName);


        // Initializing an ArrayAdapter

        TeamListSpinnerAdapter teamListSpinnerAdapter = new TeamListSpinnerAdapter(this,R.layout.team_spinner_item,teamsList, spinner);

        teamListSpinnerAdapter.setDropDownViewResource(R.layout.team_spinner_item);
        spinner.setAdapter(teamListSpinnerAdapter);
        int getDefaultPos = teamListSpinnerAdapter.getPosition("SELECT A TEAM");
        spinner.setSelection(getDefaultPos); // Default Message

        // Listener for Spinner item
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTeamName = (String) parent.getItemAtPosition(position);
                if (position == 0)
                    CommonFunctions.makeToast("PLEASE SELECT A TEAM",getApplicationContext());
                else {
                    // SELECTION LOGIC MAKE POST VOLLEY
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}