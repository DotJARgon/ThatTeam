package hotel_management;

import java.util.Date;

import billing_services.Billing;

public class Reservation {
    private int id;
    private Date startDate, endDate;
    private Billing billing;
    private int[] rooms;
	private boolean checkedIn;
	private boolean checkedOut;

	//Custom constructor for object, where each object is determined by whoever
    //is instantiating the object
    public Reservation(int i, Date s, Date e, Billing b, int[] r, boolean chI, boolean chO) {
    	this.id = i;
		this.startDate = s;
		this.endDate = e;
		this.billing = b;
		this.rooms = r;
		this.checkedIn = chI;
		this.checkedOut = chO;
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
