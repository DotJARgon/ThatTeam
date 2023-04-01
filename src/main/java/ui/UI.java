package ui;
import hotel_management.Reservation;
import hotel_management.ReservationLoader;
import hotel_management.Room;
import hotel_management.RoomLoader;
import ui.rooms.ReserveRoomsPage;
import ui.user.LoginPage;
import ui.user.RegisterPage;
import user_services.Account;
import user_services.UserLoader;

import java.awt.*;
import java.util.*;
import javax.swing.*;

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

    private final HashMap<String, NavUpdate> pageUpdates;

    private UI() {
        super("Hotel Reservations brought to you by That Team");
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //initialization
        this.nav = new JPanel();
        this.cl = new CardLayout();
        this.loginPage = new LoginPage();
        this.registerPage = new RegisterPage();
        this.reserveRoomsPage = new ReserveRoomsPage();

        //set up main page
        this.main = new JPanel(cl);
        this.main.add(this.loginPage);
        this.main.add(this.registerPage);
        this.main.add(this.reserveRoomsPage);

        //add to card layout
        cl.addLayoutComponent(this.loginPage, Routes.LOGIN.route);
        cl.addLayoutComponent(this.registerPage, Routes.REGISTER.route);
        cl.addLayoutComponent(this.reserveRoomsPage, Routes.MAKE_RESERVATIONS.route);

        this.nav.add(this.main);
        this.add(this.nav);

        this.pageUpdates = new HashMap<>();

        this.pageUpdates.put(Routes.LOGIN.route, this.loginPage);
        this.pageUpdates.put(Routes.REGISTER.route, this.registerPage);
        this.pageUpdates.put(Routes.MAKE_RESERVATIONS.route, this.reserveRoomsPage);

        this.setPreferredSize(new Dimension(500, 700));

        this.pack();
        this.setVisible(true);
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
        Set<Room> r = new HashSet<>(roomsDebug);
        RoomLoader.saveRooms(r);

        ArrayList<Reservation> reservationsDebug = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for(int i = 0; i < 10; i++) {
            Reservation reservation = new Reservation(i, cal.getTime(), cal.getTime(), null, new int[] {1, 2, 3}, false, false);
            reservationsDebug.add(reservation);
        }
        Set<Reservation> res = new HashSet<>(reservationsDebug);
        ReservationLoader.saveReservations(res, ReservationLoader.Status.ACTIVE);
        ReservationLoader.saveReservations(res, ReservationLoader.Status.INACTIVE);

        ArrayList<Account> accountsDebug = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Account acc = new Account();
            acc.setUsername("bob" + i);
            acc.setPassword(Integer.toString(i));
            Vector<Integer> rv = new Vector<>();
            rv.add(1);
            rv.add(2);
            rv.add(3);

            acc.setReservations(rv);
            accountsDebug.add(acc);
        }
        Set<Account> acc = new HashSet<>(accountsDebug);
        UserLoader.saveUsers(acc);

        System.out.println("testing");
        UI ui = getUI();
    }
}