package com.example.plauction.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plauction.Activities.MainActivity;
import com.example.plauction.Adapters.PlayersAdapter;
import com.example.plauction.Adapters.PlayersListAdapter;
import com.example.plauction.Adapters.TeamListSpinnerAdapter;
import com.example.plauction.Common.CommonFunctions;
import com.example.plauction.Entities.AuctionTeamsEntity;
import com.example.plauction.Entities.ElementsEntity;
import com.example.plauction.Entities.PlayerInfoEntity;
import com.example.plauction.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TeamsFragment extends Fragment {

    Context context;
    Activity activity;
    private TextView teamPoints;
    private RecyclerView playerRecyclerView;

    private NestedScrollView playersNestedScrollView;
    private  View inflated_frag;
    private RelativeLayout relativeLayout;
    private Spinner spinner;
    private ArrayList<String> teamsList=new ArrayList<>();
    private ArrayList<ArrayList<PlayerInfoEntity>> teamPlayers=new ArrayList<>();
    private ArrayList<ElementsEntity> elements;
    private PlayersAdapter playersAdapter;
    private ArrayList<AuctionTeamsEntity> auctionTeamsEntitiesList;
    private  AuctionTeamsEntity[] auctionTeamsEntities;
    private Gson gson=new Gson();
    private void loadData(){
        teamsList.add("SELECT A TEAM");
        teamPlayers.add(null);

        for(int i=0; i<auctionTeamsEntities.length; i++)
        {
            teamsList.add(auctionTeamsEntities[i].getTeamName().toUpperCase());
            teamPlayers.add(auctionTeamsEntities[i].getPlayerInfo());
        }

        // Initializing an ArrayAdapter
        TeamListSpinnerAdapter teamListSpinnerAdapter = new TeamListSpinnerAdapter(context,R.layout.item_team_spinner,teamsList, spinner);
        teamListSpinnerAdapter.setDropDownViewResource(R.layout.item_team_spinner);
        spinner.setAdapter(teamListSpinnerAdapter);
        int getDefaultPos = teamListSpinnerAdapter.getPosition("SELECT A TEAM");
        spinner.setSelection(getDefaultPos); // Default Message

        // Listener for Spinner item
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTeamName = (String) parent.getItemAtPosition(position);
                ArrayList<PlayerInfoEntity> selectedTeamPlayers= teamPlayers.get(position);
                if (position == 0) {
                    CommonFunctions.makeToast("PLEASE SELECT A TEAM", context);
                }
                else {
                    // SELECTION LOGIC MAKE POST VOLLEY
                    //Log.i("SUM", CommonFunctions.getTeamTotalSum(selectedTeamPlayers)+"");
                    teamPoints.setText(selectedTeamName+" Points : "+CommonFunctions.getGameWeekAggSum(selectedTeamPlayers,0));
                    playersNestedScrollView.setVisibility(View.VISIBLE);
                    Collections.sort(selectedTeamPlayers, new Comparator<PlayerInfoEntity>() {
                        @Override
                        public int compare(PlayerInfoEntity o1, PlayerInfoEntity o2) {
                            return CommonFunctions.getPlayerIdToElementMap_().get(o2.getPlayerId()).getTotal_points().compareTo(CommonFunctions.getPlayerIdToElementMap_().get(o1.getPlayerId()).getTotal_points());
                        }
                    });
                    playersAdapter = new PlayersAdapter(getActivity(),selectedTeamPlayers,CommonFunctions.getPlayerIdToElementMap_());
                    playerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    playerRecyclerView.setAdapter(playersAdapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

        relativeLayout = (RelativeLayout)inflated_frag.findViewById(R.id.relative_detail_frag);
        spinner = inflated_frag.findViewById(R.id.spinnerTeamName);
        playerRecyclerView=(RecyclerView) inflated_frag.findViewById(R.id.playersRecyclerView);
        playersNestedScrollView=(NestedScrollView)inflated_frag.findViewById(R.id.playersNestedScrollView);
        teamPoints=(TextView) inflated_frag.findViewById(R.id.TeamPoints);
        String auctionTeamsString=getArguments().getString("AUCTION_TEAMS");
        Type listType = new TypeToken<List<AuctionTeamsEntity>>() {}.getType();
        auctionTeamsEntitiesList=gson.fromJson(auctionTeamsString,listType);
        auctionTeamsEntities = new AuctionTeamsEntity[auctionTeamsEntitiesList.size()];
        auctionTeamsEntities=  auctionTeamsEntitiesList.toArray(auctionTeamsEntities);
        // Load Data
        loadData();

        ((MainActivity)activity).onAdapterLoaded();
        return inflated_frag;

    }
}
