// A Cell is a single position on the board.

public class Cell {
    private int xCoord;
    private int yCoord;
    private char symbol; //Represents what the cell is (wall, room, etc.)

    public Cell(int x, int y, char symbol) {
        xCoord = x;
        yCoord = y;
        this.symbol = symbol;
    }

    /**
     * Get the x-coord of the cell
     *
     * @return the x-coord of the current cell
     */
    public int getxCoord() {
        return xCoord;
    }

    /**
     * Get the y-coord of the cell
     *
     * @return the y-coord of the current cell
     */
    public int getyCoord() {
        return yCoord;
    }

    /**
     * Check whether the cell is a wall
     *
     * @return true if the cell is a wall, otherwise false
     */
    public Boolean isWall() {
        if (symbol == 'X') {
            return true;
        }
        return false;
    }

    /**
     * Get the symbol of the cell
     *
     * @return the symbol of the cell
     */
    public char getSymbol() {
        return symbol;
    }
}
