package ui.custom;

/**
 * Clickable is a functional interface meant to allow for a callback
 * with the ClickableText class. This is to allow for custom behavior
 * upon clicking on the ClickableText, and is highly reusable, it
 * requires no parameters nor needs to return.
 * @author  Marcelo Carpenter
 * @version  1.1
 * @since 3/12/23
 */
@FunctionalInterface
public interface Clickable {
    /**
     * callback is a simple function that takes no parameters and returns
     * nothing
     * */
    void callback();
}