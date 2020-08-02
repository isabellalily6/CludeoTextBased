import java.lang.reflect.Array;
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

    public int getNumPlayers() {
        System.out.print("Enter the number of players to play (between 3-6): ");
        int number =  Integer.parseInt(input.nextLine());
        return number;
    }

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

    public String checkSuggestion(){
        System.out.println("Do you want to make a suggestion (S), an Accusation (A) or neither (N)? ");
        System.out.println("Type S for suggestion, A for accusation and N for neither");
        String userInput = input.next();
        return userInput;
    }

    // asks user for their suggestion or accusation
    public ArrayList<String> getMurder(Boolean suggest){
        ArrayList<String> murder = new ArrayList<>();
        String room = null;
        String character = null;
        String weapon = null;

        if(!suggest) { //if accusation then ask user for a room, if its a suggestion then we do not need to ask
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

    public void createWeapons(){
        weaponNames.put("1", "Candlestick");
        weaponNames.put("2", "Dagger");
        weaponNames.put("3", "Lead Pipe");
        weaponNames.put("4", "Revolver");
        weaponNames.put("5", "Rope");
        weaponNames.put("6", "Spanner");
    }
    public void createCharacters(){
        characterNames.put("1", "Miss Scarlett");
        characterNames.put("2", "Colonel Mustard");
        characterNames.put("3", "Mrs. White");
        characterNames.put("4", "Mr. Green");
        characterNames.put("5", "Mrs. Peacock");
        characterNames.put("6", "Professor Plum");
    }
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

    public ArrayList<String> makeSuggesstion(String room){
        System.out.println("Pick a card from Character and Weapon types to make a suggestion");
        ArrayList<String> murder = getMurder(true);
        System.out.println("You suggestion is; Room: "+room+ "  Character: "+murder.get(0)+"  Weapon: "+murder.get(1));
        return murder;
    }

    
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

    public void viewHand(ArrayList<Card> hand){
        int i = 1;
        for(Card c : hand){
            System.out.println(i+". "+c.getName());
            i++;
        }
    }

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

    public void chooseCard(Player choosing, ArrayList<Card> chooseFrom) {
        System.out.println("\nPlayer " + choosing.getSymbol() + ", choose a card to show:");
        viewHand(chooseFrom);
        System.out.print("Number: ");
        String choose = input.next();
        int chosen = Integer.parseInt(choose) -1;
        // error checking
        while(chosen < 0 || chosen >= chooseFrom.size()){   // if out of bounds
            invalidInput();
            System.out.print("Number: ");
            choose = input.next();
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
