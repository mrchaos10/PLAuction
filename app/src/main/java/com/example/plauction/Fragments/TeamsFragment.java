package com.example.plauction.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.plauction.Activities.MainActivity;
import com.example.plauction.Adapters.PlayersAdapter;
import com.example.plauction.Adapters.TeamListSpinnerAdapter;
import com.example.plauction.Common.CommonFunctions;
import com.example.plauction.Entities.AuctionTeamsEntity;
import com.example.plauction.Entities.BootstrapEntity;
import com.example.plauction.Entities.Elements;
import com.example.plauction.Entities.Playerinfo;
import com.example.plauction.R;
import com.example.plauction.RestClientImpl.RESTClientImplementation;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeamsFragment extends Fragment {

    Context context;
    Activity activity;
    private RecyclerView playerRecyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private NestedScrollView playersNestedScrollView;
    private  View inflated_frag;
    private RelativeLayout relativeLayout;
    private Spinner spinner;
    private ArrayList<String> teamsList=new ArrayList<>();
    private ArrayList<ArrayList<Playerinfo>> teamPlayers=new ArrayList<>();
    private ArrayList<Elements> elements;
    private PlayersAdapter playersAdapter;
    private void loadData(){
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        RESTClientImplementation.getBootstrapStatic(new BootstrapEntity.OnListLoad() {
            @Override
            public void onListLoaded(int code, BootstrapEntity bootstrapEntity, VolleyError volleyError) {
                if(code == 200 && volleyError!=null){
                    elements=bootstrapEntity.getElements();
                }
                else{
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    CommonFunctions.makeToast("No internet connectivity",context);
                }
        }
        },context);

        RESTClientImplementation.getAuctionTeams(new AuctionTeamsEntity.OnListLoad() {
            @Override
            public void onListLoaded(int code, final AuctionTeamsEntity[] auctionTeamsEntities, VolleyError volleyError) {
                teamsList.add("SELECT A TEAM");
                teamPlayers.add(null);
                if(code == 200 && volleyError!=null){
                    // Fetch teams
                    for(int i=0; i<auctionTeamsEntities.length; i++)
                    {
                        teamsList.add(auctionTeamsEntities[i].getTeamName().toUpperCase());
                        teamPlayers.add(auctionTeamsEntities[i].getPlayerInfo());
                    }
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    // Initializing an ArrayAdapter
                    TeamListSpinnerAdapter teamListSpinnerAdapter = new TeamListSpinnerAdapter(context,R.layout.team_spinner_item,teamsList, spinner);
                    teamListSpinnerAdapter.setDropDownViewResource(R.layout.team_spinner_item);
                    spinner.setAdapter(teamListSpinnerAdapter);
                    int getDefaultPos = teamListSpinnerAdapter.getPosition("SELECT A TEAM");
                    spinner.setSelection(getDefaultPos); // Default Message

                    // Listener for Spinner item
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedTeamName = (String) parent.getItemAtPosition(position);
                            ArrayList<Playerinfo> selectedTeamPlayers= teamPlayers.get(position);
                            if (position == 0) {
                                CommonFunctions.makeToast("PLEASE SELECT A TEAM", context);
                            }
                            else {
                                // SELECTION LOGIC MAKE POST VOLLEY
                                Log.i("selected",selectedTeamName);
                                //Log.i("players",selectedTeamPlayers.get(1).getPlayerId().toString());
                                //Log.i("elements",elements.get(1).getTeam().toString());
                                Map<Integer,Elements> map = new HashMap<Integer,Elements>();
                                for (Elements e : elements){
                                    map.put(e.getId(),e);
                                }
                                playersNestedScrollView.setVisibility(View.VISIBLE);
                                playersAdapter = new PlayersAdapter(getActivity(),selectedTeamPlayers,map);
                                playerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                playerRecyclerView.setAdapter(playersAdapter);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    CommonFunctions.makeSnackBar("Teams Data Fetched",relativeLayout).show();
                }else if(code == 700){
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    CommonFunctions.makeToast("No internet connectivity",context);
                }else {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    CommonFunctions.makeToast(CommonFunctions.getErrorMessage(code, context),context);
                }
            }
        }, context);
    }

    @Override
    public void onAttach(Context context) {
        this.context =context;
        activity = getActivity();
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        this.context = null;
        activity = null;
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        this.context = null;
        activity = null;
        super.onDetach();
    }

    public TeamsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
     //   eventDetailsLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflated_frag = inflater.inflate(R.layout.fragment_details, container, false);
        shimmerFrameLayout =(ShimmerFrameLayout)inflated_frag.findViewById(R.id.shimmer_details_frag);
        relativeLayout = (RelativeLayout)inflated_frag.findViewById(R.id.relative_detail_frag);
        spinner = inflated_frag.findViewById(R.id.spinnerTeamName);
        playerRecyclerView=(RecyclerView) inflated_frag.findViewById(R.id.playersRecyclerView);
        playersNestedScrollView=(NestedScrollView)inflated_frag.findViewById(R.id.playersNestedScrollView);
        // load Data
        loadData();


        ((MainActivity)activity).onAdapterLoaded();
        return inflated_frag;

    }
}
