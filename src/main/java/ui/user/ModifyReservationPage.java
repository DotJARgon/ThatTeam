package ui.user;

import java.util.Date;
import javax.swing.JOptionPane;

import hotel_management.HotelManagement;
import ui.UI;
import ui.rooms.ReserveRoomsPage;
import user_services.Clerk;
import user_services.Guest;

/**
 * This is the ModifyReservationPage, which extends functionality from ReserveRoomsPage,
 * however it differs in that it has an id for which reservation is selected, and is slightly
 * different in how it performs a reservation, it does not prompt card information as there
 * would have been no way to make a reservation without having proper card info, and instead
 * of making a new reservation for the current logged in account, it edits the values of a
 * reservation of the guest of a clerk account logged in
 * @author  Lizzie Nix
 * @version  3.3
 * @since 3/28/23
 */
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

    /**
     * setID sets the id of the ModifyReservationPage's reservation that it is modify
     * @param id The id of the reservation to modify
     */
    public void setID(int id) {
    	this.currResID = id;
    }
}
