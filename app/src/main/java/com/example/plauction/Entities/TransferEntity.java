package com.example.plauction.Entities;

public class TransferEntity {
    int in;
    int out;
    int gw;

    public TransferEntity(int in, int out, int gw) {
        this.in = in;
        this.out = out;
        this.gw = gw;
    }

    public int getIn() {
        return in;
    }

    public void setIn(int in) {
        this.in = in;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }

    public int getGw() {
        return gw;
    }

    public void setGw(int gw) {
        this.gw = gw;
    }
}
