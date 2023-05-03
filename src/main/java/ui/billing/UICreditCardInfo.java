package ui.billing;
import user_services.Guest;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class UICreditCardInfo extends JPanel {
    private final Guest guest;
    private boolean isCreditCardValid;

    private final JTextField fTextField, lTextField, aTextField, cTextField, dTextField1, dTextField2, cwTextField;

    public UICreditCardInfo(Guest guest) {
        super();
        this.guest = guest;
        this.isCreditCardValid = false;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(16, 16, 16, 16));

        setPreferredSize(new Dimension(600, 400));

        //First Name
        JLabel fLabel = new JLabel(" First Name: ");
        fTextField = new JTextField(12);
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
        lTextField = new JTextField(12);

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

        aTextField = new JTextField(12);
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
        cTextField = new JTextField(20);
        cTextField.setMaximumSize(new Dimension(3000, 40));
        JPanel bPane = new JPanel();
        bPane.setLayout(new BoxLayout(bPane, BoxLayout.X_AXIS));

        bPane.add(cLabel);
        bPane.add(cTextField);

        //Expiration Date + Separators
        JPanel cPane = new JPanel();
        cPane.setLayout(new BoxLayout(cPane, BoxLayout.X_AXIS));
        cPane.setSize(new Dimension(800, 100));

        JLabel dLabel = new JLabel("Expiration Date (mm/yyyy): ");
        dTextField1 = new JTextField(12);
        dTextField2 = new JTextField(12);
        JLabel dLabelA = new JLabel(" / ");

        dLabelA.setMaximumSize(new Dimension(10, 30));
        dTextField1.setMaximumSize(new Dimension(30, 30));
        dTextField2.setMaximumSize(new Dimension(60, 30));

        cPane.add(dLabel);
        cPane.add(dTextField1);
        cPane.add(dLabelA);
        cPane.add(dTextField2);

        JPanel cwPane = new JPanel();
        cwPane.setLayout(new BoxLayout(cwPane, BoxLayout.X_AXIS));
        cwPane.setSize(new Dimension(800, 100));
        JLabel cwLabel = new JLabel("Enter cw: ");
        cwTextField = new JTextField(3);
        cwTextField.setMaximumSize(new Dimension(40, 30));
        cwPane.add(cwLabel);
        cwPane.add(cwTextField);

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


        buttonPane.add(blank3);
        buttonPane.add(buttonSubmit);

        add(aPane);
        add(blank4);
        add(blank5);
        add(bPane);
        add(blank6);
        add(cPane);
        add(cwPane);
        add(blank7);
        add(blank8);
        add(buttonPane);

        buttonSubmit.addActionListener(e -> {
            this.isCreditCardValid = validateInputs();
            if(this.isCreditCardValid) {
                String firstName = fTextField.getText();
                String lastName = lTextField.getText();
                String address = aTextField.getText();
                String cardnumber = cTextField.getText();

                String month = dTextField1.getText();
                String year = dTextField2.getText();

                String cw = cwTextField.getText();

                int m = Integer.parseInt(month);
                int y = Integer.parseInt(year);

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.MONTH, m);
                cal.set(Calendar.YEAR, y);

                this.guest.setCardFirstName(firstName);
                this.guest.setCardLastName(lastName);
                this.guest.setAddress(address);
                this.guest.setCardNum(cardnumber);
                this.guest.setCardExpiration(cal.getTime());
                this.guest.setCw(cw);


                JOptionPane.showMessageDialog(null,
                        "Credit Card info successfully updated, you may close this window",
                        "Updated", JOptionPane.DEFAULT_OPTION);
            }
            else {
                JOptionPane.showMessageDialog(null,
                        "One or more entries are incorrect, please fix them, or your card is expired",
                        "Incorrect", JOptionPane.ERROR_MESSAGE);
            }
        });

        fTextField.setText(guest.getCardFirstName());
        lTextField.setText(guest.getCardLastName());
        aTextField.setText(guest.getAddress());
        cTextField.setText(guest.getCardNum());
        cwTextField.setText(guest.getCw());
        Date exp = guest.getCardExpiration();
        if(exp != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(exp);
            dTextField1.setText(Integer.toString(cal.get(Calendar.MONTH)));
            dTextField2.setText(Integer.toString(cal.get(Calendar.YEAR)));
        }

        this.isCreditCardValid = validateInputs();

        namePane.setVisible(true);
        setVisible(true);
    }

    private boolean validateInputs() {
        String firstName = fTextField.getText();
        String lastName = lTextField.getText();
        String address = aTextField.getText();
        String cardnumber = cTextField.getText();
        String month = dTextField1.getText();
        String year = dTextField2.getText();
        String cw = cwTextField.getText();

        //if empty strings return false
        if(firstName.equals("") || lastName.equals("") || address.equals("") || cardnumber.equals("") || cardnumber.equals("") || month.equals("") || year.equals(""))
            return false;

        //if not numbers
        if(!cardnumber.matches("[0-9]+") || !month.matches("[0-9]+") || !year.matches("[0-9]+") || !cw.matches("[0-9]+"))
            return false;

        //apparently credit card numbers are 16 digits
        if(cardnumber.length() != 16)
            return false;

        if(cw.length() != 3)
            return false;

        int m = Integer.parseInt(month);
        int y = Integer.parseInt(year);

        //invalid month
        if(m < 1 || m > 12)
            return false;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, m);
        cal.set(Calendar.YEAR, y);

        //if the card is expired
        if(cal.before(Calendar.getInstance()))
            return false;

        return true;
    }


    public boolean isCreditCardValid() {
        return isCreditCardValid;
    }

}