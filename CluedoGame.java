import java.util.*;

public class CluedoGame {
    private int numPlayers;
    private Set<Card> cards = new HashSet<>();
    private Suggestion murder;
    private java.util.ArrayList<Player> players = new ArrayList<>();
    private java.util.ArrayList<Player> currentPlayers = new ArrayList<>();
    private Map<String, String> roomWeapons = new HashMap<>();
    private Board board;
    private Map<java.lang.Character, String> roomNames = new HashMap<>();
    private ConsoleUI ui = new ConsoleUI();
    private Boolean gameOver = false;

    public CluedoGame(){
        board = new Board();
    }

    /**
     * Uses methods below to create everything needed to play the game (Cards, players etc)
     * then starts running/playing the game. Will continuously loop through the while loop
     * until game finishes.
     */
    public void run() {
        numPlayers = ui.getNumPlayers();
        // error checking range
        while(numPlayers < 3 || numPlayers > 6){
            ui.invalidInput();
            numPlayers = ui.getNumPlayers();
        }

        makePlayers();

        currentPlayers.add(players.get(0));
        currentPlayers.add(players.get(1));
        currentPlayers.add(players.get(2));
        if(numPlayers > 3){
            currentPlayers.add(players.get(3));
        }
        if(numPlayers > 4){
            currentPlayers.add(players.get(4));
        }
        if(numPlayers > 5){
            currentPlayers.add(players.get(5));
        }
        createCards();
        createRooms();
        addWeaponsToRooms();
        decideMurder();
        dealCards();

        ui.drawWeapons(roomWeapons);
        board.drawBoard(players);

        Player winningPlayer = null;
        while(!gameOver) {  // loops through stages 2-4 from section 2.6 'User Interface' from handout
            for (Player player : currentPlayers) {
                if(!player.getHasLost()){
                    ui.displayPlayersTurn(player.getSymbol());
                    playersTurn(player);
                }
                if(gameOver){
                    winningPlayer = player;
                    break;
                }
            }
        }
        if(checkGameOver() == null){
            gameOver(winningPlayer);
        }else{
            gameOver(checkGameOver());
        }

    }

    /**
     * Runs through a players turn including; dice roll, moves, suggestion etc
     * Will be used every time it's a new players turn
     * 
     * @param player
     */
    public void playersTurn(Player player) {
        boolean asked = false;
        Set<Cell> spacesUsed = new HashSet<Cell>();
        if(ui.seeYourHand()){   // will print out your hand if true
            ui.viewHand(player.getHand());
        }
        int diceNum = rollDice();
        ui.displayDiceRoll(diceNum);
        while(diceNum > 0){     //continue until no more moves left
            spacesUsed.add(board.getCell(player.getxPos(), player.getyPos()));
            if(roomNames.containsKey(board.getCell(player.getxPos(), player.getyPos()).getSymbol()) && !asked){
                // player is in a room
                asked = true;
                if(!ui.continueMove()){
                    //player doesn't want to use the rest of their move.
                    diceNum = 0;
                }
            }
            if(diceNum > 0) {
                ui.displayMovesLeft(diceNum);
                Boolean validMove = player.move(ui.getMoves(), board.getPlayerBoard(players), spacesUsed);
                // error checking
                while (!validMove) {
                    ui.invalidInput();
                    validMove = player.move(ui.getMoves(), board.getPlayerBoard(players), spacesUsed);
                }
                ui.drawWeapons(roomWeapons);
                board.drawBoard(players);
                diceNum -= 1;
            }
        }
         // if they are inside when they have no more moves they can make a suggestion
        if(roomNames.containsKey(board.getCell(player.getxPos(), player.getyPos()).getSymbol())){
            String userInput = ui.checkSuggestion();
            // error checking
            while(!validSuggestion(userInput)){
                ui.invalidInput();
                userInput = ui.checkSuggestion();
            }
            if(userInput.equalsIgnoreCase("S")){
                suggestion(player, roomNames.get(board.getCell(player.getxPos(), player.getyPos()).getSymbol()));
                // ask if want to make accusation or not
                if(ui.shouldMakeAccusation()){
                    userInput = "A";
                }
            }if(userInput.equalsIgnoreCase("A")){
                accusation(player);
            }
        }
        ui.drawWeapons(roomWeapons);
        board.drawBoard(players);
    }
    
