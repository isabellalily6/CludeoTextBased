import java.lang.reflect.Array;
import java.util.*;

public class ConsoleUI {
    private Map<java.lang.Character, String> weaponNames = new HashMap<>();
    private Map<java.lang.Character, String> roomNames = new HashMap<>();
    private Map<java.lang.Character, String> characterNames = new HashMap<>();
    static Scanner input = new Scanner(System.in);
    public ConsoleUI(){
        createRooms();
        createCharacters();
        createWeapons();
    }

    public int getNumPlayers() {
        System.out.print("Enter the number of players to play (between 3-6): ");
        // Error check
        int number =  Integer.parseInt(input.nextLine());
        return number;
    }

    public String getMoves(){
        System.out.println("Do you want to move UDLR? ");
        // Error Check!!!
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
        // error check here!!!
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
            for (char c : roomNames.keySet()) {
                System.out.println("Type " + c + " for " + roomNames.get(c));
            }
            // asks user for room
            System.out.println("Pick a room card: ");
            String roomInput = input.next();
            // error check here
            room = roomNames.get(roomInput);
            murder.add(room);
        }

        //prints out all character options
        for (char c : characterNames.keySet()){
            System.out.println("Type " + c + " for " + characterNames.get(c));
        }
        // asks user for character
        System.out.println("Pick a character card: ");
        String charInput = input.next();
        // error check here
        character = characterNames.get(charInput);
        murder.add(character);

        //prints out all weapon options
        for (char c : weaponNames.keySet()){
            System.out.println("Type " + c + " for " + weaponNames.get(c));
        }
        // asks user for weapon
        System.out.println("Pick a weapon card: ");
        String weaponInput = input.next();
        // error check here
        weapon = weaponNames.get(weaponInput);
        murder.add(weapon);

        return murder;
    }

    public void createWeapons(){
        weaponNames.put('1', "Candlestick");
        weaponNames.put('2', "Dagger");
        weaponNames.put('3', "Lead Pipe");
        weaponNames.put('4', "Revolver");
        weaponNames.put('5', "Rope");
        weaponNames.put('6', "Spanner");
    }
    public void createCharacters(){
        characterNames.put('1', "Miss Scarlett");
        characterNames.put('2', "Colonel Mustard");
        characterNames.put('3', "Mrs. White");
        characterNames.put('4', "Mr. Green");
        characterNames.put('5', "Mrs. Peacock");
        characterNames.put('6', "Professor Plum");
    }
    public void createRooms(){
        roomNames.put('1', "Dining Room");
        roomNames.put('2', "Kitchen");
        roomNames.put('3', "Ball Room");
        roomNames.put('4', "Conservatory");
        roomNames.put('5', "Billiard Room");
        roomNames.put('6', "Library");
        roomNames.put('7', "Study");
        roomNames.put('8', "Hall");
        roomNames.put('9', "Lounge");
    }
}
