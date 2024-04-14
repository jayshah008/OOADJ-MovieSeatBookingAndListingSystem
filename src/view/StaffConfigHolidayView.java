package src.view;

import src.controller.*;
import src.handler.*;

/**
 * View for cinema staff to add or delete the holiday from the holidays
 * ArrayList in the database
 * 
 * @author Jonathan Ng
 * @version 1.0
 */
public class StaffConfigHolidayView {
    /**
     * Error message of this view
     */
    private String errorMessage;

    /**
     * Constructor
     */
    public StaffConfigHolidayView() {
        this.errorMessage = "";
    }

    /**
     * Method to print the boiler plate
     */
    public void printMenu() {
        MainView.printBoilerPlate("Configure Holiday Dates");
        MainView.printMenuContent("""

                How would you like to configure holidays?

                01. Add holiday
                02. Delete holiday
                03. Quit and return back
                """);
    }

    /**
     * Method to call printMenu
     * Upon successful choice, configureHoliday under CinemaStaffManager will be
     * called and the holiday will be added/deleted and the database will be updated
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
                    this.errorMessage = "";
                    CinemaStaffManager.configureHoliday(choice);
                    break;

                case 2:
                    this.errorMessage = "";
                    CinemaStaffManager.configureHoliday(choice);
                    break;

                case 3:
                    this.errorMessage = "";
                    return;

                default:
                    this.errorMessage = "Error! Please enter a valid input!";
                    continue;
            }
        } while (true);
    }
}
