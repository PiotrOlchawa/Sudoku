package org.sudoku;

import org.sudoku.filler.UserFiller;
import org.apache.log4j.BasicConfigurator;

public class GameRunner {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Board board = new Board();
        UserFiller boardUserFiller = new UserFiller(board);
        GameService gameService = new GameService(boardUserFiller);
        MenuDriver menuDriver = new MenuDriver(gameService);
        menuDriver.runGame();
    }
}
