package src.view;

import src.model.*;
import src.model.enums.*;
import src.controller.*;
import src.handler.*;
import java.util.*;


public class CinemaView extends MainView {
    private Movie movie;
    private Cineplex cineplex;
    private ArrayList<Cinema> listOfCinemaClass;
    private MovieScheduleView movieScheduleView;
    private MovieGoer movieGoer;
    private ArrayList<CinemaClass> existingClass;
    private String errorMessage;


    public CinemaView(Cineplex cineplex, Movie movie, MovieGoer movieGoer) {
        this.movie = movie;
        this.cineplex = cineplex;
        this.listOfCinemaClass = CinemaManager.filterCinemaByCineplexMovie(this.cineplex, movie);
        this.movieGoer = movieGoer;
        this.errorMessage = "";
    }

    public void printCinemas() {
        this.existingClass = new ArrayList<CinemaClass>();
        String content = "\n";

        int count = 1;
        for (int i = 0; i < this.listOfCinemaClass.size(); i++) {
            Cinema cinema = this.listOfCinemaClass.get(i);
            if (!existingClass.contains(cinema.getCinemaClass())) {
                existingClass.add(cinema.getCinemaClass());
                String index = String.format("%02d. ", count);
                String payload = String.format(index + "%s\n", cinema.getCinemaClass().getDisplayName());
                content = content + payload;
                count++;
            }
        }
        String index = String.format("%02d. ", count);
        String payload = String.format(index + "Quit and return back");
        content = content + payload;

        MainView.printMenuContent(content);
    }

    /**
     * Standard function to print the menu for user to choose the next action
     */
    public void printMenu() {
        MainView.printBoilerPlate("Cinemas Showing " + this.movie.getMovieTitle());
        this.printCinemas();
    }


    public void appContent() {
        int choice = -1;
        do {
            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            choice = InputHandler.intHandler();
            if (choice < 0 || choice > this.existingClass.size() + 1) {
                this.errorMessage = "Error! Please enter a valid input!";
                continue;
            }
            if (choice == this.existingClass.size() + 1) {
                this.errorMessage = "";
                return;
            }
            ArrayList<Cinema> cinemaList = CinemaManager.filterCinemaByClass(this.existingClass.get(choice - 1),
                    cineplex);
            this.movieScheduleView = new MovieScheduleView(cinemaList, this.movie, this.movieGoer);
            this.errorMessage = "";
            this.movieScheduleView.appContent();

            if (MovieMenuView.exit) {
                this.errorMessage = "";
                return;
            }
        } while (true);
    }
}
