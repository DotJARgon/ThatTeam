package hotel_management;

import file_utilities.CSVParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ReservationLoader {
    public enum Status {
        ACTIVE, INACTIVE
    }

    public static final String ACTIVE_FILE = "active.csv";
    public static final String INACTIVE_FILE = "inactive.csv";

    public static ConcurrentHashMap<Integer, Reservation> loadReservations(Status status) {
        ArrayList<String[]> allEntries = (status == Status.ACTIVE) ? CSVParser.loadCSV(ACTIVE_FILE) : CSVParser.loadCSV(INACTIVE_FILE);
        ConcurrentHashMap<Integer, Reservation> reservations = new ConcurrentHashMap<>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try{
            if(allEntries != null) {
                //id, startDate, endDate, billing, checkedIn, checkedOut, rooms
                for(String[] line : allEntries) {
                    int id = Integer.parseInt(line[0]);
                    Date startDate = formatter.parse(line[1]);
                    Date endDate = formatter.parse(line[2]);
                    boolean checkedIn = Boolean.parseBoolean(line[4]);
                    boolean checkedOut = Boolean.parseBoolean(line[5]);
                    int[] rooms = new int[line.length - 6];

                    for(int i = 0; i < rooms.length; i++) {
                        rooms[i] = Integer.parseInt(line[6 + i]);
                    }

                    Reservation reservation = new Reservation(id, startDate, endDate, null, rooms, checkedIn, checkedOut);

                    reservations.put(id, reservation);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return reservations;
    }

    public static void saveReservations(Set<Reservation> reservations, Status status) {
        ArrayList<Object[]> allReservations = new ArrayList<>();

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //id, startDate, endDate, billing, checkedIn, checkedOut, rooms
        for(Reservation reservation : reservations) {
            Object[] properties = new Object[6 + reservation.getRooms().length];
            properties[0] = reservation.getID();
            properties[1] = formatter.format(reservation.getStartDate());
            properties[2] = formatter.format(reservation.getEndDate());
            properties[3] = "null";
            properties[4] = reservation.getCheckedIn();
            properties[5] = reservation.getCheckedOut();

            for(int i = 0; i < reservation.getRooms().length; i++) {
                properties[i + 6] = reservation.getRooms()[i];
            }

            allReservations.add(properties);
            CSVParser.writeCSV((status == Status.ACTIVE) ? ACTIVE_FILE : INACTIVE_FILE, allReservations);
        }
    }


}
