package ui.rooms;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;

import hotel_management.HotelManagement;
import hotel_management.Room;
import ui.UI;
import ui.custom.NavUpdate;

/**
 * AddModifyRoomsPage is a page for editing a room, it also can display the information of
 * a room that has been set. The user can either modify that room, or if they did not
 * select a room previously or they changed the ID field, then a new room will be
 * created
 */
public class AddModifyRoomsPage extends JPanel implements NavUpdate {
	private final JLabel numLbl, bedsLbl, smokeLbl, typeLbl, qualLbl;
	private final JSpinner inputNum, inputBeds;
	private final JCheckBox smoking;
	private final JRadioButton executive, business, comfort, economy;
	private final JRadioButton twin, full, queen, king;
	private final ButtonGroup qualityGroup, bedTypeGroup;
	private Room selectedRoom;
	private final JPanel container;

	/**
	 * This is the default constructor of AddModifyRoomsPage, it simply
	 * puts the UI elements in their place
	 */
	public AddModifyRoomsPage(){
		super();
		this.setLayout(new GridBagLayout());
		container = new JPanel();
		int contW = 100;
		int contH = 30;
		container.setBounds(0, 0, contW, contH);
		container.setLayout(new GridLayout(6,2,15,15));
		
		//Instructions
		//enterInfo = new JLabel("Please enter the room's new info:");
		//this.add(enterInfo);
		
		//Input room number
		numLbl = new JLabel("Room number:");
		container.add(numLbl);
		inputNum = new JSpinner();
		container.add(inputNum);
		
		//Input number of beds
		bedsLbl = new JLabel("Number of beds:");
		container.add(bedsLbl);
		inputBeds = new JSpinner();
		container.add(inputBeds);
		
		//Input smoking
		smokeLbl = new JLabel("Smoking permitted:");
		container.add(smokeLbl);
		smoking = new JCheckBox();
		container.add(smoking);
		
		//Input bed type
		JPanel bedTypePnl = new JPanel();
		bedTypePnl.setLayout(new BoxLayout(bedTypePnl, BoxLayout.Y_AXIS));
		typeLbl = new JLabel("Bed Type:");
		bedTypePnl.add(typeLbl);
		bedTypeGroup = new ButtonGroup();
		twin = new JRadioButton("Twin");
		twin.setActionCommand("TWIN");
		full = new JRadioButton("Full");
		full.setActionCommand("FULL");
		queen = new JRadioButton("Queen");
		queen.setActionCommand("QUEEN");
		king = new JRadioButton("King");
		king.setActionCommand("KING");
		bedTypeGroup.add(twin);
		bedTypeGroup.add(full);
		bedTypeGroup.add(queen);
		bedTypeGroup.add(king);
		bedTypePnl.add(twin);
		bedTypePnl.add(full);
		bedTypePnl.add(queen);
		bedTypePnl.add(king);
		container.add(bedTypePnl);
		
		//Input quality level
		JPanel qualTypePnl = new JPanel();
		qualTypePnl.setLayout(new BoxLayout(qualTypePnl, BoxLayout.Y_AXIS));
		qualLbl = new JLabel("Room Quality:");
		qualTypePnl.add(qualLbl);
		qualityGroup = new ButtonGroup();
		executive = new JRadioButton("Executive");
		executive.setActionCommand("EXECUTIVE");
		business = new JRadioButton("Business");
		business.setActionCommand("BUSINESS");
		comfort = new JRadioButton("Comfort");
		comfort.setActionCommand("COMFORT");
		economy = new JRadioButton("Economy");
		economy.setActionCommand("ECONOMY");
		qualityGroup.add(executive);
		qualityGroup.add(business);
		qualityGroup.add(comfort);
		qualityGroup.add(economy);
		qualTypePnl.add(executive);
		qualTypePnl.add(business);
		qualTypePnl.add(comfort);
		qualTypePnl.add(economy);
		container.add(qualTypePnl);
		
		//Back
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		UI.navTo(UI.Routes.VIEW_ROOMS);
        	}
        });
		container.add(backButton);

		//Submit
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if((int)inputBeds.getValue() < 0 || bedTypeGroup.getSelection() == null || qualityGroup.getSelection() == null)
        			JOptionPane.showMessageDialog(null, "Please enter valid inputs for all fields");
	        	else {
	        		HotelManagement.getHotelManagement().addModifyRoom((int)inputNum.getValue(),(int)inputBeds.getValue(),
	        				Room.BedType.fromString(bedTypeGroup.getSelection().getActionCommand()),
	        				smoking.isSelected(),Room.QualityType.fromString(qualityGroup.getSelection().getActionCommand()));
	        		UI.navTo(UI.Routes.VIEW_ROOMS);
	        	}
        	}
        });
		container.add(submitButton);
		
		this.add(container);
	}

	/**
	 * navUpdate in AddModifyRoomsPage clears out all of the information
	 * in it, otherwise, it takes the current selected room, and populates
	 * this page using the values in it
	 */
	@Override
	public void navUpdate() {
		inputNum.setValue(0);
		inputBeds.setValue(0);
		smoking.setSelected(false);
		qualityGroup.clearSelection();
		bedTypeGroup.clearSelection();
		if(selectedRoom != null) {
			inputNum.setValue(selectedRoom.getID());
			inputBeds.setValue(selectedRoom.getNumBeds());
			smoking.setSelected(selectedRoom.getCanSmoke());

			switch(selectedRoom.getQualityType()) {
				case EXECUTIVE: executive.setSelected(true); break;
				case BUSINESS: business.setSelected(true); break;
				case COMFORT: comfort.setSelected(true); break;
				case ECONOMY: economy.setSelected(true); break;
			}

			switch(selectedRoom.getBedType()) {
				case TWIN: twin.setSelected(true); break;
				case FULL: full.setSelected(true); break;
				case QUEEN: queen.setSelected(true); break;
				case KING: king.setSelected(true); break;
			}
		}
	}

	/**
	 * setSelectedRoom sets the currently selected room of this page
	 * @param selectedRoom The room to be made the selected page
	 */
	public void setSelectedRoom(Room selectedRoom) {
		this.selectedRoom = selectedRoom;
	}
}
