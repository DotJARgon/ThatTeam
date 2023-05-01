package ui.user;

import hotel_management.HotelManagement;
import ui.custom.Clickable;
import ui.UI;
import ui.custom.ClickableText;
import user_services.Account;
import javax.swing.*;
import java.awt.*;
import java.util.jar.JarEntry;
/**
 * The ResetPage class is responsible for initalizing and managing
 * the page for resetting password. The page handles the verification
 * of which user's password is being changed, and verification on
 * the answer to the question and the new passwords.
 *
 * @author  Bryant Huang
 * @version  1.6
 * @since 4/28/2023
 */

public class ResetPage extends UserField {
    protected final JTextField username = new JTextField(), secA = new JTextField(), newPass = new JTextField();
    private final JLabel Q = new JLabel(), A, Pass, reqUser;
    private Account accountValidation;
    private final ClickableText verifyUser;
    /**
     * When the button verifyUser is pressed, this function verifies that the user is a real user then displays the security question
     * to the user
     */
    private final Clickable updateUser = () -> {
        //System.out.println(HotelManagement.getHotelManagement().getAccountByUsername(this.username.getText()).getSecurityA() + " " + HotelManagement.getHotelManagement().getAccountByUsername(this.username.getText()).getSecurityQ());
        this.accountValidation = HotelManagement.getHotelManagement().getUser(this.username.getText()); //change to getUser()
        //System.out.println(accountValidation.getSecurityA() + " " + accountValidation.getSecurityQ());
        this.Q.setText("<html>" + accountValidation.getSecurityQ() + "</html>");
        //this.Q.setMaximumSize(new Dimension(100, 10));
    };
    /**
     * Handles the navigation after a click of the login page button
     */
    private final Clickable loginPageAction = () -> {
        UI.navTo(UI.Routes.LOGIN);
    };

    /**
     * Checks if the answer is equal to the set user's questions. If so,
     * Show a dialog page to confirm the change, and change the user's password and rehash it,
     * and redirect the page to login. If the username entered doesn't exist, prompt the user to
     * create a new account.
     */

    private final Clickable resetAction = () -> {

        this.accountValidation = HotelManagement.getHotelManagement().getUser(this.username.getText());
        this.Q.setText(accountValidation.getSecurityQ());

        System.out.println(secA.getText() + " " + newPass.getText() + " " + username.getText());
        if (!(secA.getText().equals("") || newPass.equals(""))) {
            //System.out.println(secA.getText());
            Object[] options = {"OK", "CANCEL"};
            int option = JOptionPane.showOptionDialog(null, //FIX ME
                    "Would you like to reset your password?",
                    "RESET",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);
            //continue to ui to make reservations
            if (option == 0) {
                //System.out.println(accountValidation.getSecurityA() + " " + accountValidation.getSecurityQ());
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
    /**
     * A constructor of the ResetPage object, which creates and initializes the page object with
     * the proper fields.
     */
    public ResetPage() {
        super("Reset Password", "Return to login",2);
        this.verifyUser = new ClickableText("Click to see security question!");
        this.Pass = new JLabel("Enter New Password:");
        this.A = new JLabel("Enter Answer:");
        this.secA.setToolTipText("enter answer");
        this.reqUser = new JLabel("Enter Username:");

        GridBagConstraints RUGrid = new GridBagConstraints();
        RUGrid.fill = GridBagConstraints.HORIZONTAL;
        RUGrid.gridx = 0;
        RUGrid.gridy = 0;

        GridBagConstraints UGrid = new GridBagConstraints();
        UGrid.fill = GridBagConstraints.HORIZONTAL;
        UGrid.gridx = 0;
        UGrid.gridy = 1;
        UGrid.gridwidth = 3;

        GridBagConstraints SGrid = new GridBagConstraints();
        SGrid.fill = GridBagConstraints.NONE;
        SGrid.gridx = 0;
        SGrid.gridy = 2;

        GridBagConstraints QGrid = new GridBagConstraints();
        QGrid.fill = GridBagConstraints.NONE;
        QGrid.gridx = 0;
        QGrid.gridy = 4;
        QGrid.gridwidth = 3;

        GridBagConstraints enterAGrid = new GridBagConstraints();
        enterAGrid.fill = GridBagConstraints.HORIZONTAL;
        enterAGrid.gridx = 0;
        enterAGrid.gridy = 5;

        GridBagConstraints AGrid = new GridBagConstraints();
        AGrid.fill = GridBagConstraints.HORIZONTAL;
        AGrid.gridx = 0;
        AGrid.gridy = 6;
        AGrid.gridwidth = 3;

        GridBagConstraints PGrid = new GridBagConstraints();
        PGrid.fill = GridBagConstraints.NONE;
        PGrid.gridx = 0;
        PGrid.gridy = 7;

        GridBagConstraints NPGrid = new GridBagConstraints();
        NPGrid.fill = GridBagConstraints.HORIZONTAL;
        NPGrid.gridx = 0;
        NPGrid.gridy = 8;
        NPGrid.gridwidth = 3;

        this.add(reqUser, RUGrid);
        this.add(username, UGrid);
        this.add(verifyUser, SGrid);
        this.add(Q, QGrid);
        this.add(Pass, PGrid);
        this.add(newPass, NPGrid);
        this.add(A, enterAGrid);
        this.add(secA, AGrid);
        this.left.addClickAction(this.resetAction);
        this.right.addClickAction(this.loginPageAction);
        this.verifyUser.addClickAction(this.updateUser);
    }

    /**
        Overrides the navUpdate function in the UI, to clean the
        page once navigated off, and repopulate the page once navigated on.
    */
    @Override
    public void navUpdate() {
        this.username.setText("");
        this.password.setText("");
        this.newPass.setText("");
        this.secA.setText("");
        this.Q.setText("");
    }
}
