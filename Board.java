import java.util.ArrayList;

public class Board {
    private int WIDTH = 24;
    private int HEIGHT = 25;
    private Cell[][] board = new Cell[WIDTH][HEIGHT];

    public Board(){
        makeBoard();
    }

    public void drawBoard(ArrayList<Player> players) {
        Cell[][] tempBoard = new Cell[WIDTH][HEIGHT];
        for(int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                tempBoard[x][y] = board[x][y];
            }
        }
        for(Player player: players){
            Cell c = new Cell(player.getxPos(), player.getyPos(), player.getSymbol());
            tempBoard[player.getxPos()][player.getyPos()] = c;
        }
        for(int y = 0; y < HEIGHT; y++) {
            System.out.println();
            for (int x = 0; x < WIDTH; x++) {
                System.out.print(tempBoard[x][y].getSymbol());
                System.out.print(" ");
            }
        }
    }

    public Cell getCell(int x, int y) {
        return board[x][y];
    }

    public void makeBoard(){
        String boardLayout =
                "XXXXXXXXX_XXXX_XXXXXXXXX" +
                "XXXXXXX___XXXX___XXXXXXX" +
                "XKKKKX__XXXBBXXX__XCCCCX" +
                "XKKKKX__XBBBBBBX__XCCCCX" +
                "XKKKKX__XBBBBBBX__CCCCCX" +
                "XXKKKX__BBBBBBBB___XXXXX" +
                "XXXXKX__XBBBBBBX________" +
                "________XBXXXXBX_______X" +
                "X_________________XXXXXX" +
                "XXXXX_____________IIIIIX" +
                "XDDDXXXX__XXXXX___XIIIIX" +
                "XDDDDDDX__XXXXX___XIIIIX" +
                "XDDDDDDD__XXXXX___XXXXIX" +
                "XDDDDDDX__XXXXX________X" +
                "XDDDDDDX__XXXXX___XXLXXX" +
                "XXXXXXDX__XXXXX__XXLLLLX" +
                "X_________XXXXX__LLLLLLX" +
                "_________________XXLLLLX" +
                "X________XXHHXX___XXXXXX" +
                "XXXXXOX__XHHHHX_________" +
                "XOOOOOX__XHHHHH________X" +
                "XOOOOOX__XHHHHX__XSXXXXX" +
                "XOOOOOX__XHHHHX__XSSSSSX" +
                "XOOOOOX__XHHHHX__XSSSSSX" +
                "XXXXXXX_XXXXXXXX_XXXXXXX";
        int count = 0;
        for(int y = 0; y < HEIGHT; y++){
            for(int x = 0; x < WIDTH; x++) {
                char symbol = boardLayout.charAt(count);
                Cell newCell = new Cell(x, y, symbol);
                board[x][y] = newCell;
                count+=1;
            }
        }

    }
}
