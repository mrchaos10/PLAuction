package com.example.plauction.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plauction.Activities.MainActivity;
import com.example.plauction.Adapters.PlayersListAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerListFragment extends Fragment {

    Context context;
    Activity activity;
    private RecyclerView playerListRecyclerView;
    private PlayersListAdapter playersListAdapter;
    private  View inflated_frag;
    private CheckBox unsoldCheckbox;
    private ArrayList<AuctionTeamsEntity> auctionTeamsEntitiesList;
    private  AuctionTeamsEntity[] auctionTeamsEntities;
    private Gson gson=new Gson();
    private Map<Integer, ElementsEntity> allPlayers = new HashMap<>(), unsoldPlayers=new HashMap<>();
    private List<ElementsEntity> currentList_, allPlayersList, unsoldPlayersList;
    private EditText searchPlayerName;

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

    public PlayerListFragment() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //   eventDetailsLayout.setVisibility(View.VISIBLE);
    }

    public void loadData()
    {

        // Prepare list of unsold players;
        for (Map.Entry<Integer,  ElementsEntity> entry : CommonFunctions.getPlayerIdToElementMap_().entrySet())
        {
            unsoldPlayers.put(entry.getKey(),entry.getValue());
            allPlayers.put(entry.getKey(),entry.getValue());
        }

        for(AuctionTeamsEntity a: auctionTeamsEntities)
        {
            for(PlayerInfoEntity p: a.getPlayerInfo())
            {
                unsoldPlayers.remove(p.getPlayerId());
            }
        }

        List<ElementsEntity> elementsEntities = new ArrayList<>();

        for (Map.Entry<Integer,  ElementsEntity> entry : allPlayers.entrySet())
        {
            elementsEntities.add(entry.getValue());
        }
        // Sort by total points
        Collections.sort(elementsEntities, new Comparator<ElementsEntity>() {
            @Override
            public int compare(ElementsEntity o1, ElementsEntity o2) {
                return Integer.compare(o2.getTotal_points(), o1.getTotal_points());
            }
        });

        allPlayersList = elementsEntities;

        List<ElementsEntity> elementsEntities2 = new ArrayList<>();

        for (Map.Entry<Integer,  ElementsEntity> entry : unsoldPlayers.entrySet())
        {
            elementsEntities2.add(entry.getValue());
        }

        Collections.sort(elementsEntities2, new Comparator<ElementsEntity>() {
            @Override
            public int compare(ElementsEntity o1, ElementsEntity o2) {
                return Integer.compare(o2.getTotal_points(), o1.getTotal_points());
            }
        });

        unsoldPlayersList = elementsEntities2;

        currentList_ = allPlayersList;

    }

    CompoundButton.OnCheckedChangeListener unsoldCheckedChanged = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked)
            {
                currentList_ = unsoldPlayersList;
            }
            else
            {
                currentList_ = allPlayersList;
            }
            playersListAdapter = new PlayersListAdapter(activity, currentList_);
            playersListAdapter.notifyDataSetChanged();
            playerListRecyclerView.setAdapter(playersListAdapter);
            playerListRecyclerView.scrollToPosition(0);
            searchPlayerName.getText().clear();

        }
    };

    private TextWatcher playerNameTextWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            // update adapter
            String key = searchPlayerName.getText().toString();
            updateAdapter(key);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {


        }
    };

    private void updateAdapter(String key)
    {
        if(key.isEmpty()){
            playersListAdapter = new PlayersListAdapter(activity, currentList_);
            playerListRecyclerView.setAdapter(playersListAdapter);
            playersListAdapter.notifyDataSetChanged();
            return;
        }
        List<ElementsEntity> filteredList = new ArrayList<>();
        for(ElementsEntity e : currentList_) {
            if(e.getWeb_name().toLowerCase().contains(key.toLowerCase())){
                filteredList.add(e);
            }
        }
        playersListAdapter = new PlayersListAdapter(activity, filteredList);
        playerListRecyclerView.setAdapter(playersListAdapter);
        playersListAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflated_frag = inflater.inflate(R.layout.fragment_playerlist, container, false);

        playerListRecyclerView = inflated_frag.findViewById(R.id.playersListRecyclerView);
        playerListRecyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        playerListRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        unsoldCheckbox = inflated_frag.findViewById(R.id.checkbox_unsold);
        unsoldCheckbox.setOnCheckedChangeListener(unsoldCheckedChanged);

        searchPlayerName = inflated_frag.findViewById(R.id.playerNameEditText);
        searchPlayerName.addTextChangedListener(playerNameTextWatcher);

        String auctionTeamsString=getArguments().getString("AUCTION_TEAMS");
        Type listType = new TypeToken<List<AuctionTeamsEntity>>() {}.getType();
        allPlayers = CommonFunctions.getPlayerIdToElementMap_();

        auctionTeamsEntitiesList=gson.fromJson(auctionTeamsString,listType);
        auctionTeamsEntities = new AuctionTeamsEntity[auctionTeamsEntitiesList.size()];
        auctionTeamsEntities=  auctionTeamsEntitiesList.toArray(auctionTeamsEntities);


        loadData();

        playersListAdapter = new PlayersListAdapter(activity,currentList_);
        playerListRecyclerView.setAdapter(playersListAdapter);

        ((MainActivity)activity).onAdapterLoaded();

        return inflated_frag;

    }
}

