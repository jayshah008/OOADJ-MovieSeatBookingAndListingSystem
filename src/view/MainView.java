package src.view;


public abstract class MainView {


    public MainView() {
    }

 
    public abstract void printMenu();

    public abstract void appContent();


    public static void printBoilerPlate(String content) {
        String spaces = String.format("%" + (75 - content.length()) + "s", "");
        System.out.println(
                "╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ " + content + spaces + "║");
        System.out.println(
                "╚════════════════════════════════════════════════════════════════════════════╝");
    }


    public static void printMenuContent(String content) {
        System.out.println(
                "---------------------------");
        System.out.println(content);
        System.out.println(
                "--------------------------");
    }
}
