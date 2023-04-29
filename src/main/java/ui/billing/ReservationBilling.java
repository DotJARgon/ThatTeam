package ui.billing;

import hotel_management.Reservation;

import javax.swing.*;
import java.awt.*;

public class ReservationBilling extends JPanel {
    private final Reservation reservation;
    public ReservationBilling(Reservation reservation) {
        super(new GridBagLayout());
        this.reservation = reservation;
        GridBagConstraints g = new GridBagConstraints();
        //g.fill = GridBagConstraints.BOTH;
        g.anchor = GridBagConstraints.CENTER;
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 1;
        g.gridheight = 1;

        JLabel idLabel = new JLabel("Reservation ID");
        this.add(idLabel, g);
        
    }
}
