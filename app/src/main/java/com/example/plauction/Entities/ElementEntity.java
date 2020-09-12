package com.example.plauction.Entities;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

public class ElementEntity {

    private ArrayList<History> history;

    public ArrayList<History> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<History> history) {
        this.history = history;
    }

    private ElementEntity(ArrayList<History> history){
        this.history=history;
    }
    public interface OnListLoad {
        public void onListLoaded(int code, ElementEntity elementEntity, VolleyError volleyError);
    }
}
