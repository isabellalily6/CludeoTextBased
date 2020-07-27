import java.util.ArrayList;

public class Player {
    private java.util.ArrayList<Card> hand;
    private Character characterCard;
    private Boolean hasLost = false;
    private String playerName;
    private Cell location;
    private int xPos;
    private int yPos;
    private char symbol;
    private Board board;

    public Player(Character name, int startX, int startY, char symbol, Board board){
        characterCard = name;
        location = new Cell(startX, startY, symbol);
        this.symbol = symbol;
        xPos = startX;
        yPos = startY;
        this.board = board;
    }

    public ArrayList<Card> checkHand() {
        return null;
    }

    public Character getCharacterCard() {
        return characterCard;
    }

    public char getSymbol(){
        return symbol;
    }

    public Boolean move(String move){
        if(move.equals("U")){
            if(yPos != 0){
                if(!board.getCell(xPos, yPos-1).isWall()){
                    yPos -= 1;
                    return true;
                }
            }
        }else if(move.equals("D")){
            if(yPos != 24){
                if(!board.getCell(xPos, yPos+1).isWall()){
                    yPos += 1;
                    return true;
                }
            }
        }else if(move.equals("L")){
            if(xPos != 0){
                if(!board.getCell(xPos-1, yPos).isWall()){
                    xPos -= 1;
                    return true;
                }
            }
        }else if(move.equals("R")){
            if(xPos != 23){
                if(!board.getCell(xPos+1, yPos).isWall()){
                    xPos += 1;
                    return true;
                }
            }
        }
        return false;
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
}
