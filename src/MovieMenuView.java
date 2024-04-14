package src.view;

import src.handler.*;
import src.model.*;


public class MovieMenuView {

    public static boolean exit = false;

    private MovieListView movieListView;

    private MovieGoer movieGoer;

    private String errorMessage;

    public MovieMenuView(MovieGoer movieGoer) {
        this.movieGoer = movieGoer;
        this.movieListView = new MovieListView(this.movieGoer);
        this.errorMessage = "";
    }

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
