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
        System.out.println(" ╔════════════════════════════════════════════════════════╗");
        System.out.println(" ║                                                        ║");
        System.out.println(" ║     ╔══╗══╗  ╔════╗  ╔════╗  ╔════╗ ╔══   ╔══╗         ║");
        System.out.println(" ║     ║  ║  ║  ║    ║  ║    ║  ║    ║ ║  ║    ║          ║");
        System.out.println(" ║     ║  ║  ║  ║    ║  ║    ║  ║════║ ║  ║    ║          ║");
        System.out.println(" ║     ╝     ╚  ╚════╝  ╚════╝  ╝    ╚ ╚══   ══╝          ║");
        System.out.println(" ║                                                        ║");
        System.out.println(" ║     MOvie system On Object Oriented Application        ║");
        System.out.println(" ║                                                        ║");
        System.out.println(" ║                                                        ║");
        System.out.println(" ║ Done By:                                               ║");
        System.out.println(" ║ 1)Jawahar Balachandher - PES2UG21CS212                 ║");
        System.out.println(" ║ 2)Jay Mintu Shah - PES2UG21CS213                       ║");
        System.out.println(" ║ 3)Kota Bharadwaj - PES2UG21CS234                       ║");
        System.out.println(" ║ 4)Kothuri Venkata Srujan - PES2UG21CS236               ║");
        System.out.println(" ║ 5)Krish Bharadwaj - PES2UG21CS238                      ║");
        System.out.println(" ╚════════════════════════════════════════════════════════╝");

        System.out.println("");
        System.out.println(" Press any key to continue: ");
        InputHandler.stringHandler();
        defaultView.appContent();
        System.out.println(" Thank you for using MOOADJ!");
    }
}
