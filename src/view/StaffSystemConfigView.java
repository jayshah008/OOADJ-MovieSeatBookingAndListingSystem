package src.view;

import src.handler.*;
import src.model.*;

/**
 * View for the initial cinema staff module upon successful login
 * 
 * @author Jonathan Ng
 * @version 1.0
 */

public class StaffSystemConfigView extends MainView {

    /**
     * StaffMovieDetailsView to be displayed
     */
    private StaffMovieDetailsView staffMovieDetailsView;

    /**
     * StaffConfigSettings to be displayed
     */
    private StaffConfigSettingsView staffConfigSettingsView;

    /**
     * StaffMovieListRankingView to be displayed
     */
    private StaffMovieListRankingView staffMovieListRankingView;

    /**
     * DatabaseView to be displayed
     */
    private DatabaseView databaseView;

    /**
     * Error message of the view
     */
    private String errorMessage;

    /**
     * Cinema Staff {@link CinemaStaff} object
     */
    private CinemaStaff cinemaStaff;

    /**
     * Constructor for the StaffSystemConfig module
     * 
     * @param cinemaStaff {@link CinemaStaff}
     */
    public StaffSystemConfigView(CinemaStaff cinemaStaff) {
        this.errorMessage = "";
        this.cinemaStaff = cinemaStaff;
    }

    /**
     * Method to print boiler plate for the staff system module
     */
    public void printMenu() {
        MainView.printBoilerPlate("Staff Module");
        MainView.printMenuContent("""

                Select a function to be executed.

                01. Configure movie details
                02. Configure system settings
                03. Configure Database
                04. List Top 5
                05. Logout
                """);
    }

    /**
     * Method to call printMenu and accepts cinema staffs' input of what function is
     * to be executed
     * Navigates to different views based on the choice of the cinema staff upon
     * successful choice
     */
    public void appContent() {
        int choice = -1;

        do {
            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            choice = InputHandler.intHandler();

            if (choice < 0 || choice > 5) {
                this.errorMessage = "Error! Please enter a valid input!";
                continue;
            }
            switch (choice) {
                case 1:
                    this.errorMessage = "";
                    this.staffMovieDetailsView = new StaffMovieDetailsView(this.cinemaStaff);
                    this.staffMovieDetailsView.appContent();
                    break;

                case 2:
                    this.errorMessage = "";
                    this.staffConfigSettingsView = new StaffConfigSettingsView();
                    this.staffConfigSettingsView.appContent();
                    break;

                case 3:
                    this.errorMessage = "";
                    this.databaseView = new DatabaseView();
                    this.databaseView.appContent();
                    break;

                case 4:
                    this.errorMessage = "";
                    this.staffMovieListRankingView = new StaffMovieListRankingView();
                    this.staffMovieListRankingView.appContent();
                    break;

                case 5:
                    this.errorMessage = "";
                    return;
            }
        } while (true);
    }
}
