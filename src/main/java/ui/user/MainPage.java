package ui.user;

import ui.UI;
import ui.custom.NavUpdate;
import user_services.Account;
import user_services.Clerk;
import user_services.Guest;

import javax.swing.*;

public class MainPage extends JPanel implements NavUpdate {
    private final JButton logout, reserveroom, modifyroom;
    public MainPage() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.logout = new JButton("Logout");
        this.reserveroom = new JButton("Make Reservation");
        this.modifyroom = new JButton("Modify Room Info");

        this.logout.addActionListener(e -> {
            UI.updateCurrentClient(null);
            UI.navTo(UI.Routes.LOGIN);
        });
        this.reserveroom.addActionListener(e -> UI.navTo(UI.Routes.MAKE_RESERVATIONS));
        this.modifyroom.addActionListener(e -> UI.navTo(UI.Routes.MODIFY_ROOMS));

        this.add(this.logout);
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
