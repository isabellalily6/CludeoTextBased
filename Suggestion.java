// A suggestion object stores a character, a room, and a weapon. It is used for the solution of the murder and when players make a suggestion of what the murder could be.

public class Suggestion {
    private Card character;
    private Card room;
    private Card weapon;

    public Suggestion(Room room, Character character, Weapon weapon) {
        this.character = character;
        this.room = room;
        this.weapon = weapon;
    }

    /**
     * Returns the character card
     *
     * @return character card
     */
    public Card getCharacter() {
        return character;
    }

    /**
     * Returns the room card
     *
     * @return room card
     */
    public Card getRoom() {
        return room;
    }

    /**
     * Returns the weapon card
     *
     * @return weapon card
     */
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
    public boolean equals(Object object) {
        if (object instanceof Suggestion) {
            Suggestion suggestion = (Suggestion) object;
            if (suggestion.getCharacter().getName().equals(this.character.getName()) &&
                    suggestion.getRoom().getName().equals(this.room.getName()) &&
                    suggestion.getWeapon().getName().equals(this.weapon.getName())) {
                return true;
            }
        }
        return false;
    }
}
