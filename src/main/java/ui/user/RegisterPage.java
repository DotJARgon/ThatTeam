package ui.user;

import hotel_management.HotelManagement;
import ui.custom.Clickable;
import ui.UI;
import user_services.Account;

import javax.swing.*;
import java.awt.*;

public class RegisterPage extends UserField {
    protected final JTextField secQ = new JTextField(), secA = new JTextField();
    private final JLabel enterQ, enterA;
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
            Account account = HotelManagement.getHotelManagement().registerUser(username.getText(), password.getText(), secQ.getText(), secA.getText());
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

    private final Clickable resetAction = () -> {
        //immediately go to register user
        UI.navTo(UI.Routes.RESET_PASSWORD);
    };

    public RegisterPage() {
        super("Register", "Return to login", 1);
        this.enterQ = new JLabel("Enter Security Question:");
        this.enterA = new JLabel("Enter Answer:");

        this.secQ.setToolTipText("enter security question");
        this.secA.setToolTipText("enter answer");

        GridBagConstraints enterQGrid = new GridBagConstraints();
        enterQGrid.fill = GridBagConstraints.NONE;
        enterQGrid.gridx = 0;
        enterQGrid.gridy = 5;

        GridBagConstraints QGrid = new GridBagConstraints();
        QGrid.fill = GridBagConstraints.HORIZONTAL;
        QGrid.gridx = 0;
        QGrid.gridy = 6;
        QGrid.gridwidth = 3;

        GridBagConstraints enterAGrid = new GridBagConstraints();
        enterAGrid.fill = GridBagConstraints.NONE;
        enterAGrid.gridx = 0;
        enterAGrid.gridy = 7;

        GridBagConstraints AGrid = new GridBagConstraints();
        AGrid.fill = GridBagConstraints.HORIZONTAL;
        AGrid.gridx = 0;
        AGrid.gridy = 8;
        AGrid.gridwidth = 3;

        this.add(enterQ, enterQGrid);
        this.add(secQ, QGrid);
        this.add(enterA, enterAGrid);
        this.add(secA, AGrid);
        this.left.addClickAction(this.registerAction);
        this.right.addClickAction(this.loginPageAction);
    }
}
