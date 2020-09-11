package com.example.plauction.Entities;

import com.android.volley.VolleyError;

import java.util.ArrayList;

public class Elements {
    String photo;
    Integer total_points;
    Integer team;

    public Integer getTeam() {
        return team;
    }

    public void setTeam(Integer team) {
        this.team = team;
    }

    public Elements(String photo, Integer total_points) {
        this.photo = photo;
        this.total_points = total_points;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getTotal_points() {
        return total_points;
    }

    public void setTotal_points(Integer total_points) {
        this.total_points = total_points;
    }
}

