package billing_services;

import hotel_management.Reservation;

public class BillingCalculator {
    private static final double CANCEL_COST = 0.8;

    //ADJUST THESE VALUES LATER
    public static Billing generate(Reservation reservation) {
        Billing billing = new Billing();
        billing.cost = 0.0;
        billing.discount = 0.0;
        billing.tip = 0.0;
        return billing;
    }

    public static void calculateCancelledCost(Reservation reservation){
        Billing billing = reservation.getBilling();
        billing.cancelledCost = billing.getCost()*CANCEL_COST;
        billing.isCancelled = true;
    }

}
