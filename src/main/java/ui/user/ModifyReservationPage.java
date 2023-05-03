package ui.user;

import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import hotel_management.HotelManagement;
import hotel_management.Reservation;
import hotel_management.Room;
import ui.UI;
import ui.custom.DateBox;
import ui.custom.NavUpdate;
import ui.rooms.ReserveRoomsPage;
import user_services.Account;
import user_services.Clerk;
import user_services.Guest;

public class ModifyReservationPage extends ReserveRoomsPage {
    private int currResID = -1;

    public ModifyReservationPage() {
        super();
    }

    @Override
    public void reserveRooms() {
        if(startDate.getDate().after(endDate.getDate())) {
            JOptionPane.showMessageDialog(this, "Dates are wrong",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Object[] options = { "OK", "CANCEL" };
        int option = JOptionPane.showOptionDialog(null,
                "Are you sure you want to reserve these rooms?",
                "Reserve?",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        //continue to ui to make reservations
        if(option == 0) {
            int[] rows = this.roomsTable.getSelectedRows();
            int[] roomIds = new int[rows.length];
            for(int i = 0; i < rows.length; i++) {
                roomIds[i] = Integer.parseInt((String) this.roomsTable.getValueAt(rows[i], 0));
            }

            Date start = startDate.getDate();
            Date end = endDate.getDate();

            if(UI.getCurrentClient() instanceof Guest)
                HotelManagement.getHotelManagement().modifyReservation(currResID, start, end, roomIds);
            else if(UI.getCurrentClient() instanceof Clerk) {
            	if(((Clerk)UI.getCurrentClient()).getGuest() != null)
            		HotelManagement.getHotelManagement().modifyReservation(currResID, start, end, roomIds);
            	else
            		JOptionPane.showMessageDialog(null, "Cannot reserve for guest because there is no designated guest");
            }
            this.currResID = -1;

            this.navUpdate();
            UI.navTo(UI.Routes.VIEW_RESERVATIONS);
        }
    }

    public void setID(int id) {
    	this.currResID = id;
    }
}
