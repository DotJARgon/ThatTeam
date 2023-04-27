package ui.user;

import hotel_management.HotelManagement;
import ui.custom.Clickable;
import ui.UI;
import user_services.Account;

import javax.swing.*;

public class LoginPage extends UserField {
    private final Clickable loginAction = () -> {
        Account account = HotelManagement.getHotelManagement().logIn(username.getText(), password.getText());

        if (account == null) {
            Object[] options = {"OK", "CANCEL"};
            int option = JOptionPane.showOptionDialog(null,
                    "Incorrect log in information, would you like to register a new account?",
                    "Invalid credentials",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                    null, options, options[0]);

            //go to register page
            if (option == 0) {
                UI.navTo(UI.Routes.REGISTER);
            } else {
                UI.navTo(UI.Routes.LOGIN);
            }
        } else {
            Object[] options = {"OK", "CANCEL"};
            int option = JOptionPane.showOptionDialog(null,
                    "Successfully logged in!",
                    "Log in success",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            //continue to ui to make reservations
            if (option == 0) {
                UI.updateCurrentClient(account);
                UI.navTo(UI.Routes.MAIN_PAGE);
            } else {
                UI.navTo(UI.Routes.LOGIN);
            }

        }
    };
    private final Clickable registerAction = () -> {
        //immediately go to register user
        UI.navTo(UI.Routes.REGISTER);
    };

    public LoginPage() {
        super("Login", "Register new account?");
        this.left.addClickAction(this.loginAction);
        this.right.addClickAction(this.registerAction);
    }
}