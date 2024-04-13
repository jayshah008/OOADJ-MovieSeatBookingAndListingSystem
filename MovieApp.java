package src;

import src.handler.*;
import src.view.*;


/**
 * The start of the MOBLIMA Application
 * @author Lee Juin
 * @version 1.0
 */
public class MovieApp {

    /**
     * Default constructor
     */
    public MovieApp(){}
    
    private static MovieAppView defaultView = new MovieAppView();

    /**
     * Main driver function for the application
     * @param args is the arguments to be passed into the function
     */
    public static void main(String args[]) {
        UIHandler.clearScreen();
        System.out.println("");
        System.out.println(" ╔═══════════════════════════════════════════════════════════════╗");
        System.out.println(" ║                                                               ║");
        System.out.println(" ║     ╔══╗══╗  ╔════╗  ╔═══╗  ╔      ╔   ╔══╗══╗   ╔════╗       ║");
        System.out.println(" ║     ║  ║  ║  ║    ║  ║═══╝  ║      ║   ║  ║  ║   ║    ║       ║");
        System.out.println(" ║     ║  ║  ║  ║    ║  ║═══╗  ║      ║   ║  ║  ║   ║════║       ║");
        System.out.println(" ║     ╝     ╚  ╚════╝  ╚═══╝  ╚════  ╝   ╝     ╚   ╝    ╚       ║");
        System.out.println(" ║                                                               ║");
        System.out.println(" ║       MOvie Booking and LIsting Management Application        ║");
        System.out.println(" ║                                                               ║");
        System.out.println(" ╚═══════════════════════════════════════════════════════════════╝");
        System.out.println("");
        System.out.println(" Press any key to continue: ");
        InputHandler.stringHandler();
        defaultView.appContent();
        System.out.println(" Thank you for using MOBLIMA!");
    }
}
