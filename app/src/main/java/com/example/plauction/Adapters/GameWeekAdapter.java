package com.example.plauction.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plauction.Entities.History;
import com.example.plauction.Entities.Playerinfo;
import com.example.plauction.R;

import java.util.List;


public class GameWeekAdapter extends RecyclerView.Adapter<GameWeekAdapter.ViewHolder> {
    private List<History> history;
    private Activity activity;
    public GameWeekAdapter(Activity activity, List<History> history){
        this.activity=activity;
        this.history=history;
    }

    @NonNull
    @Override
    public GameWeekAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playergameweekdetails_layout, parent, false);
        GameWeekAdapter.ViewHolder holder = new GameWeekAdapter.ViewHolder(view);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GameWeekAdapter.ViewHolder holder, int position) {
        History gameWeek = history.get(position);
//        Log.i("element",element.getTeam().toString());
//        Log.i("image",element.getPhoto());
//        Log.i("points",element.getTotal_points().toString());
        holder.gameWeekID.setText("GAMEWEEK "+gameWeek.getRound());
        holder.gameWeekMP.setText(gameWeek.getMinutes()+"");
        holder.gameWeekGS.setText(gameWeek.getGoals_scored()+"");
        holder.gameWeekAS.setText(gameWeek.getAssists()+"");
        holder.gameWeekCS.setText(gameWeek.getClean_sheets()+"");
        holder.gameweekPts.setText(gameWeek.getTotal_points()+"");
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Playerinfo player, int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView gameWeekID, gameWeekMP, gameWeekGS,gameWeekAS,gameWeekCS,gameweekPts;
        public ViewHolder(View itemView) {
            super(itemView);
            gameWeekID=(TextView) itemView.findViewById(R.id.gameweekID);
            gameWeekMP=(TextView) itemView.findViewById(R.id.gameweekMins);
            gameWeekGS=(TextView) itemView.findViewById(R.id.gamweekGS);
            gameWeekAS=(TextView) itemView.findViewById(R.id.gameweekAssists);
            gameWeekCS=(TextView) itemView.findViewById(R.id.gameweekCS);
            gameweekPts=(TextView) itemView.findViewById(R.id.gameweekPoints);
        }
    }
}