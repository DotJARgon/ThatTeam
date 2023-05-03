package ui.user;

import java.awt.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

public class ViewReservations extends JPanel implements NavUpdate {
	private JTable resTable;
	private Guest g;
	private JPanel tablePanel;
	private JButton back, modify, generate, remove, check;
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
		back.addActionListener(e -> {
			UI.navTo(UI.Routes.MAIN_PAGE);
		});
		this.add(back, c);

		c.gridx++;
		modify = new JButton("Edit");
		modify.addActionListener(e -> {
			int row = resTable.getSelectedRow();
			int resID = Integer.parseInt((String) resTable.getValueAt(row, 0));
			UI.getUI().setModResID(resID);
			UI.navTo(UI.Routes.MODIFY_RESERVATION);
		});
		this.add(modify, c);

		c.gridx++;
		generate = new JButton("Billing");
		generate.addActionListener(e -> {
			int row = resTable.getSelectedRow();
			if(row != -1) {
				int resID = Integer.parseInt((String) resTable.getValueAt(row, 0));
				HotelManagement.getHotelManagement().getRes(resID).getBilling().calculateTotalCost();
				JOptionPane.showMessageDialog(null, new ReservationBilling(HotelManagement.getHotelManagement().getRes(resID)));
			}
			else
				JOptionPane.showMessageDialog(null, "No reservation selected");
		});
		this.add(generate, c);

		c.gridx++;
		remove = new JButton("Cancel");
		remove.addActionListener(e -> {
			int row = resTable.getSelectedRow();
			int resID = Integer.parseInt((String) resTable.getValueAt(row, 0));
			if(row == -1)
				JOptionPane.showMessageDialog(null,"No reservation has been selected");
			else if(HotelManagement.getHotelManagement().getRes(resID).getStart().after(Calendar.getInstance().getTime())) {

				if(HotelManagement.getHotelManagement().hasCancellationFee(resID)) {
					Object[] options = {"OK", "CANCEL"};
					int option = JOptionPane.showOptionDialog(null,
							"Cancellation will result in 80% fee, do you still want to cancel?",
							"Late Cancellation Fee",
							JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
							null, options, options[0]);
					if(option == 0) {
						HotelManagement.getHotelManagement().cancelReservation(resID, g);
						int modelRow = resTable.convertRowIndexToModel(row);
						DefaultTableModel model = (DefaultTableModel)resTable.getModel();
						model.removeRow(modelRow);
					}
				}
				else {
					HotelManagement.getHotelManagement().cancelReservation(resID, g);
					int modelRow = resTable.convertRowIndexToModel(row);
					DefaultTableModel model = (DefaultTableModel)resTable.getModel();
					model.removeRow(modelRow);
				}

			}
			else
				JOptionPane.showMessageDialog(null,"Reservation cannot be canceled because it is too late");
		});
		this.add(remove, c);

		c.gridx++;
		check = new JButton("Check In/Out");
		check.addActionListener(e -> {
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

			if(account instanceof Guest guest)
				g = guest;
			else if(account instanceof Clerk clerk && clerk.getGuest() != null) {
				g = clerk.getGuest();

				//this is to update the reference to guest that is possessed by Clerk, it may have been
				//causing issues
				Account database_account = HotelManagement.getHotelManagement().getUser(g.getUsername());
				if(database_account instanceof Guest guest) {
					g = guest;
					clerk.setGuest(g);
				}
				else {
					//something went wrong, somehow the user
					//is not the same as a previous session perhaps, which
					//could happen if they change their account information
					g = null;
					clerk.setGuest(null);
				}
			}

			if(g == null || account instanceof Guest) {
				c.weightx = .25;
				check.setVisible(false);
			}
			else{
				c.weightx = .2;
				check.setVisible(true);
			}

			//populate table
			String[] columns = new String[] {
					"Reservation ID", "Rooms", "Reserved On", "Start Date", "End Date", "Checked In", "Checked Out", "Canceled"
			};
			Object[][] data = (g != null) ?
					new Object[g.getReservations().size()][columns.length]
					: new Object[HotelManagement.getHotelManagement().getAllReservations().size()][columns.length];


			//grab reference to all of the reservations
			ConcurrentHashMap<Integer, Reservation> allReservations = HotelManagement.getHotelManagement().getAllReservations();

			//get the set of ids
			Set<Integer> reservations = (g != null) ?
					g.getReservations().stream().collect(Collectors.toSet())
					:
					allReservations.keySet();

			//iteration over all of them, and parse them
			int ndx = 0;
			for(Integer id : reservations) {
				Reservation r = allReservations.get(id);
				if(r != null) {
					data[ndx][0] = Integer.toString(r.getID());
					data[ndx][1] = Arrays.toString(r.getRooms());
					data[ndx][2] = r.getReserved().toString();
					data[ndx][3] = r.getStart().toString();
					data[ndx][4] = r.getEnd().toString();
					data[ndx][5] = r.getCheckedIn();
					data[ndx][6] = r.getCheckedOut();
					data[ndx][7] = r.getCanceled();
					ndx++;
				}
			}

			DefaultTableModel table = new DefaultTableModel(data, columns);

			this.resTable.setModel(table);

			this.remove(this.back);
			this.remove(this.modify);
			this.remove(this.generate);
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
			this.add(this.generate, c);
			c.gridx++;
			this.add(this.remove, c);
			c.gridx++;
			this.add(this.check, c);
			c.gridx = 0;
			c.gridy++;
			c.ipady = 100;
			c.ipadx = 500;
			c.gridwidth = 5;
			this.add(this.tablePanel, c);
		}
		else {
			this.tablePanel.setVisible(false);
			UI.navTo(UI.Routes.LOGIN);
		}
	}
}
