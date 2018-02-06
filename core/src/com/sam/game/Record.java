package com.sam.game;

import java.io.Serializable;


public class Record implements Serializable {
    private int currentBones;
    private int topScore;
    private boolean hasSkin1;
    public Record(int currentBones, int topScore, boolean hasSkin1) {
        this.currentBones = currentBones;
        this.topScore = topScore;
        this.hasSkin1 = hasSkin1;
    }


    public int getCurrentBones() {
        return currentBones;
    }

    public int getTopScore() {
        return topScore;
    }

    public boolean HasSkin1() {
        return hasSkin1;
    }
}
