package ui.rooms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DateSelector extends JPanel {
    private static Integer[] MONTH, DAY, YEAR, HOUR, MINUTE;
    static {
        DateSelector.MONTH = new Integer[12];
        for(int i = 0; i < 12; i++) DateSelector.MONTH[i] = i+1;
        DateSelector.DAY = new Integer[31];
        for(int i = 0; i < 31; i++) DateSelector.DAY[i] = i+1;
        DateSelector.YEAR = new Integer[100];
        for(int i = 0; i < 100; i++) DateSelector.YEAR[i] = 2000+i;

        DateSelector.HOUR = new Integer[12];
        for(int i = 0; i < 12; i++) DateSelector.HOUR[i] = i + 1;
        DateSelector.MINUTE = new Integer[60];
        for(int i = 0; i < 60; i++) DateSelector.MINUTE[i] = i;

    }
    private JComboBox<Integer> month, day, year, hour, minute;
    private final String name;

    private final JPanel comboPanel;
    private final JLabel selected;

    private String mm = MONTH[0].toString(),
            dd = DAY[0].toString(),
            yy = YEAR[0].toString(),
            hr = HOUR[0].toString(),
            mn = MINUTE[0].toString();

    private final ActionListener monthListener = e -> {
        this.mm = this.month.getSelectedItem().toString();
        buildLabel();
    };
    private final ActionListener dayListener = e -> {
        this.dd = this.day.getSelectedItem().toString();
        buildLabel();
    };
    private final ActionListener yearListener = e -> {
        this.yy = this.year.getSelectedItem().toString();
        buildLabel();
    };
    private final ActionListener hourListener = e -> {
        this.hr = this.hour.getSelectedItem().toString();
        buildLabel();
    };
    private final ActionListener minuteListener = e -> {
        this.mn = this.minute.getSelectedItem().toString();
        buildLabel();
    };

    public DateSelector(String name) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createHorizontalGlue());

        this.name = name;
        this.comboPanel = new JPanel(new GridLayout(2, 5));

        this.selected = new JLabel();
        buildLabel();

        this.month = new JComboBox<>(DateSelector.MONTH);
        this.day = new JComboBox<>(DateSelector.DAY);
        this.year = new JComboBox<>(DateSelector.YEAR);
        this.hour = new JComboBox<>(DateSelector.HOUR);
        this.minute = new JComboBox<>(DateSelector.MINUTE);

        this.month.addActionListener(monthListener);
        this.day.addActionListener(dayListener);
        this.year.addActionListener(yearListener);
        this.hour.addActionListener(hourListener);
        this.minute.addActionListener(minuteListener);

        this.comboPanel.add(new JLabel("Month"));
        this.comboPanel.add(new JLabel("Day"));
        this.comboPanel.add(new JLabel("Year"));
        this.comboPanel.add(new JLabel("Hour"));
        this.comboPanel.add(new JLabel("Minute"));

        this.comboPanel.add(this.month);
        this.comboPanel.add(this.day);
        this.comboPanel.add(this.year);
        this.comboPanel.add(this.hour);
        this.comboPanel.add(this.minute);

        this.add(selected);
        this.add(comboPanel);
    }

    private void buildLabel() {
        this.selected.setText(this.name + ": " + this.mm + "/" + this.dd + "/" + this.yy + " " + this.hr + ":" + this.mn);
    }
}
