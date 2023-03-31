package ui;
import user_services.Account;

import java.awt.*;

import javax.swing.*;

public class UI extends JFrame {
    enum Routes {
        LOGIN("LOGIN"), REGISTER("REGISTER"), MAKE_RESERVATIONS("MAKE_RESERVATIONS");

        public final String route;
        Routes(String route) {
            this.route = route;
        }
    }
    private static UI ui = null;
    private static Account currentClient = null;

    //basically convert UI into a singleton, since there
    //will only be one
    public static UI getUI() {
        if(ui == null) ui = new UI();
        return ui;
    }
    protected static void updateCurrentClient(Account account) {
        currentClient = account;
    }
    public static Account getCurrentClient() {
        if(currentClient == null) UI.navTo(Routes.LOGIN);
        return currentClient;
    }

    private final CardLayout cl;
    private final LoginPage loginPage;
    private final RegisterPage registerPage;
    private final JPanel main, nav;
    private UI() {
        super("Hotel Reservations brought to you by That Team");
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.nav = new JPanel(new GridLayout(2, 1));
        //card layout
        this.cl = new CardLayout();
        this.loginPage = new LoginPage();
        this.registerPage = new RegisterPage();

        this.main = new JPanel(cl);

        this.main.add(this.loginPage);
        this.main.add(this.registerPage);
        cl.addLayoutComponent(this.loginPage, Routes.LOGIN.route);
        cl.addLayoutComponent(this.registerPage, Routes.REGISTER.route);

        this.nav.add(this.main);

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
