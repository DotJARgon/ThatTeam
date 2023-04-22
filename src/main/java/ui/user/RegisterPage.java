package ui.user;

import hotel_management.HotelManagement;
import ui.custom.Clickable;
import ui.UI;
import user_services.Account;

import javax.swing.*;
import java.awt.*;

public class RegisterPage extends UserField {
    private final JLabel eS1, eS2, eS3, eA1, eA2, eA3;
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
        super("Register", "Return to login", false);
        this.eS1 = new JLabel("Enter Your First Security Question:");
        this.eS2 = new JLabel("Enter Your Second Security Question:");
        this.eS3 = new JLabel("Enter Your Third Security Question:");
        this.eA1 = new JLabel("Enter Your First Security Answer:");
        this.eA2 = new JLabel("Enter Your Second Security Answer:");
        this.eA3 = new JLabel("Enter Your Third Security Answer:");
        this.username.setToolTipText("enter username");
        this.password.setToolTipText("enter password");
        this.s1.setToolTipText("enter security question");
        this.s2.setToolTipText("enter security question");
        this.s3.setToolTipText("enter security question");
        this.a1.setToolTipText("enter security answer");
        this.a2.setToolTipText("enter security answer");
        this.a3.setToolTipText("enter security answer");
        GridBagConstraints loginC = new GridBagConstraints();
        GridBagConstraints registerC = new GridBagConstraints();

        loginC.fill = GridBagConstraints.NONE;
        loginC.gridx = 0;
        loginC.gridy = 16;

        registerC.fill = GridBagConstraints.NONE;
        registerC.gridx = 2;
        registerC.gridy = 16;
        GridBagConstraints enterS1 = new GridBagConstraints();
        enterS1.fill = GridBagConstraints.NONE;
        enterS1.gridx = 0;
        enterS1.gridy = 4;
        GridBagConstraints s1C = new GridBagConstraints();
        s1C.fill = GridBagConstraints.HORIZONTAL;
        s1C.gridx = 0;
        s1C.gridy = 5;
        s1C.gridwidth = 3;
        GridBagConstraints enterS2 = new GridBagConstraints();
        enterS2.fill = GridBagConstraints.NONE;
        enterS2.gridx = 0;
        enterS2.gridy = 6;
        GridBagConstraints s2C = new GridBagConstraints();
        s2C.fill = GridBagConstraints.HORIZONTAL;
        s2C.gridx = 0;
        s2C.gridy = 7;
        s2C.gridwidth = 3;
        GridBagConstraints enterS3 = new GridBagConstraints();
        enterS3.fill = GridBagConstraints.NONE;
        enterS3.gridx = 0;
        enterS3.gridy = 8;
        GridBagConstraints s3C = new GridBagConstraints();
        s3C.fill = GridBagConstraints.HORIZONTAL;
        s3C.gridx = 0;
        s3C.gridy = 9;
        s3C.gridwidth = 3;
        GridBagConstraints enterA1 = new GridBagConstraints();
        enterA1.fill = GridBagConstraints.NONE;
        enterA1.gridx = 0;
        enterA1.gridy = 10;
        GridBagConstraints a1C = new GridBagConstraints();
        a1C.fill = GridBagConstraints.HORIZONTAL;
        a1C.gridx = 0;
        a1C.gridy = 11;
        a1C.gridwidth = 3;
        GridBagConstraints enterA2 = new GridBagConstraints();
        enterA2.fill = GridBagConstraints.NONE;
        enterA2.gridx = 0;
        enterA2.gridy = 12;
        GridBagConstraints a2C = new GridBagConstraints();
        a2C.fill = GridBagConstraints.HORIZONTAL;
        a2C.gridx = 0;
        a2C.gridy = 13;
        a2C.gridwidth = 3;
        GridBagConstraints enterA3 = new GridBagConstraints();
        enterA3.fill = GridBagConstraints.NONE;
        enterA3.gridx = 0;
        enterA3.gridy = 14;
        GridBagConstraints a3C = new GridBagConstraints();
        a3C.fill = GridBagConstraints.HORIZONTAL;
        a3C.gridx = 0;
        a3C.gridy = 15;
        a3C.gridwidth = 3;

        this.add(eS1, enterS1);
        this.add(s1, s1C);
        this.add(eS2, enterS2);
        this.add(s2, s2C);
        this.add(eS3, enterS3);
        this.add(s3, s3C);
        this.add(eA1, enterA1);
        this.add(a1, a1C);
        this.add(eA2, enterA2);
        this.add(a2, a2C);
        this.add(left, loginC);
        this.add(right, registerC);
        this.add(eA3, enterA3);
        this.add(a3, a3C);
        this.left.addClickAction(this.registerAction);
        this.right.addClickAction(this.loginPageAction);
    }
}