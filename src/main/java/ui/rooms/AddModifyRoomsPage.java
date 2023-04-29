package ui.rooms;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;

import hotel_management.HotelManagement;
import hotel_management.Room;
import ui.UI;
import ui.custom.NavUpdate;

public class AddModifyRoomsPage extends JPanel implements NavUpdate {
	private final JLabel numLbl, bedsLbl, smokeLbl, typeLbl, qualLbl;
	private Room newRoom;
	public AddModifyRoomsPage(){
		super();
		this.setLayout(new GridLayout(6,2,15,15));
		
		//Instructions
		//enterInfo = new JLabel("Please enter the room's new info:");
		//this.add(enterInfo);
		
		//Input room number
		numLbl = new JLabel("Room number:");
		this.add(numLbl);
		JSpinner inputNum = new JSpinner();
		this.add(inputNum);
		
		//Input number of beds
		bedsLbl = new JLabel("Number of beds:");
		this.add(bedsLbl);
		JSpinner inputBeds = new JSpinner();
		this.add(inputBeds);
		
		//Input smoking
		smokeLbl = new JLabel("Smoking permitted:");
		this.add(smokeLbl);
		JCheckBox smoking = new JCheckBox();
		this.add(smoking);
		
		//Input bed type
		JPanel bedTypePnl = new JPanel();
		bedTypePnl.setLayout(new BoxLayout(bedTypePnl, BoxLayout.Y_AXIS));
		typeLbl = new JLabel("Bed Type:");
		bedTypePnl.add(typeLbl);
		ButtonGroup bedTypeGroup = new ButtonGroup();
		JRadioButton twin = new JRadioButton("Twin");
		twin.setActionCommand("TWIN");
		JRadioButton full = new JRadioButton("Full");
		full.setActionCommand("FULL");
		JRadioButton queen = new JRadioButton("Queen");
		queen.setActionCommand("QUEEN");
		JRadioButton king = new JRadioButton("King");
		king.setActionCommand("KING");
		bedTypeGroup.add(twin);
		bedTypeGroup.add(full);
		bedTypeGroup.add(queen);
		bedTypeGroup.add(king);
		bedTypePnl.add(twin);
		bedTypePnl.add(full);
		bedTypePnl.add(queen);
		bedTypePnl.add(king);
		this.add(bedTypePnl);
		
		//Input quality level
		JPanel qualTypePnl = new JPanel();
		qualTypePnl.setLayout(new BoxLayout(qualTypePnl, BoxLayout.Y_AXIS));
		qualLbl = new JLabel("Room Quality:");
		qualTypePnl.add(qualLbl);
		ButtonGroup qualityGroup = new ButtonGroup();
		JRadioButton executive = new JRadioButton("Executive");
		executive.setActionCommand("EXECUTIVE");
		JRadioButton business = new JRadioButton("Business");
		business.setActionCommand("BUSINESS");
		JRadioButton comfort = new JRadioButton("Comfort");
		comfort.setActionCommand("COMFORT");
		JRadioButton economy = new JRadioButton("Economy");
		economy.setActionCommand("ECONOMY");
		qualityGroup.add(executive);
		qualityGroup.add(business);
		qualityGroup.add(comfort);
		qualityGroup.add(economy);
		qualTypePnl.add(executive);
		qualTypePnl.add(business);
		qualTypePnl.add(comfort);
		qualTypePnl.add(economy);
		this.add(qualTypePnl);
		
		//Back
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		UI.navTo(UI.Routes.VIEW_ROOMS);
        	}
        });
		this.add(backButton);

		//Submit
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		HotelManagement.getHotelManagement().addModifyRoom((int)inputNum.getValue(),(int)inputBeds.getValue(),
        				Room.BedType.fromString(bedTypeGroup.getSelection().getActionCommand()),
        				smoking.isSelected(),Room.QualityType.fromString(qualityGroup.getSelection().getActionCommand()));
        		UI.navTo(UI.Routes.VIEW_ROOMS);
        	}
        });
		this.add(submitButton);
		this.setVisible(false);
	}
	@Override
	public void navUpdate() {
		this.setVisible(true);
	}
}
