package hotel_management;

import java.util.Vector;

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
    private Vector<Reservation> reservations;
    public Vector<Reservation> getReservations() {
        return reservations;
    }
    public void addReservation(Reservation r) {
        reservations.add(r);
    }
    public int getID() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
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
}
