package ui.custom;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.util.Calendar;

public class DateBox extends JPanel {
    private static final String[] MONTHS = new String[]{
            "January", "February", "March", "April", "May", "June", "July",
            "August", "Sepetember", "October", "November", "December"
    };
    private int year = 0, month = 0, day = 0, numDays = 31;
    private final JLabel yearLabel = new JLabel(), monthLabel = new JLabel(), dateLabel = new JLabel();
    private final JButton[] dayButtons = new JButton[31];

    private final JButton nextYearButton, prevYearButton, nextMonthButton, prevMonthButton;

    private final JPanel dayPanel;

    private final ActionListener daySelect = e -> {
        int d = Integer.parseInt(e.getActionCommand());
        this.day = d;
        this.updateDays();
        this.updateDate();
    };

    private final ActionListener nextMonth = e -> this.nextMonth();
    private final ActionListener prevMonth = e -> this.prevMonth();

    private final ActionListener nextYear = e -> {
        this.year++;
        this.yearLabel.setText(Integer.toString(this.year));
        this.updateDays();
        this.updateDate();
    };
    private final ActionListener prevYear = e -> {
        Calendar cal = Calendar.getInstance();
        int currYear = cal.get(Calendar.YEAR);
        this.year = Math.max(this.year - 1, currYear);
        this.yearLabel.setText(Integer.toString(this.year));
        this.updateDays();
        this.updateDate();
    };

    private Color highlightColor = DefaultPalette.highlightColor;
    private Color defaultColor = DefaultPalette.defaultColor;

    public DateBox() {
        super();
        this.setLayout(new GridBagLayout());
        this.setBorder(new EmptyBorder(16, 16, 16, 16));

        this.dayPanel = new JPanel(new GridLayout(5, 7, 1, 1));

        for(int i = 0; i < dayButtons.length; i++) {
            dayButtons[i] = new JButton(Integer.toString(i + 1));
            dayButtons[i].addActionListener(daySelect);
            this.dayPanel.add(dayButtons[i]);
        }

        Calendar cal = Calendar.getInstance();
        int d = cal.get(Calendar.DAY_OF_MONTH);
        int m = cal.get(Calendar.MONTH);
        int y = cal.get(Calendar.YEAR);

        this.year = y;
        this.month = m;
        this.day = d;

        this.yearLabel.setText(Integer.toString(this.year));
        this.monthLabel.setText(MONTHS[this.month]);
        updateDays();
        updateDate();

        this.nextYearButton = new JButton(">");
        this.prevYearButton = new JButton("<");

        this.nextYearButton.addActionListener(this.nextYear);
        this.prevYearButton.addActionListener(this.prevYear);

        this.nextMonthButton = new JButton(">");
        this.prevMonthButton = new JButton("<");

        this.nextMonthButton.addActionListener(this.nextMonth);
        this.prevMonthButton.addActionListener(this.prevMonth);

        GridBagConstraints con = new GridBagConstraints();

        con.gridx = 0;
        con.gridy = 0;
        con.fill = GridBagConstraints.NONE;
        con.anchor = GridBagConstraints.CENTER;
        this.add(this.prevYearButton, con);

        con.gridx = 1;
        con.gridy = 0;
        con.fill = GridBagConstraints.HORIZONTAL;
        con.anchor = GridBagConstraints.CENTER;
        this.add(this.yearLabel, con);

        con.gridx = 2;
        con.gridy = 0;
        con.fill = GridBagConstraints.NONE;
        con.anchor = GridBagConstraints.CENTER;
        this.add(this.nextYearButton, con);

        con.gridx = 0;
        con.gridy = 1;
        con.fill = GridBagConstraints.NONE;
        con.anchor = GridBagConstraints.CENTER;
        this.add(this.prevMonthButton, con);

        con.gridx = 1;
        con.gridy = 1;
        con.fill = GridBagConstraints.NONE;
        con.anchor = GridBagConstraints.CENTER;
        this.add(this.monthLabel, con);

        con.gridx = 2;
        con.gridy = 1;
        con.fill = GridBagConstraints.NONE;
        con.anchor = GridBagConstraints.CENTER;
        this.add(this.nextMonthButton, con);

        con.gridx = 3;
        con.gridy = 1;
        con.fill = GridBagConstraints.NONE;
        con.anchor = GridBagConstraints.CENTER;
        this.add(this.dateLabel, con);

        con.gridx = 1;
        con.gridy = 2;
        con.gridheight = 3;
        con.gridwidth = 3;
        con.fill = GridBagConstraints.BOTH;
        con.anchor = GridBagConstraints.CENTER;
        this.add(this.dayPanel, con);

    }

    private void nextMonth() {
        this.month = (this.month + 1) % 12;
        this.monthLabel.setText(MONTHS[this.month]);
        this.updateDays();
        this.updateDate();
    }
    private void prevMonth() {
        Calendar cal = Calendar.getInstance();
        int m = cal.get(Calendar.MONTH);
        int y = cal.get(Calendar.YEAR);

        if(!(this.year == y && this.month == m)) {
            this.month = (this.month + 11) % 12;
            this.monthLabel.setText(MONTHS[this.month]);
            this.updateDays();
            this.updateDate();
        }
    }

    private void updateDays() {
        Calendar cal = Calendar.getInstance();
        int d = cal.get(Calendar.DAY_OF_MONTH);
        int m = cal.get(Calendar.MONTH);
        int y = cal.get(Calendar.YEAR);

        if(this.year == y && this.month < m ) {
            this.month = m;
            this.monthLabel.setText(MONTHS[this.month]);
        }

        YearMonth yearMonthObject = YearMonth.of(this.year, this.month + 1);
        this.numDays = yearMonthObject.lengthOfMonth();
        for(int i = 0; i < dayButtons.length; i++) {
            dayButtons[i].setForeground(this.defaultColor);
            dayButtons[i].setVisible(i < this.numDays);

            //if before current date
            if(this.year == y && this.month == m && i < d - 1) {
                dayButtons[i].setVisible(false);
            }
        }

        this.day = Math.min(this.day, this.numDays);

        if(this.year == y && this.month+1 == m) {
            this.day = Math.max(this.day, d);
        }

        this.dayButtons[this.day - 1].setForeground(this.highlightColor);
    }

    private void updateDate() {
        String date = (this.month + 1) + "/" + (this.day) + "/" + (this.year);
        this.dateLabel.setText(date);
    }
}
