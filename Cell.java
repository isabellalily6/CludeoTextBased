public class Cell {
    private int xCoord;
    private int yCoord;
    private Boolean isRoom;
    private char symbol;

    public Cell(int x, int y, char symbol){
        xCoord = x;
        yCoord = y;
        this.symbol = symbol;
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
