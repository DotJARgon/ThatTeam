package ui.user;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hotel_management.HotelManagement;
import ui.UI;
import ui.custom.NavUpdate;
import user_services.Account;
import user_services.Clerk;
import user_services.Guest;

/**
 * The HelpGuestPage is meant for the Clerk account, it allows a
 * Clerk to select a Guest by the username and start helping them!
 */
public class HelpGuestPage extends JPanel implements NavUpdate {
	private JTextField text;
	private final JPanel container;

	/**
	 * This is the default constructor for the HelpGuestPage, this simply
	 * sets up the UI elements
	 */
	public HelpGuestPage(){
		this.setLayout(new GridBagLayout());
		container = new JPanel();
		int contW = 100;
		int contH = 30;
		container.setBounds(0, 0, contW, contH);
		container.setLayout(new GridLayout(2,2,15,15));
		//Enter username
		JLabel nameLabel = new JLabel("Enter Guest's username:");
		container.add(nameLabel);
		text = new JTextField();
		container.add(text);
		
		//Back button
		JButton backBut = new JButton("Back");
		backBut.addActionListener(e -> UI.navTo(UI.Routes.MAIN_PAGE));
		container.add(backBut);
		
		//Submit button
		JButton submitBut = new JButton("Submit");
		submitBut.addActionListener(e -> {
			Account a = HotelManagement.getHotelManagement().getUser(text.getText());
			if(a == null)
				JOptionPane.showMessageDialog(null,"User does not exist");
			else if(a instanceof Guest) {
				((Clerk)UI.getCurrentClient()).setGuest((Guest)a);
				UI.navTo(UI.Routes.MAIN_PAGE);
			}
			else
				JOptionPane.showMessageDialog(null,"User is not a guest");
		});
		container.add(submitBut);
		this.add(container, new GridBagConstraints());
	}

	/**
	 * navUpdate in HelpGuestPage does not do anything
	 */
	@Override
	public void navUpdate() {

	}

}
