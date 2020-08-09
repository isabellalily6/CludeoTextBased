// A suggestion object stores a character, a room, and a weapon. It is used for the solution of the murder and when players make a suggestion of what the murder could be.

public class Suggestion {
    private Card character;
    private Card room;
    private Card weapon;

    public Suggestion(Character character, Room room, Weapon weapon){
        this.character = character;
        this.room = room;
        this.weapon = weapon;
    }

    public Card getCharacter() {
        return character;
    }

    public Card getRoom() {
        return room;
    }

    public Card getWeapon() {
        return weapon;
    }
    
    /**
     * Compares 2 Suggestion objects to check if they are the same
     *
     * @param object
     * @return true if the character, the room, and the weapon of each Suggestion are the same, otherwise false
     */
    @Override
    public boolean equals(Object object){
        if(object instanceof Suggestion){
            Suggestion suggestion = (Suggestion) object;
            if(suggestion.getCharacter().equals(this.character) && suggestion.getRoom().equals(this.room)
                    && suggestion.getWeapon().equals(this.weapon)){
                return true;
            }
        }
        return false;
    }
}
