package org.sudoku;

import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

@Log4j
public class BoardTest {

    @Test
    public void deepCopy() throws CloneNotSupportedException {
        // Given
        Board board = new Board();
        int fieldValue1 = 1;
        int fieldValue2 = 1;
        int coordinatex1 = 0;
        int coordinatey1 = 0;
        int coordinatex2 = 3;
        int coordinatey2 = 3;

        board.getFields()[coordinatex1][coordinatey1].setValue(fieldValue1);
        board.getFields()[coordinatex2][coordinatey2].setValue(fieldValue2);

        board.getFields()[coordinatex1][coordinatey1].setCoordinatex(coordinatex1);
        board.getFields()[coordinatex1][coordinatey1].setCoordinatey(coordinatey1);
        board.getFields()[coordinatex2][coordinatey2].setCoordinatex(coordinatex2);
        board.getFields()[coordinatex2][coordinatey2].setCoordinatey(coordinatey2);

        // When
        Board boardDeepCopy = board.deepCopy();
        int sumAllElementsboardDeepCopy = Arrays.stream(boardDeepCopy.getFields())
                .flatMap(l-> Arrays.stream(l))
                .map(l->l.getValue())
                .reduce(0, Integer::sum);
        int sumAllElementsboard = Arrays.stream(board.getFields())
                .flatMap(l-> Arrays.stream(l))
                .map(l->l.getValue())
                .reduce(0, Integer::sum);
        board.getFields()[5][5].setValue(5);
        // Then
        Assert.assertEquals(1, boardDeepCopy.getFields()[coordinatex1][coordinatey1].getValue());
        Assert.assertEquals(1, boardDeepCopy.getFields()[coordinatex2][coordinatey2].getValue());
        Assert.assertEquals(0, boardDeepCopy.getFields()[coordinatex1][coordinatey1].getCoordinatex());
        Assert.assertEquals(3, boardDeepCopy.getFields()[coordinatex2][coordinatey2].getCoordinatey());
        Assert.assertEquals(-1, boardDeepCopy.getFields()[2][2].getValue());
        Assert.assertNotEquals(5, boardDeepCopy.getFields()[5][5].getValue());
        Assert.assertEquals(-1, boardDeepCopy.getFields()[5][5].getValue());
        Assert.assertEquals(sumAllElementsboard,sumAllElementsboardDeepCopy);
    }

    @Test
    public void resetBoard() {
        // Given
        Board board = new Board();

        // When
        board.resetBoard();

        boolean defaulSizeAvaliableValueList = Arrays.stream(board.getFields())
                .flatMap(Arrays::stream)
                .map(l -> l.getAvailableValueList())
                .allMatch(l -> l.size() == 9);
        boolean defaultValue = Arrays.stream(board.getFields())
                .flatMap(Arrays::stream)
                .allMatch(l -> l.getValue() == Field.NO_VALUE);

        //Then
        Assert.assertTrue(defaulSizeAvaliableValueList);
        Assert.assertTrue(defaultValue);
    }

    @Test
    public void initBoard() {
        // Given
        Board board = new Board();

        // When
        Field field = board.getFields()[0][0];

        // Then
        Assert.assertNotNull(board.getFields()[0][0]);
        Assert.assertTrue(field instanceof Field);
    }

    @Test
    public void isBoardCompleted() throws CloneNotSupportedException {
        // Given
        Board boardFilled = new Board();
        Field [][] fields = boardFilled.getFields();

        // When
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[0].length; j++) {
                fields[i][j].setValue(5);
            }
        }
        Board boardNoFilled = boardFilled.deepCopy();
        boardNoFilled.getFields()[0][0].setValue(Field.NO_VALUE);
        boolean checkBoardFilled = boardFilled.isBoardCompleted();
        boolean checkBoardNoFilled = boardNoFilled.isBoardCompleted();

        // Then
        Assert.assertTrue(checkBoardFilled);
        Assert.assertFalse(checkBoardNoFilled);
    }
}