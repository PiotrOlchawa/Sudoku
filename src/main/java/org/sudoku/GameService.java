package org.sudoku;

import lombok.AllArgsConstructor;
import org.sudoku.filler.UserFiller;

@AllArgsConstructor
public class GameService {

    private UserFiller userFiller;

    void runThroughBoard() {
        setBoardAtStart();
        while (true) {
            String entry = Commander.getUserEntry();
            if (checkEntryForResolveCommand(entry)) {
                resolveSudoku(selectResolver());
                break;
            }
            if (isEntryValid(entry)) {
                userFiller.fillSingleField(entry);
                showBoardAfterEntry(userFiller.getBoard());
            }
        }
    }

    private void setBoardAtStart(){
        userFiller.getBoard().resetBoard();
    }

    private boolean isEntryValid(String entry) {
        return Validator.checkUserEntry(entry);
    }

    private boolean checkEntryForResolveCommand(String entry) {
        return entry.equals(Commander.getSolutionCommand());
    }

    private void showBoardAfterEntry(Board board) {
        Commander.showBoardAfterEntry(board);
    }

    private void resolveSudoku(Resolver resolver) {
        Commander.getFinalSolution();
        Commander.showFinalValidation(resolver.resolve());
    }

    private Resolver selectResolver() {
        return Commander.getResolver(userFiller.getBoard());
    }
}
