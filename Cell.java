public class Cell {
    private int xCoord;
    private int yCoord;
    private char symbol;

    public Cell(int x, int y, char symbol){
        xCoord = x;
        yCoord = y;
        this.symbol = symbol;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public Boolean isWall(){
        if(symbol=='X'){
            return true;
        }
        return false;
    }

    public char getSymbol() {
        return symbol;
    }
}
