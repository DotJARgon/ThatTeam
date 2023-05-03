package ui.custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * ClickableText is a custom ui element that appears like a hyperlink,
 * it has a click action event that allows for custom behavior when clicking
 * it
 * */
public class ClickableText extends JLabel {
    private Color defaultColor = DefaultPalette.defaultColor;
    private Color highlightColor = DefaultPalette.highlightColor;
    /**
     * ClickableText constructor, this initializes this text element with specific text
     *
     * @param label The text to be displayed that can be clicked on
     * */
    public ClickableText(String label) {
        super(label);
        this.updateColor(defaultColor);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * addClickAction is similar to an action listener, it is a callback
     * function that is called when this text label is clicked on
     * @param click the callback function (a functional interface) that will be
     *              called when this component is clicked on
     */
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

    /**
     * This function updates the foreground, it is a private function
     * that helps with changing the color when the mouse hovers
     * @param color the color to set the text color to
     */
    private void updateColor(Color color) {
        this.setForeground(color);
    }

    /**
     * setDefault sets the default color of this element, which is the
     * color when the mouse is not hovering
     * @param color the color to set the text color to
     */
    public void setDefault(Color color) {
        this.defaultColor = color;
    }

    /**
     * setHighlight sets the highlight color to the passed parameter,
     * which is the color when the mouse is hovering
     * @param color the color to set the text color to
     */
    public void setHighlight(Color color) {
        this.highlightColor = color;
    }
}
