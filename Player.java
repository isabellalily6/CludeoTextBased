// A player is what each human playing the game controls. It stores and manipulates their hand and their position on the board.

import java.util.*;

public class Player {
    private java.util.ArrayList<Card> hand = new ArrayList<>();
    private Character characterCard; // the character that the player is playing as
    private Boolean hasLost = false;
    private Cell originalLocation;
    private int xPos;
    private int yPos;
    private char symbol; //symbol to represent the player on the board
    private static Set<java.lang.Character> playerNums = new HashSet<java.lang.Character>();

    public Player(Character name, int startX, int startY, char symbol){
        characterCard = name;
        originalLocation = new Cell(startX, startY, symbol);
        this.symbol = symbol;
        xPos = startX;
        yPos = startY;
        playerNums.add(symbol);
    }

    public Boolean getHasLost() {
        return hasLost;
    }

    /**
     * Checks hand to see if this player has any cards from suggestion
     *
     * @param toCheck
     * @return ArrayList of Cards that match the cards in the suggestion
     */
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

    /**
     * Makes sure that the move is valid by first making sure the string is one of the four
     * valid inputs and then by making sure that they can move in that direction; not a wall,
     * not another player, not a space they have already used, and that they are not going
     * out of bounds
     *
     * @param move
     * @param playerBoard
     * @param spacesUsed
     * @return true if the player successfully moved
     */
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
        
        //Check they are moving to a valid cell
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

    /**
     * Set hasLost to true and move the player back to their original position
     */
    public void setHasLost(){
        hasLost = true;
        
        //move back to starting space
        xPos = originalLocation.getxCoord();
        yPos = originalLocation.getyCoord();
    }

}
