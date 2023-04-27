package ui.rooms;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;

public class AddModifyRoomsPage extends JPanel {
	private final JLabel enterInfo, numLbl, bedsLbl, smokeLbl, typeLbl, qualLbl;
	AddModifyRoomsPage(Component bedTypeLbl){
		super();
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		this.setLayout(new GridBagLayout());
		
		//Instructions
		enterInfo = new JLabel("Please enter the room's new info:");
		this.add(enterInfo);
		
		//Input room number
		c.gridy++;
		numLbl = new JLabel("Room number:");
		this.add(numLbl);
		c.gridx = 1;
		JSpinner inputNum = new JSpinner();
		this.add(inputNum);
		
		//Input number of beds
		c.gridy++;
		c.gridx = 0;
		bedsLbl = new JLabel("Number of beds:");
		this.add(bedsLbl);
		c.gridx = 1;
		JSpinner inputBeds = new JSpinner();
		this.add(inputBeds);
		
		//Input smoking
		c.gridy++;
		c.gridx = 0;
		smokeLbl = new JLabel("Smoking permitted:");
		this.add(smokeLbl);
		c.gridx = 1;
		JCheckBox smoking = new JCheckBox();
		this.add(smoking);
		
		//Input bed type
		c.gridy++;
		c.gridx = 0;
		JPanel bedTypePnl = new JPanel();
		bedTypePnl.setLayout(new BoxLayout(bedTypePnl, BoxLayout.Y_AXIS));
		typeLbl = new JLabel("Bed Type:");
		bedTypePnl.add(typeLbl);
		ButtonGroup bedTypeGroup = new ButtonGroup();
		JRadioButton twin = new JRadioButton("Twin");
		JRadioButton full = new JRadioButton("Full");
		JRadioButton queen = new JRadioButton("Queen");
		JRadioButton king = new JRadioButton("King");
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
		c.gridx++;
		JPanel qualTypePnl = new JPanel();
		bedTypePnl.setLayout(new BoxLayout(bedTypePnl, BoxLayout.Y_AXIS));
		qualLbl = new JLabel("Room Quality:");
		bedTypePnl.add(qualLbl);
		ButtonGroup qualityGroup = new ButtonGroup();
		JRadioButton executive = new JRadioButton("Executive");
		JRadioButton business = new JRadioButton("Business");
		JRadioButton comfort = new JRadioButton("Comfort");
		JRadioButton economy = new JRadioButton("Economy");
		qualityGroup.add(executive);
		qualityGroup.add(business);
		qualityGroup.add(comfort);
		qualityGroup.add(economy);
		qualTypePnl.add(executive);
		qualTypePnl.add(business);
		qualTypePnl.add(comfort);
		qualTypePnl.add(economy);
		this.add(qualTypePnl);
	}
}
