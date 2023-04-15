package hotel_management;

import file_utilities.XMLList;
import file_utilities.XMLParser;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ReservationLoader {
    public static final String RESERVATION_FILE = "reservation.that.xml";

    public static ConcurrentHashMap<Integer, Reservation> loadReservations() {
        ConcurrentHashMap<Integer, Reservation> reservations = new ConcurrentHashMap<>();
        try {
            List<Reservation> res = XMLParser.load(RESERVATION_FILE, XMLList.class, Reservation.class);
            for(Reservation r : res) reservations.put(r.getID(), r);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            return reservations;
        }
    }

    public static void saveReservations(List<Reservation> reservations) {
        XMLParser.save(reservations, RESERVATION_FILE, XMLList.class, Reservation.class);
    }


}
