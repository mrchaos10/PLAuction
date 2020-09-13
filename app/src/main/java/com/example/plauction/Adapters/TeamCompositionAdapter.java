package com.example.plauction.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.plauction.Entities.TeamCompositionEntity;
import com.example.plauction.R;

import java.util.ArrayList;

public class TeamCompositionAdapter extends BaseAdapter {
    private final Context mContext;
    private final ArrayList<TeamCompositionEntity> teamCompositionEntities;


    public TeamCompositionAdapter(Context context, ArrayList<TeamCompositionEntity> teamCompositionEntities) {
        this.mContext = context;
        this.teamCompositionEntities = teamCompositionEntities;
    }

    @Override
    public int getCount() {
        return teamCompositionEntities.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TeamCompositionEntity teamCompositionEntity = teamCompositionEntities.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.item_teamgridcomp, null);
        }

        TextView title = (TextView)convertView.findViewById(R.id.gridItemTitle);
        TextView points = (TextView)convertView.findViewById(R.id.gridItemPoints);

        title.setText(teamCompositionEntity.getTitle());
        points.setText(teamCompositionEntity.getPoints()+" pts");

        return convertView;
    }

}
