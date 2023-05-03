package ui.custom;

/**
 * The NavUpdate interface is meant to allow for individual pages
 * to "know" that they are being viewed, this is to allow the page
 * to populate data. It keeps resource usage down since there
 * is no need to constantly update every page constantly. It is meant
 * to be used as a lambda expression in some cases, or just a single
 * implementation
 * @author  Marcelo Carpenter
 * @version  1.2
 * @since 3/12/23
 */
@FunctionalInterface
public interface NavUpdate {
    /**
     * navUpdate is an update function called before a navigation
     * is completed to a UI element, giving the UI element time to
     * load data (especially useful for classes that cannot initialize,
     * all data upon being created)
     * */
    void navUpdate();
}
