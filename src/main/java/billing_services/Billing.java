package billing_services;

import hotel_management.Reservation;
import user_services.Account;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * The Billing class is responsible for keeping track of
 * the total cost of one or more reservations made by a user
 * as well as its cancelled cost.
 * @author  Alexzander DeVries
 * @version  1.6
 * @since 4/25/23
 */
@XmlRootElement(name = "Billing")
public class Billing {
    protected Double cost = 0.0;
    protected Double tip = 0.0;
    protected Double discount = 0.0;
    protected Double cancelledCost = 0.0;
    protected Boolean isCancelled = false;
    protected Boolean isPaid = false;

    /**
     * Default constructor for the Billing class
     */
    public Billing(){

    }

    /**
     *
     * @param d the discount amount from the total cost
     * @param tc the total cost of the reservation made
     * @param t the amount tipped by the user
     */
    protected Billing(Double d, Double tc, Double t) {
        cost = tc;
        tip = t;
        discount = d;
    }

    public Double getTip() {
        return tip;
    }
    public Double getCost() {
        return cost;
    }

    public Double getDiscount() {
        return discount;
    }

    public Double getCancelledCost() {
        return cancelledCost;
    }

    public void setCancelledCost(Double cancelledCost) {
        this.cancelledCost = cancelledCost;
    }

    public Boolean getCancelled() {
        return isCancelled;
    }

    public void setCancelled(Boolean cancelled) {
        isCancelled = cancelled;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    /**
     * This function is used to calculate the total cost of a billing object
     * @return Returns the calculated total cost based on the discount and tip amount
     */
    public Double calculateTotalCost() {
        return (cost * (1 - discount)) + tip;
    }
}
