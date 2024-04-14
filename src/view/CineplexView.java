package src.view;

import java.util.*;
import src.model.*;
import src.controller.*;
import src.handler.*;


public class CineplexView extends MainView {
    private Movie movie;
    private ArrayList<Cineplex> cineplexes;
    private CinemaView cinemaView;
    private MovieGoer movieGoer;
    private String errorMessage;

    public CineplexView(Movie movie, MovieGoer movieGoer) {
        this.movie = movie;
        this.cineplexes = CineplexManager.filterCineplexesByMovie(this.movie);
        this.movieGoer = movieGoer;
        this.errorMessage = "";
    }


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


    public void printMenu() {
        MainView.printBoilerPlate("Cineplexes showing " + this.movie.getMovieTitle());
        this.printCineplex();
    }

 
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
