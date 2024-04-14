package src.handler;

import java.util.Scanner;


public class InputHandler {
    
    private static Scanner scanner = new Scanner(System.in);

   
    public InputHandler() {
    }

    
    public static int intHandler() {
        try {
            int input = InputHandler.scanner.nextInt();
            scanner.nextLine();
            return input;
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Error! Please enter a valid integer!");
            return -1;
        }
    }

    
    public static String stringHandler() {
        try {
            String input = InputHandler.scanner.nextLine();
            return input;
        } catch (Exception e) {
            System.out.println("Error! Please enter a valid string!");
            return null;
        }
    }

    
    public static double doubleHandler() {
        try {
            double input = InputHandler.scanner.nextDouble();
            return input;
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Error! Please enter a valid double!");
            return -1;
        }
    }
}
