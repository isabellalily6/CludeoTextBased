// Controls all output and input of the game

import java.util.*;

public class ConsoleUI {
    private Map<String, String> weaponNames = new HashMap<>();
    private Map<String, String> roomNames = new HashMap<>();
    private Map<String, String> characterNames = new HashMap<>();
    static Scanner input = new Scanner(System.in);
    
    public ConsoleUI(){
        createRooms();
        createCharacters();
        createWeapons();
    }

    /**
     * Asks for input of the number of people playing
     *
     * @return the number of people playing
     */
    public int getNumPlayers() {
        System.out.print("Enter the number of players to play (between 3-6): ");
        String number =  input.nextLine();
        while(!number.matches("[3-6]\\d*")){ //check valid input between 3 and 6
            invalidInput();
            System.out.print("Enter the number of players to play (between 3-6): ");
            number =  input.nextLine();
        }
        return Integer.parseInt(number);
    }

    /**
     * Asks for input of which direction to move player
     *
     * @return "U", "D", "L", "R" indicating up, down, left, or right
     */
    public String getMoves(){
        System.out.println("Do you want to move UDLR? ");
        String move = input.next();
        return move;
    }

    public void displayPlayersTurn(char player){
        System.out.println("\nIt is player " + player + "'s turn");
    }

    public void displayDiceRoll(int number){
        System.out.println("\nThe dice rolled: " + number);
    }

    public void displayMovesLeft(int movesLeft){
        System.out.println("\nYou have " + movesLeft + " moves");
    }

    public void invalidInput(){
        System.out.println("Invalid input, try again: ");
    }

    /**
     * Ask for input of if the player wants to make a suggestion or accusation or neither
     *
     * @return "S", "A", "N" indication suggestion, accusation or neither
     */
    public String checkSuggestion(){
        System.out.println("Do you want to make a suggestion (S), an Accusation (A) or neither (N)? ");
        System.out.println("Type S for suggestion, A for accusation and N for neither");
        String userInput = input.next();
        return userInput;
    }

    /**
     * Asks for input of suggestion or accusation
     *
     * @param suggest (true if suggestion, false if accusation)
     * @return ArrayList of Strings describing the 3 cards they chose
     */
    public ArrayList<String> getMurder(Boolean suggest){
        ArrayList<String> murder = new ArrayList<>();
        String room = null;
        String character = null;
        String weapon = null;

        if(!suggest) { //if accusation then ask user for a room, if it's a suggestion then we do not need to ask
            //prints out all room options
            for (String c : roomNames.keySet()) {
                System.out.println("Type " + c + " for " + roomNames.get(c));
            }
            
            // asks user for room
            System.out.println("Pick a room card: ");
            String roomInput = input.next();
            room = roomNames.get(roomInput);
            
            // error check here
            while(room == null){
                invalidInput();
                System.out.println("Pick a room card: ");
                roomInput = input.next();
                room = roomNames.get(roomInput);
            }
            murder.add(room);
        }

        //prints out all character options
        for (String c : characterNames.keySet()){
            System.out.println("Type " + c + " for " + characterNames.get(c));
        }
        
        // asks user for character
        System.out.println("Pick a character card: ");
        String charInput = input.next();
        character = characterNames.get(charInput);
        
        // error check here
        while(character == null){
            invalidInput();
            System.out.println("Pick a character card: ");
            charInput = input.next();
            character = characterNames.get(charInput);
        }
        murder.add(character);

        //prints out all weapon options
        for (String c : weaponNames.keySet()){
            System.out.println("Type " + c + " for " + weaponNames.get(c));
        }
        
        // asks user for weapon
        System.out.println("Pick a weapon card: ");
        String weaponInput = input.next();
        weapon = weaponNames.get(weaponInput);
        
        // error check here
        while(weapon == null){
            invalidInput();
            System.out.println("Pick a weapon card: ");
            weaponInput = input.next();
            weapon = weaponNames.get(weaponInput);
        }
        murder.add(weapon);

        return murder;
    }

    /**
     * Create and store the weapon names
     */
    public void createWeapons(){
        weaponNames.put("1", "Candlestick");
        weaponNames.put("2", "Dagger");
        weaponNames.put("3", "Lead Pipe");
        weaponNames.put("4", "Revolver");
        weaponNames.put("5", "Rope");
        weaponNames.put("6", "Spanner");
    }

    /**
     * Create and store the character names
     */
    public void createCharacters(){
        characterNames.put("1", "Miss Scarlett");
        characterNames.put("2", "Colonel Mustard");
        characterNames.put("3", "Mrs. White");
        characterNames.put("4", "Mr. Green");
        characterNames.put("5", "Mrs. Peacock");
        characterNames.put("6", "Professor Plum");
    }

    /**
     * Create and store the room names
     */
    public void createRooms(){
        roomNames.put("1", "Dining Room");
        roomNames.put("2", "Kitchen");
        roomNames.put("3", "Ball Room");
        roomNames.put("4", "Conservatory");
        roomNames.put("5", "Billiard Room");
        roomNames.put("6", "Library");
        roomNames.put("7", "Study");
        roomNames.put("8", "Hall");
        roomNames.put("9", "Lounge");
    }

