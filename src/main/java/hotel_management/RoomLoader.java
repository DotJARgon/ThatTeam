package hotel_management;

import file_utilities.CSVParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.Vector;

public class RoomLoader {
    public static final String ROOM_FILE = "rooms.csv";

    public static Vector<Room> loadRooms() {
        ArrayList<String[]> allEntries = CSVParser.loadCSV(ROOM_FILE);
        Vector<Room> rooms = new Vector<>();
        if(allEntries != null) {
            //id, numBeds, bedType, canSmoke, qualityLevel
            for(String[] line : allEntries) {
                String id = line[0];
                String numBeds = line[1];
                String bedType = line[2];
                String canSmoke = line[3];
                String quality = line[4];

                Room room = new Room();

                room.setID(Integer.parseInt(id));
                room.setNumBeds(Integer.parseInt(numBeds));
                room.setBedType(Room.BedType.fromString(bedType));
                room.setCanSmoke(Boolean.parseBoolean(canSmoke));
                room.setQualityType(Room.QualityType.fromString(quality));

                rooms.add(room);
            }
        }

        return rooms;
    }

    public static void saveRooms(Set<Room> rooms) {
        ArrayList<Object[]> allRooms = new ArrayList<>();

        for(Room room : rooms) {
            Object[] properties = new Object[] {
                    room.getID(),
                    room.getNumBeds(),
                    room.getBedType(),
                    room.getCanSmoke(),
                    room.getQualityType()
            };
            allRooms.add(properties);
        }

        CSVParser.writeCSV(ROOM_FILE, allRooms);
    }
}