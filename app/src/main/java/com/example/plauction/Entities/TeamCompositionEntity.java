package com.example.plauction.Entities;

public class TeamCompositionEntity {

    String title;
    Integer points;

    public TeamCompositionEntity(String title, Integer points) {
        this.title = title;
        this.points = points;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }


}
