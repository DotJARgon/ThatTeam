package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
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

public class UI {
    public static enum op {DATESET, ROOMSET, CONFIRM};

    public static Calendar start;
    public static Calendar end;
    public static int room;
    public static op operation;
    public static Room room1;
    //write a function that takes in a string and returns a date
    //Assumes correct format, verify before calling function
    //Format: "HH:MMAM MM/DD/YY"
    private static Calendar stringToCalendar(String calStr) {
        Calendar ret = new GregorianCalendar();
        ret.set(Integer.parseInt(calStr.substring(14)), Integer.parseInt(calStr.substring(8,10)), Integer.parseInt(calStr.substring(11,13)));
        ret.set(Calendar.HOUR, Integer.parseInt(calStr.substring(0,2)));
        ret.set(Calendar.MINUTE, Integer.parseInt(calStr.substring(3,5)));
        if(calStr.substring(5,7).equals("AM"))
        	ret.set(Calendar.AM_PM, Calendar.AM);
        else
        	ret.set(Calendar.AM_PM, Calendar.PM);
        return ret;
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
                start = stringToCalendar(result);
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
                end = stringToCalendar(result);
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
