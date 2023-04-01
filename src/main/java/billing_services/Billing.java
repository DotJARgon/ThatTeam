package billing_services;

import user_services.Account;

public class Billing {
    private Account user;
    private Double totalCost;
    private Double tip;
    public Billing(Account u, Double tc, Double t) {
        user = u;
        totalCost = tc;
        tip = t;
    }
    public void setUser(Account u) {
        user = u;
    }
    public Account getUser() {
        return user;
    }
    public void setTip(Double t) {
        tip = t;
    }
    public Double getTip() {
        return tip;
    }
    public Double getTotalCost() {
        return totalCost;
    }
    public void calculateTotal() {
        totalCost = 0.0; // need to calculate total of reservation(s) including tax and tip
    }
}
