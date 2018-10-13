package org.sudoku;

import org.sudoku.filler.UserFiller;
import lombok.AllArgsConstructor;

//wydielenie jako service
// readme.md
// osobny modul



@AllArgsConstructor
class MenuDriver {

    private UserFiller boardUserFiller;

    void startGame() {
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
        boardUserFiller.getBoard().resetBoard();
        while (true) {
            String entry = Commander.getUserEntry();
            if (entry.equals(Commander.getSolutionCommand())) {
                resolveSudoku(selectResolver());
                break;
            } else if (Validator.checkUserEntry(entry)) {
                boardUserFiller.fillSingleField(entry);
                System.out.println(boardUserFiller.getBoard().toString());
            }
        }
    }

    private void exitSudoku() {
        Commander.showExit();
        System.exit(0);
    }

    private void resolveSudoku(Resolver resolver) {
        Commander.getFinalSolution();
        Commander.showFinalValidation(resolver.resolve());
    }

    private Resolver selectResolver() {
        return Commander.getResolver(boardUserFiller.getBoard());
    }
}

