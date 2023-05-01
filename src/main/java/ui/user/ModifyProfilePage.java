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
import user_services.UserLoader;

public class ModifyProfilePage extends JPanel implements NavUpdate{
	private JTextField nameTxt, firstTxt, lastTxt;
	private final JPanel container;
	
	public ModifyProfilePage(){
		this.setLayout(new GridBagLayout());
		container = new JPanel();
		int contW = 100;
		int contH = 30;
		container.setBounds(0, 0, contW, contH);
		container.setLayout(new GridLayout(4,2,15,15));
		
		//Enter username
		JLabel nameLabel = new JLabel("Enter new username:");
		container.add(nameLabel);
		nameTxt = new JTextField();
		container.add(nameTxt);
		
		//Enter first name
		JLabel firstLabel = new JLabel("Enter your first name:");
		container.add(firstLabel);
		firstTxt = new JTextField();
		container.add(firstTxt);
		
		//Enter last name
		JLabel lastLabel = new JLabel("Enter your last name:");
		container.add(lastLabel);
		lastTxt = new JTextField();
		container.add(lastTxt);
		
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
		JButton submitBut = new JButton("Submit");
		submitBut.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Account c = UI.getCurrentClient();
				HotelManagement.getHotelManagement().modifyProfile(c.getUsername(), nameTxt.getText(), firstTxt.getText(), lastTxt.getText());
				UI.navTo(UI.Routes.MAIN_PAGE);
			}
		});
		container.add(submitBut);
		this.add(container, new GridBagConstraints());
	}
	
	@Override
	public void navUpdate() {
		nameTxt.setText(UI.getCurrentClient().getUsername());
		firstTxt.setText(UI.getCurrentClient().getFirstName());
		lastTxt.setText(UI.getCurrentClient().getLastName());
	}

}
