package com.example.plauction.Entities;

import com.android.volley.VolleyError;

import java.util.ArrayList;

public class ElementHistoryEntity {

    private ArrayList<HistoryEntity> history;

    public ArrayList<HistoryEntity> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<HistoryEntity> history) {
        this.history = history;
    }

    private ElementHistoryEntity(ArrayList<HistoryEntity> history){
        this.history=history;
    }
    public interface OnListLoad {
        public void onListLoaded(int code, ElementHistoryEntity elementHistoryEntity, VolleyError volleyError);
    }
}
