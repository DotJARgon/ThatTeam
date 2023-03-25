package hotel_management;

import java.util.Date;
import billing_services.Billing;
import java.util.Vector;

public class Reservation {
    private int id;
    //TODO
    private Account guest;
    private Date startDate, endDate;
    private Billing billing;
    private Vector <Room> rooms = new Vector<>();
    
    //Custom constructor for object, where each object is determined by whoever
    //is instantiating the object
    public Reservation(int i, /*Guest g, */Date s, Date e, Billing b, Vector <Room> r) {
    	id = i;
    	//guest = g;
    	startDate = s;
    	endDate = e;
    	billing = b;
    	rooms = r;
    }
    
    //Accessor function for id
	public int getID() {
		return id;
	}
	//Mutator function for id
	public void setID(int id) {
		this.id = id;
	}
	
	//Accessor function for startDate
	public Date getStartDate() {
    	return startDate;
    }
	//Mutator function for startDate
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	//Accessor function for endDate
	public Date getEndDate() {
		return endDate;
	}
	//Mutator function for endDate
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	//Accessor function for billing
	public Billing getBilling() {
		return billing;
	}
	//Mutator function for billing
	public void setBilling(Billing billing) {
		this.billing = billing;
	}

	//Accessor function for rooms
	public Vector<Room> getRooms() {
		return rooms;
	}
	//Mutator function for rooms
	public void setRooms(Vector<Room> rooms) {
		this.rooms = rooms;
	}
    
    public int reserveRoom(Account x, Room y, Date z, Date a) {
    	guest = x;
        if (HotelManagement.checkRoomAvailability(y) == false) {
            return 1;
        }
        rooms.add(y);
        if (z.compareTo(a) > 0) { //check to see if startDate is after endDate
            return 1;
        }
        startDate = z;
        endDate = a;
    	return 0;
    }
} 
