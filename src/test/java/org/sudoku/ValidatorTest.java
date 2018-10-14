package org.sudoku;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;



/*                  Correct Board Sample To Test
                1    2    3    4    5    6    7    8    9

            1   2  | 8  | 3  | 9  | 1  | 5  | 6  | 4  | 7  |
            2   5  | 7  | 4  | -1 | -1 | -1 | -1 | -1 | -1 |
            3   6  | 1  | 9  | -1 | -1 | -1 | -1 | -1 | -1 |
            4   4  | -1 | -1 | -1 | -1 | -1 | -1 | -1 | -1 |
            5   3  | -1 | -1 | -1 | -1 | -1 | -1 | -1 | -1 |
            6   1  | -1 | -1 | -1 | -1 | -1 | -1 | -1 | -1 |
            7   7  | -1 | -1 | -1 | -1 | -1 | -1 | -1 | -1 |
            8   8  | -1 | -1 | -1 | -1 | -1 | -1 | -1 | -1 |
            9   9  | -1 | -1 | -1 | -1 | -1 | -1 | -1 | -1 |
*/


public class ValidatorTest {

    @Test
    public void validate() throws CloneNotSupportedException {
        // Given
        Board validBoard = new Board();
        Validator validator = new Validator();
        // Row Values
        validBoard.getFields()[0][0].setValue(2);
        validBoard.getFields()[0][1].setValue(8);
        validBoard.getFields()[0][2].setValue(3);
        validBoard.getFields()[0][3].setValue(9);
        validBoard.getFields()[0][4].setValue(1);
        validBoard.getFields()[0][5].setValue(5);
        validBoard.getFields()[0][6].setValue(6);
        validBoard.getFields()[0][7].setValue(4);
        validBoard.getFields()[0][8].setValue(7);
        // Column Values
        validBoard.getFields()[1][0].setValue(5);
        validBoard.getFields()[2][0].setValue(6);
        validBoard.getFields()[3][0].setValue(4);
        validBoard.getFields()[4][0].setValue(3);
        validBoard.getFields()[5][0].setValue(1);
        validBoard.getFields()[6][0].setValue(7);
        validBoard.getFields()[7][0].setValue(8);
        validBoard.getFields()[8][0].setValue(9);
        // Section Values
        validBoard.getFields()[1][1].setValue(7);
        validBoard.getFields()[1][2].setValue(4);
        validBoard.getFields()[2][1].setValue(1);
        validBoard.getFields()[2][2].setValue(9);
        Board invalidBoard = validBoard.deepCopy();
        // Set invalid Row
        invalidBoard.getFields()[1][1].setValue(9);
        // Count Row,Column,Section sum
        int columnSum = Arrays.stream(validBoard.getFields()).mapToInt(l -> l[0].getValue()).sum();
        int rowSum = Arrays.stream(validBoard.getFields()[0])
                .map(l -> l.getValue())
                .mapToInt(l -> l).sum();
        int sectionSum = IntStream.range(0, 3).map(l -> validBoard.getFields()[0][l].getValue()).sum() +
                IntStream.range(0, 3).map(l -> validBoard.getFields()[1][l].getValue()).sum() +
                IntStream.range(0, 3).map(l -> validBoard.getFields()[2][l].getValue()).sum();
        int invalidSectionSum = IntStream.range(0, 3).map(l -> invalidBoard.getFields()[0][l].getValue()).sum() +
                IntStream.range(0, 3).map(l -> invalidBoard.getFields()[1][l].getValue()).sum() +
                IntStream.range(0, 3).map(l -> invalidBoard.getFields()[2][l].getValue()).sum();

        // When
        boolean isValidBoard = validator.validate(validBoard);
        boolean isInvalidBoard = validator.validate(invalidBoard);

        // Then
        Assert.assertTrue(isValidBoard);
        Assert.assertTrue(!isInvalidBoard);
        Assert.assertEquals(45, rowSum);
        Assert.assertEquals(45, columnSum);
        Assert.assertEquals(45, sectionSum);
        Assert.assertNotEquals(45, invalidSectionSum);
    }


    @Test
    public void validateNewEntry() {

    }

    @Test
    public void validateNumber() {
        // Given
        char[] correctEntry = "123".toCharArray();
        char [] incorrectEntry1 = "1WWw".toCharArray();
        char [] incorrectEntry2 = "12W".toCharArray();
        // When
        boolean isCorrectEntry = Validator.validateNumber(correctEntry);
        boolean isIncorrectEntry1 = Validator.validateNumber(incorrectEntry1);
        boolean isIncorrectEntry2 = Validator.validateNumber(incorrectEntry2);
        // Then
        Assert.assertTrue(isCorrectEntry);
        Assert.assertFalse(isIncorrectEntry1);
        Assert.assertFalse(isIncorrectEntry2);
    }

    @Test
    public void checkUserEntry() {
        // Given
        String correctEntry = "123";
        String incorrectEntry1 = "1WWw";
        String incorrectEntry2 = "12W";
        // When
        boolean isCorrectEntry = Validator.checkUserEntry(correctEntry);
        boolean isIncorrectEntry1 = Validator.checkUserEntry(incorrectEntry1);
        boolean isIncorrectEntry2 = Validator.checkUserEntry(incorrectEntry2);
        // Then
        Assert.assertTrue(isCorrectEntry);
        Assert.assertFalse(isIncorrectEntry1);
        Assert.assertFalse(isIncorrectEntry2);
    }
}