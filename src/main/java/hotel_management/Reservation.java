package hotel_management;

import java.util.Date;

import billing_services.Billing;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "reservation")
public class Reservation {
	private int id;
	private Date start, end;
    private Billing billing;
    private int[] rooms;
	private boolean checkedIn;
	private boolean checkedOut;

	public Reservation() {}

	//Custom constructor for object, where each object is determined by whoever
    //is instantiating the object
    public Reservation(int i, Date s, Date e, Billing b, int[] r, boolean chI, boolean chO) {
    	this.id = i;
		this.start = s;
		this.end = e;
		this.billing = b;
		this.rooms = r;
		this.checkedIn = chI;
		this.checkedOut = chO;
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
	public int[] getRooms() {
		return rooms;
	}
	//Mutator function for rooms
	public void setRooms(int[] rooms) {
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
