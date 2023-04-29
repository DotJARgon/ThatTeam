package hotel_management;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Vector;

/**
 * The Room class is responsible for holding all information
 * relating to a room which includes its bed type which can be
 * a twin, full, queen, or king-size bed, the room's quality type
 * which can be an executive, business, comfort, or economy level room,
 * the unique id for the room, the number of beds, whether a person
 * is allowed to smoke, and all unique reservation ids the room is
 * reserved in.
 * @author  Alexzander DeVries, Lizzie Nix, Bryant Huang
 * @version  1.6
 * @since 3/25/23
 */
@XmlRootElement(name = "room")
public class Room {
    public enum BedType {
        TWIN,
        FULL,
        KING,
        QUEEN;

        @Override
        public String toString() {
            return this.name();
        }
        public static BedType fromString(String bedType) {
            return BedType.valueOf(BedType.class, bedType);
        }
    }
    public enum QualityType {
        EXECUTIVE,
        BUSINESS,
        COMFORT,
        ECONOMY;

        @Override
        public String toString() {
            return this.name();
        }
        public static QualityType fromString(String qualityType) {
            return QualityType.valueOf(QualityType.class, qualityType);
        }
    }
    private int id;
    private int numBeds;
    private BedType bedType;
    private boolean canSmoke;
    private QualityType qualityLevel;
    private Vector<Integer> reservations = new Vector<>();

    /**
     * The default constructor for the room object
     */
    public Room() {}

    /**
     * A constructor for the room object
     * @param i the unique id of the room
     * @param b the number of beds of the room
     * @param bt the bed type of the beds in the room
     * @param s determines if the guest can smoke in the room
     * @param qt the quality type of the room
     */
    public Room(int i, int b, BedType bt, boolean s, QualityType qt) {
    	id = i;
    	numBeds = b;
    	bedType = bt;
    	canSmoke = s;
    	qualityLevel = qt;
    }

    /**
     * Update room modifies all appropriate attributes of
     * the room object
     * @param b the number of beds in the room
     * @param bt the bed type of the beds in the room
     * @param s determines if the guest can smoke in the room
     * @param qt the quality type of the room
     */
    public void updateRoom(int b, BedType bt, boolean s, QualityType qt) {
    	numBeds = b;
    	bedType = bt;
    	canSmoke = s;
    	qualityLevel = qt;
    }
    
    public Vector<Integer> getReservations() {
        return reservations;
    }

    /**
     * addReservation adds a reservation id to the room's vector of
     * reservations it has been reserved in.
     * @param r the unique id of the reservation the room was reserved in
     */
    public void addReservation(int r) {
        reservations.add(r);
    }
    public void setID(int id) {
    	this.id = id;
    }
    public int getID() {
        return id;
    }
    public int getNumBeds() {
        return numBeds;
    }
    public void setNumBeds(int numBeds) {
        this.numBeds = numBeds;
    }
    public BedType getBedType() {
        return bedType;
    }
    public void setBedType(BedType bedType) {
        this.bedType = bedType;
    }
    public boolean getCanSmoke() {
        return canSmoke;
    }
    public void setCanSmoke(boolean canSmoke) {
        this.canSmoke = canSmoke;
    }
    public QualityType getQualityType() {
        return qualityLevel;
    }
    public void setQualityType(QualityType qualityLevel) {
        this.qualityLevel = qualityLevel;
    }

    /**
     * cancelReservation removes the reservation id from the room's
     * vector if reservation ids it has been reserved in
     * @param resID the reservation id the room was reserved in
     */
    public void cancelReservation(Integer resID) {
		reservations.remove(resID);
	}
}
