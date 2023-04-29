package hotel_management;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import billing_services.Billing;
import billing_services.BillingCalculator;
import file_utilities.XMLList;
import file_utilities.XMLParser;
import user_services.Account;
import user_services.Admin;
import user_services.Clerk;
import user_services.Guest;
import user_services.UserLoader;

public class HotelManagement {
    private static final int NUMBER_OF_ROOMS = 40;
    private static HotelManagement hotelManagement = null;

    private ConcurrentHashMap<Integer, Reservation> allReservations;
    private Vector<Billing> paymentHistory;
    //likely there needs to be a change of these from Vector to
    //HashSets for better look up times
    private ConcurrentHashMap<String, Account> accounts;
    //there is a static number of rooms
    private ConcurrentHashMap<Integer, Room> rooms;

    public static HotelManagement getHotelManagement() {
        if(hotelManagement == null) hotelManagement = new HotelManagement();
        return hotelManagement;
    }

    private HotelManagement() {
        this.allReservations = ReservationLoader.loadReservations();
        this.paymentHistory = new Vector<>();
        this.accounts = UserLoader.loadUsers();
        this.rooms = RoomLoader.loadRooms();
    }

    public Billing generateBilling(Reservation reservation) {
        Billing billing = BillingCalculator.generate(reservation);
        reservation.setBilling(billing);
        return reservation.getBilling();
    }
    //Assumes end != start
    public Vector<Room> getAvailableRooms(Date start, Date end) {
        Vector<Room> availableRooms = new Vector<>();
    	if(end.before(start) || end.equals(start))
    		return availableRooms;
        for(Map.Entry<Integer,Room> r: rooms.entrySet()) {
            boolean roomAvailable = true;
            for(Integer resNum: r.getValue().getReservations()) {
            	Reservation res = allReservations.get(resNum);
            	if(start.equals(res.getStart()) || start.equals(res.getEnd()) ||
            	   end.equals(res.getStart()) || end.equals(res.getEnd()))
            		roomAvailable = false;
            	else if(start.before(res.getStart())) {
                    if(end.after(res.getStart()))
                        roomAvailable = false;
                }
                else if(start.before(res.getEnd()))
                    roomAvailable = false;
            }
            if(roomAvailable)
                availableRooms.add(r.getValue());
        }
        return availableRooms;
    }
    public ConcurrentHashMap<Integer, Reservation> getAllReservations() {
        return allReservations;
    }

    public ConcurrentHashMap<String, Account> getAccounts() {
        return accounts;
    }

    public void addReservation(Reservation res, Guest g) {
        allReservations.put(res.getID(), res);
        //loop thru reservation's rooms. Will typically be 1 room
        for(int r : res.getRooms()) {
        	rooms.get(r).addReservation(res.getID());
        }
        g.addReservation(res.getID());
        ReservationLoader.saveReservations(allReservations.values().stream().toList());
    }
    public Room getRoomByID(int i){
        return this.rooms.get(i);
    }
    
    //Assumes it's not a leap year
    public void cancelReservation(int resID, Guest g) {
    	Reservation res = allReservations.get(resID);
    	//Determine whether to charge the guest
    	GregorianCalendar resDate = new GregorianCalendar();
    	resDate.setTime(res.getReserved());
    	GregorianCalendar currDate = new GregorianCalendar();
    	currDate.setTime(new Date()); //prolly not necessary, but for security
    	if(currDate.get(Calendar.YEAR) == resDate.get(Calendar.YEAR) && currDate.get(Calendar.DAY_OF_YEAR) - resDate.get(Calendar.DAY_OF_YEAR) > 2 ||
    	   currDate.get(Calendar.YEAR) == resDate.get(Calendar.YEAR)+1 && resDate.get(Calendar.DAY_OF_YEAR) - currDate.get(Calendar.DAY_OF_YEAR) < 363) {
    		//TODO: Display message with the correct cost. The infrastructure isn't settled as of me writing this
    	}
    	//remove associations
    	g.cancelReservation(resID);
    	for(int r: res.getRooms()) {
    		rooms.get(r).cancelReservation(resID);
    	}
    	allReservations.remove(resID);
    }
    
    public void addModifyRoom(int id, int b, Room.BedType bt, boolean s, Room.QualityType qt){
    	if(rooms.containsKey(id))
    		rooms.get(id).updateRoom(b, bt, s, qt);
    	else
    		rooms.put(id, new Room(id, b, bt, s, qt));
    	RoomLoader.saveRooms(this.rooms.values().stream().toList());
    	
    	for(Room r : this.rooms.values())System.out.print(r.getID()+" ");
    	System.out.println();
    }
    public void promoteAccountToClerk(String promotedAcc) {
        Clerk promoted = new Clerk();
        if(HotelManagement.getHotelManagement().getAccounts().contains(promotedAcc)){
            Account acc = HotelManagement.getHotelManagement().getAccounts().get(promotedAcc);
            promoted.setUsername(acc.getUsername());
            promoted.setFirstName(acc.getFirstName());
            promoted.setLastName(acc.getLastName());
            promoted.setId(acc.getId());
            promoted.setPassword(acc.getPassword());
            HotelManagement.getHotelManagement().getAccounts().remove(promotedAcc);
            HotelManagement.getHotelManagement().getAccounts().put(promotedAcc, promoted);
        }
    }
    
    public ConcurrentHashMap<Integer, Room> getRooms(){
    	return rooms;
    }
    public Room getRoom(int k) {
    	return rooms.get(k);
    }
    public void removeRoom(int id) {
    	rooms.remove(id);
    	RoomLoader.saveRooms(this.rooms.values().stream().toList());
    }


    public Vector<Reservation> generateSummary(Date day) {
        Vector<Reservation> summary = new Vector<>();
        for(Reservation r : allReservations.values()){
            if(r.getStart() == day){
                summary.add(r);
            }
        }
        //a summary of the billings for a day
        return summary;
    }
    
    public Account logOut() {
        //logs the user out of their session, this
        //will clear any lingering temporary data
        //about this user
        return null;
    }

    public void checkIn(int reserveID){
        this.allReservations.get(reserveID).setCheckedIn(true);
    }
    public void checkOut(int reserveID){
        /*this.inactiveReservations.put(reserveID, this.allReservations.get(reserveID));
        this.allReservations.remove(reserveID);*/
    }
    public Account logIn(String username, String password) {
        //query data base, will be added soon,
        //likely will need to change the logIn with a token
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(username);
            if (account != null) {
                if (account.matches(password)) {
                    return account;
                }
            }
        }
        //if no username or password matches with the ones of an already existing account
        return null;
    }

    public Account registerUser(String username, String password, String securityA, String securityB) {
        if(!accounts.containsKey(username)) {
            Account acc = new Account(username, password, securityA, securityB);
            accounts.put(username, acc);
            UserLoader.saveUsers(accounts.values().stream().toList());
            return acc;
        }
        return null;
    }

    public Account getAccountByUsername(String username) {
        return this.accounts.get(username);
    }

    private void loadUsers() {
        try {
            List<Account> users = XMLParser.load("users.xml", XMLList.class, Account.class, Guest.class, Clerk.class, Admin.class);
            this.accounts = new ConcurrentHashMap<>();
            for(Account account : users) {
                this.accounts.put(account.getUsername(), account);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveUsers() {
        List<Account> a = this.accounts.values().stream().collect(Collectors.toList());
        XMLParser.save(a, "users.xml", XMLList.class, Account.class, Guest.class, Clerk.class, Admin.class);
    }
    
    public Account getUser(String s) {
    	return accounts.get(s);
    }
}