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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import hotel_management.HotelManagement;
import hotel_management.Reservation;
import hotel_management.ReservationLoader;
import hotel_management.Room;
import hotel_management.RoomLoader;
import ui.custom.Clickable;
import ui.custom.ClickableText;
import ui.custom.NavUpdate;
import ui.rooms.AddModifyRoomsPage;
import ui.rooms.ReserveRoomsPage;
import ui.rooms.ViewRoomsPage;
import ui.user.AddClerkPage;
import ui.user.AddCorporationPage;
import ui.user.HelpGuestPage;
import ui.user.LoginPage;
import ui.user.MainPage;
import ui.user.ModifyReservationPage;
import ui.user.RegisterPage;
import ui.user.ResetPage;
import ui.user.ViewReservations;
import user_services.Account;
import user_services.Admin;
import user_services.Clerk;
import user_services.Guest;
import user_services.UserLoader;

public class UI extends JFrame {
    public enum Routes {
        LOGIN("LOGIN"), REGISTER("REGISTER"), MAKE_RESERVATIONS("MAKE_RESERVATIONS"), ADD_GUEST("ADD_GUEST"),
        VIEW_ROOMS("VIEW_ROOMS"), MODIFY_ROOMS("MODIFY_ROOMS"), MAIN_PAGE("MAIN_PAGE"), ADD_CLERK("ADD_CLERK"),
        RESET_PASSWORD("RESET_PASSWORD"), MODIFY_RESERVATION("MODIFY_RESERVATION"), 
        VIEW_RESERVATIONS("VIEW_RESERVATIONS"), ADD_CORP("ADD_CORP");

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
    private final ViewRoomsPage viewRoomsPage;
    private final ReserveRoomsPage reserveRoomsPage;
    private final AddModifyRoomsPage modifyRoomsPage;
    private final HelpGuestPage helpGuestPage;
    private final AddClerkPage addClerkPage;
    private final ModifyReservationPage modifyResPage;
    private final ViewReservations viewResesPage;
    private final AddCorporationPage addCorpPage;
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
        this.viewRoomsPage = new ViewRoomsPage();
        this.reserveRoomsPage = new ReserveRoomsPage();
        this.resetPasswordPage = new ResetPage();
        this.modifyRoomsPage = new AddModifyRoomsPage();
        this.helpGuestPage = new HelpGuestPage();
        this.addClerkPage = new AddClerkPage();
        this.modifyResPage = new ModifyReservationPage();
        this.viewResesPage = new ViewReservations();
        this.addCorpPage = new AddCorporationPage();

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
        this.main.add(this.viewRoomsPage);
        this.main.add(this.reserveRoomsPage);
        this.main.add(this.modifyRoomsPage);
        this.main.add(this.resetPasswordPage);
        this.main.add(this.mainPage);
        this.main.add(this.helpGuestPage);
        this.main.add(this.addClerkPage);
        this.main.add(this.modifyResPage);
        this.main.add(this.viewResesPage);
        this.main.add(this.addCorpPage);


        //add to card layout
        cl.addLayoutComponent(this.loginPage, Routes.LOGIN.route);
        cl.addLayoutComponent(this.registerPage, Routes.REGISTER.route);
        cl.addLayoutComponent(this.viewRoomsPage, Routes.VIEW_ROOMS.route);
        cl.addLayoutComponent(this.reserveRoomsPage, Routes.MAKE_RESERVATIONS.route);
        cl.addLayoutComponent(this.modifyRoomsPage, Routes.MODIFY_ROOMS.route);
        cl.addLayoutComponent(this.resetPasswordPage, Routes.RESET_PASSWORD.route);
        cl.addLayoutComponent(this.mainPage, Routes.MAIN_PAGE.route);
        cl.addLayoutComponent(this.helpGuestPage, Routes.ADD_GUEST.route);
        cl.addLayoutComponent(this.addClerkPage, Routes.ADD_CLERK.route);
        cl.addLayoutComponent(this.modifyResPage, Routes.MODIFY_RESERVATION.route);
        cl.addLayoutComponent(this.viewResesPage, Routes.VIEW_RESERVATIONS.route);
        cl.addLayoutComponent(this.addCorpPage, Routes.ADD_CORP.route);


        this.nav.add(this.main, mainC);
        this.add(this.nav);

        this.pageUpdates = new HashMap<>();

        this.pageUpdates.put(Routes.LOGIN.route, this.loginPage);
        this.pageUpdates.put(Routes.REGISTER.route, this.registerPage);
        this.pageUpdates.put(Routes.VIEW_ROOMS.route, this.viewRoomsPage);
        this.pageUpdates.put(Routes.MAKE_RESERVATIONS.route, this.reserveRoomsPage);
        this.pageUpdates.put(Routes.MODIFY_ROOMS.route, this.modifyRoomsPage);
        this.pageUpdates.put(Routes.MAIN_PAGE.route, this.mainPage);
        this.pageUpdates.put(Routes.RESET_PASSWORD.route, this.resetPasswordPage);
        this.reset.addClickAction(verifyUserName);
        this.pageUpdates.put(Routes.ADD_GUEST.route, this.helpGuestPage);
        this.pageUpdates.put(Routes.ADD_CLERK.route, this.addClerkPage);
        this.pageUpdates.put(Routes.MODIFY_RESERVATION.route, this.modifyResPage);
        this.pageUpdates.put(Routes.VIEW_RESERVATIONS.route, this.viewResesPage);
        this.pageUpdates.put(Routes.ADD_CORP.route, this.addCorpPage);

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
        Account accountValidation = HotelManagement.getHotelManagement().getUser(username);
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
            g.setHashedPassword(Integer.toString(i));
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
        accountsDebug.add(c);
        
        Admin a = new Admin();
        a.setUsername("Jimbo3");
        a.setHashedPassword(Integer.toString(3));
        accountsDebug.add(a);
        
        /*AccountList accountList = new AccountList();
        accountList.setAccountsList(accountsDebug);
        UserLoader.saveUsers(accountList);*/

        UserLoader.saveUsers(accountsDebug);
        ConcurrentHashMap<String, Account> accounts = UserLoader.loadUsers();
        for(Account acc : accounts.values()) System.out.println(acc.getUsername());

        UI ui = getUI();
    }
    
    public void setModResID(int id) {
    	this.modifyResPage.setID(id);
    }
}