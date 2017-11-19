package castle;

import java.util.HashMap;

public class Room {
    private String description;

    private HashMap<String, Room> neighborRooms;

    public Room(String description) 
    {

        this.description = description;
        this.neighborRooms = new HashMap<String, Room>();
    }

    public void setExit(String key, Room room)
    {
        this.neighborRooms.put(key, room);
    }

    @Override
    public String toString()
    {
        return description;
    }

    public void printRoomDesc() {
        for (String key : neighborRooms.keySet()) {
            System.out.println(key);
        }
    }

    public void printExitInfo() {
        for (String key : neighborRooms.keySet()) {
            System.out.print(key+"  ");
        }
    }

    public Room gotoRoom(String direction) {
        return this.neighborRooms.get(direction);
    }
}
