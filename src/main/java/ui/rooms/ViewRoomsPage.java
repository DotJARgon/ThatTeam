package ui.rooms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import hotel_management.HotelManagement;
import hotel_management.Room;
import ui.UI;
import ui.custom.NavUpdate;
import user_services.Account;

public class ViewRoomsPage extends JPanel implements NavUpdate{
	private JTable roomsTable;
    private final JPanel tablePanel;
	
	public ViewRoomsPage() {
		this.roomsTable = new JTable();
		this.tablePanel = new JPanel();
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = .33;
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
		modify.addActionListener(e -> {
			UI.getUI().setModifyRoomsPage(null);
			int row = roomsTable.getSelectedRow();
			if(row != -1) {
				int roomID = Integer.parseInt((String) roomsTable.getValueAt(row, 0));
				Room room = HotelManagement.getHotelManagement().getRoomByID(roomID);
				if(room != null) {
					UI.getUI().setModifyRoomsPage(room);
				}
			}

			UI.navTo(UI.Routes.MODIFY_ROOMS);
		});
		this.add(modify, c);

		c.gridx++;
		JButton remove = new JButton("Remove");
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = roomsTable.getSelectedRow();
				int roomID = Integer.parseInt((String) roomsTable.getValueAt(row, 0));
				if(row != -1 && HotelManagement.getHotelManagement().getRoom(roomID).getReservations().size() == 0) {
					HotelManagement.getHotelManagement().removeRoom(roomID);
					int modelRow = roomsTable.convertRowIndexToModel(row);
					DefaultTableModel model = (DefaultTableModel)roomsTable.getModel();
					model.removeRow(modelRow);
				}
				else if (row == -1) {
					JOptionPane.showMessageDialog(null,"No room has been selected");
				}
				else
					JOptionPane.showMessageDialog(null,"Room cannot be removed because it has at least one reservation");
			}
    	});
    	this.add(remove, c);
		

    	c.gridx = 0;
    	c.gridy++;
    	c.ipady = 100;
    	c.ipadx = 500;
    	c.gridwidth = 3;
		this.tablePanel.setLayout(new BoxLayout(this.tablePanel, BoxLayout.Y_AXIS));
		this.tablePanel.add(new JScrollPane(this.roomsTable));
		this.roomsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.add(tablePanel, c);
		
        this.tablePanel.setVisible(false);
	}

	@Override
	public void navUpdate() {
        this.tablePanel.setVisible(true);
		Account account = UI.getCurrentClient();
        if(account != null) {
        	ConcurrentHashMap<Integer, Room> rooms = HotelManagement.getHotelManagement().getRooms();

        	//populate table
            String[] columns = new String[] {
            	"Room number", "Beds", "Smoker status", "Quality", "Number of beds"
            };
            Object[][] data = new Object[rooms.size()][columns.length];
            
            int ndx = 0;
            for(Map.Entry<Integer,Room> r: rooms.entrySet()) {
            	System.out.println("Room: " + r.getValue().getID());
            	data[ndx][0] = Integer.toString(r.getValue().getID());
                data[ndx][1] = r.getValue().getBedType().toString();
                data[ndx][2] = r.getValue().getCanSmoke() ? "Can smoke" : "Cannot smoke";
                data[ndx][3] = r.getValue().getQualityType().toString();
				data[ndx][4] = r.getValue().getNumBeds();
                ndx++;
            }

            JScrollPane scroll = new JScrollPane(this.roomsTable);
            this.tablePanel.add(scroll);
            this.roomsTable.setModel(new DefaultTableModel(data, columns));
        }
        else {
            this.tablePanel.setVisible(false);
            UI.navTo(UI.Routes.LOGIN);
        }
	}

}
