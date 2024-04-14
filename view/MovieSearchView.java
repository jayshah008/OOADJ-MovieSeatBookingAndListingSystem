package src.view;

import src.handler.*;
import src.model.*;
import src.controller.*;
import java.util.ArrayList;

/**
 * View for movie goer to search for a particular movie title
 * 
 * @author Yeek Sheng, Jerick
 * @version 1.0
 */
public class MovieSearchView extends MainView {
    /**
     * MovieGoer object {@link MovieGoer}
     */
    private MovieGoer movieGoer;

    /**
     * Error message of the view
     */
    private String errorMessage;

    /**
     * Creates a a new MovieSearchView with movieGoer
     * 
     * @param movieGoer movieGoer accessing the view {@link MovieGoer}
     */
    public MovieSearchView(MovieGoer movieGoer) {
        this.movieGoer = movieGoer;
        this.errorMessage = "";
    }

    /**
     * Method to print the boiler plate and menu content
     */
    public void printMenu() {
        MainView.printBoilerPlate("Search Movie");
        MainView.printMenuContent("Search for a movie (Press 1 to exit)");
    }

    /**
     * Method to take in the choice of the user and calls MovieDetailsView if the
     * movieTitle is available
     */
    public void appContent() {
        String movieTitle;

        do {
            if (MovieMenuView.exit) {
                return;
            }

            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            movieTitle = InputHandler.stringHandler();

            if (movieTitle.equals("1")) {
                this.errorMessage = "";
                return;
            }

            ArrayList<Movie> movies = MovieManager.getMovieList(movieTitle);

            if (movies.size() != 0) {
                this.errorMessage = "";
                MovieDetailsView detailsView = new MovieDetailsView(movieTitle, movieGoer);
                detailsView.appContent();
            } else {
                this.errorMessage = "Error! The searched movie is not available!";
                continue;
            }

        } while (true);
    }
}