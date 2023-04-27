package hotel_management;

import file_utilities.XMLList;
import file_utilities.XMLParser;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RoomLoader {
    public static final String ROOM_FILE = "rooms.that.xml";

    public static ConcurrentHashMap<Integer, Room> loadRooms() {
        ConcurrentHashMap<Integer, Room> rooms = new ConcurrentHashMap<>();
        try {
            List<Room> r = XMLParser.load(ROOM_FILE, XMLList.class, Room.class);
            for(Room room : r) {
                rooms.put(room.getID(), room);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            return rooms;
        }
    }

    public static void saveRooms(List<Room> rooms) {
        XMLParser.save(rooms, ROOM_FILE, XMLList.class, Room.class);
    }
}