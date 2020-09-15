package com.example.plauction.Entities;

import com.android.volley.VolleyError;

import java.util.ArrayList;

public class BootstrapEntity {
    ArrayList<ElementsEntity> elements;
    private BootstrapEntity(ArrayList<ElementsEntity> elements){
        this.elements=elements;
    }
    public interface OnListLoad {
        public void onListLoaded(int code, BootstrapEntity bootstrapEntity, VolleyError volleyError);
    }
    public ArrayList<ElementsEntity> getElements() {
        return elements;
    }

    public void setElements(ArrayList<ElementsEntity> elements) {
        this.elements = elements;
    }
}
