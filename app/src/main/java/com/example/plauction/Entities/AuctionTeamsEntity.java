package com.example.plauction.Entities;

import com.android.volley.VolleyError;
import com.example.plauction.Common.CommonFunctions;

import java.util.ArrayList;

public class AuctionTeamsEntity {
    String teamName;
    ArrayList<PlayerInfoEntity> playerInfo;
    ArrayList<TransferEntity> transferEntities;

    public interface OnListLoad {
        public void onListLoaded(int code, AuctionTeamsEntity[] auctionTeamsEntities, VolleyError volleyError);
    }

    public ArrayList<PlayerInfoEntity> getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(ArrayList<PlayerInfoEntity> playerInfo) {
        this.playerInfo = playerInfo;
    }

    public ArrayList<TransferEntity> getTransferEntities() {
        return transferEntities;
    }

    public AuctionTeamsEntity(String teamName, ArrayList<PlayerInfoEntity> playerInfo, ArrayList<TransferEntity> transferEntities) {
        this.teamName = teamName;
        this.transferEntities = transferEntities;
        this.playerInfo = playerInfo;
    }


    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTransferEntities(ArrayList<TransferEntity> transferEntities) {
        this.transferEntities = transferEntities;
    }

    public int getTotalPoints() {
        if (transferEntities == null) {
            return CommonFunctions.getTeamTotalSum(getPlayerInfo());
        } else {
            return CommonFunctions.getGameWeekAggSum(getPlayerInfo());
        }

    }
}

