package src.view;

import src.handler.*;
import src.model.*;
import src.model.enums.*;
import src.controller.*;
import java.util.*;

/**
 * View for movie goer to view the different movie types for the movie title
 * 
 * @author Yeek Sheng, Jerick
 * @version 1.0
 */
public class MovieTypeView extends MainView {
    /**
     * Title of the movie
     */
    private String movieTitle;

    /**
     * ArrayList of {@link Movie}
     */
    private ArrayList<Movie> listOfMovieTypes;

    /**
     * MovieGoer object {@link MovieGoer}
     */
    private MovieGoer movieGoer;

    /**
     * Error message of the view
     */
    private String errorMessage;

    /**
     * Creates a MovieTypeView with the movieTitle and the movieGoer
     * 
     * @param movieTitle title of the movie chosen
     * @param movieGoer  movieGoer accessing the view {@link MovieGoer}
     */
    public MovieTypeView(String movieTitle, MovieGoer movieGoer) {
        this.movieTitle = movieTitle;
        this.listOfMovieTypes = new ArrayList<>();
        ArrayList<Movie> allMovieTypes = MovieManager.getMovieList(movieTitle);
        for (int i = 0; i < allMovieTypes.size(); i++) {
            if (allMovieTypes.get(i).getMovieShowingStatus() != MovieShowingStatus.COMING_SOON) {
                this.listOfMovieTypes.add(allMovieTypes.get(i));
            }
        }
        this.movieGoer = movieGoer;
        this.errorMessage = "";
    }

    /**
     * Method to print the movie type matching the movieTitle and calls
     * printMenuContent
     */
    public void printMovieType() {
        String content = "\n";

        int count = 0;
        for (int i = 0; i < listOfMovieTypes.size(); i++) {
            Movie movie = listOfMovieTypes.get(i);
            String index = String.format("%02d. ", i + 1);
            String payload = String.format(index + "%s\n", movie.getMovieType().getDisplayName());
            content = content + payload;
            count = i + 1;
        }
        String index = String.format("%02d. ", count + 1);
        String payload = String.format(index + "Quit and return back");
        content = content + payload;

        MainView.printMenuContent(content);
    }

    /**
     * Method to print boiler plate and calls printMovieType
     */
    public void printMenu() {
        MainView.printBoilerPlate("Movie Type for " + this.movieTitle);
        this.printMovieType();
    }

    /**
     * Method to call printMenu and accept user choice if the movie is available
     * Creates a CineplexView based on the movieType chosen
     */
    public void appContent() {
        int choice = -1;

        do {
            if (MovieMenuView.exit) {
                this.errorMessage = "";
                return;
            }
            if (this.listOfMovieTypes.size() == 0) {
                MainView.printMenuContent("This movie is currently not available!");
                System.out.println("Press any key to return: ");
                InputHandler.stringHandler();
                return;
            }

            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            choice = InputHandler.intHandler();

            if (choice < 0 || choice > this.listOfMovieTypes.size() + 1) {
                this.errorMessage = "Error! Please enter a valid input!";
                continue;
            }
            if (choice == this.listOfMovieTypes.size() + 1) {
                this.errorMessage = "";
                return;
            } else {
                this.errorMessage = "";
                CineplexView cineplex = new CineplexView(listOfMovieTypes.get(choice - 1), this.movieGoer);
                cineplex.appContent();
            }

        } while (true);
    }
}
