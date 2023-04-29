package hotel_management;

import file_utilities.XMLList;
import file_utilities.XMLParser;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The RoomLoader class is responsible for storing and loading
 * Room objects into a xml file in the proper format
 * @author  Marcelo Carpenter
 * @version  1.6
 * @since 4/18/23
 */
public class RoomLoader {
    public static final String ROOM_FILE = "rooms.that.xml";

    /**
     * loadRooms will read a text file in xml format, creating a hashmap with all
     * the room objects and their unique id numbers.
     * @return returns a hashMap containing the rooms and their unique
     * id numbers corresponding to them
     */
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

    /**
     * saveRooms will use the XMLParser class in order to save all the
     * Rooms objects being passed into a xml file.
     * @param rooms the list of room objects being converted into a xml
     *                    file
     */
    public static void saveRooms(List<Room> rooms) {
        XMLParser.save(rooms, ROOM_FILE, XMLList.class, Room.class);
    }
}