package org.sudoku.filler;

import org.sudoku.Board;
import org.sudoku.Commander;
import org.sudoku.Validator;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
public class UserFiller {
    @Getter
    private Board board;

    public UserFiller(Board board) {
        this.board = board;
    }

    public void fillSingleField(String entry) {
        int[] field = getField(entry);
        try {
            Board boardDeepCopy = board.deepCopy();
            boardDeepCopy.getFields()[field[0] - 1][field[1] - 1].setValue(field[2]);
            if (!Validator.validateNewEntry((field[0] - 1), (field[1] - 1),boardDeepCopy)) {
                Commander.showValidationError();
            } else {
                board.getFields()[field[0] - 1][field[1] - 1].setValue(field[2]);
                log.debug("Entry Board " + board.toString() + "Entry is Valid " + (field[0] - 1) + (field[1] - 1));
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private static int[] getField(String entry) {
        char[] fieldChar = entry.toCharArray();
        return convertCharArrayToIntArray(fieldChar);
    }

    private static int[] convertCharArrayToIntArray(char[] fieldChar) {
        int[] fieldInt = new int[fieldChar.length];
        for (int i = 0; i < fieldChar.length; i++) {
            fieldInt[i] = Integer.parseInt(String.valueOf(fieldChar[i]));
        }
        return fieldInt;
    }
}
