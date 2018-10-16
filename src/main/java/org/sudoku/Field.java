package org.sudoku;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class Field {

    public static final int NO_VALUE = -1;
    public static final int MAX_VALUE = 9;
    public static final int MIN_VALUE = 1;
    private List<Integer> availableValueList = new ArrayList<>();
    private int value = NO_VALUE;
    int coordinatex;
    int coordinatey;

    Field(int coordinatex, int coordinatey) {
        this.coordinatex = coordinatex;
        this.coordinatey = coordinatey;
        setDefaultAvailableValueList();
    }

    public boolean checkAvailiableValue(int value) {
        return availableValueList.contains(value);
    }

    public void deleteFromAvailableValueList(int value) {
        availableValueList.remove(Integer.valueOf(value));
    }

    public boolean hasNoValue() {
        return value == NO_VALUE;
    }

    void resetAvailableValueList() {
        availableValueList.clear();
        setDefaultAvailableValueList();
    }

    private void setDefaultAvailableValueList() {
        Integer[] valueList = new Integer[MAX_VALUE];
        for (int i = 0; i < MAX_VALUE; i++) {
            valueList[i] = i + 1;
        }
        this.availableValueList = new ArrayList<>(Arrays.asList(valueList));
    }

}
