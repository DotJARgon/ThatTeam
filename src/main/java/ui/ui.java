package ui;
import javax.swing.*;

import hotel_management.Room;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class ui {
    public static enum op {DATESET, ROOMSET, CONFIRM};

    public static Date start;
    public static Date end;
    public static int room;
    public static op operation;
    public static Room room1;
    //write a function that takes in a string and returns a date
    private static Date stringToDate(String result) {
        //TODO
        return null; //FIXME
    }

    public static void main(String[] args) {
        // Create and set up a frame window
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("ReserveRoom");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel newPanel = new JPanel();
        // Define new buttons

        final JLabel jlbl = new JLabel();
        final JLabel entry = new JLabel();
        JButton jb1 = new JButton("Enter Start Date");
        JButton jb2 = new JButton("Enter End Date");
        JButton jb3 = new JButton("Enter Room Number");
        jb1.setBorder(BorderFactory.createBevelBorder(1, Color.red, Color.blue));
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = (String)JOptionPane.showInputDialog(
                        frame,
                        "Enter a Start Date:",
                        "Swing Tester",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "Red"
                );
                start = new Date();
                start = stringToDate(result);
            }
        });
        jb2.setBorder(BorderFactory.createBevelBorder(1, Color.red, Color.blue));
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = (String)JOptionPane.showInputDialog(
                        frame,
                        "Enter an End Date:",
                        "Swing Tester",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "Red"
                );
                end = new Date();
                end = stringToDate(result);
            }
        });
        jb3.setBorder(BorderFactory.createBevelBorder(1, Color.red, Color.blue));
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = (String)JOptionPane.showInputDialog(
                        frame,
                        "Enter a Room Number:",
                        "Swing Tester",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "Red"
                );
                room = Integer.parseInt(result);
            }
        });
        // Define the panel to hold the buttons
        JPanel panel = new JPanel();
        newPanel.setLayout(new GridLayout(3,3,4,5 ));
        panel.setLayout(new GridLayout(3,3, 4, 5));
        entry.setSize(new Dimension(1000, 300));
        entry.setBorder(BorderFactory.createBevelBorder(1, Color.red, Color.blue));
        jlbl.setBorder(BorderFactory.createBevelBorder(1, Color.blue, Color.red));
        newPanel.add(entry);
        newPanel.add(jlbl);
        panel.add(jb1);
        panel.add(jb2);
        panel.add(jb3);
        // Set the window to be visible as the default to be false
        frame.add(newPanel);
        frame.setLayout(new GridLayout(0,1));
        frame.add(panel);
        frame.setSize(1000,500);
        frame.setVisible(true);
        //System.out.println(first + " " + second);
    }
}
