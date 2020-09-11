package com.example.plauction.Entities;

import com.android.volley.VolleyError;

import java.util.ArrayList;

public class PlayersEntity {
    String team_name;
    ArrayList<Integer> players;
    ArrayList<TransferEntity> transferEntities;
    float price;
    ArrayList<String> members;

    public PlayersEntity(String team_name, ArrayList<Integer> players, ArrayList<TransferEntity> transferEntities, float price, ArrayList<String> members) {
        this.team_name = team_name;
        this.players = players;
        this.transferEntities = transferEntities;
        this.price = price;
        this.members = members;
    }

    public interface OnListLoad {
        public void onListLoaded(int code, PlayersEntity playersEntity, VolleyError volleyError);
    }


}

