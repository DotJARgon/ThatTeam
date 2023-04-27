package ui.user;

import ui.UI;
import ui.custom.NavUpdate;
import user_services.Account;
import user_services.Clerk;
import user_services.Guest;

import javax.swing.*;

public class MainPage extends JPanel implements NavUpdate {

    public MainPage() {
        super();
        
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
