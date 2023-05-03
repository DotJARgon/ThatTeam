package hotel_management;

import java.util.Date;

import billing_services.Billing;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Reservation class is responsible for holding all information
 * relating to a reservation which includes its unique id, the start and end
 * dates, the date that the reservation was made, the unique ids of all
 * rooms reserved,the corresponding billing object with its total cost,
 * discount amount, tip amount and cancelled cost amount,
 * as well as whether the reservation has been checked in or checked out
 * @author  Alexzander DeVries, Christian Ocana, Marcelo Carpenter
 * @version  1.6
 * @since 3/25/23
 */
@XmlRootElement(name = "reservation")
public class Reservation {
	private int id;
	private Date start, end, reserved;
    private Billing billing;
    private int[] rooms;
	private boolean checkedIn;
	private boolean checkedOut;
	private boolean canceled;

	/**
	 * The default constructor for the reservation object
	 */
	public Reservation() {}

	/**
	 * A constructor for the reservation object
	 * @param i the unique id for the reservation
	 * @param s the start date for the reservation
	 * @param e the end date for the reservation
	 * @param b the billing information of the reservation
	 * @param r the room ids of the reservation
	 * @param chI the checked in status of the reservation
	 * @param chO the checked out status of the reservation
	 */
    public Reservation(int i, Date s, Date e, Billing b, int[] r, boolean chI, boolean chO) {
    	this.id = i;
    	this.reserved = new Date(); //automatically to the current date
		this.start = s;
		this.end = e;
		this.billing = b;
		this.rooms = r;
		this.checkedIn = chI;
		this.checkedOut = chO;
		this.setCanceled(false);
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

	public Date getReserved() {
		return reserved;
	}

	public void setReserved(Date reserved) {
		this.reserved = reserved;
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

	public boolean getCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
}
