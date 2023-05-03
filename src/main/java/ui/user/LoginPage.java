package ui.user;

import hotel_management.HotelManagement;
import ui.custom.Clickable;
import ui.UI;
import ui.custom.ClickableText;
import user_services.Account;

import javax.swing.*;
import java.awt.*;

/**
 * The LoginPage extends the UserField, it allows the user to login using
 * their username and password, when the login button is clicked, if that information
 * was correct, then the user is logged in, otherwise, they are prompted whether they
 * want to register a new account. There are also clickable text options for either
 * selecting to register a new account or reset password!
 *
 * @author  Bryant Huang and Marcelo Carpenter
 * @version  1.6
 * @since 5/1/2023
 */
public class LoginPage extends UserField {
    private final ClickableText reset = new ClickableText("Reset Password");
    private final Clickable loginAction = () -> {
        Account account = HotelManagement.getHotelManagement().logIn(username.getText(), password.getText());

        if (account == null) {
            Object[] options2 = {"REGISTER", "CANCEL"};
            int option = JOptionPane.showOptionDialog(null,
                    "Not a valid username! Want to register a new user?",
                    "Invalid Username",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options2, options2[0]);
            if (option == 0) {
                UI.navTo(UI.Routes.REGISTER);
            }
            else {
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

    private final Clickable resetAction = () -> {
        //immediately go to register user
        UI.navTo(UI.Routes.RESET_PASSWORD);
    };

    //remember to add a route!

    /**
     * This is the LoginPage default constructor, is sets the reset button, which
     * is an additional component not found in UserField
     */
    public LoginPage() {
        super("Login", "Register new account?", 0);
        GridBagConstraints resetC = new GridBagConstraints();
        resetC.fill = GridBagConstraints.NONE;
        resetC.gridx = 0;
        resetC.gridy = 5;

        this.add(this.reset, resetC);
        this.reset.addClickAction(this.resetAction);
        this.left.addClickAction(this.loginAction);
        this.right.addClickAction(this.registerAction);
    }
}