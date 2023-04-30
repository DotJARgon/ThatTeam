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

public class AddCorporationPage extends JPanel implements NavUpdate{
	private JTextField text;
	
	public AddCorporationPage(){
		super();
		this.setLayout(new GridLayout(2,2,15,15));
		//Enter username
		JLabel nameLabel = new JLabel("Enter corporation's username:");
		this.add(nameLabel);
		text = new JTextField();
		this.add(text);
		
		//Back button
		JButton backBut = new JButton("Back");
		backBut.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				UI.navTo(UI.Routes.MAIN_PAGE);
			}
		});
		this.add(backBut);
		
		//Submit button
		JButton submitBut = new JButton("Submit");
		submitBut.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(text.getText().equals(""))
					JOptionPane.showMessageDialog(null,"Please enter a corporation name");
				else {
					((Guest)UI.getCurrentClient()).setCorporation(text.getText());
					JOptionPane.showMessageDialog(null,"Successfully set to " + ((Guest)UI.getCurrentClient()).getCorporation());
					UI.navTo(UI.Routes.MAIN_PAGE);
				}
			}
		});
		this.add(submitBut);
	}
	
	@Override
	public void navUpdate() {

	}

}
