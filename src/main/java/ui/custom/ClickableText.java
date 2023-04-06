package ui.custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClickableText extends JLabel {
    private Color defaultColor = DefaultPalette.defaultColor;
    private Color highlightColor = DefaultPalette.highlightColor;
    public ClickableText(String label) {
        super(label);
        this.updateColor(defaultColor);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void addClickAction(final Clickable click) {
        final ClickableText myself = this;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click.callback();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                myself.updateColor(myself.highlightColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                myself.updateColor(myself.defaultColor);
            }
        });
    }

    private void updateColor(Color color) {
        this.setForeground(color);
    }

    public void setDefault(Color color) {
        this.defaultColor = color;
    }
    public void setHighlight(Color color) {
        this.highlightColor = color;
    }
}
