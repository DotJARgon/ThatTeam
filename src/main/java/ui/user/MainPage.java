package ui.user;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import hotel_management.HotelManagement;
import ui.UI;
import ui.custom.NavUpdate;
import user_services.Account;
import user_services.Clerk;
import user_services.Guest;

/**
 * MainPage is the central navigation UI element after logging in or registering a new account! It allows
 * for a user to easily get to any page from on convenient center. It only displays the buttons to go to pages
 * that this user has permission to go to! Like for example, the Admin can only log out, create a clerk, or modify
 * their profile info! While a clerk cannot create a clerk however can view rooms or even help a guest!
 */
public class MainPage extends JPanel implements NavUpdate {
	private final JButton logout, reserveroom, viewroom, helpguest, stophelp, addclerk, viewreses, addcorp, paycorp, modprofile;
	private final JLabel username;

	/**
	 * This is the MainPage default constructor, all it does is initializes all of the buttons, which will
	 * later be hidden or shown accordingly!
	 */
	public MainPage() {
		super();
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.insets = new Insets(10,10,10,10);
		c.ipadx = 20;
		c.ipady = 10;
		c.fill = GridBagConstraints.BOTH;

		this.username = new JLabel("",SwingConstants.CENTER);

		this.logout = new JButton("Logout");
		this.reserveroom = new JButton("Make Reservation");
		this.viewroom = new JButton("View Rooms");
		this.helpguest = new JButton("Help Guest");
		this.stophelp = new JButton("Stop Helping");
		this.addclerk = new JButton("Add Clerk");
		this.viewreses = new JButton("View Reservations");
		this.addcorp = new JButton("Add a Corporation");
		this.paycorp = new JButton("Pay Corporate Bill");
		this.modprofile = new JButton("Modify Profile");


		this.logout.addActionListener(e -> {
			UI.updateCurrentClient(null);
			UI.navTo(UI.Routes.LOGIN);
		});
		this.reserveroom.addActionListener(e -> UI.navTo(UI.Routes.MAKE_RESERVATIONS));
		this.viewroom.addActionListener(e ->
		UI.navTo(UI.Routes.VIEW_ROOMS));
		this.helpguest.addActionListener(e -> UI.navTo(UI.Routes.ADD_GUEST));
		this.stophelp.addActionListener(e -> {
			JOptionPane.showMessageDialog(null, "You are no longer helping " + ((Clerk)UI.getCurrentClient()).getGuest().getUsername());
			((Clerk)UI.getCurrentClient()).setGuest(null);
			stophelp.setVisible(false);
			helpguest.setVisible(true);
			this.username.setText(UI.getCurrentClient().getUsername());
		});
		this.addclerk.addActionListener(e -> UI.navTo(UI.Routes.ADD_CLERK));
		this.viewreses.addActionListener(e -> UI.navTo(UI.Routes.VIEW_RESERVATIONS));
		this.addcorp.addActionListener(e -> UI.navTo(UI.Routes.ADD_CORP));
		this.paycorp.addActionListener(e -> {
			Guest g = null;
			if(UI.getCurrentClient() instanceof Guest)
				g = (Guest) UI.getCurrentClient();
			else
				g = ((Clerk) UI.getCurrentClient()).getGuest();
			for(int rID: g.getReservations()) {
				if(HotelManagement.getHotelManagement().getAllReservations().get(rID).getCheckedOut()) {
					HotelManagement.getHotelManagement().getAllReservations().get(rID).getBilling().setPaid(true);
					JOptionPane.showMessageDialog(null, "Reservation " + rID + " has been paid");
				}
			}
		});
		this.modprofile.addActionListener(e -> UI.navTo(UI.Routes.MODIFY_PROFILE));


		this.add(this.username, c);
		c.gridy++;
		this.add(this.logout, c);
		c.gridy++;
		this.add(this.reserveroom, c);
		c.gridy++;
		this.add(this.viewroom, c);
		c.gridy++;
		this.add(this.helpguest, c);
		c.gridy++;
		this.add(this.stophelp, c);
		c.gridy++;
		this.add(this.addclerk, c);
		c.gridy++;
		this.add(this.viewreses, c);
		c.gridy++;
		this.add(this.addcorp, c);
		c.gridy++;
		this.add(this.paycorp, c);
		c.gridy++;
		this.add(this.modprofile, c);
	}

	/**
	 * navUpdate in MainPage either hides or shows the buttons according to what kind
	 * of Account is logged in, it also updates the text label at the top to either denote
	 * the name of the user, or the name of the clerk plus the guest they are helping!
	 */
	@Override
	public void navUpdate() {
		Account curr = UI.getCurrentClient();
		if(curr != null) {
			String name = curr.getFirstName() + " " + curr.getLastName();
			if(curr instanceof Clerk && ((Clerk) curr).getGuest() != null)
				this.username.setText(name + ", helping " + ((Clerk) curr).getGuest().getUsername());
			else
				this.username.setText(name);
			if(curr instanceof Guest g) {
				this.reserveroom.setVisible(true);
				this.helpguest.setVisible(false);
				this.stophelp.setVisible(false);
				this.viewroom.setVisible(false);
				this.addclerk.setVisible(false);
				this.viewreses.setVisible(true);
				this.modprofile.setVisible(true);
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
				this.modprofile.setVisible(true);
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
				this.modprofile.setVisible(true);
			}
		}
	}
}
