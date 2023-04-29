package hotel_management;

import java.util.Date;

import billing_services.Billing;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "reservation")
public class Reservation {
	private int id;
	private Date start, end, reserved;
    private Billing billing;
    private int[] rooms;
	private boolean checkedIn;
	private boolean checkedOut;

	public Reservation() {}

	//Custom constructor for object, where each object is determined by whoever
    //is instantiating the object
    public Reservation(int i, Date s, Date e, Billing b, int[] r, boolean chI, boolean chO) {
    	this.id = i;
    	this.reserved = new Date(); //automatically to the current date
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

	public void setBilling(Billing billing) {
		this.billing = billing;
	}

	//Mutator function for end
	public void setEnd(Date end) {
		this.end = end;
	}

	//Accessor function for billing
	public Billing getBilling() {
		return billing;
	}
	//Mutator function for billing
	public void setRooms(Billing billing) {
		this.billing = billing;
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

	public void setReserved(Date r){
		this.reserved = r;
	}
	public Date getReserved() {
		return reserved;
	}

	@Override
	public String toString() {
		return "ID: "+this.id+"\nStart Date: "+this.start+"\nEnd Date: "+this.end+"\nBilling: "+this.billing+
				"\n# of Rooms: "+this.rooms.length+"\nChecked In: "+this.checkedIn+"\nChecked Out: "+this.checkedOut;
	}

	public void modify(Date s, Date e, int[] r) {
		start = s;
		end = e;
		rooms = r;
	}
}
