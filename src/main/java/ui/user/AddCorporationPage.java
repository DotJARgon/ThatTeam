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

import ui.UI;
import ui.custom.NavUpdate;
import user_services.Guest;

public class AddCorporationPage extends JPanel implements NavUpdate{
	private JTextField text;
	private JPanel container;
	
	public AddCorporationPage(){
		this.setLayout(new GridBagLayout());
		container = new JPanel();
		int contW = 100;
		int contH = 30;
		container.setBounds(0, 0, contW, contH);
		container.setLayout(new GridLayout(2,2,15,15));
		//Enter username
		JLabel nameLabel = new JLabel("Enter Corporation's name:");
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
		container.add(submitBut);
		this.add(container, new GridBagConstraints());
	}
	
	@Override
	public void navUpdate() {

	}

}
