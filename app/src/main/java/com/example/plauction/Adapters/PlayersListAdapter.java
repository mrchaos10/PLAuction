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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.plauction.Constants.Constants;
import com.example.plauction.Entities.ElementsEntity;

import com.example.plauction.R;

import java.util.List;



public class PlayersListAdapter extends RecyclerView.Adapter<PlayersListAdapter.ViewHolder> {

    private List<ElementsEntity> elementsEntities;
    private Activity activity;
    private String[] teams=new String[]{"Arsenal","Aston Villa","Brighton","Burnley","Chelsea", "Crystal Palace","Everton","Fulham","Leicester","Leeds","Liverpool", "Man City","Man Utd","Newcastle United","Sheffield United","Southampton","Spurs","West Bromwich","West Ham","Wolves"};
    private int[] colors= new int[]{Color.RED,Color.parseColor("#670E36"),Color.parseColor("#0057B8"),Color.parseColor("#6C1D45"),Color.parseColor("#034694"),Color.parseColor("#C4122E"),Color.parseColor("#003399"),Color.parseColor("#CC0000"),Color.parseColor("#003090"),Color.parseColor("#FFCD00"),Color.parseColor("#C8102E"),Color.parseColor("#6CABDD"),Color.parseColor("#DA291C"),Color.parseColor("#241F20"),Color.parseColor("#EE2737"),Color.parseColor("#D71920"),Color.parseColor("#132257"),Color.parseColor("#122F67"),Color.parseColor("#7A263A"),Color.parseColor("#FDB913")};


    public PlayersListAdapter(Activity activity, List<ElementsEntity> elementsEntities){
        this.activity=activity;
        this.elementsEntities = elementsEntities;
    }

    @NonNull
    @Override
    public PlayersListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_playerslist, parent, false);
        PlayersListAdapter.ViewHolder holder = new PlayersListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayersListAdapter.ViewHolder holder, int position) {
        ElementsEntity element=elementsEntities.get(position);
        holder.playerName.setText(element.getWeb_name());
        holder.playerTotalPoints.setText(element.getTotal_points()+" points");
        holder.playerClub.setText(teams[element.getTeam()-1]);
        holder.playerClub.setBackgroundColor(colors[element.getTeam()-1]);

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.player_image)
                .error(R.drawable.player_image);
        Glide.with(holder.playerImage.getContext()).load(Constants.PLAYER_IMAGE_URL+element.getPhoto().replace(".jpg",".png")).apply(options).into(holder.playerImage);

    }

    @Override
    public int getItemCount() {
        return elementsEntities.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView playerName, playerTotalPoints,playerClub;
        private CardView cardView;
        private ImageView playerImage;
        public ViewHolder(View itemView) {
            super(itemView);
            playerImage=(ImageView) itemView.findViewById(R.id.playerImage);
            playerName=(TextView) itemView.findViewById(R.id.playerName);
            playerClub=(TextView) itemView.findViewById(R.id.playerClub);
            playerTotalPoints=(TextView) itemView.findViewById(R.id.playerTotalPoints);
        }
    }
}