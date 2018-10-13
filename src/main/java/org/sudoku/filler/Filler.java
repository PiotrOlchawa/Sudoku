package org.sudoku.filler;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.sudoku.Board;
import org.sudoku.Field;

import lombok.extern.log4j.Log4j;

@Log4j
public class Filler {

    private static Random RANDOM = new Random();
    private List<FillerEntity> fillerEntities = new LinkedList<>();

    public boolean fillField(Board board) throws CloneNotSupportedException {
        int rowCoordinate, columnCoordinate;
        FillerEntity fillerEntity = new FillerEntity();
        fillerEntity.setBoard(board.deepCopy());
        do {
            rowCoordinate = randomCoordinateSelector();
            columnCoordinate = randomCoordinateSelector();
        } while (!fieldHasNoValue(rowCoordinate, columnCoordinate, board));

        log.debug("selected Field" + rowCoordinate +" " + columnCoordinate);
        Field field = board.getFields()[rowCoordinate][columnCoordinate];
        int size = field.getAvailableValueList().size();
        if (size==0){
            log.debug("Filling error");
            System.exit(1);
        }

        int randomlySelectedValue = randomAvailableValueSelector(size);
        int setValue = field.getAvailableValueList().get(randomlySelectedValue);
        log.debug("Filling at position " + rowCoordinate + columnCoordinate + " with value " + setValue);
        field.setValue(setValue);
        field.deleteFromAvailableValueList(setValue);
        fillerEntity.setRowCoordinate(rowCoordinate);
        fillerEntity.setColumnCoordinate(columnCoordinate);
        fillerEntity.setFieldValue(setValue);
        fillerEntities.add(fillerEntity);
        return true;
    }

    public Board recoverFromFillField()  {
        if (fillerEntities.size() != 0) {
            FillerEntity lastEntity = fillerEntities.get(fillerEntities.size() - 1);
            Board board = lastEntity.getBoard();
            int row = lastEntity.getRowCoordinate();
            int column = lastEntity.getColumnCoordinate();
            Field field = board.getFields()[row][column];
            field.deleteFromAvailableValueList(lastEntity.getFieldValue());
            fillerEntities.remove(fillerEntities.size() - 1);
            log.debug("Recovering.. at " + row + " " + column);
            log.debug("Board from lastEntity " + board.toString());


            try {
                return board.deepCopy();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return new Board();
    }

    private int randomCoordinateSelector() {
        return RANDOM.nextInt(Board.BOARD_SIZE);
    }

    private int randomAvailableValueSelector(int value) {
        return RANDOM.nextInt(value);
    }

    private boolean fieldHasNoValue(int row, int column,Board board) {
        return board.getFields()[row][column].hasNoValue();
    }
}
