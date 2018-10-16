package org.sudoku;

import org.sudoku.filler.UserFiller;
import org.apache.log4j.BasicConfigurator;

import java.util.*;
import java.util.stream.Collectors;

public class GameRunner {

    // readme.md

    public static void main(String[] args) {

        List<Adress> adresy = new ArrayList<>();

        adresy.add(new Adress("Tarnów", "Krakowsa"));
        adresy.add(new Adress("Tarnów", "Lwowska"));
        adresy.add(new Adress("Brzesko", "Głowackiego"));

        Map<String, List<Adress>> hashMap = adresy.stream().collect(
                Collectors.groupingBy(Adress::getCity, Collectors.toList()));

        Map<String, List<String>> hashMap1 = adresy.stream().collect(
                Collectors.groupingBy(Adress::getCity,
                        Collectors.mapping(Adress::getStreet, Collectors.toList())));
        System.out.println(hashMap1);


        BasicConfigurator.configure();
        Board board = new Board();
        UserFiller boardUserFiller = new UserFiller(board);
        GameService gameService = new GameService(boardUserFiller);
        MenuDriver menuDriver = new MenuDriver(gameService);
        menuDriver.runGame();

    }
}
