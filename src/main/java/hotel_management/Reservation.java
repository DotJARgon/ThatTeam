package hotel_management;

import java.util.Date;

public class Reservation {
	private int id;
	private Date start, end;
    private Room[] rooms;
	private boolean checkedIn;
	private boolean checkedOut;

	//Custom constructor for object, where each object is determined by whoever
    //is instantiating the object

    public Reservation(int i, Date s, Date e, /*Billing b, */Room[] r, boolean chI, boolean chO) {
    	id = i;
    	start = s;
    	end = e;
    	rooms = r;
		checkedIn = chI;
		checkedOut = chO;
    }
	
	//Accessor function for start
	public Date getStart() {
    	return start;
    }
	//Mutator function for start
	public void setStart(Date s) {
		this.start = s;
	}

	//Accessor function for end
	public int getID() {
		return id;
	}
	//Mutator function for end
	public void setID(int id) {
		this.id = id;
	}
	
	//Accessor function for end
	public Date getEnd() {
		return end;
	}
	//Mutator function for end
	public void setEnd(Date end) {
		this.end = end;
	}

	//Accessor function for rooms
	public Room[] getRooms() {
		return rooms;
	}
	//Mutator function for rooms
	public void setRooms(Room[] rooms) {
		this.rooms = rooms;
	}

	public boolean getCheckedIn() {
		return checkedIn;
	}

	public void setCheckedIn(boolean checkedIn) {
		this.checkedIn = checkedIn;
	}

	public boolean getCheckedOut() {
		return checkedOut;
	}

	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}
}
