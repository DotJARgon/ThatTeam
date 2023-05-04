package hotel_management;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import billing_services.Billing;
import billing_services.BillingCalculator;
import file_utilities.XMLList;
import file_utilities.XMLParser;
import ui.UI;
import user_services.Account;
import user_services.Admin;
import user_services.Clerk;
import user_services.Guest;
import user_services.UserLoader;

import javax.swing.*;

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

    private Set<Integer> userIds;

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
        this.userIds = new HashSet<>();
        for(Account a : this.accounts.values()) this.userIds.add(a.getId());
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
    	ReservationLoader.saveReservations(this.allReservations.values().stream().toList());
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
        for(Room r : this.rooms.values()) {
            boolean roomAvailable = true;
            for(Integer resNum: r.getReservations()) {
                Reservation res = allReservations.get(resNum);
                if(res != null) {
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
            }
            if(roomAvailable)
                availableRooms.add(r);
        }
        return availableRooms;
    }

    /**
     * getAllReservations returns all of the id to reservation mappings
     * @return all reservation mappings
     */
    public ConcurrentHashMap<Integer, Reservation> getAllReservations() {
        return allReservations;
    }

    /**
     * getAccounts returns all of the username to account mappings
     * @return all account mappings
     */
    public ConcurrentHashMap<String, Account> getAccounts() {
        return accounts;
    }

    /**
     * modifyProfile changes the username, firstname and lastname of an already existing user
     * @param username the original username of the user
     * @param new_username the new username of the user
     * @param new_firstname the new firstname of the user
     * @param new_lastname the new lastname of the user
     */
    public void modifyProfile(String username, String new_username, String new_firstname, String new_lastname) {
        Account newAcc = this.accounts.get(username);
        this.accounts.remove(username);
        newAcc.setUsername(new_username);
        newAcc.setFirstName(new_firstname);
        newAcc.setLastName(new_lastname);
        this.accounts.put(new_username, newAcc);
        this.userIds = new HashSet<>();
        for(Account a : this.accounts.values()) this.userIds.add(a.getId());
        this.saveUsers();
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
        this.saveUsers();
        this.saveReservations();
    }

    /**
     * getRoomByID gets a room by its id
     * @param id The id of the room to get
     * @return either null if the room does not exist or the room associated with that id
     */
    public Room getRoomByID(int id){
        return this.rooms.get(id);
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
        if((currDate.get(Calendar.YEAR) == resDate.get(Calendar.YEAR) && currDate.get(Calendar.DAY_OF_YEAR) - resDate.get(Calendar.DAY_OF_YEAR) > 2) ||
                (currDate.get(Calendar.YEAR) == resDate.get(Calendar.YEAR)+1 && resDate.get(Calendar.DAY_OF_YEAR) - currDate.get(Calendar.DAY_OF_YEAR) < 363))
            BillingCalculator.calculateCancelledCost(res);

        if(g == null) {
            for(Map.Entry<String, Account> acc : this.accounts.entrySet()) {
                if(acc.getValue() instanceof Guest) {
                    Guest currG = (Guest)acc.getValue();
                    if(currG.getReservations().contains(resID));{
                        g = currG;
                        break;
                    }
                }
            }
        }
        //remove associations
        if(g != null) g.cancelReservation(resID);

        for(int r: res.getRooms()) rooms.get(r).cancelReservation(resID);
        res.setCanceled(true);

        this.saveUsers();
        this.saveReservations();
    }

    /**
     * hasCancellationFee returns true if the reservation, if it were cancelled now, would result
     * in a cancellation fee, this is so the status of a cancellation can be known ahead of time.
     * It assumes the reservation id is a valid reservation as well
     * @param resID The reservation id to check
     * @return true if the cancellation fee would incur if it was cancelled now, false otherwise
     */
    public boolean hasCancellationFee(int resID) {
        Reservation res = allReservations.get(resID);
        //Determine whether to charge the guest
        GregorianCalendar resDate = new GregorianCalendar();
        resDate.setTime(res.getReserved());
        GregorianCalendar currDate = new GregorianCalendar();
        currDate.setTime(new Date()); //prolly not necessary, but for security
        //if within cancellation time
        return ((currDate.get(Calendar.YEAR) == resDate.get(Calendar.YEAR) && currDate.get(Calendar.DAY_OF_YEAR) - resDate.get(Calendar.DAY_OF_YEAR) > 2) ||
                (currDate.get(Calendar.YEAR) == resDate.get(Calendar.YEAR)+1 && resDate.get(Calendar.DAY_OF_YEAR) - currDate.get(Calendar.DAY_OF_YEAR) < 363));
    }

    /**
     * modifyReservation modifies a reservation with a new start and end date, and
     * a new set of rooms
     * @param resID The id of the reservation to modify
     * @param start the new start date
     * @param end the new end date
     * @param rooms the new rooms
     */
    public void modifyReservation(int resID, Date start, Date end, int[] rooms) {
        Reservation res = allReservations.get(resID);
        if(res != null) {
            res.modify(start, end, rooms);
            res.setBilling(BillingCalculator.generate(res));
            this.saveReservations();
        }
    }

    /**
     * getRes gets a reservation based off of its id
     * @param resID The id of the reservation to get
     * @return null if that resID does not exist or the reservation associated with that id
     */
    public Reservation getRes(int resID) {
    	return allReservations.get(resID);
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
        this.saveRooms();
    }

    /**
     * promoteAccountToClerk will take an already existing account and convert it
     * into an instance of a clerk in the HotelManagement system.
     * @param promotedAcc The username of the account being promoted
     */
    //Assumes that promotedAcc is a guest, verify before calling
    public void promoteAccountToClerk(String promotedAcc) {
        Clerk promoted = new Clerk();
        Guest acc = (Guest) accounts.get(promotedAcc);
        promoted.setUsername(acc.getUsername());
        promoted.setFirstName(acc.getFirstName());
        promoted.setLastName(acc.getLastName());
        promoted.setId(acc.getId());
        promoted.setPassword(acc.getPassword());
        HotelManagement.getHotelManagement().getAccounts().remove(promotedAcc);
        HotelManagement.getHotelManagement().getAccounts().put(promotedAcc, promoted);
        UserLoader.saveUsers(accounts.values().stream().toList());

        this.userIds = new HashSet<>();
        for(Account a : this.accounts.values()) this.userIds.add(a.getId());
    }

    /**
     * getRooms returns the rooms mapping
     * @return the map of rooms ids to room objects
     */
    public ConcurrentHashMap<Integer, Room> getRooms(){
    	return rooms;
    }

    /**
     * getRoom returns a room by id
     * @param id The id of the room to get
     * @return null if the room does not exist, or the room corresponding with that id
     */
    public Room getRoom(int id) {
    	return rooms.get(id);
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
     * generateSummary will write a local file of all of the reservations and
     * their billings
     * @return Returns a vector of all reservations made on that day
     */
    public void generateSummary() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("summary.txt"));
            for(Reservation r : this.allReservations.values()) {
                Billing billing = r.getBilling();
                if(billing != null) {
                    bw.write("Reservation id: " + r.getID() + "===================================\n");
                    bw.write("Cancelled Cost: " + billing.getCost() + "\n");
                    if(billing.getCancelled()) bw.write("Cancelled Cost: " + billing.getCost() + "\n");
                    else bw.write("Cost:           " + billing.getCost() + "\n");
                    bw.write("Discount:       " + billing.getDiscount() + "\n");
                    bw.write("Tip:            " + billing.getTip() + "\n");
                    bw.write("Paid:           " + billing.getPaid() + "\n");
                }
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * checkIn will take a reservation ID and check in the reservation object related to it
     * @param reserveID The reservation ID of the reservation being checked in
     */
    public void checkIn(int reserveID){
        this.allReservations.get(reserveID).setCheckedIn(true);
        this.saveReservations();
    }

    /**
     * checkOut will take a reservation ID and check out that reservation
     * @param reserveID The reservation ID of the reservation being checked out
     */
    public void checkOut(int reserveID){
        this.allReservations.get(reserveID).setCheckedIn(false);
        this.allReservations.get(reserveID).setCheckedOut(true);
        Billing newBilling = BillingCalculator.generate(allReservations.get(reserveID));
        allReservations.get(reserveID).setBilling(newBilling);
        Guest g;
        if(UI.getCurrentClient() instanceof Guest)
            g = (Guest) UI.getCurrentClient();
        else 
            g = ((Clerk) UI.getCurrentClient()).getGuest();
        if(g.getCorporation().equals(""))
            allReservations.get(reserveID).getBilling().setPaid(true);

        this.saveReservations();
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
        Account account = accounts.get(username);
        //System.out.println(accounts.get(username));
        if (account != null) {
            if (account.matches(password)) {
                return account;
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
     * @param securityQ the security question answer of the account
     * @return
     */
    public Guest registerUser(String username, String password, String firstName, String lastName, int id, String securityQ, String securityA) {
        if(!accounts.containsKey(username)) {
            Guest acc = new Guest(username, password, firstName, lastName, id, securityQ, securityA);
            accounts.put(username, acc);
            this.userIds.add(id);

            this.saveUsers();

            return acc;
        }
        return null;
    }

    /**
     * saveRooms saves all of the rooms to an xml file
     */
    public void saveRooms() {
        RoomLoader.saveRooms(this.rooms.values().stream().toList());
    }
    /**
     * saveUsers saves all of the accounts to an xml file
     */
    public void saveUsers() {
        UserLoader.saveUsers(this.accounts.values().stream().toList());
    }
    /**
     * saveReservations saves all of the reservations to an xml file
     */
    public void saveReservations() {
        ReservationLoader.saveReservations(this.allReservations.values().stream().toList());
    }

    /**
     * checkID is to check if an id already exists
     * @param id the id to check
     * @return true if the id exists false otherwise
     */
    public boolean checkID(int id) {
        return userIds.contains(id);
    }

    /**
     * getUser gets a user based off of their username
     * @param username The username of the user to get
     * @return either null if the username does not exist or the corresponding Account
     */
    public Account getUser(String username) {
    	return accounts.get(username);
    }
}