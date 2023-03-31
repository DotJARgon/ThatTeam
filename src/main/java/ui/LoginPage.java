package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPage extends JPanel {
    public static int i = 0;
    private final JTextField username, password;
    private final JButton login, register;

    private final ActionListener loginAction = e -> {
        //do nothing for now
    };
    private final ActionListener registerAction = e -> {
        UI.navTo(UI.Routes.REGISTER);
    };
    public LoginPage() {
        super(new GridLayout(4, 1, 0, 10));

        this.username = new JTextField();
        this.password = new JTextField();
        this.register = new JButton("Register new account?");
        this.login = new JButton("Login " + (i++));

        this.username.setToolTipText("enter username");
        this.password.setToolTipText("enter password");

        this.login.addActionListener(this.loginAction);
        this.register.addActionListener(this.registerAction);

        this.add(username);
        this.add(password);
        this.add(login);
        this.add(register);
    }
}
