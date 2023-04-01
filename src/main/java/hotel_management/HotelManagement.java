package hotel_management;

import java.util.Date;
import java.util.Vector;

import billing_services.Billing;
import user_services.Account;

public class HotelManagement {
    private static final int NUMBER_OF_ROOMS = 40;
    private static HotelManagement hotelManagement = null;

    private Vector<Reservation> reservations;
    private Vector<Billing> paymentHistory;
    //likely there needs to be a change of these from Vector to
    //HashSets for better look up times
    private Vector<Account> accounts;
    //there is a static number of rooms
    private Vector<Room> rooms;

    public static HotelManagement getHotelManagement() {
        if(hotelManagement == null) hotelManagement = new HotelManagement();
        return hotelManagement;
    }

    public HotelManagement() {
        this.reservations = new Vector<>();
        this.paymentHistory = new Vector<>();
        this.accounts = new Vector<>();
        this.rooms = RoomLoader.loadRooms();
    }

    public Billing generateBilling(Reservation reservation) {
        //todo
        return null;
    }
    //Assumes end > start
    public Vector<Room> getAvailableRooms(Date start, Date end) {
        Vector<Room> availableRooms = new Vector<Room>();
        for(Room r: rooms) {
        	boolean roomAvailable = true;
        	for(Reservation res: r.getReservations()) {
        		if(start.before(res.getStart())) {
        			if(end.after(res.getStart()))
        				roomAvailable = false;
        		}
        		else if(start.before(res.getEnd())) {
        			roomAvailable = false;
        		}
        	}
        	if(roomAvailable)
        		availableRooms.add(r);
        }
        return availableRooms;
    }
    
    public void addReservation(Reservation res, /*Guest g, */Room room) {
    	reservations.add(res);
    	//g.addReservation(r)
    	room.addReservation(res);
    }

    public Vector<Reservation> getReservations() {
        //todo
        return reservations;
    }
    public Vector<Room> getRooms(){
    	return rooms;
    }

    public Vector<Billing> generateSummary() {
        //a summary of the billings for a day
        return new Vector<>();
    }

    public Account logIn(String username, String password) {
        //query data base, will be added soon,
        //likely will need to change the logIn with a token
        return new Account();
    }

    public boolean logOut(String username) {
        //logs the user out of their session, this
        //will clear any lingering temporary data
        //about this user
        return true;
    }
}