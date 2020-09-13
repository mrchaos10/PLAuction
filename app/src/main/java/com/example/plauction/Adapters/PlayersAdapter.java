package com.example.plauction.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.plauction.Common.CommonFunctions;
import com.example.plauction.Constants.Constants;
import com.example.plauction.Entities.BootstrapEntity;
import com.example.plauction.Entities.ElementEntity;
import com.example.plauction.Entities.Elements;
import com.example.plauction.Entities.History;
import com.example.plauction.Entities.Playerinfo;
import com.example.plauction.R;
import com.example.plauction.RestClientImpl.RESTClientImplementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {
    private List<Playerinfo> selectedTeamPlayers;
    private Map<Integer,Elements> elements;
    private GameWeekAdapter gameWeekAdapter;
    private Activity activity;
    private String[] teams=new String[]{"Arsenal","Aston Villa","Brighton","Burnley","Chelsea", "Crystal Palace","Everton","Fulham","Leicester","Leeds","Liverpool", "Man City","Man Utd","Newcastle United","Sheffield United","Southampton","Spurs","West Bromwich","West Ham","Wolves"};
    public PlayersAdapter(Activity activity, List<Playerinfo> selectedTeamPlayers, Map<Integer,Elements> elements){
        this.activity=activity;
        this.elements=elements;
        this.selectedTeamPlayers=selectedTeamPlayers;
    }

    @NonNull
    @Override
    public PlayersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.players_adapter, parent, false);
        PlayersAdapter.ViewHolder holder = new PlayersAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayersAdapter.ViewHolder holder, int position) {
        Playerinfo player = selectedTeamPlayers.get(position);
        Elements element=elements.get(player.getPlayerId());
//        Log.i("element",element.getTeam().toString());
//        Log.i("image",element.getPhoto());
//        Log.i("points",element.getTotal_points().toString());
        holder.playerName.setText(player.getPlayerName());
        holder.playerPrice.setText(player.getAmountBought().toString());
        holder.playerTotalPoints.setText(element.getTotal_points().toString());
        holder.playerClub.setText(teams[element.getTeam()-1]);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.player_image)
                .error(R.drawable.player_image);
        Glide.with(holder.playerImage.getContext()).load(Constants.PLAYER_IMAGE_URL+element.getPhoto().replace(".jpg",".png")).apply(options).into(holder.playerImage);
   }

    @Override
    public int getItemCount() {
        return selectedTeamPlayers.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Playerinfo player, int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView playerName, playerPrice, playerTotalPoints,playerClub;
        private CardView cardView;
        private ImageView playerImage;
        private View playerGwViewHeader;
        private ConstraintLayout playersLiveLayout;
        private NestedScrollView playersGwDetails;
        private RecyclerView playersGwRecyclerView;
        private ArrayList<History> history;
        public ViewHolder(View itemView) {
            super(itemView);
            playerGwViewHeader=(View) itemView.findViewById(R.id.playerDetailsTitle);
            playersGwDetails=(NestedScrollView) itemView.findViewById(R.id.playerGameweekDetailsLayout);
            playersGwRecyclerView=(RecyclerView) itemView.findViewById(R.id.playerGameweekDetailsRecyclerView);
            cardView=(CardView) itemView.findViewById(R.id.livePlayersCardView);
            playersLiveLayout=(ConstraintLayout) itemView.findViewById(R.id.PlayerLiveLayout);
            playerImage=(ImageView) itemView.findViewById(R.id.playerImage);
            playerName=(TextView) itemView.findViewById(R.id.playerName);
            playerPrice=(TextView) itemView.findViewById(R.id.playerPrice);
            playerClub=(TextView) itemView.findViewById(R.id.playerClub);
            playerTotalPoints=(TextView) itemView.findViewById(R.id.playerTotalPoints);
            playerClub=(TextView) itemView.findViewById(R.id.playerClub);
            playersLiveLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    Log.i("clicking","working");
                    final Playerinfo player = selectedTeamPlayers.get(getAdapterPosition());
                    RESTClientImplementation.getElementSummary(new ElementEntity.OnListLoad() {
                        @Override
                        public void onListLoaded(int code, ElementEntity elementEntity, VolleyError volleyError) {
                            if(code == 200 && volleyError!=null){
                                history=elementEntity.getHistory();
//                                Log.i("history",history.get(0).getMinutes().toString());
                                if(playerGwViewHeader.getVisibility()==View.GONE && playersGwDetails.getVisibility()==View.GONE && history!=null){
                                    playerGwViewHeader.setVisibility(View.VISIBLE);
                                    playersGwDetails.setVisibility(View.VISIBLE);
                                    gameWeekAdapter = new GameWeekAdapter(activity,history);
                                    playersGwRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                                    playersGwRecyclerView.setAdapter(gameWeekAdapter);
                                }
                                else{
                                    playerGwViewHeader.setVisibility(View.GONE);
                                    playersGwDetails.setVisibility(View.GONE);
                                }
                            }
                            else{
                                Log.i("Failed","Network Error");
                            }
                        }
                    },view.getContext(),player.getPlayerId());
                }
            });

        }
    }
}