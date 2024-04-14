package src.handler;


public class UIHandler {

    
    public UIHandler() {
    }

    
    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception err) {

        }
    }
}
