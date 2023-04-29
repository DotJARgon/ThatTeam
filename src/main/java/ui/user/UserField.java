package ui.user;

import ui.custom.ClickableText;
import ui.custom.NavUpdate;

import javax.swing.*;
import java.awt.*;

public class UserField extends JPanel implements NavUpdate {
    protected final JTextField username = new JTextField();
    JPasswordField password = new JPasswordField();
    private final JLabel enterUsername, enterPassword;
    protected final ClickableText left, right;
    //protected final String resetUsername = "";
    public UserField(String leftName, String rightName, int reg) {
        super();
        this.enterUsername = new JLabel("Enter Username:");
        this.enterPassword = new JLabel("Enter Password:");
        this.left = new ClickableText(leftName);
        this.right = new ClickableText(rightName);
        this.setLayout(new GridBagLayout());

        if (reg != 2) {
            this.username.setToolTipText("enter username");
            this.password.setToolTipText("enter password");

            GridBagConstraints enterUserC = new GridBagConstraints();
            enterUserC.fill = GridBagConstraints.HORIZONTAL;
            enterUserC.gridx = 0;
            enterUserC.gridy = 0;

            GridBagConstraints userC = new GridBagConstraints();
            userC.fill = GridBagConstraints.HORIZONTAL;
            userC.gridx = 0;
            userC.gridy = 1;
            userC.gridwidth = 3;

            GridBagConstraints enterPassC = new GridBagConstraints();
            enterPassC.fill = GridBagConstraints.HORIZONTAL;
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
            password.setEchoChar('*');
        }
        GridBagConstraints loginC = new GridBagConstraints();
        GridBagConstraints registerC = new GridBagConstraints();
        if (reg == 0) {
            loginC.fill = GridBagConstraints.NONE;
            loginC.gridx = 0;
            loginC.gridy = 4;

            registerC.fill = GridBagConstraints.NONE;
            registerC.gridx = 2;
            registerC.gridy = 4;
            this.add(left, loginC);
            this.add(right, registerC);
        }
        else {
            loginC.fill = GridBagConstraints.NONE;
            loginC.gridx = -2;
            loginC.gridy = 10;

            registerC.fill = GridBagConstraints.NONE;
            registerC.gridx = 0;
            registerC.gridy = 10;

            this.add(left, loginC);
            this.add(right, registerC);
        }
    }

    @Override
    public void navUpdate() {
        this.username.setText("");
        this.password.setText("");
    }
}
