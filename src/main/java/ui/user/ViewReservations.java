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
import ui.billing.ReservationBilling;
import ui.custom.NavUpdate;
import user_services.Account;
import user_services.Clerk;
import user_services.Guest;

public class ViewReservations extends JPanel implements NavUpdate{
	private JTable resTable;
	private Guest g;
	private JPanel tablePanel;
	private JButton back, modify, remove, check;
	private GridBagConstraints c;
	public ViewReservations() {
		this.resTable = new JTable();
		this.tablePanel = new JPanel();
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
    	c.insets = new Insets(5,5,5,5);
        c.gridx = 0;
        c.gridy = 0;
    	
    	back = new JButton("Back");
    	back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UI.navTo(UI.Routes.MAIN_PAGE);
			}
    	});
    	this.add(back, c);
    	
    	c.gridx++;
    	modify = new JButton("Edit");
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
    	remove = new JButton("Cancel");
    	remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = resTable.getSelectedRow();
				int resID = Integer.parseInt((String) resTable.getValueAt(row, 0));
				if(row == -1)
					JOptionPane.showMessageDialog(null,"No reservation has been selected");
				else if(HotelManagement.getHotelManagement().getRes(resID).getStart().after(new Date())) {
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
    	check = new JButton("Check In/Out");
    	check.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = resTable.getSelectedRow();
				int resID = -1;
				if(row != -1)
					resID = Integer.parseInt((String) resTable.getValueAt(row, 0));
				Reservation res = HotelManagement.getHotelManagement().getAllReservations().get(resID);
				//Set Dates for checking later
				GregorianCalendar resStart = new GregorianCalendar();
		        resStart.setTime(res.getStart());
		        GregorianCalendar currTime = new GregorianCalendar();
		        resStart.setTime(new Date());
		        //determine action based on context
		        if(row == -1) {
		        	JOptionPane.showMessageDialog(null,"No reservation selected");
		        }
		        else if(!res.getCheckedIn() &&
		        		resStart.get(Calendar.YEAR) == currTime.get(Calendar.YEAR) &&
		                resStart.get(Calendar.MONTH) == currTime.get(Calendar.MONTH) && 
		                resStart.get(Calendar.DATE) == currTime.get(Calendar.DATE)) {
					HotelManagement.getHotelManagement().checkIn(resID);
					navUpdate();
		        }
		        else if(res.getCheckedIn() && !res.getCheckedOut()) {
		        	HotelManagement.getHotelManagement().checkOut(resID);
		        	JOptionPane.showMessageDialog(null, new ReservationBilling(res));
					navUpdate();
		        }
				else
					JOptionPane.showMessageDialog(null,"Guest cannot be checked in at this time");
			}
    	});
    	this.add(check, c);

    	this.tablePanel.setLayout(new BoxLayout(this.tablePanel, BoxLayout.Y_AXIS));
		this.tablePanel.add(new JScrollPane(this.resTable));
        JScrollPane scroll = new JScrollPane(this.resTable);
        this.tablePanel.add(scroll);

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
        	if(account instanceof Guest)
        		g = (Guest)account;
        	else if(account instanceof Clerk && ((Clerk)account).getGuest() != null)
        		g = ((Clerk)account).getGuest();
        	
        	if(g == null || account instanceof Guest) {
                c.weightx = .33;
                check.setVisible(false);
        	}
        	else{
        		c.weightx = .25;
                check.setVisible(true);
        	}

        	//populate table
            String[] columns = new String[] {
            	"Reservation ID", "Rooms", "Start Date", "End Date", "Checked In", "Checked Out", "Canceled"
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
	                data[ndx][4] = r.getCheckedIn();
	                data[ndx][5] = r.getCheckedOut();
	                data[ndx][6] = r.getCanceled();
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
	                data[ndx][4] = r.getValue().getCheckedIn();
	                data[ndx][5] = r.getValue().getCheckedOut();
	                data[ndx][6] = r.getValue().getCanceled();
	                ndx++;
	            }
            }
            
            this.resTable.setModel(new DefaultTableModel(data, columns));

            this.remove(this.back);
            this.remove(this.modify);
            this.remove(this.remove);
            this.remove(this.check);
            this.remove(this.resTable);
            
            c.gridx = 0;
            c.gridy = 0;
            c.ipadx = 0;
            c.ipady = 0;
            c.gridwidth = 1;
            this.add(this.back, c);
            c.gridx++;
            this.add(this.modify, c);
            c.gridx++;
            this.add(this.remove, c);
            c.gridx++;
            this.add(this.check, c);
            c.gridx = 0;
        	c.gridy++;
        	c.ipady = 100;
        	c.ipadx = 500;
        	c.gridwidth = 4;
            this.add(this.tablePanel, c);
        }
        else {
            this.tablePanel.setVisible(false);
            UI.navTo(UI.Routes.LOGIN);
        }
	}
}
