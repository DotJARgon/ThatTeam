package ui;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.*;
import javax.xml.bind.annotation.XmlList;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import file_utilities.XMLList;
import file_utilities.XMLParser;
import hotel_management.*;
import ui.custom.DateBox;
import ui.custom.NavUpdate;
import ui.rooms.ReserveRoomsPage;
import ui.user.LoginPage;
import ui.user.RegisterPage;
import user_services.*;

public class UI extends JFrame {
    public enum Routes {
        LOGIN("LOGIN"), REGISTER("REGISTER"), MAKE_RESERVATIONS("MAKE_RESERVATIONS");

        public final String route;
        Routes(String route) {
            this.route = route;
        }
    }
    private static UI ui = null;
    private static Account currentClient = null;

    //basically convert UI into a singleton, since there
    //will only be one
    public static UI getUI() {
        if(ui == null) ui = new UI();
        return ui;
    }
    public static void updateCurrentClient(Account account) {
        currentClient = account;
    }
    public static Account getCurrentClient() {
        if(currentClient == null) UI.navTo(Routes.LOGIN);
        return currentClient;
    }

    private final CardLayout cl;
    private final LoginPage loginPage;
    private final RegisterPage registerPage;
    private final ReserveRoomsPage reserveRoomsPage;
    private final JPanel main, nav;

    enum Theme {
        LIGHT("light mode"), DARK("dark mode");

        public final String mode;
        Theme(String mode) {
            this.mode = mode;
        }
    }

    private final JComboBox<String> theme;
    private final String[] themes = new String[] {
            Theme.LIGHT.mode,
            Theme.DARK.mode
    };

    private final HashMap<String, NavUpdate> pageUpdates;

    private UI() {
        super("Hotel Reservations brought to you by That Team");
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.theme = new JComboBox<>(themes);

        //initialization
        this.nav = new JPanel();
        this.cl = new CardLayout();
        this.loginPage = new LoginPage();
        this.registerPage = new RegisterPage();
        this.reserveRoomsPage = new ReserveRoomsPage();

        this.nav.setLayout(new GridBagLayout());
        GridBagConstraints themeC = new GridBagConstraints();
        GridBagConstraints mainC = new GridBagConstraints();

        mainC.fill = GridBagConstraints.BOTH;
        mainC.anchor = GridBagConstraints.CENTER;
        mainC.weightx = 0.80;
        mainC.weighty = 0.80;
        mainC.gridx = 0;
        mainC.gridy = 1;
        mainC.gridwidth = 3;
        mainC.gridheight = 3;
        mainC.insets = new Insets(0, 30, 0, 30);

        themeC.anchor = GridBagConstraints.NORTH;
        themeC.fill = GridBagConstraints.NONE;
        themeC.weightx = 0.25;
        themeC.weighty = 0.10;
        themeC.gridx = 1;
        themeC.gridy = 0;
        themeC.gridwidth = 1;
        themeC.gridheight = 1;

        this.nav.add(this.theme, themeC);

        //set up main page
        this.main = new JPanel(cl);
        this.main.add(this.loginPage);
        this.main.add(this.registerPage);
        this.main.add(this.reserveRoomsPage);

        //add to card layout
        cl.addLayoutComponent(this.loginPage, Routes.LOGIN.route);
        cl.addLayoutComponent(this.registerPage, Routes.REGISTER.route);
        cl.addLayoutComponent(this.reserveRoomsPage, Routes.MAKE_RESERVATIONS.route);

        this.nav.add(this.main, mainC);
        this.add(this.nav);

        this.pageUpdates = new HashMap<>();

        this.pageUpdates.put(Routes.LOGIN.route, this.loginPage);
        this.pageUpdates.put(Routes.REGISTER.route, this.registerPage);
        this.pageUpdates.put(Routes.MAKE_RESERVATIONS.route, this.reserveRoomsPage);

        this.theme.addActionListener(event -> {
            String selected = this.theme.getSelectedItem().toString();
            if(selected.equals(Theme.LIGHT.mode)) {
                try {
                    UIManager.setLookAndFeel( new FlatLightLaf() );
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }
            else if(selected.equals(Theme.DARK.mode)) {
                try {
                    UIManager.setLookAndFeel( new FlatDarkLaf() );
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }
        });

        this.setPreferredSize(new Dimension(500, 700));

        this.pack();
        this.setVisible(true);

        JOptionPane.showMessageDialog(null, new DateBox());
    }
    private void nav(String page) {
        cl.show(this.main, page);
    }
    public static void navTo(Routes page) {
        NavUpdate navUpdate = UI.getUI().pageUpdates.get(page.route);
        if(navUpdate != null) {
            navUpdate.navUpdate();
        }
        UI.getUI().nav(page.route);
    }

    public static void main(String[] args) {
        ArrayList<Room> roomsDebug = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Room room = new Room();
            room.setID(i);
            room.setQualityType(Room.QualityType.COMFORT);
            room.setNumBeds(2);
            room.setBedType(Room.BedType.QUEEN);
            room.setCanSmoke(false);
            roomsDebug.add(room);
        }
        RoomLoader.saveRooms(roomsDebug);

        ArrayList<Reservation> reservationsDebug = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for(int i = 0; i < 10; i++) {
            Reservation reservation = new Reservation(i, cal.getTime(), cal.getTime(), null, new int[] {1, 2, 3}, false, false);
            reservationsDebug.add(reservation);
        }
        ReservationLoader.saveReservations(reservationsDebug);

        ArrayList<Account> accountsDebug = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Guest g = new Guest();
            g.setUsername("bob" + i);
            g.setPassword(Integer.toString(i));
            Vector<Integer> rv = new Vector<>();
            rv.add(1);
            rv.add(2);
            rv.add(3);

            g.setReservations(rv);
            accountsDebug.add(g);
        }
        /*AccountList accountList = new AccountList();
        accountList.setAccountsList(accountsDebug);
        UserLoader.saveUsers(accountList);*/

        UserLoader.saveUsers(accountsDebug);
        ConcurrentHashMap<String, Account> accounts = UserLoader.loadUsers();
        for(Account a : accounts.values()) System.out.println(a.getUsername());

        //UI ui = getUI();
    }
}