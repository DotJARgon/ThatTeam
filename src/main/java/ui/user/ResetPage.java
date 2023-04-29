package ui.user;

import hotel_management.HotelManagement;
import ui.custom.Clickable;
import ui.UI;
import ui.custom.ClickableText;
import user_services.Account;

import javax.swing.*;
import java.awt.*;
import java.util.jar.JarEntry;

public class ResetPage extends UserField {
    protected final JTextField secA = new JTextField(), newPass = new JTextField();
    private final JLabel Q, A, Pass;
    private Account accountValidation;
    private final Clickable registerAction = () -> {
        //should do nothing
    };

    private final Clickable loginPageAction = () -> {
        UI.navTo(UI.Routes.LOGIN);
    };

    private final Clickable resetAction = () -> {
        System.out.println(secA.getText() + " " + newPass.getText() + " " + username.getText());
        if (!(secA.getText().equals("") || newPass.equals(""))) {
            System.out.println(secA.getText());
            Object[] options = {"OK", "CANCEL"};
            int option = JOptionPane.showOptionDialog(null, //FIX ME
                    "Would you like to reset your password?",
                    "RESET",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            //continue to ui to make reservations
            if (option == 0) {
                if (accountValidation.resetPassword(secA.getText())) {
                    accountValidation.setHashedPassword(newPass.getText());
                    Object[] options2 = {"OK", "CANCEL"};
                    int option2 = JOptionPane.showOptionDialog(null, //FIX ME
                            "Password Sucessfully Changed! Return to the log in page to login with your new password!",
                            "RESET",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                            null, options2, options2[0]);
                    UI.navTo(UI.Routes.LOGIN);
                } else {
                    Object[] options2 = {"REGISTER", "CANCEL"};
                    int option3 = JOptionPane.showOptionDialog(null, //FIX ME
                            "That wasn't the correct answer! Would you like to register a new user?",
                            "RESET",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                            null, options2, options2[0]);
                    if (option3 == 0) {
                        UI.navTo(UI.Routes.REGISTER);
                    } else {
                        UI.navTo(UI.Routes.LOGIN);
                    }
                }
            } else {
                UI.navTo(UI.Routes.LOGIN);
            }
        }
        else {
            Object[] options4 = {"OK", "CANCEL"};
            int option3 = JOptionPane.showOptionDialog(null, //FIX ME
                    "Please Input an Answer and a Password!",
                    "RESET",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options4, options4[0]);
            if (option3 > -1) {
                UI.navTo(UI.Routes.LOGIN);
            }
        }
    };

    /*private final Clickable verifyUserName = ()-> {
        String username = JOptionPane.showInputDialog("Enter a username");
        //continue to ui to make reservations
        accountValidation = HotelManagement.getHotelManagement().getAcc(username);
        if (accountValidation == null) {
            Object[] options2 = {"REGISTER", "CANCEL"};
            int option = JOptionPane.showOptionDialog(null,
                    "Not a valid username! Want to register a new user?",
                    "Invalid Username",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options2, options2[0]);
            if (option == 0) {
                UI.navTo(UI.Routes.REGISTER);
            } else {
                UI.navTo(UI.Routes.LOGIN);
            }
        }
    };*/
    public ResetPage(String verifiedUsername) {
        super("Reset Password", "Return to login",2);
        //this.userName = new JLabel("Enter Username, then click submit button");
        //ClickableText submit = new ClickableText("Submit!");
        //this.left.addClickAction(this.verifyUserName);
        //this.secA.setToolTipText("enter username");
        this.Pass = new JLabel("Enter New Password:");
        this.A = new JLabel("Enter Answer:");
        this.secA.setToolTipText("enter answer");
        this.accountValidation = HotelManagement.getHotelManagement().getAcc(verifiedUsername);
        this.Q = new JLabel(accountValidation.getSecurityQ());
        /*GridBagConstraints UGrid = new GridBagConstraints();
        UGrid.fill = GridBagConstraints.NONE;
        UGrid.gridx = 0;
        UGrid.gridy = 0;

        GridBagConstraints UAGrid = new GridBagConstraints();
        UAGrid.fill = GridBagConstraints.HORIZONTAL;
        UAGrid.gridx = 0;
        UAGrid.gridy = 1;
        UAGrid.gridwidth = 3;

        GridBagConstraints UBGrid = new GridBagConstraints();
        UBGrid.fill = GridBagConstraints.NONE;
        UBGrid.gridx = 0;
        UBGrid.gridy = 2;*/

        GridBagConstraints QGrid = new GridBagConstraints();
        QGrid.fill = GridBagConstraints.NONE;
        QGrid.gridx = 0;
        QGrid.gridy = 3;

        GridBagConstraints enterAGrid = new GridBagConstraints();
        enterAGrid.fill = GridBagConstraints.NONE;
        enterAGrid.gridx = 0;
        enterAGrid.gridy = 4;

        GridBagConstraints AGrid = new GridBagConstraints();
        AGrid.fill = GridBagConstraints.HORIZONTAL;
        AGrid.gridx = 0;
        AGrid.gridy = 5;
        AGrid.gridwidth = 3;

        GridBagConstraints PGrid = new GridBagConstraints();
        PGrid.fill = GridBagConstraints.NONE;
        PGrid.gridx = 0;
        PGrid.gridy = 6;

        GridBagConstraints NPGrid = new GridBagConstraints();
        NPGrid.fill = GridBagConstraints.HORIZONTAL;
        NPGrid.gridx = 0;
        NPGrid.gridy = 7;
        NPGrid.gridwidth = 3;

/*
        this.add(userName, UGrid);
        this.add(user, UAGrid);
        this.add(submit, UBGrid);

 */
        this.add(Q, QGrid);
        this.add(Pass, PGrid);
        this.add(newPass, NPGrid);
        this.add(A, enterAGrid);
        this.add(secA, AGrid);
        this.left.addClickAction(this.resetAction);
        this.right.addClickAction(this.loginPageAction);
    }
}
