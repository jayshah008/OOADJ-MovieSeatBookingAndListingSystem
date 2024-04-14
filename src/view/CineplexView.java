package src.view;

import java.util.*;
import src.model.*;
import src.controller.*;
import src.handler.*;

/**
 * View class for handling all the UI related to {@link Cineplex}
 * 
 * @author Lee Juin, Jerick
 * @version 1.0
 */
public class CineplexView extends MainView {
    private Movie movie;
    private ArrayList<Cineplex> cineplexes;
    private CinemaView cinemaView;
    private MovieGoer movieGoer;
    private String errorMessage;

    /**
     * Constructor
     * 
     * @param movie     is the movie selected by the Movie Goer
     * @param movieGoer is the target Movie Goer
     */
    public CineplexView(Movie movie, MovieGoer movieGoer) {
        this.movie = movie;
        this.cineplexes = CineplexManager.filterCineplexesByMovie(this.movie);
        this.movieGoer = movieGoer;
        this.errorMessage = "";
    }

    /**
     * Method to print the cineplexes which are showing the movie selected by the
     * user
     */
    public void printCineplex() {
        String content = "";
        int count = 0;
        for (int i = 0; i < cineplexes.size(); i++) {
            Cineplex cineplex = cineplexes.get(i);
            String index = String.format("%02d. ", i + 1);
            String payload = String.format(index + "%s\n", cineplex.getCineplexName());
            payload += String.format("%s\n", cineplex.getCineplexLocation());

            content = content + payload;
            count = i + 1;
        }
        String index = String.format("%02d. ", count + 1);
        String payload = String.format(index + "Quit and return back");
        content = content + payload;

        MainView.printMenuContent(content);
    }

    /**
     * Standard function to print the menu for user to choose the next action
     */
    public void printMenu() {
        MainView.printBoilerPlate("Cineplexes showing " + this.movie.getMovieTitle());
        this.printCineplex();
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
            if (choice < 0 || choice > this.cineplexes.size() + 1) {
                this.errorMessage = "Error! Please enter a valid input!";
                continue;
            }
            if (choice == this.cineplexes.size() + 1) {
                this.errorMessage = "";
                return;
            }
            this.cinemaView = new CinemaView(cineplexes.get(choice - 1), this.movie, this.movieGoer);
            this.errorMessage = "";
            this.cinemaView.appContent();

            if (MovieMenuView.exit) {
                this.errorMessage = "";
                return;
            }

        } while (true);
    }
}
