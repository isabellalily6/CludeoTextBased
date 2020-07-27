public class Card {
    private String name;

    public String getName() {
        return name;
    }

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
