package org.sudoku;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.util.stream.Collectors;

@Log4j
public class Board extends Prototype {

    public static final int BOARD_SIZE = 9;
    public static final int SUBSECTION_SIZE = 3;
    private static final String BOARD_HORIZONTAL_HEADER = "\n\n    1    2    3    4    5    6    7    8    9\n";
    @Getter
    private Field fields[][] = new Field[BOARD_SIZE][BOARD_SIZE];

    public Board() {
        initBoard();
    }

    public void resetBoard() {
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[0].length; j++) {
                fields[i][j].setValue(Field.NO_VALUE);
                fields[i][j].resetAvailableValueList();
            }
        }
    }

    private void initBoard() {
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[0].length; j++) {
                fields[i][j] = new Field(i, j);
            }
        }
    }

    public boolean isBoardCompleted() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (fields[i][j].getValue() == Field.NO_VALUE) {
                    return false;
                }
            }
        }
        return true;
    }

    private Board shallowCopy() throws CloneNotSupportedException {
        return (Board) super.clone();
    }

    public Board deepCopy() throws CloneNotSupportedException {
        Board clonedBoard = shallowCopy();
        clonedBoard.fields = new Field[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int column = 0; column < Board.BOARD_SIZE; column++) {
                clonedBoard.fields[row][column] = new Field(row, column);
                clonedBoard.fields[row][column].setValue(fields[row][column].getValue());
                clonedBoard.fields[row][column].setAvailableValueList(fields[row][column].getAvailableValueList().stream().collect(Collectors.toList()));
                clonedBoard.fields[row][column].setCoordinatex(fields[row][column].getCoordinatex());
                clonedBoard.fields[row][column].setCoordinatey(fields[row][column].getCoordinatey());
            }
        }
        return clonedBoard;
    }

    @Override
    public String toString() {
        StringBuilder results = new StringBuilder();
        results.append(BOARD_HORIZONTAL_HEADER);
        for (int i = 0; i < fields.length; i++) {
            results.append('\n').append(i + 1).append("   ");
            for (int j = 0; j < fields[i].length; j++) {
                if (fields[i][j].getValue() == Field.NO_VALUE) {
                    results.append(fields[i][j].getValue()).append(" | ");
                } else {
                    results.append(fields[i][j].getValue()).append("  | ");
                }
            }
        }
        return results.toString();
    }
}
