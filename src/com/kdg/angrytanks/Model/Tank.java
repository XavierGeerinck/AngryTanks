package com.kdg.angrytanks.Model;

import java.util.Random;

public class Tank {
    private final int x_position;
    private final int y_position;
    private final static double GRAVITATIECONSTANTE = 9.81;

    private boolean mode;
    private double speed;
    private double angle;
    private int wind;

    private final String symbol;

    private String name;

    private int score;

    public Tank(int x, int y, String symbol) {
        x_position = x;
        y_position = y;
        this.symbol = symbol;
        name = symbol;

        score = 0;
    }

    public int getXPosition() {
        return x_position;
    }

    public int getYPosition() {
        return y_position;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public double getSpeed() {
        return speed;
    }

    public double getAngle() {
        return angle;
    }

    public int getWind() {
        return wind;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setMode(boolean mode){
        this.mode = mode;
    }

    public int calculateFormula(int x) {
        double angle = this.angle * Math.PI / 180;
        double temp1 = -GRAVITATIECONSTANTE / 2 / Math.pow(speed * Math.cos(angle) - wind, 2) * Math.pow(x - x_position, 2);

        double temp2 = (x - x_position) * Math.sin(angle) / (Math.cos(angle) - wind / speed) + y_position;

        double total = temp1 + temp2;
        // return 0 if we got negative

        if (total > 1 ) {
            return (int) total;
        } else {
            return 1;
        }
    }

    public void createWind() {
        if (mode){
            wind = 0;
        }
        else{
            Random random = new Random();
            wind = random.nextInt(20) - 10;
        }
    }


}
