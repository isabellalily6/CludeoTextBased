// The board is a 2D Array of cells. The game board that holds and displays information about everything on the board. 

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int WIDTH = 24;
    private int HEIGHT = 25;
    private Cell[][] board = new Cell[WIDTH][HEIGHT]; // storing each cell in position

    public Board() {
        makeBoard();
    }

    /**
     * Display the board
     *
     * @param players
     */
    public void drawBoard(ArrayList<Player> players) {
        Cell[][] tempBoard = getPlayerBoard(players);
        for (int y = 0; y < HEIGHT; y++) {
            System.out.println();
            for (int x = 0; x < WIDTH; x++) {
                //print the symbol indicating what is in the cell with a space in between
                System.out.print(tempBoard[x][y].getSymbol());
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    /**
     * Adds all players the player on the board in their current position
     *
     * @param players
     * @return 2D array of cells including where the players are
     */
    public Cell[][] getPlayerBoard(ArrayList<Player> players) {
        //store the current board in a new 2D array
        Cell[][] tempBoard = new Cell[WIDTH][HEIGHT];
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                tempBoard[x][y] = board[x][y];
            }
        }

        //add the players to the new board
        for (Player player : players) {
            Cell c = new Cell(player.getxPos(), player.getyPos(), player.getSymbol());
            tempBoard[player.getxPos()][player.getyPos()] = c;
        }
        return tempBoard;
    }

    /**
     * Returns the cell at the x and y co-ordinates passed in
     *
     * @param x
     * @param y
     * @return the Cell at the co-ordinates
     */
    public Cell getCell(int x, int y) {
        return board[x][y];
    }

    /**
     * Returns the top left position in a room
     *
     * @param letter  indicating the room
     * @param players
     * @return x and y value of top left cell in the relevant room
     */
    public List<Integer> getRoom(char letter, ArrayList<Player> players) {
        Cell[][] tempBoard = getPlayerBoard(players);
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (tempBoard[x][y].getSymbol() == letter) {
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(x);
                    list.add(y);
                    return list;
                }
            }
        }
        return null;
    }

    /**
     * Design and store the board layout
     */
    public void makeBoard() {
        String boardLayout =
                "XXXXXXXXXXXXXXXXXXXXXXXX" +
                        "XXXXXXX___XXXX___XXXXXXX" +
                        "XKKKKX__XXXBBXXX__XCCCCX" +
                        "XKKKKX__XBBBBBBX__XCCCCX" +
                        "XKKKKX__XBBBBBBX__CCCCCX" +
                        "XXKKKX__BBBBBBBB___XXXXX" +
                        "XXXXKX__XBBBBBBX_______X" +
                        "X_______XBXXXXBX_______X" +
                        "X_________________XXXXXX" +
                        "XXXXX_____________IIIIIX" +
                        "XDDDXXXX__XXXXX___XIIIIX" +
                        "XDDDDDDX__XXXXX___XIIIIX" +
                        "XDDDDDDD__XXXXX___XXXXIX" +
                        "XDDDDDDX__XXXXX________X" +
                        "XDDDDDDX__XXXXX___XXLXXX" +
                        "XXXXXXDX__XXXXX__XXLLLLX" +
                        "X_________XXXXX__LLLLLLX" +
                        "X________________XXLLLLX" +
                        "X________XXHHXX___XXXXXX" +
                        "XXXXXOX__XHHHHX________X" +
                        "XOOOOOX__XHHHHH________X" +
                        "XOOOOOX__XHHHHX__XSXXXXX" +
                        "XOOOOOX__XHHHHX__XSSSSSX" +
                        "XOOOOOX__XHHHHX__XSSSSSX" +
                        "XXXXXXXXXXXXXXXXXXXXXXXX";
        int count = 0;
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                char symbol = boardLayout.charAt(count);
                Cell newCell = new Cell(x, y, symbol);
                board[x][y] = newCell;
                count += 1;
            }
        }

    }
}
