package hotel_management;

import java.util.Date;

public class Reservation {
	private Date start, end;
    private Room[] rooms;
    
    //Custom constructor for object, where each object is determined by whoever
    //is instantiating the object
    public Reservation(Date s, Date e, /*Billing b,*/ Room[] r) {
    	start = s;
    	end = e;
    	rooms = r;
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
}
