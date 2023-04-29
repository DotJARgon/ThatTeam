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

public class AddClerkPage extends JPanel implements NavUpdate{
	private final JTextField nameText, passText, confText;
	public AddClerkPage(){
		this.setLayout(new GridLayout(4,2,15,15));
		//username
		JLabel nameLabel = new JLabel("username:");
		this.add(nameLabel);
		nameText = new JTextField();
		this.add(nameText);

		//password
		JLabel passLabel = new JLabel("password:");
		this.add(passLabel);
		passText = new JTextField();
		this.add(passText);
		
		//confirm password
		JLabel confLabel = new JLabel("confirm pasword:");
		this.add(confLabel);
		confText = new JTextField();
		this.add(confText);
		
		//back button
		JButton backBut = new JButton("Back");
		backBut.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				UI.navTo(UI.Routes.MAIN_PAGE);
			}
		});
		this.add(backBut);
		
		//submit button
		JButton submitBut = new JButton("Create Clerk");
		submitBut.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(nameText.getText().equals("") || passText.getText().equals("") || confText.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Please fill all fields");
				else if(!passText.getText().equals(confText.getText()))
					JOptionPane.showMessageDialog(null, "Passwords do not match");
				else if(HotelManagement.getHotelManagement().getUser(nameText.getText()) != null)
					JOptionPane.showMessageDialog(null, "Account already exists");
				else
					HotelManagement.getHotelManagement().registerUser(nameText.getText(), passText.getText());
			}
		});
		this.add(submitBut);
	}
	
	@Override
	public void navUpdate() {
		// TODO Auto-generated method stub
		
	}

}
