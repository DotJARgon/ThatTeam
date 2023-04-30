package ui.user;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import hotel_management.HotelManagement;
import hotel_management.Reservation;
import ui.UI;
import ui.custom.NavUpdate;
import user_services.Account;
import user_services.Clerk;
import user_services.Guest;

public class ViewReservations extends JPanel implements NavUpdate{
	private JTable resTable;
	private Guest g;
	JPanel tablePanel;
	public ViewReservations() {
		this.resTable = new JTable();
		this.tablePanel = new JPanel();
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = .25;
    	c.insets = new Insets(5,5,5,5);
    	
    	JButton back = new JButton("Back");
    	back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UI.navTo(UI.Routes.MAIN_PAGE);
			}
    	});
    	this.add(back, c);
    	
    	c.gridx++;
    	JButton modify = new JButton("Edit");
    	modify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = resTable.getSelectedRow();
				int resID = Integer.parseInt((String) resTable.getValueAt(row, 0));
				UI.getUI().setModResID(resID);
				UI.navTo(UI.Routes.MODIFY_RESERVATION);
			}
    	});
    	this.add(modify, c);

    	c.gridx++;
    	JButton remove = new JButton("Remove");
    	remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = resTable.getSelectedRow();
				int resID = Integer.parseInt((String) resTable.getValueAt(row, 0));
				if(HotelManagement.getHotelManagement().getRes(resID).getStart().after(new Date())) {
					System.out.println("REMOVING");
					HotelManagement.getHotelManagement().cancelReservation(resID, g);
					int modelRow = resTable.convertRowIndexToModel(row);
					DefaultTableModel model = (DefaultTableModel)resTable.getModel();
					model.removeRow(modelRow);
				}
				else
					JOptionPane.showMessageDialog(null,"Reservation cannot be canceled because it is too late");
			}
    	});
    	this.add(remove, c);
    	
    	c.gridx++;
    	JButton check = new JButton("Check In/Out");
    	check.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = resTable.getSelectedRow();
				int resID = Integer.parseInt((String) resTable.getValueAt(row, 0));
				GregorianCalendar resStart = new GregorianCalendar();
		        resStart.setTime(HotelManagement.getHotelManagement().getAllReservations().get(resID).getStart());
		        GregorianCalendar currTime = new GregorianCalendar();
		        resStart.setTime(new Date());
		        if(row == -1) {
		        	JOptionPane.showMessageDialog(null,"No reservation selected");
		        }
		        else if(HotelManagement.getHotelManagement().getAllReservations().get(resID).getCheckedIn() &&
		        		resStart.get(Calendar.YEAR) == currTime.get(Calendar.YEAR) &&
		                resStart.get(Calendar.MONTH) == currTime.get(Calendar.MONTH) && resStart.get(Calendar.DATE) == currTime.get(Calendar.DATE))
					HotelManagement.getHotelManagement().checkIn(resID);
		        else if(!HotelManagement.getHotelManagement().getAllReservations().get(resID).getCheckedIn())
		        	HotelManagement.getHotelManagement().checkOut(resID);
				else
					JOptionPane.showMessageDialog(null,"Guest cannot be checked in at this time");
			}
    	});
    	this.add(check, c);
    
		

    	c.gridx = 0;
    	c.gridy++;
    	c.ipady = 100;
    	c.ipadx = 500;
    	c.gridwidth = 4;
		this.tablePanel.setLayout(new BoxLayout(this.tablePanel, BoxLayout.Y_AXIS));
		this.tablePanel.add(new JScrollPane(this.resTable));
		this.resTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.add(tablePanel, c);
		
        this.tablePanel.setVisible(false);
	}

	@Override
	public void navUpdate() {
		this.tablePanel.setVisible(true);
		Account account = UI.getCurrentClient();
        if(account != null) {
        	g = null;
        	if(UI.getCurrentClient() instanceof Guest) 
        		g = (Guest)UI.getCurrentClient();
        	else if(UI.getCurrentClient() instanceof Clerk && ((Clerk)UI.getCurrentClient()).getGuest() != null)
        		g = ((Clerk)UI.getCurrentClient()).getGuest();

        	//populate table
            String[] columns = new String[] {
            	"Reservation ID", "Rooms", "Start Date", "End Date"
            };
            Object[][] data;
            if(g != null) {
            	data = new Object[g.getReservations().size()][columns.length];
            	int ndx = 0;
            	for(int id : g.getReservations()) {
            		Reservation r = HotelManagement.getHotelManagement().getAllReservations().get(id);
            		data[ndx][0] = Integer.toString(r.getID());
	                data[ndx][1] = Arrays.toString(r.getRooms());
	                data[ndx][2] = r.getStart().toString();
	                data[ndx][3] = r.getEnd().toString();
	                ndx++;
            	}
            }
            else {
            	data = new Object[HotelManagement.getHotelManagement().getAllReservations().size()][columns.length];
	            int ndx = 0;
	            for(Map.Entry<Integer,Reservation> r: HotelManagement.getHotelManagement().getAllReservations().entrySet()) {
	            	data[ndx][0] = Integer.toString(r.getValue().getID());
	                data[ndx][1] = Arrays.toString(r.getValue().getRooms());
	                data[ndx][2] = r.getValue().getStart().toString();
	                data[ndx][3] = r.getValue().getEnd().toString();
	                ndx++;
	            }
            }
            

            JScrollPane scroll = new JScrollPane(this.resTable);
            this.tablePanel.add(scroll);
            this.resTable.setModel(new DefaultTableModel(data, columns));
        }
        else {
            this.tablePanel.setVisible(false);
            UI.navTo(UI.Routes.LOGIN);
        }
	}
}
