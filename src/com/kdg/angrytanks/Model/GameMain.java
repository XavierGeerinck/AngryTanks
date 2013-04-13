package com.kdg.angrytanks.Model;

import com.kdg.angrytanks.View.ConsoleView;


public class GameMain {
    private Tank tank;
    private Landscape landscape;

    private ConsoleView consoleview;


    private int currentPlayer;
    private Tank player;

    //Enum for the game state
    private static enum GameState {
        INITIALIZED, STARTED, PLAYING, GAMEOVER
    }

    private static GameState gameState;

    //Constructor
    public GameMain() {
        //Init the game
        gameInit();
    }

    //Init the game
    public void gameInit() {
        //Mostly we declare our object that we need for each player here.
        consoleview = new ConsoleView();
        landscape = new Landscape(consoleview.askMapChoice());


        currentPlayer = 0;

        //After initializing we set the sate on initialized
        gameState = GameState.INITIALIZED;
        //After everything we start the game
        gameStart();
    }

    //Start the game
    public void gameStart() {
        //First get the info of what they want to play, difficulty, ...
        boolean mode = consoleview.askMode();
        for(Tank tank:landscape.getPlayers()) {
            tank.setName(consoleview.askName(tank.getSymbol()));
            tank.setMode(mode);
        }


        //Then make a gameloop
        Thread gameThread = new Thread() {
            //Main override of the run()
            @Override
            public void run() {
                gameLoop();
            }
        };
        //After creating the thread we start it
        gameThread.start();

        gameState = GameState.STARTED;
    }

    //Loop
    private void gameLoop() {
        gameState = GameState.PLAYING;

        while(gameState == GameState.PLAYING) {
            //First check if it's game over or not

            /*
             * Ok the thing of this is that we first calculate everything that
             * we need to calculate, for example where is our tank?
             * where is it shooting to?
             * who is shooting to who?
             */
            player = landscape.getPlayers().get(currentPlayer);
            //Create wind

            //Show score, wind, current player
            consoleview.showStats(landscape.getPlayers(), landscape);

            //Show map
            consoleview.showPreviousMove(player);
            consoleview.showLandscape(landscape.getLandscape());
            player.createWind();
            //Show current player
            consoleview.showCurrentPlayer(landscape.getPlayers().get(currentPlayer).getName());

            //Show the Wind Speed
            consoleview.showWind(player.getWind());

            //Ask angle, speed
            player.setAngle(consoleview.askAngle());
            player.setSpeed(consoleview.askSpeed());

            landscape.calculateFormulaArray(currentPlayer);

            //After all those things we update the game and check on collision
            //and change the game state if needed
            gameUpdate();


            //Redraw the display later on + fps modifying
        }
        if (gameState == GameState.GAMEOVER){
            if (consoleview.askRegame() == 1){
                gameInit();
            }
        }
    }

    private void gameUpdate() {
        //Check the collision, this function will check for collisions and set the right array + say if we got a tank or a piece of the landscape
        landscape.createTempArray(currentPlayer); // Should check collision of our tank it's bullet

        consoleview.showLandscape(landscape.getTempShowLandscape());
        stopGame();
        //Make sure we increment the player if we still got players left else reset the counter.

        //Size - 1 because else we get the array size and then we go till index 2 instead of 1 (array starts at 0)
        if(currentPlayer < (landscape.getPlayers().size() - 1)) {
            currentPlayer++;
        } else {
            currentPlayer = 0;
        }
    }

    //Stop the game
    public void stopGame() {
        if(player.getScore() == 3){
            gameState = GameState.GAMEOVER;
            consoleview.showWinner(player);
        }

    }


}