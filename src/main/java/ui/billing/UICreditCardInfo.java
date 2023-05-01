package ui.billing;

import com.formdev.flatlaf.FlatLightLaf;
import com.sun.xml.bind.v2.model.impl.DummyPropertyInfo;
import user_services.Guest;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


public class UICreditCardInfo extends JPanel {
    private Guest guest;
    private Boolean isCreditCardValid;
    public Boolean IsCreditCardValid() {
        return isCreditCardValid;
    }
    public Guest getGuest() {
        return guest;
    }
    public void setGuest(Guest g) {
        guest = g;
    }

    public UICreditCardInfo() {
        super();
        isCreditCardValid = false;
        guest = new Guest();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(16, 16, 16, 16));

        createGUI();

    }

    private void createGUI() {
        setPreferredSize(new Dimension(600, 400));

        //First Name
        JLabel fLabel = new JLabel(" First Name: ");
        JTextField fTextField = new JTextField(12);
        JPanel namePane = new JPanel();
        namePane.setLayout(new BoxLayout(namePane, BoxLayout.X_AXIS));
        namePane.setSize(new Dimension(800, 100));

        JPanel fPane = new JPanel();
        fPane.setLayout(new BoxLayout(fPane, BoxLayout.X_AXIS));
        fTextField.setMaximumSize(new Dimension(1000, 40));

        fPane.add(fLabel);
        fPane.add(fTextField);

        //Last Name
        JLabel lLabel = new JLabel(" Last Name: ");
        JTextField lTextField = new JTextField(12);

        JPanel lPane = new JPanel();
        lPane.setLayout(new BoxLayout(lPane, BoxLayout.X_AXIS));
        lTextField.setMaximumSize(new Dimension(1000, 40));

        lPane.add(lLabel);
        lPane.add(lTextField);
        namePane.add(fPane);
        namePane.add(lPane);
        add(namePane);
        //Address
        JLabel aLabel = new JLabel("Address: ");
        JPanel aPane = new JPanel();
        aPane.setLayout(new BoxLayout(aPane, BoxLayout.X_AXIS));

        JTextField aTextField = new JTextField(12);
        aTextField.setMaximumSize(new Dimension(3000, 40));

        aPane.add(aLabel);
        aPane.add(aTextField);

        //Separator
        JLabel blank = new JLabel(" ");
        blank.setSize(new Dimension(800, 600));
        blank.setLayout(new BoxLayout(blank, BoxLayout.X_AXIS));
        add(blank);

        //Credit Card Number
        JLabel cLabel = new JLabel("Credit Card Number: ");
        JTextField cTextField = new JTextField(20);
        cTextField.setMaximumSize(new Dimension(3000, 40));
        JPanel bPane = new JPanel();
        bPane.setLayout(new BoxLayout(bPane, BoxLayout.X_AXIS));

        bPane.add(cLabel);
        bPane.add(cTextField);

        //Expiration Date + Separators
        JPanel cPane = new JPanel();
        cPane.setLayout(new BoxLayout(cPane, BoxLayout.X_AXIS));
        cPane.setSize(new Dimension(800, 100));

        JLabel dLabel = new JLabel("Expiration Date (mm/dd/yyyy): ");
        JTextField dTextField1 = new JTextField(12);
        JTextField dTextField2 = new JTextField(12);
        JTextField dTextField3 = new JTextField(12);
        JLabel dLabelA = new JLabel(" / ");
        JLabel dLabelB = new JLabel(" / ");

        dLabelA.setMaximumSize(new Dimension(10, 30));
        dLabelB.setMaximumSize(new Dimension(10, 30));
        dTextField1.setMaximumSize(new Dimension(20, 30));
        dTextField2.setMaximumSize(new Dimension(20, 30));
        dTextField3.setMaximumSize(new Dimension(60, 30));

        cPane.add(dLabel);
        cPane.add(dTextField1);
        cPane.add(dLabelA);
        cPane.add(dTextField2);
        cPane.add(dLabelB);
        cPane.add(dTextField3);

        JLabel blank1 = new JLabel(" ");
        blank1.setSize(new Dimension(800, 600));
        blank1.setLayout(new BoxLayout(blank1, BoxLayout.X_AXIS));
        add(blank1);
        JLabel blank2 = new JLabel(" ");
        blank2.setSize(new Dimension(800, 600));
        blank2.setLayout(new BoxLayout(blank2, BoxLayout.X_AXIS));
        add(blank2);
        JLabel blank3 = new JLabel(" ");
        blank3.setSize(new Dimension(800, 600));
        blank3.setLayout(new BoxLayout(blank3, BoxLayout.X_AXIS));
        JLabel blank4 = new JLabel(" ");
        blank4.setSize(new Dimension(800, 600));
        blank4.setLayout(new BoxLayout(blank4, BoxLayout.X_AXIS));
        JLabel blank5 = new JLabel(" ");
        blank5.setSize(new Dimension(800, 600));
        blank5.setLayout(new BoxLayout(blank5, BoxLayout.X_AXIS));
        JLabel blank6 = new JLabel(" ");
        blank6.setSize(new Dimension(800, 600));
        blank6.setLayout(new BoxLayout(blank6, BoxLayout.X_AXIS));
        JLabel blank7 = new JLabel(" ");
        blank7.setSize(new Dimension(800, 600));
        blank7.setLayout(new BoxLayout(blank7, BoxLayout.X_AXIS));
        JLabel blank8 = new JLabel(" ");
        blank8.setSize(new Dimension(800, 600));
        blank8.setLayout(new BoxLayout(blank8, BoxLayout.X_AXIS));

        //Submit and Cancel Button
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));

        JButton buttonSubmit = new JButton("Update Credit Card Information");
        buttonSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* FIXME: WHAT IT SHOULD DO:
                *  1. Checks if the information submitted is valid
                *  2. If it is invalid, states its invalid and returns back to this window
                *  3. If it is valid, asks user yes-or-no if they want to be charge
                *  4. If the credit card has a date that expired or not enough credits to pay for the total price,
                *     inputs an error message and returns the user back to this window
                *  5. If the credit card has an ongoing date and enough credits to pay for the total price, the
                *     credit card gets updated by subtracting it from the price and confirms the user that the
                *     payment was successful and updates the user's reservation list
                */

                /*boolean no = false;
                if (aTextField.getText().isEmpty()) {
                    no = true;
                }
                else if (cTextField.getText().isEmpty()) {
                    no = true;
                }
                else if (fTextField.getText().isEmpty()) {
                    no = true;
                }
                else if (lTextField.getText().isEmpty()) {
                    no = true;
                }
                else if (dTextField1.getText().isEmpty()) {
                    no = true;
                }
                else if (dTextField2.getText().isEmpty()) {
                    no = true;
                }
                else if (dTextField3.getText().isEmpty()) {
                    no = true;
                }
                if (no) {
                    JOptionPane.showMessageDialog(null, "Not all information is entered. Please update it.",
                            "Oops", JOptionPane.ERROR_MESSAGE);
                    isCreditCardValid = false;
                }
                else {
                    boolean fake = false;
                    if (!fTextField.getText().equals(guest.getFirstName())) {
                        fake = true;
                    }
                    if (!lTextField.getText().equals(guest.getLastName())) {
                        fake = true;
                    }
                    if (!aTextField.getText().equals(guest.getAddress())) {
                        //if (aTextField.getText().equals("")) {
                        fake = true;
                        //}
                        // {
                            //guest.setAddress(aTextField.getText());
                        //}
                    }
                    if (!cTextField.getText().equals(String.valueOf(guest.getCardNum()))) {
                        //if (cTextField.getText().equals("")) {
                        fake = true;
                        //}
                        //else {
                        //    guest.setCardNum(Integer.parseInt(cTextField.getText()));
                        //}
                    }
                    if (!dTextField1.getText().equals(String.valueOf(1+guest.getCardExpiration().getMonth()))) {
                        fake = true;
                    }
                    if (!dTextField2.getText().equals(String.valueOf(guest.getCardExpiration().getDate()))) {
                        fake = true;
                    }
                    if (!dTextField3.getText().equals(String.valueOf(1900+guest.getCardExpiration().getYear()))) {
                        fake = true;
                    }
                    if (fake) {
                        JOptionPane.showMessageDialog(null, "Some of the information inputted is incorrect. Please update it.",
                                "Oops", JOptionPane.ERROR_MESSAGE);
                        isCreditCardValid = false;
                    }*/
                    //else {
                        JOptionPane.showMessageDialog(null, "Credit Card info successfully updated",
                                "Yay", JOptionPane.DEFAULT_OPTION);

                        isCreditCardValid = true;

                guest.setAddress(aTextField.getText());
                guest.setCardNum(Integer.parseInt(cTextField.getText()));
                guest.setCardExpiration(new Date(Integer.parseInt(dTextField3.getText())-1900,
                        Integer.parseInt(dTextField1.getText())-1,
                        Integer.parseInt(dTextField2.getText())));

                    //}
                //}
            }
        });


        buttonPane.add(blank3);
        buttonPane.add(buttonSubmit);


        add(aPane);
        add(blank4);
        add(blank5);
        add(bPane);
        add(blank6);
        add(cPane);
        add(blank7);
        add(blank8);
        add(buttonPane);

        namePane.setVisible(true);
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