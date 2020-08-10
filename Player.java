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

    public Player(Character name, int startX, int startY, char symbol) {
        characterCard = name;
        originalLocation = new Cell(startX, startY, symbol);
        this.symbol = symbol;
        xPos = startX;
        yPos = startY;
        playerNums.add(symbol);
    }

    /**
     * Returns whether the player has lost the game or not

     * @return true if they have lost the game, otherwise false
     */
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
        for (Card c : hand) {
            if (toCheck.contains(c.getName())) {
                matches.add(c);
            }
        }
        return matches;
    }

    /**
     * Returns the players character card

     * @return the character card of the player
     */
    public Character getCharacterCard() {
        return characterCard;
    }

    /**
     * Returns the players symbol

     * @return the symbol of the player
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Returns the players hand

     * @return the hand of the player
     */
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
    public Boolean move(String move, Cell[][] playerBoard, Set<Cell> spacesUsed) {
        int tempX = xPos;
        int tempY = yPos;
        if (move.equalsIgnoreCase("U")) {
            tempY -= 1;
        } else if (move.equalsIgnoreCase("D")) {
            tempY += 1;
        } else if (move.equalsIgnoreCase("L")) {
            tempX -= 1;
        } else if (move.equalsIgnoreCase("R")) {
            tempX += 1;
        } else {
            return false;
        }
        // Check if they are moving to a cell on the board
        if ((tempX >= 0 && tempX <= 23) && (tempY >= 0 && tempY <= 24)) {
            //Check they are moving to a valid cell
            if ((!playerBoard[tempX][tempY].isWall() && !playerNums.contains(playerBoard[tempX][tempY].getSymbol())
                    && !spacesUsed.contains(playerBoard[tempX][tempY]))) {
                xPos = tempX;
                yPos = tempY;
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the new co-ordinates of the player
     *
     * @param xPos
     * @param yPos
     */
    public void setCoords(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * Returns the x position of the player

     * @return the x position
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * Returns the y position of the player

     * @return the y position
     */
    public int getyPos() {
        return yPos;
    }

    /**
     * Adds a card to the players hand

     * @param card
     */
    public void addToHand(Card card) {
        hand.add(card);
    }

    /**
     * Set hasLost to true and move the player back to their original position
     */
    public void setHasLost() {
        hasLost = true;

        //move back to starting space
        xPos = originalLocation.getxCoord();
        yPos = originalLocation.getyCoord();
    }

}
