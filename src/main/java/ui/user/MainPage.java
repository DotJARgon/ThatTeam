package ui.user;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ui.UI;
import ui.custom.NavUpdate;
import user_services.Account;
import user_services.Clerk;
import user_services.Guest;

public class MainPage extends JPanel implements NavUpdate {
    private final JButton logout, reserveroom, viewroom, helpguest, stophelp, addclerk, viewreses, addcorp, paycorp;
    public MainPage() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.logout = new JButton("Logout");
        this.reserveroom = new JButton("Make Reservation");
        this.viewroom = new JButton("View Rooms");
        this.helpguest = new JButton("Help Guest");
        this.stophelp = new JButton("Stop Helping");
        this.addclerk = new JButton("Add Clerk");
        this.viewreses = new JButton("View Reservations");
        this.addcorp = new JButton("Add a Corporation");
        this.paycorp = new JButton("Pay Corporate Bill");
        
        
        this.logout.addActionListener(e -> {
            UI.updateCurrentClient(null);
            UI.navTo(UI.Routes.LOGIN);
        });
        this.reserveroom.addActionListener(e -> UI.navTo(UI.Routes.MAKE_RESERVATIONS));
        this.viewroom.addActionListener(e -> UI.navTo(UI.Routes.VIEW_ROOMS));
    	this.helpguest.addActionListener(e -> UI.navTo(UI.Routes.ADD_GUEST));
    	this.stophelp.addActionListener(e -> {
    		JOptionPane.showMessageDialog(null, "You are no longer helping " + ((Clerk)UI.getCurrentClient()).getGuest().getUsername());
    		((Clerk)UI.getCurrentClient()).setGuest(null);
    		stophelp.setVisible(false);
    		helpguest.setVisible(true);
    	});
    	this.addclerk.addActionListener(e -> UI.navTo(UI.Routes.ADD_CLERK));
    	this.viewreses.addActionListener(e -> UI.navTo(UI.Routes.VIEW_RESERVATIONS));
    	this.addcorp.addActionListener(e -> UI.navTo(UI.Routes.ADD_CORP));
    	this.paycorp.addActionListener(e -> UI.navTo(UI.Routes.ADD_CORP)); //TODO
    	

        this.add(this.logout);
        this.add(this.reserveroom);
        this.add(this.viewroom);
    	this.add(this.helpguest);
    	this.add(this.stophelp);
    	this.add(this.addclerk);
    	this.add(this.viewreses);
    	this.add(this.addcorp);
    	this.add(this.paycorp);
    }

    @Override
    public void navUpdate() {
        Account curr = UI.getCurrentClient();
        if(curr != null) {
            if(curr instanceof Guest g) {
            	this.reserveroom.setVisible(true);
            	this.helpguest.setVisible(false);
        		this.stophelp.setVisible(false);
        		this.viewroom.setVisible(false);
        		this.addclerk.setVisible(false);
        		this.viewreses.setVisible(true);
        		if(((Guest)UI.getCurrentClient()).getCorporation().equals("")) {
        			this.paycorp.setVisible(false);
        			this.addcorp.setVisible(true);
        		}
        		else {
        			this.paycorp.setVisible(true);
        			this.addcorp.setVisible(false);
        		}
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
            	this.reserveroom.setVisible(true);
        		this.viewroom.setVisible(true);
        		this.addclerk.setVisible(false);
        		this.viewreses.setVisible(true);
    			this.paycorp.setVisible(false);
    			this.addcorp.setVisible(false);
            }
            else {
            	this.reserveroom.setVisible(false);
            	this.helpguest.setVisible(false);
        		this.stophelp.setVisible(false);
        		this.viewroom.setVisible(false);
        		this.addclerk.setVisible(true);
        		this.viewreses.setVisible(false);
    			this.paycorp.setVisible(false);
    			this.addcorp.setVisible(false);
            }
        }
    }
}
