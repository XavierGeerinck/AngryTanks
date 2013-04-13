package com.kdg.angrytanks.View;

import java.util.List;
import java.util.Scanner;

import com.kdg.angrytanks.Model.Landscape;
import com.kdg.angrytanks.Model.Tank;

public class ConsoleView {
    private Scanner input;

    public ConsoleView() {
        input = new Scanner(System.in);
    }

    public void showMenu() {
        System.out.println("ConsoleView has been created.");
    }

    public String askName(String tankName) {
        System.out.print("Naam tank: " + tankName + " = ?");
        return input.next();
    }

    public void showStats(List<Tank> players, Landscape landscape) {
        //Show score, wind, current player
        String temp = "\n";

        for(Tank tank : players) {
            temp += String.format("Tank: %-15s Score: %5d X:%5d Y:%5d\n", tank.getName(), tank.getScore(), tank.getXPosition(), tank.getYPosition());
        }

        System.out.println(temp);
    }

    public void showPreviousMove(Tank player){
        System.out.println("Previous Turn");
        System.out.printf("Speed:\t %4.1f \t\t\tAngle:\t %4.1f\n", player.getSpeed(), player.getAngle());
        System.out.println("Wind: \t " + player.getWind());
    }

    public double askSpeed() {
        Scanner input = new Scanner(System.in);
        System.out.print("Speed = ?");
        return input.nextDouble();
    }

    public double askAngle() {
        Scanner input = new Scanner(System.in);
        System.out.print("Angle = ?");
        return input.nextDouble();
    }

    public void showCurrentPlayer(String playerName) {
        System.out.println("Current Player: " + playerName);
    }

    public void showWind(int wind) {
        String temp = "";

        if(wind < 0) {
            temp += "=====>";
        } else if(wind > 0) {
            temp += "<=====";
        } else {
            temp += "WindStil";
        }
        System.out.println("Wind = " + -wind + " Richting: " + temp);
    }

    public void showLandscape(String[][] landscapeString) {
        StringBuilder temp = new StringBuilder();
        for(int i = 0; i < landscapeString[0].length; i ++) {
            for(int j = 0; j < landscapeString.length; j ++) {
                temp.append(landscapeString[j][i]);

            }
            temp.append("\n");
        }
        System.out.println(temp.toString());
    }

    public void showWinner(Tank player){
        System.out.println("EINDE VAN HET SPEL!!!!!");
        System.out.println("Speler " + player.getName() + " met tank " + player.getSymbol() + " is gewonnen!!!");
    }

    public int askMapChoice(){
        int choice = 0;
        while ((choice != 1) && (choice !=2) && (choice != 3) && (choice !=4) && (choice != 5) && (choice !=6)){
            System.out.println("Welke map wenst u te spelen");
            System.out.println("1)King of the Hill");
            System.out.println("2)MyFortress");
            System.out.println("3)Take me");
            System.out.println("4)Corridor");
            System.out.println("5)Willekeurige map");
            System.out.println("6)Random Gegenereerde map");
            choice = input.nextInt();
        }
        return choice;
    }


    public boolean askMode(){
        int mode=0;
        while ((mode!=1) && (mode !=2)){
            System.out.println("Wilt u easy mode(geen wind) i.p.v normal mode(wind van -10 tot 10)");
            System.out.println("1)Yes");
            System.out.println("2)No");
            mode = input.nextInt();
        }
        if (mode == 1) return true;
        return false;
    }

    public static void intro(){
        System.out.println("Welkom bij Angry Tanks (door Xavier Geerinck & Thierry Van Loo!!!!!!!\n\n");
    }

    public int askRegame(){
        int playGame = 0;
        Scanner in = new Scanner(System.in);
        while ((playGame != 1) && (playGame != 2)){
            System.out.println("Wenst u Angry Tanks te spelen");
            System.out.println("1)Yes");
            System.out.println("2)No");
            playGame = in.nextInt();
        }
        return playGame;
    }

}
