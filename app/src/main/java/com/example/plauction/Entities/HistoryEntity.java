package com.example.plauction.Entities;

public class HistoryEntity {
    Integer total_points;
    Integer round;
    Integer minutes;
    Integer goals_scored;
    Integer assists;
    Integer clean_sheets;
    public HistoryEntity(Integer total_points, Integer round, Integer minutes, Integer goals_scored, Integer assists, Integer clean_sheets) {
        this.total_points = total_points;
        this.round = round;
        this.minutes = minutes;
        this.goals_scored = goals_scored;
        this.assists = assists;
        this.clean_sheets = clean_sheets;
    }

    public Integer getTotal_points() {
        return total_points;
    }

    public void setTotal_points(Integer total_points) {
        this.total_points = total_points;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getGoals_scored() {
        return goals_scored;
    }

    public void setGoals_scored(Integer goals_scored) {
        this.goals_scored = goals_scored;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Integer getClean_sheets() {
        return clean_sheets;
    }

    public void setClean_sheets(Integer clean_sheets) {
        this.clean_sheets = clean_sheets;
    }
}
