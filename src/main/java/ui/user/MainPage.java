package ui.user;

import ui.UI;
import ui.custom.NavUpdate;
import user_services.Account;
import user_services.Clerk;
import user_services.Guest;

import javax.swing.*;

public class MainPage extends JPanel implements NavUpdate {
    private final JButton logout, reserveroom, viewroom, helpguest, stophelp;
    public MainPage() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.logout = new JButton("Logout");
        this.reserveroom = new JButton("Make Reservation");
        this.viewroom = new JButton("View Rooms");
        this.helpguest = new JButton("Help Guest");
        this.stophelp = new JButton("Stop Helping");
        
        this.logout.addActionListener(e -> {
            UI.updateCurrentClient(null);
            UI.navTo(UI.Routes.LOGIN);
        });
        this.reserveroom.addActionListener(e -> UI.navTo(UI.Routes.MAKE_RESERVATIONS));
        this.viewroom.addActionListener(e -> UI.navTo(UI.Routes.VIEW_ROOMS));
    	this.helpguest.addActionListener(e -> UI.navTo(UI.Routes.ADD_GUEST));
    	this.stophelp.addActionListener(e -> ((Clerk)UI.getCurrentClient()).setGuest(null));

        this.add(this.logout);
        this.add(this.reserveroom);
        this.add(this.viewroom);
    	this.add(this.helpguest);
    	this.add(this.stophelp);
    }

    @Override
    public void navUpdate() {
        Account curr = UI.getCurrentClient();
        if(curr != null) {
            if(curr instanceof Guest g) {
            	this.helpguest.setVisible(false);
        		this.stophelp.setVisible(false);
        		this.viewroom.setVisible(false);
            }
            else if(curr instanceof Clerk c) {
            	if(c.getGuest() == null) {
            		this.helpguest.setVisible(true);
            		this.stophelp.setVisible(false);
            	}
            	else {
            		this.helpguest.setVisible(false);
            		this.stophelp.setVisible(true);
            	}
            }
            else {

            }
        }
    }
}
