package hotel_management;

import billing_services.Billing;
import user_services.Account;
import user_services.UserLoader;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class HotelManagement {
    private static final int NUMBER_OF_ROOMS = 40;
    private static HotelManagement hotelManagement = null;

    private ConcurrentHashMap<Integer, Reservation> activeReservations;
    private ConcurrentHashMap<Integer, Reservation> inactiveReservations;
    private Vector<Billing> paymentHistory;
    //likely there needs to be a change of these from Vector to
    //HashSets for better look up times
    private ConcurrentHashMap<String, Account> accounts;
    //there is a static number of rooms
    private Vector<Room> rooms;

    public static HotelManagement getHotelManagement() {
        if(hotelManagement == null) hotelManagement = new HotelManagement();
        return hotelManagement;
    }

    public HotelManagement() {
        this.activeReservations = ReservationLoader.loadReservations(ReservationLoader.Status.ACTIVE);
        this.inactiveReservations = ReservationLoader.loadReservations(ReservationLoader.Status.INACTIVE);
        this.paymentHistory = new Vector<>();
        this.accounts = UserLoader.loadUsers();
        this.rooms = RoomLoader.loadRooms();
    }

    public Billing generateBilling(Reservation reservation) {
        //todo
        return null;
    }

    public Vector<Room> getAvailableRooms(Account account) {
        //dummy for now
        return this.rooms;
    }

    public Vector<Reservation> getReservations(Account account) {
        //todo
        return new Vector<>();
    }

    public Vector<Billing> generateSummary() {
        //a summary of the billings for a day
        return new Vector<>();
    }
    
    public boolean logOut(String username) {
        //logs the user out of their session, this
        //will clear any lingering temporary data
        //about this user
        return true;
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
            if(account != null) {
                if(account.getPassword().equals(password)) {
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