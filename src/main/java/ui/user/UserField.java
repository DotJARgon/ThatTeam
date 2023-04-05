package ui.user;

import ui.NavUpdate;

import javax.swing.*;
import java.awt.*;

public class UserField extends JPanel implements NavUpdate {
    protected final JTextField username = new JTextField(), password = new JTextField();
    protected final JPanel textPanel, buttonPanel;
    private final JLabel enterUsername, enterPassword;
    public UserField() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createHorizontalGlue());

        this.textPanel = new JPanel(new GridLayout(4, 1, 0, 5));

        this.enterUsername = new JLabel("Enter Username:");
        this.enterPassword = new JLabel("Enter Password:");

        this.textPanel.add(enterUsername);
        this.textPanel.add(username);
        this.textPanel.add(enterPassword);
        this.textPanel.add(password);

        this.add(textPanel);

        this.buttonPanel = new JPanel();
        /*this.buttonPanel.setLayout(new BoxLayout(this.buttonPanel, BoxLayout.X_AXIS));
        this.buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);*/
        this.buttonPanel.setLayout(new GridBagLayout());
        //this.buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.add(buttonPanel);
    }

    @Override
    public void navUpdate() {
        //do nothing for this one
    }
}
