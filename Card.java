// A card represents the weapons, characters and rooms that players have in their hand and make up a suggestion

public class Card {
    private String name;

    public String getName() {
        return name;
    }
    
    /**
     * Compares the name of each card to check if they are the same
     *
     * @param object 
     * @return true if they are the the same, false if they aren't
     */
    public boolean equals(Object object){
        if(object instanceof Card){
            Card card = (Card) object;
            if(card.getName().equals(this.name)){
                return true;
            }
        }
        return false;
    }
}
