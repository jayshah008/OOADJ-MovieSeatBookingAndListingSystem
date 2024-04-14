package src.view;

import src.handler.*;
import src.controller.*;
import src.model.*;

/**
 * View for the cinema staff to configure specific detail of the movie selected
 * from StaffMovieDetailsView
 * 
 * @author Jonathan Ng
 * @version 1.0
 */
public class StaffConfigureMovieView {
    /**
     * Movie {@link Movie} object}
     */
    private Movie movie;

    /**
     * Error message of this view
     */
    private String errorMessage;

    /**
     * Constructor for StaffConfigureMovieView
     * 
     * @param movie is the movie selected by cinema staff under
     *              StaffMovieDetailsView
     */
    public StaffConfigureMovieView(Movie movie) {
        this.movie = movie;
        this.errorMessage = "";
    }

    /**
     * Method to print boiler plate
     */
    public void printMenu() {
        MainView.printBoilerPlate("Configure Setting for " + this.movie.getMovieTitle());
        MainView.printMenuContent("""

                Select the detail to be configured.

                01. Movie Title
                02. Movie Type
                03. Age Rating
                04. Showing Status
                05. Cast Member's Names
                06. Movie Director's Name
                07. Movie Synopsis
                08. Movie Duration
                09. Movie Schedule
                10. Return.
                    """);
    }

    /**
     * Method to call print Menu, queries cinema staff for the detail to be
     * configured
     * Calls updateExistingMovieDetails under CinemaStaffManager
     */
    public void appContent() {
        int choice = -1;

        do {
            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            choice = InputHandler.intHandler();

            if (choice == 10) {
                this.errorMessage = "";
                return;
            } else if (choice <= 9 && choice >= 1) {
                this.errorMessage = "";
                CinemaStaffManager.updateExistingMovieDetails(this.movie, choice);
            } else {
                this.errorMessage = "Error! Please enter a valid input!";
                continue;
            }
        } while (true);
    }
}