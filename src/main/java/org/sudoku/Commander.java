package org.sudoku;

import org.sudoku.resolver.KodillaResolver;
import org.sudoku.resolver.RandomResolver;

import java.util.Scanner;

public class Commander {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String INPUT_NUMBER = "Please input number as xyn, where xy are position and n is value";
    private static final String EXITING = "Game ended....";
    private static final String SUDOKU_SOLUTION = "Sudoku Solution";
    private static final String ASK_FOR_GAME_ACTION = "(x) - exit (n) - new game";
    private static final String SUDOKU_RESOLVE_COMMAND = "sudoku";
    private static final String RESOLVER = "Choose resolver (1)Random (2)Kodilla";
    private static final String VALID_BOARD = "Board was finally validated and is VALID ! ";
    private static final String INVALID_BOARD = "Board was finally validated and is INVALID !";
    private static final String BAD_RESOLVER = "Input is not correct please input correct resolver";
    private static final String BAD_VALIDATION_ERROR = "Input data validation error !";
    private static final String BAD_GAME_ACTION = "Bad game action !";
    private static final String BAD_NUMBER = "Incorrect number, please enter valid number";
    private static final String WELCOME = "Please input numbers," +
            "at (xyn) - where xy are position and n is value.\n" +
            "\"sudoku\" show game result.";

    static char getGameAction() {
        askForGameAction();
        while (true) {
            String gameEndAction = SCANNER.nextLine();
            if (gameEndAction.length() == 0 || ((gameEndAction.charAt(0) != 'x' && gameEndAction.length() != 1)
                    || (gameEndAction.charAt(0) != 'n' && gameEndAction.length() != 1))
                    ) {
                badGameActionInput();
            } else {
                return gameEndAction.charAt(0);
            }
        }
    }

    static Resolver getResolver(Board board) {
        askForResolver();
        while (true) {
            try {
                int intResolverChoice = Integer.parseInt(SCANNER.nextLine());
                if (intResolverChoice < 1 || intResolverChoice > 2) {
                    badResolver();
                } else {
                    if (intResolverChoice == 1) {
                        return new RandomResolver(board);
                    } else {
                        return new KodillaResolver(board);
                    }
                }
            } catch (NumberFormatException e) {
                badResolver();
            }
        }
    }

    static void showFinalValidation(Board board) {
        if (new Validator(board).validate(board)) {
            System.out.println(VALID_BOARD);
            System.out.println(board.toString());
        } else {
            System.out.println(INVALID_BOARD);
            System.out.println(board.toString());
        }
    }

    public static void showValidationError() {
        System.out.println(BAD_VALIDATION_ERROR);
    }

    static String getUserEntry() {
        System.out.println(INPUT_NUMBER);
        return SCANNER.nextLine();
    }

    static String getSolutionCommand() {
        return SUDOKU_RESOLVE_COMMAND;
    }

    static void showWelcome() {
        System.out.println(WELCOME);
    }

    static void getFinalSolution() {
        System.out.println(SUDOKU_SOLUTION);
    }

    static void showExit() {
        System.out.println(EXITING);
    }

    private static void badResolver() {
        System.out.println(BAD_RESOLVER);
        System.out.println(RESOLVER);
    }

    static void badNumber() {
        System.out.println(BAD_NUMBER);
    }

    private static void askForGameAction() {
        System.out.println(ASK_FOR_GAME_ACTION);
    }

    private static void badGameActionInput() {
        System.out.println(BAD_GAME_ACTION);
    }

    private static void askForResolver() {
        System.out.println(RESOLVER);
    }
}
