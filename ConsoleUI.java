import java.util.Scanner;

public class ConsoleUI {
    static Scanner input = new Scanner(System.in);
    public ConsoleUI(){

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
}
