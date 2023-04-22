package ui.user;

import ui.custom.ClickableText;
import ui.custom.NavUpdate;

import javax.swing.*;
import java.awt.*;

public class UserField extends JPanel implements NavUpdate {
    protected final JTextField username = new JTextField(), password = new JTextField(), s1 = new JTextField(), s2 = new JTextField(), s3 = new JTextField(), a1 = new JTextField(), a2 = new JTextField(), a3 = new JTextField();
    private final JLabel enterUsername, enterPassword;
    protected final ClickableText left, right;
    public UserField(String leftName, String rightName, boolean isRegister) {
        super();

        this.left = new ClickableText(leftName);
        this.right = new ClickableText(rightName);

        this.setLayout(new GridBagLayout());
        this.enterUsername = new JLabel("Enter Username:");
        this.enterPassword = new JLabel("Enter Password:");

        GridBagConstraints enterUserC = new GridBagConstraints();
        enterUserC.fill = GridBagConstraints.NONE;
        enterUserC.gridx = 0;
        enterUserC.gridy = 0;

        GridBagConstraints userC = new GridBagConstraints();
        userC.fill = GridBagConstraints.HORIZONTAL;
        userC.gridx = 0;
        userC.gridy = 1;
        userC.gridwidth = 3;

        GridBagConstraints enterPassC = new GridBagConstraints();
        enterPassC.fill = GridBagConstraints.NONE;
        enterPassC.gridx = 0;
        enterPassC.gridy = 2;
        GridBagConstraints passC = new GridBagConstraints();
        passC.fill = GridBagConstraints.HORIZONTAL;
        passC.gridx = 0;
        passC.gridy = 3;
        passC.gridwidth = 3;


        this.add(enterUsername, enterUserC);
        this.add(username, userC);
        this.add(enterPassword, enterPassC);
        this.add(password, passC);



        if (isRegister) {
            GridBagConstraints loginC = new GridBagConstraints();
            GridBagConstraints registerC = new GridBagConstraints();

            loginC.fill = GridBagConstraints.NONE;
            loginC.gridx = 0;
            loginC.gridy = 4;

            registerC.fill = GridBagConstraints.NONE;
            registerC.gridx = 2;
            registerC.gridy = 4;

            this.add(left, loginC);
            this.add(right, registerC);
        }
    }

    @Override
    public void navUpdate() {
        //do nothing for this one
    }
}