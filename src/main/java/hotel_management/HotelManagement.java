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
/**
 * The HotelManagement class is responsible for performing all
 * the desired behavior of the hotel management system.  It contains
 * a hashMap of all reservations and their corresponding id numbers,
 * a hashMap of all accounts recorded and their corresponding id numbers, and
 * a hashMap of all rooms contained and their corresponding id numbers.
 *
 * The HotelManagement class also is responsible for using generating billing
 * for reservations, getting all currently available rooms, adding reservations
 * cancelling reservations, allowing clerks to modify room information, allowing admin
 * accounts to promote an existing account to a clerk, generating a summary of all
 * billing objects generated for a day, and checking in and out any reservation made
 * @author  Alexzander DeVries, Lizzie Nix, Bryant Huang,
 *          Marcelo Carpenter, Christian Ocana
 * @version  4.2
 * @since 3/15/23
 */
public class HotelManagement {
    private static final int NUMBER_OF_ROOMS = 40;
    private static HotelManagement hotelManagement = null;

    private ConcurrentHashMap<Integer, Reservation> allReservations;

    //likely there needs to be a change of these from Vector to
    //HashSets for better look up times
    private ConcurrentHashMap<String, Account> accounts;
    //there is a static number of rooms
    private ConcurrentHashMap<Integer, Room> rooms;

    /**
     * getHotelManagement returns a singleton of the HotelManagement class
     * @return returns the HotelManagement object
     */
    public static HotelManagement getHotelManagement() {
        if(hotelManagement == null) hotelManagement = new HotelManagement();
        return hotelManagement;
    }

    /**
     * default constructor for the HotelManagement class, which uses
     * the respective loader classes for all accounts, reservations, and rooms
     * stored within their XML files
     */
    private HotelManagement() {
        this.allReservations = ReservationLoader.loadReservations();
        this.accounts = UserLoader.loadUsers();
        this.rooms = RoomLoader.loadRooms();
    }

    /**
     * generateBilling takes in a reservation object and using the BillingCalculator
     * class, creates a billing object with the correct cost and returns it
     * @param reservation the reservation object that is having its billing info
     *                    calculated
     * @return returns the calculated billing object
     */
    public Billing generateBilling(Reservation reservation) {
        Billing billing = BillingCalculator.generate(reservation);
        reservation.setBilling(billing);
        return reservation.getBilling();
    }

    /**
     * getAvailableRooms takes in a start and end date, which then are used
     * to determine which rooms in the HotelManagement object are able to be
     * reserved in that time span.
     * @param start The start date of the rooms being checked
     * @param end The end date of the rooms being checked
     * @return Returns a vector of all Room objects available in the time span
     */
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

    /**
     * addReservation adds a reservation object into HotelManagement and its
     * corresponding Guest that is making the reservation
     * @param res The reservation object being made
     * @param g The guest account making the reservation
     */
    public void addReservation(Reservation res, Guest g) {
        res.setBilling(BillingCalculator.generate(res));
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

    /**
     * cancelReservation will take a reservation id and a guest account and cancel
     * the reservation.  if it was not cancelled within the appropriate window
     * of time, the cancelled cost is calculated and used.
     * @param resID The reservation id of the reservation being cancelled
     * @param g The guest account related to the reservation
     */
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
            BillingCalculator.calculateCancelledCost(res);
    	}
    	//remove associations
    	g.cancelReservation(resID);
    	for(int r: res.getRooms()) {
    		rooms.get(r).cancelReservation(resID);
    	}
    	allReservations.remove(resID);
    }

    /**
     * addModifyRoom will modify a room's information and add it into the HotelManagement
     * object with its new information
     * @param id the id of the room
     * @param b the number of beds in the room
     * @param bt the bed type of the beds in the room
     * @param s determine if the guest can smoke in the room or not
     * @param qt the quality type of the room
     */
    public void addModifyRoom(int id, int b, Room.BedType bt, boolean s, Room.QualityType qt){
    	if(rooms.containsKey(id))
    		rooms.get(id).updateRoom(b, bt, s, qt);
    	else
    		rooms.put(id, new Room(id, b, bt, s, qt));
    	RoomLoader.saveRooms(this.rooms.values().stream().toList());
    	
    	for(Room r : this.rooms.values())System.out.print(r.getID()+" ");
    	System.out.println();
    }

    /**
     * promoteAccountToClerk will take an already existing account and convert it
     * into an instance of a clerk in the HotelManagement system.
     * @param promotedAcc The username of the account being promoted
     */
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

    /**
     * removeRoom will remove a room from the HotelManagement system
     * and update the room XML file.
     * @param id the room id of the room being removed
     */
    public void removeRoom(int id) {
    	rooms.remove(id);
    	RoomLoader.saveRooms(this.rooms.values().stream().toList());
    }

    /**
     * generateSummary will return a vector of reservation objects created
     * on that day given to the fuction as a parameter.
     * @param day The day that the summary is generated from
     * @return Returns a vector of all reservations made on that day
     */
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

    /**
     * logIn will log an account in if given the proper username and password
     * @param username The username of the account
     * @param password The password of the account
     * @return returns the account object being logged into the system
     */
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

    /**
     * registerUser will create a new account and save it in the users xml file
     * @param username the username of the account
     * @param password the password of the account
     * @param securityA the security question of the account
     * @param securityB the security question answer of the account
     * @return
     */
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
    
    public Account getUser(String s) {
    	return accounts.get(s);
    }
}