import java.util.*;

public class Player {
    private java.util.ArrayList<Card> hand = new ArrayList<>();
    private Character characterCard;
    private Boolean hasLost = false;
    private Cell originalLocation;
    private int xPos;
    private int yPos;
    private char symbol;
    private Board board;
    private static Set<java.lang.Character> playerNums = new HashSet();

    public Player(Character name, int startX, int startY, char symbol, Board board){
        characterCard = name;
        originalLocation = new Cell(startX, startY, symbol);
        this.symbol = symbol;
        xPos = startX;
        yPos = startY;
        this.board = board;
        playerNums.add(symbol);
    }

    public Boolean getHasLost() {
        return hasLost;
    }

    public ArrayList<Card> checkHand(ArrayList<String> toCheck) {
        ArrayList<Card> matches = new ArrayList<Card>();
        for(Card c : hand) {
            if(toCheck.contains(c.getName())) {
                matches.add(c);
            }
        }
        return matches;
    }

    public Character getCharacterCard() {
        return characterCard;
    }

    public char getSymbol(){
        return symbol;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Boolean move(String move, Cell[][] playerBoard, Set<Cell> spacesUsed){
        int tempX = xPos;
        int tempY = yPos;
        if(move.equalsIgnoreCase("U")){
            tempY -= 1;
        }else if(move.equalsIgnoreCase("D")){
            tempY += 1;
        }else if(move.equalsIgnoreCase("L")){
            tempX -= 1;
        }else if(move.equalsIgnoreCase("R")){
            tempX += 1;
        }else{
            return false;
        }
        if((!playerBoard[tempX][tempY].isWall() && !playerNums.contains(playerBoard[tempX][tempY].getSymbol()) &&
                (tempX <= 23 && tempX >= 0) && (tempY >= 0 && tempY<=24) && !spacesUsed.contains(playerBoard[tempX][tempY]))){
            xPos = tempX;
            yPos = tempY;
            return true;
        }
        return false;
    }

    public void setCoords(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void addToHand(Card card) {
        hand.add(card);
    }

    public void setHasLost(){
        hasLost = true;
        xPos = originalLocation.getxCoord();
        yPos = originalLocation.getyCoord();
    }

}
