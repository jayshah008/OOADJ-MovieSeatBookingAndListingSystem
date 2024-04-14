package src.view;

import src.controller.*;
import src.database.*;
import src.handler.*;

/**
 * View class for handling all UI related to {@link Database}
 * 
 * @author Lee Juin
 * @version 1.0
 */
public class DatabaseView extends MainView {
    private StaffAddMovieView staffAddMovieView;
    private String errorMessage;

    /**
     * Constructor
     */
    public DatabaseView() {
        this.errorMessage = "";
    }

    /**
     * Standard function to print the menu for user to choose the next action
     */
    public void printMenu() {
        MainView.printBoilerPlate("Database");
        MainView.printMenuContent("""

                Welcome to the Database! What would you like to do?

                01. Load initial data into database
                02. Add new movies into database
                03. Reset database
                04. Quit and return back
                """);
    }

    /**
     * Main content of the UI
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
                    System.out.println("Loading... Please wait.");
                    DatabaseManager.initializePrices();
                    if (DatabaseManager.initializeCineplexData() && DatabaseManager.initializeMovie()
                            && DatabaseManager.initializeMovieScheduleData()
                            && DatabaseManager.initalizeCinemaStaff()) {
                        this.errorMessage = "Data loaded successfully!";
                    } else {
                        this.errorMessage = "Existing data in the database. Only price data are resetted!";
                    }
                    break;
                case 2:
                    this.staffAddMovieView = new StaffAddMovieView();
                    this.errorMessage = "";
                    staffAddMovieView.appContent();
                    break;
                case 3:
                    Database.resetDatabase();
                    this.errorMessage = "Database resetted successfully!";
                    break;
                case 4:
                    this.errorMessage = "";
                    return;
                default:
                    this.errorMessage = "Error! Please enter a valid input!";
                    continue;
            }
        } while (true);
    }
}
