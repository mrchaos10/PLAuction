package com.example.plauction.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.plauction.Common.CommonFunctions;
import com.example.plauction.Entities.AuctionTeamsEntity;
import com.example.plauction.R;

import java.util.List;

public class LeaderboardAdapter extends
        RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private List<AuctionTeamsEntity> auctionTeamsEntityList;
    public  int gw;

    public LeaderboardAdapter(List<AuctionTeamsEntity> auctionTeamsEntityList,int gw) {
        this.auctionTeamsEntityList = auctionTeamsEntityList;
        this.gw = gw;
    }





    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView rankTextView,teamNameTextView, pointsTextView;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            rankTextView = (TextView) itemView.findViewById(R.id.rank_tv);
            teamNameTextView = (TextView) itemView.findViewById(R.id.team_name_tv);
            pointsTextView = (TextView)itemView.findViewById(R.id.points_tv);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.leaderboard_item);
        }

        @Override
        public void onClick(View view) {
            CommonFunctions.getTeamComposition(auctionTeamsEntityList.get(getAdapterPosition()), view.getContext(), getAdapterPosition()+1, gw).show();
        }
    }

    @Override
    public LeaderboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_leaderboard, parent, false);

        return new ViewHolder(contactView);
    }
    @Override
    public void onBindViewHolder(LeaderboardAdapter.ViewHolder holder, int position) {

        AuctionTeamsEntity auctionTeamsEntity = auctionTeamsEntityList.get(position);
        RelativeLayout relativeLayout = holder.relativeLayout;
        TextView rankTextView = holder.rankTextView;
        TextView teamNameTextView = holder.teamNameTextView;
        TextView pointsTextView = holder.pointsTextView;


        rankTextView.setText(Integer.toString(position+1));
        teamNameTextView.setText(auctionTeamsEntity.getTeamName());
        pointsTextView.setText(Integer.toString(auctionTeamsEntity.getTotalPoints(this.gw))+" pts");

    }

    @Override
    public int getItemCount() {
        return auctionTeamsEntityList.size();
    }
}