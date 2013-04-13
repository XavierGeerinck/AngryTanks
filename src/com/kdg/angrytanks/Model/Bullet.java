package com.kdg.angrytanks.Model;

/**
 * Created by IntelliJ IDEA.
 * User: TVL
 * Date: 24/01/12
 * Time: 15:46
 * To change this template use File | Settings | File Templates.
 */
public class Bullet {
    private int xcoord;
    private int ycoord;

    public Bullet(int xcoord, int ycoord) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    public int getXcoord() {
        return xcoord;
    }

    public int getYcoord() {
        return ycoord;
    }
}
