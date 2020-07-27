import java.util.*;

public class CluedoGame {
    private int minPlayers = 3;
    private int maxPlayers = 6;
    private int numPlayers;
    private List<Card> cards = new ArrayList();
    private Suggestion murder;
    private java.util.ArrayList<Player> players = new ArrayList<>();
    private Board board;
    private Map<java.lang.Character, String> roomNames = new HashMap<>();
    private Map<Player, Cell> playersPosition;
    private ConsoleUI ui = new ConsoleUI();
    private Boolean gameOver = false;

    public CluedoGame(){
        board = new Board();
    }
    public void setUpCards() {
    }

    public void run() {
        numPlayers = ui.getNumPlayers();
        //• Miss Scarlett - 24, 7
        //• Colonel Mustard - 17, 0
        //• Mrs. White - 0, 9
        //• Mr. Green - 0, 14
        //• Mrs. Peacock - 6, 23
        //• Professor Plum - 19, 23
        players.add(new Player(new Character("Miss Scarlett"), 7, 24, '1', board));
        players.add(new Player(new Character("Colonel Mustard"), 0, 17, '2', board));
        players.add(new Player(new Character("Mrs. White"), 9, 0, '3', board));
        if(numPlayers > 3){
            players.add(new Player(new Character("Mr. Green"), 14, 0, '4', board));
        }
        if(numPlayers > 4){
            players.add(new Player(new Character("Mrs. Peacock"), 23, 6, '5', board));
        }
        if(numPlayers > 5){
            players.add(new Player(new Character("Professor Plum"), 23, 19, '6', board));
        }

        createCards();
        createRooms();
        decideMurder();
        dealCards();

        board.drawBoard(players);

        while(!gameOver) {
            for (Player player : players) {
                ui.displayPlayersTurn(player.getSymbol());
                playersTurn(player);
            }
        }

    }

    public void playersTurn(Player player) {
        int diceNum = rollDice();
        ui.displayDiceRoll(diceNum);
        while(diceNum != 0){
            ui.displayMovesLeft(diceNum);
            Boolean validMove = player.move(ui.getMoves());
            while(!validMove){
                ui.invalidInput();
                validMove = player.move(ui.getMoves());
            }
            if(roomNames.containsKey(board.getCell(player.getxPos(), player.getyPos()).getSymbol())){
                // player is in a room
                // ask if want to make S, A or N
                System.out.println("you're in a room");
                diceNum = 0;
                String userInput = ui.checkSuggestion();
                if(userInput.equals("S")){
                    // after ask if want to make accusation or not
                    suggestion(player, roomNames.get(board.getCell(player.getxPos(), player.getyPos()).getSymbol()));
                }else if(userInput.equals("A")){
                    accusation(player);
                }
            }
            board.drawBoard(players);
            diceNum -= 1;
        }
    }

    public void gameOver() {
    }

    public void createCards(){
        //create all the cards and add to list
        // Character cards
        cards.add(new Character("Mrs White"));
        cards.add(new Character("Mr Green"));
        cards.add(new Character("Mrs Peacock"));
        cards.add(new Character("Prof Plum"));
        cards.add(new Character("Miss Scarlett"));
        cards.add(new Character("Col Mustard"));

        // Weapon Cards
        cards.add(new Weapon("Candlestick"));
        cards.add(new Weapon("Dagger"));
        cards.add(new Weapon("Lead Pipe"));
        cards.add(new Weapon("Revolver"));
        cards.add(new Weapon("Rope"));
        cards.add(new Weapon("Spanner"));

        // Room Cards
        cards.add(new Room("Kitchen"));
        cards.add(new Room("Ball Room"));
        cards.add(new Room("Conservatory"));
        cards.add(new Room("Billiard Room"));
        cards.add(new Room("Library"));
        cards.add(new Room("Study"));
        cards.add(new Room("Hall"));
        cards.add(new Room("Lounge"));
        cards.add(new Room("Dining Room"));
    }

    public void createRooms(){
        roomNames.put('D', "Dining Room");
        roomNames.put('K', "Kitchen");
        roomNames.put('B', "Ball Room");
        roomNames.put('C', "Conservatory");
        roomNames.put('I', "Billiard Room");
        roomNames.put('L', "Library");
        roomNames.put('S', "Study");
        roomNames.put('H', "Hall");
        roomNames.put('O', "Lounge");
    }

    public void decideMurder(){

        int i = (int) (Math.random() * cards.size());
        while (!(cards.get(i) instanceof  Weapon)) {
            i = (int) (Math.random() * cards.size());
        }
        Weapon weapon = (Weapon) cards.get(i);
        cards.remove(weapon);

        while (!(cards.get(i) instanceof  Room)) {
            i = (int) (Math.random() * cards.size());
        }
        Room room = (Room) cards.get(i);
        cards.remove(room);

        while (!(cards.get(i) instanceof  Character)) {
            i = (int) (Math.random() * cards.size());
        }
        Character character = (Character) cards.get(i);
        cards.remove(character);

        murder = new Suggestion(character, room, weapon);
    }

    public int rollDice() {
        return (int) ((Math.random() * 5 + 1) + (Math.random() * 5 + 1));
    }

    public void dealCards() {
        while (!cards.isEmpty()) {
            for (Player player : players) {
                if (cards.size() > 0) {
                    player.addToHand(cards.get(0));
                    cards.remove(0);
                }
            }
        }
    }

    public void suggestion(Player player, String room){

    }

    public void accusation(Player player){

    }

    public static void main(String[] args) {
        CluedoGame game = new CluedoGame();
        game.run();
    }

}
