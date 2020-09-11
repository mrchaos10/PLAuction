package com.example.plauction.Entities;

import java.util.ArrayList;

public class BootstrapEntity {
    ArrayList<Elements> elements;
    private BootstrapEntity(ArrayList<Elements> elements){
        this.elements=elements;
    }

    public ArrayList<Elements> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Elements> elements) {
        this.elements = elements;
    }
}
