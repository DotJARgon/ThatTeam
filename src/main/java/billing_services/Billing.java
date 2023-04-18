package billing_services;

import hotel_management.Reservation;
import user_services.Account;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Billing")
public class Billing {
    protected Double cost;
    protected Double tip;
    protected Double discount;
    protected Double cancelledCost = 0.0;
    protected Boolean isCancelled = false;

    public Billing(){

    }

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

    public Double calculateTotalCost(double cost) {
        return (cost * (1 - discount)) + tip;
    }
}
