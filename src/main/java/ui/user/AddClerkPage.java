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
import user_services.Guest;

public class AddClerkPage extends JPanel implements NavUpdate{
	private final JTextField text;
	private JPanel container;
	public AddClerkPage(){
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
		backBut.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				UI.navTo(UI.Routes.MAIN_PAGE);
			}
		});
		container.add(backBut);
		
		//Submit button
		JButton submitBut = new JButton("Add Clerk");
		submitBut.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Account a = HotelManagement.getHotelManagement().getUser(text.getText());
				if(a == null)
					JOptionPane.showMessageDialog(null,"User does not exist");
				else if(a instanceof Guest) {
					HotelManagement.getHotelManagement().promoteAccountToClerk(a.getUsername());;
					UI.navTo(UI.Routes.MAIN_PAGE);
				}
				else
					JOptionPane.showMessageDialog(null,"User is not a guest");
			}
		});
		container.add(submitBut);
		this.add(container, new GridBagConstraints());
	}
	
	@Override
	public void navUpdate() {
		// TODO Auto-generated method stub
		
	}

}
