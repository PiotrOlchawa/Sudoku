package org.sudoku.resolver;

import org.sudoku.Board;
import org.sudoku.Field;

import org.sudoku.Resolver;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.Arrays;
import java.util.Random;

@Log4j
@AllArgsConstructor
public class RandomResolver implements Resolver {

    private static Random RANDOM = new Random();
    Board board;

    @Override
    public Board resolve() {
        log.debug("Starting RandomResolver");
        while (!board.isBoardCompleted()) {
            board.resetBoard();
            for (int row = 0; row < Board.BOARD_SIZE; row++) {
                for (int column = 0; column < Board.BOARD_SIZE; column++) {
                    Field field = board.getFields()[row][column];
                    int fieldValue = board.getFields()[row][column].getValue();
                    if (fieldValue == Field.NO_VALUE) {
                        processRow(field, row);
                        processColumn(field, column);
                        processSection(field, row, column);
                        if (!setRandomValue(field)) {
                        }
                    } else {
                        if (field.getAvailableValueList().contains(fieldValue)) {
                            field.getAvailableValueList().removeAll(Arrays.asList(fieldValue));
                        }
                    }
                }
            }
        }
        return board;
    }

    private void processRow(Field field, int row) {
        for (int column = 0; column < Board.BOARD_SIZE; column++) {
            int value = board.getFields()[row][column].getValue();
            if (field.checkAvailiableValue(value)) {
                field.deleteFromAvailableValueList(value);
            }
        }
    }

    private void processColumn(Field field, int column) {
        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            int value = board.getFields()[row][column].getValue();
            if (field.checkAvailiableValue(value)) {
                field.deleteFromAvailableValueList(value);
            }
        }
    }

    private void processSection(Field field, int row, int column) {
        int subsectionRowStart = (row / Board.SUBSECTION_SIZE) * Board.SUBSECTION_SIZE;
        int subsectionRowEnd = subsectionRowStart + Board.SUBSECTION_SIZE;
        int subsectionColumnStart = (column / Board.SUBSECTION_SIZE) * Board.SUBSECTION_SIZE;
        int subsectionColumnEnd = subsectionColumnStart + Board.SUBSECTION_SIZE;
        for (int r = subsectionRowStart; r < subsectionRowEnd; r++) {
            for (int c = subsectionColumnStart; c < subsectionColumnEnd; c++) {
                int value = board.getFields()[r][c].getValue();
                if (field.checkAvailiableValue(value)) {
                    field.deleteFromAvailableValueList(value);
                }
            }
        }
    }

    private boolean setRandomValue(Field field) {
        if (field.getAvailableValueList().size() > 0) {
            int randomIndex = RANDOM.nextInt(field.getAvailableValueList().size());
            field.setValue(field.getAvailableValueList().get(randomIndex));
            field.getAvailableValueList().remove(randomIndex);
            return true;
        }
        return false;
    }
}

