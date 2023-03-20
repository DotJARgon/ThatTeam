package hotel_management;
public class Room {
    private enum BedType {
        //todo
        TWIN,
        FULL,
        KING,
        QUEEN
    }
    private enum QualityType {
        EXECUTIVE,
        BUSINESS,
        COMFORT,
        ECONOMY
    }
    private int id;
    private int numBeds;
    private BedType bedType;
    private boolean canSmoke;
    private int qualityLevel;
    private QualityType qualityLevel;

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
    public int getBedType() {
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
    public int getQualityType() {
        return qualityLevel;
    }
    public void setQualityType(QualityType qualityLevel) {
        this.qualityLevel = qualityLevel;
    }
}
