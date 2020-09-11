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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.plauction.Constants.Constants;
import com.example.plauction.Entities.Elements;
import com.example.plauction.Entities.Playerinfo;
import com.example.plauction.R;

import java.util.List;


public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {
    private List<Playerinfo> selectedTeamPlayers;
    private List<Elements> elements;
    private Activity activity;
    private String[] teams=new String[]{"Arsenal","Aston Villa","Brighton","Burnley","Chelsea", "Crystal Palace","Everton","Fulham","Leicester","Leeds","Liverpool", "Man City","Man Utd","Newcastle United","Sheffield United","Southampton","Spurs","West Bromwich","West Ham","Wolves"};
    public PlayersAdapter(Activity activity,List<Playerinfo> selectedTeamPlayers,List<Elements> elements){
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
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
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

        public ViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView) itemView.findViewById(R.id.livePlayersCardView);
            playerImage=(ImageView) itemView.findViewById(R.id.playerImage);
            playerName=(TextView) itemView.findViewById(R.id.playerName);
            playerPrice=(TextView) itemView.findViewById(R.id.playerPrice);
            playerClub=(TextView) itemView.findViewById(R.id.playerClub);
            playerTotalPoints=(TextView) itemView.findViewById(R.id.playerTotalPoints);
            playerClub=(TextView) itemView.findViewById(R.id.playerClub);

        }
    }
}