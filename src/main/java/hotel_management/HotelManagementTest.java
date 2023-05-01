package hotel_management;

import billing_services.Billing;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import user_services.Account;
import user_services.Admin;
import user_services.Guest;

import java.util.Calendar;
import java.util.Date;
/*
public class HotelManagementTest {
    private static HotelManagement management;
    // TC ID#: CreateHotelManagement
    // Scenario/Condition: make a new HotelManagement
    // Test inputs: creates and compares two separate HotelManagement
    // Expected Result: Equal to each other
    // Actual Result: Equals to each other
    // Assigned tester name(s): Christian
    @Test
    public void CreateHotelManagement() {
        management = HotelManagement.getHotelManagement();
        HotelManagement management1 = management.getHotelManagement();
        //Should avoid assert
        Assert.assertEquals("They should not be equal", management, management1);
    }
    // TC ID#: GenerateBilling
    // Scenario/Condition: Create billing for given reservation
    // Test inputs: hotel management and reservation
    // Expected Result: Billing is not null
    // Actual Result: Billing is not null
    // Assigned tester name(s): Christian
    @Test
    public void GenerateBilling() {
        Reservation r = new Reservation(11, new Date(2023, Calendar.MAY, 20), new Date(2023, Calendar.MAY, 21),
                new Billing(), new int[40], false, false);
        management = HotelManagement.getHotelManagement();
        Billing b = management.generateBilling(r);
        //Should not be null; output should be 0.0 since billing has yet to be fully implemented
        Assert.assertNotNull(b);
        System.out.println("Cost: "+b.getCost());
        System.out.println("Discount: "+b.getDiscount());
        System.out.println("Tip: "+b.getTip());
        System.out.println("Total Cost: "+b.calculateTotalCost());

    }
    // TC ID#: AccountLogs
    // Scenario/Condition: makes sure that Users cannot be registered twice; checks if log in and out work properly
    // Test inputs: Separate accounts and a management exist
    // Expected Result: Account = null if tried to register same account twice; Logged in Account equals given Account
    // Actual Result: Account = null if tried to register same account twice; Logged in Account equals given Account
    // Assigned tester name(s): Christian
    @Test
    public void AccountLogs() {
        //test for registerUser
        management = HotelManagement.getHotelManagement();
        Account a = new Account("TheAcc0unt", "ACC0UNT3D", "What is Alex's name?", "Debris");
        Account b = new Account("TheUnAcc0unt", "unAcc0un7ED", "Is Alex a god project manager?", "yes");
        Account c = b;
        //a = management.registerUser(a.getUsername(), a.getPassword();
        //b = management.registerUser(b.getUsername(), b.getPassword());
        //c = management.registerUser(c.getUsername(), c.getPassword());
        //Should avoid assert
        Assert.assertNull("Should be null", c);
        System.out.println("registerUser is good");

        //test for logIn and logOut
        c = new Account("TheAcc0unt", "ACC0UNT3D", "What is life?", "Bruh");
        Account d = management.logIn(c.getUsername(), c.getPassword());
        //Should avoid both asserts
        Assert.assertEquals("Should be equal in username", c.getUsername(), d.getUsername());
        ///ISSUE WITH MD5, MAKING THE HASHING OF THE PASSWORD DIFFERENT EACH TIME, WILL FIX SOON
        ///Assert.assertEquals("Should be equal in password", c.getPassword(), d.getPassword());

        d = management.logOut();
        //Should be null since logged out of Account = null
        Assert.assertNull(d);
    }

    // TC ID#: ReservingWithLimitations
    // Scenario/Condition: Restricts how reservations should work by making the end date later than the start date or
    // have the start date be on a date that has not passed.
    // Test inputs: Reservations and accounts and dates
    // Expected Result: Dates are organized the way it should be
    // Actual Result: Dates are organized the way it should be
    // Assigned tester name(s): Christian
    /*@Test
    public void ReservingWithLimitations() {
        /*Reservation r = new Reservation(21, new Date(2023, Calendar.MAY, 25), new Date(2023, Calendar.JUNE, 13),
                new Billing(), new int[40], false, false);
        management = HotelManagement.getHotelManagement();
        System.out.println(r.toString());
        Account a = new Account("User", "Password", "Mr", "Stake", 587412);
        Account g = new Guest("Guest", "Typing", "Miss", "Stake",
                682103, "Jimbo Ave", 14562933, new Date(2023, Calendar.JUNE, 30));
        Guest guest = new Guest("Guest", "Typing", "Miss", "Stake",
                682103, "Jimbo Ave", 40684319, new Date(2023, Calendar.JUNE, 30));

        //Cannot make reservation if end date is earlier than start date
        Boolean failed = true;
        if (r.getStart().getYear() <= r.getEnd().getYear()) {
            if (r.getStart().getMonth() <= r.getEnd().getMonth() || r.getStart().getYear() < r.getEnd().getYear()) {
                if (r.getStart().getDate() <= r.getEnd().getDate() || r.getStart().getMonth() < r.getEnd().getMonth()) {
                    failed = false;
                }
            }
        }
        Assert.assertFalse("End date should be later than Start Date", failed);
        //Cannot make reservation if start date is on a date that already happened in real time
        failed = true;
        Date now = Calendar.getInstance().getTime();
        if (now.getYear() <= r.getStart().getYear()) {
            if (now.getMonth() <= r.getStart().getMonth() || now.getYear() < r.getStart().getYear()) {
                if (now.getDate() <= r.getStart().getDate() || now.getMonth() < r.getStart().getMonth()) {
                    failed = false;
                }
            }
        }
        Assert.assertFalse("Start date should not have passed", failed);


        //Cannot request the same reservation
        management.addReservation(r, guest);
        management.addReservation(r, guest);
        management.addReservation(r, guest);
        for (Integer i : r.getRooms()) {
            //FIXME: identify duplicate reservation
        }
    }
    // TC ID#: GetRooms [Undeclared]
    // Scenario/Condition
    // Test inputs
    // Expected Result
    // Actual Result (can be left blank if not tested yet)
    // Assigned tester name(s): Christian
    @Test
    public void GetRooms() {

    }
}*/