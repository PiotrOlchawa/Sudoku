package org.sudoku.resolver;

//findAny findFirst roznice

import lombok.extern.log4j.Log4j;
import org.sudoku.Board;
import org.sudoku.Field;
import org.sudoku.Resolver;
import org.sudoku.filler.Filler;

@Log4j
public class KodillaResolver implements Resolver {

    private Filler boardFiller = new Filler();
    private Board board;

    public KodillaResolver(Board board) {
        this.board = board;
    }


    // suma el kolumna i wiersz

    @Override
    public Board resolve() {
        log.debug("Starting KodilaResolver");
        while (!board.isBoardCompleted()) {
            while (!processRowColumnSection()) {
                try {
                    boardFiller.fillField(this.board);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
               // processRowColumnSection();
            }
        }
        return board;
    }

    private boolean processRowColumnSection() {
        boolean result = false;
        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int column = 0; column < Board.BOARD_SIZE; column++) {
                Field field = board.getFields()[row][column];
                int fieldValue = board.getFields()[row][column].getValue();
                if (fieldValue == Field.NO_VALUE) {
                    result = result
                            || processRow(field, row)
                            || processColumn(field, column)
                            || processSection(field, row, column)
                            || processFieldForLastAvailiableValue(field);
                }
            }
        }
        return result;
    }


    private boolean processRow(Field field, int row) {
        boolean deleteOperation = false;
        for (int column = 0; column < Board.BOARD_SIZE; column++) {
            int value = board.getFields()[row][column].getValue();
            if (field.checkAvailiableValue(value)) {
                //delete value from other
                if (field.getAvailableValueList().size() > 1) {
                    field.deleteFromAvailableValueList(value);
                    deleteOperation = true;
                }
                // last value if is in other field --- recovery
                if (field.getAvailableValueList().size() == 1) {
                    for (int c = 0; c < Board.BOARD_SIZE; c++) {
                        if (field.getAvailableValueList().get(0) == board.getFields()[row][c].getValue()) {
                            log.debug("Row recovering");
                            this.board = boardFiller.recoverFromFillField();
                            return false;
                        }
                    }
                }
            }
        }
        boolean writeValue = true;
        for (int i = 0; i < field.getAvailableValueList().size(); i++) {
            int availableValue = field.getAvailableValueList().get(i);
            for (int column = 0; column < Board.BOARD_SIZE; column++) {
                Field checkedField = board.getFields()[row][column];
                if ((checkedField.getAvailableValueList().contains(availableValue)) || (checkedField.getValue() == availableValue)) {
                    writeValue = false;
                }
            }
            if (writeValue) {
                field.setValue(availableValue);
                log.info("processRow" + availableValue);
                return true;
            }
        }
        return deleteOperation;
    }

    private boolean processColumn(Field field, int column) {
        boolean deleteOperation = false;
        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            int value = board.getFields()[row][column].getValue();
            if (field.checkAvailiableValue(value)) {
                //delete value from other
                if (field.getAvailableValueList().size() > 1) {
                    field.deleteFromAvailableValueList(value);
                    deleteOperation = true;
                }
                // last value if is in other field --- recovery
                if (field.getAvailableValueList().size() == 1) {
                    for (int r = 0; r < Board.BOARD_SIZE; r++) {
                        if (field.getAvailableValueList().get(0) == board.getFields()[r][column].getValue()) {
                            log.debug("Column recovering");
                            this.board = boardFiller.recoverFromFillField();
                            return false;
                        }
                    }
                }
            }
        }

        boolean writeValue = true;
        for (int i = 0; i < field.getAvailableValueList().size(); i++) {
            int availableValue = field.getAvailableValueList().get(i);
            for (int row = 0; row < Board.BOARD_SIZE; row++) {
                Field checkedField = board.getFields()[row][column];
                if ((checkedField.getAvailableValueList().contains(availableValue)) || (checkedField.getValue() == availableValue)) {
                    writeValue = false;
                }
            }
            if (writeValue) {
                field.setValue(availableValue);
                log.info("processColumn" + availableValue);
                return true;
            }
        }
        return deleteOperation;
    }

    private boolean processSection(Field field, int row, int column) {
        int subsectionRowStart = (row / Board.SUBSECTION_SIZE) * Board.SUBSECTION_SIZE;
        int subsectionRowEnd = subsectionRowStart + Board.SUBSECTION_SIZE;
        int subsectionColumnStart = (column / Board.SUBSECTION_SIZE) * Board.SUBSECTION_SIZE;
        int subsectionColumnEnd = subsectionColumnStart + Board.SUBSECTION_SIZE;
        boolean deleteOperation = false;
        for (int r = subsectionRowStart; r < subsectionRowEnd; r++) {
            for (int c = subsectionColumnStart; c < subsectionColumnEnd; c++) {
                int value = board.getFields()[r][c].getValue();
                if (field.checkAvailiableValue(value)) {
                    // delete value from other
                    if (field.getAvailableValueList().size() > 1) {
                        field.deleteFromAvailableValueList(value);
                        deleteOperation = true;
                    }
                    // last value if is in other field --- recovery
                    if (field.getAvailableValueList().size() == 1) {
                        for (int rchk = subsectionRowStart; rchk < subsectionRowEnd; rchk++) {
                            for (int cchk = subsectionColumnStart; cchk < subsectionColumnEnd; cchk++) {
                                if (field.getAvailableValueList().get(0) == board.getFields()[rchk][cchk].getValue()) {
                                    log.debug("Section recovering");
                                    this.board = boardFiller.recoverFromFillField();

                                    return false;
                                }
                            }
                        }
                    }
                }
            }

            boolean writeValue = true;
            for (int i = 0; i < field.getAvailableValueList().size(); i++) {
                int availableValue = field.getAvailableValueList().get(i);
                for (int rr = subsectionRowStart; rr < subsectionRowEnd; rr++) {
                    for (int cc = subsectionColumnStart; cc < subsectionColumnEnd; cc++) {
                        Field checkedField = board.getFields()[row][column];
                        if ((board.getFields()[rr][cc].getAvailableValueList().contains(availableValue)) || (checkedField.getValue() == availableValue)) {
                            writeValue = false;
                        }
                    }
                }
                if (writeValue) {
                    field.setValue(availableValue);
                    log.info("processSection" + availableValue);
                    return true;
                }
            }
        }
        return deleteOperation;
    }

    private boolean processFieldForLastAvailiableValue(Field field) {
        if (field.getAvailableValueList().size() == 1) {
            log.debug("LastAvaliableValue " + field.getAvailableValueList().get(0) + "  at " + field.getCoordinatex() + " " + field.getCoordinatey());
            int lastValue = field.getAvailableValueList().get(0);
            field.setValue(lastValue);
            field.deleteFromAvailableValueList(lastValue);
            return true;
        }
        return false;
    }

}