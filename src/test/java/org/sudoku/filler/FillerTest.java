package org.sudoku.filler;

import org.junit.Assert;
import org.junit.Test;
import org.sudoku.Board;
import org.sudoku.Field;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FillerTest {

    @Test
    public void fillField() throws NoSuchFieldException, CloneNotSupportedException, IllegalAccessException {
        // Given
        Board board = new Board();
        Filler filler = new Filler();
        java.lang.reflect.Field fillerEntitiesListField = Filler.class.getDeclaredField("fillerEntities");
        fillerEntitiesListField.setAccessible(true);
        // When
        filler.fillField(board);
        List<FillerEntity> fillerEntityList = (List<FillerEntity>) fillerEntitiesListField.get(filler);
        FillerEntity fillerEntity = fillerEntityList.get(0);
        int coordinatex = fillerEntity.getRowCoordinate();
        int coordinatey = fillerEntity.getColumnCoordinate();
        int value = fillerEntity.getFieldValue();
        int boardFilledFieldValue = board.getFields()[coordinatex][coordinatey].getValue();
        // Then
        Assert.assertEquals(value,boardFilledFieldValue);
    }

    @Test
    public void recoverFromFillField() throws CloneNotSupportedException {
        // Given
        Board board = new Board();
        board.resetBoard();
        Filler filler = new Filler();
        // When
        filler.fillField(board);
        Board recoveredBoard = filler.recoverFromFillField();
        boolean boardHasAlldefaultValues = Arrays.stream(recoveredBoard.getFields())
                .flatMap(Arrays::stream)
                .allMatch(l -> l.getValue() == Field.NO_VALUE);
        Assert.assertTrue(boardHasAlldefaultValues);
    }
}