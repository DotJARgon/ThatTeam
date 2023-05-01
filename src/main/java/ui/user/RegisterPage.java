package ui.user;

import hotel_management.HotelManagement;
import ui.custom.Clickable;
import ui.UI;
import user_services.Account;

import javax.swing.*;
import java.awt.*;

/**
 * The RegisterPage class is responsible for initalizing and managing
 * the page for registering a new user. The page handles creation of a new account,
 * requiring the user to register with a username, password, security question, and
 * security answer.
 * @author  Bryant Huang
 * @version  1.6
 * @since 5/1/2023
 */

public class RegisterPage extends UserField {
    protected final JTextField secQ = new JTextField(), secA = new JTextField(), firstName = new JTextField(), lastName = new JTextField();
    private final JLabel enterQ, enterA, enterF, enterL;
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
            Account account = HotelManagement.getHotelManagement().registerUser(username.getText(), password.getText(), secQ.getText(), secA.getText(), firstName.getText(), lastName.getText());
            if(account != null) {
                UI.updateCurrentClient(account);
                UI.navTo(UI.Routes.MAIN_PAGE);
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
        this.enterF = new JLabel("Enter First Name:");
        this.enterL = new JLabel("Enter Last Name:");

        this.secQ.setToolTipText("enter security question");
        this.secA.setToolTipText("enter answer");

        GridBagConstraints enterFGrid = new GridBagConstraints();
        enterFGrid.fill = GridBagConstraints.HORIZONTAL;
        enterFGrid.gridx = 0;
        enterFGrid.gridy = 5;

        GridBagConstraints FGrid = new GridBagConstraints();
        FGrid.fill = GridBagConstraints.HORIZONTAL;
        FGrid.gridx = 0;
        FGrid.gridy = 6;
        FGrid.gridwidth = 3;

        GridBagConstraints enterLGrid = new GridBagConstraints();
        enterLGrid.fill = GridBagConstraints.HORIZONTAL;
        enterLGrid.gridx = 0;
        enterLGrid.gridy = 7;

        GridBagConstraints LGrid = new GridBagConstraints();
        LGrid.fill = GridBagConstraints.HORIZONTAL;
        LGrid.gridx = 0;
        LGrid.gridy = 8;
        LGrid.gridwidth = 3;

        GridBagConstraints enterQGrid = new GridBagConstraints();
        enterQGrid.fill = GridBagConstraints.NONE;
        enterQGrid.gridx = 0;
        enterQGrid.gridy = 9;

        GridBagConstraints QGrid = new GridBagConstraints();
        QGrid.fill = GridBagConstraints.HORIZONTAL;
        QGrid.gridx = 0;
        QGrid.gridy = 10;
        QGrid.gridwidth = 3;

        GridBagConstraints enterAGrid = new GridBagConstraints();
        enterAGrid.fill = GridBagConstraints.HORIZONTAL;
        enterAGrid.gridx = 0;
        enterAGrid.gridy = 11;

        GridBagConstraints AGrid = new GridBagConstraints();
        AGrid.fill = GridBagConstraints.HORIZONTAL;
        AGrid.gridx = 0;
        AGrid.gridy = 12;
        AGrid.gridwidth = 3;
        this.add(enterF, enterFGrid);
        this.add(firstName, FGrid);
        this.add(enterL, enterLGrid);
        this.add(lastName, LGrid);
        this.add(enterQ, enterQGrid);
        this.add(enterQ, enterQGrid);
        this.add(enterQ, enterQGrid);
        this.add(secQ, QGrid);
        this.add(enterA, enterAGrid);
        this.add(secA, AGrid);
        this.left.addClickAction(this.registerAction);
        this.right.addClickAction(this.loginPageAction);
    }

    @Override
    public void navUpdate() {
        this.username.setText("");
        this.password.setText("");
        this.secA.setText("");
        this.secA.setText("");
        this.secQ.setText("");
        this.firstName.setText("");
        this.lastName.setText("");
    }

}
