package hotel_management;

import billing_services.Billing;
import user_services.Account;

import java.util.Vector;

public class HotelManagement {
    private static final int NUMBER_OF_ROOMS = 40;
    private static HotelManagement hotelManagement = null;

    private Vector<Reservation> activeReservations;
    private Vector<Reservation> inactiveReservations;
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
        this.activeReservations = new Vector<>();
        this.inactiveReservations = new Vector<>();
        this.paymentHistory = new Vector<>();
        this.accounts = new Vector<>();
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

    public void checkIn(Reservation r){
        r.setCheckedIn(true);
    }
    public void checkOut(Reservation r){
        r.setCheckedOut(true);
    }
}