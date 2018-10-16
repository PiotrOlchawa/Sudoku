package org.sudoku;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.IntStream;

public class FieldTest {

    @Test
    public void resetAvailableValueList() {
        // Given
        Field field = new Field(0, 0);
        // When
        field.resetAvailableValueList();
        boolean doesFieldContainsAllAvaliableValues = IntStream.range(1, 10).allMatch(l -> field.getAvailableValueList().contains(l));
        int fieldSize = field.getAvailableValueList().size();
        // Then
        Assert.assertEquals(9,fieldSize);
        Assert.assertTrue(doesFieldContainsAllAvaliableValues);
    }

    @Test
    public void setDefaultAvailableValueList() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Given
        Field field = new Field(0, 0);
        Method setDefaultAvailableValueList = Field.class.getDeclaredMethod("setDefaultAvailableValueList");
        setDefaultAvailableValueList.setAccessible(true);
        // When
        setDefaultAvailableValueList.invoke(field);
        boolean doesFieldContainsAllAvaliableValues = IntStream.range(1, 10).allMatch(l -> field.getAvailableValueList().contains(l));
        int fieldSize = field.getAvailableValueList().size();
        // Then
        Assert.assertEquals(9,fieldSize);
        Assert.assertTrue(doesFieldContainsAllAvaliableValues);
    }
}