    /**
     * Asks for input of weapon and character
     *
     * @param room
     * @return ArrayList of Strings describing the 2 cards they chose
     */
    public ArrayList<String> makeSuggesstion(String room){
        System.out.println("Pick a card from Character and Weapon types to make a suggestion");
        ArrayList<String> murder = getMurder(true);
        System.out.println("You suggestion is; Room: "+room+ "  Character: "+murder.get(0)+"  Weapon: "+murder.get(1));
        return murder;
    }

    /**
     * Asks for input of accusation
     *
     * @return Suggestion object containing the 3 cards they chose
     */  
    public Suggestion makeAccusation(){
        System.out.println("Pick a card from each type to make a accusation");
        ArrayList<String> murder = getMurder(false);
        Room sugRoom = new Room(murder.get(0));
        Character sugChar = new Character(murder.get(1));
        Weapon sugWeapon = new Weapon(murder.get(2));
        Suggestion sug = new Suggestion(sugChar, sugRoom, sugWeapon);
        System.out.println("Your accusation is; Room: "+sug.getRoom().getName()+ "  Character: "+sug.getCharacter().getName()+"  Weapon: "+sug.getWeapon().getName());
        return sug;
    }

    public void wrongAccusation(){
        System.out.println("Your accusation was wrong. You have lost!");
    }
    public void correctAccusation(){
        System.out.println("Your accusation was right. You have WON!");
    }

    /**
     * Asks for input of whether the player wants to see their hand
     *
     * @return true if they want to view their hand, otherwise false
     */
    public Boolean seeYourHand(){
        System.out.println("Do you want to view your hand? Y or N: ");
        String userInput = input.next();
        if(userInput.equalsIgnoreCase("Y")){
            return true;
        } else if (userInput.equalsIgnoreCase("N")) {
            return false;
        } else {    
            //error checking
            invalidInput();
            seeYourHand();
        }
        return true;
    }

    /**
     * Displays the hand
     *
     * @param hand
     */
    public void viewHand(ArrayList<Card> hand){
        int i = 1;
        for(Card c : hand){
            System.out.println(i+". "+c.getName());
            i++;
        }
    }

    /**
     * Asks for input of if whether they want to make an accusation
     *
     * @return true if they want to make an accusation, otherwise false
     */
    public boolean shouldMakeAccusation(){
        System.out.println("Do you want to make an Accusation? ");
        System.out.println("Enter Y or N");
        String userInput = input.next();
        if(userInput.equalsIgnoreCase("Y")){
            return true;
        }else if(userInput.equalsIgnoreCase("N")){
            return false;
        } else {    
            // error checking
            shouldMakeAccusation();
        }
        return true;
    }

    /**
     * Asks for input of whether they want to continue to move after entering a room
     *
     * @return true if they want to keep moving, otherwise false
     */
    public boolean continueMove(){
        System.out.println("You are in a room!!!");
        System.out.println("Do you want to use the rest of your moves? Y or N");
        String userInput = input.next();
        if(userInput.equalsIgnoreCase("Y")){
            return true;
        } else if(userInput.equalsIgnoreCase("N")){
            return false;
        } else {    
            // error checking
            continueMove();
        }
        return true;
    }

    public void displayCard(Player withCard, Card toShow) {
        System.out.println("\nPlayer " + withCard.getSymbol() + " showed you " + toShow.getName() + ".");
    }

    /**
     * Asks for input of which card to show and then display that card
     *
     * @param player choosing
     * @param chooseFrom
     */
    public void chooseCard(Player choosing, ArrayList<Card> chooseFrom) {
        System.out.println("\nPlayer " + choosing.getSymbol() + ", choose a card to show:");
        viewHand(chooseFrom);
        System.out.print("Number: ");
        String choose = input.next();

        while(!choose.matches("\\d+")){
            invalidInput();
            System.out.print("Number: ");
            choose =  input.nextLine();
        }

        int chosen = Integer.parseInt(choose) -1;
        // error checking
        while(chosen < 0 || chosen >= chooseFrom.size()){   // if out of bounds
            invalidInput();
            System.out.print("Number: ");
            choose =  input.nextLine();
            while(!choose.matches("\\d+")){
                invalidInput();
                System.out.print("Number: ");
                choose =  input.nextLine();
            }
            chosen = Integer.parseInt(choose) -1;
        }
        System.out.println("\nPlayer " + choosing.getSymbol() + " showed you " + chooseFrom.get(chosen).getName() + ".");
    }

    public void drawWeapons(Map<String, String> map){
        for(String room: map.keySet()){
            System.out.println("Room: " + room + " contains Weapon: " + map.get(room));
        }
    }

    public void displayWinningPlayer(char player){
        if(player==' '){
            System.out.println("You all lost the game");
        }else {
            System.out.println("CONGRATS Player " + player + "\nYou have won the game!!!");
        }
    }

    public void noCardShown() {
        System.out.println("\nNobody has those cards.");
    }
}
