package com.kdg.angrytanks.Model;

import sun.awt.WindowIDProvider;
import sun.plugin.services.WIExplorerBrowserService;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Landscape {
    //Define what symbols are represented as players
    private static final String PLAYER_SYMBOLS = "abcdefghijklmopqrstuvwxyz";
    //Define what symbols are represented as landscape pieces
    private final static String LANDSCAPE_SYMBOLS = "#"; //SPACE CHAR IS A CHAR TO!!!
    //Define the bullet symbol
    private final static String BULLET_SYMBOL = "*";

    private int HEIGHT; //LINE COUNT!!
    private int WIDTH;

    private final List<Tank> players;
    private String[][] landscape;
    private String[][] tempShowLandscape;
    private List<Bullet> tempLandscape;

    private final String fileLocation;

    public Landscape(int choice) {
        //first define the players
        players = new ArrayList<Tank>();

        //Then define the location of the landscape since else we cant start reading it
        //this.fileLocation = chooseFile(choice);

        //Then read height and width
        if (choice == 5){
            Random generator = new Random();
            choice = generator.nextInt(4) + 1;
        }

        fileLocation = chooseFile(choice);
        tempLandscape = new ArrayList<Bullet>();
        //Set the landscape
        if (choice != 6){
            readFileDimensions(fileLocation);
            landscape = new String[WIDTH][HEIGHT];
            setLandscape();
        }
        else {
            makeRandom();
        }
        tempShowLandscape = new String[WIDTH][HEIGHT];
    }



    private void setLandscape() {
        //Set height = 0 to start counting
        int y = HEIGHT;
        File file = new File(fileLocation);
        try{
            Scanner scanner = new Scanner(file);

            //Read File Line By Line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                for (int x = 0; x< WIDTH; x++) {
                    String temp = line.substring(x, x + 1);
                    //Read the landscape and set it
                    landscape[x][HEIGHT - y] = line.substring(x, (x + 1));
                    //Set our players into our landscape
                    for (int i = 0; i < PLAYER_SYMBOLS.length(); i++){
                        if(PLAYER_SYMBOLS.substring(i, (i + 1)).equalsIgnoreCase(temp)) {
                            players.add(new Tank(x, y, temp));
                        }
                    }
                }
                y--;
            }

            //Close the input stream
            scanner.close();
        }catch (FileNotFoundException e){//Catch exception if any
            e.printStackTrace();
        }
    }

    private void makeRandom(){
        int counter = 0;
        Random generator = new Random();
        WIDTH = generator.nextInt(50)+50;
        HEIGHT = generator.nextInt(30)+ 10;
        landscape = new String[WIDTH][HEIGHT];
        //Makes random collomns of landscape symbols
        for(int i = 0; i < WIDTH; i++){
            //Makes a random height of a collumn
            int max = generator.nextInt(HEIGHT-(HEIGHT/3));
            for(int y = 0; y < HEIGHT; y++){
                if (y < max){
                    landscape[i][(HEIGHT - 1) - y] = LANDSCAPE_SYMBOLS.substring(0, 1); ;
                }
                else{
                    landscape[i][(HEIGHT - 1) - y] = " ";
                }
            }
        }
        //Makes the border around the field
        for(int i = 0; i<WIDTH; i++){
            for(int y = 0; y < HEIGHT; y++){
                if (i == 0)landscape[i][y] = LANDSCAPE_SYMBOLS.substring(0, 1);
                if (i == (WIDTH - 1)) landscape[i][y] = LANDSCAPE_SYMBOLS.substring(0, 1);
                if (y == 0)landscape[i][y] = LANDSCAPE_SYMBOLS.substring(0, 1);
                if (y == (HEIGHT - 1)) landscape[i][y] = LANDSCAPE_SYMBOLS.substring(0, 1);
            }
        }

        // places the player1 symbol in the field
        int player1 = generator.nextInt(WIDTH - 1) + 1;
        int y = (HEIGHT - 1);
        while(landscape[player1][y] != " "){
            y--;
        }
        landscape[player1][y] = "A";
        players.add(new Tank(player1, HEIGHT - y, "A"));

        //places the player2 symbol in the field
        int player2 = generator.nextInt(WIDTH - 1) + 1;
        y = (HEIGHT - 1);
        while(landscape[player2][y] != " "){
            y--;
        }
        landscape[player2][y] = "B";
        players.add(new Tank(player2, HEIGHT - y, "B"));

    }

    public void readFileDimensions(String filename) {
        int lineNumber = 0; // Start at 0 to make the array happy
        int width = 0;
        File file = new File(filename);
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                width = line.length()>width?line.length():width;
                lineNumber++; // Increment the line after we are done sticking this line in the array
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        WIDTH = width;
        HEIGHT = lineNumber;

    }
    public void calculateFormulaArray(int currentPlayer) {
        int formula = 0;
        boolean outOfBounds = true;
        //wind hier toevoegen
        tempLandscape.clear(); //0 = x, 1 = y
        Tank player = players.get(currentPlayer);
        int x = player.getXPosition();
        x = player.getAngle() < 90 ? x + 1 : x - 1;
        while(x < WIDTH && x > 0) {
            formula = player.calculateFormula(x);

            if ((formula < HEIGHT) && outOfBounds){
                tempLandscape.add(new Bullet(x, HEIGHT - formula));
            }
            else{ outOfBounds = false;}

            x = (player.getAngle() < 90)?x+1:x-1;

        }

    }

    public String[][] createTempArray(int currentplayer) {
        //Clone our landscape Important that we do this trough a special cloneArray function since
        //else we will clone the memory adress and then it will change the landscape array to.!!!
        tempShowLandscape = cloneArray(landscape);
        boolean noCollsion = true;
        for(Bullet bullet : tempLandscape) {
            //Check collision on the x and y value

            int check = checkCollision(bullet.getXcoord(), bullet.getYcoord(), currentplayer);
            if((check == 2)&&noCollsion) {
                //TANK HIT, increment the score of that tank by 1
                players.get(currentplayer).setScore(players.get(currentplayer).getScore() + 1);
                noCollsion = false;

            } else if((check == 1)&&noCollsion) {
                //LANDSCAPE HIT, set the landscape part on a space since we shoot it away.
                if((bullet.getXcoord() < (WIDTH-1)) && (bullet.getXcoord() > 0) &&
                        (bullet.getYcoord() < (HEIGHT-1)) && (bullet.getYcoord() > 0)) {
                    landscape[bullet.getXcoord()][bullet.getYcoord()] = " ";
                    tempShowLandscape[bullet.getXcoord()][bullet.getYcoord()] = BULLET_SYMBOL;
                }
                noCollsion = false;

            } else if (noCollsion) {
                //NOTHING HIT, Set bullet on x, y value of the landscape.
                if((bullet.getXcoord() < (WIDTH-1)) && (bullet.getXcoord() > 0) &&
                        (bullet.getYcoord() < (HEIGHT-1)) && (bullet.getYcoord() > 0)) {
                    tempShowLandscape[bullet.getXcoord()][bullet.getYcoord()] = BULLET_SYMBOL;
                }
            }

        }

        return tempShowLandscape;
    }

    private int checkCollision(int x, int y, int currentplayer) {
        //Get player symbols without the current player
        String tempPlayerSymbols = PLAYER_SYMBOLS.replaceAll(players.get(currentplayer).getSymbol().toLowerCase(), "").replaceAll(players.get(currentplayer).getSymbol().toUpperCase(), "");

        //Check for collision tanks
        for(int i = 0; i < tempPlayerSymbols.length(); i++) {
            //Check if we hit a tank that is DIFFERENT fom ourself
            if(tempPlayerSymbols.substring(i, (i + 1)).equalsIgnoreCase(landscape[x][y])) {
                //TANK HAS BEEN HIT
                return 2;
            }
        }

        //Check for collision landscape
        for(int i = 0; i < LANDSCAPE_SYMBOLS.length(); i++) {
            if(LANDSCAPE_SYMBOLS.substring(i, (i + 1)).equalsIgnoreCase(landscape[x][y])) {
                //LANDSCAPE HAS BEEN HIT
                return 1;
            }
        }

        //Nothing has been hit
        return 0;
    }

    public String[][] cloneArray(String[][] array) {
        String[][] copy = new String[array.length][];
        for (int i = 0; i < array.length; i++) {
            System.arraycopy(array[i], 0, copy[i] = new String[array[i].length], 0, array[i].length);
        }
        return copy;
    }

    public String chooseFile(int choice){
        switch(choice){
            case 1: return "landscape/1.txt";
            case 2: return "landscape/2.txt";
            case 3: return "landscape/3.txt";
            case 4: return "landscape/4.txt";
        }
        return "";
    }

    public String[][] getLandscape() {
        return landscape;
    }

    public String[][] getTempShowLandscape() {
        return tempShowLandscape;
    }

    public List<Tank> getPlayers() {
        return players;
    }


}
