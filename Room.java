public class Room extends Card {
    private String name;

    public Room(String roomName){
        name = roomName;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof Room){
            if(((Room)object).getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}
