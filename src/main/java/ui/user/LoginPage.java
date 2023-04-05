package ui.user;

import hotel_management.HotelManagement;
import ui.UI;
import user_services.Account;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPage extends UserField {
    //temporary
    public static int i = 0;
    //these have to be intialized here
    private final JButton login, register;

    private final ActionListener loginAction = e -> {
        Account account = HotelManagement.getHotelManagement().logIn(username.getText(), password.getText());

        if(account == null) {
            Object[] options = { "OK", "CANCEL" };
            int option = JOptionPane.showOptionDialog(null,
                    "Incorrect log in information, would you like to register a new account?",
                    "Invalid credentials",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                    null, options, options[0]);

            //go to register page
            if(option == 0) {
                UI.navTo(UI.Routes.REGISTER);
            }
            else {
                UI.navTo(UI.Routes.LOGIN);
            }
        }
        else {
            Object[] options = { "OK", "CANCEL" };
            int option = JOptionPane.showOptionDialog(null,
                    "Successfully logged in!",
                    "Log in success",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            //continue to ui to make reservations
            if(option == 0) {
                UI.updateCurrentClient(account);
                UI.navTo(UI.Routes.MAKE_RESERVATIONS);
            }
            else {
                UI.navTo(UI.Routes.LOGIN);
            }

        }
    };
    private final ActionListener registerAction = e -> {
        //immediately go to register user
        UI.navTo(UI.Routes.REGISTER);
    };
    public LoginPage() {
        super();

        this.register = new JButton("Register new account?");
        this.login = new JButton("Login");

        this.username.setToolTipText("enter username");
        this.password.setToolTipText("enter password");

        this.login.addActionListener(this.loginAction);
        this.register.addActionListener(this.registerAction);

        GridBagConstraints loginC = new GridBagConstraints();
        GridBagConstraints registerC = new GridBagConstraints();

        loginC.fill = GridBagConstraints.NONE;
        loginC.anchor = GridBagConstraints.EAST;
        loginC.insets = new Insets(0, 0, 0, 10);
        loginC.gridx = 0;

        registerC.fill = GridBagConstraints.NONE;
        registerC.anchor = GridBagConstraints.WEST;
        registerC.insets = new Insets(0, 10, 0, 0);
        registerC.gridx = 2;

        this.buttonPanel.add(login, loginC);
        this.buttonPanel.add(register, registerC);
    }
}
