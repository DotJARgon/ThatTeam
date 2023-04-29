package hotel_management;

import file_utilities.XMLList;
import file_utilities.XMLParser;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The ReservationLoader class is responsible for storing and loading
 * Reservation objects into a xml file in the proper format
 * @author  Marcelo Carpenter
 * @version  1.6
 * @since 4/18/23
 */
public class ReservationLoader {
    public static final String RESERVATION_FILE = "reservation.that.xml";

    /**
     * loadReservations will read a text file in xml format, creating a hashmap with all
     * the reservation objects and their unique id numbers.
     * @return returns a hashMap containing the reservations and their unique
     * id numbers corresponding to them
     */
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

    /**
     * saveReservations will use the XMLParser class in order to save all the
     * Reservation objects being passed into a xml file.
     * @param reservations the list of reservations objects being converted into a xml
     *                    file
     */
    public static void saveReservations(List<Reservation> reservations) {
        XMLParser.save(reservations, RESERVATION_FILE, XMLList.class, Reservation.class);
    }


}
