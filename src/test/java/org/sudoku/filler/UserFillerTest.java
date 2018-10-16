package org.sudoku.filler;

import javafx.beans.binding.When;
import org.junit.Assert;
import org.junit.Test;
import org.sudoku.Board;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class UserFillerTest {

    @Test
    public void fillSingleField() {
        //Given
        Board board = new Board();
        UserFiller userFiller = new UserFiller(board);
        String entry = "123";
        //When
        userFiller.fillSingleField(entry);
        int fieldValue = board.getFields()[0][1].getValue();
        //Then
        Assert.assertEquals(3, fieldValue);
    }

    @Test
    public void getField() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Given
        Board board = new Board();
        UserFiller userFiller = new UserFiller(board);
        String fillString = "123";
        Method getField = UserFiller.class.getDeclaredMethod("getField", String.class);
        getField.setAccessible(true);
        // When
        int[] output = (int[]) getField.invoke(userFiller, fillString);
        // Then
        Assert.assertEquals(3, output.length);
        Assert.assertEquals(Arrays.toString(new char[] {'1', '2', '3'}), Arrays.toString(output));
    }

    @Test
    public void convertCharArrayToIntArray() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Given
        Board board = new Board();
        UserFiller userFiller = new UserFiller(board);
        char [] fieldChar = new char[] {'1', '2', '3'};
        Method convertCharArrayToIntArray = UserFiller.class.getDeclaredMethod("convertCharArrayToIntArray", char[].class);
        convertCharArrayToIntArray.setAccessible(true);
        // When
        int[] output = (int[]) convertCharArrayToIntArray.invoke(userFiller, fieldChar);
        // Then
        Assert.assertEquals(3, output.length);
        Assert.assertEquals(Arrays.toString(new char[] {'1', '2', '3'}), Arrays.toString(output));
    }

}