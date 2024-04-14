package src.view;

/**
 * All views will inherit from this class
 * Provides the boilerplate for the program
 * 
 * @author Lee Juin
 * @version 1.0
 */
public abstract class MainView {

    /**
     * Constructor
     */
    public MainView() {
    }

    /**
     * Abstract method to be implemented to print the menu for user to select the
     * next action
     */
    public abstract void printMenu();

    /**
     * Abstract method to print the main content of the UI
     */
    public abstract void appContent();

    /**
     * Used in printing the title of the view such as "Database" or "Add Movie"
     * 
     * @param content is the title to be shown
     */
    public static void printBoilerPlate(String content) {
        String spaces = String.format("%" + (75 - content.length()) + "s", "");
        System.out.println(
                "╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ " + content + spaces + "║");
        System.out.println(
                "╚════════════════════════════════════════════════════════════════════════════╝");
    }

    /**
     * Used in printing the selection for users. Differentiated from boilerplate to
     * provide better UI experiences
     * 
     * @param content is all the selection available for the user
     */
    public static void printMenuContent(String content) {
        System.out.println(
                "---------------------------");
        System.out.println(content);
        System.out.println(
                "--------------------------");
    }
}
