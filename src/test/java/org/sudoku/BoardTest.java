package org.sudoku;

import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

@Log4j
public class BoardTest {

    @Test
    public void resetBoard() {
    }

    @Test
    public void isBoardCompleted() {
    }

    @Test
    public void deepCopy() throws CloneNotSupportedException {
        // Given
        Board boardDeepCopy;
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
        boardDeepCopy = board.deepCopy();
        board.getFields()[5][5].setValue(5);
        // Then
        Assert.assertEquals(1, boardDeepCopy.getFields()[coordinatex1][coordinatey1].getValue());
        Assert.assertEquals(1, boardDeepCopy.getFields()[coordinatex2][coordinatey2].getValue());
        Assert.assertEquals(0, boardDeepCopy.getFields()[coordinatex1][coordinatey1].getCoordinatex());
        Assert.assertEquals(3, boardDeepCopy.getFields()[coordinatex2][coordinatey2].getCoordinatey());
        Assert.assertEquals(-1,boardDeepCopy.getFields()[2][2].getValue());
        Assert.assertNotEquals(5,boardDeepCopy.getFields()[5][5].getValue());
        Assert.assertEquals(-1,boardDeepCopy.getFields()[5][5].getValue());
    }
}