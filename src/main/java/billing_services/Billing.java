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
    public Billing(){}


    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getTip() {
        return tip;
    }

    public void setTip(Double tip) {
        this.tip = tip;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
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
