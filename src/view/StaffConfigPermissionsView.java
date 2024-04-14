package src.view;

import src.handler.*;
import src.controller.*;

/**
 * View for cinema staffs to configure movie goer's permissions to view top 5
 * movies
 * 
 * @author Jonathan Ng
 * @version 1.0
 */
public class StaffConfigPermissionsView {
    /**
     * Error message of this view
     */
    private String errorMessage;

    /**
     * Constructor
     */
    public StaffConfigPermissionsView() {
        this.errorMessage = "";
    }

    /**
     * Method to print boiler plate
     */
    public void printMenu() {
        MainView.printBoilerPlate("Configure User Permissions");
        MainView.printMenuContent("""
                How would you like to configure movie goer permissions?

                01. Opt out list by overall ratings permission
                02. Opt out list by movie sales permission
                03. Opt in list by overall ratings permission
                04. Opt in list by movie sales permission
                05. Return
                """);
    }

    /**
     * Method to call printMenu
     * Upon successful choice, optInOut under CinemaStaffManager will be called and
     * the movieGoer's permissions will be updated and saved into the database
     */
    public void appContent() {
        int choice = -1;

        do {
            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            choice = InputHandler.intHandler();

            switch (choice) {
                case 1:
                    this.errorMessage = "Movie goer can no longer view top 5 based on overall ratings.";
                    CinemaStaffManager.optInOut(1, false);
                    break;

                case 2:
                    this.errorMessage = "Movie goer can no longer view top 5 based on movie sales.";
                    CinemaStaffManager.optInOut(2, false);
                    break;

                case 3:
                    this.errorMessage = "Movie goer can now view top 5 based on overall ratings.";
                    CinemaStaffManager.optInOut(1, true);
                    break;

                case 4:
                    this.errorMessage = "Movie goer can now view top 5 based on movie sales.";
                    CinemaStaffManager.optInOut(2, true);
                    break;

                case 5:
                    this.errorMessage = "";
                    return;

                default:
                    this.errorMessage = "Error! Please enter a valid input!";
                    continue;
            }
        } while (true);
    }
}
