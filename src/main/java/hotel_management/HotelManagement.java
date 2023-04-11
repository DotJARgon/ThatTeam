package hotel_management;

import java.util.Date;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import billing_services.Billing;
import user_services.Account;
import user_services.Guest;
import user_services.UserLoader;

public class HotelManagement {
    private static final int NUMBER_OF_ROOMS = 40;
    private static HotelManagement hotelManagement = null;

    private ConcurrentHashMap<Integer, Reservation> activeReservations;
    private ConcurrentHashMap<Integer, Reservation> inactiveReservations;
    private Vector<Billing> paymentHistory;
    private ConcurrentHashMap<String, Account> accounts;
    //there is a static number of rooms
    private ConcurrentHashMap<Integer, Room> rooms;

    public static HotelManagement getHotelManagement() {
        if(hotelManagement == null) hotelManagement = new HotelManagement();
        return hotelManagement;
    }

    private HotelManagement() {
        this.activeReservations = ReservationLoader.loadReservations(ReservationLoader.Status.ACTIVE);
        this.inactiveReservations = ReservationLoader.loadReservations(ReservationLoader.Status.INACTIVE);
        this.paymentHistory = new Vector<>();
        this.accounts = UserLoader.loadUsers();
        //TODO: Load rooms using XML
        //this.rooms = RoomLoader.loadRooms();
    }

    public Billing generateBilling(Reservation reservation) {
        //todo
        return reservation.getBilling();
    }
    //Assumes end > start
    public Vector<Room> getAvailableRooms(Date start, Date end) {
        Vector<Room> availableRooms = new Vector<>();
        for(Map.Entry<Integer,Room> entry: rooms.entrySet()) {
            boolean roomAvailable = true;
            for(Integer res: entry.getValue().getReservations()) {
                if(end.after(start)) {
                    if(start.before(activeReservations.get(res).getStart()))
                        if(end.after(activeReservations.get(res).getStart()))
                            roomAvailable = false;
                    else if(start.before(activeReservations.get(res).getEnd()))
                        roomAvailable = false;
                }
                else 
                    roomAvailable = false;
            }
            if(roomAvailable)
                availableRooms.add(entry.getValue());
        }
        return availableRooms;
    }
    
    public void addReservation(Reservation res, Guest g) {
        activeReservations.put(res.getID(), res);
        for(int i = 0; i < res.getRooms().length; i++) {
        	this.rooms.get(i).addReservation(res.getID());
        }
        g.addReservation(res.getID());
        ReservationLoader.saveReservations(activeReservations.values().stream().collect(Collectors.toSet()), ReservationLoader.Status.ACTIVE);
    }
    
    public void cancelReservation(int res, Guest g) {
    	if(activeReservations.containsKey(res)) {
	    	g.cancelReservation(res);
	    	for(Integer i: activeReservations.get(res).getRooms()) 
	    		this.rooms.get(i).cancelReservation(res);
	    	activeReservations.remove(res);
	    	//TODO: Save to XML
    	}
    	else {
    		//TODO: Throw exception
    	}
    }
    
    //hotelManagement, being the only one to hold a complete instance of the reservations,
    //should be the only one allowed to directly modify it
    public void modifyReservation(int id, Date start, Date end, int[] r) {
    	activeReservations.get(id).setStart(start);
    	activeReservations.get(id).setEnd(end);
    	activeReservations.get(id).setRooms(r);
    }
    
    public Map<Integer,Room> getRooms(){
    	return rooms;
    }

    public Vector<Billing> generateSummary() {
        //a summary of the billings for a day
        return new Vector<>();
    }
    
    public Account logOut() {
        //logs the user out of their session, this
        //will clear any lingering temporary data
        //about this user
        return null;
    }

    public void checkIn(int reserveID){
        this.activeReservations.get(reserveID).setCheckedIn(true);
    }
    public void checkOut(int reserveID){
        this.inactiveReservations.put(reserveID, this.activeReservations.get(reserveID));
        this.activeReservations.remove(reserveID);
    }
    public Account logIn(String username, String password) {
        //query data base, will be added soon,
        //likely will need to change the logIn with a token
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(username);
            if (account != null) {
                if (account.getPassword().equals(password)) {
                    return account;
                }
            }
        }
        //if no username or password matches with the ones of an already existing account
        return null;
    }

    public Account registerUser(String username, String password) {
        if(!accounts.containsKey(username)) {
            Account acc = new Account(username, password);
            accounts.put(username, acc);
            UserLoader.saveUsers(accounts.values().stream().collect(Collectors.toSet()));
            return acc;
        }
        return null;
    }
}