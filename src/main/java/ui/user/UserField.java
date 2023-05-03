package ui.user;

import ui.custom.ClickableText;
import ui.custom.NavUpdate;

import javax.swing.*;
import java.awt.*;

/**
 * UserField is a versatile component meant for either logging in or registering a new user,
 * it has a title, two input fields, one is username the other is password, and two clickable
 * text fields
 * @author  Marcelo Carpenter, Bryant Huang
 * @version  1.3
 * @since 3/15/23
 */
public class UserField extends JPanel implements NavUpdate {
    protected final JTextField username = new JTextField();
    JPasswordField password = new JPasswordField();
    private final JLabel enterUsername, enterPassword;
    protected final ClickableText left, right;

    /**
     * This is the UserField constructor, it takes in two strings, leftName
     * being the text label of the left clickable text component, and rightName
     * being the same for the right clickable text component, and reg is a control for
     * specific types of UserFields
     * @param leftName The name of the left clickable text component
     * @param rightName The name of the right clickable text component
     * @param reg A control for a type of UserField layout
     */
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
            loginC.gridy = 13;

            registerC.fill = GridBagConstraints.NONE;
            registerC.gridx = 0;
            registerC.gridy = 13;

            this.add(left, loginC);
            this.add(right, registerC);
        }
    }

    /**
     * navUpdate in UserField clears all text input fields
     */
    @Override
    public void navUpdate() {
        this.username.setText("");
        this.password.setText("");
    }
}
