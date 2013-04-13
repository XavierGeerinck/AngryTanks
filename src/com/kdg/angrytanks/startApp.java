package com.kdg.angrytanks;

import com.kdg.angrytanks.Model.GameMain;
import com.kdg.angrytanks.View.ConsoleView;

/**
 * Created by IntelliJ IDEA.
 * User: TVL
 * Date: 26/01/12
 * Time: 15:09
 * To change this template use File | Settings | File Templates.
 */
public class startApp {

    public static void main(String[] args) {
        ConsoleView.intro();
        new GameMain();
    }
}
