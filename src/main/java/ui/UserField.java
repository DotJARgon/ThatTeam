package ui;

import javax.swing.*;
import java.awt.*;

public class UserField extends JPanel {
    protected final JTextField username = new JTextField(), password = new JTextField();
    protected final JPanel textPanel, buttonPanel;
    public UserField() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createHorizontalGlue());

        this.textPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        this.textPanel.add(username);
        this.textPanel.add(password);

        this.add(textPanel);


        this.buttonPanel = new JPanel();
        this.buttonPanel.setLayout(new BoxLayout(this.buttonPanel, BoxLayout.Y_AXIS));
        this.buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.add(buttonPanel);
    }
}