    /**
     * used for error checking
     */
    public boolean validSuggestion(String input){
        if(input.equalsIgnoreCase("S") || input.equalsIgnoreCase("A") || input.equalsIgnoreCase("N")){
            return true;
        }
        return false;
    }

    /**
     * Display that the game is over and who won the game
     *
     * @param winningPlayer
     */
    public void gameOver(Player winningPlayer){
        if(winningPlayer!=null){
            ui.displayWinningPlayer(winningPlayer.getSymbol());
        }else{
            ui.displayWinningPlayer(' ');
        }
    }

    /**
     * Create the character, weapon and room cards
     *
     */
    public void createCards(){
        //create all the cards and add to list
        // Character cards
        cards.add(new Character("Mrs. White"));
        cards.add(new Character("Mr. Green"));
        cards.add(new Character("Mrs. Peacock"));
        cards.add(new Character("Professor Plum"));
        cards.add(new Character("Miss Scarlett"));
        cards.add(new Character("Colonel Mustard"));

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

    /**
     * Add a weapon into a room, there can be no two weapon in the same room
     */
    public void addWeaponsToRooms(){
        ArrayList<Weapon> weapons = new ArrayList<>();
        for(Card card: cards){
            if(card instanceof Weapon){
                weapons.add((Weapon) card);
            }
        }

        for(Card card: cards){
            if(card instanceof Room){
                if(!weapons.isEmpty()){
                    Weapon weapon = weapons.get(0);
                    roomWeapons.put(((Room)card).getName(), weapon.getName());
                    weapons.remove(0);
                }
            }
        }
    }

    /**
     * Create rooms for displaying on the board, the key will be used to display
     */
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

    /**
     * Create player for displaying on the board, the symbol will be used to display
     * each player is a character
     */
    public void makePlayers(){
        players.add(new Player(new Character("Miss Scarlett"), 7, 24, '1'));
        players.add(new Player(new Character("Colonel Mustard"), 0, 17, '2'));
        players.add(new Player(new Character("Mrs. White"), 9, 0, '3'));
        players.add(new Player(new Character("Mr. Green"), 14, 0, '4'));
        players.add(new Player(new Character("Mrs. Peacock"), 23, 6, '5'));
        players.add(new Player(new Character("Professor Plum"), 23, 19, '6'));
    }

    /**
     * Creates a Suggestion object that contains the answer to the game created
     * once per game
     */
    public void decideMurder(){
        Iterator<Card> index = cards.iterator();
        Card chosenCard = index.next();

        // searches for a weapon card and removes it from the stack of cards
        while(!(chosenCard instanceof Weapon)){
            chosenCard = index.next();
        }
        Weapon weapon = (Weapon) chosenCard;
        cards.remove(weapon);

        index = cards.iterator();
        // searches for a room card and removes it from the stack of cards
        while(!(chosenCard instanceof Room)){
            chosenCard = index.next();
        }
        Room room = (Room) chosenCard;
        cards.remove(room);

        index = cards.iterator();
        // searches for a character card and removes it from the stack of cards
        while(!(chosenCard instanceof Character)){
            chosenCard = index.next();
        }
        Character character = (Character) chosenCard;
        cards.remove(character);

        murder = new Suggestion(character, room, weapon);
    }

    public int rollDice() {
        return (int) ((Math.random() * 5 + 1) + (Math.random() * 5 + 1));
    }

    /**
     * Evenly hands out the rest of the cards into the players hands. If odd amount 
     * some players will have one more card.
     */
    public void dealCards() {
        Iterator<Card> index = cards.iterator();

        while (index.hasNext()) {
            for (Player player : currentPlayers) {
                if (index.hasNext()) {
                    Card card = index.next();
                    player.addToHand(card);
                }
            }
        }
        cards.clear();
    }

    /**
     * If weapon from a different room is used in a suggestion then move it to the room that
     * the suggestion was made in, and if that room already has a weapon in it then swap rooms
     * with the weapon
     * 
     * @param room
     * @param weapon
     */
    public void moveWeapon(String room, String weapon){
        String oldRoom = null; // name of room weapon needed is in currently
        for (Map.Entry<String,String> entry : roomWeapons.entrySet()){
            if(entry.getValue().equals(weapon)){
                oldRoom = entry.getKey();
            }
        }
        if(roomWeapons.containsKey(room)){
            // swaps room
            roomWeapons.put(oldRoom, roomWeapons.get(room));
        }else{
            //if no weapon is in room
            roomWeapons.remove(oldRoom);
        }
        roomWeapons.put(room, weapon);
    }

    /**
     * Move a player to the first available top left cell of a specified room
     *
     * @param room
     * @param player
     */
    public void movePlayer(String room, String player){
        char letter = ' ';
        for (Map.Entry<java.lang.Character,String> entry : roomNames.entrySet()){
            if(entry.getValue().equals(room)){
                letter = entry.getKey();
                break;
            }
        }
        List<Integer> playerCoords = board.getRoom(letter, players);
        for(Player p: players){
            if(p.getCharacterCard().getName().equals(player)){
                if(board.getCell(p.getxPos(), p.getyPos()).getSymbol()!=letter) {
                    p.setCoords(playerCoords.get(0), playerCoords.get(1));
                }
            }
        }
    }

    /**
     * This class gets the players suggestion an controls the players suggestion.
     *
     * @param player
     * @param room
     */
    public void suggestion(Player player, String room){
        ArrayList<String> guess = ui.makeSuggesstion(room);

        // try make more efficient!!! Map in UI
        moveWeapon(room, guess.get(1));
        movePlayer(room, guess.get(0));

        ui.drawWeapons(roomWeapons);
        board.drawBoard(players);

        guess.add(room);

        boolean shownCard = false;
        int num = 0;
        if((player.getSymbol()-'0') == numPlayers){ // if last player go back to the beginning
            num = 0;
        }else{
            num = player.getSymbol() - '0';
        }
        // loop clockwise around other players and check their hands for any cards in suggestion
        for(int i = num; i != (int) (player.getSymbol()-'0')-1; i++) {
            if(i == numPlayers){
                i = 0;
            }
            Player p = currentPlayers.get(i);
            ArrayList<Card> matches = p.checkHand(guess);

            if(matches.size() == 1) {
                ui.displayCard(p, matches.get(0));
                shownCard = true;
                break;
            } else if(matches.size() > 1) { // make the user choose which card to show to the current player
                ui.chooseCard(p, matches);
                shownCard = true;
                break;
            }
        }
        if(!shownCard) {
            ui.noCardShown();
        }
    }

    /**
     * This class get the players accusation controls the players accusation.
     *
     * @param player
     */
    public void accusation(Player player){
        Suggestion sug = ui.makeAccusation();
        if(!sug.equals(murder)){
            ui.wrongAccusation();
            player.setHasLost();
            if(checkGameOver() !=null){
                gameOver = true;
            }
        } else {
            ui.correctAccusation();
            gameOver = true;
        }
    }

    /**
     * Returns the player which has won the game if the game is over
     *
     * @return either null is the game isn't over yet, or the winning player
     */
    public Player checkGameOver(){
        int count = 0;
        Player winningPlayer = null;
        for(Player player: currentPlayers){
            if(player.getHasLost()){
                count += 1;
            }else{
                winningPlayer = player;
            }
        }
        if(count == numPlayers - 1){
            return winningPlayer;
        }
        return null;
    }

    /**
     * Creates a new instance of the game Cluedo and runs the game
     *
     * @param args
     */
    public static void main(String[] args) {
        CluedoGame game = new CluedoGame();
        game.run();
    }

}
