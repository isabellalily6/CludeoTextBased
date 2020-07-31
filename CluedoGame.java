import java.util.*;

public class CluedoGame {
    private int minPlayers = 3;
    private int maxPlayers = 6;
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
        while(!gameOver) {
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

    public void playersTurn(Player player) {
        boolean asked = false;
        Set<Cell> spacesUsed = new HashSet<Cell>();
        if(ui.seeYourHand()){
            ui.viewHand(player.getHand());
        }
        int diceNum = rollDice();
        ui.displayDiceRoll(diceNum);
        while(diceNum > 0){
            spacesUsed.add(board.getCell(player.getxPos(), player.getyPos()));
            if(roomNames.containsKey(board.getCell(player.getxPos(), player.getyPos()).getSymbol()) && !asked){
                // player is in a room
                asked = true;
                if(!ui.continueMove()){
                    diceNum = 0;
                }
            }
            if(diceNum > 0) {
                ui.displayMovesLeft(diceNum);
                Boolean validMove = player.move(ui.getMoves(), board.getPlayerBoard(players), spacesUsed);
                while (!validMove) {
                    ui.invalidInput();
                    validMove = player.move(ui.getMoves(), board.getPlayerBoard(players), spacesUsed);
                }
                ui.drawWeapons(roomWeapons);
                board.drawBoard(players);
                diceNum -= 1;
            }
        }
        if(roomNames.containsKey(board.getCell(player.getxPos(), player.getyPos()).getSymbol())){
            String userInput = ui.checkSuggestion();
            // error checking
            while(!validSuggestion(userInput)){
                ui.invalidInput();
                userInput = ui.checkSuggestion();
            }
            if(userInput.equals("S")){
                suggestion(player, roomNames.get(board.getCell(player.getxPos(), player.getyPos()).getSymbol()));
                // ask if want to make accusation or not
                if(ui.shouldMakeAccusation()){
                    userInput = "A";
                }
            }if(userInput.equals("A")){
                accusation(player);
            }
        }
        ui.drawWeapons(roomWeapons);
        board.drawBoard(players);
    }
    
    // used for error checking
    public boolean validSuggestion(String input){
        if(input.equalsIgnoreCase("S") || input.equalsIgnoreCase("A") || input.equalsIgnoreCase("N")){
            return true;
        }
        return false;
    }

    public void gameOver(Player winningPlayer){
        if(winningPlayer!=null){
            ui.displayWinningPlayer(winningPlayer.getSymbol());
        }else{
            ui.displayWinningPlayer(' ');
        }
    }

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

    public void makePlayers(){
        players.add(new Player(new Character("Miss Scarlett"), 7, 24, '1', board));
        players.add(new Player(new Character("Colonel Mustard"), 0, 17, '2', board));
        players.add(new Player(new Character("Mrs. White"), 9, 0, '3', board));
        players.add(new Player(new Character("Mr. Green"), 14, 0, '4', board));
        players.add(new Player(new Character("Mrs. Peacock"), 23, 6, '5', board));
        players.add(new Player(new Character("Professor Plum"), 23, 19, '6', board));
    }

    public void decideMurder(){
        Iterator<Card> index = cards.iterator();
        Card chosenCard = index.next();

        while(!(chosenCard instanceof Weapon)){
            chosenCard = index.next();
        }
        Weapon weapon = (Weapon) chosenCard;
        cards.remove(weapon);

        index = cards.iterator();
        while(!(chosenCard instanceof Room)){
            chosenCard = index.next();
        }
        Room room = (Room) chosenCard;
        cards.remove(room);

        index = cards.iterator();
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

    public void moveWeapon(String room, String weapon){
        String oldRoom = null;
        for (Map.Entry<String,String> entry : roomWeapons.entrySet()){
            if(entry.getValue().equals(weapon)){
                oldRoom = entry.getKey();
            }
        }
        if(roomWeapons.containsKey(room)){
            roomWeapons.put(oldRoom, roomWeapons.get(room));
            roomWeapons.put(room, weapon);
        }else{
            roomWeapons.remove(oldRoom);
            roomWeapons.put(room, weapon);
        }
    }

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
        if((player.getSymbol()-'0') == numPlayers){
            num = 0;
        }else{
            num = player.getSymbol() - '0';
        }
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
            } else if(matches.size() > 1) {
                ui.chooseCard(p, matches);
                shownCard = true;
                break;
            }
        }
        if(!shownCard) {
            ui.noCardShown();
        }
    }

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

    public static void main(String[] args) {
        CluedoGame game = new CluedoGame();
        game.run();
    }

}
