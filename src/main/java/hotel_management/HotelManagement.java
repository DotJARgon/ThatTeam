package hotel_management;

import billing_services.Billing;

import java.util.Vector;

public class HotelManagement {
    private Vector<Reservation> reservations;
    private Vector<Billing> paymentHistory;
    //todo
    //likely there needs to be a change of these from Vector to
    //HashSets for better look up times
    //private Vector<Account> accounts;
    //there is a static number of rooms
    private Room[] rooms;
}
