package billing_services;

import hotel_management.HotelManagement;
import hotel_management.Reservation;
import hotel_management.Room;

/**
 * The BillingCalculator class is responsible for handling the calculations
 * of a hilling object based on the type of room and other factors
 * @author  Alexzander DeVries
 * @version  1.6
 * @since 4/25/23
 */
public class BillingCalculator {
    private static final double CANCEL_COST = 0.8;
    private static final double EXECUTIVE_COST = 250;
    private static final double BUSINESS_COST = 200;
    private static final double COMFORT_COST = 150;
    private static final double ECONOMY_COST = 100;

    /**
     * Generate is responsible for generating a billing object with the proper
     * values based on the reservation object given to it.
     * @param reservation the reservation used to generate the billing
     * @return Returns a billing object that has the correct total cost,
     *         tip amount and discount
     */
    public static Billing generate(Reservation reservation) {
        Billing billing = new Billing();
        //Grabs the id values for all rooms in the reservation
        int[] roomids = reservation.getRooms();
        for(Integer i : roomids) {
            //Finds the room with the same id desired
            Room room = HotelManagement.getHotelManagement().getRoomByID(i);
            if(room.getQualityType() == Room.QualityType.EXECUTIVE){
                billing.cost += EXECUTIVE_COST * room.getNumBeds();
            }
            else if(room.getQualityType() == Room.QualityType.BUSINESS){
                billing.cost += BUSINESS_COST * room.getNumBeds();
            }
            else if(room.getQualityType() == Room.QualityType.COMFORT){
                billing.cost += COMFORT_COST * room.getNumBeds();
            }
            else if(room.getQualityType() == Room.QualityType.ECONOMY){
                billing.cost += ECONOMY_COST * room.getNumBeds();
            }
        }
        billing.discount = 0.0;
        billing.tip = billing.cost * 0.03;
        return billing;
    }

    /**
     * This function calculates the cancelled cost for a room
     * if it has been cancelled after the time window allowed
     * @param reservation  the reservation that the cancelled cost is being calculated
     */
    public static void calculateCancelledCost(Reservation reservation){
        Billing billing = reservation.getBilling();
        billing.cancelledCost = billing.getCost()*CANCEL_COST;
        billing.isCancelled = true;
        reservation.setBilling(billing);
    }
}
