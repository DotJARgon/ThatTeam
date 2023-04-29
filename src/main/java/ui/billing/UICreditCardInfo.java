package ui.billing;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class UICreditCardInfo extends JPanel {
    public UICreditCardInfo() {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(16, 16, 16, 16));

        createGUI();

    }

    private void createGUI() {
        setPreferredSize(new Dimension(800, 400));
        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.Y_AXIS));
        add(listPane);

        //
        JLabel flabel = new JLabel("First");





        listPane.setVisible(true);
        setVisible(true);

    }


    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Enter your credit card information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        UICreditCardInfo newContentPane = new UICreditCardInfo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }



    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}