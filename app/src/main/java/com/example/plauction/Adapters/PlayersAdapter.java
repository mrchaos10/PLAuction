package com.example.plauction.Adapters;

import android.app.Activity;
import android.graphics.Color;
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
import com.example.plauction.Entities.ElementHistoryEntity;
import com.example.plauction.Entities.ElementsEntity;
import com.example.plauction.Entities.HistoryEntity;
import com.example.plauction.Entities.PlayerInfoEntity;
import com.example.plauction.R;
import com.example.plauction.RestClientImpl.RESTClientImplementation;

import java.util.List;
import java.util.Map;

import static java.sql.Types.NULL;


public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {
    private List<PlayerInfoEntity> selectedTeamPlayers;
    private Map<Integer, ElementsEntity> elements;
    private GameWeekAdapter gameWeekAdapter;
    private Activity activity;
    private String[] teams=new String[]{"Arsenal","Aston Villa","Brighton","Burnley","Chelsea", "Crystal Palace","Everton","Fulham","Leicester","Leeds","Liverpool", "Man City","Man Utd","Newcastle United","Sheffield United","Southampton","Spurs","West Bromwich","West Ham","Wolves"};
    private int[] colors= new int[]{Color.RED,Color.parseColor("#670E36"),Color.parseColor("#0057B8"),Color.parseColor("#6C1D45"),Color.parseColor("#034694"),Color.parseColor("#C4122E"),Color.parseColor("#003399"),Color.parseColor("#CC0000"),Color.parseColor("#003090"),Color.parseColor("#FFCD00"),Color.parseColor("#C8102E"),Color.parseColor("#6CABDD"),Color.parseColor("#DA291C"),Color.parseColor("#241F20"),Color.parseColor("#EE2737"),Color.parseColor("#D71920"),Color.parseColor("#132257"),Color.parseColor("#122F67"),Color.parseColor("#7A263A"),Color.parseColor("#FDB913")};
    public PlayersAdapter(Activity activity, List<PlayerInfoEntity> selectedTeamPlayers, Map<Integer, ElementsEntity> elements){
        this.activity=activity;
        this.elements=elements;
        this.selectedTeamPlayers=selectedTeamPlayers;
    }

    @NonNull
    @Override
    public PlayersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_players, parent, false);
        PlayersAdapter.ViewHolder holder = new PlayersAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayersAdapter.ViewHolder holder, int position) {
        PlayerInfoEntity player = selectedTeamPlayers.get(position);
        ElementsEntity element=elements.get(player.getPlayerId());
//        Log.i("element",element.getTeam().toString());
//        Log.i("image",element.getPhoto());
//        Log.i("points",element.getTotal_points().toString());
        holder.playerName.setText(player.getPlayerName());
        holder.playerPrice.setText(player.getAmountBought().toString());
        holder.playerTotalPoints.setText(element.getTotal_points().toString());
        holder.playerClub.setText(teams[element.getTeam()-1]);
        holder.playerClub.setBackgroundColor(colors[element.getTeam()-1]);
        int In=0,out=0;
        if(player.getTransfer()!=null) {
                In = player.getTransfer().getIn();
                holder.playerTransferStatusIn.setText(""+String.valueOf(In));
                out = player.getTransfer().getOut();
                holder.playerTransferStatusOut.setText(""+String.valueOf(out));
        }
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
        void onItemClick(View view, PlayerInfoEntity player, int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView playerName, playerPrice, playerTotalPoints,playerClub;
        private CardView cardView;
        private ImageView playerImage;
        private View playerGwViewHeader;
        private ConstraintLayout playersLiveLayout;
        private NestedScrollView playersGwDetails;
        private RecyclerView playersGwRecyclerView;
        private TextView playerTransferStatusIn;
        private TextView playerTransferStatusOut;
        private List<HistoryEntity> history;
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
            playerTransferStatusIn=(TextView)itemView.findViewById(R.id.playerTransferStatusIn);
            playerTransferStatusOut=(TextView)itemView.findViewById(R.id.playerTransferStatusOut);
            playerClub=(TextView) itemView.findViewById(R.id.playerClub);
            playersLiveLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    Log.i("clicking","working");
                    final PlayerInfoEntity player = selectedTeamPlayers.get(getAdapterPosition());
                    Map<String, List<HistoryEntity>> elementSummaries = CommonFunctions.getElementSummaries();
                    history = elementSummaries.get(player.getPlayerId()+"");
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

                    // Below call is now not required as we have fetched all element summaries
                    
                    /*RESTClientImplementation.getElementSummary(new ElementHistoryEntity.OnListLoad() {
                        @Override
                        public void onListLoaded(int code, ElementHistoryEntity elementHistoryEntity, VolleyError volleyError) {
                            if(code == 200 && volleyError!=null){
                                history= elementHistoryEntity.getHistory();
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
                    },view.getContext(),player.getPlayerId()); */

                }
            });

        }
    }
}