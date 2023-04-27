package ui.user;

import ui.UI;
import ui.custom.NavUpdate;
import user_services.Account;
import user_services.Clerk;
import user_services.Guest;

import javax.swing.*;

public class MainPage extends JPanel implements NavUpdate {
    private final JButton login, register, reserveroom, modifyroom;
    public MainPage() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.login = new JButton("Login");
        this.register = new JButton("Register New Account");
        this.reserveroom = new JButton("Make Reservation");
        this.modifyroom = new JButton("Modify Room Info");

        this.login.addActionListener(e -> UI.navTo(UI.Routes.LOGIN));
        this.register.addActionListener(e -> UI.navTo(UI.Routes.REGISTER));
        this.reserveroom.addActionListener(e -> UI.navTo(UI.Routes.MAKE_RESERVATIONS));
        this.modifyroom.addActionListener(e -> UI.navTo(UI.Routes.MODIFY_ROOMS));

        this.add(this.login);
        this.add(this.register);
        this.add(this.reserveroom);
        this.add(this.modifyroom);
        //this.add(this.login);

    }

    @Override
    public void navUpdate() {
        Account curr = UI.getCurrentClient();
        if(curr != null) {
            if(curr instanceof Guest g) {

            }
            else if(curr instanceof Clerk c) {

            }
            else {

            }
        }
    }
}
