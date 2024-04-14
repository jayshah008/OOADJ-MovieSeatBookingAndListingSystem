package src.view;

import src.handler.*;
import src.model.*;

/**
 * View for movie goer after successful login or register
 * 
 * @author Yeek Sheng, Jerick
 * @version 1.0
 */
public class MovieMenuView {
    /**
     * Static variable to return to MovieMenuView after successful payment
     */
    public static boolean exit = false;

    /**
     * MovieListView to be displayed
     */
    private MovieListView movieListView;

    /**
     * MovieGoer object {@link MovieGoer}
     */
    private MovieGoer movieGoer;

    /**
     * Error message of the view
     */
    private String errorMessage;

    /**
     * Creates a new MovieMenuView with the movieGoer
     * Constructs the MovieListView
     * 
     * @param movieGoer movieGoer accessing the view {@link MovieGoer}
     */
    public MovieMenuView(MovieGoer movieGoer) {
        this.movieGoer = movieGoer;
        this.movieListView = new MovieListView(this.movieGoer);
        this.errorMessage = "";
    }

    /**
     * Method to print the boiler plate and displays the choices of the user
     */
    public void printMenu() {
        MainView.printBoilerPlate("Main Menu");
        MainView.printMenuContent(String.format("""

                Welcome to MOBLIMA! %s

                01. List Movies
                02. Search Movie
                03. View Booking History
                04. List Top 5 Movies
                05. Logout
                """, this.movieGoer.getUsername()));
    }

    /**
     * Method to take in the choice of the user and creates a corresponding view
     * Available choices for MovieListView, MovieSearchView,
     * MovieBookingHistoryView, MovieListRankingView
     * Sets exit boolean to false if it is true
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
                    this.movieListView.appContent();
                    break;
                case 2:
                    MovieSearchView search = new MovieSearchView(this.movieGoer);
                    search.appContent();
                    break;
                case 3:
                    MovieBookingHistoryView bookingView = new MovieBookingHistoryView(this.movieGoer);
                    bookingView.appContent();
                    break;
                case 4:
                    MovieListRankingView rank = new MovieListRankingView();
                    rank.appContent();
                    break;
                case 5:
                    this.errorMessage = "";
                    return;
                default:
                    this.errorMessage = "Error! Please enter a valid input!";
            }

            if (MovieMenuView.exit) {
                MovieMenuView.exit = false;
            }

        } while (true);
    }
}
