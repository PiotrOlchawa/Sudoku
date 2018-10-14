package org.sudoku;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class MenuDriver {

    GameService gameService;

    void runGame() {
        Commander.showWelcome();
        while (true) {
            makeChoice();
        }
    }

    private void makeChoice() {
        switch (Commander.getGameAction()) {
            case 'x':
                exitSudoku();
                break;
            case 'n':
                startSudoku();
                break;
            default:
        }
    }

    private void startSudoku() {
        gameService.runThroughBoard();
    }

    private void exitSudoku() {
        Commander.showExit();
        System.exit(0);
    }
}

