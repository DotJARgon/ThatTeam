package hotel_management;

import java.util.Date;

import billing_services.Billing;
import user_services.*;

public class Reservation {
    private int id;
    private Date startDate, endDate;
    private Date checkInTime, checkOutTime;
    //todo
    //private Guest payingGuest;
    //private Guest guest;
    private Billing billing;
    private Room[] rooms;

}
