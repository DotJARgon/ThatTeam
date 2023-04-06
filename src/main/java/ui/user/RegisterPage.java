package ui.user;

import hotel_management.HotelManagement;
import ui.UI;
import user_services.Account;

import javax.swing.*;
import java.awt.event.ActionListener;

public class RegisterPage extends UserField {
    private final ActionListener registerAction = e -> {
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

    private final ActionListener loginPageAction = e -> {
        UI.navTo(UI.Routes.LOGIN);
    };

    public RegisterPage() {
        super("Register", "Return to login");
        this.left.addActionListener(this.registerAction);
        this.right.addActionListener(this.loginPageAction);
    }
}
