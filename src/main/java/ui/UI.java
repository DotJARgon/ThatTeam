package ui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import hotel_management.Room;

public class UI extends JFrame {
    enum Routes {
        LOGIN("LOGIN"), REGISTER("REGISTER");

        public final String route;
        Routes(String route) {
            this.route = route;
        }
    }
    private static UI ui = null;

    //basically convert UI into a singleton, since there
    //will only be one
    public static UI getUI() {
        if(ui == null) ui = new UI();
        return ui;
    }

    private final CardLayout cl;
    private final LoginPage loginPage, loginPage2;
    private final JPanel main, nav;
    private UI() {
        super("Hotel Reservations brought to you by That Team");
        super.setDefaultLookAndFeelDecorated(true);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.nav = new JPanel(new GridLayout(2, 1));
        //card layout
        this.cl = new CardLayout();
        this.loginPage = new LoginPage();
        this.loginPage2 = new LoginPage();

        this.main = new JPanel(cl);

        this.main.add(this.loginPage);
        this.main.add(this.loginPage2);
        cl.addLayoutComponent(this.loginPage, Routes.LOGIN.route);
        cl.addLayoutComponent(this.loginPage2, Routes.REGISTER.route);

        JButton playGame = new JButton("Play!");
        ActionListener playGameListener = e -> cl.show(this.main, "login2");
        playGame.addActionListener(playGameListener);
        this.nav.add(this.main);
        this.nav.add(playGame);

        super.add(this.nav);

        super.setPreferredSize(new Dimension(700, 700));

        this.pack();
        this.setVisible(true);
    }
    private void nav(String page) {
        cl.show(this.main, page);
    }
    protected static void navTo(Routes page) {
        UI.getUI().nav(page.route);
    }

    public static void main(String[] args) {
        UI ui = getUI();
    }
}
