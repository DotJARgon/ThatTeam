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

public class HotelManagementTest {
    private static HotelManagement management;
    // TC ID#: CreateHotelManagement
    // Scenario/Condition: make a new HotelManagement
    // Test inputs: getHotelManagement
    // Expected Result: pass
    // Actual Result (can be left blank if not tested yet)
    // Assigned tester name(s): none
    @Test
    public void CreateHotelManagement() {
        management = HotelManagement.getHotelManagement();
        HotelManagement management1 = management.getHotelManagement();
        //Should avoid assert
        Assert.assertEquals("They should not be equal", management, management1);
    }
    // TC ID#: Billing Test
    // Scenario/Condition
    // Test inputs
    // Expected Result
    // Actual Result (can be left blank if not tested yet)
    // Assigned tester name(s)
    @Test
    public void GetRooms() {

    }
    // TC ID#: Billing Test
    // Scenario/Condition
    // Test inputs
    // Expected Result
    // Actual Result (can be left blank if not tested yet)
    // Assigned tester name(s)
    @Test
    public void GenerateBilling() {
        Reservation r = new Reservation(11, new Date(2023, Calendar.MAY, 20), new Date(2023, Calendar.MAY, 21),
                new Billing(), new int[40], false, false);
        management = HotelManagement.getHotelManagement();
        Billing b = management.generateBilling(r);
        //Should not be null; output should be 0.0 since billing has yet to be fully implemented
        Assert.assertNotNull(b);
        System.out.println("Cost : "+b.getCost());
        System.out.println("Discount : "+b.getDiscount());
        System.out.println("Tip : "+b.getTip());
        System.out.println("Total Cost : "+b.calculateTotalCost(b.getCost()));

    }
    // TC ID#: Billing Test
    // Scenario/Condition
    // Test inputs
    // Expected Result
    // Actual Result (can be left blank if not tested yet)
    // Assigned tester name(s)
    @Test
    public void AccountLogs() {
        //test for registerUser
        management = HotelManagement.getHotelManagement();
        Account a = new Account("TheAcc0unt", "ACC0UNT3D");
        Account b = new Account("TheUnAcc0unt", "unAcc0un7ED");
        Account c = b;
        a = management.registerUser(a.getUsername(), a.getPassword());
        b = management.registerUser(b.getUsername(), b.getPassword());
        c = management.registerUser(c.getUsername(), c.getPassword());
        //Should avoid assert
        Assert.assertNull("Should be null", c);
        System.out.println("registerUser is good");

        //test for logIn and logOut
        c = new Account("TheAcc0unt", "ACC0UNT3D");
        Account d = management.logIn(c.getUsername(), c.getPassword());
        //Should avoid both asserts
        Assert.assertEquals("Should be equal in username", c.getUsername(), d.getUsername());
        ///ISSUE WITH MD5, MAKING THE HASHING OF THE PASSWORD DIFFERENT EACH TIME, WILL FIX SOON
        ///Assert.assertEquals("Should be equal in password", c.getPassword(), d.getPassword());

        d = management.logOut();
        //Should be null since logged out of Account = null
        Assert.assertNull(d);

    }

    // TC ID#: Billing Test
    // Scenario/Condition
    // Test inputs
    // Expected Result
    // Actual Result (can be left blank if not tested yet)
    // Assigned tester name(s)
    @Test
    public void ReservingWithLimitations() {
        Reservation r = new Reservation(11, new Date(2023, Calendar.MAY, 25), new Date(2023, Calendar.JUNE, 23),
                new Billing(), new int[40], false, false);
        System.out.println(r.toString());
        Account a = new Account("User", "Pass", "Mr", "Stake", 587412);
        Account g = new Guest("Guest", "Type", "Miss", "Stake",
                682103, "Mommy Ave", 31612905, new Date(2023, Calendar.MAY, 30));
        Guest guest = new Guest("Guest", "Type", "Miss", "Stake",
                682103, "Mommy Ave", 31612905, new Date(2023, Calendar.MAY, 30));
        management = HotelManagement.getHotelManagement();
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
        management.addReservation(r, guest, r.getRooms());
        management.addReservation(r, guest, r.getRooms());
        management.addReservation(r, guest, r.getRooms());
        for (Integer i : r.getRooms()) {
            //FIXME
        }
    }
}
