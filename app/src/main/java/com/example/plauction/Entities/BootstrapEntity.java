package com.example.plauction.Entities;

import com.android.volley.VolleyError;

import java.util.ArrayList;

public class BootstrapEntity {
    ArrayList<Elements> elements;
    private BootstrapEntity(ArrayList<Elements> elements){
        this.elements=elements;
    }
    public interface OnListLoad {
        public void onListLoaded(int code, BootstrapEntity bootstrapEntity, VolleyError volleyError);
    }
    public ArrayList<Elements> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Elements> elements) {
        this.elements = elements;
    }
}
