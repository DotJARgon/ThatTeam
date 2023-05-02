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
import user_services.Account;
import user_services.Clerk;
import user_services.Guest;

public class ModifyReservationPage extends JPanel implements NavUpdate {
    private final JTable roomsTable;
    private final JPanel tablePanel, datePanel;
    private final JButton reserveRooms, refresh;
    
    private int currResID = -1;

    private final DateBox startDate, endDate;

    private final ActionListener reserveListener = e -> {
        reserveRooms();
    };
    private final ActionListener refreshListener = e -> {
        this.navUpdate();
    };

    public ModifyReservationPage() {
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
        this.refresh = new JButton("Refresh");
        this.refresh.addActionListener(refreshListener);

        this.add(datePanel);
        this.add(tablePanel);
        this.add(reserveRooms);
        this.add(refresh);

        this.tablePanel.setVisible(false);
    }

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
    
    public void setID(int id) {
    	this.currResID = id;
    }
}
