package hotel_management;

import file_utilities.XMLList;
import file_utilities.XMLParser;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Vector;

public class RoomLoader {
    public static final String ROOM_FILE = "rooms.that.xml";

    public static Vector<Room> loadRooms() {
        Vector<Room> rooms = new Vector<>();
        try {
            List<Room> res = XMLParser.load(ROOM_FILE, XMLList.class, Room.class);
            rooms = new Vector<>(res);

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