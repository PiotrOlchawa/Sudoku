# Sudoku Resolver

The application solves the popular number puzzle - Sudoku [9x9]

Application has a text interface in which the game board is displayed using ASCII characters.

The user is asked to enters the coordinates of the place and the value that is to be found in the given place. When entering data, the game board is displayed each time.

When the user enters SUDOKU and presses the Enter key, the application asks you to select the resolver to solve Sudoku and display the solution.

Application have two solvers:
 
1.RandomResolver : This algorithm uses the random function to search for a solution.

2.KodillaResolver : This algorithm is more complex and uses eliminating procedure with copy and fail restoration to get solution.

## Getting Started

### Prerequisites

To run application you need gradle 4.9 and java 1.8.

### Installing

To build application run command from sudoku directory

```
gradle build
```

To run application please type 

```
gradle run --console=plain
```

Sample output should look similar to this:


Please input numbers,at (xyn) - where xy are position and n is value.
\
"sudoku" show game result.
(x) - exit (n) - new game

When you enter sudoku, you should get a solution.

## Running the tests

To run application test just run gradle test.

