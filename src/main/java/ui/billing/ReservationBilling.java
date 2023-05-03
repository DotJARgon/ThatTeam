package ui.billing;

import billing_services.Billing;
import hotel_management.Reservation;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * The Reservation Billing class is a specific panel that
 * shows a billing of a Reservation, passed in the constructor,
 * in an easily digestible manner, it is meant to be shown
 * so users can easily view it
 * @author  Alexzander DeVries, Lizzie Nix, Bryant Huang,
 *          Marcelo Carpenter, Christian Ocana
 * @version  4.2
 * @since 3/15/23
 */
public class ReservationBilling extends JPanel {
    /**
     * Constructor of the ReservationBilling page, it takes in just
     * one parameter
     *
     * @param reservation The reservation to use to display the billing info
     */
    public ReservationBilling(Reservation reservation) {
        super(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        //g.fill = GridBagConstraints.BOTH;
        g.anchor = GridBagConstraints.WEST;

        g.gridwidth = 1;
        g.gridheight = 1;

        g.gridx = 0;
        g.gridy = 0;
        JLabel idLabel = new JLabel("Reservation ID");
        this.add(idLabel, g);

        g.gridx = 0;
        g.gridy = 1;
        JLabel startLabel = new JLabel("Start Date");
        this.add(startLabel, g);

        g.gridx = 0;
        g.gridy = 2;
        JLabel endLabel = new JLabel("End Date");
        this.add(endLabel, g);

        g.gridx = 0;
        g.gridy = 3;
        JLabel roomLabel = new JLabel("ReservedRooms");
        this.add(roomLabel, g);

        Billing billing = reservation.getBilling();

        //if it is cancelled
        if(billing.getCancelled()) {
            g.gridx = 0;
            g.gridy = 4;
            JLabel cancelled = new JLabel("Cancelled:");
            this.add(cancelled, g);
            g.gridx = 0;
            g.gridy = 5;
            JLabel costLabel = new JLabel("Cost");
            this.add(costLabel, g);
            g.gridx = 0;
            g.gridy = 6;
            JLabel discountLabel = new JLabel("Discount");
            this.add(discountLabel, g);
            g.gridx = 0;
            g.gridy = 7;
            JLabel tipLabel = new JLabel("Tip");
            this.add(tipLabel, g);

            g.gridwidth = 2;
            g.gridx = 1;
            g.gridy = 5;
            JLabel cost = new JLabel(Double.toString(billing.getCancelledCost()));
            this.add(cost, g);
            g.gridx = 1;
            g.gridy = 6;
            JLabel discount = new JLabel(Double.toString(billing.getDiscount()));
            this.add(discount, g);
            g.gridx = 1;
            g.gridy = 7;
            JLabel tip = new JLabel(Double.toString(billing.getTip()));
            this.add(tip, g);
        }
        else {
            g.gridx = 0;
            g.gridy = 4;
            JLabel costLabel = new JLabel("Cost");
            this.add(costLabel, g);
            g.gridx = 0;
            g.gridy = 5;
            JLabel discountLabel = new JLabel("Discount");
            this.add(discountLabel, g);
            g.gridx = 0;
            g.gridy = 6;
            JLabel tipLabel = new JLabel("Tip");
            this.add(tipLabel, g);

            g.gridwidth = 2;
            g.gridx = 1;
            g.gridy = 4;
            JLabel cost = new JLabel(Double.toString(billing.getCost()));
            this.add(cost, g);
            g.gridx = 1;
            g.gridy = 5;
            JLabel discount = new JLabel(Double.toString(billing.getDiscount()));
            this.add(discount, g);
            g.gridx = 1;
            g.gridy = 6;
            JLabel tip = new JLabel(Double.toString(billing.getTip()));
            this.add(tip, g);
        }

        g.gridwidth = 2;
        g.gridx = 1;
        g.gridy = 0;
        JLabel id = new JLabel(String.valueOf(reservation.getID()));
        this.add(id, g);

        g.gridx = 1;
        g.gridy = 1;
        JLabel start = new JLabel(reservation.getStart().toString());
        this.add(start, g);

        g.gridx = 1;
        g.gridy = 2;
        JLabel end = new JLabel(reservation.getEnd().toString());
        this.add(end, g);

        g.gridx = 1;
        g.gridy = 3;
        JLabel room = new JLabel(Arrays.toString(reservation.getRooms()));
        this.add(room, g);

    }
}
