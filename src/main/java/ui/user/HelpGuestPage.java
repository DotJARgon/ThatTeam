package ui.user;

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

public class HelpGuestPage extends JPanel implements NavUpdate{
	public HelpGuestPage(){
		this.setLayout(new GridLayout(2,2,15,15));
		JLabel nameLabel = new JLabel("Enter Guest's username:");
		this.add(nameLabel);
		JTextField text = new JTextField();
		this.add(text);
		JButton backBut = new JButton("Back");
		backBut.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				UI.navTo(UI.Routes.LOGIN); //TODO: MainPage after merge with master
			}
		});
		this.add(backBut);
		JButton submitBut = new JButton("Submit");
		submitBut.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Account a = HotelManagement.getHotelManagement().getUser(text.getText());
				if(a == null)
					JOptionPane.showMessageDialog(null,"User does not exist");
				else if(a instanceof Guest) {
					((Clerk)UI.getCurrentClient()).setGuest((Guest)a);
					UI.navTo(UI.Routes.MAKE_RESERVATIONS); //TODO: Set to MAIN_PAGE
				}
				else
					JOptionPane.showMessageDialog(null,"User is not a guest");
			}
		});
		this.add(submitBut);
	}
	
	@Override
	public void navUpdate() {

	}

}
