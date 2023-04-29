package ui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.*;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import hotel_management.*;
import ui.custom.*;
import ui.rooms.*;
import ui.user.*;
import user_services.*;

import hotel_management.Reservation;
import hotel_management.ReservationLoader;
import hotel_management.Room;
import hotel_management.RoomLoader;

public class UI extends JFrame {
    public enum Routes {
        LOGIN("LOGIN"), REGISTER("REGISTER"), MAKE_RESERVATIONS("MAKE_RESERVATIONS"), ADD_GUEST("ADD_GUEST"),
        VIEW_ROOMS("VIEW_ROOMS"), MODIFY_ROOMS("MODIFY_ROOMS"), MAIN_PAGE("MAIN_PAGE"),
        RESET_PASSWORD("RESET_PASSWORD");

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
        if(currentClient == null) {
            getUI().mainButton.setFocusable(false);
            getUI().mainButton.setVisible(false);
        }
        else {
            getUI().mainButton.setFocusable(true);
            getUI().mainButton.setVisible(true);
        }
    }
    public static Account getCurrentClient() {
        if(currentClient == null) UI.navTo(Routes.LOGIN);
        return currentClient;
    }
    private final CardLayout cl;
    private final MainPage mainPage;
    private final LoginPage loginPage;
    private final RegisterPage registerPage;
    private final ReserveRoomsPage reserveRoomsPage;
    private final AddModifyRoomsPage modifyRoomsPage;
    private final JPanel main, nav;
    private final JButton mainButton;
    private ResetPage resetPasswordPage;

    enum Theme {
        LIGHT("light mode"), DARK("dark mode");

        public final String mode;
        Theme(String mode) {
            this.mode = mode;
        }
    }

    private final JComboBox<String> theme;
    private ClickableText reset;
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
        this.reset = new ClickableText("Reset Password");
        //initialization
        this.nav = new JPanel();
        this.cl = new CardLayout();
        this.mainPage = new MainPage();
        this.loginPage = new LoginPage();
        this.registerPage = new RegisterPage();
        this.reserveRoomsPage = new ReserveRoomsPage();
        this.resetPasswordPage = new ResetPage();
        this.modifyRoomsPage = new AddModifyRoomsPage();

        this.mainButton = new JButton("main menu");
        this.mainButton.addActionListener(e -> navTo(Routes.MAIN_PAGE));
        this.mainButton.setFocusable(false);
        this.mainButton.setVisible(false);

        this.nav.setLayout(new GridBagLayout());
        GridBagConstraints themeC = new GridBagConstraints();
        GridBagConstraints mainC = new GridBagConstraints();
        GridBagConstraints resetC = new GridBagConstraints();

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

        themeC.anchor = GridBagConstraints.NORTH;
        themeC.fill = GridBagConstraints.NONE;
        themeC.weightx = 0.25;
        themeC.weighty = 0.10;
        themeC.gridx = 0;
        themeC.gridy = 0;
        themeC.gridwidth = 1;
        themeC.gridheight = 1;

        this.nav.add(this.mainButton, themeC);

        resetC.fill = GridBagConstraints.BOTH;
        resetC.anchor = GridBagConstraints.CENTER;
        resetC.weightx = 0.80;
        resetC.weighty = 0.80;
        resetC.gridx = 1;
        resetC.gridy = 4;
        resetC.gridwidth = 3;
        resetC.gridheight = 0;
        resetC.insets = new Insets(0, 30, 0, 30);

        this.nav.add(this.reset, resetC);

        //set up main page
        this.main = new JPanel(cl);
        this.main.add(this.loginPage);
        this.main.add(this.registerPage);
        this.main.add(this.reserveRoomsPage);
        this.main.add(this.modifyRoomsPage);
        this.main.add(this.resetPasswordPage);
        this.main.add(this.mainPage);


        //add to card layout
        cl.addLayoutComponent(this.loginPage, Routes.LOGIN.route);
        cl.addLayoutComponent(this.registerPage, Routes.REGISTER.route);
        cl.addLayoutComponent(this.reserveRoomsPage, Routes.MAKE_RESERVATIONS.route);
        cl.addLayoutComponent(this.modifyRoomsPage, Routes.MODIFY_ROOMS.route);
        cl.addLayoutComponent(this.resetPasswordPage, Routes.RESET_PASSWORD.route);
        cl.addLayoutComponent(this.mainPage, Routes.MAIN_PAGE.route);


        this.nav.add(this.main, mainC);
        this.add(this.nav);

        this.pageUpdates = new HashMap<>();

        this.pageUpdates.put(Routes.LOGIN.route, this.loginPage);
        this.pageUpdates.put(Routes.REGISTER.route, this.registerPage);
        this.pageUpdates.put(Routes.MAKE_RESERVATIONS.route, this.reserveRoomsPage);
        this.pageUpdates.put(Routes.MODIFY_ROOMS.route, this.modifyRoomsPage);
        this.pageUpdates.put(Routes.MAIN_PAGE.route, this.mainPage);
        this.pageUpdates.put(Routes.RESET_PASSWORD.route, this.resetPasswordPage);

        this.reset.addClickAction(verifyUserName);

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

        //JOptionPane.showMessageDialog(null, new DateBox());
    }
    private final Clickable verifyUserName = ()-> {
        String username = JOptionPane.showInputDialog("Enter a username");
        //continue to ui to make reservations
        Account accountValidation = HotelManagement.getHotelManagement().getAccountByUsername(username);
        if (accountValidation == null) {
            Object[] options2 = {"REGISTER", "CANCEL"};
            int option = JOptionPane.showOptionDialog(null,
                    "Not a valid username! Want to register a new user?",
                    "Invalid Username",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options2, options2[0]);
            if (option == 0) {
                UI.navTo(Routes.REGISTER);
            } else {
                UI.navTo(UI.Routes.LOGIN);
            }
        }
        else {
            UI.navTo(Routes.RESET_PASSWORD);
        }
        //reset.setVisible(false);
    };
    private void nav(String page) {
        //reset.isVisible();
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
            Room room = new Room(i,2,Room.BedType.QUEEN,false,Room.QualityType.COMFORT);
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
            g.setSecurityQ("Your mother was a hamster and your father was smelt of elderberries");
            g.setHashedSecurityA("No you!");
            g.setHashedPassword(Integer.toString(i));
            System.out.println(g.getSecurityA());
            Vector<Integer> rv = new Vector<>();
            rv.add(1);
            rv.add(2);
            rv.add(3);
            g.setReservations(rv);
            accountsDebug.add(g);
        }

        Clerk c = new Clerk();
        c.setUsername("sheila1");
        c.setHashedPassword(Integer.toString(1));
        c.setSecurityQ("What is the best christmas movie of all time?");
        c.setHashedSecurityA("Die Hard");
        accountsDebug.add(c);
        /*AccountList accountList = new AccountList();
        accountList.setAccountsList(accountsDebug);
        UserLoader.saveUsers(accountList);*/

        UserLoader.saveUsers(accountsDebug);
        ConcurrentHashMap<String, Account> accounts = UserLoader.loadUsers();
        for(Account a : accounts.values()) System.out.println(a.getUsername());

        UI ui = getUI();
    }
}