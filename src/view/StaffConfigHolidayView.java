package src.view;

import src.controller.*;
import src.handler.*;


public class StaffConfigHolidayView {
  
    private String errorMessage;

  
    public StaffConfigHolidayView() {
        this.errorMessage = "";
    }

    public void printMenu() {
        MainView.printBoilerPlate("Configure Holiday Dates");
        MainView.printMenuContent("""

                How would you like to configure holidays?

                01. Add holiday
                02. Delete holiday
                03. Quit and return back
                """);
    }

  
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
