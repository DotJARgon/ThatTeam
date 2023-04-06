package ui.user;

import hotel_management.HotelManagement;
import ui.custom.Clickable;
import ui.UI;
import user_services.Account;

import javax.swing.*;

public class RegisterPage extends UserField {
    private final Clickable registerAction = () -> {
        //immediately go to register user
        UI.navTo(UI.Routes.REGISTER);

        Object[] options = { "OK", "CANCEL" };
        int option = JOptionPane.showOptionDialog(null,
                "Would you like to register with this username and password?",
                "Register",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        //continue to ui to make reservations
        if(option == 0) {
            //create a new account, temporary until register user
            //exists
            Account account = HotelManagement.getHotelManagement().registerUser(username.getText(), password.getText());
            if(account != null) {
                UI.updateCurrentClient(account);
                UI.navTo(UI.Routes.MAKE_RESERVATIONS);
            }
            else {
                UI.navTo(UI.Routes.REGISTER);
            }
        }
        else {
            UI.navTo(UI.Routes.REGISTER);
        }
    };

    private final Clickable loginPageAction = () -> {
        UI.navTo(UI.Routes.LOGIN);
    };

    public RegisterPage() {
        super("Register", "Return to login");
        this.left.addClickAction(this.registerAction);
        this.right.addClickAction(this.loginPageAction);
    }
}
