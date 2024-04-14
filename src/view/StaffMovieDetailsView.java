package src.view;

import src.handler.*;
import java.util.*;
import src.controller.*;
import src.model.*;
import src.model.enums.*;

/**
 * View for staff to view the list of movies
 * 
 * @author Jonathan Ng
 * @version 1.0
 */
public class StaffMovieDetailsView {
    private StaffConfigureMovieView staffConfigureMovieView;
    /**
     * ArrayList of {@link Movie} objects
     */
    private ArrayList<Movie> allMovies;

    /**
     * Error of this view
     */
    private String errorMessage;

    /**
     * Constructor for StaffMovieDetailsView
     * 
     * @param cinemaStaff is the cinema staff accessing this page
     */
    public StaffMovieDetailsView(CinemaStaff cinemaStaff) {
        this.errorMessage = "";
        this.allMovies = MovieManager.getAllMovieList(cinemaStaff);
    }

    /**
     * Method to print boiler plate and calls printMovieList
     */
    public void printMenu() {
        MainView.printBoilerPlate("List of Movies");
        this.printMovieList();
    }

    /**
     * Method to print all the movies available
     */
    public void printMovieList() {
        String content = "\nHere are the movies available:\n\n";
        int count = 0;
        for (int i = 0; i < this.allMovies.size(); i++) {
            Movie movie = allMovies.get(i);
            String index = String.format("%02d. ", (i + 1));
            String payload;
            if (movie.getMovieType() == MovieType.ThreeD) {
                payload = String.format(index + "[ %s ]\t\t", movie.getMovieType().getDisplayName());
            } else {
                payload = String.format(index + "[ %s ]\t", movie.getMovieType().getDisplayName());
            }
            payload += String.format("%s\n", movie.getMovieTitle());
            content = content + payload;
            count = i + 1;
        }
        String index = String.format("%02d. ", (count + 1));
        String payload = String.format(index + "Quit and return back\n");
        content = content + payload;

        MainView.printMenuContent(content);
    }

    /**
     * Method to call printMenu and obtains the index of the movie
     * Passes a movie object to the staffConfigureMovieView upon successful choice
     * selected
     */
    public void appContent() {
        int choice = -1;

        do {
            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            choice = InputHandler.intHandler();
            if (choice < 0 || choice > this.allMovies.size() + 1) {
                this.errorMessage = "Error! Please enter a valid input!";
                continue;
            }
            if (choice == this.allMovies.size() + 1) {
                this.errorMessage = "";
                return;
            } else {
                Movie movie = this.allMovies.get(choice - 1);
                this.staffConfigureMovieView = new StaffConfigureMovieView(movie);
                this.staffConfigureMovieView.appContent();
            }
        } while (true);
    }
}
