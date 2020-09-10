package com.example.plauction.Entities;

import com.android.volley.VolleyError;

import java.util.ArrayList;

public class AuctionTeamsEntity {
    String team_name;
    ArrayList<Integer> players;
    ArrayList<TransferEntity> transferEntities;
    float price;
    ArrayList<String> members;

    public AuctionTeamsEntity(String team_name, ArrayList<Integer> players, ArrayList<TransferEntity> transferEntities, float price, ArrayList<String> members) {
        this.team_name = team_name;
        this.players = players;
        this.transferEntities = transferEntities;
        this.price = price;
        this.members = members;
    }

    public interface OnListLoad {
        public void onListLoaded(int code, AuctionTeamsEntity[] auctionTeamsEntities, VolleyError volleyError);
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public ArrayList<Integer> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Integer> players) {
        this.players = players;
    }

    public ArrayList<TransferEntity> getTransferEntities() {
        return transferEntities;
    }

    public void setTransferEntities(ArrayList<TransferEntity> transferEntities) {
        this.transferEntities = transferEntities;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }
}

