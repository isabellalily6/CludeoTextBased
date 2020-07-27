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
