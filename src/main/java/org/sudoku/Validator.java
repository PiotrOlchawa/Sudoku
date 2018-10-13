package org.sudoku;

import java.util.ArrayList;
import java.util.HashSet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor
@NoArgsConstructor
public class Validator {

    @Getter
    @Setter
    Board board;

    public static boolean validateNewEntry(int rowCoordinate, int columnCoordinate, Board board) {
        log.info("Inside validator " + board.toString());
        return validateOneRow(rowCoordinate, board) && validateOneColumn(columnCoordinate, board) && validateOneSection(rowCoordinate, columnCoordinate, board);
    }

    static boolean validateNumber(char[] fieldChar) {
        if (fieldChar.length != 3) {
            return false;
        }
        for (int i = 0; i < fieldChar.length; i++) {
            try {
                int value = Integer.parseInt(String.valueOf(fieldChar[i]));
                if (value < Field.MIN_VALUE || value > Field.MAX_VALUE) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    private static boolean validateOneRow(int row, Board board) {
        ArrayList<Integer> rowNumbers = new ArrayList<>();
        for (int column = 0; column < Board.BOARD_SIZE; column++) {
            int columnValue = board.getFields()[row][column].getValue();
            if (columnValue != Field.NO_VALUE) {
                rowNumbers.add(columnValue);
            }
        }
        HashSet<Integer> numberSet = new HashSet<>(rowNumbers);
        if (rowNumbers.size() > numberSet.size()) {
            log.debug("validateOneRow error " + rowNumbers + " " + numberSet + " at " + row);
            return false;
        }
        log.debug("validateOneRow true at row " + row);
        return true;
    }

    private static boolean validateOneColumn(int column, Board board) {
        ArrayList<Integer> columnNumbers = new ArrayList<>();
        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            int rowValue = board.getFields()[row][column].getValue();
            if (rowValue != Field.NO_VALUE) {
                columnNumbers.add(rowValue);
            }
        }
        HashSet<Integer> numberSet = new HashSet<>(columnNumbers);
        if (columnNumbers.size() > numberSet.size()) {
            log.debug("validateOneColumn error " + columnNumbers + " " + numberSet + " at " + column);
            return false;
        }
        log.debug("validateOneColumn true at column " + column);
        return true;
    }

    @SuppressWarnings("Duplicates")
    private static boolean validateOneSection(int row, int column, Board board) {
        ArrayList<Integer> sectionNumbers = new ArrayList<>();
        int subsectionRowStart = (row / Board.SUBSECTION_SIZE) * Board.SUBSECTION_SIZE;
        int subsectionRowEnd = subsectionRowStart + Board.SUBSECTION_SIZE;
        int subsectionColumnStart = (column / Board.SUBSECTION_SIZE) * Board.SUBSECTION_SIZE;
        int subsectionColumnEnd = subsectionColumnStart + Board.SUBSECTION_SIZE;
        for (int r = subsectionRowStart; r < subsectionRowEnd; r++) {
            for (int c = subsectionColumnStart; c < subsectionColumnEnd; c++) {
                int sectionValue = board.getFields()[r][c].getValue();
                if (sectionValue != Field.NO_VALUE) {
                    sectionNumbers.add(sectionValue);
                }
            }
        }
        HashSet<Integer> numberSet = new HashSet<>(sectionNumbers);
        if (sectionNumbers.size() > numberSet.size()) {
            log.debug("validateOneSection error " + sectionNumbers + " " + numberSet + " at " + subsectionRowStart + " " + subsectionColumnStart);
            return false;
        }
        log.debug("validateOneSection true at rol col " + row + column);
        return true;
    }

    boolean validate(Board board) {
        this.board = board;
        return validateRow() && validateColumn() && validateSection();
    }

    @SuppressWarnings("Duplicates")
    private boolean validateRow() {
        ArrayList<Integer> rowNumbers = new ArrayList<>();
        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            rowNumbers.clear();
            for (int column = 0; column < Board.BOARD_SIZE; column++) {
                int columnValue = board.getFields()[row][column].getValue();
                if (columnValue != Field.NO_VALUE) {
                    rowNumbers.add(columnValue);
                }
            }
            HashSet<Integer> numberSet = new HashSet<>(rowNumbers);
            if (rowNumbers.size() > numberSet.size()) {
                log.debug("validateRow error " + rowNumbers + " " + numberSet + " at " + row);
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("Duplicates")
    private boolean validateColumn() {
        ArrayList<Integer> columnNumbers = new ArrayList<>();
        for (int column = 0; column < Board.BOARD_SIZE; column++) {
            columnNumbers.clear();
            for (int row = 0; row < Board.BOARD_SIZE; row++) {
                int rowValue = board.getFields()[row][column].getValue();
                if (rowValue != Field.NO_VALUE) {
                    columnNumbers.add(rowValue);
                }
            }
            HashSet<Integer> numberSet = new HashSet<>(columnNumbers);
            if (columnNumbers.size() > numberSet.size()) {
                log.debug("validateColumn error " + columnNumbers + " " + numberSet + " at " + column);
                return false;
            }
        }
        return true;
    }

    private boolean validateSection() {
        ArrayList<Integer> sectionNumbers = new ArrayList<>();
        for (int row = 0; row < Board.BOARD_SIZE; row = row + Board.SUBSECTION_SIZE) {
            for (int column = 0; column < Board.BOARD_SIZE; column = column + Board.SUBSECTION_SIZE) {
                int subsectionRowStart = (row / Board.SUBSECTION_SIZE) * Board.SUBSECTION_SIZE;
                int subsectionRowEnd = subsectionRowStart + Board.SUBSECTION_SIZE;
                int subsectionColumnStart = (column / Board.SUBSECTION_SIZE) * Board.SUBSECTION_SIZE;
                int subsectionColumnEnd = subsectionColumnStart + Board.SUBSECTION_SIZE;
                sectionNumbers.clear();
                for (int r = subsectionRowStart; r < subsectionRowEnd; r++) {
                    for (int c = subsectionColumnStart; c < subsectionColumnEnd; c++) {
                        int sectionValue = board.getFields()[r][c].getValue();
                        if (sectionValue != Field.NO_VALUE) {
                            sectionNumbers.add(sectionValue);
                        }
                    }
                }
                HashSet<Integer> numberSet = new HashSet<>(sectionNumbers);
                if (sectionNumbers.size() > numberSet.size()) {
                    log.debug("validateSection error " + sectionNumbers + " " + numberSet + " at " + subsectionRowStart + " " + subsectionColumnStart);
                    return false;
                }
            }
        }
        return true;
    }

    static boolean checkUserEntry(String entry) {
        char[] fieldChar = entry.toCharArray();
        if (!Validator.validateNumber(fieldChar)) {
            Commander.badNumber();
            return false;
        }
        return true;
    }

}