package com.example.plauction.Entities;

public class PlayerInfoEntity {
    String playerName;
    Integer playerId;
    Float amountBought;
    TransferEntity transfers;

    public TransferEntity getTransfers() {
        return transfers;
    }

    public void setTransfers(TransferEntity transfers) {
        this.transfers = transfers;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Float getAmountBought() {
        return amountBought;
    }

    public void setAmountBought(Float amountBought) {
        this.amountBought = amountBought;
    }
}
