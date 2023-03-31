package ui;

import user_services.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegisterPage extends UserField {
    private final JButton register;

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
            Account account = new Account();
            account.setUsername(username.getText());
            account.setPassword(password.getText());
            UI.updateCurrentClient(account);
            UI.navTo(UI.Routes.MAKE_RESERVATIONS);
        }
        else {
            UI.navTo(UI.Routes.REGISTER);
        }
    };

    public RegisterPage() {
        super();

        this.register = new JButton("Register");

        this.username.setToolTipText("enter username");
        this.password.setToolTipText("enter password");

        this.register.addActionListener(this.registerAction);

        this.buttonPanel.add(register);
    }
}
