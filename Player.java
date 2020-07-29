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

    public Boolean move(String move, Cell[][] playerBoard){
        if(move.equals("U")){
            if(yPos != 0){
                if(!board.getCell(xPos, yPos-1).isWall() && !playerNums.contains(playerBoard[xPos][yPos-1].getSymbol())){
                    yPos -= 1;
                    return true;
                }
            }
        }else if(move.equals("D")){
            if(yPos != 24){
                if(!board.getCell(xPos, yPos+1).isWall() && !playerNums.contains(playerBoard[xPos][yPos+1].getSymbol())){
                    yPos += 1;
                    return true;
                }
            }
        }else if(move.equals("L")){
            if(xPos != 0){
                if(!board.getCell(xPos-1, yPos).isWall() && !playerNums.contains(playerBoard[xPos-1][yPos].getSymbol())){
                    xPos -= 1;
                    return true;
                }
            }
        }else if(move.equals("R")){
            if(xPos != 23){
                if(!board.getCell(xPos+1, yPos).isWall() && !playerNums.contains(playerBoard[xPos+1][yPos].getSymbol())){
                    xPos += 1;
                    return true;
                }
            }
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
