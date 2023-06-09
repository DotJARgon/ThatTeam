package ui.rooms;

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

import billing_services.Billing;
import hotel_management.HotelManagement;
import hotel_management.Reservation;
import hotel_management.Room;
import ui.UI;
import ui.billing.ReservationBilling;
import ui.billing.UICreditCardInfo;
import ui.custom.DateBox;
import ui.custom.NavUpdate;
import user_services.Account;
import user_services.Clerk;
import user_services.Guest;

/**
 * ReserveRoomsPage is the page for making reservations. It gives the user the
 * ability to select certain dates to checkout a room, rooms will only show
 * for a given set of dates if and only if those rooms are not reserved in any
 * part of that period! Additionally more than one room can be selected,
 * as well as it will prompt the user to either enter their credit card
 * info for the first time or make sure their card info is correct. Then a
 * billing is shown, this billing is what the user will be charged once they check in
 * @author  Marcelo Carpenter
 * @version  3.1
 * @since 3/20/23
 */
public class ReserveRoomsPage extends JPanel implements NavUpdate {
    protected final JTable roomsTable;
    private final JPanel tablePanel, datePanel;
    private final JButton reserveRooms;
    protected final DateBox startDate, endDate;

    private final ActionListener reserveListener = e -> {
        reserveRooms();
    };

    /**
     * This is the ReserveRoomsPage, it simply sets up the GUI
     */
    public ReserveRoomsPage() {
        super();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createHorizontalGlue());

        this.tablePanel = new JPanel();
        this.datePanel = new JPanel();

        this.tablePanel.setLayout(new BoxLayout(this.tablePanel, BoxLayout.Y_AXIS));
        this.datePanel.setLayout(new BoxLayout(this.datePanel, BoxLayout.X_AXIS));

        this.startDate = new DateBox();
        this.endDate = new DateBox();

        //add these as callbacks
        this.startDate.setCallback(this::navUpdate);
        this.endDate.setCallback(this::navUpdate);

        this.datePanel.add(startDate);
        this.datePanel.add(endDate);

        this.roomsTable = new JTable();

        this.tablePanel.add(new JScrollPane(this.roomsTable));

        this.reserveRooms = new JButton("Reserve");
        this.reserveRooms.addActionListener(reserveListener);


        this.add(datePanel);
        this.add(tablePanel);
        this.add(reserveRooms);

        this.tablePanel.setVisible(false);
    }

    /**
     * reserveRooms is the called function that performs all of the logic
     * for reserving a set of selected rooms, it checks to make sure rooms
     * are selected, the start and end dates are proper, and also prompts the
     * user to enter their credit card info as well as works for the clerk too, and
     * allows the clerk to make a reservation for a guest
     */
    public void reserveRooms() {

        if(startDate.getDate().after(endDate.getDate())) {
            JOptionPane.showMessageDialog(this, "Dates are wrong",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.roomsTable.getSelectedRows().length == 0) {
            JOptionPane.showMessageDialog(this, "Select a room",
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

            Reservation res = new Reservation((int) (Math.random()*Integer.MAX_VALUE), start, end, new Billing(), roomIds, false, false);

            if(UI.getCurrentClient() instanceof Guest guest) {
                UICreditCardInfo creditCardInfo = new UICreditCardInfo(guest);
                JOptionPane.showMessageDialog(null, creditCardInfo);

                if(creditCardInfo.isCreditCardValid()) {
                    JOptionPane.showMessageDialog(null, "Successfully Reserved");
                    HotelManagement.getHotelManagement().addReservation(res, guest);
                    JOptionPane.showMessageDialog(null, new ReservationBilling(res));
                }
                else {
                    JOptionPane.showMessageDialog(null,"Cancelled or invalid card information",
                            "Oops", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(UI.getCurrentClient() instanceof Clerk clerk) {
            	if(((Clerk)UI.getCurrentClient()).getGuest() != null) {
                    HotelManagement.getHotelManagement().addReservation(res, clerk.getGuest());
                    JOptionPane.showMessageDialog(null, new ReservationBilling(res));
                }
            	else
            		JOptionPane.showMessageDialog(null, "Cannot reserve for guest because there is no designated guest");
            }

            this.navUpdate();
        }
    }

    /**
     * navUpdate here collects all of the reservations that are available and displays
     * them nicely in the table
     */
    @Override
    public void navUpdate() {
        this.tablePanel.setVisible(true);
        Account account = UI.getCurrentClient();
        if(account != null) {
        	Vector<Room> rooms;
            rooms = HotelManagement.getHotelManagement().getAvailableRooms(startDate.getDate(),endDate.getDate());

            //populate all rooms
            String[] columns = new String[] {
                    "Room number", "Beds", "Smoker status", "Quality"
            };

            String[][] data = new String[rooms.size()][columns.length];

            for(int i = 0; i < rooms.size(); i++) {
                data[i][0] = Integer.toString(rooms.get(i).getID());
                data[i][1] = rooms.get(i).getBedType().toString();
                data[i][2] = rooms.get(i).getCanSmoke() ? "Can smoke" : "Cannot smoke";
                data[i][3] = rooms.get(i).getQualityType().toString();
            }

            this.roomsTable.setModel(new DefaultTableModel(data, columns));
        }
        else {
            this.tablePanel.setVisible(false);
            UI.navTo(UI.Routes.LOGIN);
        }

    }
}
