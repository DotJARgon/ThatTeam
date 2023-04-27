package billing_services;

import hotel_management.HotelManagement;
import hotel_management.Reservation;
import hotel_management.Room;

public class BillingCalculator {
    private static final double CANCEL_COST = 0.8;
    private static final double EXECUTIVE_COST = 250;
    private static final double BUSINESS_COST = 200;
    private static final double COMFORT_COST = 150;
    private static final double ECONOMY_COST = 100;


    //ADJUST THESE VALUES LATER
    public static Billing generate(Reservation reservation) {
        Billing billing = new Billing();
        int[] roomids = reservation.getRooms();
        for(Integer i : roomids) {
            Room room = HotelManagement.getHotelManagement().getRoomByID(i);
            if(room.getQualityType() == Room.QualityType.EXECUTIVE){
                billing.cost += EXECUTIVE_COST;
            }
            else if(room.getQualityType() == Room.QualityType.BUSINESS){
                billing.cost += BUSINESS_COST;
            }
            else if(room.getQualityType() == Room.QualityType.COMFORT){
                billing.cost += COMFORT_COST;
            }
            else if(room.getQualityType() == Room.QualityType.ECONOMY){
                billing.cost += ECONOMY_COST;
            }
        }
        billing.discount = 0.0;
        billing.tip = billing.cost * 0.03;
        return billing;
    }

    public static void calculateCancelledCost(Reservation reservation){
        Billing billing = reservation.getBilling();
        billing.cancelledCost = billing.getCost()*CANCEL_COST;
        billing.isCancelled = true;
    }
}
