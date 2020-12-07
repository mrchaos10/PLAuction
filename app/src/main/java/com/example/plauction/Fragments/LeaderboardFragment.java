package com.example.plauction.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plauction.Activities.MainActivity;
import com.example.plauction.Adapters.LeaderboardAdapter;
import com.example.plauction.Common.CommonFunctions;
import com.example.plauction.Entities.AuctionTeamsEntity;
import com.example.plauction.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderboardFragment extends Fragment {


    Context context;
    Activity activity;
    private  View inflated_frag;
    private RelativeLayout relativeLayout;
    private RecyclerView recyclerView;
    private ArrayList<AuctionTeamsEntity> auctionTeamsEntitiesList;
    private  AuctionTeamsEntity[] auctionTeamsEntities;
    private Gson gson=new Gson();
    private TextView tvSeekBarProgress;
    private LeaderboardAdapter leaderboardAdapter;

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

    public LeaderboardFragment() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //   eventDetailsLayout.setVisibility(View.VISIBLE);
    }


    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            tvSeekBarProgress.setText(progress != 0 ? "GW: " + progress : "TOTAL");
            //textView.setY(100); just added a value set this properly using screen with height aspect ratio , if you do not set it by default it will be there below seek bar
            updateLeaderBoard(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private void updateLeaderBoard(final int progress)
    {
        Collections.sort(auctionTeamsEntitiesList, new Comparator<AuctionTeamsEntity>() {
            @Override
            public int compare(AuctionTeamsEntity o1, AuctionTeamsEntity o2) {
                return Integer.compare(o2.getTotalPoints(progress), o1.getTotalPoints(progress));
            }
        });
        leaderboardAdapter = new LeaderboardAdapter(auctionTeamsEntitiesList,progress);
        recyclerView.setAdapter(leaderboardAdapter);
        leaderboardAdapter.notifyDataSetChanged();

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflated_frag = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        String auctionTeamsString=getArguments().getString("AUCTION_TEAMS");
        Type listType = new TypeToken<List<AuctionTeamsEntity>>() {}.getType();
        auctionTeamsEntitiesList=gson.fromJson(auctionTeamsString,listType);
        auctionTeamsEntities = new AuctionTeamsEntity[auctionTeamsEntitiesList.size()];
        auctionTeamsEntities=  auctionTeamsEntitiesList.toArray(auctionTeamsEntities);

        tvSeekBarProgress = inflated_frag.findViewById(R.id.seekbar_progress);
        recyclerView = (RecyclerView)inflated_frag.findViewById(R.id.leaderboard_recyclerview);
        SeekBar seekBar = inflated_frag.findViewById(R.id.slider_leaderboard);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        // Set seek bar min:0 and max: gw
        seekBar.setMax(CommonFunctions.getMaxGw());


        //recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(leaderboardAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        ((MainActivity)activity).onAdapterLoaded();
        return inflated_frag;


    }
}
