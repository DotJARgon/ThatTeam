package ui.rooms;

import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import hotel_management.Room;
import ui.NavUpdate;
import ui.UI;
import user_services.Account;

public class ReserveRoomsPage extends JPanel implements NavUpdate {
    private final JTable roomsTable;
    private final JPanel tablePanel, datePanel;
    private final JButton reserveRooms;

    private final DateSelector startDate, endDate;

    private final ActionListener reserveListener = e -> {
        reserveRooms();
    };

    public ReserveRoomsPage() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createHorizontalGlue());

        this.tablePanel = new JPanel();
        this.datePanel = new JPanel();

        this.tablePanel.setLayout(new BoxLayout(this.tablePanel, BoxLayout.Y_AXIS));
        this.datePanel.setLayout(new BoxLayout(this.datePanel, BoxLayout.X_AXIS));

        this.startDate = new DateSelector("Start date");
        this.endDate = new DateSelector("End date");
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

    public void reserveRooms() {
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
                roomIds[i] = Integer.parseInt((String)this.roomsTable.getValueAt(rows[i], 0));
            }

            System.out.println(Arrays.toString(roomIds));

            //make a call to reserving this room

            //calling the navUpdate actually refreshes this page
            this.navUpdate();
        }
    }

    @Override
    public void navUpdate() {
        this.tablePanel.setVisible(true);
        Account account = UI.getCurrentClient();
        if(account != null) {
        	Vector<Room> rooms;
        	try {
				rooms= HotelManagement.getHotelManagement().getAvailableRooms(startDate.getDate(),endDate.getDate());
			} catch (ParseException e) {
				rooms = new Vector<>();
			}


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
